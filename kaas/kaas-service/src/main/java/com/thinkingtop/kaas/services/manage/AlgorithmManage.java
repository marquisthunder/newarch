package com.thinkingtop.kaas.services.manage;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault;
import com.thinkingtop.kaas.services.dao.implfile.RuleDAOFileImpl;
import com.thinkingtop.kaas.services.util.ClassUtil;

@Component("algorithmManage")
public class AlgorithmManage {
	static Logger logger=Logger.getLogger(AlgorithmManage.class);
	private Algorithm myAlgorithm;
	private RuleDAOFileImpl ruleDAOFileImpl;
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
	
	public void runIt(String name) {
		logger.info("myAlgorithm:----"+myAlgorithm);
		myAlgorithm.runIt(name);
	}
	public String[] getRecommend(String scheme,String inputItems, int outputItemsNum,
			int outputQuantitye) {
		return ruleDAOFileImpl.getRecommend(scheme,inputItems, outputItemsNum, outputQuantitye);
	}

	public RuleDAOFileImpl getRuleDAOFileImpl() {
		return ruleDAOFileImpl;
	}

	@Resource(name="ruleDAOFileImpl")
	public void setRuleDAOFileImpl(RuleDAOFileImpl ruleDAOFileImpl) {
		this.ruleDAOFileImpl = ruleDAOFileImpl;
	}
}
