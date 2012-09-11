package com.thinkingtop.kaas.services.service;

import static org.junit.Assert.*;


import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExclusiveKeyServiceImplTest {

	@Test
	public void testGetGoods() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ExclusiveKeyServiceImpl service = (ExclusiveKeyServiceImpl)ctx.getBean("exclusiveKeyServiceImpl");
		System.out.println(service.getClass());
		String s = service.getGoods("jingdong", "7gwIgipv1iCXk13-4X70oO030eJAeKh$0t~~Y82f6gwqt8UyLbfhua~yRG_UE4TDg28itxhRij#bGOt", "1,2", 2);
		System.out.println(s);
		ctx.destroy();
	}

}
