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
    private String freqSetMaxSizeStr;
    private String supportGateStr;
    private String goodsDelimiter;
    private String maxTryCount;
    private int ofThreadEndNum;
    private int rThreadEndNum;

    public String getSupportGateStr() {
        return supportGateStr;
    }
    public void setSupportGateStr(String supportGateStr) {
        this.supportGateStr = supportGateStr;
    }
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
    public String getFreqSetMaxSizeStr() {
        return freqSetMaxSizeStr;
    }
    public void setFreqSetMaxSizeStr(String freqSetMaxSizeStr) {
        this.freqSetMaxSizeStr = freqSetMaxSizeStr;
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
    
    public String getGoods(String basisGoods, int basisSize){
    	return rdao.getRuleMap(basisGoods,basisSize);
    }

    public void println(){
    	System.out.println("------------------------------------println properties ");
    	System.out.println("fileHistoryDAO:  "+fileHistoryDAO.getClass());
    	System.out.println("ofdao:  "+ofdao.getClass());
    	System.out.println("rdao:  "+rdao.getClass());
    	System.out.println("threadNum:  "+threadNum);
    	System.out.println("dataPath:  "+dataPath);
    	System.out.println("folder:  "+folder);
    	System.out.println("waitTime:  "+waitTime);
    	System.out.println("submitLoopMaxStr:  "+submitLoopMaxStr);
    	System.out.println("freqSetMaxSizeStr:  "+freqSetMaxSizeStr);
    	System.out.println("supportGateStr:  "+supportGateStr);
    	System.out.println("------------------------------------println properties end");
    }
    
    
    public void runIt(){
    //println();
    	ofdao.setFileAll(new HashMap<String, KaasOrderFrequent>());
    	rdao.setMarsRuleAll(new HashMap<String, KaasRule>());
    	ofThreadEndNum=0;
        List<String> filelist=fileHistoryDAO.getFileList();
        if(filelist == null || filelist.size() == 0){
            logger.info("No orders are needed to do offline training!");
            return;
        }
        int threadN=Integer.parseInt(threadNum);
        int loop;
        int taskN;
        if(threadN<filelist.size()){
            loop=filelist.size()/threadN;
            taskN=filelist.size()%threadN==0?threadN:threadN+1;
        }else{
            loop=1;
            taskN=filelist.size();
        }
        logger.info("Do offline training With "+taskN+" Threads!");
        if(taskExecutor.getThreadPoolExecutor().isShutdown()){
            taskExecutor.initialize();
        }
        int submitLoopMax=Integer.parseInt(submitLoopMaxStr);
        int freqSetMaxSize=Integer.parseInt(freqSetMaxSizeStr);
        int  supportGate=Integer.parseInt(supportGateStr);
        for(int i=0;i<taskN;i++){
            List<String> partOfFiles=new ArrayList<String>();
            int start=i*loop;
            int end=(i+1)*loop<filelist.size()?(i+1)*loop:filelist.size();

            for(int j=start;j<end;j++){
                partOfFiles.add(filelist.get(j));
            }

            MarsAprioriTask marsAprioriTask = new MarsAprioriTask(partOfFiles,submitLoopMax,freqSetMaxSize,supportGate);
            taskExecutor.execute(marsAprioriTask);
        }
        int time=Integer.parseInt(waitTime);
        try{
        	while(ofThreadEndNum!=threadN){
        		Thread.sleep(time);
        	}
        	if(ofThreadEndNum==threadN){
        		runR();
        	}
        }catch(Exception e){
            ;
        }
        logger.info("Offline Training Task Finished Once!");

        return;

    }
    
    private void runR() {
    	int ofsize = ofdao.size();
    	if(ofsize<=0){
    		return;
    	}
    	int threadN=Integer.parseInt(threadNum);
        int loop;
        int taskN;
        if(threadN<ofsize){
            loop=ofsize/threadN;
            taskN=(int) (ofsize%threadN);
        }else{
            loop=1;
            taskN = 0;
            threadN=(int) ofsize;
        }
        
        int submitLoopMax=Integer.parseInt(submitLoopMaxStr);
        int freqSetMaxSize=Integer.parseInt(freqSetMaxSizeStr);
        int  supportGate=Integer.parseInt(supportGateStr);
        for(int i=0;i<threadN;i++){
            int start;
            int end;
            if(taskN>i){
	            start=i*(loop+1);
	            end=(i+1)*(loop+1)<ofsize?(i+1)*(loop+1):ofsize;
            }else{
            	start=taskN*(loop+1)+(i-taskN)*loop;
            	end = (taskN*(loop+1)+(i-taskN+1)*loop)<ofsize?(taskN*(loop+1)+(i-taskN+1)*loop):ofsize;
            }
            
            MarsAprioriTask marsAprioriTask = new MarsAprioriTask(start,end);
            taskExecutor.execute(marsAprioriTask);
        }
        int time=Integer.parseInt(waitTime);
        try{
        	while(rThreadEndNum!=threadN){
        		Thread.sleep(time);
        	}
        }catch(Exception e){
            ;
        }
        logger.info("Offline Training Task Finished Once!");
//System.out.println(threadNum);
	}

    private class MarsAprioriTask implements Runnable {
        private List<String> filelist;
        private Map<String,Integer> submitMap;

        private int submitLoopCur;
        private int submitLoopNum;
        private int submitLoopMax;
        private int freqSetMaxSize;
        private int supportGate;
        private int start;
        private int end;
        
        public void printlnM(){
        	System.out.println(filelist.size());
        	System.out.println(submitMap.size());
        	System.out.println(submitLoopCur);
        	System.out.println(submitLoopNum);
        	System.out.println(submitLoopMax);
        	System.out.println(freqSetMaxSize);
        	System.out.println(supportGate);
        }
        public MarsAprioriTask(List<String> filelist,int submitLoopMax,int freqSetMaxSize,int supportGate) {
            this.filelist = filelist;
            this.submitLoopMax = submitLoopMax;
            submitLoopCur = 0;
            submitLoopNum = 0;
            this.freqSetMaxSize = freqSetMaxSize;
            this.supportGate = supportGate;
            submitMap = new HashMap<String,Integer>();
        }

        public MarsAprioriTask(int start, int end) {
        	this.start = start;
        	this.end = end;
		}
        
		public void run() {
			if(ofThreadEndNum==Integer.valueOf(threadNum)){
				runAndRules();
				oneRThreadEnd();
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
                        Set<String> idlist = getProductsInOrderLine(line);
                        executeLine(idlist);
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
            genRulesFromMemory();
            oneOfThreadEnd();

        }
        
        private void runAndRules() {
			
		}
		private Set<String> getProductsInOrderLine(String line){
            if(line == null || line.length()<=0){
                return null;
            }
            String[] ss = line.split(goodsDelimiter);
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
        private void executeLine(Set<String> idlist){
            if(idlist == null || idlist.size() == 0){
                return;
            }

            CombinationModel cm = new CombinationModel(idlist.toArray(new String[0]),submitMap);
            cm.genCombinations(freqSetMaxSize);
            cm=null;
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
        		
                KaasOrderFrequent of = new KaasOrderFrequent();
                of.setCombination(me.getKey());
                of.setFrequent(me.getValue());
                of.setItemNum(me.getKey().split(",").length);
                of.setOfType("all");
                olist.add(of);
            }
            long consumingTime0 = System.nanoTime();
            logger.warn("generate all rules:"+(consumingTime0- startTime1)/1000000000+" seconds!");

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
            ofdao.submit();
            long consumingTime1 = System.nanoTime();
            logger.warn("save all OF:"+(consumingTime1- consumingTime0)/1000000000+" seconds!");

            logger.info("Loop times:"+submitLoopNum++);
            return true;
        }

        private List<KaasRule> genRulesByLine(String line,int baseSupport) {

            Map<String,Integer> rulemap = null;
            //generate all rules
            String[] lineArr=line.split(goodsDelimiter);
            CombinationModel cm = new CombinationModel(lineArr);
            rulemap = cm.genRuleCombinations();
            cm = null;
            lineArr=null;
            List<KaasRule> rlist= new ArrayList<KaasRule>();
            if(rulemap != null){
                for (Map.Entry<String, Integer> me: rulemap.entrySet()){
                    String[] tmp = me.getKey().split("\\|");
                    KaasOrderFrequent of = ofdao.findOneByProperty("freqSet", tmp[0]);
                    if(of != null || submitMap.containsKey(tmp[0])){
                        Double downSup = (of == null?0.0:of.getFrequent())+submitMap.get(tmp[0]);
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
        
        private List<KaasRule> genMapRulesByLine(Map<String,Integer> history){
        	List<KaasRule> rlist= new ArrayList<KaasRule>();
        	for (Map.Entry<String, Integer> me: history.entrySet()){
                String[] tmp = me.getKey().split("\\|");
                if(submitMap.containsKey(tmp[0]+goodsDelimiter+tmp[1])){
                	continue;
                }
                KaasOrderFrequent hi = ofdao.findOneByProperty("freqSet", tmp[0]+goodsDelimiter+tmp[1]);
                if(hi==null){
                	continue;
                }
                KaasOrderFrequent of = ofdao.findOneByProperty("freqSet", tmp[0]);
                if(of != null || submitMap.containsKey(tmp[0])){
                    Double downSup = (of == null?0.0:of.getFrequent())+submitMap.get(tmp[0]);
                    Double x = (hi.getFrequent()*1.0)/downSup;
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
        	return rlist;
        }

    }

	public String getGoodsDelimiter() {
		return goodsDelimiter;
	}
	public void setGoodsDelimiter(String goodsDelimiter) {
		this.goodsDelimiter = goodsDelimiter;
	}
	public String getMaxTryCount() {
		return maxTryCount;
	}
	public void setMaxTryCount(String maxTryCount) {
		this.maxTryCount = maxTryCount;
	}

}
