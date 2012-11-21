package com.thinkingtop.kaas.ecweb.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.ecweb.dao.GoodDAO;
import com.thinkingtop.kaas.ecweb.model.Good;

@Component("goodManage")
public class GoodManage {
	static Logger logger=Logger.getLogger(GoodManage.class);
	private GoodDAO gooddao;
	
	public Good getGood(int id){
		return gooddao.getGood(id);
	}
	
	public List<Good> getRecommends(List<String>  recommend){
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


}
