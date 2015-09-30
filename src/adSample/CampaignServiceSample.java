package adSample;

import error.impl.CampaignServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.CampaignService.*;
import jp.yahooapis.ss.V5.CampaignService.BiddingStrategy;
import jp.yahooapis.ss.V5.CampaignService.BiddingStrategyType;
import jp.yahooapis.ss.V5.CampaignService.EnhancedCpcBiddingScheme;
import jp.yahooapis.ss.V5.CampaignService.Error;
import jp.yahooapis.ss.V5.CampaignService.Operator;
import jp.yahooapis.ss.V5.CampaignService.PageOnePromotedBiddingScheme;
import jp.yahooapis.ss.V5.CampaignService.Paging;
import jp.yahooapis.ss.V5.CampaignService.TargetCpaBiddingScheme;
import jp.yahooapis.ss.V5.CampaignService.TargetRoasBiddingScheme;
import jp.yahooapis.ss.V5.CampaignService.TargetSpendBiddingScheme;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Program for CampaignService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class CampaignServiceSample {

    /**
     * main method for CampaignServiceSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();
            long biddingStrategyId = SoapUtils.getBiddingStrategyId();

            // CampaignService ADD
            List<CampaignValues> campaignValues = add(accountId, biddingStrategyId);

            // CampaignService GET
            get(accountId, campaignValues);

            // CampaignService SET
            set(accountId, campaignValues, biddingStrategyId);

            // CampaignService REMOVE
            remove(accountId, campaignValues);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sample Program for CampaignService ADD.
     *
     * @param accountId         Account ID
     * @param biddingStrategyId Auto bidding ID
     * @return CampaignValues
     * @throws Exception
     */
    public static List<CampaignValues> add(long accountId, long biddingStrategyId) throws Exception {

        // Set Budget
        Budget budget = new Budget();
        budget.setPeriod(BudgetPeriod.DAILY);
        budget.setAmount((long) 1000);
        budget.setDeliveryMethod(BudgetDeliveryMethod.STANDARD);

        // Set GeoTargetTypeSetting
        GeoTargetTypeSetting geoTargetTypeSetting = new GeoTargetTypeSetting();
        geoTargetTypeSetting.setType(SettingType.GEO_TARGET_TYPE_SETTING);
        geoTargetTypeSetting.setPositiveGeoTargetType(GeoTargetType.AREA_OF_INTENT);
        geoTargetTypeSetting.setNegativeGeoTargetType(GeoTargetType.LOCATION_OF_PRESENCE);

        // Set AutoBidding
        BiddingStrategy autoBiddingStrategy = new BiddingStrategy();
        autoBiddingStrategy.setBiddingStrategyId(biddingStrategyId);

        // Set ManualCpc
        BiddingStrategy manualCpcStrategy = new BiddingStrategy();
        manualCpcStrategy.setBiddingStrategyType(BiddingStrategyType.MANUAL_CPC);

        // Set AutoBidding Campaign
        Campaign autoBiddingCampaign = new Campaign();
        autoBiddingCampaign.setAccountId(accountId);
        autoBiddingCampaign.setCampaignName("SampleAutoBiddingCampaign_CreateOn_" + SoapUtils.getCurrentTimestamp());
        autoBiddingCampaign.setUserStatus(UserStatus.ACTIVE);
        autoBiddingCampaign.setStartDate("20300101");
        autoBiddingCampaign.setEndDate("20301231");
        autoBiddingCampaign.setBudget(budget);
        autoBiddingCampaign.setBiddingStrategyConfiguration(autoBiddingStrategy);
        autoBiddingCampaign.setAdServingOptimizationStatus(AdServingOptimizationStatus.ROTATE_INDEFINITELY);
        autoBiddingCampaign.getSettings().add(geoTargetTypeSetting);

        // Set ManualCpc Campaign
        Campaign manualCpcCampaign = new Campaign();
        manualCpcCampaign.setAccountId(accountId);
        manualCpcCampaign.setCampaignName("SampleManualCpcCampaign_CreateOn_" + SoapUtils.getCurrentTimestamp());
        manualCpcCampaign.setUserStatus(UserStatus.ACTIVE);
        manualCpcCampaign.setStartDate("20300101");
        manualCpcCampaign.setEndDate("20301231");
        manualCpcCampaign.setBudget(budget);
        manualCpcCampaign.setBiddingStrategyConfiguration(manualCpcStrategy);
        manualCpcCampaign.setAdServingOptimizationStatus(AdServingOptimizationStatus.ROTATE_INDEFINITELY);
        manualCpcCampaign.getSettings().add(geoTargetTypeSetting);

        // Set Operation
        CampaignOperation campaignOperation = new CampaignOperation();
        campaignOperation.setOperator(Operator.ADD);
        campaignOperation.setAccountId(accountId);
        campaignOperation.getOperand().addAll(Arrays.asList(
                autoBiddingCampaign,
                manualCpcCampaign
        ));

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignService::mutate(ADD)");
        System.out.println("############################################");

        Holder<CampaignReturnValue> campaignReturnValueHolder = new Holder<CampaignReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignServiceInterface campaignService = SoapUtils.createServiceInterface(CampaignServiceInterface.class, CampaignService.class);
        campaignService.mutate(campaignOperation, campaignReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignService Add");
        }

        // Display
        for (CampaignValues campaignValues : campaignReturnValueHolder.value.getValues()) {
            if (campaignValues.isOperationSucceeded()) {
                display(campaignValues.getCampaign());
            } else {
                SoapUtils.displayErrors(new CampaignServiceErrorEntityFactory(campaignValues.getError()), true);
            }
        }

        // Response
        return campaignReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for CampaignService SET.
     *
     * @param accountId          Account ID
     * @param campaignValuesList CampaignValues entity for set.
     * @param biddingStrategyId  Auto bidding ID
     * @return CampaignValues
     * @throws Exception
     */
    public static List<CampaignValues> set(long accountId, List<CampaignValues> campaignValuesList, long biddingStrategyId) throws Exception {

        // Set Operation
        CampaignOperation campaignOperation = new CampaignOperation();
        campaignOperation.setOperator(Operator.SET);
        campaignOperation.setAccountId(accountId);

        // Set Operand
        for (CampaignValues campaignValues : campaignValuesList) {

            // Set Budget
            Budget budget = new Budget();
            budget.setPeriod(BudgetPeriod.DAILY);
            budget.setAmount((long) 2000);
            budget.setDeliveryMethod(BudgetDeliveryMethod.STANDARD);

            // Set AutoBidding
            BiddingStrategy autoBiddingStrategy = new BiddingStrategy();
            autoBiddingStrategy.setBiddingStrategyId(biddingStrategyId);

            Campaign campaign = new Campaign();
            campaign.setAccountId(campaignValues.getCampaign().getAccountId());
            campaign.setCampaignId(campaignValues.getCampaign().getCampaignId());
            campaign.setCampaignName("Sample_UpdateOn_" + campaignValues.getCampaign().getCampaignId() + "_" + SoapUtils.getCurrentTimestamp());
            campaign.setUserStatus(UserStatus.PAUSED);
            campaign.setStartDate("20300101");
            campaign.setEndDate("20301231");
            campaign.setBudget(budget);
            campaign.setAdServingOptimizationStatus(AdServingOptimizationStatus.OPTIMIZE);
            // Change Auto Bidding Strategy
            campaign.setBiddingStrategyConfiguration(autoBiddingStrategy);

            campaignOperation.getOperand().add(campaign);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignService::mutate(SET)");
        System.out.println("############################################");

        Holder<CampaignReturnValue> campaignReturnValueHolder = new Holder<CampaignReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignServiceInterface campaignService = SoapUtils.createServiceInterface(CampaignServiceInterface.class, CampaignService.class);
        campaignService.mutate(campaignOperation, campaignReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignService Set");
        }

        // Display
        for (CampaignValues campaignValues : campaignReturnValueHolder.value.getValues()) {
            if (campaignValues.isOperationSucceeded()) {
                display(campaignValues.getCampaign());
            } else {
                SoapUtils.displayErrors(new CampaignServiceErrorEntityFactory(campaignValues.getError()), true);
            }
        }

        // Response
        return campaignReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for CampaignService REMOVE.
     *
     * @param accountId          Account ID
     * @param campaignValuesList CampaignValues entity for remove.
     * @return CampaignValues
     * @throws Exception
     */
    public static List<CampaignValues> remove(long accountId, List<CampaignValues> campaignValuesList) throws Exception {

        // Set Operation
        CampaignOperation campaignOperation = new CampaignOperation();
        campaignOperation.setOperator(Operator.REMOVE);
        campaignOperation.setAccountId(accountId);

        // Set Operand
        for (CampaignValues campaignValues : campaignValuesList) {

            Campaign campaign = new Campaign();
            campaign.setAccountId(campaignValues.getCampaign().getAccountId());
            campaign.setCampaignId(campaignValues.getCampaign().getCampaignId());

            campaignOperation.getOperand().add(campaign);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignService::mutate(REMOVE)");
        System.out.println("############################################");

        Holder<CampaignReturnValue> campaignReturnValueHolder = new Holder<CampaignReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignServiceInterface campaignService = SoapUtils.createServiceInterface(CampaignServiceInterface.class, CampaignService.class);
        campaignService.mutate(campaignOperation, campaignReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignService Remove");
        }

        // Display
        for (CampaignValues campaignValues : campaignReturnValueHolder.value.getValues()) {
            if (campaignValues.isOperationSucceeded()) {
                display(campaignValues.getCampaign());
            } else {
                SoapUtils.displayErrors(new CampaignServiceErrorEntityFactory(campaignValues.getError()), true);
            }
        }

        // Response
        return campaignReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for CampaignService GET.
     *
     * @param accountId          Account ID
     * @param campaignValuesList CampaignValues entity for get.
     * @return CampaignValues
     * @throws Exception
     */
    public static List get(long accountId, List<CampaignValues> campaignValuesList) throws Exception {

        // Set Selector
        CampaignSelector campaignSelector = new CampaignSelector();
        campaignSelector.setAccountId(accountId);
        for (CampaignValues campaignValues : campaignValuesList) {
            campaignSelector.getCampaignIds().add((campaignValues.getCampaign().getCampaignId()));
        }
        campaignSelector.getUserStatuses().addAll(Arrays.asList(
                UserStatus.ACTIVE,
                UserStatus.PAUSED
        ));
        Paging paging = new Paging();
        paging.setStartIndex(1);
        paging.setNumberResults(20);
        campaignSelector.setPaging(paging);

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignService::get");
        System.out.println("############################################");

        Holder<CampaignPage> campaignPageHolder = new Holder<CampaignPage>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignServiceInterface CampaignService = SoapUtils.createServiceInterface(CampaignServiceInterface.class, CampaignService.class);
        CampaignService.get(campaignSelector, campaignPageHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignPageHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignService Get");
        }

        // Display
        for (CampaignValues CampaignValues : campaignPageHolder.value.getValues()) {
            if (CampaignValues.isOperationSucceeded()) {
                display(CampaignValues.getCampaign());
            } else {
                SoapUtils.displayErrors(new CampaignServiceErrorEntityFactory(CampaignValues.getError()), true);
            }
        }

        // Response
        return campaignPageHolder.value.getValues();
    }

    /**
     * display Campaign entity to stdout.
     *
     * @param campaign Campaign entity for display.
     */
    public static void display(Campaign campaign) {

        System.out.println("accountId = " + campaign.getAccountId());
        System.out.println("campaignId = " + campaign.getCampaignId());
        System.out.println("campaignName = " + campaign.getCampaignName());
        System.out.println("userStatus = " + campaign.getUserStatus());
        System.out.println("startDate = " + campaign.getStartDate());
        System.out.println("endDate = " + campaign.getEndDate());
        if (campaign.getBudget() != null) {
            System.out.println("budget/period = " + campaign.getBudget().getPeriod());
            System.out.println("budget/amount = " + campaign.getBudget().getAmount());
            System.out.println("budget/deliveryMethod = " + campaign.getBudget().getDeliveryMethod());
        }
        if (campaign.getBiddingStrategyConfiguration() != null) {
            System.out.println("biddingStrategyConfiguration/biddingStrategyId = " + campaign.getBiddingStrategyConfiguration().getBiddingStrategyId());
            System.out.println("biddingStrategyConfiguration/biddingStrategyName = " + campaign.getBiddingStrategyConfiguration().getBiddingStrategyName());
            System.out.println("biddingStrategyConfiguration/biddingStrategyType = " + campaign.getBiddingStrategyConfiguration().getBiddingStrategyType());
            System.out.println("biddingStrategyConfiguration/biddingStrategySource = " + campaign.getBiddingStrategyConfiguration().getBiddingStrategySource());

            if (campaign.getBiddingStrategyConfiguration().getBiddingScheme() instanceof EnhancedCpcBiddingScheme) {
                EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = (EnhancedCpcBiddingScheme) campaign.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(EnhancedCpcBiddingScheme)/biddingStrategyType = " + enhancedCpcBiddingScheme.getBiddingStrategyType());
            } else if (campaign.getBiddingStrategyConfiguration().getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {
                PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = (PageOnePromotedBiddingScheme) campaign.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/biddingStrategyType = " + pageOnePromotedBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidCeiling = " + pageOnePromotedBiddingScheme.getBidCeiling());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidMultiplier = " + pageOnePromotedBiddingScheme.getBidMultiplier());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidChangesForRaisesOnly = " + pageOnePromotedBiddingScheme.getBidChangesForRaisesOnly());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenBudgetConstrained = " + pageOnePromotedBiddingScheme.getRaiseBidWhenBudgetConstrained());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenLowQualityScore = " + pageOnePromotedBiddingScheme.getRaiseBidWhenLowQualityScore());
            } else if (campaign.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetCpaBiddingScheme) {
                TargetCpaBiddingScheme targetCpaBiddingScheme = (TargetCpaBiddingScheme) campaign.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/biddingStrategyType = " + targetCpaBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/targetCpa = " + targetCpaBiddingScheme.getTargetCpa());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/bidCeiling = " + targetCpaBiddingScheme.getBidCeiling());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/bidFloor = " + targetCpaBiddingScheme.getBidFloor());
            } else if (campaign.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetSpendBiddingScheme) {
                TargetSpendBiddingScheme targetSpendBiddingScheme = (TargetSpendBiddingScheme) campaign.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetSpendBiddingScheme)/biddingStrategyType = " + targetSpendBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetSpendBiddingScheme)/bidCeiling = " + targetSpendBiddingScheme.getBidCeiling());
            } else if (campaign.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetRoasBiddingScheme) {
                TargetRoasBiddingScheme targetRoasBiddingScheme = (TargetRoasBiddingScheme) campaign.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/biddingStrategyType = " + targetRoasBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/targetRoas = " + targetRoasBiddingScheme.getTargetRoas());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/bidCeiling = " + targetRoasBiddingScheme.getBidCeiling());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/bidFloor = " + targetRoasBiddingScheme.getBidFloor());
            } else if (campaign.getBiddingStrategyConfiguration().getBiddingScheme() instanceof ManualCpcBiddingScheme) {
                ManualCpcBiddingScheme manualCpcBiddingScheme = (ManualCpcBiddingScheme) campaign.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(ManualCpcBiddingScheme)/biddingStrategyType = " + manualCpcBiddingScheme.getBiddingStrategyType());
            } else if (campaign.getBiddingStrategyConfiguration().getBiddingScheme() instanceof BudgetOptimizerBiddingScheme) {
                BudgetOptimizerBiddingScheme budgetOptimizerBiddingScheme = (BudgetOptimizerBiddingScheme) campaign.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(BudgetOptimizerBiddingScheme)/biddingStrategyType = " + budgetOptimizerBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(BudgetOptimizerBiddingScheme)/bidCeiling = " + budgetOptimizerBiddingScheme.getBidCeiling());
            }
        }

        System.out.println("conversionOptimizerEligibility = " + campaign.getConversionOptimizerEligibility());
        System.out.println("adServingOptimizationStatus = " + campaign.getAdServingOptimizationStatus());

        if (campaign.getSettings() != null) {
            for (Settings settings : campaign.getSettings()) {
                if (settings instanceof GeoTargetTypeSetting) {
                    System.out.println("settings(GeoTargetTypeSetting)/type = " + settings.getType());
                    GeoTargetTypeSetting geoTargetTypeSetting = (GeoTargetTypeSetting) settings;
                    System.out.println("settings(GeoTargetTypeSetting)/positiveGeoTargetType = " + geoTargetTypeSetting.getPositiveGeoTargetType());
                    System.out.println("settings(GeoTargetTypeSetting)/negativeGeoTargetType = " + geoTargetTypeSetting.getNegativeGeoTargetType());

                } else if (settings instanceof KeywordMatchSetting) {
                    System.out.println("settings(KeywordMatchSetting)/type = " + settings.getType());
                    KeywordMatchSetting keywordMatchSetting = (KeywordMatchSetting) settings;
                    System.out.println("settings(KeywordMatchSetting)/optIn = " + keywordMatchSetting.getOptIn());
                }
            }
        }

        System.out.println("---------");
    }
}
