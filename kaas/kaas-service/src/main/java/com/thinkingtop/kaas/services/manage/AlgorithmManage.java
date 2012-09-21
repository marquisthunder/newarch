package com.thinkingtop.kaas.services.manage;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.Algorithm;

@Component("algorithmManage")
public class AlgorithmManage {
	private Algorithm myAlgorithm;
	private Map<String,Algorithm> algorithms;
	
	public Map<String, Algorithm> getAlgorithms() {
		return algorithms;
	}
	public void setAlgorithms(Map<String, Algorithm> algorithms) {
		this.algorithms = algorithms;
	}
	public Algorithm getMyAlgorithm() {
		return myAlgorithm;
	}
	
	@Resource(name="algorithmDefault")
	public void setMyAlgorithm(Algorithm myAlgorithm) {
		this.myAlgorithm = myAlgorithm;
	}
}
