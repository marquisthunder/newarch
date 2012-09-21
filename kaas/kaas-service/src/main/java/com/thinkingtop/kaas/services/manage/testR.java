package com.thinkingtop.kaas.services.manage;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.AprioriRunner;

@Component("testR")
public class testR {
    static Logger logger=Logger.getLogger(AprioriRunner.class);
	private ThreadPoolTaskExecutor taskExecutor;
	private ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<String, Integer>();
	
    public void runIt(){
    	//chm.put("test", 1);
    	logger.info("test.value:"+chm.get("test"));
    	taskExecutor.execute(new AprioriTask());
    	taskExecutor.execute(new AprioriTask2());
    	try{
        		Thread.sleep(1000);
        }catch(Exception e){
            ;
        }
    	logger.info("test.value:"+chm.get("test"));
    }
	
    public void add(String name, Integer value, int run){
    		if(chm.containsKey(name)){
	    		chm.put(name, chm.get(name)+value);
	    		logger.info("run "+run+" , "+name+".value: - "+chm.get(name));
    		
			}else{
						chm.put(name, value);
					logger.info("run "+run+" , "+name+".value: - "+chm.get(name));
			}
    }
    
    private class AprioriTask implements Runnable {

		public void run() {
			for(int i=0;i<100;i++)
			add("test", 1, 1);
			logger.info("run 1");
		}
	}
    private class AprioriTask2 implements Runnable {
			
    	public void run() {
    		for(int i=0;i<100;i++)
    		add("test", 1, 2);
			logger.info("run 2");
			
		}
	}
	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	@Resource(name="threadPoolTaskExecutor")
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
}
