package com.thinkingtop.kaas.etl.service.impl;

import java.util.List;

import com.thinkingtop.kaas.etl.dao.EtlDAO;
import com.thinkingtop.kaas.etl.model.KaasItem;
import com.thinkingtop.kaas.etl.service.EtlService;

/**
 * Hello world!
 *
 */
public class EtlServiceImpl implements EtlService {

	public EtlDAO etlDAO;

	public EtlDAO getEtlDAO() {
		return etlDAO;
	}

	public void setEtlDAO(EtlDAO transformerDAO) {
		this.etlDAO = transformerDAO;
	}

	@Override
	public List<Integer> getOrderList(String tableName, String orderIdName) {
		return etlDAO.getOrderList(tableName, orderIdName);
	}

	@Override
	public List<Integer> getItemIdGroup(Integer orderId, String tableName,
			String itemIdName, String orderIdName) {
		return etlDAO.getItemIdGroup(orderId, tableName, itemIdName, orderIdName);
	}

	@Override
	public List<Integer> getItemIdGroup(Integer orderId, String tableName,
			String itemListName, String orderIdName, String separator) {
		return etlDAO.getItemIdGroup(orderId, tableName, itemListName, orderIdName, separator);
	}

	@Override
	public List<Integer> getItemIdGroup(Integer orderId, String tableName,
			String orderIdName, String prefix, String startId,
			String colunmCount) {
		return etlDAO.getItemIdGroup(orderId, tableName, orderIdName, prefix, startId, colunmCount);
	}

}
