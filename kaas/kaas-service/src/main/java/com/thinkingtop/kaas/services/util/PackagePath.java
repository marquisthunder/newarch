package com.thinkingtop.kaas.services.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.Algorithm;

@Component("packagePath")
public class PackagePath {
	static Logger logger=Logger.getLogger(PackagePath.class);
	public  Properties packageProperties = new Properties();
	private String packagePaths;
	private String rPath;
	private String myKaasdataPath;
	
	public PackagePath(){
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("package.properties");
			packageProperties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String packagePath;
		for(int i=1;(packagePath = packageProperties.getProperty("package.path"+i))!=null;i++){
			File f = new File(packagePath);
			if(f.exists()){
				this.packagePaths = packagePath;
				break;
			}
		}
		
		if(packagePath==null){
			logger.warn("Path not found");
		}
		
	}
	
	public String getAlgorithmPath(){
		String  algorithmPath = Algorithm.class.getResource("").toString();
		if(algorithmPath.startsWith("file:")){
			algorithmPath = algorithmPath.substring("file:".length());
		}
		if(algorithmPath.matches(".*target/test-classes/com.*")){
			algorithmPath = algorithmPath.replaceFirst("target/test-classes/com","target/classes/com");
		}
		return algorithmPath;
	}
	
	public String getMyKaasdataPath() {
		return myKaasdataPath;
	}
	public void setMyKaasdataPath(String myKaasdataPath) {
		this.myKaasdataPath = myKaasdataPath;
	}

	public String getPackagePaths() {
		return packagePaths;
	}

	public String getRDataPath() {
		logger.info("RDataPath:--------------"+packagePaths + "/../" +rPath);
		return packagePaths + "/../" +rPath;
	}

	public String getrPath() {
		return rPath;
	}
	
	@Value("${algorithm.ruleOutPath}")
	public void setrPath(String rPath) {
		this.rPath = rPath;
	}
}
