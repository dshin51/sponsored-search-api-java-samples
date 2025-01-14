
package jp.yahooapis.ss.V5.BulkService;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "BulkService", targetNamespace = "http://ss.yahooapis.jp/V5", wsdlLocation = "https://sandbox.ss.yahooapis.jp/services/V5.3/BulkService?wsdl")
public class BulkService
    extends Service
{

    private final static URL BULKSERVICE_WSDL_LOCATION;
    private final static WebServiceException BULKSERVICE_EXCEPTION;
    private final static QName BULKSERVICE_QNAME = new QName("http://ss.yahooapis.jp/V5", "BulkService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://sandbox.ss.yahooapis.jp/services/V5.3/BulkService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BULKSERVICE_WSDL_LOCATION = url;
        BULKSERVICE_EXCEPTION = e;
    }

    public BulkService() {
        super(__getWsdlLocation(), BULKSERVICE_QNAME);
    }

    public BulkService(WebServiceFeature... features) {
        super(__getWsdlLocation(), BULKSERVICE_QNAME, features);
    }

    public BulkService(URL wsdlLocation) {
        super(wsdlLocation, BULKSERVICE_QNAME);
    }

    public BulkService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BULKSERVICE_QNAME, features);
    }

    public BulkService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BulkService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns BulkServiceInterface
     */
    @WebEndpoint(name = "BulkService")
    public BulkServiceInterface getBulkService() {
        return super.getPort(new QName("http://ss.yahooapis.jp/V5", "BulkService"), BulkServiceInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BulkServiceInterface
     */
    @WebEndpoint(name = "BulkService")
    public BulkServiceInterface getBulkService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ss.yahooapis.jp/V5", "BulkService"), BulkServiceInterface.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BULKSERVICE_EXCEPTION!= null) {
            throw BULKSERVICE_EXCEPTION;
        }
        return BULKSERVICE_WSDL_LOCATION;
    }

}
