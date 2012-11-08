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
import com.thinkingtop.kaas.services.algorithm.util.AlgorithmProperties;
import com.thinkingtop.kaas.services.algorithm.util.PackageDate;

@Component("jarAlgorithmManage")
public class JarAlgorithmManage {
	private PackageDate packageDate;
	private AlgorithmProperties algorithmProperties;
	static Logger logger=Logger.getLogger(JarAlgorithmManage.class);
	private Map<String,Algorithm> algorithms;
	
	public static JarAlgorithmManage getJarAlgorithmManage(){
		ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("algorithmbeans.xml");
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
		String[] algorithmSequence =  algorithmProperties.getSequence().split(algorithmProperties.getItemDelimiter());
		for(int i=0; i<algorithmSequence.length; i++){
			if(algorithmSequence[i].equals("")){
				continue;
			}
			String c = algorithmSequence[i].substring(0,1);
			String lower = c.toLowerCase();
			String algorithmName = lower + algorithmSequence[i].substring(1,algorithmSequence[i].length());
			Algorithm myAlgorithm = algorithms.get(algorithmName);
			if(myAlgorithm==null){
				logger.warn("Configuration file does not correspond with the algorithm");
			}
			logger.info("algorithm:"+(i+1)+"---- "+algorithmName +"="+myAlgorithm);
			myAlgorithm.runIt("data"+(i+1));
		}
		packageDate.packageD();
	}

	public AlgorithmProperties getAlgorithmProperties() {
		return algorithmProperties;
	}

	@Resource(name="algorithmProperties")
	public void setAlgorithmProperties(AlgorithmProperties algorithmProperties) {
		this.algorithmProperties = algorithmProperties;
	}

	public PackageDate getPackageDate() {
		return packageDate;
	}

	@Resource(name="packageDate")
	public void setPackageDate(PackageDate packageDate) {
		this.packageDate = packageDate;
	}
}
