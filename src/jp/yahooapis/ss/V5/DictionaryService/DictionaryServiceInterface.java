
package jp.yahooapis.ss.V5.DictionaryService;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "DictionaryServiceInterface", targetNamespace = "http://ss.yahooapis.jp/V5")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DictionaryServiceInterface {


    /**
     * 
     * @param selector
     * @param rval
     * @param error
     * @throws ApiException
     */
    @WebMethod
    @RequestWrapper(localName = "getDisapprovalReason", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.DictionaryService.GetDisapprovalReason")
    @ResponseWrapper(localName = "getDisapprovalReasonResponse", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.DictionaryService.GetDisapprovalReasonResponse")
    public void getDisapprovalReason(
        @WebParam(name = "selector", targetNamespace = "http://ss.yahooapis.jp/V5")
        DisapprovalReasonSelector selector,
        @WebParam(name = "rval", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<DisapprovalReasonPage> rval,
        @WebParam(name = "error", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<List<Error>> error)
        throws ApiException
    ;

    /**
     * 
     * @param selector
     * @param rval
     * @param error
     */
    @WebMethod
    @RequestWrapper(localName = "getGeographicLocation", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.DictionaryService.GetGeographicLocation")
    @ResponseWrapper(localName = "getGeographicLocationResponse", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.DictionaryService.GetGeographicLocationResponse")
    public void getGeographicLocation(
        @WebParam(name = "selector", targetNamespace = "http://ss.yahooapis.jp/V5")
        GeographicLocationSelector selector,
        @WebParam(name = "rval", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<GeographicLocationPage> rval,
        @WebParam(name = "error", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<List<Error>> error);

}