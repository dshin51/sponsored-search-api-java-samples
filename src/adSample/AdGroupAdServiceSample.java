package adSample;

import error.impl.AdGroupAdServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.AdGroupAdService.*;
import jp.yahooapis.ss.V5.AdGroupAdService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Program for AdGroupAdService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class AdGroupAdServiceSample {

    /**
     * main method for AdGroupAdServiceSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();
            long campaignId = SoapUtils.getCampaignId();
            long adGroupId = SoapUtils.getAdGroupId();

            // AdGroupAdService ADD
            List<AdGroupAdValues> adGroupAdValues = add(accountId, campaignId, adGroupId);

            // AdGroupAdService GET
            get(accountId, campaignId, adGroupId, adGroupAdValues);

            // AdGroupAdService SET
            set(accountId, campaignId, adGroupId, adGroupAdValues);

            // AdGroupAdService REMOVE
            remove(accountId, campaignId, adGroupId, adGroupAdValues);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sample Program for AdGroupAdService ADD.
     *
     * @param accountId  Account ID
     * @param campaignId Campaign ID
     * @param adGroupId  Ad group ID
     * @return AdGroupAdValues
     * @throws Exception
     */
    public static List<AdGroupAdValues> add(long accountId, long campaignId, long adGroupId) throws Exception {

        // Set TextAd2
        TextAd2 textAd2 = new TextAd2();
        textAd2.setType(AdType.TEXT_AD_2);
        textAd2.setHeadline("sample headline");
        textAd2.setDescription("sample ad desc");
        textAd2.setDescription2("sample ad desc2");
        textAd2.setUrl("http://www.yahoo.co.jp/");
        textAd2.setDisplayUrl("www.yahoo.co.jp");
        textAd2.setDevicePreference(DevicePreference.SMART_PHONE);

        AdGroupAd textAd2AdGroupAd = new AdGroupAd();
        textAd2AdGroupAd.setAccountId(accountId);
        textAd2AdGroupAd.setCampaignId(campaignId);
        textAd2AdGroupAd.setAdGroupId(adGroupId);
        textAd2AdGroupAd.setAdName("SampleTextAd2_CreateOn_" + SoapUtils.getCurrentTimestamp());
        textAd2AdGroupAd.setAd(textAd2);
        textAd2AdGroupAd.setUserStatus(UserStatus.ACTIVE);

        // Set AppAd
        AppAd appAd = new AppAd();
        appAd.setType(AdType.APP_AD);
        appAd.setHeadline("sample");
        appAd.setDescription("sample ad desc");
        appAd.setDescription2("sample ad desc2");
        appAd.setUrl("http://www.yahoo.co.jp/");
        appAd.setDisplayUrl("www.yahoo.co.jp");
        appAd.setAppStore(AppStore.ANDROID);
        appAd.setAppId("99999");
        appAd.setDevicePreference(DevicePreference.SMART_PHONE);

        AdGroupAd appAdAdGroupAd = new AdGroupAd();
        appAdAdGroupAd.setAccountId(accountId);
        appAdAdGroupAd.setCampaignId(campaignId);
        appAdAdGroupAd.setAdGroupId(adGroupId);
        appAdAdGroupAd.setAdName("SampleAppAd_CreateOn_" + SoapUtils.getCurrentTimestamp());
        appAdAdGroupAd.setAd(appAd);
        appAdAdGroupAd.setUserStatus(UserStatus.ACTIVE);

        // Set Operation
        AdGroupAdOperation adGroupAdOperation = new AdGroupAdOperation();
        adGroupAdOperation.setOperator(Operator.ADD);
        adGroupAdOperation.setAccountId(accountId);
        adGroupAdOperation.setCampaignId(campaignId);
        adGroupAdOperation.setAdGroupId(adGroupId);
        adGroupAdOperation.getOperand().addAll(Arrays.asList(
                textAd2AdGroupAd,
                appAdAdGroupAd
        ));

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupAdService::mutate(ADD)");
        System.out.println("############################################");

        Holder<AdGroupAdReturnValue> adGroupAdReturnValueHolder = new Holder<AdGroupAdReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupAdServiceInterface adGroupAdService = SoapUtils.createServiceInterface(AdGroupAdServiceInterface.class, AdGroupAdService.class);
        adGroupAdService.mutate(adGroupAdOperation, adGroupAdReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupAdServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupAdReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupAdService Add");
        }

        // Display
        for (AdGroupAdValues adGroupAdValues : adGroupAdReturnValueHolder.value.getValues()) {
            if (adGroupAdValues.isOperationSucceeded()) {
                display(adGroupAdValues.getAdGroupAd());
            } else {
                SoapUtils.displayErrors(new AdGroupAdServiceErrorEntityFactory(adGroupAdValues.getError()), true);
            }
        }

        // Response
        return adGroupAdReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupAdService SET.
     *
     * @param accountId           Account ID
     * @param campaignId          Campaign ID
     * @param adGroupId           Ad group ID
     * @param adGroupAdValuesList AdGroupAdValues entity for set.
     * @return AdGroupAdValues
     * @throws Exception
     */
    public static List<AdGroupAdValues> set(long accountId, long campaignId, long adGroupId, List<AdGroupAdValues> adGroupAdValuesList) throws Exception {

        // Set Operation
        AdGroupAdOperation adGroupAdOperation = new AdGroupAdOperation();
        adGroupAdOperation.setOperator(Operator.SET);
        adGroupAdOperation.setAccountId(accountId);
        adGroupAdOperation.setCampaignId(campaignId);
        adGroupAdOperation.setAdGroupId(adGroupId);

        // Set Operand
        for (AdGroupAdValues adGroupAdValues : adGroupAdValuesList) {

            AdGroupAd adGroupAd = new AdGroupAd();
            adGroupAd.setAccountId(adGroupAdValues.getAdGroupAd().getAccountId());
            adGroupAd.setCampaignId(adGroupAdValues.getAdGroupAd().getCampaignId());
            adGroupAd.setAdGroupId(adGroupAdValues.getAdGroupAd().getAdGroupId());
            adGroupAd.setAdId(adGroupAdValues.getAdGroupAd().getAdId());
            adGroupAd.setUserStatus(UserStatus.PAUSED);

            // Set Ad
            if (AdType.TEXT_AD_2.equals(adGroupAdValues.getAdGroupAd().getAd().getType())) {

                // Set TextAd2
                TextAd2 textAd2 = new TextAd2();
                textAd2.setType(AdType.TEXT_AD_2);
                textAd2.setUrl("http://www.yahoo.mod.co.jp/");
                textAd2.setDisplayUrl("www.yahoo.mod.co.jp");
                textAd2.setHeadline("mod sample headline");
                textAd2.setDescription("mod sample ad desc");
                textAd2.setDescription2("mod sample ad desc2");

                adGroupAd.setAdName("SampleTextAd2_UpdateOn_" + SoapUtils.getCurrentTimestamp());
                adGroupAd.setAd(textAd2);

            } else if (AdType.APP_AD.equals(adGroupAdValues.getAdGroupAd().getAd().getType())) {

                // Set AppAd
                AppAd appAd = new AppAd();
                appAd.setType(AdType.APP_AD);
                appAd.setHeadline("mod sample");
                appAd.setDescription("mod sample ad desc");
                appAd.setDescription2("mod sample ad desc2");
                appAd.setUrl("http://www.yahoo.mod.co.jp/");
                appAd.setDisplayUrl("www.yahoo.mod.co.jp");

                adGroupAd.setAdName("SampleAppAd_UpdateOn_" + SoapUtils.getCurrentTimestamp());
                adGroupAd.setAd(appAd);
            }

            adGroupAdOperation.getOperand().add(adGroupAd);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupAdService::mutate(SET)");
        System.out.println("############################################");

        Holder<AdGroupAdReturnValue> adGroupAdReturnValueHolder = new Holder<AdGroupAdReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupAdServiceInterface adGroupAdService = SoapUtils.createServiceInterface(AdGroupAdServiceInterface.class, AdGroupAdService.class);
        adGroupAdService.mutate(adGroupAdOperation, adGroupAdReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupAdServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupAdReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupAdService Set");
        }

        // Display
        for (AdGroupAdValues adGroupAdValues : adGroupAdReturnValueHolder.value.getValues()) {
            if (adGroupAdValues.isOperationSucceeded()) {
                display(adGroupAdValues.getAdGroupAd());
            } else {
                SoapUtils.displayErrors(new AdGroupAdServiceErrorEntityFactory(adGroupAdValues.getError()), true);
            }
        }

        // Response
        return adGroupAdReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupAdService REMOVE.
     *
     * @param accountId           Account ID
     * @param campaignId          Campaign ID
     * @param adGroupId           Ad group ID
     * @param adGroupAdValuesList AdGroupAdValues entity for remove.
     * @return AdGroupAdValues
     * @throws Exception
     */
    public static List<AdGroupAdValues> remove(long accountId, long campaignId, long adGroupId, List<AdGroupAdValues> adGroupAdValuesList) throws Exception {

        // Set Operation
        AdGroupAdOperation adGroupAdOperation = new AdGroupAdOperation();
        adGroupAdOperation.setOperator(Operator.REMOVE);
        adGroupAdOperation.setAccountId(accountId);
        adGroupAdOperation.setCampaignId(campaignId);
        adGroupAdOperation.setAdGroupId(adGroupId);

        // Set Operand
        for (AdGroupAdValues adGroupAdValues : adGroupAdValuesList) {

            AdGroupAd adGroupAd = new AdGroupAd();
            adGroupAd.setAccountId(adGroupAdValues.getAdGroupAd().getAccountId());
            adGroupAd.setCampaignId(adGroupAdValues.getAdGroupAd().getCampaignId());
            adGroupAd.setAdGroupId(adGroupAdValues.getAdGroupAd().getAdGroupId());
            adGroupAd.setAdId(adGroupAdValues.getAdGroupAd().getAdId());

            adGroupAdOperation.getOperand().add(adGroupAd);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupAdService::mutate(REMOVE)");
        System.out.println("############################################");

        Holder<AdGroupAdReturnValue> adGroupAdReturnValueHolder = new Holder<AdGroupAdReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupAdServiceInterface adGroupAdService = SoapUtils.createServiceInterface(AdGroupAdServiceInterface.class, AdGroupAdService.class);
        adGroupAdService.mutate(adGroupAdOperation, adGroupAdReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupAdServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupAdReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupAdService Remove");
        }

        // Display
        for (AdGroupAdValues adGroupAdValues : adGroupAdReturnValueHolder.value.getValues()) {
            if (adGroupAdValues.isOperationSucceeded()) {
                display(adGroupAdValues.getAdGroupAd());
            } else {
                SoapUtils.displayErrors(new AdGroupAdServiceErrorEntityFactory(adGroupAdValues.getError()), true);
            }
        }

        // Response
        return adGroupAdReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupAdService GET.
     *
     * @param accountId           Account ID
     * @param campaignId          Campaign ID
     * @param adGroupId           Ad group ID
     * @param adGroupAdValuesList AdGroupAdValues entity for get.
     * @return AdGroupAdValues
     * @throws Exception
     */
    public static List get(long accountId, long campaignId, long adGroupId, List<AdGroupAdValues> adGroupAdValuesList) throws Exception {

        // Set Selector
        AdGroupAdSelector adGroupAdSelector = new AdGroupAdSelector();
        adGroupAdSelector.setAccountId(accountId);
        adGroupAdSelector.getCampaignIds().add(campaignId);
        adGroupAdSelector.getAdGroupIds().add(adGroupId);
        for (AdGroupAdValues adGroupAdValues : adGroupAdValuesList) {
            adGroupAdSelector.getAdIds().add((adGroupAdValues.getAdGroupAd().getAdId()));
        }
        adGroupAdSelector.getUserStatuses().addAll(Arrays.asList(
                UserStatus.ACTIVE,
                UserStatus.PAUSED
        ));
        adGroupAdSelector.getApprovalStatuses().addAll(Arrays.asList(
                ApprovalStatus.APPROVED,
                ApprovalStatus.APPROVED_WITH_REVIEW,
                ApprovalStatus.REVIEW,
                ApprovalStatus.POST_DISAPPROVED,
                ApprovalStatus.PRE_DISAPPROVED
        ));
        Paging paging = new Paging();
        paging.setStartIndex(1);
        paging.setNumberResults(20);
        adGroupAdSelector.setPaging(paging);

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupAdService::get");
        System.out.println("############################################");

        Holder<AdGroupAdPage> adGroupAdPage = new Holder<AdGroupAdPage>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupAdServiceInterface adGroupAdService = SoapUtils.createServiceInterface(AdGroupAdServiceInterface.class, AdGroupAdService.class);
        adGroupAdService.get(adGroupAdSelector, adGroupAdPage, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupAdServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupAdPage.value == null) {
            throw new Exception("NoDataResponse:AdGroupAdService Get");
        }

        // Display
        for (AdGroupAdValues adGroupAdValues : adGroupAdPage.value.getValues()) {
            if (adGroupAdValues.isOperationSucceeded()) {
                display(adGroupAdValues.getAdGroupAd());
            } else {
                SoapUtils.displayErrors(new AdGroupAdServiceErrorEntityFactory(adGroupAdValues.getError()), true);
            }
        }

        // Response
        return adGroupAdPage.value.getValues();
    }

    /**
     * display AdGroupAd entity to stdout.
     *
     * @param adGroupAd AdGroupAd entity for display.
     */
    public static void display(AdGroupAd adGroupAd) {

        System.out.println("accountId = " + adGroupAd.getAccountId());
        System.out.println("campaignId = " + adGroupAd.getCampaignId());
        System.out.println("campaignName = " + adGroupAd.getCampaignName());
        System.out.println("adGroupId = " + adGroupAd.getAdGroupId());
        System.out.println("adGroupName = " + adGroupAd.getAdGroupName());
        System.out.println("adId = " + adGroupAd.getAdId());
        System.out.println("adName = " + adGroupAd.getAdName());
        System.out.println("userStatus = " + adGroupAd.getUserStatus());
        System.out.println("approvalStatus = " + adGroupAd.getApprovalStatus());

        if (adGroupAd.getAd() != null) {
            Ad ad = adGroupAd.getAd();
            if (ad instanceof TextAd2) {
                TextAd2 textAd2 = (TextAd2) ad;
                System.out.println("ad/type = " + textAd2.getType());
                System.out.println("ad/url = " + textAd2.getUrl());
                System.out.println("ad/displayUrl = " + textAd2.getDisplayUrl());
                System.out.println("ad/headline = " + textAd2.getHeadline());
                System.out.println("ad/description = " + textAd2.getDescription());
                System.out.println("ad/description2 = " + textAd2.getDescription2());
                System.out.println("ad/devicePreference = " + textAd2.getDevicePreference());
            } else if (ad instanceof MobileAd) {
                MobileAd mobileAd = (MobileAd) ad;
                System.out.println("ad/type = " + mobileAd.getType().toString());
                System.out.println("ad/url = " + mobileAd.getUrl());
                System.out.println("ad/displayUrl = " + mobileAd.getDisplayUrl());
                System.out.println("ad/headline = " + mobileAd.getHeadline());
                System.out.println("ad/description = " + mobileAd.getDescription());
                if (mobileAd.getMobileCarriers() != null) {
                    for (CarrierName carrierName : mobileAd.getMobileCarriers()) {
                        System.out.println("ad/mobileCarriers = " + carrierName);
                    }
                }
            } else if (ad instanceof AppAd) {
                AppAd appAd = (AppAd) ad;
                System.out.println("ad/type = " + appAd.getType().toString());
                System.out.println("ad/url = " + appAd.getUrl());
                System.out.println("ad/displayUrl = " + appAd.getDisplayUrl());
                System.out.println("ad/headline = " + appAd.getHeadline());
                System.out.println("ad/description = " + appAd.getDescription());
                System.out.println("ad/description2 = " + appAd.getDescription2());
                System.out.println("ad/appStore = " + appAd.getAppStore());
                System.out.println("ad/appId = " + appAd.getAppId());
                System.out.println("ad/devicePreference = " + appAd.getDevicePreference());
            }
        }
        System.out.println("---------");
    }
}
