package com.thinkingtop.kaasservice;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

public class KaasServiceManage {
	private Logger logger=Logger.getLogger(KaasServiceManage.class);
	private KaasService kaasService;
	KaasServiceManage(){
		try{
			kaasService = new KaasService();
			logger.info("webservice Connection successful");
		}catch(Exception e){
			logger.info("webservice Connection failure");
			kaasService = null;
		}
	}
	public KaasService getKaasService(){
		return kaasService;
	}
	
	public ExclusiveKeyService getExclusiveKeyService(){
		if(kaasService==null){
			getKaasService();
		}
		if(kaasService==null){
			logger.info("webservice has closed");
			return null;
		}
		ExclusiveKeyService eks;
		try{
			eks = kaasService.getExclusiveKeyServicePort();
		}catch(Exception e){
			logger.info("webservice has closed");
			return null;
		}
		return eks;
	}
}
