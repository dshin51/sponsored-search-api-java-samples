
package jp.yahooapis.ss.V5.BulkService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BulkEntityType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BulkEntityType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CAMPAIGN"/>
 *     &lt;enumeration value="NEGATIVE_CAMPAIGN_CRITERION"/>
 *     &lt;enumeration value="AD_GROUP"/>
 *     &lt;enumeration value="BIDDABLE_AD_GROUP_CRITERION"/>
 *     &lt;enumeration value="NEGATIVE_AD_GROUP_CRITERION"/>
 *     &lt;enumeration value="AD"/>
 *     &lt;enumeration value="ALL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BulkEntityType")
@XmlEnum
public enum BulkEntityType {

    CAMPAIGN,
    NEGATIVE_CAMPAIGN_CRITERION,
    AD_GROUP,
    BIDDABLE_AD_GROUP_CRITERION,
    NEGATIVE_AD_GROUP_CRITERION,
    AD,
    ALL;

    public String value() {
        return name();
    }

    public static BulkEntityType fromValue(String v) {
        return valueOf(v);
    }

}
