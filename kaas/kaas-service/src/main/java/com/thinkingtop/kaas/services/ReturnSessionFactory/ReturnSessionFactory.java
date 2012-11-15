package com.thinkingtop.kaas.services.ReturnSessionFactory;

import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReturnSessionFactory {
	public static SessionFactory getSessionFactory(){
		ClassPathXmlApplicationContext acxDatabase = new ClassPathXmlApplicationContext("algorithmdatabase.xml");
		SessionFactory sessionFactory = (SessionFactory) acxDatabase.getBean("sessionFactory");
		return sessionFactory;
	}
}
