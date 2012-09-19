package com.thinkingtop.kaas.services.service;

import static org.junit.Assert.*;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.service.util.BeforeTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class ExclusiveKeyServiceImplTest {
	@Autowired
	private ExclusiveKeyServiceImpl exclusiveKeyServiceImpl;
    static Logger logger=Logger.getLogger(ExclusiveKeyServiceImplTest.class);
    
    @Before
    public void init() {
    	BeforeTest.init();
    }
    
	@Test
	public void testRecommends() {
		int outputItemsNum = 2;
		int outputQuantitye = 9;
		String[] ss = exclusiveKeyServiceImpl.getRecommends("jingdong", "an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ", "1", outputItemsNum, outputQuantitye);
		for(String s : ss)
			if(s!=null)
			Assert.assertEquals(outputItemsNum*2-1, s.length());
		Assert.assertEquals(true,ss.length<=outputQuantitye);
	}

}
