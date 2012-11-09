package com.thinkingtop.kaas.services.algorithm.manage;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.util.BeforeTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:algorithmbeans.xml")
public class KaasOrderFrequentManageTest {
    static Logger logger=Logger.getLogger(KaasOrderFrequentManageTest.class);
    @Autowired
    private KaasOrderFrequentManage kaasOrderFrequentManage;
    
    @Before
    public void init() {
    	BeforeTest.init();
    }
	@Test
	public void testAdd() {
		KaasOrderFrequent of = new KaasOrderFrequent("2,3,4,5,2", 2, 4, "all");
		kaasOrderFrequentManage.add(of);
		of = kaasOrderFrequentManage.getOrderFrequent("2,3,4,5,2");
		Assert.assertEquals(3, of.getId());
	}
	
	@Test
	public void testAdd2() {
		boolean isThrough = false;
		try{
			KaasOrderFrequent of = new KaasOrderFrequent("2,3,4,5", 2, 4, "all");
			kaasOrderFrequentManage.add(of);
		}catch(Exception e){
			isThrough = true;
		}
		Assert.assertEquals(true, isThrough);
	}
	
	@Test
	public void testGetOrderFrequent() {
		KaasOrderFrequent of = kaasOrderFrequentManage.getOrderFrequent("2,3,4,5");
		Assert.assertEquals(1,of.getId());
	}
	
	@Test
	public void testSize() {
		long i = kaasOrderFrequentManage.size();
		logger.info("kaasOrderFrequent size : "+i);
		Assert.assertEquals(2,i);
		
	}
	
	@Test
	public void testDeleteAll() {
		kaasOrderFrequentManage.deleteAll();
		long i = kaasOrderFrequentManage.size();
		logger.info("kaasOrderFrequent size : "+i);
		Assert.assertEquals(0,i);
	}
	
	@Test
	public void testUpdate() {
		KaasOrderFrequent of = kaasOrderFrequentManage.getOrderFrequent("2,3,4,5");
		logger.info("kaasOrderFrequent ID : "+of.getId());
		of.setFrequent(3);
		kaasOrderFrequentManage.update(of);
		KaasOrderFrequent of2 = kaasOrderFrequentManage.getOrderFrequent("2,3,4,5");
		Assert.assertEquals(3,(int)of2.getFrequent());
	}

}
