package com.thinkingtop.kaas.daemon.client;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class KaasDeamonAPIKeyValidator {
	private static KaasDeamonAPIKeyValidator validator=null;
	private static Client client;
	private KaasDeamonAPIKeyValidator() {}
	
	public static KaasDeamonAPIKeyValidator newInstance() { 
		if(validator == null) {
			validator = new KaasDeamonAPIKeyValidator();
			JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
			client = clientFactory.createClient("http://"+KaasDaemonPropertiesReader.getInstance().getProperty(
					"webServiceIP")+":8080/kaas-service/services/Service?wsdl");
		}
		return validator;
	}
	public int getAPIKeyState(String name, String key) {
		
		Object[] result=null;
		//Object s[] = {};
		try {
			result = client.invoke("GetAPIKeyState", name,key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ExclusiveKeyService temp = new E
		return (Integer)result[0];
	}
}
