package dictionarySample;

import error.impl.DictionaryServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.DictionaryService.*;
import jp.yahooapis.ss.V5.DictionaryService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.List;


/**
 * Sample Program for DictionaryService. Copyright (C) 2012 Yahoo Japan
 * Corporation. All Rights Reserved.
 */
public class DictionarySample {

    /**
     * main method for DictionarySample
     *
     * @param args
     *            command line arguments
     */
    public static void main(String[] args){
        try {
            // =================================================================
            // DictionaryService
            // =================================================================
            DictionaryServiceInterface dictionaryService = SoapUtils.createServiceInterface(DictionaryServiceInterface.class, DictionaryService.class);

           //-----------------------------------------------
           // DictionaryService::getDisapprovalReason
           //-----------------------------------------------
           //request
           DisapprovalReasonSelector disapprovalReasonSelector = new DisapprovalReasonSelector();
           disapprovalReasonSelector.setLang(DictionaryLang.EN);

           //call API
           System.out.println("############################################");
           System.out.println("DictionaryService::getDisapprovalReason");
           System.out.println("############################################");
           Holder<DisapprovalReasonPage> disapprovalReasonPageHolder = new Holder<DisapprovalReasonPage>();
           Holder<List<Error>> errorArrayHolder = new Holder<List<Error>>();
           dictionaryService.getDisapprovalReason(disapprovalReasonSelector, disapprovalReasonPageHolder, errorArrayHolder);

           // if error
           if(errorArrayHolder.value != null && errorArrayHolder.value.size() > 0){
               SoapUtils.displayErrors(new DictionaryServiceErrorEntityFactory(errorArrayHolder.value), true);
           }

           //reponse
           if(disapprovalReasonPageHolder.value != null){
               if(disapprovalReasonPageHolder.value.getValues() != null){
                   for (DisapprovalReasonValues values : disapprovalReasonPageHolder.value.getValues()) {
                    if(values.isOperationSucceeded()){
                        displayDisapprovalReason(values.getDisapprovalReason());
                    }else{
                        //if error
                        SoapUtils.displayErrors(new DictionaryServiceErrorEntityFactory(values.getError()), true);
                    }
                }
               }
           }

           //-----------------------------------------------
           // DictionaryService::getGeographicLocation
           //-----------------------------------------------
           //request
           GeographicLocationSelector geographicLocationSelector = new GeographicLocationSelector();
           geographicLocationSelector.setLang(DictionaryLang.JA);

           //call API
           System.out.println("############################################");
           System.out.println("DictionaryService::getGeographicLocation");
           System.out.println("############################################");
           Holder<GeographicLocationPage> geographicLocationPageHolder = new Holder<GeographicLocationPage>();
           Holder<List<Error>> errorArrayHolder2 = new Holder<List<Error>>();
           dictionaryService.getGeographicLocation(geographicLocationSelector, geographicLocationPageHolder, errorArrayHolder2);

           // if error
           if(errorArrayHolder2.value != null && errorArrayHolder2.value.size() > 0){
               SoapUtils.displayErrors(new DictionaryServiceErrorEntityFactory(errorArrayHolder2.value), true);
           }

           //reponse
           if(geographicLocationPageHolder.value != null){
               if(geographicLocationPageHolder.value.getValues() != null){
                   for (GeographicLocationValues values : geographicLocationPageHolder.value.getValues()) {
                    if(values.isOperationSucceeded()){
                        displayGeographicLocation(values.getGeographicLocation());
                    }else{
                        //if error
                        SoapUtils.displayErrors(new DictionaryServiceErrorEntityFactory(values.getError()), true);
                    }
                }
               }
           }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * display GeographicLocation entity to stdout.
     *
     * @param geographicLocation GeographicLocation entity for display.
     */
    private static void displayGeographicLocation(GeographicLocation geographicLocation) {
        System.out.println("code = " + geographicLocation.getCode());
        System.out.println("parent = " + geographicLocation.getParent());
        System.out.println("name = " + geographicLocation.getName());
        System.out.println("fullName = " + geographicLocation.getFullName());
        System.out.println("order = " + geographicLocation.getOrder());
        System.out.println("status = " + geographicLocation.getStatus());
        
        List<GeographicLocation> geographicLocationList = geographicLocation.getChild();
        for(int i = 0; i < geographicLocationList.size(); i++){
            GeographicLocation childGeographicLocation = geographicLocationList.get(i);
            System.out.println("child["+ i +"]:code = " + childGeographicLocation.getCode());
            System.out.println("child["+ i +"]:parent = " + childGeographicLocation.getParent());
            System.out.println("child["+ i +"]:name = " + childGeographicLocation.getName());
            System.out.println("child["+ i +"]:fullName = " + childGeographicLocation.getFullName());
            System.out.println("child["+ i +"]:order = " + childGeographicLocation.getOrder());
            System.out.println("child["+ i +"]:status = " + childGeographicLocation.getStatus());
        }
        System.out.println("---------");
    }

    /**
     * display DisapprovalReason entity to stdout.
     *
     * @param disapprovalReason DisapprovalReason entity for display.
     */
    private static void displayDisapprovalReason(DisapprovalReason disapprovalReason) {
        System.out.println("disapprovalReasonCode = " + disapprovalReason.getDisapprovalReasonCode());
        System.out.println("lang = " + disapprovalReason.getLang());
        System.out.println("title = " + disapprovalReason.getTitle());
        System.out.println("description = " + disapprovalReason.getDescription());
        System.out.println("recommendation = " + disapprovalReason.getRecommendation());
        System.out.println("---------");
    }
}
