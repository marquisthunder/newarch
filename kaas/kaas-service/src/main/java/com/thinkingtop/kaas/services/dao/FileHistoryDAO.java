package com.thinkingtop.kaas.services.dao;

import java.util.List;
public interface FileHistoryDAO {
	List<String> getFileList(String string, int i, int j);
	void updateFlag(List<String> filelist, String string, int i);
}
