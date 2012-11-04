package com.thinkingtop.kaas.server.upload;

public class SocketServerRunnableWrapper implements Runnable{

	@Override
	public void run() {
		try {
			new KaasSocketServer().setListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
