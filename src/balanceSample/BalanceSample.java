package balanceSample;

import error.impl.BalanceServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.BalanceService.*;
import jp.yahooapis.ss.V5.BalanceService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.List;


/**
 * Sample Program for BalanceService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class BalanceSample {
    /**
     * main method for BalanceSample
     * @param args command line arguments
     */
    public static void main(String[] args) {

        try{
            long accountId = SoapUtils.getAccountId();

            //=================================================================
            // BalanceService
            //=================================================================
            BalanceServiceInterface balanceService = SoapUtils.createServiceInterface(BalanceServiceInterface.class,BalanceService.class);

            //-----------------------------------------------
            // BalanceService::get
            //-----------------------------------------------
            //request
            BalanceSelector balanceSelector = new BalanceSelector();
            balanceSelector.getAccountIds().add(new Long(accountId));

            //call API
            System.out.println("############################################");
            System.out.println("BalanceService::get");
            System.out.println("############################################");

            Holder<BalancePage> balancePageHolder = new Holder<BalancePage>();
            Holder<List<Error>> balanceErrorHolder = new Holder<List<Error>>();
            balanceService.get(balanceSelector, balancePageHolder, balanceErrorHolder);

            //if error
            if(balanceErrorHolder.value != null && balanceErrorHolder.value.size() > 0){
                SoapUtils.displayErrors(new BalanceServiceErrorEntityFactory(balanceErrorHolder.value), true);
            }

            //response
            if(balancePageHolder.value != null){
                BalancePage balancePage = balancePageHolder.value;
                if(balancePage.getValues() != null){
                    for (BalanceValues balanceValues : balancePage.getValues()) {
                        //display response
                        if(balanceValues.isOperationSucceeded()){
                            Balance balance =balanceValues.getBalance();
                            System.out.println("accountId = " + balance.getAccountId());
                            System.out.println("balance = " + balance.getBalance());
                        }else{
                            //if error
                            SoapUtils.displayErrors(new BalanceServiceErrorEntityFactory(balanceValues.getError()), true);
                        }
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

