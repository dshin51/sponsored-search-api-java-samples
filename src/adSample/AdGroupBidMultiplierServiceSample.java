package adSample;

import error.impl.AdGroupBidMultiplierServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.AdGroupBidMultiplierService.*;
import jp.yahooapis.ss.V5.AdGroupBidMultiplierService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.List;

/**
 * Sample Program for AdGroupBidMultiplierService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class AdGroupBidMultiplierServiceSample {

    /**
     * main method for AdGroupBidMultiplierServiceSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();
            long campaignId = SoapUtils.getCampaignId();
            long adGroupId = SoapUtils.getAdGroupId();

            // AdGroupBidMultiplierService SET
            set(accountId, campaignId, adGroupId);

            // AdGroupBidMultiplierService GET
            get(accountId, campaignId, adGroupId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sample Program for AdGroupBidMultiplierService SET.
     *
     * @param accountId  Account ID
     * @param campaignId Campaign ID
     * @param adGroupId  Ad group ID
     * @return AdGroupBidMultiplierValues
     * @throws Exception
     */
    public static List<AdGroupBidMultiplierValues> set(long accountId, long campaignId, long adGroupId) throws Exception {

        // Set PlatformBidMultiplier
        PlatformBidMultiplier platformBidMultiplier = new PlatformBidMultiplier();
        platformBidMultiplier.setType(BidMultiplierType.PLATFORM);
        platformBidMultiplier.setPlatformName(PlatformType.SMART_PHONE);
        platformBidMultiplier.setBidMultiplier(3.2);

        // Set PlatformBidMultiplierList
        PlatformBidMultiplierList platformBidMultiplierList = new PlatformBidMultiplierList();
        platformBidMultiplierList.setType(BidMultiplierType.PLATFORM);
        platformBidMultiplierList.getBidMultipliers().add(platformBidMultiplier);

        // Set Operand
        AdGroupBidMultiplier adGroupBidMultiplier = new AdGroupBidMultiplier();
        adGroupBidMultiplier.setAdGroupId(adGroupId);
        adGroupBidMultiplier.getBidMultipliers().add(platformBidMultiplierList);

        // Set Operation
        AdGroupBidMultiplierOperation adGroupBidMultiplierOperation = new AdGroupBidMultiplierOperation();
        adGroupBidMultiplierOperation.setOperator(Operator.SET);
        adGroupBidMultiplierOperation.setAccountId(accountId);
        adGroupBidMultiplierOperation.setCampaignId(campaignId);
        adGroupBidMultiplierOperation.getOperand().add(adGroupBidMultiplier);

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupBidMultiplierService::mutate(SET)");
        System.out.println("############################################");

        Holder<AdGroupBidMultiplierReturnValue> adGroupBidMultiplierReturnValueHolder = new Holder<AdGroupBidMultiplierReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupBidMultiplierServiceInterface adGroupBidMultiplierService = SoapUtils.createServiceInterface(AdGroupBidMultiplierServiceInterface.class, AdGroupBidMultiplierService.class);
        adGroupBidMultiplierService.mutate(adGroupBidMultiplierOperation, adGroupBidMultiplierReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupBidMultiplierServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupBidMultiplierReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupBidMultiplierService Add");
        }

        // Display
        for (AdGroupBidMultiplierValues adGroupBidMultiplierValues : adGroupBidMultiplierReturnValueHolder.value.getValues()) {
            if (adGroupBidMultiplierValues.isOperationSucceeded()) {
                display(adGroupBidMultiplierValues.getAdGroupBidMultiplier());
            } else {
                SoapUtils.displayErrors(new AdGroupBidMultiplierServiceErrorEntityFactory(adGroupBidMultiplierValues.getError()), true);
            }
        }

        // Response
        return adGroupBidMultiplierReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupBidMultiplierService GET.
     *
     * @param accountId  Account ID
     * @param campaignId Campaign ID
     * @param adGroupId  Ad group ID
     * @return AdGroupBidMultiplierValues
     * @throws Exception
     */
    public static List get(long accountId, long campaignId, long adGroupId) throws Exception {

        // Set Selector
        AdGroupBidMultiplierSelector adGroupBidMultiplierSelector = new AdGroupBidMultiplierSelector();
        adGroupBidMultiplierSelector.setAccountId(accountId);
        adGroupBidMultiplierSelector.getCampaignIds().add(campaignId);
        adGroupBidMultiplierSelector.getAdGroupIds().add(adGroupId);
        Paging paging = new Paging();
        paging.setStartIndex(1);
        paging.setNumberResults(20);
        adGroupBidMultiplierSelector.setPaging(paging);

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupBidMultiplierService::get");
        System.out.println("############################################");

        Holder<AdGroupBidMultiplierPage> adGroupBidMultiplierPage = new Holder<AdGroupBidMultiplierPage>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupBidMultiplierServiceInterface adGroupBidMultiplierService = SoapUtils.createServiceInterface(AdGroupBidMultiplierServiceInterface.class, AdGroupBidMultiplierService.class);
        adGroupBidMultiplierService.get(adGroupBidMultiplierSelector, adGroupBidMultiplierPage, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupBidMultiplierServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupBidMultiplierPage.value == null) {
            throw new Exception("NoDataResponse:AdGroupBidMultiplierService Get");
        }

        // Display
        for (AdGroupBidMultiplierValues adGroupBidMultiplierValues : adGroupBidMultiplierPage.value.getValues()) {
            if (adGroupBidMultiplierValues.isOperationSucceeded()) {
                display(adGroupBidMultiplierValues.getAdGroupBidMultiplier());
            } else {
                SoapUtils.displayErrors(new AdGroupBidMultiplierServiceErrorEntityFactory(adGroupBidMultiplierValues.getError()), true);
            }
        }

        // Response
        return adGroupBidMultiplierPage.value.getValues();
    }

    /**
     * display AdGroupBidMultiplier entity to stdout.
     *
     * @param adGroupBidMultiplier AdGroupBidMultiplier entity for display.
     */
    public static void display(AdGroupBidMultiplier adGroupBidMultiplier) {

        System.out.println("accountId = " + adGroupBidMultiplier.getAccountId());
        System.out.println("campaignId = " + adGroupBidMultiplier.getCampaignId());
        System.out.println("adGroupId = " + adGroupBidMultiplier.getAdGroupId());

        if (adGroupBidMultiplier.getBidMultipliers() != null) {
            for (BidMultiplierList bidMultiplier : adGroupBidMultiplier.getBidMultipliers()) {
                System.out.println("bidMultipliers/type = " + bidMultiplier.getType());
                if (bidMultiplier instanceof PlatformBidMultiplierList) {
                    for (PlatformBidMultiplier platformBidMultiplier : ((PlatformBidMultiplierList) bidMultiplier).getBidMultipliers()) {
                        System.out.println("bidMultipliers/bidMultipliers/type = " + platformBidMultiplier.getType());
                        System.out.println("bidMultipliers/bidMultipliers/type = " + platformBidMultiplier.getPlatformName());
                        System.out.println("bidMultipliers/bidMultipliers/type = " + platformBidMultiplier.getBidMultiplier());
                    }
                }
            }
        }

        System.out.println("---------");
    }
}
