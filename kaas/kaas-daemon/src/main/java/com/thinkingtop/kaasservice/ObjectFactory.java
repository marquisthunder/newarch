
package com.thinkingtop.kaasservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.thinkingtop.kaasservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAPIKeyState_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "GetAPIKeyState");
    private final static QName _GetAPIKeyStateResponse_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "GetAPIKeyStateResponse");
    private final static QName _TestResponse_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "TestResponse");
    private final static QName _GetAPIKeyResponse_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "GetAPIKeyResponse");
    private final static QName _GetAPIKey_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "GetAPIKey");
    private final static QName _GetStateResponse_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "GetStateResponse");
    private final static QName _GetState_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "GetState");
    private final static QName _GetRecommends_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "GetRecommends");
    private final static QName _GetRecommendsResponse_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "GetRecommendsResponse");
    private final static QName _Test_QNAME = new QName("http://www.thinkingtop.com/kaasservice", "Test");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.thinkingtop.kaasservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAPIKey }
     * 
     */
    public GetAPIKey createGetAPIKey() {
        return new GetAPIKey();
    }

    /**
     * Create an instance of {@link GetRecommendsResponse }
     * 
     */
    public GetRecommendsResponse createGetRecommendsResponse() {
        return new GetRecommendsResponse();
    }

    /**
     * Create an instance of {@link GetAPIKeyResponse }
     * 
     */
    public GetAPIKeyResponse createGetAPIKeyResponse() {
        return new GetAPIKeyResponse();
    }

    /**
     * Create an instance of {@link GetStateResponse }
     * 
     */
    public GetStateResponse createGetStateResponse() {
        return new GetStateResponse();
    }

    /**
     * Create an instance of {@link GetAPIKeyState }
     * 
     */
    public GetAPIKeyState createGetAPIKeyState() {
        return new GetAPIKeyState();
    }

    /**
     * Create an instance of {@link GetAPIKeyStateResponse }
     * 
     */
    public GetAPIKeyStateResponse createGetAPIKeyStateResponse() {
        return new GetAPIKeyStateResponse();
    }

    /**
     * Create an instance of {@link GetRecommends }
     * 
     */
    public GetRecommends createGetRecommends() {
        return new GetRecommends();
    }

    /**
     * Create an instance of {@link Test }
     * 
     */
    public Test createTest() {
        return new Test();
    }

    /**
     * Create an instance of {@link GetState }
     * 
     */
    public GetState createGetState() {
        return new GetState();
    }

    /**
     * Create an instance of {@link TestResponse }
     * 
     */
    public TestResponse createTestResponse() {
        return new TestResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAPIKeyState }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "GetAPIKeyState")
    public JAXBElement<GetAPIKeyState> createGetAPIKeyState(GetAPIKeyState value) {
        return new JAXBElement<GetAPIKeyState>(_GetAPIKeyState_QNAME, GetAPIKeyState.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAPIKeyStateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "GetAPIKeyStateResponse")
    public JAXBElement<GetAPIKeyStateResponse> createGetAPIKeyStateResponse(GetAPIKeyStateResponse value) {
        return new JAXBElement<GetAPIKeyStateResponse>(_GetAPIKeyStateResponse_QNAME, GetAPIKeyStateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "TestResponse")
    public JAXBElement<TestResponse> createTestResponse(TestResponse value) {
        return new JAXBElement<TestResponse>(_TestResponse_QNAME, TestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAPIKeyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "GetAPIKeyResponse")
    public JAXBElement<GetAPIKeyResponse> createGetAPIKeyResponse(GetAPIKeyResponse value) {
        return new JAXBElement<GetAPIKeyResponse>(_GetAPIKeyResponse_QNAME, GetAPIKeyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAPIKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "GetAPIKey")
    public JAXBElement<GetAPIKey> createGetAPIKey(GetAPIKey value) {
        return new JAXBElement<GetAPIKey>(_GetAPIKey_QNAME, GetAPIKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "GetStateResponse")
    public JAXBElement<GetStateResponse> createGetStateResponse(GetStateResponse value) {
        return new JAXBElement<GetStateResponse>(_GetStateResponse_QNAME, GetStateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetState }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "GetState")
    public JAXBElement<GetState> createGetState(GetState value) {
        return new JAXBElement<GetState>(_GetState_QNAME, GetState.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecommends }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "GetRecommends")
    public JAXBElement<GetRecommends> createGetRecommends(GetRecommends value) {
        return new JAXBElement<GetRecommends>(_GetRecommends_QNAME, GetRecommends.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecommendsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "GetRecommendsResponse")
    public JAXBElement<GetRecommendsResponse> createGetRecommendsResponse(GetRecommendsResponse value) {
        return new JAXBElement<GetRecommendsResponse>(_GetRecommendsResponse_QNAME, GetRecommendsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Test }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.thinkingtop.com/kaasservice", name = "Test")
    public JAXBElement<Test> createTest(Test value) {
        return new JAXBElement<Test>(_Test_QNAME, Test.class, null, value);
    }

}
