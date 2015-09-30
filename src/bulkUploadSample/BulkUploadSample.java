package bulkUploadSample;

import error.impl.BulkServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.BulkService.*;
import jp.yahooapis.ss.V5.BulkService.Error;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import util.SoapUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.Holder;
import java.io.StringReader;
import java.util.List;


/**
 * Sample Program for Bulk Upload.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class BulkUploadSample {

    /**
     * main method for BulkUploadSample
     * @param args command line arguments
     */
    public static void main(String[] args){

        try{

            long accountId = SoapUtils.getAccountId();

            //=================================================================
            // BulkService
            //=================================================================
            BulkServiceInterface bulkService = SoapUtils.createServiceInterface(BulkServiceInterface.class, BulkService.class);

            //-----------------------------------------------
            // BulkService::getUploadUrl
            //-----------------------------------------------
            //call API
            System.out.println("############################################");
            System.out.println("BulkService::getUploadUrl");
            System.out.println("############################################");
            Holder<UploadUrlValue> getUploadUrlValueHolder = new Holder<UploadUrlValue>();
            Holder<List<Error>> getUploadUrlErrorHolder = new Holder<List<Error>>();
            bulkService.getUploadUrl(accountId, getUploadUrlValueHolder, getUploadUrlErrorHolder);

            //if error
            if(getUploadUrlErrorHolder.value != null && getUploadUrlErrorHolder.value.size() > 0){
                SoapUtils.displayErrors(new BulkServiceErrorEntityFactory(getUploadUrlErrorHolder.value), true);
            }

            //response
            String uploadUrlStr = null;
            if(getUploadUrlValueHolder.value != null){
                UploadUrlValue uploadUrlValue = getUploadUrlValueHolder.value;
                System.out.println("accountId = " + uploadUrlValue.getAccountId());
                System.out.println("uploadUrl = " + uploadUrlValue.getUploadUrl());
                System.out.println("acceptableUploadStatus = " + uploadUrlValue.isAcceptableUploadStatus());
                if(!uploadUrlValue.isAcceptableUploadStatus()){
                    System.out.println("AcceptableUploadStatus is false at this accountId. Please wait a minute.");
                    System.exit(0);
                }
                uploadUrlStr = uploadUrlValue.getUploadUrl();
            }

            //-----------------------------------------------
            // BulkService::upload
            //-----------------------------------------------
            System.out.println("############################################");
            System.out.println("BulkService::upload");
            System.out.println("############################################");
            String response = SoapUtils.upload(uploadUrlStr, "SampleBulkUpload.csv", "text/csv");

            //get upload bulk job ID
            long uploadJobId = -1;
            Document responseDOM = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
            NodeList nodeList = responseDOM.getElementsByTagName("uploadBulkJobId");
            if(nodeList.getLength() > 0){
                uploadJobId = Long.parseLong(nodeList.item(0).getFirstChild().getNodeValue());
            }else{
                throw new RuntimeException("Fail to upload to Bulk. response=" + response);
            }

            //-----------------------------------------------
            // BulkService::getBulkUploadStatus
            //-----------------------------------------------
            //request
            BulkUploadStatusSelector bulkUploadStatusSelector = new BulkUploadStatusSelector();
            bulkUploadStatusSelector.setAccountId(accountId);
            bulkUploadStatusSelector.getUploadBulkJobIds().add(uploadJobId);
            bulkUploadStatusSelector.setLang(BulkLang.JA);
            bulkUploadStatusSelector.setOutput(BulkOutput.CSV);
            bulkUploadStatusSelector.setEncoding(BulkEncoding.SJIS);
            Paging paging = new Paging();
            paging.setStartIndex(1);
            paging.setNumberResults(20);
            bulkUploadStatusSelector.setPaging(paging);

            String downloadBulkUploadFileUrl = null;
            String downloadBulkUploadErrorFileUrl = null;
            UploadBulkJobStatus jobStatus = null;

            for(int wait_count=0; wait_count<30; wait_count++){
                //sleep 30 second.
                System.out.println("\n***** sleep 30 seconds for Bulk Upload Job *****\n");
                Thread.sleep(30000);

                //call API
                System.out.println("############################################");
                System.out.println("BulkService::getBulkUploadStatus");
                System.out.println("############################################");
                Holder<BulkUploadStatusPage> getBulkUploadStatusPageHolder = new Holder<BulkUploadStatusPage>();
                Holder<List<Error>> getBulkUploadStatusErrorHolder = new Holder<List<Error>>();
                bulkService.getBulkUploadStatus(bulkUploadStatusSelector, getBulkUploadStatusPageHolder, getBulkUploadStatusErrorHolder);

                //if error
                if(getBulkUploadStatusErrorHolder.value != null && getBulkUploadStatusErrorHolder.value.size() > 0){
                    SoapUtils.displayErrors(new BulkServiceErrorEntityFactory(getUploadUrlErrorHolder.value), true);
                }

                //response
                if(getBulkUploadStatusPageHolder.value != null){
                    BulkUploadStatusPage page = getBulkUploadStatusPageHolder.value;
                    if(page.getValues() != null && page.getValues().size() > 0){
                        List<BulkUploadValues> values = page.getValues();
                        for(int i=0; i<values.size(); i++){
                            if(values.get(i).isOperationSucceeded()){
                                UploadBulkJob uploadBulkJob = values.get(i).getUploadBulkJob();
                                jobStatus = uploadBulkJob.getUploadBulkJobStatus();
                                System.out.println("----------");
                                System.out.println("accountId = " + uploadBulkJob.getAccountId());
                                System.out.println("uploadBulkJobId = " + uploadBulkJob.getUploadBulkJobId());
                                System.out.println("uploadBulkJobName = " + uploadBulkJob.getUploadBulkJobName());
                                System.out.println("uploadBulkUserName = " + uploadBulkJob.getUploadBulkUserName());
                                System.out.println("uploadBulkStartDate = " + uploadBulkJob.getUploadBulkStartDate());
                                System.out.println("uploadBulkEndDate = " + uploadBulkJob.getUploadBulkEndDate());
                                System.out.println("uploadBulkJobStatus = " + uploadBulkJob.getUploadBulkJobStatus().toString());
                                System.out.println("progress = " + uploadBulkJob.getProgress());
                                if(uploadBulkJob.getProcessingItemsCount() != null){
                                    System.out.println("processingItemsCount/campaignCount = " + uploadBulkJob.getProcessingItemsCount().getCampaignCount());
                                    System.out.println("processingItemsCount/campaignErrorCount = " + uploadBulkJob.getProcessingItemsCount().getCampaignErrorCount());
                                    System.out.println("processingItemsCount/negativeCampaignCriterionCount = " + uploadBulkJob.getProcessingItemsCount().getNegativeCampaignCriterionCount());
                                    System.out.println("processingItemsCount/negativeCampaignCriterionErrorCount = " + uploadBulkJob.getProcessingItemsCount().getNegativeCampaignCriterionErrorCount());
                                    System.out.println("processingItemsCount/adGroupCount = " + uploadBulkJob.getProcessingItemsCount().getAdGroupCount());
                                    System.out.println("processingItemsCount/adGroupErrorCount = " + uploadBulkJob.getProcessingItemsCount().getAdGroupErrorCount());
                                    System.out.println("processingItemsCount/negativeAdGroupCriterionCount = " + uploadBulkJob.getProcessingItemsCount().getNegativeAdGroupCriterionCount());
                                    System.out.println("processingItemsCount/negativeAdGroupCriterionErrorCount = " + uploadBulkJob.getProcessingItemsCount().getNegativeAdGroupCriterionErrorCount());
                                    System.out.println("processingItemsCount/biddableAdGroupCriterionCount = " + uploadBulkJob.getProcessingItemsCount().getBiddableAdGroupCriterionCount());
                                    System.out.println("processingItemsCount/biddableAdGroupCriterionErrorCount = " + uploadBulkJob.getProcessingItemsCount().getBiddableAdGroupCriterionErrorCount());
                                    System.out.println("processingItemsCount/adGroupAdCount = " + uploadBulkJob.getProcessingItemsCount().getAdGroupAdCount());
                                    System.out.println("processingItemsCount/adGroupAdErrorCount = " + uploadBulkJob.getProcessingItemsCount().getAdGroupAdErrorCount());
                                }
                                if(uploadBulkJob.getDownloadBulkUploadFileUrl() != null){
                                    downloadBulkUploadFileUrl = uploadBulkJob.getDownloadBulkUploadFileUrl();
                                    System.out.println("downloadBulkUploadFileUrl = " + uploadBulkJob.getDownloadBulkUploadFileUrl());
                                }
                                if(uploadBulkJob.getDownloadBulkUploadErrorFileUrl() != null){
                                    downloadBulkUploadErrorFileUrl = uploadBulkJob.getDownloadBulkUploadErrorFileUrl();
                                    System.out.println("downloadBulkUploadErrorFileUrl = " + uploadBulkJob.getDownloadBulkUploadErrorFileUrl());
                                }
                            }else{
                                SoapUtils.displayErrors(new BulkServiceErrorEntityFactory(getUploadUrlErrorHolder.value), true);
                            }
                        }
                    }
                }

                if(jobStatus.equals(UploadBulkJobStatus.COMPLETED)){
                    break;
                }else{
                    if(jobStatus.equals(UploadBulkJobStatus.IN_PROGRESS)){
                        continue;
                    }else{
                        System.out.println("\nError : Job Status failed.");
                        System.exit(0);
                    }
                }
            }

            if(jobStatus != null && jobStatus.equals(UploadBulkJobStatus.COMPLETED)){
                System.out.println("Success to upload Bulk Sheet.");
            }else{
                System.out.println("BulkUpload job in process on long time. please wait.");
                System.exit(0);
            }

            //-----------------------------------------------
            // BulkService::downloadBulkUploadFile
            //-----------------------------------------------
            if(downloadBulkUploadFileUrl != null){
                System.out.println("\n############################################");
                System.out.println("BulkService::downloadBulkUploadFile");
                System.out.println("############################################");
                SoapUtils.download(downloadBulkUploadFileUrl, "SampleBulkUploadFile.csv");
            }

            //-----------------------------------------------
            // BulkService::downloadBulkUploadErrorFile
            //-----------------------------------------------
            if(downloadBulkUploadErrorFileUrl != null){
                System.out.println("\n############################################");
                System.out.println("BulkService::downloadBulkUploadErrorFile");
                System.out.println("############################################");
                SoapUtils.download(downloadBulkUploadErrorFileUrl, "SampleBulkUploadErrorFile.csv");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
