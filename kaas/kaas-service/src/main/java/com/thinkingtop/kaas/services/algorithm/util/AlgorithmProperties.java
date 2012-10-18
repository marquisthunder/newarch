package com.thinkingtop.kaas.services.algorithm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.stereotype.Component;
@Component("algorithmProperties")
public class AlgorithmProperties {
	public static Properties properties = new Properties();
	AlgorithmProperties(){
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("algorithm.properties");
			properties.load(inputStream);
			inputStream.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getThreadNum(){
		return properties.getProperty("algorithm.threadNum");
	}
	
	public String getDataPath(){
		return properties.getProperty("algorithm.dataPath");
	}
	public String getFolder(){
		return properties.getProperty("algorithm.folder");
	}
	public String getWaitTime(){
		return properties.getProperty("algorithm.waitTime");
	}
	public String getItemDelimiter(){
		return properties.getProperty("algorithm.itemDelimiter");
	}
	public String getMaxTryCount(){
		return properties.getProperty("algorithm.maxTryCount");
	}
	public String getDataFile(){
		return properties.getProperty("algorithm.dataFile");
	}
	public String getOrderFrequentOutPath(){
		return properties.getProperty("algorithm.orderFrequentOutPath");
	}
	public String getRuleOutPath(){
		return properties.getProperty("algorithm.ruleOutPath");
	}
	public String getSubmitLoopMaxStr(){
		return properties.getProperty("algorithm.submitLoopMaxStr");
	}
	public String getCombinationMaxSizeStr(){
		return properties.getProperty("algorithm.combinationMaxSizeStr");
	}
	public String getFrequencyLowerLimitStr(){
		return properties.getProperty("algorithm.frequencyLowerLimitStr");
	}
}
