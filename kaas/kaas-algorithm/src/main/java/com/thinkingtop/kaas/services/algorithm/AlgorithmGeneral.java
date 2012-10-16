package com.thinkingtop.kaas.services.algorithm;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.thinkingtop.kaas.services.algorithm.dao.FileHistoryDAO;
import com.thinkingtop.kaas.services.algorithm.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.algorithm.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;

public class AlgorithmGeneral {
	private ThreadPoolTaskExecutor taskExecutor;
    private FileHistoryDAO fileHistoryDAO;
    private KaasOrderFrequentDAO ofdao;
    private KaasRuleDAO rdao;
    private String threadNum;
    private KaasDataPath kaasDataPath;
    private String folder;
    private String waitTime;
    private String itemDelimiter;
    private String maxTryCount;
    private int actualThreadNum;
    private int threadEndNum;
    private long runAllTime;
    private long runTimeRecord0;
    
	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	
	@Resource(name="threadPoolTaskExecutor")
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public FileHistoryDAO getFileHistoryDAO() {
		return fileHistoryDAO;
	}
	
	@Resource(name="fileHistoryDAOFileImpl")
	public void setFileHistoryDAO(FileHistoryDAO fileHistoryDAO) {
		this.fileHistoryDAO = fileHistoryDAO;
	}
	public KaasOrderFrequentDAO getOfdao() {
		return ofdao;
	}
	
	@Resource(name="kaasOrderFrequentDAOFileImpl")
	public void setOfdao(KaasOrderFrequentDAO ofdao) {
		this.ofdao = ofdao;
	}
	public KaasRuleDAO getRdao() {
		return rdao;
	}
	
	@Resource(name="kaasRuleDAOFileImpl")
	public void setRdao(KaasRuleDAO rdao) {
		this.rdao = rdao;
	}
	public String getThreadNum() {
		return threadNum;
	}
	@Value("${algorithm.threadNum}")
	public void setThreadNum(String threadNum) {
		this.threadNum = threadNum;
	}
	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}
	
	@Resource(name="kaasDataPath")
	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}
	public String getFolder() {
		return folder;
	}
	@Value("${algorithm.folder}")
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getWaitTime() {
		return waitTime;
	}
	@Value("${algorithm.waitTime}")
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	public String getItemDelimiter() {
		return itemDelimiter;
	}
	@Value("${algorithm.itemDelimiter}")
	public void setItemDelimiter(String itemDelimiter) {
		this.itemDelimiter = itemDelimiter;
	}
	public String getMaxTryCount() {
		return maxTryCount;
	}
	@Value("${algorithm.maxTryCount}")
	public void setMaxTryCount(String maxTryCount) {
		this.maxTryCount = maxTryCount;
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
}
