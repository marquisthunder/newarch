package com.thinkingtop.kaas.services.apriori;

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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.combinationutil.CombinationModel;
import com.thinkingtop.kaas.services.dao.FileHistoryDAO;
import com.thinkingtop.kaas.services.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.model.KaasRule;

/**
 * Generation rule class
 * @author roadahead
 *
 */
public class AprioriRunnerMultiThread {

    static Logger logger=Logger.getLogger(AprioriRunnerMultiThread.class);
    private ThreadPoolTaskExecutor taskExecutor;
    private FileHistoryDAO fileHistoryDAO;
    private KaasOrderFrequentDAO ofdao;
    private KaasRuleDAO rdao;
    private String threadNum;
    private String dataPath;
    private String folder;
    private String waitTime;
    private String submitLoopMaxStr;
    private String combinationMaxSizeStr;
    private String frequencyLowerLimitStr;
    private String itemDelimiter;
    private String maxTryCount;
    private int ofThreadNum;
    private int ofThreadEndNum;
    private int rThreadNum;
    private int rThreadEndNum;
    private long runAllTime;
    private long runTimeRecord0;
    
    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }
    
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
    public String getThreadNum() {
        return threadNum;
    }
    public void setThreadNum(String threadNum) {
        this.threadNum = threadNum;
    }
    public String getDataPath() {
        return dataPath;
    }
    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }
    public String getFolder() {
        return folder;
    }
    public void setFolder(String folder) {
        this.folder = folder;
    }
    public String getWaitTime() {
        return waitTime;
    }
    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }
    public FileHistoryDAO getFileHistoryDAO() {
        return fileHistoryDAO;
    }
    public void setFileHistoryDAO(FileHistoryDAO fileHistoryDAO) {
        this.fileHistoryDAO = fileHistoryDAO;
    }
    public String getSubmitLoopMaxStr() {
        return submitLoopMaxStr;
    }
    public void setSubmitLoopMaxStr(String submitLoopMaxStr) {
        this.submitLoopMaxStr = submitLoopMaxStr;
    }
    public String getCombinationMaxSizeStr() {
        return combinationMaxSizeStr;
    }
    public void setCombinationMaxSizeStr(String combinationMaxSizeStr) {
        this.combinationMaxSizeStr = combinationMaxSizeStr;
    }
    public KaasOrderFrequentDAO getOfdao() {
        return ofdao;
    }
    public void setOfdao(KaasOrderFrequentDAO ofdao) {
        this.ofdao = ofdao;
    }
    public KaasRuleDAO getRdao() {
        return rdao;
    }
    public void setRdao(KaasRuleDAO rdao) {
        this.rdao = rdao;
    }
    
    private synchronized void oneOfThreadEnd(){
    	ofThreadEndNum++;
    }
    
    private synchronized void oneRThreadEnd(){
    	rThreadEndNum++;
    }
    
    public String getRecommend(String basisItems, int basisSize){
    	return rdao.getRuleMap(basisItems,basisSize);
    }

    public void println(){
    	logger.info("------------------------------------println properties ");
    	logger.info("fileHistoryDAO:  "+fileHistoryDAO.getClass());
    	logger.info("ofdao:  "+ofdao.getClass());
    	logger.info("rdao:  "+rdao.getClass());
    	logger.info("threadNum:  "+threadNum);
    	logger.info("dataPath:  "+dataPath);
    	logger.info("folder:  "+folder);
    	logger.info("waitTime:  "+waitTime);
    	logger.info("submitLoopMaxStr:  "+submitLoopMaxStr);
    	logger.info("combinationMaxSizeStr:  "+combinationMaxSizeStr);
    	logger.info("frequencyLowerLimitStr:  "+frequencyLowerLimitStr);
    	logger.info("------------------------------------println properties end");
    }
    
    
    public void runIt(){
    //println();
    runTimeRecord0 = System.nanoTime();
    logger.info("of start time :"+runTimeRecord0);
    	ofdao.setFileAll(new HashMap<String, KaasOrderFrequent>());
    	rdao.setMarsRuleAll(new HashMap<String, KaasRule>());
    	ofThreadEndNum=0;
        List<String> filelist=fileHistoryDAO.getFileList();
        if(filelist == null || filelist.size() == 0){
            logger.info("No orders are needed to do offline training!");
            return;
        }
        ofThreadNum=Integer.parseInt(threadNum);
        int loop;
        int Remainder;
        if(ofThreadNum<filelist.size()){
            loop=filelist.size()/ofThreadNum;
            Remainder=filelist.size()%ofThreadNum;
        }else{
            loop=1;
            Remainder=0;
            ofThreadNum = filelist.size();
        }
        logger.info("Do offline training With "+Remainder+" Threads!");
        if(taskExecutor.getThreadPoolExecutor().isShutdown()){
        	logger.info("this thread isShutdown");
            taskExecutor.initialize();
        }
        int submitLoopMax=Integer.parseInt(submitLoopMaxStr);
        int combinationMaxSize=Integer.parseInt(combinationMaxSizeStr);
        int  frequencyLowerLimit=Integer.parseInt(frequencyLowerLimitStr);
        for(int i=0;i<ofThreadNum;i++){
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
            taskExecutor.execute(kaasAprioriTask);
        }
        int time=Integer.parseInt(waitTime);
        try{
        	while(ofThreadEndNum!=ofThreadNum){
        		Thread.sleep(time);
        	}
        	if(ofThreadEndNum==ofThreadNum){
        logger.info("run in R");
        		runR();
        	}
        logger.info("run all need time: "+runAllTime);
        }catch(Exception e){
            ;
        }
        logger.info("Offline Training Task Finished Once!");

        return;

    }
    
    private void runR() {
    	runTimeRecord0 = System.nanoTime();
        logger.info("R start time :"+runTimeRecord0);
    	int ofsize = ofdao.size();
    	if(ofsize<=0){
    		return;
    	}
    	rThreadNum = Integer.parseInt(threadNum);
        int loop;
        int Remainder;
        if(rThreadNum<ofsize){
            loop = ofsize/rThreadNum;
            Remainder = ofsize%rThreadNum;
        }else{
            loop = 1;
            Remainder = 0;
            rThreadNum = ofsize;
        }
        
        int submitLoopMax=Integer.parseInt(submitLoopMaxStr);
        int combinationMaxSize=Integer.parseInt(combinationMaxSizeStr);
        int  frequencyLowerLimit=Integer.parseInt(frequencyLowerLimitStr);
        for(int i=0;i<rThreadNum;i++){
            int start;
            int end;
            if(Remainder>i){
	            start = i*(loop+1);
	            end = (i+1)*(loop+1);
	            end = end < ofsize ? end : ofsize;
            }else{
            	start = Remainder*(loop+1)+(i-Remainder)*loop;
            	end = Remainder*(loop+1)+(i-Remainder+1)*loop;
            	end = end<ofsize?end:ofsize;
            }
        logger.info("run in R "+start+"~"+end);
            KaasAprioriTask marsAprioriTask = new KaasAprioriTask(start,end,submitLoopMax,frequencyLowerLimit);
            taskExecutor.execute(marsAprioriTask);
        }
        int time=Integer.parseInt(waitTime);
        try{
        	while(rThreadEndNum!=rThreadNum){
        		Thread.sleep(time);
        	}
        }catch(Exception e){
            ;
        }
        logger.info("Offline Training Task Finished Once!");
//System.out.println(threadNum);
	}

    private class KaasAprioriTask implements Runnable {
        private List<String> filelist;
        private Map<String,Integer> submitMap;

        private int submitLoopCur;
        private int submitLoopNum;
        private int submitLoopMax;
        private int combinationMaxSize;
        private int frequencyLowerLimit;
        private int start;
        private int end;
        
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

        public KaasAprioriTask(int start, int end,int submitLoopMax,int frequencyLowerLimit) {
        	this.start = start;
        	this.end = end;
        	submitLoopCur = 0;
        	this.submitLoopMax = submitLoopMax;
        	this.frequencyLowerLimit = frequencyLowerLimit;
		}
        
		public void run() {
			if(ofThreadEndNum==ofThreadNum){
				runAndRules();
				oneRThreadEnd();
				if(rThreadEndNum==rThreadNum){
					rdao.submit();
	            	long consumingTimeOf = System.nanoTime();
	            	runAllTime += consumingTimeOf - runTimeRecord0;
	            	logger.warn("generate all Rules End time:"+ consumingTimeOf +" seconds!");
	                logger.warn("generate all Rules:"+(consumingTimeOf- runTimeRecord0)+" seconds!");
	            }
				return;
			}
            String[] basePathes = dataPath.split(";");
            String realBase = null;
            boolean smbAddr=false;
            for(String base:basePathes){
                File tmp=new File(base);
                if(tmp.isDirectory()){
                    realBase=base;
                    smbAddr=false;
                    break;
                }
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

                DataInputStream in = null;
                try {
                    in = new DataInputStream(new BufferedInputStream(
                            new FileInputStream(realBase + File.separator
                                    + folder + File.separator + fileone)));
                } catch (FileNotFoundException e) {
                    logger.warn("local offline file may be moved or renamed!");
                    continue;
                }
                

                logger.info("Current File Name:" + fileone
                        + " | Training Progress:" + indexFile + "/"
                        + totalFiles);

                String line = null;
                try {
                    while ((line = in.readLine()) != null) {
                        if(Thread.currentThread().isInterrupted()){
                            in.close();
                            logger.warn("offline training threads interrupted");
                            return;
                        }
                //logger.info(line.toString());
                        Set<String> idlist = getProductsInOrderLine(line);
                //logger.info(idlist.toString());
                        addAIdOrder(idlist);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            genCombinationFromMemory();
            oneOfThreadEnd();
            if(ofThreadEndNum==ofThreadNum){
            	ofdao.submit();
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
            String[] ss = line.split(itemDelimiter);
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
            	genCombinationFromMemory();
            }
        }

        public boolean genCombinationFromMemory(){
            List<KaasOrderFrequent> olist=new ArrayList<KaasOrderFrequent>();
            List<KaasRule> rlist=new ArrayList<KaasRule>();

            for(Map.Entry<String, Integer> me : submitMap.entrySet()){

        		
                KaasOrderFrequent of = new KaasOrderFrequent();
                of.setCombination(me.getKey());
                of.setFrequent(me.getValue());
                of.setItemNum(me.getKey().split(itemDelimiter).length);
                of.setOfType("all");
            //logger.info("my key:"+me.getKey());
            //logger.info(of.getCombination());
                olist.add(of);
             //logger.info("olist:"+olist.size());
             
            }
        //logger.info(olist.size()+":"+olist.get(0).getCombination()+":"+olist.get(0).getFrequent());

            submitMap.clear();
            submitLoopCur = 0;


            for(KaasOrderFrequent o : olist){
                int rval=0;
                rval = ofdao.submit(o);
                if(rval !=1){
                    //all error case
                    if(rval == 2){
                        //concurrent case
                    	int maxTryCountNum=Integer.valueOf(maxTryCount);
                        while(rval == 2 && maxTryCountNum>0){
                            rval=ofdao.submit(o);
                            maxTryCountNum--;
                        }
                        if(rval==2){
                            logger.warn("exceed maxTryCount of order frequent submit!");
                        }
                    }
                }
            }
            //ofdao.submit();

            logger.info("Loop times:"+submitLoopNum++);
            return true;
        }

        private void runAndRules() {
        	List<KaasRule> rlist=new ArrayList<KaasRule>();
        	submitLoopCur = 0;
			for(int i=start; i<end; i++){
				KaasOrderFrequent of = ofdao.getKeyMarsOrderFrequent(i);
				if(of!=null&&of.getFrequent()>=frequencyLowerLimit){
					if(of.getCombination().matches(".?")){
						continue;
					}
					List<KaasRule> subRlist = genRulesByLine(of.getCombination(),of.getFrequent());
					rlist.addAll(subRlist);
		//logger.info("println Combination:"+of.getCombination()+"-Frequent:"+of.getFrequent());
		//logger.info("frequencyLowerLimit:"+frequencyLowerLimit);
				}
				submitLoopCur++;
				if(submitLoopCur == submitLoopMax){
					genRulesFromMemory(rlist);
					continue;
				}
			}
			genRulesFromMemory(rlist);
		}
        
        private List<KaasRule> genRulesByLine(String line,int baseSupport) {

            Map<String,Integer> rulemap = null;
            //generate all rules
            String[] lineArr=line.split(itemDelimiter);
            CombinationModel cm = new CombinationModel(lineArr);
            rulemap = cm.genRuleCombinations();
            cm = null;
            lineArr=null;
            List<KaasRule> rlist = new ArrayList<KaasRule>();
            if(rulemap != null){
                for (Map.Entry<String, Integer> me: rulemap.entrySet()){
                    String[] tmp = me.getKey().split("\\|");
                    KaasOrderFrequent of = ofdao.findOneByProperty("freqSet", tmp[0]);
                    if(of != null){
                        Double downSup = of.getFrequent()*1.0;
                        Double x = (baseSupport*1.0)/downSup;
                        KaasRule r = new KaasRule();
                        r.setProducts(tmp[0]);
                        r.setRecommendation(tmp[1]);
                        r.setConfidence(x);
                        r.setFlag("general");
                        ////logger.info(tmp[0]+"->"+tmp[1]+":"+x.toString());
                        //rdao.submit(r);
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
        
        public boolean genRulesFromMemory(List<KaasRule> rlist){
        	for(KaasRule r: rlist){
                int rval=0;
                rdao.submit(r);
                if(rval !=1){
                    //all error case
                    if(rval == 2){
                        //concurrent case
                        int maxTryCountNum=Integer.valueOf(maxTryCount);
                        while(rval == 2 && maxTryCountNum>0){
                            rval=rdao.submit(r);
                            maxTryCountNum--;
                        }
                        if(rval==2){
                            logger.warn("exceed maxTryCount of rule submit!");
                        }
                    }
                }
            }
            //rdao.submit();
            submitLoopCur = 0;
            rlist.clear();
        	return true;
        }

    }

	public String getMaxTryCount() {
		return maxTryCount;
	}
	public void setMaxTryCount(String maxTryCount) {
		this.maxTryCount = maxTryCount;
	}
	public String getItemDelimiter() {
		return itemDelimiter;
	}
	public void setItemDelimiter(String itemDelimiter) {
		this.itemDelimiter = itemDelimiter;
	}

	public String getfrequencyLowerLimitStr() {
		return frequencyLowerLimitStr;
	}

	public void setfrequencyLowerLimitStr(String frequencyLowerLimitStr) {
		this.frequencyLowerLimitStr = frequencyLowerLimitStr;
	}

}
