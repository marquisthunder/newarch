package com.thinkingtop.kaasservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

import org.springframework.stereotype.Component;

/**
 * This class was generated by Apache CXF 2.6.1
 * 2012-11-19T14:45:10.864+08:00
 * Generated source version: 2.6.1
 * 
 */
@Component("kaasService")
@WebServiceClient(name = "kaasService", 
                  wsdlLocation = "http://192.168.0.101:8080/kaas-service/services/Service?wsdl",
                  targetNamespace = "http://www.thinkingtop.com/kaasservice") 
public class KaasService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.thinkingtop.com/kaasservice", "kaasService");
    public final static QName ExclusiveKeyServicePort = new QName("http://www.thinkingtop.com/kaasservice", "ExclusiveKeyServicePort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.0.101:8080/kaas-service/services/Service?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(KaasService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.0.101:8080/kaas-service/services/Service?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public KaasService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public KaasService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public KaasService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public KaasService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public KaasService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public KaasService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }

    /**
     *
     * @return
     *     returns ExclusiveKeyService
     */
    @WebEndpoint(name = "ExclusiveKeyServicePort")
    public ExclusiveKeyService getExclusiveKeyServicePort() {
        return super.getPort(ExclusiveKeyServicePort, ExclusiveKeyService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ExclusiveKeyService
     */
    @WebEndpoint(name = "ExclusiveKeyServicePort")
    public ExclusiveKeyService getExclusiveKeyServicePort(WebServiceFeature... features) {
        return super.getPort(ExclusiveKeyServicePort, ExclusiveKeyService.class, features);
    }

}
