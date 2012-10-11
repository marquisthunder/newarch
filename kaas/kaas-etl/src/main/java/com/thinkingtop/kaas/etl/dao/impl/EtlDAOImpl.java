package com.thinkingtop.kaas.etl.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.thinkingtop.kaas.etl.dao.EtlDAO;
import com.thinkingtop.kaas.etl.model.KaasItem;
import com.thinkingtop.kaas.etl.validator.ValidateXML;

public class EtlDAOImpl implements EtlDAO {
 
	private static final Logger logger = LoggerFactory.getLogger(EtlDAOImpl.class.getName());
	
	/*JdbcTemplate*/
	private JdbcTemplate jdbcTemplate;

	/* set to inject the proxy class of jdbcTemplate*/
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getOrderList(String tableName, final String orderIdName) {
		List<String> idList = new ArrayList<String>();
		idList = (List<String>)jdbcTemplate.query("select DISTINCT "+orderIdName+" from "+tableName, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String s_temp = rs.getString(orderIdName);
				return s_temp;
			}
		});
		return idList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public LinkedHashSet<String> getItemIdGroup(String orderId, String tableName, final String itemIdName, String orderIdName) {
		LinkedHashSet<String> itemIdSet = new LinkedHashSet<String>();
		logger.info("select "+itemIdName+" from "+tableName+" where "+orderIdName+" = "+orderId);
		itemIdSet = (LinkedHashSet<String>)jdbcTemplate.query("select "+itemIdName+" from "+tableName+" where "+orderIdName+" = "+orderId,  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String s_temp = rs.getString(itemIdName);
				return s_temp;
			}
		});
		return itemIdSet;
	}


	@Override
	@SuppressWarnings("unchecked")
	public LinkedHashSet<String> getItemIdGroup(String orderId, String tableName, final String itemListName, String orderIdName, String separator) {
		LinkedHashSet<String> itemIdSet = new LinkedHashSet<String>();
		String goodsId_str = (String)jdbcTemplate.queryForObject("select "+itemListName+" from "+tableName+" where "+orderIdName+" = "+orderId,  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String str = rs.getString(itemListName);
					
				return str;
			}
		});
		
		String str_array[] = goodsId_str.split(separator);
		for(int i=0;i<str_array.length;i++) {
			itemIdSet.add(String.valueOf(str_array[i]));
		}
		return itemIdSet;
		
	}


	@Override
	@SuppressWarnings("unchecked")
	public LinkedHashSet<String> getItemIdGroup(String orderId, String tableName, String orderIdName, final String prefix, String startId, String colunmCount) {
		LinkedHashSet<String> itemIdSet = new LinkedHashSet<String>();
		final int origin = Integer.parseInt(startId);
		int start = new Integer(origin);
		StringBuilder sb = new StringBuilder();
		final int length = Integer.parseInt(colunmCount);
		sb.append(prefix+startId);
		for(int i=0;i<length-1;i++) {
			start++;
			sb.append(","+prefix+start);
		}
		logger.info("select "+sb.toString()+" from "+tableName+" where "+orderIdName+" = "+orderId);
		itemIdSet = (LinkedHashSet<String>)jdbcTemplate.queryForObject("select "+sb.toString()+" from "+tableName+" where "+orderIdName+" = "+orderId,  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LinkedHashSet<String> list = new LinkedHashSet<String>();
				int start = origin;
				for(int i=0;i<length;i++) {
					
					String temp = rs.getString(prefix+start);
					start++;
				
					list.add(temp);
					}
				return list;
			}
		});
		
		return itemIdSet;
		
	}

	
	
}