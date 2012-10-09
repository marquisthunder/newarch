package com.thinkingtop.kaas.services.algorithm.dao.implfile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.dao.KaasRuleDAO;
import com.thinkingtop.kaas.services.algorithm.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.algorithm.model.KaasRule;
import com.thinkingtop.kaas.services.util.KaasDataPath;

@Component("kaasRuleDAOFileImpl")
public class KaasRuleDAOFileImpl implements KaasRuleDAO {
    static Logger logger=Logger.getLogger(KaasRuleDAOFileImpl.class);
	private String itemDelimiter;
    private KaasDataPath kaasDataPath;
	private Map<String,KaasRule> marsRuleAll;
	public KaasRuleDAOFileImpl(){
		marsRuleAll = new ConcurrentHashMap<String, KaasRule>();
	}
	
	public int submit(KaasRule r) {
		try{
			String rpr = r.getProducts()+"|"+r.getRecommendation();
			marsRuleAll.put(rpr, r);
		}catch (Exception e) {
			return 2;
		}
		return 1;
	}
	public int submit() {
		FileOutputStream fo = null;
		try {
            fo = new FileOutputStream(kaasDataPath.getRDataPath(),false);
            logger.info("kaasDataOutPath:-"+ kaasDataPath.getRDataPath());
            logger.info("marsRuleAll.size:" + marsRuleAll.size());
            boolean one =true;
            for(Entry<String, KaasRule> me: marsRuleAll.entrySet()){
            	KaasRule o = me.getValue();
            	String song="";
            	if(one){
            		one = false;
            		song = o.getProducts() + "==" + o.getRecommendation() + "==" + o.getConfidence() + "==" + o.getFlag();
            		fo.write(song.getBytes());
            		fo = new FileOutputStream(kaasDataPath.getRDataPath(),true);
            	}else{
            		song = "\r\n" + o.getProducts() + "==" + o.getRecommendation() + "==" + o.getConfidence() + "==" + o.getFlag();
            		fo.write(song.getBytes());
            	}
            	//logger.info("FileOutputStream:-"+ song);
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
	public List<KaasRule> getRule(String string) {
		List<KaasRule> marsRuleList = new ArrayList<KaasRule>();
		Set<String> mra = marsRuleAll.keySet();
		for(String s : mra){
			if(s.startsWith(string+"|")){
				marsRuleList.add(marsRuleAll.get(s));
			}
		}
		return marsRuleList;
	}
	
	public Map<String,Integer> getRuleMap(String string) {
		Map<String,Integer> marsRuleList = new HashMap<String,Integer>();
		Set<String> mra = marsRuleAll.keySet();
		for(String s : mra){
			if(s.startsWith(string+"|")){
				marsRuleList.put(s,0);
			}
		}
		return marsRuleList;
	}

	public Map<String, KaasRule> getMarsRuleAll() {
		return marsRuleAll;
	}

	public void setMarsRuleAll(Map<String, KaasRule> marsRuleAll) {
		this.marsRuleAll = marsRuleAll;
	}

	public String[] getRuleMap(String inputItems, int outputItemsNum,int outputQuantitye) {
		HashMap<String,Double> marsRuleList = new HashMap<String,Double>();
		if(outputQuantitye<=0){
			if(outputItemsNum<=0){
				outputItemsNum = 1;
			}
			outputQuantitye=1;
			for(Map.Entry<String, KaasRule> me : marsRuleAll.entrySet()){
				String[] bg = me.getKey().split("\\|");
				if(bg[0].equals(inputItems)&&(bg[1].split(itemDelimiter).length==outputItemsNum)){
					marsRuleList.put(bg[1],me.getValue().getConfidence());
				}
			}
			
		}else{
			if(outputItemsNum<=0){
				for(Map.Entry<String, KaasRule> me : marsRuleAll.entrySet()){
					String[] bg = me.getKey().split("\\|");
					if(bg[0].equals(inputItems)){
						marsRuleList.put(bg[1],me.getValue().getConfidence());
					}
				}
			}else{
				for(Map.Entry<String, KaasRule> me : marsRuleAll.entrySet()){
					String[] bg = me.getKey().split("\\|");
					if(bg[0].equals(inputItems)&&(bg[1].split(itemDelimiter).length==outputItemsNum)){
						marsRuleList.put(bg[1],me.getValue().getConfidence());
					}
				}
			}
		}
		ArrayList<Map.Entry<String, Double>> list = sortMap(marsRuleList);
		String[] item = new String[outputQuantitye];
		for(int i=0;i<outputQuantitye&&i<list.size();i++){
			item[i] = list.get(i).getKey();
		}
		return item;
	}
	public String[] getRuleMap(String inputItems, int outputItemsNum) {
		HashMap<String,Double> marsRuleList = new HashMap<String,Double>();
		for(Map.Entry<String, KaasRule> me : marsRuleAll.entrySet()){
			String[] bg = me.getKey().split("\\|");
			if(bg[0].equals(inputItems)&&(bg[1].split(itemDelimiter).length==outputItemsNum)){
				marsRuleList.put(bg[1],me.getValue().getConfidence());
			}
		}
		ArrayList<Map.Entry<String, Double>> list = sortMap(marsRuleList);
		String[] item = {list.get(0).getKey()};
		return item;
	}
	
	public ArrayList<Map.Entry<String, Double>> sortMap(Map map){
		 ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(map.entrySet());
		 Collections.sort(list,new Comparator<Map.Entry<String, Double>>(){
			 public int compare(Entry<java.lang.String, Double> arg0,
			 Entry<java.lang.String, Double> arg1) {
				 double o1 = arg0.getValue();
				 double o2 = arg1.getValue();
				 if(o1<o2) return 1;
					else if(o1==o2) return 0;
					else return -1;
			 }
		 });
		 return list;
	}
	

	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}

	@Resource(name="kaasDataPath")
	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}

	public String getItemDelimiter() {
		return itemDelimiter;
	}

	@Value("${algorithm.itemDelimiter}")
	public void setItemDelimiter(String itemDelimiter) {
		this.itemDelimiter = itemDelimiter;
	}




}
