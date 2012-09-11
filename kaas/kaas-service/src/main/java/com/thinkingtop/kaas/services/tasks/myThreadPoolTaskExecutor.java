package com.thinkingtop.kaas.services.tasks;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

import com.thinkingtop.kaas.services.dao.FileHistoryDAO;
import com.thinkingtop.kaas.services.dao.MarsOrderFrequentDAO;
import com.thinkingtop.kaas.services.dao.MarsRuleDAO;
import com.thinkingtop.kaas.services.dao.implfile.FileHistoryDAOFileImpl;
import com.thinkingtop.kaas.services.dao.implfile.MarsOrderFrequentDAOFileImpl;
import com.thinkingtop.kaas.services.dao.implfile.MarsRuleDAOFileImpl;
import com.thinkingtop.kaas.services.model.MarsOrderFrequent;
import com.thinkingtop.kaas.services.model.MarsRule;
import com.thinkingtop.kaas.services.tools.CombinationModel;


//@Component("myThreadPoolTaskExecutor")
public class myThreadPoolTaskExecutor {
    static Logger logger=Logger.getLogger(MarsAprioriJobRunner.class);
    private ThreadPoolTaskExecutor taskExecutor;
    private FileHistoryDAO fileHistoryDAO;
    private MarsOrderFrequentDAO ofdao;
    private MarsRuleDAO rdao;
    private String threadNum = "1";
    private String dataPath ="/home/roadahead/myTest/testsmb";//目录
    private String folder = "smbChildren"; //主目录下的分目录
    private String waitTime;
    private String submitLoopMaxStr;
    private String freqSetMaxSizeStr;
    private String supportGateStr;
    private MarsAprioriTask marsAprioriTask;
    private int inof;
    public void runIt(){
    	inof = 0;
    	fileHistoryDAO = new FileHistoryDAOFileImpl();
    	ofdao = new MarsOrderFrequentDAOFileImpl();
    	rdao =  new MarsRuleDAOFileImpl();
    	List<String> partOfFiles=new ArrayList<String>(); //文件列表
    	int start=1;
    	int end=2;
    	int submitLoopMax=20;
        int freqSetMaxSize=3;
        int supportGate=12;
    	for(int j=start;j<end;j++){
            partOfFiles.add("smb"+j);
        }
    	marsAprioriTask = new MarsAprioriTask(partOfFiles,submitLoopMax,freqSetMaxSize,supportGate);
    	taskExecutor.execute(marsAprioriTask);
        try{
            Thread.sleep(10*60*1000);
        }catch(Exception e){
            ;
        }
        taskExecutor.getThreadPoolExecutor().shutdownNow();
        logger.info("Offline Training Task Finished Once!");

        return;
    }
    
    public void runit2(){
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
            List<String> partOfFiles=new ArrayList<String>();
            int start;
            int end;
            if(taskN>i){
	            start=i*(loop+1);
	            end=(i+1)*(loop+1)<ofsize?(i+1)*(loop+1):ofsize;
            }else{
            	start=taskN*(loop+1)+(i-taskN)*loop;
            	end = (taskN*(loop+1)+(i-taskN+1)*loop)<ofsize?(taskN*(loop+1)+(i-taskN+1)*loop):ofsize;
            }

            taskExecutor.execute(marsAprioriTask);
        }
    	System.out.println(threadNum);
    }
    
    
    private class MarsAprioriTask implements Runnable {
    	private List<String> filelist;
        private Map<String,Integer> submitMap;
        private List<MarsOrderFrequent> MarsOrderFrequents;

        private int submitLoopCur;
        private int submitLoopNum;
        private int submitLoopMax;
        private int freqSetMaxSize;
        private int supportGate;
        
        public MarsAprioriTask(){}
        public MarsAprioriTask(List<String> filelist,int submitLoopMax,int freqSetMaxSize,int supportGate) {
            this.filelist = filelist;
            this.submitLoopMax = submitLoopMax;
            submitLoopCur = 0;
            submitLoopNum = 0;
            this.freqSetMaxSize = freqSetMaxSize;
            this.supportGate = supportGate;
            submitMap = new HashMap<String,Integer>();
        }
        
        public void run2(){
        	int start = 0;
        	int end = 0;
        	for(int i=start,j=0;i<end;i++){
        		MarsOrderFrequent kmof = ofdao.getKeyMarsOrderFrequent(i);
        		j++;
        		if(j==supportGate){
        			
        		}
        	}
        }
        
		public void run() {
			if(inof==Integer.valueOf(threadNum)){
				run2();
			}
			System.out.println("线程启动了！");

            String[] basePathes = dataPath.split(";");
            String realBase = null;
            boolean smbAddr=false;
            for(String base:basePathes){
            	System.out.println(base);
                    File tmp=new File(base);
           System.out.println(tmp.getClass());
                    if(tmp.isDirectory()){
                        realBase=base;
                        smbAddr=false;
           System.out.println(tmp.getPath());
                        break;
                    }
                }
            
            if (realBase == null) {
                logger.info("No valide order folders");
                return;
            }
            
            System.out.println("realBase！=null");
            
            List<String> notFinished = new ArrayList<String>(filelist);
            List<String> finished = new ArrayList<String>();
            int totalFiles = filelist.size();
            int indexFile = 0;
            
            for (String fileone : filelist) {
                indexFile++;
                //BufferedReader in = null;
                DataInputStream in = null;
                try {
                    in = new DataInputStream(new BufferedInputStream(
                            new FileInputStream(realBase + File.separator
                                    + folder + File.separator + fileone)));
                	/*in = new BufferedReader(new InputStreamReader(
                    		new BufferedInputStream(
                                new FileInputStream(realBase + File.separator
                                        + folder + File.separator + fileone))));*/
                } catch (FileNotFoundException e) {
                    logger.warn("local offline file may be moved or renamed!");
                    continue;
                }
                
                
                logger.info("Current File Name:" + fileone
                        + " | Training Progress:" + indexFile + "/"
                        + totalFiles);

                //System.out.println(in);
                String line = null;
                
                try {
                	int i = 1;
                    while ((line = in.readLine()) != null) {
                        if(Thread.currentThread().isInterrupted()){
                            in.close();
                            System.out.println("线程死了");
                            logger.warn("offline training threads interrupted");
                            //fileHistoryDAO.updateFlag(notFinished, "Order", 2);
                            //fileHistoryDAO.updateFlag(finished, "Order", 4);
                            return;
                        }
                        //System.out.println(i++);

                        System.out.println("smb0 = "+line);
                        System.out.println("第"+ (i++) +"行");
                        //System.out.println("\u00fd");
                        Set<String> idlist = getProductsInOrderLine(line);
                        System.out.println(idlist.size());
                        //System.out.println(idlist.toArray(new String[0]).length);
                        /*for(String m : idlist.toArray(new String[0])){
                        	System.out.println(m);
                        }*/
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
                notFinished.remove(fileone);
                finished.add(fileone);
                
            }
            
            //System.out.println(finished.toString());
            //System.out.println(finished.size());
            genRulesFromMemory();
            //fileHistoryDAO.updateFlag(filelist, "Order", 4);
            inof++;
     		if(inof==Integer.valueOf(threadNum)){
     			runit2();
     		}
            
		}
		
		
		private Set<String> getProductsInOrderLine(String line){
	   //System.out.println("11111111111111111");
            if(line == null || line.length()<=0){
                return null;
            }
            //System.out.println(line);
            String[] ss = line.split(" ");//"\u00fd"
        /*System.out.println("ss.length:"+ss.length);
            if(ss.length <= 10 || ss[0].equals("SEQ")){
                return null;
            }
            int idpos=12;*/int idpos = 0;
            Set<String> idlist=new TreeSet<String>();
       //System.out.println(ss[39]);
            while (idpos < ss.length){
       //System.out.println(idpos);
                String tmp = new String(ss[idpos]); //hack for reducing memory from big line
                if(tmp.equals("")){
                    //logger.error(line.replaceAll("\u00fd", " | "));
                    //idpos+=4;
                	System.out.println("youkong");
                    continue;
                }
                idlist.add(tmp);
       //System.out.println(tmp);
                //idpos +=4;
         		idpos++;
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
            if(submitLoopCur == submitLoopMax){//
        System.out.println(submitMap.size());
                genRulesFromMemory();
            }
        }
        
        
        public boolean genRulesFromMemoryR(){
        	List<MarsRule> rlist=new ArrayList<MarsRule>();
        	return true;
        }
        
		public boolean genRulesFromMemory(){
            List<MarsOrderFrequent> olist=new ArrayList<MarsOrderFrequent>(); //全部
            List<MarsRule> rlist=new ArrayList<MarsRule>(); //value超过supportGate
        System.out.println("------------------------------------------------------");
            long startTime1 = System.nanoTime();
            for(Map.Entry<String, Integer> me : submitMap.entrySet()){
                MarsOrderFrequent of = new MarsOrderFrequent();
                of.setFreqSet(me.getKey());
                of.setSupport(me.getValue());
                of.setLevel(me.getKey().split(",").length);
                of.setOfType("all");
                olist.add(of);
                int newSup=me.getValue();
                if(newSup>= supportGate){
           System.out.println("mekey:"+me.getKey());
           System.out.println("newSup:"+newSup);
                    List<MarsRule> subRlist = genRulesByLine(me.getKey(),newSup);
         //System.out.println(subRlist.size());
        for(MarsRule mr : subRlist){
        	System.out.println(mr.getProducts());
        	System.out.println(mr.getRecommendation());
        	System.out.println(mr.getFlag());
        	System.out.println(mr.getConfidence());
        	System.out.println("------");
        }
                    rlist.addAll(subRlist);
                    continue;
                }

                MarsOrderFrequent tmp = ofdao.findOneByProperty("freqSet", me.getKey());
                if(tmp != null){
                    newSup+=tmp.getSupport();
                    if(newSup >= supportGate){
                        List<MarsRule> subRlist = genRulesByLine(me.getKey(),newSup);
                        rlist.addAll(subRlist);
                        continue;
                    }
                }
            }
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
            
     System.out.println("rlist.size:"+rlist.size());
            /*long consumingTime1 = System.nanoTime();
            logger.warn("save all OF:"+(consumingTime1- consumingTime0)/1000000000+" seconds!");
            for(MarsRule r: rlist){
                int rval=0;
                rval = rdao.submit(r);
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

            logger.info("Loop times:"+submitLoopNum++);*/
            return true;
        }
		
		/**
		 * 
		 * @param line 要给出的组合
		 * @param baseSupport
		 * @return 大于supportGate的组合的MarsRule
		 */
        private List<MarsRule> genRulesByLine(String line,int baseSupport) {

            String[] lineArr=line.split(",");
            Map<String,Integer> rulemap = null;
            //generate all rules
            CombinationModel cm = new CombinationModel(lineArr);
            rulemap = cm.genRuleCombinations();
            cm = null;
        System.out.println(rulemap.size());
            lineArr=null;
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
                        rlist.add(r);
                        tmp=null;
                        x=null;
                        r=null;
                    }else{
                        logger.info("error:"+tmp[1]+" cannot be found!");
                    }

                }
                //logger.info("line rule over");
            }else {
                //logger.info("line: "+line+" is skipped!");
            }
            return rlist;
        }
		public List<MarsOrderFrequent> getMarsOrderFrequents() {
			return MarsOrderFrequents;
		}
		public void setMarsOrderFrequents(List<MarsOrderFrequent> marsOrderFrequents) {
			MarsOrderFrequents = marsOrderFrequents;
		}

    }
	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	//@Resource(name="threadPoolTaskExecutor")
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
