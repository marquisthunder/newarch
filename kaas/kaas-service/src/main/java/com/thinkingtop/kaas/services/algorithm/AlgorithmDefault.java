package com.thinkingtop.kaas.services.algorithm;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("algorithmDefault")
public class AlgorithmDefault extends AlgorithmGeneral implements Algorithm{
    static Logger logger=Logger.getLogger(AlgorithmDefault.class);
	private String submitLoopMaxStr;
	private String combinationMaxSizeStr;
	private String frequencyLowerLimitStr;
	
	public String getSubmitLoopMaxStr() {
		return submitLoopMaxStr;
	}
	
    public void println(){
    	logger.info("------------------------------------println properties ");
    	logger.info("fileHistoryDAO:  "+super.getFileHistoryDAO().getClass());
    	logger.info("ofdao:  "+super.getOfdao().getClass());
    	logger.info("rdao:  "+super.getRdao().getClass());
    	logger.info("threadNum:  "+super.getThreadNum());
    	logger.info("folder:  "+super.getFolder());
    	logger.info("waitTime:  "+super.getWaitTime());
    	logger.info("submitLoopMaxStr:  "+submitLoopMaxStr);
    	logger.info("combinationMaxSizeStr:  "+combinationMaxSizeStr);
    	logger.info("frequencyLowerLimitStr:  "+frequencyLowerLimitStr);
    	logger.info("------------------------------------println properties end");
    }
	
	@Value("${algorithm.submitLoopMaxStr}")
	public void setSubmitLoopMaxStr(String submitLoopMaxStr) {
		this.submitLoopMaxStr = submitLoopMaxStr;
	}
	public String getCombinationMaxSizeStr() {
		return combinationMaxSizeStr;
	}
	
	@Value("${algorithm.combinationMaxSizeStr}")
	public void setCombinationMaxSizeStr(String combinationMaxSizeStr) {
		this.combinationMaxSizeStr = combinationMaxSizeStr;
	}
	public String getFrequencyLowerLimitStr() {
		return frequencyLowerLimitStr;
	}
	
	@Value("${algorithm.frequencyLowerLimitStr}")
	public void setFrequencyLowerLimitStr(String frequencyLowerLimitStr) {
		this.frequencyLowerLimitStr = frequencyLowerLimitStr;
	}

	public void runIt() {
		// TODO Auto-generated method stub
		
	}

	public String[] getRecommend(String inputItems, int outputItemsNum,
			int outputQuantitye) {
		// TODO Auto-generated method stub
		String[] a = {"test"};
		return a;
	}
}
