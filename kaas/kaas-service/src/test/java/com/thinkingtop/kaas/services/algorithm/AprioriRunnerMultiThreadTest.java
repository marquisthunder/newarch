package com.thinkingtop.kaas.services.algorithm;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.util.KaasDataPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class AprioriRunnerMultiThreadTest {
	static Logger logger=Logger.getLogger(AprioriRunnerMultiThreadTest.class);
	@Autowired
	private AprioriRunnerMultiThread aprioriRunnerMultiThread;
	@Autowired
    private KaasDataPath kaasDataPath;
	
	@Test
	public void aprioriOfflineTest(){
		aprioriRunnerMultiThread.runIt();
		
		DataInputStream inR = null;
		DataInputStream inOf = null;
        try {
            inR = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(kaasDataPath.getRDataPath())));
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
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
        
		//Assert.assertEquals(86, iR);
		Assert.assertEquals(62, iOf);
	}

}
