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
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("algorithm.properties");
			algorithmProperties.load(inputStream);
			inputStream.close();
			inputStream = this.getClass().getClassLoader().getResourceAsStream("scheme.properties");
			schemeProperties.load(inputStream);
			inputStream.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSchemeName(){
		return schemeProperties.getProperty("scheme.name");
	}
	
	public String getECommerceName(){
		return schemeProperties.getProperty("eCommerce.name");
	}
	
	public String getSequence(){
		return schemeProperties.getProperty("algorithm.Sequence");
	}
	
	public String getRelationFolder(){
		return schemeProperties.getProperty("scheme.name");
	}
	
	public String getKaasDataFolder(){
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
