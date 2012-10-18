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

import com.thinkingtop.kaas.services.dao.ExclusiveKeyDAO;
import com.thinkingtop.kaas.services.manage.AlgorithmManage;
import com.thinkingtop.kaas.services.manage.ExclusiveKeyManage;
import com.thinkingtop.kaas.services.manage.ECommerceManage;
import com.thinkingtop.kaas.services.model.ExclusiveKey;
import com.thinkingtop.kaas.services.model.ECommerce;
import com.thinkingtop.kaas.services.model.Scheme;
import com.thinkingtop.kaas.services.util.APIKey;
import com.thinkingtop.kaas.services.util.PackageAlgorithm;

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
	private ECommerceManage ecommerceManage;
	private AlgorithmManage algorithmManage;
	private PackageAlgorithm packageAlgorithm;
	private APIKey apiKey;

	/**
	 * External exposure method, user request recommendation
	 * @param ecommerceName:The requesting user name
	 * @param apiKey:The requesting APIKey
	 * @param inputItems:User input commodity
	 * @param outputItemsNum:Recommended Items number
	 * @param outputQuantitye:Recommended Items Quantitye
	 * @return If ecommerceName or apiKey or inputItems is empty,or apiKey without permission, return null
	 */
	public String[] getRecommends(String ecommerceName,String apiKey,String algorithm,String inputItems,int outputItemsNum,int outputQuantitye) {
		if(!ecommerceManage.isHold(ecommerceName)){
			logger.info("The user does not exist");
			return null;
		}
		if(!exclusiveKeyManage.isHold(ecommerceName,apiKey)){
			logger.info("The user does not have the APIKey");
			return null;
		}
		if(exclusiveKeyManage.isActivation(apiKey)){
			algorithmManage.process(algorithm);
			algorithmManage.runIt();
	logger.info("inputItems:"+inputItems);
	logger.info("outputItemsNum"+outputItemsNum);
	logger.info("outputQuantitye:"+outputQuantitye);
			String[] mapItems = algorithmManage.getRecommend(inputItems,outputItemsNum,outputQuantitye);
			return mapItems;
		}
		return null;
	}
	
	/**
	 * External exposure method, the user request and returns a APIKey,
	 * and put it into the database
	 * @param websecommerce: User address or username
	 */
	public String getAPIKey(String ecommerceName) {
		ECommerce ecommerce =ecommerceManage.getECommerce(ecommerceName);
		if(ecommerce==null){
			return "User does not exist!";
		}
		StringBuffer keyString = apiKey.getAPIKey();
		boolean e = exclusiveKeyManage.isHold(keyString);
	logger.info(e);
		while(exclusiveKeyManage.isHold(keyString)){
			keyString = apiKey.getAPIKey();
		}
		exclusiveKeyManage.add(ecommerce,keyString);
		return keyString.toString();
	}
	
	/**
	 * External exposure method,the return of the user's APIKey state
	 * @param ecommerceName:The requesting user name
	 * @param apiKey:The requesting APIKey
	 * @return If the user does not exist then return to -1,if the user does not have the APIKey returns -2,
	 * 		If APIKey does not activate the return 1,If the APIKey is activated and can use return 2,
	 * 		If the APIKey is out of date return 3,If APIKey are forbidden to use return 4
	 */
	public String[] getAPIKeyState(String ecommerceName, String keyString) {
		ECommerce ecommerce =ecommerceManage.getECommerceAndScheme(ecommerceName);
		String[] state = new String[2];
		if(ecommerce==null){
			logger.info("The user does not exist");
			state[0] = "-1";
			return state;
		}
		ExclusiveKey ek = exclusiveKeyManage.getExclusiveKey(keyString);
		if(ek==null){
			state[0] = "-2";
			return state;
		}
		state[0] = String.valueOf(ek.getState());
		if(ek.getState()==2){
			state[1] = "";
			Set<Scheme> schemes = ecommerce.getSchemes();
			for(Scheme s : schemes.toArray(new Scheme[schemes.size()])){
				state[1] += s.getSchemeName()+",";
			}
			packageAlgorithm.packageA(ecommerce);
		}
		
		return state;
	}
	
	public ExclusiveKeyManage getExclusiveKeyManage() {
		return exclusiveKeyManage;
	}
	@Resource(name="exclusiveKeyManage")
	public void setExclusiveKeyManage(ExclusiveKeyManage exclusiveKeyManage) {
		this.exclusiveKeyManage = exclusiveKeyManage;
	}

	public ECommerceManage getECommerceManage() {
		return ecommerceManage;
	}
	@Resource(name="ecommerceManage")
	public void setECommerceManage(ECommerceManage ecommerceManage) {
		this.ecommerceManage = ecommerceManage;
	}


	public APIKey getApiKey() {
		return apiKey;
	}
	@Resource(name="apiKey")
	public void setApiKey(APIKey apiKey) {
		this.apiKey = apiKey;
	}

	public AlgorithmManage getAlgorithmManage() {
		return algorithmManage;
	}

	@Resource(name="algorithmManage")
	public void setAlgorithmManage(AlgorithmManage algorithmManage) {
		this.algorithmManage = algorithmManage;
	}

	public PackageAlgorithm getPackageAlgorithm() {
		return packageAlgorithm;
	}

	@Resource(name="packageAlgorithm")
	public void setPackageAlgorithm(PackageAlgorithm packageAlgorithm) {
		this.packageAlgorithm = packageAlgorithm;
	}



}
