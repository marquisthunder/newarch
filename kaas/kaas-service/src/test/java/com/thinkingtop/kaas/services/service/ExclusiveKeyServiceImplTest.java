package com.thinkingtop.kaas.services.service;

import static org.junit.Assert.*;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExclusiveKeyServiceImplTest {
    static Logger logger=Logger.getLogger(ExclusiveKeyServiceImplTest.class);
	@Test
	public void testGetGoods() {
		/*ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ExclusiveKeyServiceImpl service = (ExclusiveKeyServiceImpl)ctx.getBean("exclusiveKeyServiceImpl");
		System.out.println(service.getClass());
		String[] ss = service.getRecommends("jingdong", "7gwIgipv1iCXk13-4X70oO030eJAeKh$0t~~Y82f6gwqt8UyLbfhua~yRG_UE4TDg28itxhRij#bGOt", "1,2", 2, 1);
		for(String s : ss)
		logger.info("Pick of the month:  "+s);
		ctx.destroy();*/
	}

}
