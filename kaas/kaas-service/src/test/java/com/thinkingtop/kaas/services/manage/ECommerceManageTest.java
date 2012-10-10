package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jolbox.bonecp.BoneCPDataSource;
import com.thinkingtop.kaas.services.manage.ECommerceManage;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.ECommerce;
import com.thinkingtop.kaas.services.model.Scheme;
import com.thinkingtop.kaas.services.service.ExclusiveKeyServiceImpl;
import com.thinkingtop.kaas.services.util.BeforeTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class ECommerceManageTest {
	@Autowired
	private ECommerceManage ecommerceManage;
	
    @Before
    public void init() {
    	BeforeTest.init();
    }
	
	@Test
	public void testAdd() {
		ECommerce ecommerce = new ECommerce();
		ecommerce.setEcommerceName("taobao");
		ecommerceManage.add(ecommerce);
		
		ECommerce ecommerce2 = ecommerceManage.getECommerce(2);
		Assert.assertEquals("taobao", ecommerce2.getEcommerceName());
	}
	
	@Test
	public void testAdd2() {
		boolean isThrough = false;
		try{
			ECommerce ecommerce = new ECommerce();
			ecommerce.setEcommerceName("jingdong");
			ecommerceManage.add(ecommerce);
		}catch(Exception e){
			isThrough = true;
		}
		Assert.assertEquals(true, isThrough);
	}
	
	@Test
	public void testGetECommerce() {
		ECommerce ecommerce = ecommerceManage.getECommerce(1);
		Assert.assertEquals("jingdong", ecommerce.getEcommerceName());
		
		ecommerce = ecommerceManage.getECommerce("jingdong");
		long ecommerceId = ecommerce.getId();
		Assert.assertEquals(1, ecommerceId);
	}
	
	@Test
	public void testGetECommerceAndScheme() {
		ECommerce ecommerce = ecommerceManage.getECommerceAndScheme("jingdong");
		long ecommerceId = ecommerce.getId();
		Set<Scheme> schemes = ecommerce.getSchemes();
		Assert.assertEquals(true, schemes!=null);
		Assert.assertEquals(1, ecommerceId);
	}

}
