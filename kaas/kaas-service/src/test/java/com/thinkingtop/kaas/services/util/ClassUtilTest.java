package com.thinkingtop.kaas.services.util;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault;

public class ClassUtilTest {

	@Test
	public void testGetClasses() {
		//KaasDataPath.getClassName();
		/*String classLocation = kdp.getKassDataPath();
		System.out.println(classLocation);
		String packageName = "com.thinkingtop.kaas.services.algorithm";
		String[] classst = ClassUtil.getPackageAllClassName(classLocation, packageName);
		for(String s : classst){
			System.out.println(s);
		}*/
		/*Set<Class<?>> classstr = ClassUtil.getClasses(AlgorithmDefault.class.getPackage());
		for(Class<?> c : classstr){
			String name = c.getName();
			if(name.matches(".+\\.[\\w]+$")){
				try {
					Algorithm a = (Algorithm)c.newInstance();
					int i = name.lastIndexOf(".");
					String name2 = name.substring(i+1);
					
					System.out.println("----------"+name2);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(c.getName());
		}*/
		/*Set<Class<?>> classstr = ClassUtil.getClasses(AlgorithmDefault.class.getPackage());
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
		}*/
	}

}
