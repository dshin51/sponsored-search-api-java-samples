
package jp.yahooapis.ss.V5.ReportDefinitionService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportFrequency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReportFrequency">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ONETIME"/>
 *     &lt;enumeration value="DAILY"/>
 *     &lt;enumeration value="EVERYSUN"/>
 *     &lt;enumeration value="EVERYMON"/>
 *     &lt;enumeration value="EVERYTUE"/>
 *     &lt;enumeration value="EVERYWED"/>
 *     &lt;enumeration value="EVERYTHU"/>
 *     &lt;enumeration value="EVERYFRI"/>
 *     &lt;enumeration value="EVERYSAT"/>
 *     &lt;enumeration value="SPECIFYDAY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReportFrequency")
@XmlEnum
public enum ReportFrequency {

    ONETIME,
    DAILY,
    EVERYSUN,
    EVERYMON,
    EVERYTUE,
    EVERYWED,
    EVERYTHU,
    EVERYFRI,
    EVERYSAT,
    SPECIFYDAY;

    public String value() {
        return name();
    }

    public static ReportFrequency fromValue(String v) {
        return valueOf(v);
    }

}
