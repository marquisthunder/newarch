package com.thinkingtop.kaas.etl.result;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.thinkingtop.kaas.etl.reader.EtlInfoReader;
import com.thinkingtop.kaas.etl.service.EtlService;
import com.thinkingtop.kaas.etl.validator.ValidateXML;

public class Result{

	private static final Logger logger = LoggerFactory.getLogger(ValidateXML.class.getName());
	
	public void getResult() {
		ValidateXML v = new ValidateXML();
		//validate the xml with xsd first...
		if(!v.validate()) {
			return;
		}
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		EtlService etlService = (EtlService) ac.getBean("etlService");
		getItemsList(etlService);
	}
	
	public List<String> getItemsList(EtlService etlService) {
		EtlInfoReader reader = EtlInfoReader.getInstance();
		Map<String, String> map = (Map<String, String>)reader.getMappings();
		logger.info("{}",map);
		/** many to many(relation0)
		 {mapping_table_name=ecs_order_goods, 
		 relation_name=many-to-many, 
		goods_id=goods_id,
		 order_id=order_id
		 mapping_table_id=rec_id}
		 */
		String relation_name = map.get("relation_name");
		
		if(relation_name.equals("many-to-many")) {
			logger.info("this is a many-to-many mapping");
			String itemIdName =  map.get("goods_id");
			String orderIdName = map.get("order_id"); 
			String mappingTableName = map.get("mapping_table_name");
		
			List<Integer> list = etlService.getOrderList(mappingTableName, orderIdName);
			logger.info("order::{}"+list);
			//transformerService.getOrderListByFieldName(name)
			for(int i=0;i<list.size();i++) {
				logger.info("order_"+list.get(i)+"'s goods list::");
				logger.info(" {} ",etlService.getItemIdGroup(list.get(i), mappingTableName, itemIdName, orderIdName));
			}
		}
		else {
			String singleOrMulti_name = map.get("singleOrMulti_name");
			if(singleOrMulti_name.equals("singleColumn")) {
				logger.info("this is a one-to-many mapping including single column");
				/**
				 one to many(relation1 singleColumn)
				{mapping_table_name=ecs_order, 
				relation_name=one-to-many, 
				goods_list=goods_list, 
				singleOrMulti_name=singleColumn, 
				mapping_table_id=order_id,
				separator=#}
				 */
				String mappingTableName = map.get("mapping_table_name");
				String itemListName = map.get("goods_list");
				String mappingTableId = map.get("mapping_table_id");
				String separator = map.get("separator");

				List<Integer> list = etlService.getOrderList(mappingTableName, mappingTableId);
				logger.info("order::"+list);
				for(int i=0;i<list.size();i++) {
					logger.info("order_"+list.get(i)+"'s goods list::");
					logger.info(" {} ",etlService.getItemIdGroup(list.get(i), mappingTableName, itemListName, mappingTableId, separator));
				}
			}
			else {
				logger.info("this is a one-to-many mapping including mutil columns");
				/**
				 one to many(relation1 mutilColumns)
				 {startid=1,
				  mapping_table_name=ecs_order, 
				  columnCount=5, 
				  relation_name=one-to-many, 
				  prefix=goods_id, 
				  singleOrMulti_name=mutilColumns, 
				  mapping_table_id=order_id}
				 */
				String mappingTableName = map.get("mapping_table_name");
				String mappingTableId = map.get("mapping_table_id");
				String prefix = map.get("prefix");
				String columnCount = map.get("columnCount");
				String startId = map.get("startid");
				
				List<Integer> list = etlService.getOrderList(mappingTableName, mappingTableId);
				logger.info("order::"+list);
				logger.info("result:::::::::::::::::::::::::");
				for(int i=0;i<list.size();i++) {
					logger.info("order_"+list.get(i)+"'s goods list::");
					logger.info(" {} ",etlService.getItemIdGroup(list.get(i), mappingTableName, mappingTableId, prefix, startId, columnCount));
				}
				
			}
		}
		return null;
	}
	
}
