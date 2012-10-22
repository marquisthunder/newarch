package com.thinkingtop.kaas.services.algorithm.util;

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

@Component("kaasDataPath")
public class KaasDataPath {
	static Logger logger=Logger.getLogger(KaasDataPath.class);
	private AlgorithmProperties algorithmProperties;
	
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
	
	public String getItemDataPath(){
		String itemDatap = getMyKaasdataPath() + getDataPath();
		return itemDatap;
	}
	public String getofDataPath(){
		String ofDatap = getMyKaasdataPath() + getOfPath();
		return ofDatap;
	}
	public String getRDataPath(){
		String rDatap = getMyKaasdataPath()+getrPath();
		return rDatap;
	}
	
	public Iterator<Element> getKaasOrders(String filePath){
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			
			logger.info("data : ---------"+getItemDataPath()+"/"+filePath);
			document = saxReader.read(getItemDataPath()+"/"+filePath);
			Element elements = document.getRootElement();
			Iterator<Element> applications = elements.elementIterator();
			return applications;
		} catch (DocumentException e) {
			logger.warn("local offline file may be moved or renamed!");
		}
		return null;
	}
	
	public String getDataPath() {
		return algorithmProperties.getDataPath();
	}
	public String getOfPath() {
		return algorithmProperties.getOrderFrequentOutPath();
	}
	public String getrPath() {
		return algorithmProperties.getRuleOutPath();
	}
	
	public static void getClassName(){
		/*KaasDataPath kdp = new KaasDataPath();
		String classLocation = kdp.getMyKaasdataPath();
		System.out.println(classLocation);
		String packageName = "com.thinkingtop.kaas.services.algorithm";
		String[] classst = ClassUtil.getPackageAllClassName(classLocation, packageName);
		for(String s : classst){
			System.out.println(s);
		}*/
	}
	
	public static void main(String[] args) {
		KaasDataPath kdp = new KaasDataPath();
		kdp.getClassName();
	}
	public String getMyKaasdataPath() {
		
		String folder = algorithmProperties.getDataFolder();
		String myKaasdataPath = new File("").getAbsolutePath();
		int beginIndex;
		if((beginIndex =  myKaasdataPath.lastIndexOf(folder))==-1){
			myKaasdataPath = myKaasdataPath + "/../"+folder+"/";
		}else if((beginIndex = myKaasdataPath.lastIndexOf(folder))!=-1){
			myKaasdataPath = myKaasdataPath.substring(0,beginIndex) + folder + "/";
		}
		return myKaasdataPath;
	}

	public AlgorithmProperties getAlgorithmProperties() {
		return algorithmProperties;
	}

	@Resource(name="algorithmProperties")
	public void setAlgorithmProperties(AlgorithmProperties algorithmProperties) {
		this.algorithmProperties = algorithmProperties;
	}
}
