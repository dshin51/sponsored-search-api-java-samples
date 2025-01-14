
package jp.yahooapis.ss.V5.AdGroupCriterionService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CriterionUse.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CriterionUse">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BIDDABLE"/>
 *     &lt;enumeration value="NEGATIVE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CriterionUse")
@XmlEnum
public enum CriterionUse {

    BIDDABLE,
    NEGATIVE;

    public String value() {
        return name();
    }

    public static CriterionUse fromValue(String v) {
        return valueOf(v);
    }

}
