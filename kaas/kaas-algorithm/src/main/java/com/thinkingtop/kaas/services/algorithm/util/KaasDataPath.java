package com.thinkingtop.kaas.services.algorithm.util;

import java.io.File;
import java.util.Iterator;

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
	private String myKaasdataPath;
	private String dataPath;
	private String ofPath;
	private String rPath;
	
	public KaasDataPath(){
		this.myKaasdataPath = new File("").getAbsolutePath();
		int beginIndex;
		if(this.myKaasdataPath.endsWith("kaas-algorithm")){
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
	
	public String getItemDataPath(){
		String itemDatap = getMyKaasdataPath() + dataPath;
		return itemDatap;
	}
	public String getofDataPath(){
		String ofDatap = getMyKaasdataPath() + ofPath;
		return ofDatap;
	}
	public String getRDataPath(){
		String rDatap = getMyKaasdataPath()+rPath;
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
		return dataPath;
	}
	@Value("${algorithm.dataPath}")
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	public String getOfPath() {
		return ofPath;
	}
	@Value("${algorithm.orderFrequentOutPath}")
	public void setOfPath(String ofPath) {
		this.ofPath = ofPath;
	}
	public String getrPath() {
		return rPath;
	}
	@Value("${algorithm.ruleOutPath}")
	public void setrPath(String rPath) {
		this.rPath = rPath;
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
		return myKaasdataPath;
	}
	public void setMyKaasdataPath(String myKaasdataPath) {
		this.myKaasdataPath = myKaasdataPath;
	}
}
