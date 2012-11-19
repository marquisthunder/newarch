package com.ecweb.kaasservice.manage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.schema.beans.KaasService;

import com.ecweb.dao.GoodDao;
import com.ecweb.model.Good;
import com.thinkingtop.kaasservice.ExclusiveKeyService;

public class KaasserviceManage {
	private static KaasService kaasService;
	private static ExclusiveKeyService eks;
	static{
		kaasService = new KaasService();
		eks = kaasService.getExclusiveKeyServicePort();
	}
	
	public static List<Good> getRecommends(String ecommerceName,String apiKey,String endUser,String scheme,String inputItems,int outputItemsNum,int outputQuantitye){
		List<String> recommend = eks.getRecommends(ecommerceName, apiKey, endUser, scheme, inputItems, outputItemsNum, outputQuantitye);
		List<Good> reGood = new ArrayList<Good>();
		int index = 0;
		for(String r : recommend){
			if (r == null || r.equals("")) {
				continue;
			}
			int id;
			try {
				String[] idstrs = r.split(",");
				//System.out.println("ids[0] = " + idstrs[0]);
				//System.out.println("ids.length = " + idstrs.length);
				for(String idstr : idstrs){
					id = Integer.parseInt(idstr);
					Good good = GoodDao.getGood(id);
					reGood.add(good);
				}
				index++;
				if(index == recommend.size()){
					continue;
				}
				reGood.add(new Good());
			} catch (Exception e) {
				System.out.println("ID input error");
			}
		}
		return reGood;
	}
}
