package com.thinkingtop.kaasservice;

import org.springframework.stereotype.Component;

@Component("kaasServiceManage")
public class KaasServiceManage {
	private static KaasService kaasService;
	public static KaasService getKaasService(){
		if(kaasService==null){
			try{
				kaasService = new KaasService();
			}catch(Exception e){
				kaasService = null;
				return null;
			}
		}
		return kaasService;
	}
	
	public static ExclusiveKeyService getExclusiveKeyService(){
		if(kaasService==null){
			getKaasService();
		}
		if(kaasService==null){
			return null;
		}
		ExclusiveKeyService eks;
		try{
			eks = kaasService.getExclusiveKeyServicePort();
		}catch(Exception e){
			return null;
		}
		return eks;
	}
}
