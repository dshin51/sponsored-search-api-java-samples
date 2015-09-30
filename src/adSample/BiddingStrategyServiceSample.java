package adSample;

import error.impl.BiddingStrategyServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.BiddingStrategyService.*;
import jp.yahooapis.ss.V5.BiddingStrategyService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Program for BiddingStrategyService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class BiddingStrategyServiceSample {

    /**
     * main method for BiddingStrategyServiceSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();

            // BiddingStrategyService ADD
            List<BiddingStrategyValues> biddingStrategyValues = add(accountId);

            // BiddingStrategyService GET
            get(accountId, biddingStrategyValues);

            // BiddingStrategyService SET
            set(accountId, biddingStrategyValues);

            // BiddingStrategyService REMOVE
            remove(accountId, biddingStrategyValues);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sample Program for BiddingStrategyService ADD.
     *
     * @param accountId Account ID
     * @return BiddingStrategyValues
     * @throws Exception
     */
    public static List<BiddingStrategyValues> add(long accountId) throws Exception {

        // Set EnhancedCpcBidding
        EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = new EnhancedCpcBiddingScheme();
        enhancedCpcBiddingScheme.setBiddingStrategyType(BiddingStrategyType.ENHANCED_CPC);

        BiddingStrategy enhancedCpcBidding = new BiddingStrategy();
        enhancedCpcBidding.setAccountId(accountId);
        enhancedCpcBidding.setBiddingStrategyName("SampleEnhancedCpc_CreateOn_" + SoapUtils.getCurrentTimestamp());
        enhancedCpcBidding.setBiddingScheme(enhancedCpcBiddingScheme);

        // Set PageOnePromotedBidding
        PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = new PageOnePromotedBiddingScheme();
        pageOnePromotedBiddingScheme.setBiddingStrategyType(BiddingStrategyType.PAGE_ONE_PROMOTED);
        pageOnePromotedBiddingScheme.setBidCeiling((long) 500);
        pageOnePromotedBiddingScheme.setBidMultiplier(1.00);
        pageOnePromotedBiddingScheme.setBidChangesForRaisesOnly(BidChangesForRaisesOnly.ACTIVE);
        pageOnePromotedBiddingScheme.setRaiseBidWhenBudgetConstrained(RaiseBidWhenBudgetConstrained.ACTIVE);
        pageOnePromotedBiddingScheme.setRaiseBidWhenLowQualityScore(RaiseBidWhenLowQualityScore.ACTIVE);

        BiddingStrategy pageOnePromotedBidding = new BiddingStrategy();
        pageOnePromotedBidding.setAccountId(accountId);
        pageOnePromotedBidding.setBiddingStrategyName("SamplePageOnePromoted_CreateOn_" + SoapUtils.getCurrentTimestamp());
        pageOnePromotedBidding.setBiddingScheme(pageOnePromotedBiddingScheme);

        // Set TargetCpaBidding
        TargetCpaBiddingScheme targetCpaBiddingScheme = new TargetCpaBiddingScheme();
        targetCpaBiddingScheme.setBiddingStrategyType(BiddingStrategyType.TARGET_CPA);
        targetCpaBiddingScheme.setTargetCpa((long) 500);
        targetCpaBiddingScheme.setBidCeiling((long) 700);

        BiddingStrategy targetCpaBidding = new BiddingStrategy();
        targetCpaBidding.setAccountId(accountId);
        targetCpaBidding.setBiddingStrategyName("SampleTargetCpa_CreateOn_" + SoapUtils.getCurrentTimestamp());
        targetCpaBidding.setBiddingScheme(targetCpaBiddingScheme);

        // Set TargetSpendBidding
        TargetSpendBiddingScheme targetSpendBiddingScheme = new TargetSpendBiddingScheme();
        targetSpendBiddingScheme.setBiddingStrategyType(BiddingStrategyType.TARGET_SPEND);
        targetSpendBiddingScheme.setBidCeiling((long) 700);

        BiddingStrategy targetSpendBidding = new BiddingStrategy();
        targetSpendBidding.setAccountId(accountId);
        targetSpendBidding.setBiddingStrategyName("SampleTargetSpend_CreateOn_" + SoapUtils.getCurrentTimestamp());
        targetSpendBidding.setBiddingScheme(targetSpendBiddingScheme);

        // Set TargetRoasBidding
        TargetRoasBiddingScheme targetRoasBiddingScheme = new TargetRoasBiddingScheme();
        targetRoasBiddingScheme.setBiddingStrategyType(BiddingStrategyType.TARGET_ROAS);
        targetRoasBiddingScheme.setTargetRoas(10.00);
        targetRoasBiddingScheme.setBidCeiling((long) 700);
        targetRoasBiddingScheme.setBidFloor((long) 600);

        BiddingStrategy targetRoasBidding = new BiddingStrategy();
        targetRoasBidding.setAccountId(accountId);
        targetRoasBidding.setBiddingStrategyName("SampleTargetRoas_CreateOn_" + SoapUtils.getCurrentTimestamp());
        targetRoasBidding.setBiddingScheme(targetRoasBiddingScheme);

        // Set Operation
        BiddingStrategyOperation biddingStrategyOperation = new BiddingStrategyOperation();
        biddingStrategyOperation.setOperator(Operator.ADD);
        biddingStrategyOperation.setAccountId(accountId);
        biddingStrategyOperation.getOperand().addAll(Arrays.asList(
                enhancedCpcBidding,
                pageOnePromotedBidding,
                targetCpaBidding,
                targetSpendBidding,
                targetRoasBidding
        ));

        // Call API
        System.out.println("############################################");
        System.out.println("BiddingStrategyService::mutate(ADD)");
        System.out.println("############################################");

        Holder<BiddingStrategyReturnValue> biddingStrategyReturnValueHolder = new Holder<BiddingStrategyReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        BiddingStrategyServiceInterface biddingStrategyService = SoapUtils.createServiceInterface(BiddingStrategyServiceInterface.class, BiddingStrategyService.class);
        biddingStrategyService.mutate(biddingStrategyOperation, biddingStrategyReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new BiddingStrategyServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (biddingStrategyReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:BiddingStrategyService Add");
        }

        // Display
        for (BiddingStrategyValues biddingStrategyValues : biddingStrategyReturnValueHolder.value.getValues()) {
            if (biddingStrategyValues.isOperationSucceeded()) {
                display(biddingStrategyValues.getBiddingStrategy());
            } else {
                SoapUtils.displayErrors(new BiddingStrategyServiceErrorEntityFactory(biddingStrategyValues.getError()), true);
            }
        }

        // Response
        return biddingStrategyReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for BiddingStrategyService SET.
     *
     * @param accountId                 Account ID
     * @param biddingStrategyValuesList BiddingStrategyValues entity for set.
     * @return BiddingStrategyValues
     * @throws Exception
     */
    public static List<BiddingStrategyValues> set(long accountId, List<BiddingStrategyValues> biddingStrategyValuesList) throws Exception {

        // Set Operation
        BiddingStrategyOperation biddingStrategyOperation = new BiddingStrategyOperation();
        biddingStrategyOperation.setOperator(Operator.SET);
        biddingStrategyOperation.setAccountId(accountId);

        // Set Operand
        for (BiddingStrategyValues biddingStrategyValues : biddingStrategyValuesList) {

            BiddingStrategy biddingStrategy = new BiddingStrategy();

            biddingStrategy.setAccountId(biddingStrategyValues.getBiddingStrategy().getAccountId());
            biddingStrategy.setBiddingStrategyId(biddingStrategyValues.getBiddingStrategy().getBiddingStrategyId());

            // Set BiddingScheme
            if (biddingStrategyValues.getBiddingStrategy().getBiddingScheme() instanceof EnhancedCpcBiddingScheme) {

                // Set EnhancedCpcBidding
                biddingStrategy.setBiddingStrategyName("SampleEnhancedCpc_UpdateOn_" + SoapUtils.getCurrentTimestamp());

                EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = new EnhancedCpcBiddingScheme();
                enhancedCpcBiddingScheme.setBiddingStrategyType(BiddingStrategyType.ENHANCED_CPC);
                biddingStrategy.setBiddingScheme(enhancedCpcBiddingScheme);

            } else if (biddingStrategyValues.getBiddingStrategy().getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {

                // Set PageOnePromotedBidding
                biddingStrategy.setBiddingStrategyName("SamplePageOnePromoted_UpdateOn_" + SoapUtils.getCurrentTimestamp());

                PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = new PageOnePromotedBiddingScheme();
                pageOnePromotedBiddingScheme.setBiddingStrategyType(BiddingStrategyType.PAGE_ONE_PROMOTED);
                pageOnePromotedBiddingScheme.setBidCeiling((long) 550);
                pageOnePromotedBiddingScheme.setBidMultiplier(5.00);
                pageOnePromotedBiddingScheme.setBidChangesForRaisesOnly(BidChangesForRaisesOnly.DEACTIVE);
                pageOnePromotedBiddingScheme.setRaiseBidWhenBudgetConstrained(RaiseBidWhenBudgetConstrained.DEACTIVE);
                pageOnePromotedBiddingScheme.setRaiseBidWhenLowQualityScore(RaiseBidWhenLowQualityScore.DEACTIVE);
                biddingStrategy.setBiddingScheme(pageOnePromotedBiddingScheme);

            } else if (biddingStrategyValues.getBiddingStrategy().getBiddingScheme() instanceof TargetCpaBiddingScheme) {

                // Set TargetCpaBidding
                biddingStrategy.setBiddingStrategyName("SampleTargetCpa_UpdateOn_" + SoapUtils.getCurrentTimestamp());

                TargetCpaBiddingScheme targetCpaBiddingScheme = new TargetCpaBiddingScheme();
                targetCpaBiddingScheme.setBiddingStrategyType(BiddingStrategyType.TARGET_CPA);
                targetCpaBiddingScheme.setTargetCpa((long) 550);
                targetCpaBiddingScheme.setBidCeiling((long) 750);
                biddingStrategy.setBiddingScheme(targetCpaBiddingScheme);

            } else if (biddingStrategyValues.getBiddingStrategy().getBiddingScheme() instanceof TargetSpendBiddingScheme) {

                // Set TargetSpendBidding
                biddingStrategy.setBiddingStrategyName("SampleTargetSpend_UpdateOn_" + SoapUtils.getCurrentTimestamp());

                TargetSpendBiddingScheme targetSpendBiddingScheme = new TargetSpendBiddingScheme();
                targetSpendBiddingScheme.setBiddingStrategyType(BiddingStrategyType.TARGET_SPEND);
                targetSpendBiddingScheme.setBidCeiling((long) 750);
                biddingStrategy.setBiddingScheme(targetSpendBiddingScheme);

            } else if (biddingStrategyValues.getBiddingStrategy().getBiddingScheme() instanceof TargetRoasBiddingScheme) {

                // Set TargetRoasBidding
                biddingStrategy.setBiddingStrategyName("SampleTargetRoas_UpdateOn_" + SoapUtils.getCurrentTimestamp());

                TargetRoasBiddingScheme targetRoasBiddingScheme = new TargetRoasBiddingScheme();
                targetRoasBiddingScheme.setBiddingStrategyType(BiddingStrategyType.TARGET_ROAS);
                targetRoasBiddingScheme.setTargetRoas(15.00);
                targetRoasBiddingScheme.setBidCeiling((long) 750);
                targetRoasBiddingScheme.setBidFloor((long) 650);
                biddingStrategy.setBiddingScheme(targetRoasBiddingScheme);
            }

            biddingStrategyOperation.getOperand().add(biddingStrategy);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("BiddingStrategyService::mutate(SET)");
        System.out.println("############################################");

        Holder<BiddingStrategyReturnValue> biddingStrategyReturnValueHolder = new Holder<BiddingStrategyReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        BiddingStrategyServiceInterface biddingStrategyService = SoapUtils.createServiceInterface(BiddingStrategyServiceInterface.class, BiddingStrategyService.class);
        biddingStrategyService.mutate(biddingStrategyOperation, biddingStrategyReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new BiddingStrategyServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (biddingStrategyReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:BiddingStrategyService Set");
        }

        // Display
        for (BiddingStrategyValues biddingStrategyValues : biddingStrategyReturnValueHolder.value.getValues()) {
            if (biddingStrategyValues.isOperationSucceeded()) {
                display(biddingStrategyValues.getBiddingStrategy());
            } else {
                SoapUtils.displayErrors(new BiddingStrategyServiceErrorEntityFactory(biddingStrategyValues.getError()), true);
            }
        }

        // Response
        return biddingStrategyReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for BiddingStrategyService REMOVE.
     *
     * @param accountId                 Account ID
     * @param biddingStrategyValuesList BiddingStrategyValues entity for remove.
     * @return BiddingStrategyValues
     * @throws Exception
     */
    public static List<BiddingStrategyValues> remove(long accountId, List<BiddingStrategyValues> biddingStrategyValuesList) throws Exception {

        // Set Operation
        BiddingStrategyOperation biddingStrategyOperation = new BiddingStrategyOperation();
        biddingStrategyOperation.setOperator(Operator.REMOVE);
        biddingStrategyOperation.setAccountId(accountId);

        // Set Operand
        for (BiddingStrategyValues biddingStrategyValues : biddingStrategyValuesList) {

            BiddingStrategy biddingStrategy = new BiddingStrategy();

            biddingStrategy.setAccountId(biddingStrategyValues.getBiddingStrategy().getAccountId());
            biddingStrategy.setBiddingStrategyId(biddingStrategyValues.getBiddingStrategy().getBiddingStrategyId());

            biddingStrategyOperation.getOperand().add(biddingStrategy);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("BiddingStrategyService::mutate(REMOVE)");
        System.out.println("############################################");

        Holder<BiddingStrategyReturnValue> biddingStrategyReturnValueHolder = new Holder<BiddingStrategyReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        BiddingStrategyServiceInterface biddingStrategyService = SoapUtils.createServiceInterface(BiddingStrategyServiceInterface.class, BiddingStrategyService.class);
        biddingStrategyService.mutate(biddingStrategyOperation, biddingStrategyReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new BiddingStrategyServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (biddingStrategyReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:BiddingStrategyService Remove");
        }

        // Display
        for (BiddingStrategyValues biddingStrategyValues : biddingStrategyReturnValueHolder.value.getValues()) {
            if (biddingStrategyValues.isOperationSucceeded()) {
                display(biddingStrategyValues.getBiddingStrategy());
            } else {
                SoapUtils.displayErrors(new BiddingStrategyServiceErrorEntityFactory(biddingStrategyValues.getError()), true);
            }
        }

        // Response
        return biddingStrategyReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for BiddingStrategyService GET.
     *
     * @param accountId                 Account ID
     * @param biddingStrategyValuesList BiddingStrategyValues entity for get.
     * @return BiddingStrategyValues
     * @throws Exception
     */
    public static List get(long accountId, List<BiddingStrategyValues> biddingStrategyValuesList) throws Exception {

        // Set Selector
        BiddingStrategySelector biddingStrategySelector = new BiddingStrategySelector();
        biddingStrategySelector.setAccountId(accountId);
        for (BiddingStrategyValues biddingStrategyValues : biddingStrategyValuesList) {
            biddingStrategySelector.getBiddingStrategyIds().add((biddingStrategyValues.getBiddingStrategy().getBiddingStrategyId()));
        }
        biddingStrategySelector.getBiddingStrategyTypes().addAll(Arrays.asList(
                BiddingStrategyType.ENHANCED_CPC,
                BiddingStrategyType.PAGE_ONE_PROMOTED,
                BiddingStrategyType.TARGET_CPA,
                BiddingStrategyType.TARGET_SPEND,
                BiddingStrategyType.TARGET_ROAS
        ));
        Paging paging = new Paging();
        paging.setStartIndex(1);
        paging.setNumberResults(20);
        biddingStrategySelector.setPaging(paging);

        // Call API
        System.out.println("############################################");
        System.out.println("BiddingStrategyService::get");
        System.out.println("############################################");

        Holder<BiddingStrategyPage> biddingStrategyPageHolder = new Holder<BiddingStrategyPage>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        BiddingStrategyServiceInterface biddingStrategyService = SoapUtils.createServiceInterface(BiddingStrategyServiceInterface.class, BiddingStrategyService.class);
        biddingStrategyService.get(biddingStrategySelector, biddingStrategyPageHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new BiddingStrategyServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (biddingStrategyPageHolder.value == null) {
            throw new Exception("NoDataResponse:BiddingStrategyService Get");
        }

        // Display
        for (BiddingStrategyValues biddingStrategyValues : biddingStrategyPageHolder.value.getValues()) {
            if (biddingStrategyValues.isOperationSucceeded()) {
                display(biddingStrategyValues.getBiddingStrategy());
            } else {
                SoapUtils.displayErrors(new BiddingStrategyServiceErrorEntityFactory(biddingStrategyValues.getError()), true);
            }
        }

        // Response
        return biddingStrategyPageHolder.value.getValues();
    }

    /**
     * display BiddingStrategy entity to stdout.
     *
     * @param biddingStrategy BiddingStrategy entity for display.
     */
    public static void display(BiddingStrategy biddingStrategy) {

        System.out.println("accountId = " + biddingStrategy.getAccountId());
        System.out.println("biddingStrategyId = " + biddingStrategy.getBiddingStrategyId());
        System.out.println("biddingStrategyName = " + biddingStrategy.getBiddingStrategyName());
        System.out.println("biddingStrategyType = " + biddingStrategy.getBiddingStrategyType());

        if (biddingStrategy.getBiddingScheme() instanceof EnhancedCpcBiddingScheme) {
            EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = (EnhancedCpcBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingScheme(EnhancedCpcBiddingScheme)/biddingStrategyType = " + enhancedCpcBiddingScheme.getBiddingStrategyType());
        } else if (biddingStrategy.getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {
            PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = (PageOnePromotedBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingScheme(PageOnePromotedBiddingScheme)/biddingStrategyType = " + pageOnePromotedBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingScheme(PageOnePromotedBiddingScheme)/bidCeiling = " + pageOnePromotedBiddingScheme.getBidCeiling());
            System.out.println("biddingScheme(PageOnePromotedBiddingScheme)/bidMultiplier = " + pageOnePromotedBiddingScheme.getBidMultiplier());
            System.out.println("biddingScheme(PageOnePromotedBiddingScheme)/bidChangesForRaisesOnly = " + pageOnePromotedBiddingScheme.getBidChangesForRaisesOnly());
            System.out.println("biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenBudgetConstrained = " + pageOnePromotedBiddingScheme.getRaiseBidWhenBudgetConstrained());
            System.out.println("biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenLowQualityScore = " + pageOnePromotedBiddingScheme.getRaiseBidWhenLowQualityScore());
        } else if (biddingStrategy.getBiddingScheme() instanceof TargetCpaBiddingScheme) {
            TargetCpaBiddingScheme targetCpaBiddingScheme = (TargetCpaBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingScheme(TargetCpaBiddingScheme)/biddingStrategyType = " + targetCpaBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingScheme(TargetCpaBiddingScheme)/targetCpa = " + targetCpaBiddingScheme.getTargetCpa());
            System.out.println("biddingScheme(TargetCpaBiddingScheme)/bidCeiling = " + targetCpaBiddingScheme.getBidCeiling());
            System.out.println("biddingScheme(TargetCpaBiddingScheme)/bidFloor = " + targetCpaBiddingScheme.getBidFloor());
        } else if (biddingStrategy.getBiddingScheme() instanceof TargetSpendBiddingScheme) {
            TargetSpendBiddingScheme targetSpendBiddingScheme = (TargetSpendBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingScheme(TargetSpendBiddingScheme)/biddingStrategyType = " + targetSpendBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingScheme(TargetSpendBiddingScheme)/bidCeiling = " + targetSpendBiddingScheme.getBidCeiling());
        } else if (biddingStrategy.getBiddingScheme() instanceof TargetRoasBiddingScheme) {
            TargetRoasBiddingScheme targetRoasBiddingScheme = (TargetRoasBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingScheme(TargetRoasBiddingScheme)/biddingStrategyType = " + targetRoasBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingScheme(TargetRoasBiddingScheme)/targetRoas = " + targetRoasBiddingScheme.getTargetRoas());
            System.out.println("biddingScheme(TargetRoasBiddingScheme)/bidCeiling = " + targetRoasBiddingScheme.getBidCeiling());
            System.out.println("biddingScheme(TargetRoasBiddingScheme)/bidFloor = " + targetRoasBiddingScheme.getBidFloor());
        }


        System.out.println("---------");
    }
}
