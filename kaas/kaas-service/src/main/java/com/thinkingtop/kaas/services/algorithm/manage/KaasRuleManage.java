package com.thinkingtop.kaas.services.algorithm.manage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.algorithm.model.KaasRule;
import com.thinkingtop.kaas.services.algorithm.util.KaasDataPath;

@Component("kaasRuleManage")
public class KaasRuleManage {
	static Logger logger=Logger.getLogger(KaasRuleManage.class);
	private KaasRuleDAO kaasRuleDAO;
	private KaasDataPath kaasDataPath;
	
	public int add(KaasRule r){
		KaasRule rule = kaasRuleDAO.getRule(r.getProducts(), r.getRecommendation());
		if(rule!=null){
			logger.info("update-------");
			rule.setConfidence(r.getConfidence());
			logger.info("r id : --" + r.getId());
			logger.info("r Products : --" + r.getProducts());
			logger.info("r Recommendation : --" + r.getRecommendation());
			return kaasRuleDAO.update(rule);
		}
		return kaasRuleDAO.submit(r);
	}

	public List<String> getRules(String products){
		List<String> marsRuleList = new ArrayList<String>();
		List<KaasRule> rules = kaasRuleDAO.getRules(products);
		for(KaasRule ku : rules){
			marsRuleList.add(ku.getProducts()+"|"+ku.getRecommendation());
		}
		return marsRuleList;
	}
	
	public int submit(String dataName){
		Iterator<KaasRule> kri = kaasRuleDAO.getRuleIterator();
		logger.info("------------------submit run ------------------");
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(kaasDataPath.getRDataPath()+"/" +dataName,false);
			boolean one =true;
			while(kri!=null&&kri.hasNext()){
				KaasRule kr= kri.next();
				logger.info("kaasDataOutPath:-"+ kaasDataPath.getRDataPath()+"/" +dataName);
				logger.info("-------------------");
				logger.info("kaasRule--:id-"+kr.getId());
				logger.info("kaasRule--:Products-"+kr.getProducts());
				logger.info("kaasRule--:Recommendation-"+kr.getRecommendation());
				String song="";
            	if(one){
            		one = false;
            		song = kr.getProducts() + "==" + kr.getRecommendation() + "==" + kr.getConfidence() + "==" + kr.getFlag();
            		fo.write(song.getBytes());
            		fo = new FileOutputStream(kaasDataPath.getRDataPath()+"/" + dataName,true);
            	}else{
            		song = "\r\n" + kr.getProducts() + "==" + kr.getRecommendation() + "==" + kr.getConfidence() + "==" + kr.getFlag();
            		fo.write(song.getBytes());
            	}
			}
        } catch (Exception h) {
        	return 2;
        }finally{
        	try {
        		if(fo!=null)
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return 1;
	}

	
	public boolean isHold(String products,String recommendation){
		return kaasRuleDAO.isHold(products, recommendation);
	}
	
	public int update(KaasRule r){
		return kaasRuleDAO.update(r);
	}
	
	public void deleteAll(){
		kaasRuleDAO.clearMarsRuleAll();
	}
	
	public KaasRule getRule(int id){
		return kaasRuleDAO.getRule(id);
	}
	
	public KaasRuleDAO getKaasRuleDAO() {
		return kaasRuleDAO;
	}

	@Resource(name="kaasRuleDAOImpl")
	public void setKaasRuleDAO(KaasRuleDAO kaasRuleDAO) {
		this.kaasRuleDAO = kaasRuleDAO;
	}
	
	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}

	@Resource(name="kaasDataPath")
	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}

}
