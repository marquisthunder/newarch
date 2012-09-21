package com.thinkingtop.kaas.services.algorithm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class createTestFile {
	public static void main(String[] args) {
		int filesSie = 10;
		int datasie = 100;
		Random random = new Random();
		File [] testdataFile = new File[10];
		for(int i=0;i<filesSie;i++){
			String filePath = "/home/roadahead/myTest/testsmb/smbChildren/testData"+i;
			testdataFile[i] = new File(filePath);
			if (!testdataFile[i].exists()) {
				try {
					testdataFile[i].createNewFile();
					System.err.println(testdataFile[i] + "已创建！");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
			FileOutputStream fo = null;
			try {
	            fo = new FileOutputStream(filePath,false);
	            for(int j=0;j<datasie;j++){
	            	boolean one =true;
	            	/*List<String> datalist = new 
	            	String[] data = {String.valueOf(random.nextInt(20)),"1","2","3"};
	            	if(one){
	            		one = false;
	            		song = o.getFreqSet() + " " + o.getSupport() + "==" + o.getLevel() + "==" + o.getOfType();
	            //System.out.println(song);
	            		fo.write(song.getBytes());
	            		fo = new FileOutputStream(filePath,true);
	            	}else{
	            		song = "\r\n"+o.getFreqSet() + "==" + o.getSupport() + "==" + o.getLevel() + "==" + o.getOfType();
	            		fo.write(song.getBytes());
	            	}*/
	            }
			}catch (Exception e) {
			}
			 
		}
	}
}
