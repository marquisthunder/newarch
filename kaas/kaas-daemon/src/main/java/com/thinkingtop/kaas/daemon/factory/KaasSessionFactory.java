package com.thinkingtop.kaas.daemon.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thinkingtop.kaas.daemon.inter.SessionFactoryInterface;


public class KaasSessionFactory implements SessionFactoryInterface{
	public Object getBean(String name) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		name = "sessionFactory";
		return ac.getBean(name);
	}
}
