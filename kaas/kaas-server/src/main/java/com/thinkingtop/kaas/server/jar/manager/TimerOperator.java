package com.thinkingtop.kaas.server.jar.manager;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.thinkingtop.kaas.server.jar.maintenance.Maintenance;

public class TimerOperator {   
    private static TimerOperator to=null;
	private TimerOperator (){}
	public static TimerOperator getInstance() {
		if(to==null) {
			to = new TimerOperator();
			return to;
		}
		return to;
	}
	
    public static void main(String[] args) { 
        TimerOperator tTask=new TimerOperator();
    }
    static int i = 1;
    static Timer timer = new Timer();
    public void execute(Maintenance m){
    	
    	timer.cancel();
    	timer = new Timer();
        timer.schedule(new KaasTimerTask(m), new Date(m.getFirstJarInfo().getExpiredDate().getTime()+5000));
        //timer.schedule(new KaasTimerTask(), 2000);
       
    }
    
    public void cancel() {
    	timer.cancel();
    }
    private class KaasTimerTask extends TimerTask {
    	private Maintenance m;
    	public KaasTimerTask(Maintenance m) {
    		this.m=m;
    	}
        public void run() {
            System.out.println("it is up "+(i++));
           	System.out.println("file:"+m.getFirstJarInfo().getJarName());
            
           	m.popJarInfo();
            if(m.getFirstJarInfo()!=null) {
            	
            	timer.schedule(new KaasTimerTask(m), m.getFirstJarInfo().getExpiredDate());
            }
        }
    }
    private class KaasTimerTask2 extends TimerTask {
    	
        public void run() {
            System.out.println("it is up2！"+(i++));
        }
    }
}
