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

import com.thinkingtop.kaas.services.model.ECommerce;
import com.thinkingtop.kaas.services.model.Scheme;
import com.thinkingtop.kaas.services.util.BeforeTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class SchemeManageTest {
	static Logger logger=Logger.getLogger(SchemeManageTest.class);
	@Autowired
	private SchemeManage schemeManage;
	
    @Before
    public void init() {
    	BeforeTest.init();
    }
    
	@Test
	public void testAdd() {
		boolean isThrough = false;
		try{
			Scheme scheme = new Scheme();
			scheme.setSchemeName("scheme1");
			ECommerce ecommerce = new ECommerce();
			ecommerce.setId((long)5);
			scheme.setEcommerce(ecommerce);
			scheme.setAlgorithmNames("AlgorithmDefault,AprioriRunner");
			schemeManage.add(scheme);
		}catch(Exception e){
			isThrough = true;
		}
		Assert.assertEquals(true, isThrough);
	}
	
	@Test
	public void testGetScheme(){
		Scheme scheme = schemeManage.getScheme(1);
		Assert.assertEquals(true, scheme!=null);
		//logger.info("id=1 scheme:"+scheme);
	}
	
	@Test
	public void testGetSchemeName(){
		Scheme scheme = schemeManage.getScheme("scheme1");
		Assert.assertEquals(true, scheme!=null);
		//logger.info("id=1 scheme:"+scheme);
	}

}
