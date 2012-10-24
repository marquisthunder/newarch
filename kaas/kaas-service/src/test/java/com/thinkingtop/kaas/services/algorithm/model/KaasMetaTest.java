package com.thinkingtop.kaas.services.algorithm.model;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.algorithm.util.PackageDate;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class KaasMetaTest {
    static Logger logger=Logger.getLogger(KaasMetaTest.class);
    
    @Autowired
	private PackageDate packageDate;
	@Test
	public void testCreateFile() {
		packageDate.packageD();
	}

}
