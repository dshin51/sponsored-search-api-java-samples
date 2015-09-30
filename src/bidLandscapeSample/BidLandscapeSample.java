package bidLandscapeSample;

import error.impl.BidLandscapeServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.BidLandscapeService.*;
import jp.yahooapis.ss.V5.BidLandscapeService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.List;

/**
 * Sample Program for BidLandscapeService. Copyright (C) 2012 Yahoo Japan
 * Corporation. All Rights Reserved.
 */
public class BidLandscapeSample {

    public static void main(String[] args){
        try {
            long accountId = SoapUtils.getAccountId();
            long campaignId = SoapUtils.getCampaignId();
            long adGroupId  = SoapUtils.getAdGroupId();
            List<Long> adGroupCriterionIds = SoapUtils.getAdGroupCriterionIds();

            // =================================================================
            // BidLandscapeService
            // =================================================================
            BidLandscapeServiceInterface balanceService = SoapUtils.createServiceInterface(BidLandscapeServiceInterface.class, BidLandscapeService.class);

            // -----------------------------------------------
            // BidLandscapeService::get
            // -----------------------------------------------
            // request
            BidLandscapeSelector bidLandscapeSelector = new BidLandscapeSelector();
            bidLandscapeSelector.setAccountId(accountId);
            bidLandscapeSelector.setCampaignId(campaignId);
            bidLandscapeSelector.setAdGroupId(adGroupId);
            if(adGroupCriterionIds != null && adGroupCriterionIds.size() > 0){
                bidLandscapeSelector.getCriterionIds().addAll(adGroupCriterionIds);
                bidLandscapeSelector.setSimType(SimType.CRITERION);
            }else{
                bidLandscapeSelector.setSimType(SimType.ADGROUP);
            }

            // call API
            System.out.println("############################################");
            System.out.println("BidLandscapeService::get");
            System.out.println("############################################");

            Holder<BidLandscapePage> bidLandscapePageHolder = new Holder<BidLandscapePage>();
            Holder<List<Error>> bidLandscapeErrorHolder = new Holder<List<Error>>();
            balanceService.get(bidLandscapeSelector, bidLandscapePageHolder, bidLandscapeErrorHolder);

            //if error
            if(bidLandscapeErrorHolder.value != null && bidLandscapeErrorHolder.value.size() > 0){
                SoapUtils.displayErrors(new BidLandscapeServiceErrorEntityFactory(bidLandscapeErrorHolder.value), true);
            }

            //response
            if(bidLandscapePageHolder.value != null){
                if(bidLandscapePageHolder.value.getValues() != null){
                    for (BidLandscapeValues values : bidLandscapePageHolder.value.getValues()) {
                        if(values.isOperationSucceeded()){
                            dispalyBidLandscapeValues(values);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new BidLandscapeServiceErrorEntityFactory(values.getError()), true);
                        }
                    }
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * display BidLandscape entity to stdout.
     *
     * @param values BidLandscape entity for display.
     */
    private static void dispalyBidLandscapeValues(BidLandscapeValues values) {
        if(values.getData() != null){
            BidLandscape data = values.getData();
            System.out.println("data/campaignId = " + data.getCampaignId());
            System.out.println("data/adGroupId = " + data.getAdGroupId());
            System.out.println("data/startDate = " + data.getStartDate());
            System.out.println("data/endDate = " + data.getEndDate());
            if(data.getLandscapePoints() != null){
                for (LandscapePoints points : data.getLandscapePoints()) {
                    System.out.println("data/landscapePoints/bid = " + points.getBid());
                    System.out.println("data/landscapePoints/clicks = " + points.getClicks());
                    System.out.println("data/landscapePoints/cost = " + points.getCost());
                    System.out.println("data/landscapePoints/marginalCpc = " + points.getMarginalCpc());
                    System.out.println("data/landscapePoints/impressions = " + points.getImpressions());
                }
            }
            System.out.println("data/BidLandscape.Type = " + data.getBidLandscapeType());
            if(data instanceof AdGroupBidLandscape){
                AdGroupBidLandscape adGroup = (AdGroupBidLandscape)data;
                System.out.println("data/type = " + adGroup.getType());
                System.out.println("data/landscapeCurrent = " + adGroup.isLandscapeCurrent());
            }else if(data instanceof CriterionBidLandscape){
                CriterionBidLandscape criterion = (CriterionBidLandscape)data;
                System.out.println("data/criterionId = " + criterion.getCriterionId());
            }
        }
        System.out.println("---------");
    }

}
