
package jp.yahooapis.ss.V5.CampaignFeedService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CampaignFeedList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CampaignFeedList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="campaignId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="placeholderType" type="{http://ss.yahooapis.jp/V5}PlaceholderType" minOccurs="0"/>
 *         &lt;element name="campaignFeed" type="{http://ss.yahooapis.jp/V5}CampaignFeed" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="devicePlatform" type="{http://ss.yahooapis.jp/V5}DevicePlatform" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CampaignFeedList", propOrder = {
    "accountId",
    "campaignId",
    "placeholderType",
    "campaignFeed",
    "devicePlatform"
})
public class CampaignFeedList {

    protected long accountId;
    protected Long campaignId;
    @XmlSchemaType(name = "string")
    protected PlaceholderType placeholderType;
    protected List<CampaignFeed> campaignFeed;
    @XmlSchemaType(name = "string")
    protected DevicePlatform devicePlatform;

    /**
     * Gets the value of the accountId property.
     * 
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Sets the value of the accountId property.
     * 
     */
    public void setAccountId(long value) {
        this.accountId = value;
    }

    /**
     * Gets the value of the campaignId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCampaignId() {
        return campaignId;
    }

    /**
     * Sets the value of the campaignId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCampaignId(Long value) {
        this.campaignId = value;
    }

    /**
     * Gets the value of the placeholderType property.
     * 
     * @return
     *     possible object is
     *     {@link PlaceholderType }
     *     
     */
    public PlaceholderType getPlaceholderType() {
        return placeholderType;
    }

    /**
     * Sets the value of the placeholderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlaceholderType }
     *     
     */
    public void setPlaceholderType(PlaceholderType value) {
        this.placeholderType = value;
    }

    /**
     * Gets the value of the campaignFeed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the campaignFeed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCampaignFeed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampaignFeed }
     * 
     * 
     */
    public List<CampaignFeed> getCampaignFeed() {
        if (campaignFeed == null) {
            campaignFeed = new ArrayList<CampaignFeed>();
        }
        return this.campaignFeed;
    }

    /**
     * Gets the value of the devicePlatform property.
     * 
     * @return
     *     possible object is
     *     {@link DevicePlatform }
     *     
     */
    public DevicePlatform getDevicePlatform() {
        return devicePlatform;
    }

    /**
     * Sets the value of the devicePlatform property.
     * 
     * @param value
     *     allowed object is
     *     {@link DevicePlatform }
     *     
     */
    public void setDevicePlatform(DevicePlatform value) {
        this.devicePlatform = value;
    }

}
