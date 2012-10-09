package com.thinkingtop.kaas.services.util;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class PackageAlgorithmTest {
	@Autowired
	private PackageAlgorithm packageAlgorithm;
	static Logger logger=Logger.getLogger(PackageAlgorithmTest.class);
	
	@Test
	public void testPackageA() {
		packageAlgorithm.packageA();
	}

}
