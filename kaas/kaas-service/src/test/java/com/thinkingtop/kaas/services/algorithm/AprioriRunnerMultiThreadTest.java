package com.thinkingtop.kaas.services.algorithm;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thinkingtop.kaas.services.algorithm.impl.AprioriRunnerMultiThread;
import com.thinkingtop.kaas.services.algorithm.manage.KaasOrderFrequentManage;
import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:algorithmbeans.xml")
public class AprioriRunnerMultiThreadTest {
	static Logger logger=Logger.getLogger(AprioriRunnerMultiThreadTest.class);
	@Autowired
	private AprioriRunnerMultiThread aprioriRunnerMultiThread;
	@Autowired
    private KaasDataPath kaasDataPath;
	
	@Autowired
	private KaasOrderFrequentManage kaasOrderFrequentManage;
/*	@Test
	public void aprioriOfflineFileTest(){
		aprioriRunnerMultiThread.runIt("data1");
		
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
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
        
		Assert.assertEquals(86, iR);
		Assert.assertEquals(62, iOf);
	}*/
	
	@Test
	public void aprioriOfflineTest(){
		aprioriRunnerMultiThread.runIt("data1");
		
		DataInputStream inR = null;
        try {
        	logger.info("data1 path:-----"+kaasDataPath.getRDataPath()+"/data1");
            inR = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(kaasDataPath.getRDataPath()+"/data1")));
        } catch (FileNotFoundException e) {
            logger.warn("local offline file may be moved or renamed!");
        }
        int iR = 0;
        try {
            while (inR.readLine() != null) {
            	iR++;
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
        
		Assert.assertEquals(86, iR);
		Assert.assertEquals(62, kaasOrderFrequentManage.size());
	}

}
