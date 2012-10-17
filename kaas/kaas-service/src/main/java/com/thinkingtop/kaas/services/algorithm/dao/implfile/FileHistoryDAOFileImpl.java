package com.thinkingtop.kaas.services.algorithm.dao.implfile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.thinkingtop.kaas.services.algorithm.dao.FileHistoryDAO;

@Component("fileHistoryDAOFileImpl")
public class FileHistoryDAOFileImpl implements FileHistoryDAO {
	private String itemsDelimiter;
	private String fileString;
	public List<String> getFileList() {
		List<String> fileLiest = new ArrayList<String>();
		String[] files = fileString.split(itemsDelimiter);
		for(String file : files){
			if(file==null&&file.equals("")){
				return null;
			}
			fileLiest.add(file);
		}
		return fileLiest;
	}

	public String getFileString() {
		return fileString;
	}

	@Value("${algorithm.dataFile}")
	public void setFileString(String fileString) {
		this.fileString = fileString;
	}

	public String getItemsDelimiter() {
		return itemsDelimiter;
	}
	
	@Value("${algorithm.itemDelimiter}")
	public void setItemsDelimiter(String itemsDelimiter) {
		this.itemsDelimiter = itemsDelimiter;
	}

}
