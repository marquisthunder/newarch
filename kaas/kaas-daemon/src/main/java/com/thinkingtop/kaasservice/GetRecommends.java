
package com.thinkingtop.kaasservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetRecommends complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the ecommerceName property.
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
     * Sets the value of the ecommerceName property.
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
     * Gets the value of the keyString property.
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
     * Sets the value of the keyString property.
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
     * Gets the value of the endUser property.
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
     * Sets the value of the endUser property.
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
     * Gets the value of the scheme property.
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
     * Sets the value of the scheme property.
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
     * Gets the value of the inputItems property.
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
     * Sets the value of the inputItems property.
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
     * Gets the value of the outputItemsNum property.
     * 
     */
    public int getOutputItemsNum() {
        return outputItemsNum;
    }

    /**
     * Sets the value of the outputItemsNum property.
     * 
     */
    public void setOutputItemsNum(int value) {
        this.outputItemsNum = value;
    }

    /**
     * Gets the value of the outputQuantitye property.
     * 
     */
    public int getOutputQuantitye() {
        return outputQuantitye;
    }

    /**
     * Sets the value of the outputQuantitye property.
     * 
     */
    public void setOutputQuantitye(int value) {
        this.outputQuantitye = value;
    }

}
