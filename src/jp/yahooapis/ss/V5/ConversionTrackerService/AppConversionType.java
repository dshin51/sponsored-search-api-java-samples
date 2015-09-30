
package jp.yahooapis.ss.V5.ConversionTrackerService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppConversionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AppConversionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DOWNLOAD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AppConversionType")
@XmlEnum
public enum AppConversionType {

    DOWNLOAD;

    public String value() {
        return name();
    }

    public static AppConversionType fromValue(String v) {
        return valueOf(v);
    }

}
