package com.thinkingtop.kaas.server.jar.manager;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkingtop.kaas.server.jar.maintenance.Maintenance;
import com.thinkingtop.kaas.server.jar.reader.PropertiesReader;

public class TimerOperator { 
	private static final Logger logger = LoggerFactory.getLogger(TimerOperator.class.getName());

    private static TimerOperator to=null;
	private TimerOperator (){}
	public static TimerOperator getInstance() {
		if(to==null) {
			to = new TimerOperator();
			return to;
		}
		return to;
	}
	
   /* public static void main(String[] args) { 
        TimerOperator tTask=new TimerOperator();
    }*/
   // static int i = 1;
    static Timer timer = new Timer();
    public void execute(Maintenance m){
    	
    	timer.cancel();
    	timer = new Timer();
    	Long l = Long.parseLong(PropertiesReader.getProp("priod"));
        
    	timer.schedule(new KaasTimerTask(m), new Date(m.getFirstJarInfo().getExpired().getTime()+l));
        //timer.schedule(new KaasTimerTask(), 2000);
    }
    
    private class KaasTimerTask extends TimerTask {
    	private Maintenance m;
    	public KaasTimerTask(Maintenance m) {
    		this.m=m;
    	}
        public void run() {
           	//System.out.println("file:"+m.getFirstJarInfo().getJarName());
            logger.info("push file:"+m.getFirstJarInfo().getJarName());
           	m.popJarInfos();
            if(m.getFirstJarInfo()!=null) {
            	timer.schedule(new KaasTimerTask(m), m.getFirstJarInfo().getExpired());
            }
        }
    }
}
