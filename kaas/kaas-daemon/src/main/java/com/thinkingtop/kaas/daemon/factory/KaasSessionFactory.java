package com.thinkingtop.kaas.daemon.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thinkingtop.kaas.daemon.inter.SessionFactoryInterface;


public class KaasSessionFactory implements SessionFactoryInterface{
	private static ApplicationContext ac = null ;
	public Object getBean(String name) {
		if(ac==null) {
			ac = new ClassPathXmlApplicationContext("applicationContextForH2.xml");
		}
		name = "sessionFactory";
		return ac.getBean(name);
	}
}
