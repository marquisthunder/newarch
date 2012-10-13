package com.thinkingtop.kaas.server.jar.monitor;

public class JWatchRunnableWrapper implements Runnable { 
 
	//private static final Logger logger = LoggerFactory.getLogger(JWatchRunnableWrapper.class.getName());
 
    public void run() { 
    	new JWatch().startMonitor();
    } 
} 
