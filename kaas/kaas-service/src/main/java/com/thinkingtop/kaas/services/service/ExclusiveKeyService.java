package com.thinkingtop.kaas.services.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.thinkingtop.kaas.services.model.ExclusiveKey;
/**
 * This is a ExclusiveKeyService Abstract class
 * External exposure method, allows users to call
 * @author roadahead
 *
 */
@WebService
public interface ExclusiveKeyService {
	/**
	 * External exposure method, creates and returns a APIKey, and put it into the database
	 * @param kebsiteName:Request APIKey username
	 */
	@WebMethod(operationName="GetAPIKey")
	@WebResult(name = "result")
	public String getAPIKey(@WebParam(name = "kebsiteName")String kebsiteName);
	
	/**
	 * External exposure method, user request recommendation
	 * @param kebsiteName:The requesting user name
	 * @param apiKey:The requesting APIKey
	 * @param inputItems:User input commodity
	 * @param outputItemsNum:Recommended Items number
	 * @param outputQuantitye:Recommended Items Quantitye
	 * @return If kebsiteName or apiKey or inputItems is empty,or apiKey without permission, return null
	 */
	@WebMethod(operationName="GetRecommends")
	@WebResult(name = "result")
	public String[] getRecommends(@WebParam(name = "kebsiteName")String kebsiteName,
			@WebParam(name = "apiKey")String apiKey,
			@WebParam(name = "inputItems")String inputItems,
			@WebParam(name = "outputItemsNum")int outputItemsNum,
			@WebParam(name = "outputQuantitye")int outputQuantitye);
}
