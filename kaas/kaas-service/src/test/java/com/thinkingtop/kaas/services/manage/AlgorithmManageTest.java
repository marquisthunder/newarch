package com.thinkingtop.kaas.services.manage;

import static org.junit.Assert.*;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.impl.AprioriRunner;
import com.thinkingtop.kaas.services.util.BeforeTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class AlgorithmManageTest {
	static Logger logger=Logger.getLogger(AlgorithmManageTest.class);
	
	@Qualifier("algorithmManage")
	@Autowired
	private AlgorithmManage algorithmManage;
	
	@Test
	public void testProcess() {
		String s = algorithmManage.process("aprioriRunner");
		Assert.assertEquals("yes",s);
		s = algorithmManage.process("aprioriaaa");
		Assert.assertEquals(false,s.equals("yes"));
	}
	
	@Test
	public void testGetRecommend() {
		String s = algorithmManage.process("aprioriRunner");
		algorithmManage.runIt();
		String[] a = algorithmManage.getRecommend("1,2", 2, 1);
		//logger.info("algorithmManage myAlgorithm:"+a[0]);
		Assert.assertEquals("3,6",a[0]);
	}
	
	@Test
	public void testGetAlgorithms() {
		Map<String,Algorithm> algorithms = algorithmManage.getAlgorithms();
		for(Map.Entry a : algorithms.entrySet()){
			logger.info("algorithm:-- "+a);
		}
	}
}
