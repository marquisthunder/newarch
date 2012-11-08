package com.thinkingtop.kaas.services.algorithm;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.algorithm.impl.AlgorithmDefault;
import com.thinkingtop.kaas.services.algorithm.impl.AprioriRunner;
import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class AprioriRunnerTest {
    static Logger logger=Logger.getLogger(AprioriRunnerTest.class);
	@Autowired
	private AprioriRunner aprioriRunner;
	
	@Autowired
    private KaasDataPath kaasDataPath;
	

	
	@Test
	public void aprioriOfflineTest() {
		aprioriRunner.runIt("data1");
		/*aprioriService.process(folderName);
		assert.*/
		DataInputStream inR = null;
		DataInputStream inOf = null;
        try {
            inR = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(kaasDataPath.getRDataPath()+"/data1")));
            inOf = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(kaasDataPath.getofDataPath())));
        } catch (FileNotFoundException e) {
            logger.warn("local offline file may be moved or renamed!");
        }
        int iR = 0;
        int iOf =0;
        try {
            while (inR.readLine() != null) {
            	iR++;
            }
            while (inOf.readLine() != null) {
            	iOf++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	try {
        		inR.close();
        		inOf.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
        
		Assert.assertEquals(86, iR);
		Assert.assertEquals(62, iOf);
	}
	


}
