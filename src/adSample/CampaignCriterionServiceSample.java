package adSample;

import error.impl.CampaignCriterionServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.CampaignCriterionService.*;
import jp.yahooapis.ss.V5.CampaignCriterionService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.List;

/**
 * Sample Program for CampaignCriterionService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class CampaignCriterionServiceSample {

    /**
     * main method for CampaignCriterionServiceSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();
            long campaignId = SoapUtils.getCampaignId();

            // CampaignCriterionService ADD
            List<CampaignCriterionValues> campaignCriterionValues = add(accountId, campaignId);

            // CampaignCriterionService GET
            get(accountId, campaignId, campaignCriterionValues);

            // CampaignCriterionService REMOVE
            remove(accountId, campaignId, campaignCriterionValues);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sample Program for CampaignCriterionService ADD.
     *
     * @param accountId  Account ID
     * @param campaignId Campaign ID
     * @return CampaignCriterionValues
     * @throws Exception
     */
    public static List<CampaignCriterionValues> add(long accountId, long campaignId) throws Exception {

        // Set Keyword
        Keyword keyword = new Keyword();
        keyword.setType(CriterionType.KEYWORD);
        keyword.setText("sample text");
        keyword.setMatchType(KeywordMatchType.PHRASE);

        // Set Operand
        CampaignCriterion campaignCriterion = new CampaignCriterion();
        campaignCriterion.setAccountId(accountId);
        campaignCriterion.setCampaignId(campaignId);
        campaignCriterion.setCriterionUse(CriterionUse.NEGATIVE);
        campaignCriterion.setCriterion(keyword);

        // Set Operation
        CampaignCriterionOperation campaignCriterionOperation = new CampaignCriterionOperation();
        campaignCriterionOperation.setOperator(Operator.ADD);
        campaignCriterionOperation.setAccountId(accountId);
        campaignCriterionOperation.setCampaignId(campaignId);
        campaignCriterionOperation.setCriterionUse(CriterionUse.NEGATIVE);
        campaignCriterionOperation.getOperand().add(campaignCriterion);

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignCriterionService::mutate(ADD)");
        System.out.println("############################################");

        Holder<CampaignCriterionReturnValue> campaignCriterionReturnValueHolder = new Holder<CampaignCriterionReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignCriterionServiceInterface campaignCriterionService = SoapUtils.createServiceInterface(CampaignCriterionServiceInterface.class, CampaignCriterionService.class);
        campaignCriterionService.mutate(campaignCriterionOperation, campaignCriterionReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignCriterionServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignCriterionReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignCriterionService Add");
        }

        // Display
        for (CampaignCriterionValues campaignCriterionValues : campaignCriterionReturnValueHolder.value.getValues()) {
            if (campaignCriterionValues.isOperationSucceeded()) {
                display(campaignCriterionValues.getCampaignCriterion());
            } else {
                SoapUtils.displayErrors(new CampaignCriterionServiceErrorEntityFactory(campaignCriterionValues.getError()), true);
            }
        }

        // Response
        return campaignCriterionReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for CampaignCriterionService REMOVE.
     *
     * @param accountId                   Account ID
     * @param campaignId                  Campaign ID
     * @param campaignCriterionValuesList CampaignCriterionValues entity for remove.
     * @return CampaignCriterionValues
     * @throws Exception
     */
    public static List<CampaignCriterionValues> remove(long accountId, long campaignId, List<CampaignCriterionValues> campaignCriterionValuesList) throws Exception {

        // Set Operation
        CampaignCriterionOperation campaignCriterionOperation = new CampaignCriterionOperation();
        campaignCriterionOperation.setOperator(Operator.REMOVE);
        campaignCriterionOperation.setAccountId(accountId);
        campaignCriterionOperation.setCampaignId(campaignId);

        // Set Operand
        for (CampaignCriterionValues campaignCriterionValues : campaignCriterionValuesList) {

            CampaignCriterion campaignCriterion = new CampaignCriterion();
            campaignCriterion.setAccountId(campaignCriterionValues.getCampaignCriterion().getAccountId());
            campaignCriterion.setCampaignId(campaignCriterionValues.getCampaignCriterion().getCampaignId());
            campaignCriterion.setCriterionUse(campaignCriterionValues.getCampaignCriterion().getCriterionUse());
            Keyword keyword = new Keyword();
            keyword.setCriterionId(campaignCriterionValues.getCampaignCriterion().getCriterion().getCriterionId());
            keyword.setType(campaignCriterionValues.getCampaignCriterion().getCriterion().getType());
            campaignCriterion.setCriterion(keyword);

            campaignCriterionOperation.getOperand().add(campaignCriterion);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignCriterionService::mutate(REMOVE)");
        System.out.println("############################################");

        Holder<CampaignCriterionReturnValue> campaignCriterionReturnValueHolder = new Holder<CampaignCriterionReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignCriterionServiceInterface campaignCriterionService = SoapUtils.createServiceInterface(CampaignCriterionServiceInterface.class, CampaignCriterionService.class);
        campaignCriterionService.mutate(campaignCriterionOperation, campaignCriterionReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignCriterionServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignCriterionReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignCriterionService Remove");
        }

        // Display
        for (CampaignCriterionValues campaignCriterionValues : campaignCriterionReturnValueHolder.value.getValues()) {
            if (campaignCriterionValues.isOperationSucceeded()) {
                display(campaignCriterionValues.getCampaignCriterion());
            } else {
                SoapUtils.displayErrors(new CampaignCriterionServiceErrorEntityFactory(campaignCriterionValues.getError()), true);
            }
        }

        // Response
        return campaignCriterionReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for CampaignCriterionService GET.
     *
     * @param accountId                   Account ID
     * @param campaignId                  Campaign ID
     * @param campaignCriterionValuesList CampaignCriterionValues entity for get.
     * @return CampaignCriterionValues
     * @throws Exception
     */
    public static List get(long accountId, long campaignId, List<CampaignCriterionValues> campaignCriterionValuesList) throws Exception {

        // Set Selector
        CampaignCriterionSelector campaignCriterionSelector = new CampaignCriterionSelector();
        campaignCriterionSelector.setAccountId(accountId);
        campaignCriterionSelector.getCampaignIds().add(campaignId);
        for (CampaignCriterionValues campaignCriterionValues : campaignCriterionValuesList) {
            campaignCriterionSelector.getCriterionIds().add((campaignCriterionValues.getCampaignCriterion().getCriterion().getCriterionId()));
        }
        campaignCriterionSelector.setCriterionUse(CriterionUse.NEGATIVE);
        Paging paging = new Paging();
        paging.setStartIndex(1);
        paging.setNumberResults(20);
        campaignCriterionSelector.setPaging(paging);

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignCriterionService::get");
        System.out.println("############################################");

        Holder<CampaignCriterionPage> campaignCriterionPageHolder = new Holder<CampaignCriterionPage>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignCriterionServiceInterface campaignCriterionService = SoapUtils.createServiceInterface(CampaignCriterionServiceInterface.class, CampaignCriterionService.class);
        campaignCriterionService.get(campaignCriterionSelector, campaignCriterionPageHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignCriterionServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignCriterionPageHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignCriterionService Get");
        }

        // Display
        for (CampaignCriterionValues campaignCriterionValues : campaignCriterionPageHolder.value.getValues()) {
            if (campaignCriterionValues.isOperationSucceeded()) {
                display(campaignCriterionValues.getCampaignCriterion());
            } else {
                SoapUtils.displayErrors(new CampaignCriterionServiceErrorEntityFactory(campaignCriterionValues.getError()), true);
            }
        }

        // Response
        return campaignCriterionPageHolder.value.getValues();
    }

    /**
     * display CampaignCriterion entity to stdout.
     *
     * @param campaignCriterion CampaignCriterion entity for display.
     */
    public static void display(CampaignCriterion campaignCriterion) {

        System.out.println("accountId = " + campaignCriterion.getAccountId());
        System.out.println("campaignId = " + campaignCriterion.getCampaignId());
        System.out.println("campaignName = " + campaignCriterion.getCampaignName());
        System.out.println("criterionUse = " + campaignCriterion.getCriterionUse());
        if (campaignCriterion.getCriterion() != null) {
            Criterion criterion = campaignCriterion.getCriterion();
            System.out.println("criterion/criterionId = " + criterion.getCriterionId());
            System.out.println("criterion/type = " + criterion.getType());
        }
        System.out.println("---------");
    }
}
