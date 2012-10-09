package com.thinkingtop.kaas.services.algorithm.manage;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault;
import com.thinkingtop.kaas.services.util.ClassUtil;

@Component("algorithmManage")
public class AlgorithmManage {
	static Logger logger=Logger.getLogger(AlgorithmManage.class);
	private Algorithm myAlgorithm;
	private Map<String,Algorithm> algorithms;
	
	public Map<String, Algorithm> getAlgorithms() {
		return algorithms;
	}
	
	@Resource()
	public void setAlgorithms(Map<String, Algorithm> algorithms) {
		for(Map.Entry a : algorithms.entrySet()){
			logger.info("algorithm:-- "+a);
		}
		this.algorithms = algorithms;
		/**/
	}
	public Algorithm getMyAlgorithm() {
		return myAlgorithm;
	}
	
	@Resource(name="algorithmDefault")
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
		logger.info("myAlgorithm:----"+myAlgorithm);
		myAlgorithm.runIt();
	}
	public String[] getRecommend(String inputItems, int outputItemsNum,
			int outputQuantitye) {
		return myAlgorithm.getRecommend(inputItems, outputItemsNum, outputQuantitye);
	}
}
