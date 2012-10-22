package com.thinkingtop.kaas.server.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * @author kaas
 *  file transform server
 * 
 */
public class MinaFileServer extends StreamIoHandler{
	public static final int PORT = 8888;
	@Override
	public void sessionOpened(IoSession session) {
		System.out.println("client connected:"+session.getRemoteAddress());
		super.sessionOpened(session);
	}

	protected void processStreamIo(IoSession session, InputStream in,OutputStream out) {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 6, 3,TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(3),
				new ThreadPoolExecutor.DiscardOldestPolicy());
		FileOutputStream fos = null;
		File receiveFile = new File("f://22.txt");
		try {
			fos = new FileOutputStream(receiveFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		threadPool.execute(new IoStreamThreadWork(in,fos));
//		new IoStreamThreadWork(in,fos).start();
	}
	
	public void createServerStream(){
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		
		ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();
		factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);// 
		factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
		chain.addLast("logging", new LoggingFilter());//
		acceptor.setHandler(new MinaFileServer());
		InetSocketAddress inetSocketAddress = null;
		try {
			inetSocketAddress = new InetSocketAddress(8888);
			acceptor.bind(inetSocketAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("server has been started"+8888);
	}
	public static void main(String[] args) {
		MinaFileServer server = new MinaFileServer();
		server.createServerStream();
	}
}