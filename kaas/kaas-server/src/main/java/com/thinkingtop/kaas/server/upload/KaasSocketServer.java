package com.thinkingtop.kaas.server.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer; 
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkingtop.kaas.server.jar.manager.TimerOperator;
import com.thinkingtop.kaas.services.algorithm.SevenZip.Decompression;

/*
 *  This SocketServer has been tested by transferring the files with different size.
 *  If the file is bigger than 300kb, it will get wrong. 
 */
public class KaasSocketServer {
	private static final Logger logger = LoggerFactory.getLogger(KaasSocketServer.class.getName());

 
	private static final int port = 9527;
	private Selector selector;
	private ByteBuffer clientBuffer = ByteBuffer.allocate(4096);
	private CharsetDecoder decoder = Charset.forName("utf-8").newDecoder();
	private CharsetEncoder encoder = Charset.forName("utf-8").newEncoder();

	public void setListener() throws Exception {

		selector = Selector.open();
		// define a channel -->ServerSocketChannel
		ServerSocketChannel server = ServerSocketChannel.open();
		// ServerSocketChannel bind the port
		server.socket().bind(new InetSocketAddress(port));
		// configure the channel with Non-blocking mode
		server.configureBlocking(false);
		// selector registered on the channel, accept the messages
		server.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			// select() will be blocked，until the message comes from the channel which has been registered with the selector.
			selector.select();
			Iterator iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				SelectionKey key = (SelectionKey) iter.next();
				iter.remove(); // delete the message
				process(key); // process the current thread
			}
		}
	}
	
	private void process(SelectionKey key) throws IOException {
		// receiver the message
		if (key.isAcceptable()) {
			ServerSocketChannel server = (ServerSocketChannel) key.channel();
			// similar to the io socket，ServerSocketChannel's accept method
			// return to SocketChannel
			SocketChannel channel = server.accept();
			// configure the non-block mode
			channel.configureBlocking(false);
			SelectionKey sKey = channel.register(selector, SelectionKey.OP_READ);
			sKey.attach("read_command"); // set id
		}
		// read message
		else if (key.isReadable()) {
			SocketChannel channel = (SocketChannel) key.channel();
			String name = (String) key.attachment();
			if (name.equals("read_command")) {
				int count = channel.read(clientBuffer);
				if (count > 0) {
					clientBuffer.flip();
					CharBuffer charBuffer = decoder.decode(clientBuffer);
					String command = charBuffer.toString();

					// command like：get abc.png or  put aaa.png
					//get the command from client
					System.out.println("command====" + command);

					String[] temp = command.split(" ");
					//put or get?
					command = temp[0];
					//file name
					String filename = temp[1];//file name

					SelectionKey sKey = channel.register(selector,
							SelectionKey.OP_WRITE);
					if (command.equals("put"))
						//keep the file name
						sKey.attach("UploadReady#" + filename);
					else if (command.equals("get")) {
						if (!new File(filename).exists()) {
							System.out.println("file not exsited, cannot download");
							sKey.attach("notexists");
						} else {
							//keep the file name
							sKey.attach("DownloadReady#" + filename);
						}
					}
				} 
				else {
					channel.close();
				}
			} else if (name.startsWith("read_file")) {
				System.out.println(name + "--------------");
				DataOutputStream fileOut = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(new File(
								name.split("#")[1]), true)));

				/*
				 * int nread = 0; while (nread != -1) { try { nread =
				 * channel.read(clientBuffer);
				 * System.out.println(nread+"======"); if(nread!=-1) {
				 * fileOut.write(clientBuffer.array()); }
				 * 
				 * 
				 * } catch (IOException e) { e.printStackTrace(); nread = -1; }
				 * clientBuffer.rewind(); }
				 */

				// 小文件没问题，5m的纯文字txt打包成13k的rar文件，可以传输
				// 单纯的txt 在300kb一下可以传输。
				int passlen = channel.read(clientBuffer);
				while (passlen >= 0) {
					if(passlen!=0) {
						clientBuffer.flip();
					}
					fileOut.write(clientBuffer.array(), 0, passlen);
					passlen = channel.read(clientBuffer);
					logger.debug(passlen + "----------");
					
				}
				System.out.println("upload finished！");
				fileOut.close();
				channel.close();
				// upzip....
				//new Decompression().DecompressionKaas("../dist/data/out/scheme1.kaas","../dist/data/out/scheme");
			}
			clientBuffer.clear();
		} else if (key.isWritable()) { 
			// write ... server transfer the file to client. 
			SocketChannel channel = (SocketChannel) key.channel();
			String flag = (String) key.attachment();
			if (flag.startsWith("downloading")) {
				DataInputStream fis = new DataInputStream(
						new BufferedInputStream(new FileInputStream(new File(
								flag.split("#")[1]))));

				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = fis.read(buf)) != -1) {
					
					channel.write(ByteBuffer.wrap(buf, 0, len));
				}
				fis.close();
				System.out.println("file transfer finished");
				channel.close();
			} else if (flag.equals("not exists")) {
				// channel.write(encoder.encode(CharBuffer.wrap(flag)));
				channel.write(ByteBuffer.wrap(flag.getBytes()));
																	
				channel.close();
			} else if (flag.startsWith("UploadReady")) {
				channel.write(encoder.encode(CharBuffer.wrap("UploadReady")));

				SelectionKey sKey = channel.register(selector,
						SelectionKey.OP_READ);
				sKey.attach("read_file#" + flag.split("#")[1]);
				// key.attach("read_file#"+flag.split("#")[1])
			} else if (flag.startsWith("DownloadReady")) {
				channel.write(ByteBuffer.wrap("prepared to download".getBytes()));
				// channel.write(encoder.encode(CharBuffer.wrap("prepare to download")));
				key.attach("downloading#" + flag.split("#")[1]);
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("waiting for client connecting port" + port);
			new KaasSocketServer().setListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
