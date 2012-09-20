package com.thinkingtop.kaas.services.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.apriori.AprioriRunner;
import com.thinkingtop.kaas.services.dao.ExclusiveKeyDAO;
import com.thinkingtop.kaas.services.manage.ExclusiveKeyManage;
import com.thinkingtop.kaas.services.manage.KebsiteManage;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.Kebsite;
import com.thinkingtop.kaas.services.util.APIKey;

/**
 * This is a ExclusiveKeyService implementation class
 * External exposure method, allows users to call
 * @author roadahead
 */
@Component("exclusiveKeyServiceImpl")
@WebService(endpointInterface = "com.thinkingtop.kaas.services.service.ExclusiveKeyService")
public class ExclusiveKeyServiceImpl implements ExclusiveKeyService{
    static Logger logger=Logger.getLogger(ExclusiveKeyServiceImpl.class);
	private ExclusiveKeyManage exclusiveKeyManage;
	private KebsiteManage kebsiteManage;
	private AprioriRunner aprioriRunner;
	private APIKey apiKey;

	/**
	 * External exposure method, user request recommendation
	 * @param kebsiteName:The requesting user name
	 * @param apiKey:The requesting APIKey
	 * @param inputItems:User input commodity
	 * @param outputItemsNum:Recommended Items number
	 * @param outputQuantitye:Recommended Items Quantitye
	 * @return If kebsiteName or apiKey or inputItems is empty,or apiKey without permission, return null
	 */
	public String[] getRecommends(String kebsiteName,String apiKey,String inputItems,int outputItemsNum,int outputQuantitye) {
		if(!kebsiteManage.isHold(kebsiteName)){
			logger.info("The user does not exist");
			return null;
		}
		if(!exclusiveKeyManage.isHold(kebsiteName,apiKey)){
			logger.info("The user does not have the APIKey");
			return null;
		}
		if(exclusiveKeyManage.isActivation(apiKey)){
			aprioriRunner.runIt();
	logger.info("inputItems:"+inputItems);
	logger.info("outputItemsNum"+outputItemsNum);
	logger.info("outputQuantitye:"+outputQuantitye);
			String[] mapItems = aprioriRunner.getRecommend(inputItems,outputItemsNum,outputQuantitye);
			return mapItems;
		}
		return null;
	}
	
	/**
	 * External exposure method, the user request and returns a APIKey,
	 * and put it into the database
	 * @param kebsiteName: User address or username
	 */
	public String getAPIKey(String kebsiteName) {
		Kebsite kebsite =kebsiteManage.getKebsite(kebsiteName);
		if(kebsite==null){
			return "User does not exist!";
		}
		StringBuffer keyString = apiKey.getAPIKey();
		boolean e = exclusiveKeyManage.isHold(keyString);
	logger.info(e);
		while(exclusiveKeyManage.isHold(keyString)){
			keyString = apiKey.getAPIKey();
		}
		exclusiveKeyManage.add(kebsite,keyString);
		return keyString.toString();
	}
	
	/**
	 * External exposure method,the return of the user's APIKey state
	 * @param kebsiteName:The requesting user name
	 * @param apiKey:The requesting APIKey
	 * @return If the user does not exist then return to -1,if the user does not have the APIKey returns -2,
	 * 		If APIKey does not activate the return 1,If the APIKey is activated and can use return 2,
	 * 		If the APIKey is out of date return 3,If APIKey are forbidden to use return 4
	 */
	public int getAPIKeyState(String kebsiteName, String keyString) {
		Kebsite kebsite =kebsiteManage.getKebsite(kebsiteName);
		if(kebsite==null){
			logger.info("The user does not exist");
			return -1;
		}
		ExclusiveKey ek = exclusiveKeyManage.getExclusiveKey(keyString);
		if(ek==null){
			return -2;
		}
		return ek.getState();
	}
	
	public ExclusiveKeyManage getExclusiveKeyManage() {
		return exclusiveKeyManage;
	}
	@Resource(name="exclusiveKeyManage")
	public void setExclusiveKeyManage(ExclusiveKeyManage exclusiveKeyManage) {
		this.exclusiveKeyManage = exclusiveKeyManage;
	}

	public KebsiteManage getKebsiteManage() {
		return kebsiteManage;
	}
	@Resource(name="kebsiteManage")
	public void setKebsiteManage(KebsiteManage kebsiteManage) {
		this.kebsiteManage = kebsiteManage;
	}

	public AprioriRunner getAprioriRunner() {
		return aprioriRunner;
	}

	@Resource(name="aprioriRunner")
	public void setAprioriRunner(AprioriRunner aprioriRunner) {
		this.aprioriRunner = aprioriRunner;
	}

	public APIKey getApiKey() {
		return apiKey;
	}
	@Resource(name="apiKey")
	public void setApiKey(APIKey apiKey) {
		this.apiKey = apiKey;
	}



}
