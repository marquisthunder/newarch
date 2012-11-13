package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.model.Rule;
import com.thinkingtop.kaas.services.util.BeforeTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class RuleManageTest {
	static Logger logger=Logger.getLogger(RuleManageTest.class);
	@Autowired
	private RuleManage ruleManage;
	
    @Before
    public void init() {
    	BeforeTest.init3();
    }
    
	@Test
	public void testGetRule() {
		Rule r = ruleManage.getRule(1);
		//Assert.assertEquals(true, r==null);
		Assert.assertEquals("2,4", r.getProducts());
		Assert.assertEquals("3", r.getRecommendation());
		Assert.assertEquals("0.857142857142857", r.getConfidence().toString());
	}
	
	@Test
	public void testGetRecommend() {
		int outputItemsNum = 1;
		int outputQuantitye = 2;
		String[] ss =ruleManage.getRecommend("scheme1", "2", outputItemsNum, outputQuantitye);
		//Assert.assertEquals(true, r==null);
		for(String s : ss){
			if(s!=null){
				logger.info("R ----------:"+s);
				Assert.assertEquals(outputItemsNum*2-1, s.length());
			}
		}
		Assert.assertEquals(true,ss.length<=outputQuantitye);
	}

}
