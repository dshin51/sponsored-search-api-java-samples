
package jp.yahooapis.ss.V5.ConversionTrackerService;

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
@WebServiceClient(name = "ConversionTrackerService", targetNamespace = "http://ss.yahooapis.jp/V5", wsdlLocation = "https://sandbox.ss.yahooapis.jp/services/V5.3/ConversionTrackerService?wsdl")
public class ConversionTrackerService
    extends Service
{

    private final static URL CONVERSIONTRACKERSERVICE_WSDL_LOCATION;
    private final static WebServiceException CONVERSIONTRACKERSERVICE_EXCEPTION;
    private final static QName CONVERSIONTRACKERSERVICE_QNAME = new QName("http://ss.yahooapis.jp/V5", "ConversionTrackerService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://sandbox.ss.yahooapis.jp/services/V5.3/ConversionTrackerService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CONVERSIONTRACKERSERVICE_WSDL_LOCATION = url;
        CONVERSIONTRACKERSERVICE_EXCEPTION = e;
    }

    public ConversionTrackerService() {
        super(__getWsdlLocation(), CONVERSIONTRACKERSERVICE_QNAME);
    }

    public ConversionTrackerService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CONVERSIONTRACKERSERVICE_QNAME, features);
    }

    public ConversionTrackerService(URL wsdlLocation) {
        super(wsdlLocation, CONVERSIONTRACKERSERVICE_QNAME);
    }

    public ConversionTrackerService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CONVERSIONTRACKERSERVICE_QNAME, features);
    }

    public ConversionTrackerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConversionTrackerService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ConversionTrackerServiceInterface
     */
    @WebEndpoint(name = "ConversionTrackerService")
    public ConversionTrackerServiceInterface getConversionTrackerService() {
        return super.getPort(new QName("http://ss.yahooapis.jp/V5", "ConversionTrackerService"), ConversionTrackerServiceInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConversionTrackerServiceInterface
     */
    @WebEndpoint(name = "ConversionTrackerService")
    public ConversionTrackerServiceInterface getConversionTrackerService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ss.yahooapis.jp/V5", "ConversionTrackerService"), ConversionTrackerServiceInterface.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CONVERSIONTRACKERSERVICE_EXCEPTION!= null) {
            throw CONVERSIONTRACKERSERVICE_EXCEPTION;
        }
        return CONVERSIONTRACKERSERVICE_WSDL_LOCATION;
    }

}
