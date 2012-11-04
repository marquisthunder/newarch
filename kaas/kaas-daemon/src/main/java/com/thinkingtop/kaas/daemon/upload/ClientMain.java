package com.thinkingtop.kaas.daemon.upload;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ClientMain {

	private   int ServerPort = 9527;
	private   String ServerAddress = "localhost";
	private   String GetOrPut = "get";   
	private   String local_filename = "../dist/data/out/scheme1.kaas"; 
	private   String remote_filename  = ""; 
	private   byte[] buf;
	private   int len;
	class SocketThread extends Thread{
		
		@Override
		public void run() {
			 try {
				
				File file = new File(local_filename);
				if(!file.exists()&&GetOrPut.equals("put")){ 
					System.out.println("file not existed, cannot upload"); 
					return;
				} 
				
				InetAddress loalhost = InetAddress.getByName("193.168.0.103");
				System.out.println(loalhost);
				
				Socket socket = new Socket(ServerAddress,ServerPort);
											//server ip, server port
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				 
				//dos.writeUTF(GetOrPut+" "+remote_filename);//服务器端如果是io的socket，writeUTF和writeUTF对接
				dos.write((GetOrPut+" "+remote_filename).getBytes());
				dos.flush(); 
			  
				//String tempString = dis.writeUTF(); 
				buf = new byte[4096];
				len = dis.read(buf);
				String tempString = new String(buf,0,len);//feedback from the server
				
				//System.out.println(tempString); 
				if(tempString.equals("notexists")){
					System.out.println("file is not exsited in the server！ cannot download"); 
					dos.close();
					dis.close();
					socket.close();
					return;
				}
				
				if(tempString.startsWith("perpare to download")){  
					DataOutputStream fileOut = 
						new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
 
					while ((len = dis.read(buf))!=-1) {   
		                fileOut.write(buf, 0, len);
		            }
					System.out.println("download finished！");
					fileOut.close();
					dos.close();
					dis.close();
					socket.close();
				}
				else if(tempString.equals("UploadReady")){  
					System.out.println("uploading.......");
					DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(file))); 
					  
	                while ((len = fis.read(buf))!= -1) {  
	                    dos.write(buf, 0, len);
	                }
	                dos.flush();
	                System.out.println("upload finished！");
	                fis.close();
	                dis.close();
	                dos.close();
	                socket.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean checkCommand(String command)
	{ 
		if(!command.startsWith("put")&&!command.startsWith("get")){
			System.out.println("command error");
			return false;
		}
		
		int index = -1;
		String temp = "";
		String[] tempStrings = null;
		
		if((index=command.indexOf("-h"))>0){
			temp = command.substring(index+3);
			temp = temp.substring(0, temp.indexOf(' '));
			ServerAddress = temp;
			System.out.println(ServerAddress);
		}
		if((index=command.indexOf("-p"))>0){
			temp = command.substring(index+3);
			temp = temp.substring(0, temp.indexOf(' '));
			ServerPort = Integer.valueOf(temp);
			System.out.println(ServerPort);
		}
		
		tempStrings = command.split(" ");
		if(command.startsWith("put")){
			GetOrPut = "put";
			local_filename = tempStrings[tempStrings.length-2];
			remote_filename = tempStrings[tempStrings.length-1];
			System.out.println(local_filename);
			System.out.println(remote_filename);
		}
		else if(command.startsWith("get")){
			GetOrPut = "get";
			local_filename = tempStrings[tempStrings.length-1];
			remote_filename = tempStrings[tempStrings.length-2];
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		ClientMain thisC= new ClientMain(); 
		Scanner sc = new Scanner(System.in);
		String commandString = "";
		do {
			System.out.println("input command："); 
			commandString = sc.nextLine();
		} while (!thisC.checkCommand(commandString)); 
		
		ClientMain.SocketThread a = thisC.new SocketThread();
		a.start();
	 
	}
	public void startUpload(String[] args) {
		ClientMain thisC= new ClientMain(); 
		Scanner sc = new Scanner(System.in);
		String commandString = "";
		do {
			System.out.println("input command："); 
			commandString = sc.nextLine();
		} while (!thisC.checkCommand(commandString)); 
		
		ClientMain.SocketThread a = thisC.new SocketThread();
		a.start();
		
	}

}
