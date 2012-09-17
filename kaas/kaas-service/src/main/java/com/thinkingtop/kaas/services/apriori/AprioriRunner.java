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
import com.thinkingtop.kaas.services.util.KaasDataPath;

/**
 * Generation rule class
 * @author roadahead
 *
 */
public class AprioriRunner {

    static Logger logger=Logger.getLogger(AprioriRunner.class);
    private ThreadPoolTaskExecutor taskExecutor;
    private FileHistoryDAO fileHistoryDAO;
    private KaasOrderFrequentDAO ofdao;
    private KaasRuleDAO rdao;
    private String threadNum;
    private KaasDataPath kaasDataPath;
    private String folder;
    private String waitTime;
    private String submitLoopMaxStr;
    private String combinationMaxSizeStr;
    private String frequencyLowerLimitStr;
    private String itemDelimiter;
    private String maxTryCount;
    private int actualThreadNum;
    private int threadEndNum;
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
    
    private synchronized void oneThreadEnd(){
    	threadEndNum++;
    }
    
    public String getRecommend(String inputItems, int outputItemsNum){
    	return rdao.getRuleMap(inputItems,outputItemsNum);
    }

    public void println(){
    	logger.info("------------------------------------println properties ");
    	logger.info("fileHistoryDAO:  "+fileHistoryDAO.getClass());
    	logger.info("ofdao:  "+ofdao.getClass());
    	logger.info("rdao:  "+rdao.getClass());
    	logger.info("threadNum:  "+threadNum);
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
    	threadEndNum=0;
        List<String> filelist=fileHistoryDAO.getFileList();
        if(filelist == null || filelist.size() == 0){
            logger.info("No orders are needed to do offline training!");
            return;
        }
        actualThreadNum=Integer.parseInt(threadNum);
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
        if(taskExecutor.getThreadPoolExecutor().isShutdown()){
        	logger.info("this thread isShutdown");
            taskExecutor.initialize();
        }
        int submitLoopMax=Integer.parseInt(submitLoopMaxStr);
        int combinationMaxSize=Integer.parseInt(combinationMaxSizeStr);
        int  frequencyLowerLimit=Integer.parseInt(frequencyLowerLimitStr);
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
            taskExecutor.execute(kaasAprioriTask);
        }
        int time=Integer.parseInt(waitTime);
        try{
        	while(threadEndNum!=actualThreadNum){
        		Thread.sleep(time);
        	}
        }catch(Exception e){
            ;
        }
        
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
            String[] basePathes = kaasDataPath.getItemDataPath().split(";");
         logger.info("dataPathes:"+basePathes[0]);
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
                            new FileInputStream(kaasDataPath.getItemDataPath() + File.separator
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
                //logger.info(line);
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
            genRulesFromMemory();
            oneThreadEnd();
            if(threadEndNum==actualThreadNum){
            	ofdao.submit();
            	rdao.submit();
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
                genRulesFromMemory();
            }
        }

        public boolean genRulesFromMemory(){
            List<KaasOrderFrequent> olist=new ArrayList<KaasOrderFrequent>();
            List<KaasRule> rlist=new ArrayList<KaasRule>();

            long startTime1 = System.nanoTime();
            for(Map.Entry<String, Integer> me : submitMap.entrySet()){
            	
            	Map<String,Integer> histery = rdao.getRuleMap(me.getKey());
        		if(histery.size()>0){
        			List<KaasRule> subRlist = genMapRulesByLine(histery);
        			rlist.addAll(subRlist);
        		}
        		
                KaasOrderFrequent of = new KaasOrderFrequent();
                of.setCombination(me.getKey());
                of.setFrequent(me.getValue());
                of.setItemNum(me.getKey().split(itemDelimiter).length);
                of.setOfType("all");
            //logger.info("my key:"+me.getKey());
            //logger.info(of.getCombination());
                olist.add(of);
             //logger.info("olist:"+olist.size());
                int newSup=me.getValue();
                
                KaasOrderFrequent tmp = ofdao.findOneByProperty("freqSet", me.getKey());
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

            logger.info("Loop times:"+submitLoopNum++);
            return true;
        }

        private List<KaasRule> genRulesByLine(String line,int baseSupport) {

            Map<String,Integer> rulemap = null;
            //generate all rules
            String[] lineArr=line.split(itemDelimiter);
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
                if(submitMap.containsKey(tmp[0]+itemDelimiter+tmp[1])){
                	continue;
                }
                KaasOrderFrequent hi = ofdao.findOneByProperty("freqSet", tmp[0]+itemDelimiter+tmp[1]);
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

	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}

	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}



}
