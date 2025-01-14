
package jp.yahooapis.ss.V5.TrafficEstimatorService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProposalPlatformType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProposalPlatformType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SMART_PHONE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProposalPlatformType")
@XmlEnum
public enum ProposalPlatformType {

    SMART_PHONE;

    public String value() {
        return name();
    }

    public static ProposalPlatformType fromValue(String v) {
        return valueOf(v);
    }

}
