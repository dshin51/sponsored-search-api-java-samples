
package jp.yahooapis.ss.V5.FeedItemService;

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
@WebService(name = "FeedItemServiceInterface", targetNamespace = "http://ss.yahooapis.jp/V5")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface FeedItemServiceInterface {


    /**
     * 
     * @param selector
     * @param rval
     * @param error
     * @throws ApiException
     */
    @WebMethod
    @RequestWrapper(localName = "get", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.FeedItemService.Get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.FeedItemService.GetResponse")
    public void get(
        @WebParam(name = "selector", targetNamespace = "http://ss.yahooapis.jp/V5")
        FeedItemSelector selector,
        @WebParam(name = "rval", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<FeedItemPage> rval,
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
    @RequestWrapper(localName = "mutate", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.FeedItemService.Mutate")
    @ResponseWrapper(localName = "mutateResponse", targetNamespace = "http://ss.yahooapis.jp/V5", className = "jp.yahooapis.ss.V5.FeedItemService.MutateResponse")
    public void mutate(
        @WebParam(name = "operations", targetNamespace = "http://ss.yahooapis.jp/V5")
        FeedItemOperation operations,
        @WebParam(name = "rval", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<FeedItemReturnValue> rval,
        @WebParam(name = "error", targetNamespace = "http://ss.yahooapis.jp/V5", mode = WebParam.Mode.OUT)
        Holder<List<Error>> error)
        throws ApiException
    ;

}
