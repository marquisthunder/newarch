package com.thinkingtop.kaas.services.algorithm.manage;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault;

@Component("jarAlgorithmManage")
public class JarAlgorithmManage {
	static Logger logger=Logger.getLogger(JarAlgorithmManage.class);
	private Map<String,Algorithm> algorithms;
	
	public static JarAlgorithmManage getJarAlgorithmManage(){
		ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("classpath*:/algorithmbeans.xml");
		JarAlgorithmManage algorithmManage = (JarAlgorithmManage) acx.getBean("jarAlgorithmManage");
		acx.destroy();
		return algorithmManage;
	}
	
	public Map<String, Algorithm> getAlgorithms() {
		return algorithms;
	}
	
	@Resource()
	public void setAlgorithms(Map<String, Algorithm> algorithms) {
		this.algorithms = algorithms;
	}
	
	public void runIt() {
		for(Map.Entry a : algorithms.entrySet()){
			logger.info("algorithm:-- "+a);
			Algorithm myAlgorithm = (Algorithm) a.getValue();
			myAlgorithm.runIt();
		}
	}
}
