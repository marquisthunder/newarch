package com.thinkingtop.kaas.server.upload;

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
