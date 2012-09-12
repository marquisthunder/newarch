package com.thinkingtop.kaas.etl.util;

import java.util.List;

import com.thinkingtop.kaas.etl.reader.EtlInfoReader;
import com.thinkingtop.kaas.etl.service.EtlService;

public class ExtractUtil {
	
	private EtlService etlService;

	public EtlService getEtlService() {
		return etlService;
	}

	public void setEtlService(EtlService etlService) {
		this.etlService = etlService;
	}

}
