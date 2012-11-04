package com.thinkingtop.kaas.daemon.cli;

import java.net.URLClassLoader;
import java.util.Scanner;

import org.hardcode.juf.Installer;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String commandString = null;
		do {
			System.out.println("请输入命令："); 
			commandString = sc.nextLine();
			int index = commandString.indexOf(" ");
			String className = commandString.substring(0,index);
			String a[] = commandString.substring(index+1).split(" ");
			System.out.println(className+"\\");
			//System.out.println(a+"\\");
			System.out.println(getMethodName(className));
			
			String name = getMethodName(className)+"Ops";
			URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			
			
			Class c;
			try {
				c = loader.loadClass("com.thinkingtop.kaas.daemon.cli."+name);
				Operator o = (Operator) c.newInstance();
				o.doOps(a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //System.out.println(c.getName()+"------------------------");
          
			
		} while(true); 
	}

	private static String getMethodName(String fildeName) {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}
