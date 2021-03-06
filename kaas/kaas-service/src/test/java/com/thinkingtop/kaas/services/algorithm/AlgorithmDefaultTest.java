package com.thinkingtop.kaas.services.algorithm;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:algorithmbeans.xml")
public class AlgorithmDefaultTest {
    static Logger logger=Logger.getLogger(AlgorithmDefaultTest.class);
    
	@Autowired
    private AlgorithmDefault algorithmDefault;
	
	@Test
	public void testPrintln() {
		algorithmDefault.println();
	}

}
