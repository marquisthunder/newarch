package com.thinkingtop.kaas.services.manage;

import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.thinkingtop.kaas.services.algorithm.Algorithm;

public class AlgorithmManage  implements Algorithm{
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
	
	public void setMyAlgorithm(Algorithm myAlgorithm) {
		this.myAlgorithm = myAlgorithm;
	}
	
	public String process(String algorithmName){
		for (Map.Entry<String, Algorithm> me: algorithms.entrySet()){
			if(me.getKey().equals(algorithmName)){
				setMyAlgorithm(me.getValue());
				return "yes";
			}
		}
		return "This algorithm does not exist";
	}
	
	public void runIt() {
		myAlgorithm.runIt();
	}
	public String[] getRecommend(String inputItems, int outputItemsNum,
			int outputQuantitye) {
		return myAlgorithm.getRecommend(inputItems, outputItemsNum, outputQuantitye);
	}
}
