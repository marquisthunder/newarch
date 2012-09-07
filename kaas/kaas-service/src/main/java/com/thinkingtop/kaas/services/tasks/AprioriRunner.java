package com.thinkingtop.kaas.services.tasks;
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

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.FileHistoryDAO;
import com.thinkingtop.kaas.services.dao.MarsOrderFrequentDAO;
import com.thinkingtop.kaas.services.dao.MarsRuleDAO;
import com.thinkingtop.kaas.services.model.MarsOrderFrequent;
import com.thinkingtop.kaas.services.model.MarsRule;
import com.thinkingtop.kaas.services.tools.CombinationModel;

public class AprioriRunner {

    static Logger logger=Logger.getLogger(AprioriRunner.class);
    private ThreadPoolTaskExecutor taskExecutor;
    private FileHistoryDAO fileHistoryDAO;
    private MarsOrderFrequentDAO ofdao;
    private MarsRuleDAO rdao;
    private String threadNum;
    private String dataPath;
    private String folder;
    private String waitTime;
    private String submitLoopMaxStr;
    private String freqSetMaxSizeStr;
    private String supportGateStr;
    private int threadEndNum;

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
    public MarsOrderFrequentDAO getOfdao() {
        return ofdao;
    }
    public void setOfdao(MarsOrderFrequentDAO ofdao) {
        this.ofdao = ofdao;
    }
    public MarsRuleDAO getRdao() {
        return rdao;
    }
    public void setRdao(MarsRuleDAO rdao) {
        this.rdao = rdao;
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
    	ofdao.setFileAll(new HashMap<String, MarsOrderFrequent>());
    	rdao.setMarsRuleAll(new HashMap<String, MarsRule>());
    	threadEndNum=0;
        List<String> filelist=fileHistoryDAO.getFileList("Order", 4, 2);
    //System.out.println(filelist.size());
        if(filelist == null || filelist.size() == 0){
            logger.info("No orders are needed to do offline training!");
            return;
        }
        fileHistoryDAO.updateFlag(filelist, "Order", 3);
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
//System.out.println("-------------------------------------------------");
        int time=Integer.parseInt(waitTime);
        try{
        	while(threadEndNum!=threadN){
        		Thread.sleep(100);
        	}
        }catch(Exception e){
            ;
        }
        /*taskExecutor.getThreadPoolExecutor().shutdownNow();*/
        logger.info("Offline Training Task Finished Once!");

        return;

    }

    private class MarsAprioriTask implements Runnable {
        private List<String> filelist;
        private Map<String,Integer> submitMap;

        private int submitLoopCur;
        private int submitLoopNum;
        private int submitLoopMax;
        private int freqSetMaxSize;
        private int supportGate;
        
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
        	System.out.println();
            this.filelist = filelist;
            this.submitLoopMax = submitLoopMax;
            submitLoopCur = 0;
            submitLoopNum = 0;
            this.freqSetMaxSize = freqSetMaxSize;
            this.supportGate = supportGate;
            submitMap = new HashMap<String,Integer>();
        }

        public void run() {

            String[] basePathes = dataPath.split(";");
            String realBase = null;
            boolean smbAddr=false;
            for(String base:basePathes){
                if(base.matches("^smb://.*")){
                    try {
                        SmbFile smbFile=new SmbFile(base);
                        if(smbFile.isDirectory()){
                            realBase=base;
                            smbAddr=true;
                            break;
                        }
                    } catch (MalformedURLException e) {
                        logger.warn("wrong format of samba address of offline!");
                        continue;
                    } catch (SmbException e) {
                        logger.warn("wrong samba folder of offline!");
                        continue;
                    }

                }else{
                    File tmp=new File(base);
                    if(tmp.isDirectory()){
                        realBase=base;
                        smbAddr=false;
                        break;
                    }
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
                if (smbAddr) {
                    try {
                        in = new DataInputStream(new BufferedInputStream(
                                new SmbFileInputStream(realBase
                                        + "/" + folder + "/"
                                        + fileone)));
                    } catch (SmbException e) {
                        logger.warn("error in fetch remote offline files");
                        continue;
                    } catch (MalformedURLException e) {
                        logger
                                .warn("wrond format address of remote offline files");
                        continue;
                    } catch (UnknownHostException e) {
                        logger.warn("Unkown host of remote offline files");
                        continue;
                    }
                } else {
                    try {
                        in = new DataInputStream(new BufferedInputStream(
                                new FileInputStream(realBase + File.separator
                                        + folder + File.separator + fileone)));
                    } catch (FileNotFoundException e) {
                        logger.warn("local offline file may be moved or renamed!");
                        continue;
                    }
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
                            fileHistoryDAO.updateFlag(notFinished, "Order", 2);
                            fileHistoryDAO.updateFlag(finished, "Order", 4);
                            return;
                        }
                        Set<String> idlist = getProductsInOrderLine(line);
                    //System.out.println(idlist.toString());
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
            //notFinished.remove(fileone);
            //finished.add(fileone);
            }
            genRulesFromMemory();
            //fileHistoryDAO.updateFlag(filelist, "Order", 4);
            threadEndNum++;

        }
        
        private Set<String> getProductsInOrderLine(String line){
            if(line == null || line.length()<=0){
                return null;
            }
            String[] ss = line.split(" ");
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
            //sset=null;
            cm=null;
            submitLoopCur++;
        /*System.out.println("---------------------" + submitLoopCur);
        for(Map.Entry<String, Integer> me : submitMap.entrySet()){
        	System.out.println(me.getKey()+" : "+ me.getValue());
        }
        System.out.println("---------------------end");*/
            if(submitLoopCur == submitLoopMax){
        //System.out.println("submitLoopCur:"+submitLoopCur);
                genRulesFromMemory();
            }
        }

        public boolean genRulesFromMemory(){
            List<MarsOrderFrequent> olist=new ArrayList<MarsOrderFrequent>();
            List<MarsRule> rlist=new ArrayList<MarsRule>();

            long startTime1 = System.nanoTime();
            for(Map.Entry<String, Integer> me : submitMap.entrySet()){
            	Map<String,Integer> histery = rdao.getRuleMap(me.getKey());
        //System.out.println(me.getKey()+" : "+ me.getValue());
        //System.out.println(histery.size());
        		if(histery.size()>0){
        			List<MarsRule> subRlist = genMapRulesByLine(histery);
        			rlist.addAll(subRlist);
        		}
        		
                MarsOrderFrequent of = new MarsOrderFrequent();
                of.setFreqSet(me.getKey());
                of.setSupport(me.getValue());
                of.setLevel(me.getKey().split(",").length);
                of.setOfType("all");
                olist.add(of);
                int newSup=me.getValue();
                
                MarsOrderFrequent tmp = ofdao.findOneByProperty("freqSet", me.getKey());
            //System.out.println(tmp);
            //System.out.println(newSup);
                if(tmp != null){
        //System.out.println(tmp.getSupport());
                    newSup+=tmp.getSupport();
                }
                
                if(newSup >= supportGate){
            //System.out.println(newSup);
                    List<MarsRule> subRlist = genRulesByLine(me.getKey(),newSup);
                    rlist.addAll(subRlist);
                    continue;
                }
            }
        //System.out.println("----------------------");
            long consumingTime0 = System.nanoTime();
            logger.warn("generate all rules:"+(consumingTime0- startTime1)/1000000000+" seconds!");

            submitMap.clear();
            submitLoopCur = 0;


            for(MarsOrderFrequent o : olist){
                int rval=0;
                rval = ofdao.submit(o);
                if(rval !=1){
                    //all error case
                    if(rval == 2){
                        //concurrent case
                        int maxTryCount=10;
                        while(rval == 2 && maxTryCount>0){
                            rval=ofdao.submit(o);
                            maxTryCount--;
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
            for(MarsRule r: rlist){
                int rval=0;
                rdao.submit(r);
                if(rval !=1){
                    //all error case
                    if(rval == 2){
                        //concurrent case
                        int maxTryCount=10;
                        while(rval == 2 && maxTryCount>0){
                            rval=rdao.submit(r);
                            maxTryCount--;
                        }
                        if(rval==2){
                            logger.warn("exceed maxTryCount of rule submit!");
                        }
                    }
                }
            }
            rdao.submit();
            long consumingTime2 = System.nanoTime();
            logger.warn("save all Rule:"+(consumingTime2- consumingTime1)/1000000000+" seconds!");

            logger.info("Loop times:"+submitLoopNum++);
        /*System.out.println("listAll---------------------");
        List<MarsRule> marsRuleList = rdao.getRule("1,3");
        for(MarsRule m : marsRuleList){
        	System.out.println(m.getProducts()+"=>"+m.getRecommendation()+"  : "+m.getConfidence());
        }
        System.out.println("---------------------------");*/
            return true;
        }

        private List<MarsRule> genRulesByLine(String line,int baseSupport) {

            Map<String,Integer> rulemap = null;
            //generate all rules
            String[] lineArr=line.split(",");
            CombinationModel cm = new CombinationModel(lineArr);
            rulemap = cm.genRuleCombinations();
            cm = null;
            lineArr=null;
       //System.out.println(line);
       //rulemap.putAll(histery);
            List<MarsRule> rlist= new ArrayList<MarsRule>();
            if(rulemap != null){
                for (Map.Entry<String, Integer> me: rulemap.entrySet()){
                    String[] tmp = me.getKey().split("\\|");
                    MarsOrderFrequent of = ofdao.findOneByProperty("freqSet", tmp[0]);
                    if(of != null || submitMap.containsKey(tmp[0])){
                        Double downSup = (of == null?0.0:of.getSupport())+submitMap.get(tmp[0]);
                        Double x = (baseSupport*1.0)/downSup;
                        MarsRule r = new MarsRule();
                        r.setProducts(tmp[0]);
                        r.setRecommendation(tmp[1]);
                        r.setConfidence(x);
                        r.setFlag("general");
                        ////logger.info(tmp[0]+"->"+tmp[1]+":"+x.toString());
                        //rdao.submit(r);
                    //System.out.println(tmp[0]+"->"+tmp[1]+":"+x.toString());
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
        
        private List<MarsRule> genMapRulesByLine(Map<String,Integer> hisertry){
        	List<MarsRule> rlist= new ArrayList<MarsRule>();
        	for (Map.Entry<String, Integer> me: hisertry.entrySet()){
                String[] tmp = me.getKey().split("\\|");
                if(submitMap.containsKey(tmp[0]+","+tmp[1])){
                	continue;
                }
                MarsOrderFrequent hi = ofdao.findOneByProperty("freqSet", tmp[0]+","+tmp[1]);
                if(hi==null){
                	continue;
                }
                MarsOrderFrequent of = ofdao.findOneByProperty("freqSet", tmp[0]);
                if(of != null || submitMap.containsKey(tmp[0])){
                    Double downSup = (of == null?0.0:of.getSupport())+submitMap.get(tmp[0]);
                    Double x = (hi.getSupport()*1.0)/downSup;
                    MarsRule r = new MarsRule();
                    r.setProducts(tmp[0]);
                    r.setRecommendation(tmp[1]);
                    r.setConfidence(x);
                    r.setFlag("general");
                    ////logger.info(tmp[0]+"->"+tmp[1]+":"+x.toString());
                    //rdao.submit(r);
                //System.out.println(tmp[0]+"->"+tmp[1]+":"+x.toString());
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

}
