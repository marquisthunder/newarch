package com.thinkingtop.kaas.server.upload;

import java.io.IOException;

import net.contentobjects.jnotify.JNotify;

public class SocketServerRunnableWrapper implements Runnable{

	@Override
	public void run() {
		try {
			new NewSocketServer().setListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
