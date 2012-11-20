package com.thinkingtop.kaas.ecweb.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.ecweb.dao.GoodDAO;
import com.thinkingtop.kaas.ecweb.model.Good;
import com.thinkingtop.kaasservice.ExclusiveKeyService;
import com.thinkingtop.kaasservice.KaasService;

@Component("goodManage")
public class GoodManage {
	static Logger logger=Logger.getLogger(GoodManage.class);
	private GoodDAO gooddao;
	private static KaasService kaasService;
	public static GoodManage goodManage;
	static{
		ClassPathXmlApplicationContext acx = new ClassPathXmlApplicationContext("beans.xml");
		goodManage = (GoodManage)acx.getBean("goodManage");
	}
	
	public static void main(String[] args) {
		GoodManage g = new GoodManage();
		System.out.println("adf");
	}
	
	public static GoodManage getGoodManage(){
		return goodManage;
	}
	
	public Good getGood(int id){
		return gooddao.getGood(id);
	}
	
	public List<Good> getRecommends(String ecommerceName,String apiKey,String endUser,String scheme,String inputItems,int outputItemsNum,int outputQuantitye){
		ExclusiveKeyService eks;
		try{
			eks = kaasService.getExclusiveKeyServicePort();
		}catch(Exception e){
			logger.info("webservice is close");
			return null;
		}
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
					Good good = gooddao.getGood(id);
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
	
	public GoodDAO getGooddao() {
		return gooddao;
	}
	@Resource(name="goodDAO")
	public void setGooddao(GoodDAO gooddao) {
		this.gooddao = gooddao;
	}

	public KaasService getKaasService() {
		return kaasService;
	}
	@Resource(name="kaasService")
	public void setKaasService(KaasService kaasService) {
		this.kaasService = kaasService;
	}

}
