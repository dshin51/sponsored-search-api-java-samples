
package jp.yahooapis.ss.V5.TrafficEstimatorService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the jp.yahooapis.ss.V5.TrafficEstimatorService package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ResponseHeader_QNAME = new QName("http://ss.yahooapis.jp/V5", "ResponseHeader");
    private final static QName _ApiExceptionFault_QNAME = new QName("http://ss.yahooapis.jp/V5", "ApiExceptionFault");
    private final static QName _RequestHeader_QNAME = new QName("http://ss.yahooapis.jp/V5", "RequestHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: jp.yahooapis.ss.V5.TrafficEstimatorService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoapResponseHeader }
     * 
     */
    public SoapResponseHeader createSoapResponseHeader() {
        return new SoapResponseHeader();
    }

    /**
     * Create an instance of {@link GetResponse }
     * 
     */
    public GetResponse createGetResponse() {
        return new GetResponse();
    }

    /**
     * Create an instance of {@link TrafficEstimatorPage }
     * 
     */
    public TrafficEstimatorPage createTrafficEstimatorPage() {
        return new TrafficEstimatorPage();
    }

    /**
     * Create an instance of {@link Error }
     * 
     */
    public Error createError() {
        return new Error();
    }

    /**
     * Create an instance of {@link Get }
     * 
     */
    public Get createGet() {
        return new Get();
    }

    /**
     * Create an instance of {@link TrafficEstimatorSelector }
     * 
     */
    public TrafficEstimatorSelector createTrafficEstimatorSelector() {
        return new TrafficEstimatorSelector();
    }

    /**
     * Create an instance of {@link SoapHeader }
     * 
     */
    public SoapHeader createSoapHeader() {
        return new SoapHeader();
    }

    /**
     * Create an instance of {@link EstimateValues }
     * 
     */
    public EstimateValues createEstimateValues() {
        return new EstimateValues();
    }

    /**
     * Create an instance of {@link TotalEstimateResult }
     * 
     */
    public TotalEstimateResult createTotalEstimateResult() {
        return new TotalEstimateResult();
    }

    /**
     * Create an instance of {@link ErrorDetail }
     * 
     */
    public ErrorDetail createErrorDetail() {
        return new ErrorDetail();
    }

    /**
     * Create an instance of {@link SmartPhoneEstimateResult }
     * 
     */
    public SmartPhoneEstimateResult createSmartPhoneEstimateResult() {
        return new SmartPhoneEstimateResult();
    }

    /**
     * Create an instance of {@link EstimateTarget }
     * 
     */
    public EstimateTarget createEstimateTarget() {
        return new EstimateTarget();
    }

    /**
     * Create an instance of {@link WapMobileEstimateResult }
     * 
     */
    public WapMobileEstimateResult createWapMobileEstimateResult() {
        return new WapMobileEstimateResult();
    }

    /**
     * Create an instance of {@link TrafficEstimateResult }
     * 
     */
    public TrafficEstimateResult createTrafficEstimateResult() {
        return new TrafficEstimateResult();
    }

    /**
     * Create an instance of {@link DesktopEstimateResult }
     * 
     */
    public DesktopEstimateResult createDesktopEstimateResult() {
        return new DesktopEstimateResult();
    }

    /**
     * Create an instance of {@link ProposalKeyword }
     * 
     */
    public ProposalKeyword createProposalKeyword() {
        return new ProposalKeyword();
    }

    /**
     * Create an instance of {@link EstimateRequest }
     * 
     */
    public EstimateRequest createEstimateRequest() {
        return new EstimateRequest();
    }

    /**
     * Create an instance of {@link ProposalPlatformTarget }
     * 
     */
    public ProposalPlatformTarget createProposalPlatformTarget() {
        return new ProposalPlatformTarget();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapResponseHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ss.yahooapis.jp/V5", name = "ResponseHeader")
    public JAXBElement<SoapResponseHeader> createResponseHeader(SoapResponseHeader value) {
        return new JAXBElement<SoapResponseHeader>(_ResponseHeader_QNAME, SoapResponseHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ss.yahooapis.jp/V5", name = "ApiExceptionFault")
    public JAXBElement<String> createApiExceptionFault(String value) {
        return new JAXBElement<String>(_ApiExceptionFault_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ss.yahooapis.jp/V5", name = "RequestHeader")
    public JAXBElement<SoapHeader> createRequestHeader(SoapHeader value) {
        return new JAXBElement<SoapHeader>(_RequestHeader_QNAME, SoapHeader.class, null, value);
    }

}
