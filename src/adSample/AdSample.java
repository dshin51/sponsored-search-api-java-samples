package adSample;

import jp.yahooapis.ss.V5.AdGroupAdService.AdGroupAdValues;
import jp.yahooapis.ss.V5.AdGroupCriterionService.AdGroupCriterionValues;
import jp.yahooapis.ss.V5.AdGroupService.AdGroupValues;
import jp.yahooapis.ss.V5.BiddingStrategyService.BiddingStrategyValues;
import jp.yahooapis.ss.V5.BiddingStrategyService.PageOnePromotedBiddingScheme;
import jp.yahooapis.ss.V5.CampaignCriterionService.CampaignCriterionValues;
import jp.yahooapis.ss.V5.CampaignService.CampaignValues;
import jp.yahooapis.ss.V5.CampaignTargetService.CampaignTargetValues;
import util.SoapUtils;

import java.util.List;

/**
 * Sample Program for BiddingStrategyService,CampaignService,CampaignTargetService,
 * CampaignCriterionService,AdGroupService,AdGroupCriterionService,AdGroupAdService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class AdSample {

    /**
     * main method for AdSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();
            long biddingStrategyId = 0;
            long campaignId = 0;
            long adGroupId = 0;

            //=================================================================
            // BiddingStrategyService
            //=================================================================
            // ADD
            List<BiddingStrategyValues> biddingStrategyValues = BiddingStrategyServiceSample.add(accountId);
            // GET
            BiddingStrategyServiceSample.get(accountId, biddingStrategyValues);
            // SET
            BiddingStrategyServiceSample.set(accountId, biddingStrategyValues);

            for (BiddingStrategyValues value : biddingStrategyValues) {
                if (value.getBiddingStrategy().getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {
                    biddingStrategyId = value.getBiddingStrategy().getBiddingStrategyId();
                }
            }

            //=================================================================
            // CampaignService
            //=================================================================
            // ADD
            List<CampaignValues> campaignValues = CampaignServiceSample.add(accountId, biddingStrategyId);
            // GET
            CampaignServiceSample.get(accountId, campaignValues);
            // SET
            CampaignServiceSample.set(accountId, campaignValues, biddingStrategyId);

            for (CampaignValues value : campaignValues) {
                if (campaignId == 0) {
                    campaignId = value.getCampaign().getCampaignId();
                }
            }

            //=================================================================
            // CampaignTargetService
            //=================================================================
            // ADD
            List<CampaignTargetValues> campaignTargetValues = CampaignTargetServiceSample.add(accountId, campaignId);
            // GET
            CampaignTargetServiceSample.get(accountId, campaignTargetValues);
            // SET
            CampaignTargetServiceSample.set(accountId, campaignTargetValues);

            //=================================================================
            // CampaignCriterionService
            //=================================================================
            // ADD
            List<CampaignCriterionValues> campaignCriterionValues = CampaignCriterionServiceSample.add(accountId, campaignId);
            // GET
            CampaignCriterionServiceSample.get(accountId, campaignId, campaignCriterionValues);

            //=================================================================
            // AdGroupService
            //=================================================================
            // ADD
            List<AdGroupValues> adGroupValues = AdGroupServiceSample.add(accountId, campaignId, biddingStrategyId);
            // GET
            AdGroupServiceSample.get(accountId, campaignId, adGroupValues);
            // SET
            AdGroupServiceSample.set(accountId, campaignId, adGroupValues, biddingStrategyId);

            for (AdGroupValues value : adGroupValues) {
                if (adGroupId == 0) {
                    adGroupId = value.getAdGroup().getAdGroupId();
                }
            }

            //=================================================================
            // AdGroupCriterionService
            //=================================================================
            // ADD
            List<AdGroupCriterionValues> adGroupCriterionValues = AdGroupCriterionServiceSample.add(accountId, campaignId, adGroupId);
            // GET
            AdGroupCriterionServiceSample.get(accountId, campaignId, adGroupId, adGroupCriterionValues);
            // SET
            AdGroupCriterionServiceSample.set(accountId, campaignId, adGroupId, adGroupCriterionValues);

            //=================================================================
            // AdGroupBidMultiplierService
            //=================================================================
            // SET
            AdGroupBidMultiplierServiceSample.set(accountId, campaignId, adGroupId);
            // GET
            AdGroupBidMultiplierServiceSample.get(accountId, campaignId, adGroupId);

            //=================================================================
            // AdGroupAdService
            //=================================================================
            // ADD
            List<AdGroupAdValues> adGroupAdValues = AdGroupAdServiceSample.add(accountId, campaignId, adGroupId);
            // GET
            AdGroupAdServiceSample.get(accountId, campaignId, adGroupId, adGroupAdValues);
            // SET
            AdGroupAdServiceSample.set(accountId, campaignId, adGroupId, adGroupAdValues);


            //=================================================================
            // remove AsGroupAdService, AsGroupCriterionService, AsGroupService,
            //        CampaignCriterionService, CampaignTarget, BiddingStrategy, Campaign
            //=================================================================
            // AsGroupAdService
            AdGroupAdServiceSample.remove(accountId, campaignId, adGroupId, adGroupAdValues);

            // AdGroupCriterionService
            AdGroupCriterionServiceSample.remove(accountId, campaignId, adGroupId, adGroupCriterionValues);

            // AsGroupService
            AdGroupServiceSample.remove(accountId, campaignId, adGroupValues);

            // CampaignCriterionService
            CampaignCriterionServiceSample.remove(accountId, campaignId, campaignCriterionValues);

            // CampaignTarget
            CampaignTargetServiceSample.remove(accountId, campaignTargetValues);

            // Campaign
            CampaignServiceSample.remove(accountId, campaignValues);

            // BiddingStrategy
            BiddingStrategyServiceSample.remove(accountId, biddingStrategyValues);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
