package com.thinkingtop.kaas.services.dao.implfile;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class RuleDAOFileImplTest {
	@Qualifier("ruleDAOFileImpl")
	@Autowired
	private RuleDAOFileImpl ruleDAOFileImpl;
	
	@Test
	public void testGetRecommend() {
		String[] a = ruleDAOFileImpl.getRecommend("scheme1","1,2", 2, 1);
		Assert.assertEquals("3,6",a[0]);
	}

}
