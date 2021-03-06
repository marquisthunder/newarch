package com.thinkingtop.kaas.services.algorithm.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.AlgorithmGeneral;
import com.thinkingtop.kaas.services.algorithm.combinationutil.CombinationModel;
import com.thinkingtop.kaas.services.algorithm.manage.KaasOrderFrequentManage;
import com.thinkingtop.kaas.services.algorithm.manage.KaasRuleManage;
import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.algorithm.model.KaasRule;
import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;

/**
 * Generation rule class
 * 
 * @author roadahead
 * 
 */
@Component("aprioriRunnerMultiThread")
public class AprioriRunnerMultiThread extends AlgorithmGeneral implements
		Algorithm {

	static Logger logger = Logger.getLogger(AprioriRunnerMultiThread.class);
	private String dataName;
	private int ofThreadNum;
	private int ofThreadEndNum;
	private int rThreadNum;
	private int rThreadEndNum;
	private long runAllTime;
	private long runTimeRecord0;

	public String getFolder() {
		return super.getFolder();
	}

	public String getSubmitLoopMaxStr() {
		return super.getAlgorithmProperties().getSubmitLoopMaxStr();
	}


	public String getCombinationMaxSizeStr() {
		return super.getAlgorithmProperties().getCombinationMaxSizeStr();
	}


    public KaasOrderFrequentManage getOfm() {
        return super.getOfm();
    }

    public KaasRuleManage getRm() {
        return super.getRm();
    }

	private synchronized void oneOfThreadEnd() {
		ofThreadEndNum++;
	}

	private synchronized void oneRThreadEnd() {
		rThreadEndNum++;
	}
	public void println() {
		logger.info("------------------------------------println properties ");
		logger.info("fileHistoryDAO:  " + super.getFileHistoryDAO().getClass());
		logger.info("ofdao:  " + getOfm().getClass());
		logger.info("rdao:  " + getRm().getClass());
		logger.info("threadNum:  " + super.getThreadNum());
		logger.info("folder:  " + getFolder());
		logger.info("waitTime:  " + super.getWaitTime());
		logger.info("submitLoopMaxStr:  " + getSubmitLoopMaxStr());
		logger.info("combinationMaxSizeStr:  " + getCombinationMaxSizeStr());
		logger.info("frequencyLowerLimitStr:  " + getfrequencyLowerLimitStr());
		logger.info("------------------------------------println properties end");
	}

	public void runIt(String name) {
		// println();
		dataName = name;
		runTimeRecord0 = System.nanoTime();
		logger.info("of start time :" + runTimeRecord0);
		getOfm().deleteAll();
		getRm().deleteAll();
		ofThreadEndNum = 0;
		List<String> filelist = super.getFileHistoryDAO().getFileList();
		if (filelist == null || filelist.size() == 0) {
			logger.info("No orders are needed to do offline training!");
			return;
		}
		ofThreadNum = Integer.parseInt(super.getThreadNum());
		int loop;
		int Remainder;
		if (ofThreadNum < filelist.size()) {
			loop = filelist.size() / ofThreadNum;
			Remainder = filelist.size() % ofThreadNum;
		} else {
			loop = 1;
			Remainder = 0;
			ofThreadNum = filelist.size();
		}
		logger.info("Do offline training With " + Remainder + " Threads!");
		if (super.getTaskExecutor().getThreadPoolExecutor().isShutdown()) {
			logger.info("this thread isShutdown");
			super.getTaskExecutor().initialize();
		}
		int submitLoopMax = Integer.parseInt(getSubmitLoopMaxStr());
		int combinationMaxSize = Integer.parseInt(getCombinationMaxSizeStr());
		int frequencyLowerLimit = Integer.parseInt(getfrequencyLowerLimitStr());
		for (int i = 0; i < ofThreadNum; i++) {
			List<String> partOfFiles = new ArrayList<String>();
			int start = 0;
			int end = 0;
			if (Remainder > i) {
				start = i * (loop + 1);
				end = (i + 1) * (loop + 1);
				end = end < filelist.size() ? end : filelist.size();
			} else {
				start = Remainder * (loop + 1) + (i - Remainder) * loop;
				end = Remainder * (loop + 1) + (i - Remainder + 1) * loop;
				end = end < filelist.size() ? end : filelist.size();
			}
			logger.info("run in of:" + start + "~" + end);
			for (int j = start; j < end; j++) {
				partOfFiles.add(filelist.get(j));
			}

			KaasAprioriTask kaasAprioriTask = new KaasAprioriTask(partOfFiles,
					submitLoopMax, combinationMaxSize, frequencyLowerLimit);
			super.getTaskExecutor().execute(kaasAprioriTask);
		}
		int time = Integer.parseInt(super.getWaitTime());
		try {
			while (ofThreadEndNum != ofThreadNum) {
				Thread.sleep(time);
			}
			if (ofThreadEndNum == ofThreadNum) {
				logger.info("run in R");
				runR();
			}
			logger.info("run all need time: " + runAllTime);
		} catch (Exception e) {
			;
		}
		getRm().submit(dataName);
		super.getTaskExecutor().getThreadPoolExecutor().shutdownNow();
		logger.info("Offline Training Task Finished Once!");

		return;
	}

	private void runR() {
		runTimeRecord0 = System.nanoTime();
		logger.info("R start time :" + runTimeRecord0);
		long ofsize = getOfm().size();
		if (ofsize <= 0) {
			return;
		}
		rThreadNum = Integer.parseInt(super.getThreadNum());
		int loop;
		int Remainder;
		if (rThreadNum < ofsize) {
			loop = (int) (ofsize / rThreadNum);
			Remainder = (int) (ofsize % rThreadNum);
		} else {
			loop = 1;
			Remainder = 0;
			rThreadNum = (int) ofsize;
		}

		int submitLoopMax = Integer.parseInt(getSubmitLoopMaxStr());
		int frequencyLowerLimit = Integer.parseInt(getfrequencyLowerLimitStr());
		for (int i = 0; i < rThreadNum; i++) {
			int start;
			int end;
			if (Remainder > i) {
				start = i * (loop + 1);
				end = (i + 1) * (loop + 1);
				end = (int) (end < ofsize ? end : ofsize);
			} else {
				start = Remainder * (loop + 1) + (i - Remainder) * loop;
				end = Remainder * (loop + 1) + (i - Remainder + 1) * loop;
				end = (int) (end < ofsize ? end : ofsize);
			}
			logger.info("run in R " + start + "~" + end);
			KaasAprioriTask marsAprioriTask = new KaasAprioriTask(start, end,
					submitLoopMax, frequencyLowerLimit);
			super.getTaskExecutor().execute(marsAprioriTask);
		}
		int time = Integer.parseInt(super.getWaitTime());
		try {
			while (rThreadEndNum != rThreadNum) {
				Thread.sleep(time);
			}
		} catch (Exception e) {
			;
		}
		logger.info("Offline Training Task Finished Once!");
		// System.out.println(threadNum);
	}

	private class KaasAprioriTask implements Runnable {
		private List<String> filelist;
		private Map<String, Integer> submitMap;

		private int submitLoopCur;
		private int submitLoopNum;
		private int submitLoopMax;
		private int combinationMaxSize;
		private int frequencyLowerLimit;
		private int start;
		private int end;

		public void printlnM() {
			logger.info(filelist.size());
			logger.info(submitMap.size());
			logger.info(submitLoopCur);
			logger.info(submitLoopNum);
			logger.info(submitLoopMax);
			logger.info(combinationMaxSize);
			logger.info(frequencyLowerLimit);
		}

		public KaasAprioriTask(List<String> filelist, int submitLoopMax,
				int combinationMaxSize, int frequencyLowerLimit) {
			this.filelist = filelist;
			this.submitLoopMax = submitLoopMax;
			submitLoopCur = 0;
			submitLoopNum = 0;
			this.combinationMaxSize = combinationMaxSize;
			this.frequencyLowerLimit = frequencyLowerLimit;
			submitMap = new HashMap<String, Integer>();
		}

		public KaasAprioriTask(int start, int end, int submitLoopMax,
				int frequencyLowerLimit) {
			this.start = start;
			this.end = end;
			submitLoopCur = 0;
			this.submitLoopMax = submitLoopMax;
			this.frequencyLowerLimit = frequencyLowerLimit;
		}

		public void run() {
			if (ofThreadEndNum == ofThreadNum) {
				runAndRules();
				oneRThreadEnd();
				if (rThreadEndNum == rThreadNum) {
					long consumingTimeOf = System.nanoTime();
					runAllTime += consumingTimeOf - runTimeRecord0;
					logger.warn("generate all Rules End time:"
							+ consumingTimeOf + " seconds!");
					logger.warn("generate all Rules:"
							+ (consumingTimeOf - runTimeRecord0) + " seconds!");
				}
				return;
			}
			String basePathes = getKaasDataPath().getItemDataPath();
			String realBase = null;
			File tmp = new File(basePathes);
			if (tmp.isDirectory()) {
				realBase = basePathes;
			}
			if (realBase == null) {
				logger.info("No valide order folders");
				return;
			}

			List<String> notFinished = new ArrayList<String>(filelist);
			List<String> finished = new ArrayList<String>();
			int totalFiles = filelist.size();
			int indexFile = 0;

			for (String fileone : filelist) {
				indexFile++;

				indexFile++;

				Iterator<Element> kaasOrders = getKaasDataPath().getKaasOrders(
						getFolder() + "/" + fileone);
				if (kaasOrders == null) {
					logger.warn("local offline file may be moved or renamed!");
					continue;
				}

				logger.info("Current File Name:" + fileone
						+ " | Training Progress:" + indexFile + "/"
						+ totalFiles);

				String line = null;
				while (kaasOrders.hasNext()) {
					Element kaasOrder = kaasOrders.next();
					/*
					 * Attribute leaderAttr =kaasOrder.attribute("id");
					 * logger.info("kaasOrder ID :"+leaderAttr.getValue());
					 */
					// logger.info("kaasOrder Name : "+kaasOrder.getName());
					line = kaasOrder.getText();
					if (line == null) {
						continue;
					}
					if (line.endsWith(",")) {
						line = line.substring(0, line.length() - 1);
					}
					// logger.info(line);
					Set<String> idlist = getProductsInOrderLine(line);
					// logger.info(idlist.toString());
					addAIdOrder(idlist);
				}
			}
			genCombinationFromMemory();
			oneOfThreadEnd();
			if (ofThreadEndNum == ofThreadNum) {
				long consumingTimeOf = System.nanoTime();
				runAllTime += consumingTimeOf - runTimeRecord0;
				logger.warn("generate all consuming End time:"
						+ consumingTimeOf + " seconds!");
				logger.warn("generate all consuming:"
						+ (consumingTimeOf - runTimeRecord0) + " seconds!");
			}
		}

		private Set<String> getProductsInOrderLine(String line) {
			if (line == null || line.length() <= 0) {
				return null;
			}
			String[] ss = line.split(getItemDelimiter());
			if (ss.length <= 1) {
				return null;
			}
			int idpos = 0;
			Set<String> idlist = new TreeSet<String>();
			while (idpos < ss.length) {
				String tmp = new String(ss[idpos]); // hack for reducing memory
													// from big line
				if (tmp.equals("")) {
					// logger.error(line.replaceAll("\u00fd", " | "));
					idpos += 1;
					continue;
				}
				idlist.add(tmp);
				idpos += 1;
			}
			return idlist;
		}

		private void addAIdOrder(Set<String> idlist) {
			if (idlist == null || idlist.size() == 0) {
				return;
			}

			CombinationModel cm = new CombinationModel(
					idlist.toArray(new String[0]), submitMap);
			cm.genCombinations(combinationMaxSize);
			cm = null;
			// logger.info("submitMap:"+submitMap.size());
			submitLoopCur++;
			if (submitLoopCur == submitLoopMax) {
				genCombinationFromMemory();
			}
		}

		public boolean genCombinationFromMemory() {
			List<KaasOrderFrequent> olist = new ArrayList<KaasOrderFrequent>();
			List<KaasRule> rlist = new ArrayList<KaasRule>();

			for (Map.Entry<String, Integer> me : submitMap.entrySet()) {

				KaasOrderFrequent of = new KaasOrderFrequent();
				of.setCombination(me.getKey());
				of.setFrequent(me.getValue());
				of.setItemNum(me.getKey().split(getItemDelimiter()).length);
				of.setOfType("all");
				// logger.info("my key:"+me.getKey());
				// logger.info(of.getCombination());
				olist.add(of);
				// logger.info("olist:"+olist.size());

			}
			// logger.info(olist.size()+":"+olist.get(0).getCombination()+":"+olist.get(0).getFrequent());

			submitMap.clear();
			submitLoopCur = 0;

			for (KaasOrderFrequent o : olist) {
				int rval = 0;
				rval = getOfm().add(o);
				if (rval != 1) {
					// all error case
					if (rval == 2) {
						// concurrent case
						int maxTryCountNum = Integer.valueOf(getMaxTryCount());
						while (rval == 2 && maxTryCountNum > 0) {
							rval = getOfm().add(o);
							maxTryCountNum--;
						}
						if (rval == 2) {
							logger.warn("exceed maxTryCount of order frequent submit!");
						}
					}
				}
			}
			// ofdao.submit();

			logger.info("Loop times:" + submitLoopNum++);
			return true;
		}

		private void runAndRules() {
			List<KaasRule> rlist = new ArrayList<KaasRule>();
			submitLoopCur = 0;
			for (int i = start; i < end; i++) {
				KaasOrderFrequent of = getOfm().getOrderFrequent(i);
				if (of != null && of.getFrequent() >= frequencyLowerLimit) {
					if (of.getCombination().matches(".?")) {
						continue;
					}
					List<KaasRule> subRlist = genRulesByLine(
							of.getCombination(), of.getFrequent());
					rlist.addAll(subRlist);
					// logger.info("println Combination:"+of.getCombination()+"-Frequent:"+of.getFrequent());
					// logger.info("frequencyLowerLimit:"+frequencyLowerLimit);
				}
				// logger.info("submitLoopCur : "+submitLoopCur+" : "+i);
				submitLoopCur++;
				if (submitLoopCur == submitLoopMax) {
					genRulesFromMemory(rlist);
					continue;
				}
			}
			genRulesFromMemory(rlist);
		}

		private List<KaasRule> genRulesByLine(String line, int baseSupport) {

			Map<String, Integer> rulemap = null;
			// generate all rules
			String[] lineArr = line.split(getItemDelimiter());
			CombinationModel cm = new CombinationModel(lineArr);
			rulemap = cm.genRuleCombinations();
			cm = null;
			lineArr = null;
			List<KaasRule> rlist = new ArrayList<KaasRule>();
			if (rulemap != null) {
				for (Map.Entry<String, Integer> me : rulemap.entrySet()) {
					String[] tmp = me.getKey().split("\\|");
					KaasOrderFrequent of = getOfm().getOrderFrequent(tmp[0]);
					if (of != null) {
						Double downSup = of.getFrequent() * 1.0;
						Double x = (baseSupport * 1.0) / downSup;
						KaasRule r = new KaasRule();
						r.setProducts(tmp[0]);
						r.setRecommendation(tmp[1]);
						r.setConfidence(x);
						r.setFlag("general");
						// //logger.info(tmp[0]+"->"+tmp[1]+":"+x.toString());
						// rdao.submit(r);
						rlist.add(r);
						tmp = null;
						x = null;
						r = null;
					} else {
						logger.info("error:" + tmp[0] + " cannot be found!");
					}

				}
				// logger.info("line rule over");
			} else {
				// logger.info("line: "+line+" is skipped!");
			}
			return rlist;
		}

		public boolean genRulesFromMemory(List<KaasRule> rlist) {
			for (KaasRule r : rlist) {
				int rval = 0;
				getRm().add(r);
				if (rval != 1) {
					// all error case
					if (rval == 2) {
						// concurrent case
						int maxTryCountNum = Integer.valueOf(getMaxTryCount());
						while (rval == 2 && maxTryCountNum > 0) {
							rval = getRm().add(r);
							maxTryCountNum--;
						}
						if (rval == 2) {
							logger.warn("exceed maxTryCount of rule submit!");
						}
					}
				}
			}
			// rdao.submit();
			submitLoopCur = 0;
			rlist.clear();
			return true;
		}

	}

	public String getMaxTryCount() {
		return super.getMaxTryCount();
	}

	public String getItemDelimiter() {
		return super.getItemDelimiter();
	}

	public String getfrequencyLowerLimitStr() {
		return super.getAlgorithmProperties().getFrequencyLowerLimitStr();
	}


	public KaasDataPath getKaasDataPath() {
		return super.getKaasDataPath();
	}

}
