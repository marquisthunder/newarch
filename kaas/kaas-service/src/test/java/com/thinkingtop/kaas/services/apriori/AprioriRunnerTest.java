package com.thinkingtop.kaas.services.apriori;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AprioriRunnerTest {

	@Test
	public void test() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		AprioriRunner service = (AprioriRunner)ctx.getBean("aprioriRunner");
		System.out.println(service.getClass());
		service.runIt();
		ctx.destroy();
	}

}
