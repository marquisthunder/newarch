package com.thinkingtop.kaas.services.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault;

public class ClassUtilTest {

	@Test
	public void test() {
		Map<String,Algorithm> algorithms = new HashMap<String, Algorithm>();
		Set<Class<?>> classstr = ClassUtil.getClasses(AlgorithmDefault.class.getPackage());
		for(Class<?> c : classstr){
			String algorithmClassName = c.getName();
			if(algorithmClassName.matches(".+\\.[\\w]+$")){
				try {
					int i = algorithmClassName.lastIndexOf(".");
					String name = algorithmClassName.substring(i+1);
					Algorithm a = (Algorithm)c.newInstance();
					algorithms.put(name, a);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
