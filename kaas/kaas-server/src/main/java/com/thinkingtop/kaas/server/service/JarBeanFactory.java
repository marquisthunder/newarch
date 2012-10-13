package com.thinkingtop.kaas.server.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class JarBeanFactory implements ApplicationContextAware {
	private static JarBeanFactory jbf = null;
	
	private JarBeanFactory() {
		
	}
	
	public static JarBeanFactory newInstance() {
		if(jbf==null) {
			JarBeanFactory jbf = new JarBeanFactory();
			return jbf;
		}
		return jbf;
	}
	
	private static ApplicationContext ac;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}
	
	public Object getBean(String name) {
		return ac.getBean(name);
	}

}
