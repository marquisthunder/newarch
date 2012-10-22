package com.thinkingtop.kaas.services.algorithm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.stereotype.Component;
@Component("algorithmProperties")
public class AlgorithmProperties {
	public static Properties algorithmProperties = new Properties();
	public static Properties schemeProperties = new Properties();
	AlgorithmProperties(){
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("algorithm.properties");
			algorithmProperties.load(inputStream);
			inputStream.close();
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheme.properties");
			schemeProperties.load(inputStream);
			inputStream.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setAlgorithmSequence(String algorithmSequence){
		Properties properties = new Properties();
		String alpath = Thread.currentThread().getContextClassLoader().getResource("scheme.properties").toString();
		if(alpath.startsWith("file:")){
			alpath = alpath.substring("file:".length());
		}
		try {
			OutputStream outputStream = new FileOutputStream(alpath);
			properties.setProperty("algorithm.Sequence", algorithmSequence);
            properties.store(outputStream, "author: 954068039@QQ.com");  
            outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSequence(){
		return schemeProperties.getProperty("algorithm.Sequence");
	}
	
	public String getDataFolder(){
		return algorithmProperties.getProperty("algorithm.kaasDataFolder");
	}
	public String getThreadNum(){
		return algorithmProperties.getProperty("algorithm.threadNum");
	}
	
	public String getDataPath(){
		return algorithmProperties.getProperty("algorithm.dataPath");
	}
	public String getFolder(){
		return algorithmProperties.getProperty("algorithm.folder");
	}
	public String getWaitTime(){
		return algorithmProperties.getProperty("algorithm.waitTime");
	}
	public String getItemDelimiter(){
		return algorithmProperties.getProperty("algorithm.itemDelimiter");
	}
	public String getMaxTryCount(){
		return algorithmProperties.getProperty("algorithm.maxTryCount");
	}
	public String getDataFile(){
		return algorithmProperties.getProperty("algorithm.dataFile");
	}
	public String getOrderFrequentOutPath(){
		return algorithmProperties.getProperty("algorithm.orderFrequentOutPath");
	}
	public String getRuleOutPath(){
		return algorithmProperties.getProperty("algorithm.ruleOutPath");
	}
	public String getSubmitLoopMaxStr(){
		return algorithmProperties.getProperty("algorithm.submitLoopMaxStr");
	}
	public String getCombinationMaxSizeStr(){
		return algorithmProperties.getProperty("algorithm.combinationMaxSizeStr");
	}
	public String getFrequencyLowerLimitStr(){
		return algorithmProperties.getProperty("algorithm.frequencyLowerLimitStr");
	}
}
