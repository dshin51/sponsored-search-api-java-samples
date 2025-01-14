
package jp.yahooapis.ss.V5.CampaignCriterionService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CampaignCriterionValues complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CampaignCriterionValues">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ss.yahooapis.jp/V5}ReturnValue">
 *       &lt;sequence>
 *         &lt;element name="campaignCriterion" type="{http://ss.yahooapis.jp/V5}CampaignCriterion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CampaignCriterionValues", propOrder = {
    "campaignCriterion"
})
public class CampaignCriterionValues
    extends ReturnValue
{

    protected CampaignCriterion campaignCriterion;

    /**
     * Gets the value of the campaignCriterion property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignCriterion }
     *     
     */
    public CampaignCriterion getCampaignCriterion() {
        return campaignCriterion;
    }

    /**
     * Sets the value of the campaignCriterion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignCriterion }
     *     
     */
    public void setCampaignCriterion(CampaignCriterion value) {
        this.campaignCriterion = value;
    }

}
