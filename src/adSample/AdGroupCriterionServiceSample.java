package adSample;

import error.impl.AdGroupCriterionServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.AdGroupCriterionService.*;
import jp.yahooapis.ss.V5.AdGroupCriterionService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Program for AdGroupCriterionService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class AdGroupCriterionServiceSample {

    /**
     * main method for AdGroupCriterionServiceSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();
            long campaignId = SoapUtils.getCampaignId();
            long adGroupId = SoapUtils.getAdGroupId();

            // AdGroupCriterionService ADD
            List<AdGroupCriterionValues> adGroupCriterionValues = add(accountId, campaignId, adGroupId);

            // AdGroupCriterionService GET
            get(accountId, campaignId, adGroupId, adGroupCriterionValues);

            // AdGroupCriterionService SET
            set(accountId, campaignId, adGroupId, adGroupCriterionValues);

            // AdGroupCriterionService REMOVE
            remove(accountId, campaignId, adGroupId, adGroupCriterionValues);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sample Program for AdGroupCriterionService ADD.
     *
     * @param accountId  Account ID
     * @param campaignId Campaign ID
     * @param adGroupId  Ad group ID
     * @return AdGroupCriterionValues
     * @throws Exception
     */
    public static List<AdGroupCriterionValues> add(long accountId, long campaignId, long adGroupId) throws Exception {

        // Set Keyword
        Keyword keyword = new Keyword();
        keyword.setType(CriterionType.KEYWORD);
        keyword.setText("sample text");
        keyword.setMatchType(KeywordMatchType.PHRASE);

        // Set Bid
        Bid bit = new Bid();
        bit.setMaxCpc((long) 100);
        BiddingStrategy biddingStrategy = new BiddingStrategy();
        biddingStrategy.setBid(bit);

        // Set BiddableAdGroupCriterion
        BiddableAdGroupCriterion biddableAdGroupCriterion = new BiddableAdGroupCriterion();
        biddableAdGroupCriterion.setAccountId(accountId);
        biddableAdGroupCriterion.setCampaignId(campaignId);
        biddableAdGroupCriterion.setAdGroupId(adGroupId);
        biddableAdGroupCriterion.setCriterionUse(CriterionUse.BIDDABLE);
        biddableAdGroupCriterion.setCriterion(keyword);
        biddableAdGroupCriterion.setDestinationUrl("http://www.yahoo.co.jp/");
        biddableAdGroupCriterion.setUserStatus(UserStatus.ACTIVE);
        biddableAdGroupCriterion.setBiddingStrategyConfiguration(biddingStrategy);

        // Set Operation
        AdGroupCriterionOperation adGroupCriterionOperation = new AdGroupCriterionOperation();
        adGroupCriterionOperation.setOperator(Operator.ADD);
        adGroupCriterionOperation.setAccountId(accountId);
        adGroupCriterionOperation.setCampaignId(campaignId);
        adGroupCriterionOperation.setAdGroupId(adGroupId);
        adGroupCriterionOperation.setCriterionUse(CriterionUse.BIDDABLE);
        adGroupCriterionOperation.getOperand().add(biddableAdGroupCriterion);

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupCriterionService::mutate(ADD)");
        System.out.println("############################################");

        Holder<AdGroupCriterionReturnValue> adGroupCriterionReturnValueHolder = new Holder<AdGroupCriterionReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupCriterionServiceInterface adGroupCriterionService = SoapUtils.createServiceInterface(AdGroupCriterionServiceInterface.class, AdGroupCriterionService.class);
        adGroupCriterionService.mutate(adGroupCriterionOperation, adGroupCriterionReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupCriterionServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupCriterionReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupCriterionService Add");
        }

        // Display
        for (AdGroupCriterionValues adGroupCriterionValues : adGroupCriterionReturnValueHolder.value.getValues()) {
            if (adGroupCriterionValues.isOperationSucceeded()) {
                display(adGroupCriterionValues.getAdGroupCriterion());
            } else {
                SoapUtils.displayErrors(new AdGroupCriterionServiceErrorEntityFactory(adGroupCriterionValues.getError()), true);
            }
        }

        // Response
        return adGroupCriterionReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupCriterionService SET.
     *
     * @param accountId                  Account ID
     * @param campaignId                 Campaign ID
     * @param adGroupId                  Ad group ID
     * @param adGroupCriterionValuesList AdGroupCriterionValues entity for set.
     * @return AdGroupCriterionValues
     * @throws Exception
     */
    public static List<AdGroupCriterionValues> set(long accountId, long campaignId, long adGroupId, List<AdGroupCriterionValues> adGroupCriterionValuesList) throws Exception {

        // Set Operation
        AdGroupCriterionOperation adGroupCriterionOperation = new AdGroupCriterionOperation();
        adGroupCriterionOperation.setOperator(Operator.SET);
        adGroupCriterionOperation.setAccountId(accountId);
        adGroupCriterionOperation.setCampaignId(campaignId);
        adGroupCriterionOperation.setCriterionUse(CriterionUse.BIDDABLE);
        adGroupCriterionOperation.setAdGroupId(adGroupId);

        // Set Operand
        for (AdGroupCriterionValues adGroupCriterionValues : adGroupCriterionValuesList) {

            // Set Keyword
            Keyword keyword = new Keyword();
            keyword.setCriterionId(adGroupCriterionValues.getAdGroupCriterion().getCriterion().getCriterionId());
            keyword.setType(adGroupCriterionValues.getAdGroupCriterion().getCriterion().getType());

            // Set Bid
            Bid bit = new Bid();
            bit.setMaxCpc((long) 150);
            BiddingStrategy biddingStrategy = new BiddingStrategy();
            biddingStrategy.setBid(bit);

            // Set BiddableAdGroupCriterion
            BiddableAdGroupCriterion biddableAdGroupCriterion = new BiddableAdGroupCriterion();
            biddableAdGroupCriterion.setAccountId(adGroupCriterionValues.getAdGroupCriterion().getAccountId());
            biddableAdGroupCriterion.setCampaignId(adGroupCriterionValues.getAdGroupCriterion().getCampaignId());
            biddableAdGroupCriterion.setAdGroupId(adGroupCriterionValues.getAdGroupCriterion().getAdGroupId());
            biddableAdGroupCriterion.setCriterion(keyword);
            biddableAdGroupCriterion.setUserStatus(UserStatus.PAUSED);
            biddableAdGroupCriterion.setCriterionUse(CriterionUse.BIDDABLE);
            biddableAdGroupCriterion.setBiddingStrategyConfiguration(biddingStrategy);

            adGroupCriterionOperation.getOperand().add(biddableAdGroupCriterion);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupCriterionService::mutate(SET)");
        System.out.println("############################################");

        Holder<AdGroupCriterionReturnValue> adGroupCriterionReturnValueHolder = new Holder<AdGroupCriterionReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupCriterionServiceInterface adGroupCriterionService = SoapUtils.createServiceInterface(AdGroupCriterionServiceInterface.class, AdGroupCriterionService.class);
        adGroupCriterionService.mutate(adGroupCriterionOperation, adGroupCriterionReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupCriterionServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupCriterionReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupCriterionService Set");
        }

        // Display
        for (AdGroupCriterionValues adGroupCriterionValues : adGroupCriterionReturnValueHolder.value.getValues()) {
            if (adGroupCriterionValues.isOperationSucceeded()) {
                display(adGroupCriterionValues.getAdGroupCriterion());
            } else {
                SoapUtils.displayErrors(new AdGroupCriterionServiceErrorEntityFactory(adGroupCriterionValues.getError()), true);
            }
        }

        // Response
        return adGroupCriterionReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupCriterionService REMOVE.
     *
     * @param accountId                  Account ID
     * @param campaignId                 Campaign ID
     * @param adGroupId                  Ad group ID
     * @param adGroupCriterionValuesList AdGroupCriterionValues entity for remove.
     * @return AdGroupCriterionValues
     * @throws Exception
     */
    public static List<AdGroupCriterionValues> remove(long accountId, long campaignId, long adGroupId, List<AdGroupCriterionValues> adGroupCriterionValuesList) throws Exception {

        // Set Operation
        AdGroupCriterionOperation adGroupCriterionOperation = new AdGroupCriterionOperation();
        adGroupCriterionOperation.setOperator(Operator.REMOVE);
        adGroupCriterionOperation.setAccountId(accountId);
        adGroupCriterionOperation.setCampaignId(campaignId);
        adGroupCriterionOperation.setCriterionUse(CriterionUse.BIDDABLE);
        adGroupCriterionOperation.setAdGroupId(adGroupId);

        // Set Operand
        for (AdGroupCriterionValues adGroupCriterionValues : adGroupCriterionValuesList) {

            // Set Keyword
            Keyword keyword = new Keyword();
            keyword.setCriterionId(adGroupCriterionValues.getAdGroupCriterion().getCriterion().getCriterionId());
            keyword.setType(adGroupCriterionValues.getAdGroupCriterion().getCriterion().getType());

            AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
            adGroupCriterion.setAccountId(adGroupCriterionValues.getAdGroupCriterion().getAccountId());
            adGroupCriterion.setCampaignId(adGroupCriterionValues.getAdGroupCriterion().getCampaignId());
            adGroupCriterion.setAdGroupId(adGroupCriterionValues.getAdGroupCriterion().getAdGroupId());
            adGroupCriterion.setCriterionUse(adGroupCriterionValues.getAdGroupCriterion().getCriterionUse());
            adGroupCriterion.setCriterion(keyword);

            adGroupCriterionOperation.getOperand().add(adGroupCriterion);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupCriterionService::mutate(REMOVE)");
        System.out.println("############################################");

        Holder<AdGroupCriterionReturnValue> adGroupCriterionReturnValueHolder = new Holder<AdGroupCriterionReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupCriterionServiceInterface adGroupCriterionService = SoapUtils.createServiceInterface(AdGroupCriterionServiceInterface.class, AdGroupCriterionService.class);
        adGroupCriterionService.mutate(adGroupCriterionOperation, adGroupCriterionReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupCriterionServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupCriterionReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:AdGroupCriterionService Remove");
        }

        // Display
        for (AdGroupCriterionValues adGroupCriterionValues : adGroupCriterionReturnValueHolder.value.getValues()) {
            if (adGroupCriterionValues.isOperationSucceeded()) {
                display(adGroupCriterionValues.getAdGroupCriterion());
            } else {
                SoapUtils.displayErrors(new AdGroupCriterionServiceErrorEntityFactory(adGroupCriterionValues.getError()), true);
            }
        }

        // Response
        return adGroupCriterionReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for AdGroupCriterionService GET.
     *
     * @param accountId                  Account ID
     * @param campaignId                 Campaign ID
     * @param adGroupId                  Ad group ID
     * @param adGroupCriterionValuesList AdGroupCriterionValues entity for get.
     * @return AdGroupCriterionValues
     * @throws Exception
     */
    public static List get(long accountId, long campaignId, long adGroupId, List<AdGroupCriterionValues> adGroupCriterionValuesList) throws Exception {

        // Set Selector
        AdGroupCriterionSelector adGroupCriterionSelector = new AdGroupCriterionSelector();
        adGroupCriterionSelector.setAccountId(accountId);
        adGroupCriterionSelector.getCampaignIds().add(campaignId);
        adGroupCriterionSelector.getAdGroupIds().add(adGroupId);
        for (AdGroupCriterionValues adGroupCriterionValues : adGroupCriterionValuesList) {
            adGroupCriterionSelector.getCriterionIds().add((adGroupCriterionValues.getAdGroupCriterion().getCriterion().getCriterionId()));
        }
        adGroupCriterionSelector.getUserStatuses().addAll(Arrays.asList(
                UserStatus.ACTIVE,
                UserStatus.PAUSED
        ));
        adGroupCriterionSelector.setCriterionUse(CriterionUse.BIDDABLE);
        adGroupCriterionSelector.getApprovalStatuses().addAll(Arrays.asList(
                ApprovalStatus.APPROVED,
                ApprovalStatus.APPROVED_WITH_REVIEW,
                ApprovalStatus.REVIEW,
                ApprovalStatus.POST_DISAPPROVED,
                ApprovalStatus.PRE_DISAPPROVED
        ));
        Paging paging = new Paging();
        paging.setStartIndex(1);
        paging.setNumberResults(20);
        adGroupCriterionSelector.setPaging(paging);

        // Call API
        System.out.println("############################################");
        System.out.println("AdGroupCriterionService::get");
        System.out.println("############################################");

        Holder<AdGroupCriterionPage> adGroupCriterionPage = new Holder<AdGroupCriterionPage>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        AdGroupCriterionServiceInterface adGroupCriterionService = SoapUtils.createServiceInterface(AdGroupCriterionServiceInterface.class, AdGroupCriterionService.class);
        adGroupCriterionService.get(adGroupCriterionSelector, adGroupCriterionPage, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new AdGroupCriterionServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (adGroupCriterionPage.value == null) {
            throw new Exception("NoDataResponse:AdGroupCriterionService Get");
        }

        // Display
        for (AdGroupCriterionValues adGroupCriterionValues : adGroupCriterionPage.value.getValues()) {
            if (adGroupCriterionValues.isOperationSucceeded()) {
                display(adGroupCriterionValues.getAdGroupCriterion());
            } else {
                SoapUtils.displayErrors(new AdGroupCriterionServiceErrorEntityFactory(adGroupCriterionValues.getError()), true);
            }
        }

        // Response
        return adGroupCriterionPage.value.getValues();
    }

    /**
     * display AdGroupCriterion entity to stdout.
     *
     * @param adGroupCriterion AdGroupCriterion entity for display.
     */
    public static void display(AdGroupCriterion adGroupCriterion) {

        System.out.println("accountId = " + adGroupCriterion.getAccountId());
        System.out.println("campaignId = " + adGroupCriterion.getCampaignId());
        System.out.println("campaignName = " + adGroupCriterion.getCampaignName());
        System.out.println("adGroupId = " + adGroupCriterion.getAdGroupId());
        System.out.println("adGroupName = " + adGroupCriterion.getAdGroupName());
        System.out.println("criterionUse = " + adGroupCriterion.getCriterionUse());
        if (adGroupCriterion.getCriterion() != null) {
            Criterion criterion = adGroupCriterion.getCriterion();
            System.out.println("criterion/criterionId = " + criterion.getCriterionId());
            System.out.println("criterion/type = " + criterion.getType());
        }

        if (adGroupCriterion instanceof BiddableAdGroupCriterion) {
            BiddableAdGroupCriterion biddableAdGroupCriterion = (BiddableAdGroupCriterion) adGroupCriterion;
            System.out.println("userStatus = " + biddableAdGroupCriterion.getUserStatus());
            System.out.println("approvalStatus = " + biddableAdGroupCriterion.getApprovalStatus());
            if (biddableAdGroupCriterion.getDisapprovalReasonCodes() != null) {
                for (String disapprovalReasonCode : biddableAdGroupCriterion.getDisapprovalReasonCodes()) {
                    System.out.println("disapprovalReasonCode = " + disapprovalReasonCode);
                }
            }
            System.out.println("destinationUrl = " + biddableAdGroupCriterion.getDestinationUrl());

            if (biddableAdGroupCriterion.getBiddingStrategyConfiguration() != null) {
                System.out.println("biddingStrategyConfiguration/biddingStrategyId = " + biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingStrategyId());
                System.out.println("biddingStrategyConfiguration/biddingStrategyName = " + biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingStrategyName());
                System.out.println("biddingStrategyConfiguration/biddingStrategyType = " + biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingStrategyType());
                System.out.println("biddingStrategyConfiguration/biddingStrategySource = " + biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingStrategySource());

                if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme() instanceof EnhancedCpcBiddingScheme) {
                    EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = (EnhancedCpcBiddingScheme) biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme();
                    System.out.println("biddingStrategyConfiguration/biddingScheme(EnhancedCpcBiddingScheme)/biddingStrategyType = " + enhancedCpcBiddingScheme.getBiddingStrategyType());
                } else if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {
                    PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = (PageOnePromotedBiddingScheme) biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme();
                    System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/biddingStrategyType = " + pageOnePromotedBiddingScheme.getBiddingStrategyType());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidCeiling = " + pageOnePromotedBiddingScheme.getBidCeiling());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidMultiplier = " + pageOnePromotedBiddingScheme.getBidMultiplier());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidChangesForRaisesOnly = " + pageOnePromotedBiddingScheme.getBidChangesForRaisesOnly());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenBudgetConstrained = " + pageOnePromotedBiddingScheme.getRaiseBidWhenBudgetConstrained());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenLowQualityScore = " + pageOnePromotedBiddingScheme.getRaiseBidWhenLowQualityScore());
                } else if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetCpaBiddingScheme) {
                    TargetCpaBiddingScheme targetCpaBiddingScheme = (TargetCpaBiddingScheme) biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme();
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/biddingStrategyType = " + targetCpaBiddingScheme.getBiddingStrategyType());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/targetCpa = " + targetCpaBiddingScheme.getTargetCpa());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/bidCeiling = " + targetCpaBiddingScheme.getBidCeiling());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/bidFloor = " + targetCpaBiddingScheme.getBidFloor());
                } else if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetSpendBiddingScheme) {
                    TargetSpendBiddingScheme targetSpendBiddingScheme = (TargetSpendBiddingScheme) biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme();
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetSpendBiddingScheme)/biddingStrategyType = " + targetSpendBiddingScheme.getBiddingStrategyType());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetSpendBiddingScheme)/bidCeiling = " + targetSpendBiddingScheme.getBidCeiling());
                } else if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetRoasBiddingScheme) {
                    TargetRoasBiddingScheme targetRoasBiddingScheme = (TargetRoasBiddingScheme) biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme();
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/biddingStrategyType = " + targetRoasBiddingScheme.getBiddingStrategyType());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/targetRoas = " + targetRoasBiddingScheme.getTargetRoas());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/bidCeiling = " + targetRoasBiddingScheme.getBidCeiling());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/bidFloor = " + targetRoasBiddingScheme.getBidFloor());
                } else if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme() instanceof ManualCpcBiddingScheme) {
                    ManualCpcBiddingScheme manualCpcBiddingScheme = (ManualCpcBiddingScheme) biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme();
                    System.out.println("biddingStrategyConfiguration/biddingScheme(ManualCpcBiddingScheme)/biddingStrategyType = " + manualCpcBiddingScheme.getBiddingStrategyType());
                } else if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme() instanceof BudgetOptimizerBiddingScheme) {
                    BudgetOptimizerBiddingScheme budgetOptimizerBiddingScheme = (BudgetOptimizerBiddingScheme) biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBiddingScheme();
                    System.out.println("biddingStrategyConfiguration/biddingScheme(BudgetOptimizerBiddingScheme)/biddingStrategyType = " + budgetOptimizerBiddingScheme.getBiddingStrategyType());
                    System.out.println("biddingStrategyConfiguration/biddingScheme(BudgetOptimizerBiddingScheme)/bidCeiling = " + budgetOptimizerBiddingScheme.getBidCeiling());
                }

                if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBid() != null) {
                    System.out.println("biddingStrategyConfiguration/bid/maxCpc = " + biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBid().getMaxCpc());
                    System.out.println("biddingStrategyConfiguration/bid/bidSource = " + biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBid().getBidSource());
                    System.out.println("biddingStrategyConfiguration/bid/adGroupMaxCpc = " + biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBid().getAdGroupMaxCpc());
                    System.out.println("biddingStrategyConfiguration/bid/keywordMaxCpc = " + biddableAdGroupCriterion.getBiddingStrategyConfiguration().getBid().getKeywordMaxCpc());

                }

                if (biddableAdGroupCriterion.getBiddingStrategyConfiguration().getParentBiddingStrategyConfigurations() != null) {
                    for (BiddingStrategy biddingStrategy : biddableAdGroupCriterion.getBiddingStrategyConfiguration().getParentBiddingStrategyConfigurations()) {
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
        }

        System.out.println("---------");
    }
}
