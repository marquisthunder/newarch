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
		dataP += "../../src/main/resources/";
		return dataP;
	}
	public String getDataPath() {
		return dataPath;
	}
	@Value("${runner.dataPath}")
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	public String getOfPath() {
		return ofPath;
	}
	@Value("${runner.orderFrequentOutPath}")
	public void setOfPath(String ofPath) {
		this.ofPath = ofPath;
	}
	public String getrPath() {
		return rPath;
	}
	@Value("${runner.ruleOutPath}")
	public void setrPath(String rPath) {
		this.rPath = rPath;
	}
}
