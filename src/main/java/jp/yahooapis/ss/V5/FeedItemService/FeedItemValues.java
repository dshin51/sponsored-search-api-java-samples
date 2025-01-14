
package jp.yahooapis.ss.V5.FeedItemService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FeedItemValues complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FeedItemValues">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ss.yahooapis.jp/V5}ReturnValue">
 *       &lt;sequence>
 *         &lt;element name="feedItem" type="{http://ss.yahooapis.jp/V5}FeedItem" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeedItemValues", propOrder = {
    "feedItem"
})
public class FeedItemValues
    extends ReturnValue
{

    protected FeedItem feedItem;

    /**
     * Gets the value of the feedItem property.
     * 
     * @return
     *     possible object is
     *     {@link FeedItem }
     *     
     */
    public FeedItem getFeedItem() {
        return feedItem;
    }

    /**
     * Sets the value of the feedItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeedItem }
     *     
     */
    public void setFeedItem(FeedItem value) {
        this.feedItem = value;
    }

}
