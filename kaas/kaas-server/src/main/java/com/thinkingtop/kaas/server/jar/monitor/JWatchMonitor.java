package com.thinkingtop.kaas.server.jar.monitor;
//choose this method

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class JWatchMonitor implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		new Thread(new JWatch()).start();
	}
    
}
