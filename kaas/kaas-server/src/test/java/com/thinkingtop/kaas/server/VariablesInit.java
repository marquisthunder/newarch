package com.thinkingtop.kaas.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class VariablesInit {
	public static ApplicationContext ac  =  new ClassPathXmlApplicationContext("applicationContext.xml");
}
