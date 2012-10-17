package com.thinkingtop.kaas.services.algorithm;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.thinkingtop.kaas.services.algorithm.dao.FileHistoryDAO;
import com.thinkingtop.kaas.services.algorithm.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.algorithm.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.algorithm.util.AlgorithmProperties;
import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;
 

public class AlgorithmGeneral {
	private  AlgorithmProperties algorithmProperties;
	private ThreadPoolTaskExecutor taskExecutor;
	private FileHistoryDAO fileHistoryDAO;
	private KaasOrderFrequentDAO ofdao;
	private KaasRuleDAO rdao;
	private KaasDataPath kaasDataPath;
	private int actualThreadNum;
	private int threadEndNum;
	private long runAllTime;
	private long runTimeRecord0;

	@Resource(name="algorithmProperties")
	public void setAlgorithmProperties(AlgorithmProperties algorithmProperties) {
		this.algorithmProperties = algorithmProperties;
	}
	
	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	@Resource(name = "threadPoolTaskExecutor")
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public FileHistoryDAO getFileHistoryDAO() {
		return fileHistoryDAO;
	}

	@Resource(name = "fileHistoryDAOFileImpl")
	public void setFileHistoryDAO(FileHistoryDAO fileHistoryDAO) {
		this.fileHistoryDAO = fileHistoryDAO;
	}

	public KaasOrderFrequentDAO getOfdao() {
		return ofdao;
	}

	@Resource(name = "kaasOrderFrequentDAOFileImpl")
	public void setOfdao(KaasOrderFrequentDAO ofdao) {
		this.ofdao = ofdao;
	}

	public KaasRuleDAO getRdao() {
		return rdao;
	}

	@Resource(name = "kaasRuleDAOFileImpl")
	public void setRdao(KaasRuleDAO rdao) {
		this.rdao = rdao;
	}

	public String getThreadNum() {
		System.out.println(algorithmProperties);
		return algorithmProperties.getThreadNum();
	}


	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}

	@Resource(name = "kaasDataPath")
	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}

	public String getFolder() {
		return algorithmProperties.getFolder();
	}


	public String getWaitTime() {
		return algorithmProperties.getWaitTime();
	}


	public String getItemDelimiter() {
		return algorithmProperties.getItemDelimiter();
	}


	public String getMaxTryCount() {
		return algorithmProperties.getMaxTryCount();
	}


	public int getActualThreadNum() {
		return actualThreadNum;
	}

	public void setActualThreadNum(int actualThreadNum) {
		this.actualThreadNum = actualThreadNum;
	}

	public int getThreadEndNum() {
		return threadEndNum;
	}

	public void setThreadEndNum(int threadEndNum) {
		this.threadEndNum = threadEndNum;
	}

	public long getRunAllTime() {
		return runAllTime;
	}

	public void setRunAllTime(long runAllTime) {
		this.runAllTime = runAllTime;
	}

	public long getRunTimeRecord0() {
		return runTimeRecord0;
	}

	public void setRunTimeRecord0(long runTimeRecord0) {
		this.runTimeRecord0 = runTimeRecord0;
	}

	public AlgorithmProperties getAlgorithmProperties() {
		return algorithmProperties;
	}
}
