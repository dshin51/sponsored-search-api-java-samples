package adSample;

import error.impl.AdGroupServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.AdGroupService.*;
import jp.yahooapis.ss.V5.AdGroupService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Program for AdGroupService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class AdGroupServiceSample {

    /**
     * main method for AdGroupServiceSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();
            long campaignId = SoapUtils.getCampaignId();
            long biddingStrategyId = SoapUtils.getBiddingStrategyId();

            // AdGroupService ADD
            List<AdGroupValues> adGroupValues = add(accountId, campaignId, biddingStrategyId);

            // AdGroupService GET
            get(accountId, campaignId, adGroupValues);

            // AdGroupService SET
            set(accountId, campaignId, adGroupValues, biddingStrategyId);

            // AdGroupService REMOVE
            remove(accountId, campaignId, adGroupValues);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sample Program for AdGroupService ADD.
     *
     * @param accountId         Account ID
     * @param campaignId        Campaign ID
     * @param biddingStrategyId Auto bidding ID
     * @return AdGroupValues
     * @throws Exception
     */
    public static List<AdGroupValues> add(long accountId, long campaignId, long biddingStrategyId) throws Exception {

        // Set Bid
        Bid bit = new Bid();
        bit.setMaxCpc((long) 120);

        // Set AutoBidding
        BiddingStrategy autoBiddingStrategy = new BiddingStrategy();
        autoBiddingStrategy.setBiddingStrategyId(biddingStrategyId);
        autoBiddingStrategy.setInitialBid(bit);

        // Set ManualCpc
        BiddingStrategy manualCpcStrategy = new BiddingStrategy();
        manualCpcStrategy.setBiddingStrategyType(BiddingStrategyType.MANUAL_CPC);
        manualCpcStrategy.setInitialBid(bit);

        // Set AutoBidding AdGroup
        AdGroup autoBiddingAdGroup = new AdGroup();
        autoBiddingAdGroup.setAccountId(accountId);
        autoBiddingAdGroup.setCampaignId(campaignId);
        autoBiddingAdGroup.setAdGroupName("SampleAutoBiddingAdGroup_CreateOn_" + SoapUtils.getCurrentTimestamp());
        autoBiddingAdGroup.setUserStatus(UserStatus.ACTIVE);
        autoBiddingAdGroup.setBiddingStrategyConfiguration(autoBiddingStrategy);

        // Set ManualCpc AdGroup
        AdGroup manualCpcAdGroup = new AdGroup();
        manualCpcAdGroup.setAccountId(accountId);
        manualCpcAdGroup.setCampaignId(campaignId);
        manualCpcAdGroup.setAdGroupName("SampleManualCpcAdGroup_CreateOn_" + SoapUtils.getCurrentTimestamp());
        manualCpcAdGroup.setUserStatus(UserStatus.ACTIVE);
        manualCpcAdGroup.setBiddingStrategyConfiguration(manualCpcStrategy);

        // Set Operation
        AdGroupOperation adGroupOperation = new AdGroupOperation();
        adGroupOperation.setOperator(Operator.ADD);
        adGroupOperation.setAccountId(accountId);
        adGroupOperation.setCampaignId(campaignId);
        adGroupOperation.getOperand().addAll(Arrays.asList(
                autoBiddingAdGroup,
                manualCpcAdGroup
        ));

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupService::mutate(ADD)");
        System.out.println("############################################");

        Holder<AdGroupReturnValue> adGroupReturnValueHolder = new Holder<AdGroupReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupServiceInterface adGroupService = SoapUtils.createServiceInterface(AdGroupServiceInterface.class, AdGroupService.class);
        adGroupService.mutate(adGroupOperation, adGroupReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupService Add");
        }

        // Display
        for (AdGroupValues adGroupValues : adGroupReturnValueHolder.value.getValues()) {
            if (adGroupValues.isOperationSucceeded()) {
                display(adGroupValues.getAdGroup());
            } else {
                SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(adGroupValues.getError()), true);
            }
        }

        // Response
        return adGroupReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupService SET.
     *
     * @param accountId         Account ID
     * @param campaignId        Campaign ID
     * @param adGroupValuesList AdGroupValues entity for set.
     * @param biddingStrategyId Auto bidding ID
     * @return AdGroupValues
     * @throws Exception
     */
    public static List<AdGroupValues> set(long accountId, long campaignId, List<AdGroupValues> adGroupValuesList, long biddingStrategyId) throws Exception {

        // Set Operation
        AdGroupOperation adGroupOperation = new AdGroupOperation();
        adGroupOperation.setOperator(Operator.SET);
        adGroupOperation.setAccountId(accountId);
        adGroupOperation.setCampaignId(campaignId);

        // Set Operand
        for (AdGroupValues adGroupValues : adGroupValuesList) {

            // Set Bid
            Bid bit = new Bid();
            bit.setMaxCpc((long) 200);

            // Set AutoBidding
            BiddingStrategy autoBiddingStrategy = new BiddingStrategy();
            autoBiddingStrategy.setBiddingStrategyId(biddingStrategyId);
            autoBiddingStrategy.setInitialBid(bit);

            // Set AutoBidding AdGroup
            AdGroup adGroup = new AdGroup();
            adGroup.setAccountId(adGroupValues.getAdGroup().getAccountId());
            adGroup.setCampaignId(adGroupValues.getAdGroup().getCampaignId());
            adGroup.setAdGroupId(adGroupValues.getAdGroup().getAdGroupId());
            adGroup.setCampaignName("Sample_UpdateOn_" + adGroupValues.getAdGroup().getAdGroupId() + "_" + SoapUtils.getCurrentTimestamp());
            adGroup.setUserStatus(UserStatus.PAUSED);
            // Change Auto Bidding Strategy
            adGroup.setBiddingStrategyConfiguration(autoBiddingStrategy);

            adGroupOperation.getOperand().add(adGroup);
      }

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupService::mutate(SET)");
        System.out.println("############################################");

        Holder<AdGroupReturnValue> adGroupReturnValueHolder = new Holder<AdGroupReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupServiceInterface adGroupService = SoapUtils.createServiceInterface(AdGroupServiceInterface.class, AdGroupService.class);
        adGroupService.mutate(adGroupOperation, adGroupReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupService Set");
        }

        // Display
        for (AdGroupValues adGroupValues : adGroupReturnValueHolder.value.getValues()) {
            if (adGroupValues.isOperationSucceeded()) {
                display(adGroupValues.getAdGroup());
            } else {
                SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(adGroupValues.getError()), true);
            }
        }

        // Response
        return adGroupReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupService REMOVE.
     *
     * @param accountId         Account ID
     * @param campaignId        Campaign ID
     * @param adGroupValuesList AdGroupValues entity for remove.
     * @return AdGroupValues
     * @throws Exception
     */
    public static List<AdGroupValues> remove(long accountId, long campaignId, List<AdGroupValues> adGroupValuesList) throws Exception {

        // Set Operation
        AdGroupOperation adGroupOperation = new AdGroupOperation();
        adGroupOperation.setOperator(Operator.REMOVE);
        adGroupOperation.setAccountId(accountId);
        adGroupOperation.setCampaignId(campaignId);

        // Set Operand
        for (AdGroupValues adGroupValues : adGroupValuesList) {

            AdGroup adGroup = new AdGroup();
            adGroup.setAccountId(adGroupValues.getAdGroup().getAccountId());
            adGroup.setCampaignId(adGroupValues.getAdGroup().getCampaignId());
            adGroup.setAdGroupId(adGroupValues.getAdGroup().getAdGroupId());

            adGroupOperation.getOperand().add(adGroup);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupService::mutate(REMOVE)");
        System.out.println("############################################");

        Holder<AdGroupReturnValue> adGroupReturnValueHolder = new Holder<AdGroupReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupServiceInterface adGroupService = SoapUtils.createServiceInterface(AdGroupServiceInterface.class, AdGroupService.class);
        adGroupService.mutate(adGroupOperation, adGroupReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupService Remove");
        }

        // Display
        for (AdGroupValues adGroupValues : adGroupReturnValueHolder.value.getValues()) {
            if (adGroupValues.isOperationSucceeded()) {
                display(adGroupValues.getAdGroup());
            } else {
                SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(adGroupValues.getError()), true);
            }
        }

        // Response
        return adGroupReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupService GET.
     *
     * @param accountId         Account ID
     * @param campaignId        Campaign ID
     * @param adGroupValuesList AdGroupValues entity for get.
     * @return AdGroupValues
     * @throws Exception
     */
    public static List get(long accountId, long campaignId, List<AdGroupValues> adGroupValuesList) throws Exception {

        // Set Selector
        AdGroupSelector adGroupSelector = new AdGroupSelector();
        adGroupSelector.setAccountId(accountId);
        adGroupSelector.getCampaignIds().add(campaignId);
        for (AdGroupValues adGroupValues : adGroupValuesList) {
            adGroupSelector.getAdGroupIds().add((adGroupValues.getAdGroup().getAdGroupId()));
        }
        adGroupSelector.getUserStatuses().addAll(Arrays.asList(
                UserStatus.ACTIVE,
                UserStatus.PAUSED
        ));
        Paging paging = new Paging();
        paging.setStartIndex(1);
        paging.setNumberResults(20);
        adGroupSelector.setPaging(paging);

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupService::get");
        System.out.println("############################################");

        Holder<AdGroupPage> adGroupPageHolder = new Holder<AdGroupPage>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupServiceInterface adGroupService = SoapUtils.createServiceInterface(AdGroupServiceInterface.class, AdGroupService.class);
        adGroupService.get(adGroupSelector, adGroupPageHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupPageHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupService Get");
        }

        // Display
        for (AdGroupValues adGroupValues : adGroupPageHolder.value.getValues()) {
            if (adGroupValues.isOperationSucceeded()) {
                display(adGroupValues.getAdGroup());
            } else {
                SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(adGroupValues.getError()), true);
            }
        }

        // Response
        return adGroupPageHolder.value.getValues();
    }

    /**
     * display AdGroup entity to stdout.
     *
     * @param adGroup AdGroup entity for display.
     */
    public static void display(AdGroup adGroup) {

        System.out.println("accountId = " + adGroup.getAccountId());
        System.out.println("campaignId = " + adGroup.getCampaignId());
        System.out.println("campaignName = " + adGroup.getCampaignName());
        System.out.println("adGroupId = " + adGroup.getAdGroupId());
        System.out.println("adGroupName = " + adGroup.getAdGroupName());
        System.out.println("userStatus = " + adGroup.getUserStatus());

        if (adGroup.getBiddingStrategyConfiguration() != null) {
            System.out.println("biddingStrategyConfiguration/biddingStrategyId = " + adGroup.getBiddingStrategyConfiguration().getBiddingStrategyId());
            System.out.println("biddingStrategyConfiguration/biddingStrategyName = " + adGroup.getBiddingStrategyConfiguration().getBiddingStrategyName());
            System.out.println("biddingStrategyConfiguration/biddingStrategyType = " + adGroup.getBiddingStrategyConfiguration().getBiddingStrategyType());
            System.out.println("biddingStrategyConfiguration/biddingStrategySource = " + adGroup.getBiddingStrategyConfiguration().getBiddingStrategySource());

            if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof EnhancedCpcBiddingScheme) {
                EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = (EnhancedCpcBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(EnhancedCpcBiddingScheme)/biddingStrategyType = " + enhancedCpcBiddingScheme.getBiddingStrategyType());
            } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {
                PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = (PageOnePromotedBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/biddingStrategyType = " + pageOnePromotedBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidCeiling = " + pageOnePromotedBiddingScheme.getBidCeiling());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidMultiplier = " + pageOnePromotedBiddingScheme.getBidMultiplier());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidChangesForRaisesOnly = " + pageOnePromotedBiddingScheme.getBidChangesForRaisesOnly());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenBudgetConstrained = " + pageOnePromotedBiddingScheme.getRaiseBidWhenBudgetConstrained());
                System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenLowQualityScore = " + pageOnePromotedBiddingScheme.getRaiseBidWhenLowQualityScore());
            } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetCpaBiddingScheme) {
                TargetCpaBiddingScheme targetCpaBiddingScheme = (TargetCpaBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/biddingStrategyType = " + targetCpaBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/targetCpa = " + targetCpaBiddingScheme.getTargetCpa());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/bidCeiling = " + targetCpaBiddingScheme.getBidCeiling());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/bidFloor = " + targetCpaBiddingScheme.getBidFloor());
            } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetSpendBiddingScheme) {
                TargetSpendBiddingScheme targetSpendBiddingScheme = (TargetSpendBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetSpendBiddingScheme)/biddingStrategyType = " + targetSpendBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetSpendBiddingScheme)/bidCeiling = " + targetSpendBiddingScheme.getBidCeiling());
            } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetRoasBiddingScheme) {
                TargetRoasBiddingScheme targetRoasBiddingScheme = (TargetRoasBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/biddingStrategyType = " + targetRoasBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/targetRoas = " + targetRoasBiddingScheme.getTargetRoas());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/bidCeiling = " + targetRoasBiddingScheme.getBidCeiling());
                System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/bidFloor = " + targetRoasBiddingScheme.getBidFloor());
            } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof ManualCpcBiddingScheme) {
                ManualCpcBiddingScheme manualCpcBiddingScheme = (ManualCpcBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(ManualCpcBiddingScheme)/biddingStrategyType = " + manualCpcBiddingScheme.getBiddingStrategyType());
            } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof BudgetOptimizerBiddingScheme) {
                BudgetOptimizerBiddingScheme budgetOptimizerBiddingScheme = (BudgetOptimizerBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
                System.out.println("biddingStrategyConfiguration/biddingScheme(BudgetOptimizerBiddingScheme)/biddingStrategyType = " + budgetOptimizerBiddingScheme.getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingScheme(BudgetOptimizerBiddingScheme)/bidCeiling = " + budgetOptimizerBiddingScheme.getBidCeiling());
            }

            if (adGroup.getBiddingStrategyConfiguration().getInitialBid() != null) {
                System.out.println("biddingStrategyConfiguration/initialBid/maxCpc = " + adGroup.getBiddingStrategyConfiguration().getInitialBid().getMaxCpc());
                System.out.println("biddingStrategyConfiguration/initialBid/bidSource = " + adGroup.getBiddingStrategyConfiguration().getInitialBid().getBidSource());
            }

            if (adGroup.getBiddingStrategyConfiguration().getParentBiddingStrategyConfigurations() != null) {
                for (BiddingStrategy biddingStrategy : adGroup.getBiddingStrategyConfiguration().getParentBiddingStrategyConfigurations()) {
                    System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingStrategyId = " + biddingStrategy.getBiddingStrategyId());
                    System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingStrategyName = " + biddingStrategy.getBiddingStrategyName());
                    System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingStrategyType = " + biddingStrategy.getBiddingStrategyType());
                    System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingStrategySource = " + biddingStrategy.getBiddingStrategySource());

                    if (biddingStrategy.getBiddingScheme() instanceof EnhancedCpcBiddingScheme) {
                        EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = (EnhancedCpcBiddingScheme) biddingStrategy.getBiddingScheme();
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(EnhancedCpcBiddingScheme)/biddingStrategyType = " + enhancedCpcBiddingScheme.getBiddingStrategyType());
                    } else if (biddingStrategy.getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {
                        PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = (PageOnePromotedBiddingScheme) biddingStrategy.getBiddingScheme();
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/biddingStrategyType = " + pageOnePromotedBiddingScheme.getBiddingStrategyType());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/bidCeiling = " + pageOnePromotedBiddingScheme.getBidCeiling());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/bidMultiplier = " + pageOnePromotedBiddingScheme.getBidMultiplier());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/bidChangesForRaisesOnly = " + pageOnePromotedBiddingScheme.getBidChangesForRaisesOnly());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenBudgetConstrained = " + pageOnePromotedBiddingScheme.getRaiseBidWhenBudgetConstrained());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenLowQualityScore = " + pageOnePromotedBiddingScheme.getRaiseBidWhenLowQualityScore());
                    } else if (biddingStrategy.getBiddingScheme() instanceof TargetCpaBiddingScheme) {
                        TargetCpaBiddingScheme targetCpaBiddingScheme = (TargetCpaBiddingScheme) biddingStrategy.getBiddingScheme();
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetCpaBiddingScheme)/biddingStrategyType = " + targetCpaBiddingScheme.getBiddingStrategyType());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetCpaBiddingScheme)/targetCpa = " + targetCpaBiddingScheme.getTargetCpa());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetCpaBiddingScheme)/bidCeiling = " + targetCpaBiddingScheme.getBidCeiling());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetCpaBiddingScheme)/bidFloor = " + targetCpaBiddingScheme.getBidFloor());
                    } else if (biddingStrategy.getBiddingScheme() instanceof TargetSpendBiddingScheme) {
                        TargetSpendBiddingScheme targetSpendBiddingScheme = (TargetSpendBiddingScheme) biddingStrategy.getBiddingScheme();
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetSpendBiddingScheme)/biddingStrategyType = " + targetSpendBiddingScheme.getBiddingStrategyType());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetSpendBiddingScheme)/bidCeiling = " + targetSpendBiddingScheme.getBidCeiling());
                    } else if (biddingStrategy.getBiddingScheme() instanceof TargetRoasBiddingScheme) {
                        TargetRoasBiddingScheme targetRoasBiddingScheme = (TargetRoasBiddingScheme) biddingStrategy.getBiddingScheme();
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetRoasBiddingScheme)/biddingStrategyType = " + targetRoasBiddingScheme.getBiddingStrategyType());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetRoasBiddingScheme)/targetRoas = " + targetRoasBiddingScheme.getTargetRoas());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetRoasBiddingScheme)/bidCeiling = " + targetRoasBiddingScheme.getBidCeiling());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetRoasBiddingScheme)/bidFloor = " + targetRoasBiddingScheme.getBidFloor());
                    } else if (biddingStrategy.getBiddingScheme() instanceof ManualCpcBiddingScheme) {
                        ManualCpcBiddingScheme manualCpcBiddingScheme = (ManualCpcBiddingScheme) biddingStrategy.getBiddingScheme();
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(ManualCpcBiddingScheme)/biddingStrategyType = " + manualCpcBiddingScheme.getBiddingStrategyType());
                    } else if (biddingStrategy.getBiddingScheme() instanceof BudgetOptimizerBiddingScheme) {
                        BudgetOptimizerBiddingScheme budgetOptimizerBiddingScheme = (BudgetOptimizerBiddingScheme) biddingStrategy.getBiddingScheme();
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(BudgetOptimizerBiddingScheme)/biddingStrategyType = " + budgetOptimizerBiddingScheme.getBiddingStrategyType());
                        System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(BudgetOptimizerBiddingScheme)/bidCeiling = " + budgetOptimizerBiddingScheme.getBidCeiling());
                    }
                }
            }
        }

        System.out.println("---------");
    }
}