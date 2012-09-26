package com.thinkingtop.kaas.services.dao.implfile;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.dao.KaasOrderFrequentDAO;
import com.thinkingtop.kaas.services.model.KaasOrderFrequent;
import com.thinkingtop.kaas.services.util.KaasDataPath;
import com.thinkingtop.kaas.services.util.OfConcurrentHashMap;

@Component("kaasOrderFrequentDAOFileImpl")
public class KaasOrderFrequentDAOFileImpl implements KaasOrderFrequentDAO {
    static Logger logger=Logger.getLogger(KaasOrderFrequentDAOFileImpl.class);
    private KaasDataPath kaasDataPath;
	private OfConcurrentHashMap<String,KaasOrderFrequent> fileAll;
	private String[] keys;
	public KaasOrderFrequentDAOFileImpl() {
		fileAll = new OfConcurrentHashMap<String, KaasOrderFrequent>();
	}
	public KaasOrderFrequent findOneByProperty(String freqSet, String myFreqSet) {
		return fileAll.get(myFreqSet);
	}

	public int submit(KaasOrderFrequent o) {
		try{
			fileAll.putIfAbsent(o.getCombination(), o);
		}catch (Exception e) {
			return 2;
		}
		return 1;
	}
	
	
	public int submit(){
		FileOutputStream fo = null;
		logger.info("fileAll.size : "+fileAll.size());
		try {
            fo = new FileOutputStream(kaasDataPath.getofDataPath(),false);
            boolean one =true;
            for(Map.Entry<String, KaasOrderFrequent> me: fileAll.entrySet()){
            	KaasOrderFrequent o = me.getValue();
            	String song="";
            	if(one){
            		one = false;
            		song = o.getCombination() + "==" + o.getFrequent() + "==" + o.getItemNum() + "==" + o.getOfType();
            		fo.write(song.getBytes());
            		fo = new FileOutputStream(kaasDataPath.getofDataPath(),true);
            	}else{
            		song = "\r\n"+o.getCombination() + "==" + o.getFrequent() + "==" + o.getItemNum() + "==" + o.getOfType();
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
	public OfConcurrentHashMap<String, KaasOrderFrequent> getFileAll() {
		return fileAll;
	}
	public void setFileAll(OfConcurrentHashMap<String, KaasOrderFrequent> fileAll) {
		this.fileAll = fileAll;
	}
	public void getKeys() {
		keys = fileAll.keySet().toArray(new String[0]);
	}
	public int size() {
		getKeys();
		return fileAll.size();
	}
	public KaasOrderFrequent getKeyMarsOrderFrequent(int i) {
		return fileAll.get(keys[i]);
	}
	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}
	@Resource(name="kaasDataPath")
	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}
}
