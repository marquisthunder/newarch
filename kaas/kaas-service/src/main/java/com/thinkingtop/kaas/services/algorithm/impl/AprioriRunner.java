package com.thinkingtop.kaas.services.algorithm.impl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.AlgorithmGeneral;
import com.thinkingtop.kaas.services.algorithm.combinationutil.CombinationModel;
import com.thinkingtop.kaas.services.algorithm.dao.FileHistoryDAO;
import com.thinkingtop.kaas.services.algorithm.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.algorithm.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.algorithm.model.KaasRule;
import com.thinkingtop.kaas.services.algorithm.util.AlgorithmProperties;
import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;
import com.thinkingtop.kaas.services.algorithm.util.OfConcurrentHashMap;

/**
 * Generation rule class
 * @author roadahead
 *
 */
@Component("aprioriRunner")
public class AprioriRunner extends AlgorithmGeneral implements Algorithm{
    static Logger logger=Logger.getLogger(AprioriRunner.class);
    private String dateName;
    private int actualThreadNum;
    private int threadEndNum;
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
    public KaasOrderFrequentDAO getOfdao() {
        return super.getOfdao();
    }
    public KaasRuleDAO getRdao() {
        return super.getRdao();
    }
    
    private synchronized void oneThreadEnd(){
    	threadEndNum++;
    }
    
    public String[] getRecommend(String inputItems, int outputItemsNum,int outputQuantitye){
    		return getRdao().getRuleMap(inputItems,outputItemsNum,outputQuantitye);
    }

    public void println(){
    	/*logger.info("------------------------------------println properties ");
    	logger.info("fileHistoryDAO:  "+super.getFileHistoryDAO().getClass());
    	logger.info("ofdao:  "+getOfdao().getClass());
    	logger.info("rdao:  "+getRdao().getClass());
    	logger.info("threadNum:  "+super.getThreadNum());
    	logger.info("folder:  "+getFolder());
    	logger.info("waitTime:  "+super.getWaitTime());*/
    	logger.info("submitLoopMaxStr:  "+getSubmitLoopMaxStr());
    	logger.info("combinationMaxSizeStr:  "+getCombinationMaxSizeStr());
    	logger.info("frequencyLowerLimitStr:  "+getfrequencyLowerLimitStr());
    	logger.info("------------------------------------println properties end");
    }
    
    
    public void runIt(int name){
    println();
    	dateName = "date"+name;
        runTimeRecord0 = System.nanoTime();
        logger.info("of start time :"+runTimeRecord0);
    	getOfdao().setFileAll(new OfConcurrentHashMap<String, KaasOrderFrequent>());
    	getRdao().setMarsRuleAll(new ConcurrentHashMap<String, KaasRule>());
    	threadEndNum=0;
        List<String> filelist=super.getFileHistoryDAO().getFileList();
        if(filelist == null || filelist.size() == 0){
            logger.info("No orders are needed to do offline training!");
            return;
        }
        actualThreadNum=Integer.parseInt(super.getThreadNum());
        int loop;
        int Remainder;
        if(actualThreadNum<filelist.size()){
            loop=filelist.size()/actualThreadNum;
            Remainder=filelist.size()%actualThreadNum;
        }else{
            loop=1;
            Remainder=0;
            actualThreadNum = filelist.size();
        }
        logger.info("Do offline training With "+Remainder+" Threads!");
        if(super.getTaskExecutor().getThreadPoolExecutor().isShutdown()){
        	logger.info("this thread isShutdown");
        	super.getTaskExecutor().initialize();
        }
        int submitLoopMax=Integer.parseInt(getSubmitLoopMaxStr());
        int combinationMaxSize=Integer.parseInt(getCombinationMaxSizeStr());
        int  frequencyLowerLimit=Integer.parseInt(getfrequencyLowerLimitStr());
        for(int i=0;i<actualThreadNum;i++){
            List<String> partOfFiles=new ArrayList<String>();
            int start=0;
            int end=0;
            if(Remainder>i){
	            start=i*(loop+1);
	            end = (i+1)*(loop+1);
	            end=end<filelist.size()?end:filelist.size();
            }else{
            	start=Remainder*(loop+1)+(i-Remainder)*loop;
            	end = Remainder*(loop+1)+(i-Remainder+1)*loop;
            	end = end<filelist.size()?end:filelist.size();
            }
            logger.info("run in of:"+start+"~"+end);
            for(int j=start;j<end;j++){
                partOfFiles.add(filelist.get(j));
            }

            KaasAprioriTask kaasAprioriTask = new KaasAprioriTask(partOfFiles,submitLoopMax,combinationMaxSize,frequencyLowerLimit);
            super.getTaskExecutor().execute(kaasAprioriTask);
        }
        int time=Integer.parseInt(super.getWaitTime());
        try{
        	while(threadEndNum!=actualThreadNum){
        		Thread.sleep(time);
        	}
        }catch(Exception e){
            ;
        }
        super.getTaskExecutor().getThreadPoolExecutor().shutdownNow();
        logger.info("run all need time: "+runAllTime);
        logger.info("Offline Training Task Finished Once!");

        return;

    }

    private class KaasAprioriTask implements Runnable {
        private List<String> filelist;
        private Map<String,Integer> submitMap;

        private int submitLoopCur;
        private int submitLoopNum;
        private int submitLoopMax;
        private int combinationMaxSize;
        private int frequencyLowerLimit;
        
        public void printlnM(){
        	logger.info(filelist.size());
        	logger.info(submitMap.size());
        	logger.info(submitLoopCur);
        	logger.info(submitLoopNum);
        	logger.info(submitLoopMax);
        	logger.info(combinationMaxSize);
        	logger.info(frequencyLowerLimit);
        }
        public KaasAprioriTask(List<String> filelist,int submitLoopMax,int combinationMaxSize,int frequencyLowerLimit) {
            this.filelist = filelist;
            this.submitLoopMax = submitLoopMax;
            submitLoopCur = 0;
            submitLoopNum = 0;
            this.combinationMaxSize = combinationMaxSize;
            this.frequencyLowerLimit = frequencyLowerLimit;
            submitMap = new HashMap<String,Integer>();
        }

        public void run() {
//printlnM();
            String basePathes = getKaasDataPath().getItemDataPath();
            String realBase = null;
            File tmp=new File(basePathes);
            logger.info("tmp.tustong ----"+tmp.toString());
            if(tmp.isDirectory()){
                realBase=basePathes;
            }
            if (realBase == null) {
                logger.warn("No valide order folders");
                return;
            }

            List<String> notFinished = new ArrayList<String>(filelist);
            List<String> finished = new ArrayList<String>();
            int totalFiles = filelist.size();
            int indexFile = 0;

            for (String fileone : filelist) {
                indexFile++;

                Iterator<Element> kaasOrders = getKaasDataPath().getKaasOrders(getFolder()+"/"+fileone);
                if(kaasOrders==null){
                	logger.warn("local offline file may be moved or renamed!");
                    continue;
                }
                

                logger.info("Current File Name:" + fileone
                        + " | Training Progress:" + indexFile + "/"
                        + totalFiles);
                
                String line = null;
                while (kaasOrders.hasNext()) {
					Element kaasOrder = kaasOrders.next();
					/*Attribute leaderAttr =kaasOrder.attribute("id");
					logger.info("kaasOrder ID :"+leaderAttr.getValue());*/
					//logger.info("kaasOrder Name : "+kaasOrder.getName());
					line = kaasOrder.getText();
					if(line==null){
						continue;
					}
					if(line.endsWith(",")){
						line = line.substring(0, line.length()-1);
					}
					//logger.info(line);
				    Set<String> idlist = getProductsInOrderLine(line);
            //logger.info(idlist.toString());
				    addAIdOrder(idlist);
				}

            }
            genRulesFromMemory();
            oneThreadEnd();
            if(threadEndNum==actualThreadNum){
            	getOfdao().submit();
            	getRdao().submit(dateName);
            	long consumingTimeOf = System.nanoTime();
            	runAllTime += consumingTimeOf - runTimeRecord0;
            	logger.warn("generate all consuming End time:"+ consumingTimeOf +" seconds!");
                logger.warn("generate all consuming:"+(consumingTimeOf- runTimeRecord0)+" seconds!");
            }
        }
        
        private Set<String> getProductsInOrderLine(String line){
            if(line == null || line.length()<=0){
                return null;
            }
            String[] ss = line.split(getItemDelimiter());
            if(ss.length <= 1){
                return null;
            }
            int idpos=0;
            Set<String> idlist=new TreeSet<String>();
            while (idpos < ss.length){
                String tmp = new String(ss[idpos]); //hack for reducing memory from big line
                if(tmp.equals("")){
                    //logger.error(line.replaceAll("\u00fd", " | "));
                    idpos+=1;
                    continue;
                }
                idlist.add(tmp);
                idpos +=1;
            }
            return idlist;
        }
        
        private void addAIdOrder(Set<String> idlist){
            if(idlist == null || idlist.size() == 0){
                return;
            }

            CombinationModel cm = new CombinationModel(idlist.toArray(new String[0]),submitMap);
            cm.genCombinations(combinationMaxSize);
            cm=null;
        //logger.info("submitMap:"+submitMap.size());
            submitLoopCur++;
            if(submitLoopCur == submitLoopMax){
                genRulesFromMemory();
            }
        }

        public boolean genRulesFromMemory(){
            List<KaasOrderFrequent> olist=new ArrayList<KaasOrderFrequent>();
            List<KaasRule> rlist=new ArrayList<KaasRule>();

            long startTime1 = System.nanoTime();
            for(Map.Entry<String, Integer> me : submitMap.entrySet()){
            	
            	Map<String,Integer> histery = getRdao().getRuleMap(me.getKey());
        		if(histery.size()>0){
        			List<KaasRule> subRlist = genMapRulesByLine(histery);
        			rlist.addAll(subRlist);
        		}
        		
                KaasOrderFrequent of = new KaasOrderFrequent();
                of.setCombination(me.getKey());
                of.setFrequent(me.getValue());
                of.setItemNum(me.getKey().split(getItemDelimiter()).length);
                of.setOfType("all");
            //logger.info("my key:"+me.getKey());
            //logger.info(of.getCombination());
                olist.add(of);
             //logger.info("olist:"+olist.size());
                int newSup=me.getValue();
                
                KaasOrderFrequent tmp = getOfdao().findOneByProperty("freqSet", me.getKey());
                if(tmp != null){
                    newSup+=tmp.getFrequent();
                }
                
                if(newSup >= frequencyLowerLimit){
                    List<KaasRule> subRlist = genRulesByLine(me.getKey(),newSup);
                    rlist.addAll(subRlist);
                    continue;
                }
            }
        //logger.info(olist.size()+":"+olist.get(0).getCombination()+":"+olist.get(0).getFrequent());

            submitMap.clear();
            submitLoopCur = 0;


            for(KaasOrderFrequent o : olist){
                int rval=0;
                rval = getOfdao().submit(o);
                if(rval !=1){
                    //all error case
                    if(rval == 2){
                        //concurrent case
                    	int maxTryCountNum=Integer.valueOf(getMaxTryCount());
                        while(rval == 2 && maxTryCountNum>0){
                            rval=getOfdao().submit(o);
                            maxTryCountNum--;
                        }
                        if(rval==2){
                            logger.warn("exceed maxTryCount of order frequent submit!");
                        }
                    }
                }
            }
            //getOfdao().submit();
            for(KaasRule r: rlist){
                int rval=0;
                getRdao().submit(r);
                if(rval !=1){
                    //all error case
                    if(rval == 2){
                        //concurrent case
                        int maxTryCountNum=Integer.valueOf(getMaxTryCount());
                        while(rval == 2 && maxTryCountNum>0){
                            rval=getRdao().submit(r);
                            maxTryCountNum--;
                        }
                        if(rval==2){
                            logger.warn("exceed maxTryCount of rule submit!");
                        }
                    }
                }
            }
            //getRdao().submit();

            logger.info("Loop times:"+submitLoopNum++);
            return true;
        }

        private List<KaasRule> genRulesByLine(String line,int baseSupport) {

            Map<String,Integer> rulemap = null;
            //generate all rules
            String[] lineArr=line.split(getItemDelimiter());
            CombinationModel cm = new CombinationModel(lineArr);
            rulemap = cm.genRuleCombinations();
            cm = null;
            lineArr=null;
            List<KaasRule> rlist= new ArrayList<KaasRule>();
            if(rulemap != null){
                for (Map.Entry<String, Integer> me: rulemap.entrySet()){
                    String[] tmp = me.getKey().split("\\|");
                    KaasOrderFrequent of = getOfdao().findOneByProperty("freqSet", tmp[0]);
                    if(of != null || submitMap.containsKey(tmp[0])){
                        Double downSup = (of == null?0.0:of.getFrequent())+submitMap.get(tmp[0]);
                        Double x = (baseSupport*1.0)/downSup;
                        KaasRule r = new KaasRule();
                        r.setProducts(tmp[0]);
                        r.setRecommendation(tmp[1]);
                        r.setConfidence(x);
                        r.setFlag("general");
                        ////logger.info(tmp[0]+"->"+tmp[1]+":"+x.toString());
                        //getRdao().submit(r);
                        rlist.add(r);
                        tmp=null;
                        x=null;
                        r=null;
                    }
                    else{
                        logger.info("error:"+tmp[0]+" cannot be found!");
                    }

                }
                //logger.info("line rule over");
            }else {
                //logger.info("line: "+line+" is skipped!");
            }
            return rlist;
        }
        
        private List<KaasRule> genMapRulesByLine(Map<String,Integer> history){
        	List<KaasRule> rlist= new ArrayList<KaasRule>();
        	for (Map.Entry<String, Integer> me: history.entrySet()){
                String[] tmp = me.getKey().split("\\|");
                if(submitMap.containsKey(tmp[0]+getItemDelimiter()+tmp[1])){
                	continue;
                }
                KaasOrderFrequent hi = getOfdao().findOneByProperty("freqSet", tmp[0]+getItemDelimiter()+tmp[1]);
                if(hi==null){
                	continue;
                }
                KaasOrderFrequent of = getOfdao().findOneByProperty("freqSet", tmp[0]);
                if(of != null || submitMap.containsKey(tmp[0])){
                    Double downSup = (of == null?0.0:of.getFrequent())+submitMap.get(tmp[0]);
                    Double x = (hi.getFrequent()*1.0)/downSup;
                    KaasRule r = new KaasRule();
                    r.setProducts(tmp[0]);
                    r.setRecommendation(tmp[1]);
                    r.setConfidence(x);
                    r.setFlag("general");
                    ////logger.info(tmp[0]+"->"+tmp[1]+":"+x.toString());
                    //getRdao().submit(r);
                    rlist.add(r);
                    tmp=null;
                    x=null;
                    r=null;
                }
                else{
                    logger.info("error:"+tmp[0]+" cannot be found!");
                }

            }
        	return rlist;
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
