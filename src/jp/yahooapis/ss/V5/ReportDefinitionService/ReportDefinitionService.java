
package jp.yahooapis.ss.V5.ReportDefinitionService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "ReportDefinitionService", targetNamespace = "http://ss.yahooapis.jp/V5", wsdlLocation = "https://sandbox.ss.yahooapis.jp/services/V5.0/ReportDefinitionService?wsdl")
public class ReportDefinitionService
    extends Service
{

    private final static URL REPORTDEFINITIONSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(jp.yahooapis.ss.V5.ReportDefinitionService.ReportDefinitionService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = jp.yahooapis.ss.V5.ReportDefinitionService.ReportDefinitionService.class.getResource(".");
            url = new URL(baseUrl, "https://sandbox.ss.yahooapis.jp/services/V5.0/ReportDefinitionService?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'https://sandbox.ss.yahooapis.jp/services/V5.0/ReportDefinitionService?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        REPORTDEFINITIONSERVICE_WSDL_LOCATION = url;
    }

    public ReportDefinitionService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReportDefinitionService() {
        super(REPORTDEFINITIONSERVICE_WSDL_LOCATION, new QName("http://ss.yahooapis.jp/V5", "ReportDefinitionService"));
    }

    /**
     * 
     * @return
     *     returns ReportDefinitionServiceInterface
     */
    @WebEndpoint(name = "ReportDefinitionService")
    public ReportDefinitionServiceInterface getReportDefinitionService() {
        return super.getPort(new QName("http://ss.yahooapis.jp/V5", "ReportDefinitionService"), ReportDefinitionServiceInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReportDefinitionServiceInterface
     */
    @WebEndpoint(name = "ReportDefinitionService")
    public ReportDefinitionServiceInterface getReportDefinitionService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ss.yahooapis.jp/V5", "ReportDefinitionService"), ReportDefinitionServiceInterface.class, features);
    }

}