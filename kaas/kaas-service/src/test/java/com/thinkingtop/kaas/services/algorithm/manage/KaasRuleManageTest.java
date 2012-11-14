package com.thinkingtop.kaas.services.algorithm.manage;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.algorithm.model.KaasRule;
import com.thinkingtop.kaas.services.util.BeforeTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:algorithmbeans.xml")
public class KaasRuleManageTest {
	static Logger logger=Logger.getLogger(KaasRuleManageTest.class);
	@Autowired
	private KaasRuleManage kaasRuleManage;
    @Before
    public void init() {
    	BeforeTest.init();
    }
	@Test
	public void testAdd() {
		KaasRule r = new KaasRule();
		r.setId(1);
		r.setProducts("2,4");
		r.setRecommendation("3");
		r.setConfidence(0.9111);
		r.setFlag("general");
		kaasRuleManage.add(r);
		
		KaasRule r2 = kaasRuleManage.getRule(1);
		assertEquals("2,4", r2.getProducts());
		assertEquals("0.9111", r2.getConfidence().toString());
	}
	
	@Test
	public void testClearAll(){
		kaasRuleManage.deleteAll();
		KaasRule r = kaasRuleManage.getRule(1);
		assertEquals(true, r==null);
	}
	
	@Test
	public void testUpdate(){
		KaasRule r = new KaasRule();
		r.setId(1);
		r.setProducts("2,4");
		r.setRecommendation("3");
		r.setConfidence(0.9111);
		r.setFlag("general");
		kaasRuleManage.update(r);
		
		KaasRule r2 = kaasRuleManage.getRule(1);
		assertEquals("2,4", r2.getProducts());
		assertEquals("0.9111", r2.getConfidence().toString());
	}
	
	@Test
	public void testIsHold(){
		boolean is = kaasRuleManage.isHold("2,4", "3");
		assertEquals(true, is);
	}
	
	@Test
	public void testGetRules(){
		List<String> strs = kaasRuleManage.getRules("2");
		for(String s : strs){
			logger.info("strs:-----"+s);
		}
	}

}
