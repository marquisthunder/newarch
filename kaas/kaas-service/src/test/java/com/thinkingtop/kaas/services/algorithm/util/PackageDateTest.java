package com.thinkingtop.kaas.services.algorithm.util;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class PackageDateTest {
	@Autowired
	private PackageDate packageDate;
	static Logger logger=Logger.getLogger(PackageDateTest.class);
	
	@Test
	public void testPackageD() {
		packageDate.packageD();
	}

}
