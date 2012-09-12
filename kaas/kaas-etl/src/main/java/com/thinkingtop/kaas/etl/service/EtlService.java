package com.thinkingtop.kaas.etl.service;

import java.util.LinkedHashSet;
import java.util.List;

import com.thinkingtop.kaas.etl.model.KaasItem;

/**
 * interface EtlService
 *
 */
public interface EtlService {
	
	/**
	 * 
	 * According to the tableName and primary key name called "orderIdName", 
	 * this method could acquire a order list  
	 * @param tableName
	 * @param orderIdName
	 * @return
	 */
	public List<String> getOrderList(String tableName, String orderIdName);
	
	/**
	 * itemIdGroup means all the item id in one order.
	 * this method is provided for the mapping table which belongs many-to-many
	 * 
	 * @param orderId
	 * @param tableName
	 * @param itemIdName
	 * @param orderIdName
	 * @return
	 */
	public LinkedHashSet<String> getItemIdGroup(String orderId,String tableName, String itemIdName, String orderIdName);
	
	/**
	 *  itemIdGroup means all the item id in one order.
	 * this method is provided for the mapping table which belongs one-to-many, single column 
	 * 
	 * @param orderId
	 * @param tableName
	 * @param itemListName
	 * @param orderIdName
	 * @param separator
	 * @return
	 */
	public LinkedHashSet<String> getItemIdGroup(String orderId,String tableName, String itemListName,  String orderIdName,String separator);
	
	/**
	 *   itemIdGroup means all the item id in one order.
	 * this method is provided for the mapping table which belongs one-to-many, multi columns
	 * @param orderId
	 * @param tableName
	 * @param orderIdName
	 * @param prefix
	 * @param startId
	 * @param colunmCount
	 * @return
	 */
	public LinkedHashSet<String> getItemIdGroup(String orderId,String tableName, String orderIdName, String prefix, String startId, String colunmCount);
}
