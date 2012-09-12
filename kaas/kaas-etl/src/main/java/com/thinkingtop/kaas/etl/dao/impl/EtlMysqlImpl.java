package com.thinkingtop.kaas.etl.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.thinkingtop.kaas.etl.dao.EtlDAO;
import com.thinkingtop.kaas.etl.model.KaasItem;
import com.thinkingtop.kaas.etl.validator.ValidateXML;

public class EtlMysqlImpl implements EtlDAO {
 
	private static final Logger logger = LoggerFactory.getLogger(ValidateXML.class.getName());
	
	/*JdbcTemplate*/
	private JdbcTemplate jdbcTemplate;

	/* set to inject the proxy class of jdbcTemplate*/
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	static int temp=1;

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getOrderList(String tableName, final String orderIdName) {
		List<Integer> idList = new ArrayList<Integer>();
		idList = (List<Integer>)jdbcTemplate.query("select DISTINCT "+orderIdName+" from "+tableName, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Integer i_temp = new Integer(rs.getInt(orderIdName));
				return i_temp;
			}
		});
		return idList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getItemIdGroup(Integer orderId, String tableName, final String itemIdName, String orderIdName) {
		List<Integer> goodsIdList = new ArrayList<Integer>();
		System.out.println("select "+itemIdName+" from "+tableName+" where "+orderIdName+" = "+orderId);
		goodsIdList = (List<Integer>)jdbcTemplate.query("select "+itemIdName+" from "+tableName+" where "+orderIdName+" = "+orderId,  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Integer i_temp = new Integer(rs.getInt(itemIdName));
				return i_temp;
			}
		});
		return goodsIdList;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getItemIdGroup(Integer orderId, String tableName, final String itemListName, String orderIdName, String separator) {
		List<Integer> goodsIdList = new ArrayList<Integer>();
		String goodsId_str = (String)jdbcTemplate.queryForObject("select "+itemListName+" from "+tableName+" where "+orderIdName+" = "+orderId,  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String str = rs.getString(itemListName);
					
				return str;
			}
		});
		
		String str_array[] = goodsId_str.split(separator);
		for(int i=0;i<str_array.length;i++) {
			goodsIdList.add(Integer.parseInt(str_array[i]));
		}
		return goodsIdList;
		
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getItemIdGroup(Integer orderId, String tableName, String orderIdName, final String prefix, String startId, String colunmCount) {
		List<Integer> goodsIdList = new ArrayList<Integer>();
		final int origin = Integer.parseInt(startId);
		int start = new Integer(origin);
		StringBuilder sb = new StringBuilder();
		final int length = Integer.parseInt(colunmCount);
		sb.append(prefix+startId);
		for(int i=0;i<length-1;i++) {
			start++;
			sb.append(","+prefix+start);
		}
		System.out.println("select "+sb.toString()+" from "+tableName+" where "+orderIdName+" = "+orderId);
		goodsIdList = (List<Integer>)jdbcTemplate.queryForObject("select "+sb.toString()+" from "+tableName+" where "+orderIdName+" = "+orderId,  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				List<Integer> list = new ArrayList<Integer>();
				int start = origin;
				for(int i=0;i<length;i++) {
					
					Integer temp = new Integer(rs.getInt(prefix+start));
					start++;
					
				
					list.add(temp);
					}
			System.out.println("-------------------------------------------------------------------------");
					
				return list;
			}
		});
		
		return goodsIdList;
		
	}

	
	
}