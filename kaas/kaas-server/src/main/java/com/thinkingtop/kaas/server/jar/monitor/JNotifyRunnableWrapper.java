package com.thinkingtop.kaas.server.jar.monitor;

import java.io.IOException;

import net.contentobjects.jnotify.JNotify;

public class JNotifyRunnableWrapper implements Runnable{

	@Override
	public void run() {
		try {
			new JNotify().startMonitor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
