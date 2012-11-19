
package com.thinkingtop.kaasservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>GetRecommends complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="GetRecommends">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ecommerceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KeyString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scheme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputItems" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outputItemsNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="outputQuantitye" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRecommends", propOrder = {
    "ecommerceName",
    "keyString",
    "endUser",
    "scheme",
    "inputItems",
    "outputItemsNum",
    "outputQuantitye"
})
public class GetRecommends {

    protected String ecommerceName;
    @XmlElement(name = "KeyString")
    protected String keyString;
    protected String endUser;
    protected String scheme;
    protected String inputItems;
    protected int outputItemsNum;
    protected int outputQuantitye;

    /**
     * 获取ecommerceName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEcommerceName() {
        return ecommerceName;
    }

    /**
     * 设置ecommerceName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEcommerceName(String value) {
        this.ecommerceName = value;
    }

    /**
     * 获取keyString属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyString() {
        return keyString;
    }

    /**
     * 设置keyString属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyString(String value) {
        this.keyString = value;
    }

    /**
     * 获取endUser属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndUser() {
        return endUser;
    }

    /**
     * 设置endUser属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndUser(String value) {
        this.endUser = value;
    }

    /**
     * 获取scheme属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * 设置scheme属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheme(String value) {
        this.scheme = value;
    }

    /**
     * 获取inputItems属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputItems() {
        return inputItems;
    }

    /**
     * 设置inputItems属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputItems(String value) {
        this.inputItems = value;
    }

    /**
     * 获取outputItemsNum属性的值。
     * 
     */
    public int getOutputItemsNum() {
        return outputItemsNum;
    }

    /**
     * 设置outputItemsNum属性的值。
     * 
     */
    public void setOutputItemsNum(int value) {
        this.outputItemsNum = value;
    }

    /**
     * 获取outputQuantitye属性的值。
     * 
     */
    public int getOutputQuantitye() {
        return outputQuantitye;
    }

    /**
     * 设置outputQuantitye属性的值。
     * 
     */
    public void setOutputQuantitye(int value) {
        this.outputQuantitye = value;
    }

}
