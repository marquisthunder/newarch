package com.thinkingtop.kaasservice;

import java.util.List;
import java.util.logging.Logger;

public class KaasServiceManage {
	private static Logger logger = Logger.getLogger(KaasServiceManage.class
			.toString());
	private static KaasService kaasService;

	public static KaasService getKaasService() {
		if (kaasService == null) {
			try {
				kaasService = new KaasService();
				logger.info("webservice Connection successful");
			} catch (Exception e) {
				logger.info("webservice Connection failure");
				kaasService = null;
			}
		}
		return kaasService;
	}

	public ExclusiveKeyService getExclusiveKeyService() {
		if (kaasService == null) {
			getKaasService();
		}
		if (kaasService == null) {
			logger.info("webservice has closed");
			return null;
		}
		ExclusiveKeyService eks;
		try {
			eks = kaasService.getExclusiveKeyServicePort();
		} catch (Exception e) {
			logger.info("webservice has closed");
			return null;
		}
		return eks;
	}

	public static List<String> getRecommends(String ecommerceName,
			String apiKey, String endUser, String scheme, String inputItems,
			int outputItemsNum, int outputQuantitye) {
		if (kaasService == null) {
			getKaasService();
		}
		if (kaasService == null) {
			logger.info("webservice has closed");
			return null;
		}
		ExclusiveKeyService eks;
		List<String> recommend = null;
		try {
			eks = kaasService.getExclusiveKeyServicePort();
			recommend = eks.getRecommends(ecommerceName, apiKey, endUser,
					scheme, inputItems, outputItemsNum, outputQuantitye);
		} catch (Exception e) {
			logger.info("webservice has closed");
			return null;
		}
		return recommend;
	}

	public static int getState(String ecommerceName, String keyString) {
		if (kaasService == null) {
			getKaasService();
		}
		if (kaasService == null) {
			logger.info("webservice has closed");
			return -3;
		}
		ExclusiveKeyService eks;
		int state = -3;
		try {
			eks = kaasService.getExclusiveKeyServicePort();
			state = eks.getState(ecommerceName, keyString);
		} catch (Exception e) {
			logger.info("webservice has closed");
			return -3;
		}
		return state;
	}
}
