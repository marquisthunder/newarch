package com.thinkingtop.kaas.services.util;

import java.io.File;
import java.util.Iterator;

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
	private String packagePaths;
	private String rPath;
	private String myKaasdataPath;
	
	public PackagePath(){
		/*logger.info(
				Thread.currentThread().getContextClassLoader().getResource(""));
				logger.info(PackagePath.class.getClassLoader().getResource(""));
				logger.info(ClassLoader.getSystemResource(""));
				logger.info(PackagePath.class.getResource("").toString());
				logger.info(PackagePath.class.getResource("/"));
				logger.info(new File("").getAbsolutePath());
				logger.info(System.getProperty("user.dir"));*/
		this.myKaasdataPath = new File("").getAbsolutePath();
		int beginIndex;
		if((beginIndex = this.myKaasdataPath.lastIndexOf("dist"))==-1){
			this.myKaasdataPath = this.myKaasdataPath + "/../dist/";
		}else if((beginIndex = this.myKaasdataPath.lastIndexOf("dist"))!=-1){
			this.myKaasdataPath = this.myKaasdataPath.substring(0,beginIndex)+"dist/";
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

	@Value("${package.path}")
	public void setPackagePaths(String packagePaths) {
		this.packagePaths = packagePaths;
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
