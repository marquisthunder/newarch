package com.thinkingtop.kaas.daemon.client.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpServer;
import org.mortbay.http.SocketListener;
import org.mortbay.http.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkingtop.kaas.daemon.client.KaasDaemonClient;

/**
 * Unit test for simple App.
 */
public class KaasDaemonClientTest {
	private static final Logger logger = LoggerFactory.getLogger(KaasDaemonClientTest.class.getName());

	private static HttpServer server;

	@BeforeClass
	public static void beforeClass() {
		// creata Jetty HttpServer
		server = new HttpServer();
		// set listener on port 8080，receive HTTP request 
		SocketListener listener = new SocketListener();
		listener.setPort(8080);
		server.addListener(listener);

		// create HttpContext，deal with HTTPrequest
		HttpContext context = new HttpContext();
		// setContextPath set root URL
		context.setContextPath("/kaas");
		// setResourceBase set resource base
		context.setResourceBase("../dist");

		context.addHandler(new ResourceHandler());
		server.addContext(context);
		// start jetty
		try {
			server.start();
			logger.info("jetty start");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterClass() {
		try {
			server.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testkaasDaemonClient() {
		KaasDaemonClient jus = new KaasDaemonClient();
		jus.run();
	}

}
