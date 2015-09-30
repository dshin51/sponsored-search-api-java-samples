package conversionTrackerSample;

import error.impl.ConversionTrackerServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.ConversionTrackerService.*;
import jp.yahooapis.ss.V5.ConversionTrackerService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Sample Program for ConversionTrackerService. Copyright (C) 2012 Yahoo Japan
 * Corporation. All Rights Reserved.
 */
public class ConversionTrackerSample {

    /**
     * main method for ConversionTrackerSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        long accountId = SoapUtils.getAccountId();
        String appId = SoapUtils.getAppId();
        long conversionTrackerId = -1;

        try {
            //=================================================================
            // ConversionTrackerService
            //=================================================================
            ConversionTrackerServiceInterface conversionTrackerService = SoapUtils.createServiceInterface(ConversionTrackerServiceInterface.class, ConversionTrackerService.class);

            //-----------------------------------------------
            // ConversionTrackerService::mutate(ADD)
            //-----------------------------------------------
            //request
            //AppConversionTracker
            AppConversion appConversion = new AppConversion();
            appConversion.setAppId(appId);
            appConversion.setAppPlatform(AppPlatform.ANDROID_MARKET);
            appConversion.setAppConversionType(AppConversionType.DOWNLOAD);
            appConversion.setAccountId(accountId);
            appConversion.setConversionTrackerName("SampleAppConversionTracker_CreateOn_" + SoapUtils.getCurrentTimestamp());
            appConversion.setStatus(ConversionTrackerStatus.ENABLED);
            appConversion.setCategory(ConversionTrackerCategory.DOWNLOAD);
            appConversion.setConversionTrackerType(ConversionTrackerType.APP_CONVERSION);
            appConversion.setUserRevenueValue("100");

            //WebConversionTracker
            WebConversion webConversion = new WebConversion();
            webConversion.setMarkupLanguage(MarkupLanguage.HTML);
            webConversion.setHttpProtocol(HttpProtocol.HTTPS);
            webConversion.setTrackingCodeType(TrackingCodeType.CLICK_TO_CALL);
            webConversion.setAccountId(accountId);
            webConversion.setConversionTrackerName("SampleWebConversionTracker_CreateOn_" + SoapUtils.getCurrentTimestamp());
            webConversion.setStatus(ConversionTrackerStatus.ENABLED);
            webConversion.setCategory(ConversionTrackerCategory.DEFAULT);
            webConversion.setConversionTrackerType(ConversionTrackerType.WEB_CONVERSION);
            webConversion.setUserRevenueValue("100");

            ConversionTrackerOperation addConversionTrackerOperation = new ConversionTrackerOperation();
            addConversionTrackerOperation.setOperator(Operator.ADD);
            addConversionTrackerOperation.setAccountId(accountId);
            addConversionTrackerOperation.getOperand().add(appConversion);
            addConversionTrackerOperation.getOperand().add(webConversion);

            //call API
            System.out.println("############################################");
            System.out.println("ConversionTrackerService::mutate(ADD)");
            System.out.println("############################################");
            Holder<ConversionTrackerReturnValue> addConversionTrackerReturnValueHolder = new Holder<ConversionTrackerReturnValue>();
            Holder<List<Error>> addErrorArrayHolder = new Holder<List<Error>>();

            conversionTrackerService.mutate(addConversionTrackerOperation, addConversionTrackerReturnValueHolder, addErrorArrayHolder);

            //if error
            if (addErrorArrayHolder.value != null && addErrorArrayHolder.value.size() > 0) {
                SoapUtils.displayErrors(new ConversionTrackerServiceErrorEntityFactory(addErrorArrayHolder.value), true);
            }

            //response
            if (addConversionTrackerReturnValueHolder.value != null) {
                if (addConversionTrackerReturnValueHolder.value.getValues() != null) {
                    for (ConversionTrackerValues values : addConversionTrackerReturnValueHolder.value.getValues()) {
                        if (values.isOperationSucceeded()) {
                            conversionTrackerId = values.getConversionTracker().getConversionTrackerId();
                            displayConversionTracker(values.getConversionTracker());
                        } else {
                            //if error
                            SoapUtils.displayErrors(new ConversionTrackerServiceErrorEntityFactory(values.getError()), true);
                        }
                    }

                }
            }

            //-----------------------------------------------
            // ConversionTrackerService::get
            //-----------------------------------------------
            //request
            ConversionTrackerSelector conversionTrackerSelector = new ConversionTrackerSelector();
            conversionTrackerSelector.setAccountId(accountId);
            conversionTrackerSelector.getConversionTrackerIds().add(conversionTrackerId);
            conversionTrackerSelector.getStatuses().add(ConversionTrackerStatus.ENABLED);
            conversionTrackerSelector.getCategories().add(ConversionTrackerCategory.DEFAULT);
            conversionTrackerSelector.setDateRange(createConversionDateRange());
            Paging paging = new Paging();
            paging.setStartIndex(1);
            paging.setNumberResults(20);
            conversionTrackerSelector.setPaging(paging);

            //call API
            System.out.println("############################################");
            System.out.println("ConversionTrackerService::get");
            System.out.println("############################################");
            Holder<ConversionTrackerPage> conversionTrackerPageHolder = new Holder<ConversionTrackerPage>();
            Holder<List<Error>> getErrorArrayHolder = new Holder<List<Error>>();
            conversionTrackerService.get(conversionTrackerSelector, conversionTrackerPageHolder, getErrorArrayHolder);

            //if error
            if (getErrorArrayHolder.value != null && getErrorArrayHolder.value.size() > 0) {
                SoapUtils.displayErrors(new ConversionTrackerServiceErrorEntityFactory(getErrorArrayHolder.value), true);
            }

            //response
            if (conversionTrackerPageHolder.value != null) {
                if (conversionTrackerPageHolder.value.getValues() != null) {
                    for (ConversionTrackerValues values : conversionTrackerPageHolder.value.getValues()) {
                        if (values.isOperationSucceeded()) {
                            //response display
                            displayConversionTracker(values.getConversionTracker());
                        } else {
                            //if error
                            SoapUtils.displayErrors(new ConversionTrackerServiceErrorEntityFactory(values.getError()), true);
                        }

                    }
                }

            }


            //-----------------------------------------------
            // ConversionTrackerService::mutate(SET)
            //-----------------------------------------------
            //request
            WebConversion setConversionTrackerOperand = new WebConversion();
            setConversionTrackerOperand.setAccountId(accountId);
            setConversionTrackerOperand.setConversionTrackerId(conversionTrackerId);
            setConversionTrackerOperand.setConversionTrackerName("SampleWebConversionTracker_UpdateOn_" + SoapUtils.getCurrentTimestamp());
            setConversionTrackerOperand.setCategory(ConversionTrackerCategory.DEFAULT);
            setConversionTrackerOperand.setStatus(ConversionTrackerStatus.DISABLED);
            setConversionTrackerOperand.setConversionTrackerType(ConversionTrackerType.WEB_CONVERSION);
            ConversionTrackerOperation setConversionTrackerOperation = new ConversionTrackerOperation();
            setConversionTrackerOperation.setOperator(Operator.SET);
            setConversionTrackerOperation.setAccountId(accountId);
            setConversionTrackerOperation.getOperand().add(setConversionTrackerOperand);

            //call API
            System.out.println("############################################");
            System.out.println("ConversionTrackerService::mutate(SET)");
            System.out.println("############################################");
            Holder<ConversionTrackerReturnValue> setConversionTrackerReturnValueHolder = new Holder<ConversionTrackerReturnValue>();
            Holder<List<Error>> setErrorArrayHolder = new Holder<List<Error>>();
            conversionTrackerService.mutate(setConversionTrackerOperation, setConversionTrackerReturnValueHolder, setErrorArrayHolder);

            //if error
            if (setErrorArrayHolder.value != null && setErrorArrayHolder.value.size() > 0) {
                SoapUtils.displayErrors(new ConversionTrackerServiceErrorEntityFactory(setErrorArrayHolder.value), true);
            }

            //response
            if (setConversionTrackerReturnValueHolder.value != null) {
                if (setConversionTrackerReturnValueHolder.value.getValues() != null) {
                    for (ConversionTrackerValues values : setConversionTrackerReturnValueHolder.value.getValues()) {
                        if (values.isOperationSucceeded()) {
                            //display response
                            displayConversionTracker(values.getConversionTracker());
                        } else {
                            //if error
                            SoapUtils.displayErrors(new ConversionTrackerServiceErrorEntityFactory(values.getError()), true);
                        }
                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static ConversionDateRange createConversionDateRange() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 10);

        ConversionDateRange conversionDateRange = new ConversionDateRange();
        conversionDateRange.setStartDate(format.format(Calendar.getInstance().getTime()));
        conversionDateRange.setEndDate(format.format(today.getTime()));
        return conversionDateRange;

    }

    /**
     * * display ConversionTracker entity to stdout.
     *
     * @param conversionTracker ConversionTracker entity for display.
     */
    private static void displayConversionTracker(ConversionTracker conversionTracker) {

        System.out.println("accountId = " + conversionTracker.getAccountId());
        System.out.println("conversionTrackerId = " + conversionTracker.getConversionTrackerId());
        System.out.println("conversionTrackerName = " + conversionTracker.getConversionTrackerName());
        System.out.println("status = " + conversionTracker.getStatus());
        System.out.println("category = " + conversionTracker.getCategory());
        System.out.println("numConversionEvents = " + conversionTracker.getNumConversionEvents());
        System.out.println("conversionValue = " + conversionTracker.getConversionValue());
        System.out.println("mostRecentConversionDate = " + conversionTracker.getMostRecentConversionDate());
        System.out.println("numConvertedClicks = " + conversionTracker.getNumConvertedClicks());
        System.out.println("conversionTrackerType = " + conversionTracker.getConversionTrackerType());
        System.out.println("userRevenueValue = " + conversionTracker.getUserRevenueValue());
        System.out.println("---------");
    }
}
