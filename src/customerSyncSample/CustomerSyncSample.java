package customerSyncSample;

import error.impl.CustomerSyncServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.CustomerSyncService.*;
import jp.yahooapis.ss.V5.CustomerSyncService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.List;


/**
 * Sample Program for CustomerSyncService. Copyright (C) 2012 Yahoo Japan
 * Corporation. All Rights Reserved.
 */
public class CustomerSyncSample {

    /**
     * main method for CustomerSyncSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        try {
            long accountId = SoapUtils.getAccountId();

            // =================================================================
            // CustomerSyncService
            // =================================================================
            CustomerSyncServiceInterface customerSyncService = SoapUtils.createServiceInterface(CustomerSyncServiceInterface.class, CustomerSyncService.class);

            // -----------------------------------------------
            // CustomerSyncService::get(without accountIds)
            // -----------------------------------------------
            // request
            CustomerSyncSelector customerSyncSelector = new CustomerSyncSelector();
            customerSyncSelector.setAccountId(accountId);
            customerSyncSelector.getSourceTypes().add(SourceType.API);
            DateRange dateRange = new DateRange();
            dateRange.setStartDate("-4 day 00:00:00");
            dateRange.setEndDate("+0 day 00:00:00");
            dateRange.setIncludeUnset(IncludeUnset.INCLUDED);
            customerSyncSelector.setDateRange(dateRange);


            // call API
            System.out.println("############################################");
            System.out.println("CustomerSyncService::get");
            System.out.println("############################################");
            Holder<CustomerChangeData> customerChangeDataHolder = new Holder<CustomerChangeData>();
            Holder<List<Error>> customerSyncErrorHolder = new Holder<List<Error>>();
            customerSyncService.get(customerSyncSelector, customerChangeDataHolder, customerSyncErrorHolder);

            // if error
            if (customerSyncErrorHolder.value != null
                    && customerSyncErrorHolder.value.size() > 0) {
                SoapUtils.displayErrors(new CustomerSyncServiceErrorEntityFactory(customerSyncErrorHolder.value), true);
            }

            // response
            if (customerChangeDataHolder.value != null) {
                CustomerChangeData customerChangeData = customerChangeDataHolder.value;
                if (customerChangeData.getValues() != null
                        && customerChangeData.getValues().size() > 0) {
                    for (int i = 0; i < customerChangeData.getValues().size(); i++) {
                        ChangeDataValues changeDataValues = customerChangeData.getValues().get(i);
                        if (changeDataValues.isOperationSucceeded()) {
                            // display response
                            for (int j = 0; j < changeDataValues.getAuditlog().size(); j++) {
                                displayAuditlog(changeDataValues.getAuditlog().get(j));
                            }
                        } else {
                            // if error
                            SoapUtils.displayErrors(new CustomerSyncServiceErrorEntityFactory(changeDataValues.getError()), false);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * display account entity to stdout.
     *
     * @param account account entity for display.
     */
    private static void displayAuditlog(Auditlog auditlog) {
        System.out.println("accountId = " + auditlog.getAccountId());
        System.out.println("updatedTime = " + auditlog.getUpdatedTime());
        System.out.println("entityType = " + auditlog.getEntityType());
        System.out.println("---------");
    }
}
