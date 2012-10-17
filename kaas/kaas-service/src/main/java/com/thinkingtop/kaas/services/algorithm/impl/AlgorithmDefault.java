package com.thinkingtop.kaas.services.algorithm.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.Algorithm;
import com.thinkingtop.kaas.services.algorithm.AlgorithmGeneral;
import com.thinkingtop.kaas.services.algorithm.util.AlgorithmProperties;

@Component("algorithmDefault")
public class AlgorithmDefault extends AlgorithmGeneral implements Algorithm{
    static Logger logger=Logger.getLogger(AlgorithmDefault.class);
	
	public String getSubmitLoopMaxStr() {
		return super.getAlgorithmProperties().getSubmitLoopMaxStr();
	}
	
    public void println(){
    	logger.info("------------------------------------println properties ");
    	logger.info("fileHistoryDAO:  "+super.getFileHistoryDAO().getClass());
    	logger.info("ofdao:  "+super.getOfdao().getClass());
    	logger.info("rdao:  "+super.getRdao().getClass());
    	logger.info("threadNum:  "+super.getThreadNum());
    	logger.info("folder:  "+super.getFolder());
    	logger.info("waitTime:  "+super.getWaitTime());
    	logger.info("submitLoopMaxStr:  "+super.getAlgorithmProperties().getSubmitLoopMaxStr());
    	logger.info("combinationMaxSizeStr:  "+super.getAlgorithmProperties().getCombinationMaxSizeStr());
    	logger.info("frequencyLowerLimitStr:  "+super.getAlgorithmProperties().getFrequencyLowerLimitStr());
    	logger.info("------------------------------------println properties end");
    }
	
	public String getCombinationMaxSizeStr() {
		return super.getAlgorithmProperties().getCombinationMaxSizeStr();
	}
	
	public String getFrequencyLowerLimitStr() {
		return super.getAlgorithmProperties().getFrequencyLowerLimitStr();
	}
	

	public void runIt() {
		logger.info("runIt Default");
	}

	public String[] getRecommend(String inputItems, int outputItemsNum,
			int outputQuantitye) {
		// TODO Auto-generated method stub
		String[] a = {"test"};
		return a;
	}

	public AlgorithmProperties getAlgorithmProperties() {
		return super.getAlgorithmProperties();
	}
	
}
