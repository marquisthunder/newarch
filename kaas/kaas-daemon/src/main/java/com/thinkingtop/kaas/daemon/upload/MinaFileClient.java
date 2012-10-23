package com.thinkingtop.kaas.daemon.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


/**
 * @author kaas
 */
public class MinaFileClient extends StreamIoHandler{
	IoSession session;
	public void setSession(IoSession session) {
		this.session = session;
	}
	public IoSession getSession() {
		return session;  
	}
	@Override
	protected void processStreamIo(IoSession session, InputStream in,
			OutputStream out) {
			File sendFile = new File("F:\\1.txt");
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(sendFile);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			new IoStreamThreadWork(fis,out).start();
			return;
	}
	
	public void createClienStream(){
		int port = 8888;
		String local = "127.0.0.1";
		
		NioSocketConnector connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();
		factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
		factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
		chain.addLast("logging", new LoggingFilter());
		connector.setHandler(new MinaFileClient());
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(local,port));
		connectFuture.awaitUninterruptibly();
//		@SuppressWarnings("unused")
//		IoSession session = connectFuture.getSession();
//		setSession(session);
	}
	public static void main(String[] args) {
		MinaFileClient client = new MinaFileClient();
		client.createClienStream();
	}
}