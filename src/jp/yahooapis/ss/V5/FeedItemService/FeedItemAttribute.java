
package jp.yahooapis.ss.V5.FeedItemService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FeedItemAttribute complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FeedItemAttribute">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="placeholderField" type="{http://ss.yahooapis.jp/V5}PlaceholderField" minOccurs="0"/>
 *         &lt;element name="feedAttributeValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editFeedAttributeValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeedItemAttribute", propOrder = {
    "placeholderField",
    "feedAttributeValue",
    "editFeedAttributeValue"
})
public class FeedItemAttribute {

    protected PlaceholderField placeholderField;
    protected String feedAttributeValue;
    protected String editFeedAttributeValue;

    /**
     * Gets the value of the placeholderField property.
     * 
     * @return
     *     possible object is
     *     {@link PlaceholderField }
     *     
     */
    public PlaceholderField getPlaceholderField() {
        return placeholderField;
    }

    /**
     * Sets the value of the placeholderField property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceholderField }
     *     
     */
    public void setPlaceholderField(PlaceholderField value) {
        this.placeholderField = value;
    }

    /**
     * Gets the value of the feedAttributeValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeedAttributeValue() {
        return feedAttributeValue;
    }

    /**
     * Sets the value of the feedAttributeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeedAttributeValue(String value) {
        this.feedAttributeValue = value;
    }

    /**
     * Gets the value of the editFeedAttributeValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditFeedAttributeValue() {
        return editFeedAttributeValue;
    }

    /**
     * Sets the value of the editFeedAttributeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditFeedAttributeValue(String value) {
        this.editFeedAttributeValue = value;
    }

}
