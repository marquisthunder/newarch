package com.thinkingtop.kaas.services.service;

import java.util.List;

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
@WebService(targetNamespace="kaasservice")
public interface ExclusiveKeyService {
	/**
	 * External exposure method, creates and returns a APIKey, and put it into the database
	 * @param ecommerceName:Request APIKey username
	 */
	@WebMethod(operationName="GetAPIKey")
	@WebResult(name = "result")
	public String getAPIKey(@WebParam(name = "ecommerceName")String ecommerceName);
	
	
	/**
	 * External exposure method, user request recommendation
	 * @param ecommerceName:The requesting user name
	 * @param keyString:The requesting APIKey
	 * @param endUser:The need for the user
	 * @param scheme:The recommended scheme
	 * @param inputItems:User input commodity
	 * @param outputItemsNum:Recommended Items number
	 * @param outputQuantitye:Recommended Items Quantitye
	 * @return If ecommerceName or apiKey or inputItems is empty,or apiKey without permission, return null
	 */
	@WebMethod(operationName="GetRecommends")
	@WebResult(name = "result")
	public String[] getRecommends(@WebParam(name = "ecommerceName")String ecommerceName,
			@WebParam(name = "KeyString")String keyString,
			@WebParam(name = "endUser")String endUser,
			@WebParam(name = "scheme")String scheme,
			@WebParam(name = "inputItems")String inputItems,
			@WebParam(name = "outputItemsNum")int outputItemsNum,
			@WebParam(name = "outputQuantitye")int outputQuantitye);
	
	/**
	 * External exposure method,the return of the user's APIKey state
	 * @param ecommerceName:The requesting user name
	 * @param keyString:The requesting APIKey
	 * @return If the user does not exist then return to -1,if the user does not have the APIKey returns -2,
	 * 		If APIKey does not activate the return 1,If the APIKey is activated and can use return 2,
	 * 		If the APIKey is out of date return 3,If APIKey are forbidden to use return 4
	 */
	@WebMethod(operationName="GetAPIKeyState")
	@WebResult(name = "result")
	public List<String> getAPIKeyState(@WebParam(name = "ecommerceName")String ecommerceName,
			@WebParam(name = "KeyString")String keyString);
	
	/**
	 * External exposure method,the return of the user's APIKey state
	 * @param ecommerceName:The requesting user name
	 * @param keyString:The requesting APIKey
	 * @return If the user does not exist then return to -1,if the user does not have the APIKey returns -2,
	 * 		If APIKey does not activate the return 1,If the APIKey is activated and can use return 2,
	 * 		If the APIKey is out of date return 3,If APIKey are forbidden to use return 4
	 */
	@WebMethod(operationName="GetState")
	@WebResult(name = "result")
	public int getState(@WebParam(name = "ecommerceName")String ecommerceName,
			@WebParam(name = "KeyString")String keyString);
	
	
	@WebMethod(operationName="Test")
	@WebResult(name = "result")
	public String[] getTest(@WebParam(name = "testString")String testString);
}
