package accountSample;

import error.impl.AccountServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.AccountService.*;
import jp.yahooapis.ss.V5.AccountService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Program for AccountService. Copyright (C) 2012 Yahoo Japan
 * Corporation. All Rights Reserved.
 */
public class AccountSample {

    /**
     * main method for AccountSample
     *
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {

        try {
            long accountId = SoapUtils.getAccountId();

            // =================================================================
            // AccountService
            // =================================================================
            AccountServiceInterface accountService = SoapUtils.createServiceInterface(AccountServiceInterface.class, AccountService.class);

            // -----------------------------------------------
            // AccountService::get(without accountIds)
            // -----------------------------------------------
            // request
            AccountSelector accountSelector = new AccountSelector();
            accountSelector.getAccountStatuses().addAll(Arrays.asList(AccountStatus.SERVING, AccountStatus.ENDED));
            accountSelector.getAccountTypes().addAll(Arrays.asList(AccountType.INVOICE, AccountType.PREPAY ));
            Paging paging = new Paging();
            paging.setStartIndex(1);
            paging.setNumberResults(20);
            accountSelector.setPaging(paging);

            // call API
            System.out.println("############################################");
            System.out.println("AccountService::get(without accountIds)");
            System.out.println("############################################");

            Holder<AccountPage> accountPageHolder = new Holder<AccountPage>();
            Holder<List<Error>> accountErrorHolder = new Holder<List<Error>>();
            accountService.get(accountSelector, accountPageHolder, accountErrorHolder);

            // if error
            if (accountErrorHolder.value != null
                    && accountErrorHolder.value.size() > 0) {
                SoapUtils.displayErrors(new AccountServiceErrorEntityFactory(accountErrorHolder.value), true);
            }

            // response
            if (accountPageHolder.value != null) {
                AccountPage accountPage = accountPageHolder.value;
                if (accountPage.getValues() != null
                        && accountPage.getValues().size() > 0) {
                    for (int i = 0; i < accountPage.getValues().size(); i++) {
                        AccountValues accountValues = accountPage.getValues().get(i);
                        if (accountValues.isOperationSucceeded()) {
                            // display response
                            displayAccount(accountValues.getAccount());
                        } else {
                            // if error
                            SoapUtils.displayErrors(new AccountServiceErrorEntityFactory(accountValues.getError()), false);
                        }
                    }
                }
            }

            // -----------------------------------------------
            // AccountService::get(with accountIds)
            // -----------------------------------------------
            // request
            accountSelector.getAccountIds().add(new Long(accountId));
            accountSelector.setPaging(paging);

            // call API
            System.out.println("############################################");
            System.out.println("AccountService::get(with accountIds)");
            System.out.println("############################################");
            accountService.get(accountSelector, accountPageHolder,accountErrorHolder);

            // if error
            if (accountErrorHolder.value != null
                    && accountErrorHolder.value.size() > 0) {
                SoapUtils.displayErrors(new AccountServiceErrorEntityFactory(accountErrorHolder.value), true);
            }

            // response
            if (accountPageHolder.value != null) {
                AccountPage accountPage = accountPageHolder.value;
                if (accountPage.getValues() != null
                        && accountPage.getValues().size() > 0) {
                    for (int i = 0; i < accountPage.getValues().size(); i++) {
                        AccountValues accountValues = accountPage.getValues().get(i);
                        if (accountValues.isOperationSucceeded()) {
                            // display response
                            displayAccount(accountValues.getAccount());
                        } else {
                            // if error
                            SoapUtils.displayErrors(new AccountServiceErrorEntityFactory(accountValues.getError()), true);
                        }
                    }
                }
            }

            // -----------------------------------------------
            // AccountService::mutate(SET)
            // -----------------------------------------------
            // request
            AccountOperation accountOperation = new AccountOperation();
            accountOperation.setOperator(Operator.SET);

            Account accountOperand = new Account();
            accountOperand.setAccountId(accountId);
            accountOperand.setAccountName("SampleAccount_UpdateOn_" + SoapUtils.getCurrentTimestamp());
            accountOperand.setDeliveryStatus(DeliveryStatus.PAUSED);

            accountOperation.getOperand().add(accountOperand);

            // call API
            System.out.println("############################################");
            System.out.println("AccountService::mutate(SET)");
            System.out.println("############################################");
            Holder<AccountReturnValue> accountReturnValueHolder = new Holder<AccountReturnValue>();
            Holder<List<Error>> accountSetErrorHolder = new Holder<List<Error>>();
            accountService.mutate(accountOperation, accountReturnValueHolder,accountSetErrorHolder);

            // if error
            if (accountSetErrorHolder.value != null
                    && accountSetErrorHolder.value.size() > 0) {
                SoapUtils.displayErrors(new AccountServiceErrorEntityFactory(accountErrorHolder.value), true);
            }

            // response
            if (accountReturnValueHolder.value != null) {
                AccountReturnValue returnValue = accountReturnValueHolder.value;
                if (returnValue.getValues() != null) {
                    for (AccountValues accountValues : returnValue.getValues()) {
                        if (accountValues.isOperationSucceeded()) {
                            // display response
                            displayAccount(accountValues.getAccount());
                        } else {
                            // if error
                            SoapUtils.displayErrors(new AccountServiceErrorEntityFactory(accountValues.getError()), true);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * display account entity to stdout.
     *
     * @param account
     *            account entity for display.
     */
    private static void displayAccount(Account account) {
        System.out.println("accountId = " + account.getAccountId());
        System.out.println("accountName = " + account.getAccountName());
        System.out.println("accountType = " + account.getAccountType().toString());
        System.out.println("accountStatus = " + account.getAccountStatus().toString());
        System.out.println("deliverStatus = " + account.getDeliveryStatus().toString());
        if (account.getBudget() != null) {
            System.out.println("budget/amount = " + account.getBudget().getAmount());
            System.out.println("budget/limitChargeType = " + account.getBudget().getLimitChargeType());
            System.out.println("budget/startDate = " + account.getBudget().getStartDate());
            System.out.println("budget/endDate = " + account.getBudget().getEndDate());
        }
        System.out.println("---------");
    }
}
