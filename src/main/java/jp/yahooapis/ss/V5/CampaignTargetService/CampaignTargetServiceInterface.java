
package jp.yahooapis.ss.V5.CampaignTargetService;

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
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CampaignTargetServiceInterface", targetNamespace = "http://ss.yahooapis.jp/V5")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CampaignTargetServiceInterface {


    /**
     * 
     * @param selector
     * @param rval
     * @param error
     * @throws ApiException
     */
    @WebMethod
    @RequestWrapper(localName = "get", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.CampaignTargetService.Get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.CampaignTargetService.GetResponse")
    public void get(
        @WebParam(name = "selector", targetNamespace = "http://ss.yahooapis.jp/V5")
        CampaignTargetSelector selector,
        @WebParam(name = "rval", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<CampaignTargetPage> rval,
        @WebParam(name = "error", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<List<Error>> error)
        throws ApiException
    ;

    /**
     * 
     * @param operations
     * @param rval
     * @param error
     * @throws ApiException
     */
    @WebMethod
    @RequestWrapper(localName = "mutate", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.CampaignTargetService.Mutate")
    @ResponseWrapper(localName = "mutateResponse", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.CampaignTargetService.MutateResponse")
    public void mutate(
        @WebParam(name = "operations", targetNamespace = "http://ss.yahooapis.jp/V5")
        CampaignTargetOperation operations,
        @WebParam(name = "rval", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<CampaignTargetReturnValue> rval,
        @WebParam(name = "error", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<List<Error>> error)
        throws ApiException
    ;

}