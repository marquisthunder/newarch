package com.thinkingtop.kaas.services.algorithm.dao.implfile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import com.thinkingtop.kaas.services.algorithm.dao.FileHistoryDAO;
import com.thinkingtop.kaas.services.algorithm.util.AlgorithmProperties;

@Component("fileHistoryDAOFileImpl")
public class FileHistoryDAOFileImpl implements FileHistoryDAO {
	private  AlgorithmProperties algorithmProperties;
	
	@Resource(name="algorithmProperties")
	public void setAlgorithmProperties(AlgorithmProperties algorithmProperties) {
		this.algorithmProperties = algorithmProperties;
	}
	
	public List<String> getFileList() {
		List<String> fileLiest = new ArrayList<String>();
		String[] files = getFileString().split(getItemsDelimiter());
		for(String file : files){
			if(file==null&&file.equals("")){
				return null;
			}
			fileLiest.add(file);
		}
		return fileLiest;
	}

	public String getFileString() {
		return algorithmProperties.getDataFile();
	}

	public String getItemsDelimiter() {
		return algorithmProperties.getItemDelimiter();
	}

	public AlgorithmProperties getAlgorithmProperties() {
		return algorithmProperties;
	}
	

}
