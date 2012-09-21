package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
import com.thinkingtop.kaas.service.util.BeforeTest;
import com.thinkingtop.kaas.services.manage.WebsiteManage;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Website;
import com.thinkingtop.kaas.services.service.ExclusiveKeyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class WebsiteManageTest {
	@Autowired
	private WebsiteManage websiteManage;
	
    @Before
    public void init() {
    	BeforeTest.init();
    }
	
	@Test
	public void testAdd() {
		Website website = new Website();
		website.setWebsiteName("taobao");
		websiteManage.add(website);
		
		Website website2 = websiteManage.getWebsite(2);
		Assert.assertEquals("taobao", website2.getWebsiteName());
	}
	
	@Test
	public void testAdd2() {
		boolean isThrough = false;
		try{
			Website website = new Website();
			website.setWebsiteName("jingdong");
			websiteManage.add(website);
		}catch(Exception e){
			isThrough = true;
		}
		Assert.assertEquals(true, isThrough);
	}
	
	@Test
	public void testGetWebsite() {
		Website website = websiteManage.getWebsite(1);
		Assert.assertEquals("jingdong", website.getWebsiteName());
		
		website = websiteManage.getWebsite("jingdong");
		long websiteId = website.getId();
		Assert.assertEquals(1, websiteId);
	}

}
