package com.thinkingtop.kaas.services.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("kaasDataPath")
public class KaasDataPath {
	private String dataPath;
	private String ofPath;
	private String rPath;
	public String getItemDataPath(){
		String itemDatap = getKassDataPath() + dataPath;
		return itemDatap;
	}
	public String getofDataPath(){
		String ofDatap = getKassDataPath() + ofPath;
		return ofDatap;
	}
	public String getRDataPath(){
		String rDatap = getKassDataPath()+rPath;
		return rDatap;
	}
	public String getKassDataPath(){
		String dataP = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		return dataP;
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
		KaasDataPath kdp = new KaasDataPath();
		String classLocation = kdp.getKassDataPath();
		System.out.println(classLocation);
		String packageName = "com.thinkingtop.kaas.services.algorithm";
		String[] classst = ClassUtil.getPackageAllClassName(classLocation, packageName);
		for(String s : classst){
			System.out.println(s);
		}
	}
	
	public static void main(String[] args) {
		KaasDataPath kdp = new KaasDataPath();
		kdp.getClassName();
	}
}
