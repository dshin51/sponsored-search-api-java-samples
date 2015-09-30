package adDisplayOptionSample;

import error.impl.AdGroupFeedServiceErrorEntityFactory;
import error.impl.CampaignFeedServiceErrorEntityFactory;
import error.impl.FeedItemServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.AdGroupFeedService.*;
import jp.yahooapis.ss.V5.CampaignFeedService.*;
import jp.yahooapis.ss.V5.CampaignFeedService.DevicePlatform;
import jp.yahooapis.ss.V5.FeedItemService.*;
import jp.yahooapis.ss.V5.FeedItemService.PlaceholderType;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Program for FeedItemService,CampaignFeedService,AdGroupFeedService
 * Copyright (C) 2013 Yahoo Japan Corporation. All Rights Reserved.
 */
public class AdDisplayOptionSample {

    /**
     * main method for AdDisplayOptionSample
     * @param args command line arguments
     */
    public static void main(String[] args) {

        long accountId = SoapUtils.getAccountId();
        long campaignId = SoapUtils.getCampaignId();
        long adGroupId = SoapUtils.getAdGroupId();
        long feedItemId1 = -1;
        long feedItemId2 = -1;

        try {
            //=================================================================
            // FeedItemService
            //=================================================================
            FeedItemServiceInterface feedItemService = SoapUtils.createServiceInterface(FeedItemServiceInterface.class, FeedItemService.class);

            //-----------------------------------------------
            // FeedItemService::mutate(ADD QUICKLINK)
            //-----------------------------------------------
            //request QUICKLINK
            FeedItem addFeedItemOperand1 = new FeedItem();
            addFeedItemOperand1.setAccountId(accountId);
            addFeedItemOperand1.setPlaceholderType(PlaceholderType.QUICKLINK);
            FeedItemAttribute addLinkText = new FeedItemAttribute();
            addLinkText.setPlaceholderField(PlaceholderField.LINK_TEXT);
            addLinkText.setFeedAttributeValue("samplelink");
            addFeedItemOperand1.getFeedItemAttribute().add(addLinkText);
            FeedItemAttribute addLinkUrl = new FeedItemAttribute();
            addLinkUrl.setPlaceholderField(PlaceholderField.LINK_URL);
            addLinkUrl.setFeedAttributeValue("http://www.quicklink.sample.co.jp");
            addFeedItemOperand1.getFeedItemAttribute().add(addLinkUrl);
            addFeedItemOperand1.setStartDate("20131215");
            addFeedItemOperand1.setEndDate("20141215");
            FeedItemSchedule quickLinkSchedule1 = new FeedItemSchedule();
            quickLinkSchedule1.setDayOfWeek(DayOfWeek.SUNDAY);
            quickLinkSchedule1.setStartHour(14L);
            quickLinkSchedule1.setStartMinute(MinuteOfHour.ZERO);
            quickLinkSchedule1.setEndHour(15L);
            quickLinkSchedule1.setEndMinute(MinuteOfHour.THIRTY);

            FeedItemSchedule quickLinkSchedule2 = new FeedItemSchedule();
            quickLinkSchedule2.setDayOfWeek(DayOfWeek.MONDAY);
            quickLinkSchedule2.setStartHour(14L);
            quickLinkSchedule2.setStartMinute(MinuteOfHour.ZERO);
            quickLinkSchedule2.setEndHour(15L);
            quickLinkSchedule2.setEndMinute(MinuteOfHour.THIRTY);
            FeedItemScheduling scheduling = new FeedItemScheduling();
            scheduling.getSchedules().addAll(Arrays.asList(quickLinkSchedule1,quickLinkSchedule2));
            addFeedItemOperand1.setScheduling(scheduling);

            addFeedItemOperand1.setDevicePreference(jp.yahooapis.ss.V5.FeedItemService.DevicePreference.SMART_PHONE);

            FeedItemOperation addFeedItemOperation1 = new FeedItemOperation();
            addFeedItemOperation1.setOperator(jp.yahooapis.ss.V5.FeedItemService.Operator.ADD);
            addFeedItemOperation1.setAccountId(accountId);
            addFeedItemOperation1.setPlaceholderType(PlaceholderType.QUICKLINK);
            addFeedItemOperation1.getOperand().add(addFeedItemOperand1);

            //call API
            System.out.println("############################################");
            System.out.println("FeedItemService::mutate(ADD) QUICKLINK");
            System.out.println("############################################");
            Holder<FeedItemReturnValue> addFeedItemReturnValueHolder1 = new Holder<FeedItemReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>> addFeedItemErrorHolder1 = new Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>>();
            feedItemService.mutate(addFeedItemOperation1, addFeedItemReturnValueHolder1, addFeedItemErrorHolder1);


            //if error
            if(addFeedItemErrorHolder1.value != null && addFeedItemErrorHolder1.value.size() > 0){
                SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(addFeedItemErrorHolder1.value), true);
            }

            //response
            if(addFeedItemReturnValueHolder1.value != null){
                FeedItemReturnValue returnValue = addFeedItemReturnValueHolder1.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<FeedItemValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            FeedItem feedItem = values.get(i).getFeedItem();
                            feedItemId1 = feedItem.getFeedItemId();
                            displayFeedItem(feedItem);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }

            //-----------------------------------------------
            // FeedItemService::mutate(ADD CALLEXTENSION)
            //-----------------------------------------------
            //request CALLEXTENSION
            FeedItem addFeedItemOperand2 = new FeedItem();
            addFeedItemOperand2.setAccountId(accountId);
            addFeedItemOperand2.setPlaceholderType(PlaceholderType.CALLEXTENSION);
            FeedItemAttribute addCallExtension = new FeedItemAttribute();
            addCallExtension.setPlaceholderField(PlaceholderField.CALL_PHONE_NUMBER);
            addCallExtension.setFeedAttributeValue("0120-123-556");
            addFeedItemOperand2.getFeedItemAttribute().add(addCallExtension);

            FeedItemSchedule callSchedule1 = new FeedItemSchedule();
            callSchedule1.setDayOfWeek(DayOfWeek.SUNDAY);
            callSchedule1.setStartHour(10L);
            callSchedule1.setStartMinute(MinuteOfHour.ZERO);
            callSchedule1.setEndHour(12L);
            callSchedule1.setEndMinute(MinuteOfHour.THIRTY);

            FeedItemSchedule callSchedule2 = new FeedItemSchedule();
            callSchedule2.setDayOfWeek(DayOfWeek.MONDAY);
            callSchedule2.setStartHour(10L);
            callSchedule2.setStartMinute(MinuteOfHour.ZERO);
            callSchedule2.setEndHour(12L);
            callSchedule2.setEndMinute(MinuteOfHour.THIRTY);

            FeedItemScheduling callScheduling = new FeedItemScheduling();
            callScheduling.getSchedules().addAll(Arrays.asList(callSchedule1,callSchedule2));
            addFeedItemOperand2.setScheduling(callScheduling);

            FeedItemOperation addFeedItemOperation2 = new FeedItemOperation();
            addFeedItemOperation2.setOperator(jp.yahooapis.ss.V5.FeedItemService.Operator.ADD);
            addFeedItemOperation2.setAccountId(accountId);
            addFeedItemOperation2.setPlaceholderType(PlaceholderType.CALLEXTENSION);
            addFeedItemOperation2.getOperand().add(addFeedItemOperand2);

            //call API
            System.out.println("############################################");
            System.out.println("FeedItemService::mutate(ADD) CALLEXTENSION");
            System.out.println("############################################");
            Holder<FeedItemReturnValue> addFeedItemReturnValueHolder2 = new Holder<FeedItemReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>> addFeedItemErrorHolder2 = new Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>>();
            feedItemService.mutate(addFeedItemOperation2, addFeedItemReturnValueHolder2, addFeedItemErrorHolder2);

            //if error
            if(addFeedItemErrorHolder2.value != null && addFeedItemErrorHolder2.value.size() > 0){
                SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(addFeedItemErrorHolder2.value), true);
            }

            //response
            if(addFeedItemReturnValueHolder2.value != null){
                FeedItemReturnValue returnValue = addFeedItemReturnValueHolder2.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<FeedItemValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            FeedItem feedItem = values.get(i).getFeedItem();
                            feedItemId2 = feedItem.getFeedItemId();
                            displayFeedItem(feedItem);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }

            //-----------------------------------------------
            // FeedItemService::get
            //-----------------------------------------------
            //request
            FeedItemSelector feedItemSelector = new FeedItemSelector();
            feedItemSelector.setAccountId(accountId);
            feedItemSelector.getFeedItemIds().add(feedItemId1);
            feedItemSelector.getFeedItemIds().add(feedItemId2);
            feedItemSelector.getPlaceholderTypes().add(PlaceholderType.QUICKLINK);
            feedItemSelector.getPlaceholderTypes().add(PlaceholderType.CALLEXTENSION);
            feedItemSelector.getApprovalStatuses().add(ApprovalStatus.APPROVED);
            feedItemSelector.getApprovalStatuses().add(ApprovalStatus.APPROVED_WITH_REVIEW);
            feedItemSelector.getApprovalStatuses().add(ApprovalStatus.REVIEW);
            feedItemSelector.getApprovalStatuses().add(ApprovalStatus.POST_DISAPPROVED);
            jp.yahooapis.ss.V5.FeedItemService.Paging feedItemPaging = new jp.yahooapis.ss.V5.FeedItemService.Paging();
            feedItemPaging.setStartIndex(1);
            feedItemPaging.setNumberResults(20);
            feedItemSelector.setPaging(feedItemPaging);

            //call API
            System.out.println("############################################");
            System.out.println("FeedItemService::get");
            System.out.println("############################################");
            Holder<FeedItemPage> feedItemPageHolder = new Holder<FeedItemPage>();
            Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>> getFeedItemErrorArrayHolder = new Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>>();
            feedItemService.get(feedItemSelector, feedItemPageHolder, getFeedItemErrorArrayHolder);

            //if error
            if(getFeedItemErrorArrayHolder.value != null && getFeedItemErrorArrayHolder.value.size() > 0){
                SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(getFeedItemErrorArrayHolder.value), true);
            }

            //response
            if(feedItemPageHolder.value != null){
                if(feedItemPageHolder.value.getValues() != null){
                    for (FeedItemValues values : feedItemPageHolder.value.getValues()) {
                        if(values.isOperationSucceeded()){
                            //display response
                            displayFeedItem(values.getFeedItem());
                        }else{
                            //if error
                            SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(values.getError()), true);
                        }
                    }
                }
            }

            //wait for sandbox review
            Thread.sleep(20000);

            //-----------------------------------------------
            // FeedItemService::mutate(SET) QUICKLINK
            //-----------------------------------------------
            //request
            FeedItem setFeedItemOperand1 = new FeedItem();
            setFeedItemOperand1.setAccountId(accountId);
            setFeedItemOperand1.setFeedItemId(feedItemId1);
            setFeedItemOperand1.setPlaceholderType(PlaceholderType.QUICKLINK);
            FeedItemAttribute setLinkText = new FeedItemAttribute();
            setLinkText.setPlaceholderField(PlaceholderField.LINK_TEXT);
            setLinkText.setFeedAttributeValue("editlink");
            setFeedItemOperand1.getFeedItemAttribute().add(setLinkText);
            FeedItemAttribute setLinkUrl = new FeedItemAttribute();
            setLinkUrl.setPlaceholderField(PlaceholderField.LINK_URL);
            setLinkUrl.setFeedAttributeValue("http://www.quicklink.edit.co.jp");
            setFeedItemOperand1.getFeedItemAttribute().add(setLinkUrl);
            setFeedItemOperand1.setStartDate("");
            setFeedItemOperand1.setEndDate("");

            FeedItemOperation setFeedItemOperation1 = new FeedItemOperation();
            setFeedItemOperation1.setOperator(jp.yahooapis.ss.V5.FeedItemService.Operator.SET);
            setFeedItemOperation1.setAccountId(accountId);
            setFeedItemOperation1.setPlaceholderType(PlaceholderType.QUICKLINK);
            setFeedItemOperation1.getOperand().add(setFeedItemOperand1);

            //call API
            System.out.println("############################################");
            System.out.println("FeedItemService::mutate(SET) QUICKLINK");
            System.out.println("############################################");
            Holder<FeedItemReturnValue> setFeedItemReturnValueHolder1 = new Holder<FeedItemReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>> setFeedItemErrorHolder1 = new Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>>();
            feedItemService.mutate(setFeedItemOperation1, setFeedItemReturnValueHolder1, setFeedItemErrorHolder1);

            //if error
            if(setFeedItemErrorHolder1.value != null && setFeedItemErrorHolder1.value.size() > 0){
                SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(setFeedItemErrorHolder1.value), true);
            }

            //response
            if(setFeedItemReturnValueHolder1.value != null){
                FeedItemReturnValue returnValue = setFeedItemReturnValueHolder1.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<FeedItemValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            FeedItem feedItem = values.get(i).getFeedItem();
                            feedItemId1 = feedItem.getFeedItemId();
                            displayFeedItem(feedItem);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }

            //-----------------------------------------------
            // FeedItemService::mutate(SET) CALLEXTENSION
            //-----------------------------------------------
            //request
            FeedItem setFeedItemOperand2 = new FeedItem();
            setFeedItemOperand2.setAccountId(accountId);
            setFeedItemOperand2.setFeedItemId(feedItemId2);
            setFeedItemOperand2.setPlaceholderType(PlaceholderType.CALLEXTENSION);
            FeedItemAttribute setCallExtension = new FeedItemAttribute();
            setCallExtension.setPlaceholderField(PlaceholderField.CALL_PHONE_NUMBER);
            setCallExtension.setFeedAttributeValue("0120-556-789");
            setFeedItemOperand2.getFeedItemAttribute().add(setCallExtension);
            setFeedItemOperand2.setScheduling(new FeedItemScheduling());

            FeedItemOperation setFeedItemOperation2 = new FeedItemOperation();
            setFeedItemOperation2.setOperator(jp.yahooapis.ss.V5.FeedItemService.Operator.SET);
            setFeedItemOperation2.setAccountId(accountId);
            setFeedItemOperation2.setPlaceholderType(PlaceholderType.CALLEXTENSION);
            setFeedItemOperation2.getOperand().add(setFeedItemOperand2);

            //call API
            System.out.println("############################################");
            System.out.println("FeedItemService::mutate(SET) CALLEXTENSION");
            System.out.println("############################################");
            Holder<FeedItemReturnValue> setFeedItemReturnValueHolder2 = new Holder<FeedItemReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>> setFeedItemErrorHolder2 = new Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>>();
            feedItemService.mutate(setFeedItemOperation2, setFeedItemReturnValueHolder2, setFeedItemErrorHolder2);

            //if error
            if(setFeedItemErrorHolder2.value != null && setFeedItemErrorHolder2.value.size() > 0){
                SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(setFeedItemErrorHolder2.value), true);
            }

            //response
            if(setFeedItemReturnValueHolder2.value != null){
                FeedItemReturnValue returnValue = setFeedItemReturnValueHolder2.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<FeedItemValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            FeedItem feedItem = values.get(i).getFeedItem();
                            feedItemId2 = feedItem.getFeedItemId();
                            displayFeedItem(feedItem);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }


            //=================================================================
            // CampaignFeedService
            //=================================================================
            CampaignFeedServiceInterface campaignFeedService = SoapUtils.createServiceInterface(CampaignFeedServiceInterface.class, CampaignFeedService.class);

            //-----------------------------------------------
            // CampaignFeedService::mutate(SET)
            //-----------------------------------------------
            //request add QUICKLINK setting
            CampaignFeedList setCampaignFeedListOperand = new CampaignFeedList();
            setCampaignFeedListOperand.setAccountId(accountId);
            setCampaignFeedListOperand.setCampaignId(campaignId);
            setCampaignFeedListOperand.setPlaceholderType(jp.yahooapis.ss.V5.CampaignFeedService.PlaceholderType.QUICKLINK);
            CampaignFeed setCampaignFeed1 = new CampaignFeed();
            setCampaignFeed1.setFeedItemId(feedItemId1);
            setCampaignFeedListOperand.getCampaignFeed().add(setCampaignFeed1);
            setCampaignFeedListOperand.setDevicePlatform(DevicePlatform.DESKTOP);

            CampaignFeedOperation setCampaignFeedOperation = new CampaignFeedOperation();
            setCampaignFeedOperation.setOperator(jp.yahooapis.ss.V5.CampaignFeedService.Operator.SET);
            setCampaignFeedOperation.setAccountId(accountId);
            setCampaignFeedOperation.getOperand().add(setCampaignFeedListOperand);

            //call API
            System.out.println("############################################");
            System.out.println("CampaignFeedService::mutate(SET)");
            System.out.println("############################################");
            Holder<CampaignFeedReturnValue> setCampaignFeedReturnValueHolder = new Holder<CampaignFeedReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.CampaignFeedService.Error>> setCampaignFeedErrorHolder = new Holder<List<jp.yahooapis.ss.V5.CampaignFeedService.Error>>();
            campaignFeedService.mutate(setCampaignFeedOperation, setCampaignFeedReturnValueHolder, setCampaignFeedErrorHolder);

            //if error
            if(setCampaignFeedErrorHolder.value != null && setCampaignFeedErrorHolder.value.size() > 0){
                SoapUtils.displayErrors(new CampaignFeedServiceErrorEntityFactory(setCampaignFeedErrorHolder.value), true);
            }

            //response
            if(setCampaignFeedReturnValueHolder.value != null){
                CampaignFeedReturnValue returnValue = setCampaignFeedReturnValueHolder.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<CampaignFeedValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            CampaignFeedList campaignFeedList = values.get(i).getCampaignFeedList();
                            displayCampaignFeed(campaignFeedList);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new CampaignFeedServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }

            //-----------------------------------------------
            // CampaignFeedService::get
            //-----------------------------------------------
            //request
            CampaignFeedSelector campaignFeedSelector = new CampaignFeedSelector();
            campaignFeedSelector.setAccountId(accountId);
            campaignFeedSelector.getCampaignIds().add(campaignId);
            campaignFeedSelector.getFeedItemIds().add(feedItemId1);
            campaignFeedSelector.getPlaceholderTypes().add(jp.yahooapis.ss.V5.CampaignFeedService.PlaceholderType.QUICKLINK);
            campaignFeedSelector.getPlaceholderTypes().add(jp.yahooapis.ss.V5.CampaignFeedService.PlaceholderType.CALLEXTENSION);

            jp.yahooapis.ss.V5.CampaignFeedService.Paging campaignFeedPaging = new jp.yahooapis.ss.V5.CampaignFeedService.Paging();
            campaignFeedPaging.setStartIndex(1);
            campaignFeedPaging.setNumberResults(20);
            campaignFeedSelector.setPaging(campaignFeedPaging);

            //call API
            System.out.println("############################################");
            System.out.println("CampaignFeedService::get");
            System.out.println("############################################");
            Holder<CampaignFeedPage> campaignFeedPageHolder = new Holder<CampaignFeedPage>();
            Holder<List<jp.yahooapis.ss.V5.CampaignFeedService.Error>> getCampaignFeedErrorArrayHolder = new Holder<List<jp.yahooapis.ss.V5.CampaignFeedService.Error>>();
            campaignFeedService.get(campaignFeedSelector, campaignFeedPageHolder, getCampaignFeedErrorArrayHolder);

            //if error
            if(getCampaignFeedErrorArrayHolder.value != null && getCampaignFeedErrorArrayHolder.value.size() > 0){
                SoapUtils.displayErrors(new CampaignFeedServiceErrorEntityFactory(getCampaignFeedErrorArrayHolder.value), true);
            }

            //response
            if(campaignFeedPageHolder.value != null){
                if(campaignFeedPageHolder.value.getValues() != null){
                    for (CampaignFeedValues values : campaignFeedPageHolder.value.getValues()) {
                        if(values.isOperationSucceeded()){
                            //display response
                            displayCampaignFeed(values.getCampaignFeedList());
                        }else{
                            //if error
                            SoapUtils.displayErrors(new CampaignFeedServiceErrorEntityFactory(values.getError()), true);
                        }
                    }
                }
            }


            //=================================================================
            // AdGroupFeedService
            //=================================================================
            AdGroupFeedServiceInterface adGroupFeedService = SoapUtils.createServiceInterface(AdGroupFeedServiceInterface.class, AdGroupFeedService.class);

            //-----------------------------------------------
            // AdGroupFeedService::mutate(SET)
            //-----------------------------------------------
            //request add CALLEXTENSION setting
            AdGroupFeedList setAdGroupFeedListOperand = new AdGroupFeedList();
            setAdGroupFeedListOperand.setAccountId(accountId);
            setAdGroupFeedListOperand.setCampaignId(campaignId);
            setAdGroupFeedListOperand.setAdGroupId(adGroupId);
            setAdGroupFeedListOperand.setPlaceholderType(jp.yahooapis.ss.V5.AdGroupFeedService.PlaceholderType.CALLEXTENSION);
            AdGroupFeed setAdGroupFeed1 = new AdGroupFeed();
            setAdGroupFeed1.setFeedItemId(feedItemId2);
            setAdGroupFeedListOperand.getAdGroupFeed().add(setAdGroupFeed1);
            setAdGroupFeedListOperand.setDevicePlatform(jp.yahooapis.ss.V5.AdGroupFeedService.DevicePlatform.SMART_PHONE);

            AdGroupFeedOperation setAdGroupFeedOperation = new AdGroupFeedOperation();
            setAdGroupFeedOperation.setOperator(jp.yahooapis.ss.V5.AdGroupFeedService.Operator.SET);
            setAdGroupFeedOperation.setAccountId(accountId);
            setAdGroupFeedOperation.getOperand().add(setAdGroupFeedListOperand);

            //call API
            System.out.println("############################################");
            System.out.println("AdGroupFeedService::mutate(SET)");
            System.out.println("############################################");
            Holder<AdGroupFeedReturnValue> setAdGroupFeedReturnValueHolder = new Holder<AdGroupFeedReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.AdGroupFeedService.Error>> setAdGroupFeedErrorHolder = new Holder<List<jp.yahooapis.ss.V5.AdGroupFeedService.Error>>();
            adGroupFeedService.mutate(setAdGroupFeedOperation, setAdGroupFeedReturnValueHolder, setAdGroupFeedErrorHolder);

            //if error
            if(setAdGroupFeedErrorHolder.value != null && setAdGroupFeedErrorHolder.value.size() > 0){
                SoapUtils.displayErrors(new AdGroupFeedServiceErrorEntityFactory(setAdGroupFeedErrorHolder.value), true);
            }

            //response
            if(setAdGroupFeedReturnValueHolder.value != null){
                AdGroupFeedReturnValue returnValue = setAdGroupFeedReturnValueHolder.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<AdGroupFeedValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            AdGroupFeedList adGroupFeedList = values.get(i).getAdGroupFeedList();
                            displayAdGroupFeed(adGroupFeedList);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new AdGroupFeedServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }


            //-----------------------------------------------
            // AdGroupFeedService::get
            //-----------------------------------------------
            //request
            AdGroupFeedSelector adGroupFeedSelector = new AdGroupFeedSelector();
            adGroupFeedSelector.setAccountId(accountId);
            adGroupFeedSelector.getCampaignIds().add(campaignId);
            adGroupFeedSelector.getFeedItemIds().add(feedItemId2);
            adGroupFeedSelector.getPlaceholderTypes().add(jp.yahooapis.ss.V5.AdGroupFeedService.PlaceholderType.QUICKLINK);
            adGroupFeedSelector.getPlaceholderTypes().add(jp.yahooapis.ss.V5.AdGroupFeedService.PlaceholderType.CALLEXTENSION);
            jp.yahooapis.ss.V5.AdGroupFeedService.Paging adGroupFeedPaging = new jp.yahooapis.ss.V5.AdGroupFeedService.Paging();
            adGroupFeedPaging.setStartIndex(1);
            adGroupFeedPaging.setNumberResults(20);
            adGroupFeedSelector.setPaging(adGroupFeedPaging);

            //call API
            System.out.println("############################################");
            System.out.println("AdGroupFeedService::get");
            System.out.println("############################################");
            Holder<AdGroupFeedPage> adGroupFeedPageHolder = new Holder<AdGroupFeedPage>();
            Holder<List<jp.yahooapis.ss.V5.AdGroupFeedService.Error>> getAdGroupFeedErrorArrayHolder = new Holder<List<jp.yahooapis.ss.V5.AdGroupFeedService.Error>>();
            adGroupFeedService.get(adGroupFeedSelector, adGroupFeedPageHolder, getAdGroupFeedErrorArrayHolder);

            //if error
            if(getAdGroupFeedErrorArrayHolder.value != null && getAdGroupFeedErrorArrayHolder.value.size() > 0){
                SoapUtils.displayErrors(new AdGroupFeedServiceErrorEntityFactory(getAdGroupFeedErrorArrayHolder.value), true);
            }

            //response
            if(adGroupFeedPageHolder.value != null){
                if(adGroupFeedPageHolder.value.getValues() != null){
                    for (AdGroupFeedValues values : adGroupFeedPageHolder.value.getValues()) {
                        if(values.isOperationSucceeded()){
                            //display response
                            displayAdGroupFeed(values.getAdGroupFeedList());
                        }else{
                            //if error
                            SoapUtils.displayErrors(new AdGroupFeedServiceErrorEntityFactory(values.getError()), true);
                        }
                    }
                }
            }

            //-----------------------------------------------
            // CampaignFeedService::mutate(SET)
            //-----------------------------------------------
            //request remove QUICKLINK setting
            CampaignFeedList setCampaignFeedListOperandForRemove = new CampaignFeedList();
            setCampaignFeedListOperandForRemove.setAccountId(accountId);
            setCampaignFeedListOperandForRemove.setCampaignId(campaignId);
            setCampaignFeedListOperandForRemove.setPlaceholderType(jp.yahooapis.ss.V5.CampaignFeedService.PlaceholderType.QUICKLINK);
            CampaignFeed setCampaignFeed2 = new CampaignFeed();
            setCampaignFeed2.setFeedItemId(null);
            setCampaignFeedListOperandForRemove.getCampaignFeed().add(setCampaignFeed2);

            CampaignFeedOperation setCampaignFeedOperationForRemove = new CampaignFeedOperation();
            setCampaignFeedOperationForRemove.setOperator(jp.yahooapis.ss.V5.CampaignFeedService.Operator.SET);
            setCampaignFeedOperationForRemove.setAccountId(accountId);
            setCampaignFeedOperationForRemove.getOperand().add(setCampaignFeedListOperandForRemove);

            //call API
            System.out.println("############################################");
            System.out.println("CampaignFeedService::mutate(SET) REMOVE");
            System.out.println("############################################");
            Holder<CampaignFeedReturnValue> setCampaignFeedReturnValueHolderForRemove = new Holder<CampaignFeedReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.CampaignFeedService.Error>> setCampaignFeedErrorHolderForRemove = new Holder<List<jp.yahooapis.ss.V5.CampaignFeedService.Error>>();
            campaignFeedService.mutate(setCampaignFeedOperationForRemove, setCampaignFeedReturnValueHolderForRemove, setCampaignFeedErrorHolderForRemove);

            //if error
            if(setCampaignFeedErrorHolderForRemove.value != null && setCampaignFeedErrorHolderForRemove.value.size() > 0){
                SoapUtils.displayErrors(new CampaignFeedServiceErrorEntityFactory(setCampaignFeedErrorHolderForRemove.value), true);
            }

            //response
            if(setCampaignFeedReturnValueHolderForRemove.value != null){
                CampaignFeedReturnValue returnValue = setCampaignFeedReturnValueHolderForRemove.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<CampaignFeedValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            CampaignFeedList campaignFeedList = values.get(i).getCampaignFeedList();
                            displayCampaignFeed(campaignFeedList);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new CampaignFeedServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }


            //-----------------------------------------------
            // AdGroupFeedService::mutate(SET)
            //-----------------------------------------------
            //request remove CALLEXTENSION setting
            AdGroupFeedList setAdGroupFeedListOperandForRemove = new AdGroupFeedList();
            setAdGroupFeedListOperandForRemove.setAccountId(accountId);
            setAdGroupFeedListOperandForRemove.setCampaignId(campaignId);
            setAdGroupFeedListOperandForRemove.setAdGroupId(adGroupId);
            setAdGroupFeedListOperandForRemove.setPlaceholderType(jp.yahooapis.ss.V5.AdGroupFeedService.PlaceholderType.CALLEXTENSION);
            AdGroupFeed setAdGroupFeed2 = new AdGroupFeed();
            setAdGroupFeed2.setFeedItemId(null);
            setAdGroupFeedListOperandForRemove.getAdGroupFeed().add(setAdGroupFeed2);

            AdGroupFeedOperation setAdGroupFeedOperationForRemove = new AdGroupFeedOperation();
            setAdGroupFeedOperationForRemove.setOperator(jp.yahooapis.ss.V5.AdGroupFeedService.Operator.SET);
            setAdGroupFeedOperationForRemove.setAccountId(accountId);
            setAdGroupFeedOperationForRemove.getOperand().add(setAdGroupFeedListOperandForRemove);

            //call API
            System.out.println("############################################");
            System.out.println("AdGroupFeedService::mutate(SET) REMOVE");
            System.out.println("############################################");
            Holder<AdGroupFeedReturnValue> setAdGroupFeedReturnValueHolderForRemove = new Holder<AdGroupFeedReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.AdGroupFeedService.Error>> setAdGroupFeedErrorHolderForRemove = new Holder<List<jp.yahooapis.ss.V5.AdGroupFeedService.Error>>();
            adGroupFeedService.mutate(setAdGroupFeedOperationForRemove, setAdGroupFeedReturnValueHolderForRemove, setAdGroupFeedErrorHolderForRemove);

            //if error
            if(setAdGroupFeedErrorHolderForRemove.value != null && setAdGroupFeedErrorHolderForRemove.value.size() > 0){
                SoapUtils.displayErrors(new AdGroupFeedServiceErrorEntityFactory(setAdGroupFeedErrorHolderForRemove.value), true);
            }

            //response
            if(setAdGroupFeedReturnValueHolderForRemove.value != null){
                AdGroupFeedReturnValue returnValue = setAdGroupFeedReturnValueHolderForRemove.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<AdGroupFeedValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            AdGroupFeedList adGroupFeedList = values.get(i).getAdGroupFeedList();
                            displayAdGroupFeed(adGroupFeedList);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new AdGroupFeedServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }

            //-----------------------------------------------
            // FeedItemService::mutate(REMOVE)
            //-----------------------------------------------
            //request
            FeedItem removeFeedItemOperand = new FeedItem();
            removeFeedItemOperand.setAccountId(accountId);
            removeFeedItemOperand.setFeedItemId(feedItemId1);
            removeFeedItemOperand.setPlaceholderType(PlaceholderType.QUICKLINK);

            FeedItemOperation removeFeedItemOperation = new FeedItemOperation();
            removeFeedItemOperation.setOperator(jp.yahooapis.ss.V5.FeedItemService.Operator.REMOVE);
            removeFeedItemOperation.setAccountId(accountId);
            removeFeedItemOperation.setPlaceholderType(PlaceholderType.QUICKLINK);
            removeFeedItemOperation.getOperand().add(removeFeedItemOperand);

            //call API
            System.out.println("############################################");
            System.out.println("FeedItemService::mutate(REMOVE)");
            System.out.println("############################################");
            Holder<FeedItemReturnValue> removeFeedItemReturnValueHolder = new Holder<FeedItemReturnValue>();
            Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>> removeFeedItemErrorHolder = new Holder<List<jp.yahooapis.ss.V5.FeedItemService.Error>>();
            feedItemService.mutate(removeFeedItemOperation, removeFeedItemReturnValueHolder, removeFeedItemErrorHolder);

            //if error
            if(removeFeedItemErrorHolder.value != null && removeFeedItemErrorHolder.value.size() > 0){
                SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(removeFeedItemErrorHolder.value), true);
            }

            //response
            if(removeFeedItemReturnValueHolder.value != null){
                FeedItemReturnValue returnValue = removeFeedItemReturnValueHolder.value;
                if(returnValue.getValues() != null && returnValue.getValues().size() > 0){
                    List<FeedItemValues> values = returnValue.getValues();
                    for(int i=0; i<values.size(); i++){
                        if(values.get(i).isOperationSucceeded()){
                            //display response
                            FeedItem feedItem = values.get(i).getFeedItem();
                            displayFeedItem(feedItem);
                        }else{
                            //if error
                            SoapUtils.displayErrors(new FeedItemServiceErrorEntityFactory(values.get(i).getError()), true);
                        }
                    }
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * display FeedItem entity to stdout.
     * @param feedItem FeedItem entity for display.
     */
    private static void displayFeedItem(FeedItem feedItem){
        System.out.println("accountId = " + feedItem.getAccountId());
        System.out.println("placeholderType = " + feedItem.getPlaceholderType());
        System.out.println("feedItemId = " + feedItem.getFeedItemId());
        System.out.println("approvalStatus = " + feedItem.getApprovalStatus());
        if(feedItem.getFeedItemAttribute() != null){
            for(FeedItemAttribute feedItemAttribute : feedItem.getFeedItemAttribute()){
                System.out.println("feedItemAttribute/placeholderField = "   + feedItemAttribute.getPlaceholderField());
                System.out.println("feedItemAttribute/feedAttributeValue = " + feedItemAttribute.getFeedAttributeValue());
                if(feedItemAttribute.getEditFeedAttributeValue() != null){
                    System.out.println("feedItemAttribute/editFeedAttributeValue = " + feedItemAttribute.getEditFeedAttributeValue());
                }
            }
        }
        
        if(feedItem.getDevicePreference() != null){
            System.out.println("devicePreference = " + feedItem.getDevicePreference());
        }
        
        System.out.println("startDate = " + feedItem.getStartDate());
        System.out.println("endDate = " + feedItem.getEndDate());
        
        for (FeedItemSchedule schedule : feedItem.getScheduling().getSchedules()) {
           System.out.println("scheduling/schedules/dayOfWeek = " + schedule.getDayOfWeek());
           System.out.println("scheduling/schedules/startHour = " + schedule.getStartHour());
           System.out.println("scheduling/schedules/startMinute = " + schedule.getStartMinute());
           System.out.println("scheduling/schedules/endHour = " + schedule.getEndHour());
           System.out.println("scheduling/schedules/endMinute = " + schedule.getEndMinute());
        }
        System.out.println("---------");
    }

    /**
     * display CampaignFeed entity to stdout.
     * @param campaignFeed CampaignFeed entity for display.
     */
    private static void displayCampaignFeed(CampaignFeedList campaignFeedList){
        System.out.println("accountId = " + campaignFeedList.getAccountId());
        System.out.println("campaignId = " + campaignFeedList.getCampaignId());
        System.out.println("placeholderType = " + campaignFeedList.getPlaceholderType());
        if(campaignFeedList.getCampaignFeed() != null){
            for(CampaignFeed campaignFeed : campaignFeedList.getCampaignFeed()){
                System.out.println("campaignFeed/accountld = "       + campaignFeed.getAccountId());
                System.out.println("campaignFeed/campaignld = "      + campaignFeed.getCampaignId());
                System.out.println("campaignFeed/feedItemld = "      + campaignFeed.getFeedItemId());
                System.out.println("campaignFeed/placeholderType = " + campaignFeed.getPlaceholderType());
            }
        }
        System.out.println("---------");
    }

    /**
     * display AdGroupFeed entity to stdout.
     * @param adGroupFeed AdGroupFeed entity for display.
     */
    private static void displayAdGroupFeed(AdGroupFeedList adGroupFeedList){
        System.out.println("accountId = " + adGroupFeedList.getAccountId());
        System.out.println("campaignId = " + adGroupFeedList.getCampaignId());
        System.out.println("adGroupId = " + adGroupFeedList.getAdGroupId());
        System.out.println("placeholderType = " + adGroupFeedList.getPlaceholderType());
        if(adGroupFeedList.getAdGroupFeed() != null){
            for(AdGroupFeed adGroupFeed : adGroupFeedList.getAdGroupFeed()){
                System.out.println("adGroupFeed/accountld = "       + adGroupFeed.getAccountId());
                System.out.println("adGroupFeed/campaignld = "      + adGroupFeed.getCampaignId());
                System.out.println("adGroupFeed/adGroupld = "       + adGroupFeed.getAdGroupId());
                System.out.println("adGroupFeed/feedItemld = "      + adGroupFeed.getFeedItemId());
                System.out.println("adGroupFeed/placeholderType = " + adGroupFeed.getPlaceholderType());
            }
        }
        System.out.println("---------");
    }

}
