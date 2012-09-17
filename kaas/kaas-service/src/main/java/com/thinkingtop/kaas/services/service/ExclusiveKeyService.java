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
	 * 对外暴露的方法，创建并返回一个APIKey，同时将其存进数据库中
	 */
	@WebMethod(operationName="GetAPIKey")
	@WebResult(name = "result")
	public String getAPIKey(@WebParam(name = "kebsiteName")String kebsiteName);
	
	@WebMethod(operationName="GetRecommends")
	@WebResult(name = "result")
	public String getRecommends(@WebParam(name = "kebsiteName")String kebsiteName,
			@WebParam(name = "apiKey")String apiKey,
			@WebParam(name = "inputItems")String inputItems,
			@WebParam(name = "outputItemsNum")int outputItemsNum,
			@WebParam(name = "outputQuantitye")int outputQuantitye);
}
