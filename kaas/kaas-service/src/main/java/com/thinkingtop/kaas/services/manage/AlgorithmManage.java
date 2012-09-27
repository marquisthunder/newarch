package com.thinkingtop.kaas.services.manage;

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
		/*Set<Class<?>> classstr = ClassUtil.getClasses(AlgorithmDefault.class.getPackage());
		for(Class<?> c : classstr){
			String algorithmClassName = c.getName();
			if(algorithmClassName.matches(".+\\.[\\w]+$")){
				try {
					int i = algorithmClassName.lastIndexOf(".");
					String name = algorithmClassName.substring(i+1);
					Algorithm a = (Algorithm)c.newInstance();
					this.algorithms.put(name, a);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}*/
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
