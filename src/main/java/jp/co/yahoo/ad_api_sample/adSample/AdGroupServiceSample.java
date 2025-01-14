package jp.co.yahoo.ad_api_sample.adSample;

import java.util.Arrays;
import java.util.List;

import javax.xml.ws.Holder;

import jp.co.yahoo.ad_api_sample.error.impl.AdGroupServiceErrorEntityFactory;
import jp.co.yahoo.ad_api_sample.util.SoapUtils;
import jp.yahooapis.ss.V5.AdGroupService.AdGroup;
import jp.yahooapis.ss.V5.AdGroupService.AdGroupOperation;
import jp.yahooapis.ss.V5.AdGroupService.AdGroupPage;
import jp.yahooapis.ss.V5.AdGroupService.AdGroupReturnValue;
import jp.yahooapis.ss.V5.AdGroupService.AdGroupSelector;
import jp.yahooapis.ss.V5.AdGroupService.AdGroupService;
import jp.yahooapis.ss.V5.AdGroupService.AdGroupServiceInterface;
import jp.yahooapis.ss.V5.AdGroupService.AdGroupValues;
import jp.yahooapis.ss.V5.AdGroupService.Bid;
import jp.yahooapis.ss.V5.AdGroupService.BiddingStrategy;
import jp.yahooapis.ss.V5.AdGroupService.BiddingStrategyType;
import jp.yahooapis.ss.V5.AdGroupService.BudgetOptimizerBiddingScheme;
import jp.yahooapis.ss.V5.AdGroupService.CriterionType;
import jp.yahooapis.ss.V5.AdGroupService.EnhancedCpcBiddingScheme;
import jp.yahooapis.ss.V5.AdGroupService.Error;
import jp.yahooapis.ss.V5.AdGroupService.ManualCpcBiddingScheme;
import jp.yahooapis.ss.V5.AdGroupService.Operator;
import jp.yahooapis.ss.V5.AdGroupService.PageOnePromotedBiddingScheme;
import jp.yahooapis.ss.V5.AdGroupService.Paging;
import jp.yahooapis.ss.V5.AdGroupService.TargetAll;
import jp.yahooapis.ss.V5.AdGroupService.TargetCpaBiddingScheme;
import jp.yahooapis.ss.V5.AdGroupService.TargetRoasBiddingScheme;
import jp.yahooapis.ss.V5.AdGroupService.TargetSpendBiddingScheme;
import jp.yahooapis.ss.V5.AdGroupService.TargetingSetting;
import jp.yahooapis.ss.V5.AdGroupService.UserStatus;

/**
 * Sample Program for AdGroupService. Copyright (C) 2012 Yahoo Japan Corporation. All Rights
 * Reserved.
 */
public class AdGroupServiceSample {

  /**
   * main method for AdGroupServiceSample
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws Exception {
    try {
      // =================================================================
      // Setting
      // =================================================================
      long accountId = SoapUtils.getAccountId();
      long campaignId = SoapUtils.getCampaignId();
      long appCampaignId = SoapUtils.getAppCampaignId();
      long biddingStrategyId = SoapUtils.getBiddingStrategyId();

      // =================================================================
      // AdGroupService ADD
      // =================================================================
      // Set Operation
      AdGroupOperation addAdGroupOperation = createSampleAddRequest(accountId, campaignId, appCampaignId, biddingStrategyId);

      // Run
      List<AdGroupValues> adGroupValues = add(addAdGroupOperation);

      // =================================================================
      // AdGroupService GET
      // =================================================================
      // Set Selector
      AdGroupSelector adGroupSelector = createSampleGetRequest(accountId, adGroupValues);

      // Run
      get(adGroupSelector);

      // =================================================================
      // AdGroupService SET
      // =================================================================
      // Set Operation
      AdGroupOperation setAdGroupOperation = createSampleSetRequest(accountId, biddingStrategyId, adGroupValues);

      // Run
      set(setAdGroupOperation);

      // =================================================================
      // AdGroupService REMOVE
      // =================================================================
      // Set Operation
      AdGroupOperation removeAdGroupOperation = createSampleRemoveRequest(accountId, adGroupValues);

      // Run
      remove(removeAdGroupOperation);

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * Sample Program for AdGroupService ADD.
   *
   * @param operation AdGroupOperation
   * @return AdGroupValues
   * @throws Exception
   */
  public static List<AdGroupValues> add(AdGroupOperation operation) throws Exception {

    // Call API
    System.out.println("############################################");
    System.out.println("AdGroupService::mutate(ADD)");
    System.out.println("############################################");

    Holder<AdGroupReturnValue> adGroupReturnValueHolder = new Holder<AdGroupReturnValue>();
    Holder<List<Error>> errorHolder = new Holder<List<Error>>();
    AdGroupServiceInterface adGroupService = SoapUtils.createServiceInterface(AdGroupServiceInterface.class, AdGroupService.class);
    adGroupService.mutate(operation, adGroupReturnValueHolder, errorHolder);

    // Error
    if (errorHolder.value != null && errorHolder.value.size() > 0) {
      SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(errorHolder.value), true);
    }
    if (adGroupReturnValueHolder.value == null) {
      throw new Exception("NoDataResponse:AdGroupService Add");
    }

    // Display
    for (AdGroupValues adGroupValues : adGroupReturnValueHolder.value.getValues()) {
      if (adGroupValues.isOperationSucceeded()) {
        display(adGroupValues.getAdGroup());
      } else {
        SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(adGroupValues.getError()), true);
      }
    }

    // Response
    return adGroupReturnValueHolder.value.getValues();
  }

  /**
   * Sample Program for AdGroupService SET.
   *
   * @param adGroupOperation AdGroupOperation
   * @return AdGroupValues
   * @throws Exception
   */
  public static List<AdGroupValues> set(AdGroupOperation adGroupOperation) throws Exception {

    // Call API
    System.out.println("############################################");
    System.out.println("AdGroupService::mutate(SET)");
    System.out.println("############################################");

    Holder<AdGroupReturnValue> adGroupReturnValueHolder = new Holder<AdGroupReturnValue>();
    Holder<List<Error>> errorHolder = new Holder<List<Error>>();
    AdGroupServiceInterface adGroupService = SoapUtils.createServiceInterface(AdGroupServiceInterface.class, AdGroupService.class);
    adGroupService.mutate(adGroupOperation, adGroupReturnValueHolder, errorHolder);

    // Error
    if (errorHolder.value != null && errorHolder.value.size() > 0) {
      SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(errorHolder.value), true);
    }
    if (adGroupReturnValueHolder.value == null) {
      throw new Exception("NoDataResponse:AdGroupService Set");
    }

    // Display
    for (AdGroupValues adGroupValues : adGroupReturnValueHolder.value.getValues()) {
      if (adGroupValues.isOperationSucceeded()) {
        display(adGroupValues.getAdGroup());
      } else {
        SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(adGroupValues.getError()), true);
      }
    }

    // Response
    return adGroupReturnValueHolder.value.getValues();
  }

  /**
   * Sample Program for AdGroupService REMOVE.
   *
   * @param operation AdGroupOperation
   * @return AdGroupValues
   * @throws Exception
   */
  public static List<AdGroupValues> remove(AdGroupOperation operation) throws Exception {

    // Call API
    System.out.println("############################################");
    System.out.println("AdGroupService::mutate(REMOVE)");
    System.out.println("############################################");

    Holder<AdGroupReturnValue> adGroupReturnValueHolder = new Holder<AdGroupReturnValue>();
    Holder<List<Error>> errorHolder = new Holder<List<Error>>();
    AdGroupServiceInterface adGroupService = SoapUtils.createServiceInterface(AdGroupServiceInterface.class, AdGroupService.class);
    adGroupService.mutate(operation, adGroupReturnValueHolder, errorHolder);

    // Error
    if (errorHolder.value != null && errorHolder.value.size() > 0) {
      SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(errorHolder.value), true);
    }
    if (adGroupReturnValueHolder.value == null) {
      throw new Exception("NoDataResponse:AdGroupService Remove");
    }

    // Display
    for (AdGroupValues adGroupValues : adGroupReturnValueHolder.value.getValues()) {
      if (adGroupValues.isOperationSucceeded()) {
        display(adGroupValues.getAdGroup());
      } else {
        SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(adGroupValues.getError()), true);
      }
    }

    // Response
    return adGroupReturnValueHolder.value.getValues();
  }

  /**
   * Sample Program for AdGroupService GET.
   *
   * @param selector AdGroupSelector
   * @return AdGroupValues
   * @throws Exception
   */
  public static List<AdGroupValues> get(AdGroupSelector selector) throws Exception {

    // Call API
    System.out.println("############################################");
    System.out.println("AdGroupService::get");
    System.out.println("############################################");

    Holder<AdGroupPage> adGroupPageHolder = new Holder<AdGroupPage>();
    Holder<List<Error>> errorHolder = new Holder<List<Error>>();
    AdGroupServiceInterface adGroupService = SoapUtils.createServiceInterface(AdGroupServiceInterface.class, AdGroupService.class);
    adGroupService.get(selector, adGroupPageHolder, errorHolder);

    // Error
    if (errorHolder.value != null && errorHolder.value.size() > 0) {
      SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(errorHolder.value), true);
    }
    if (adGroupPageHolder.value == null) {
      throw new Exception("NoDataResponse:AdGroupService Get");
    }

    // Display
    for (AdGroupValues adGroupValues : adGroupPageHolder.value.getValues()) {
      if (adGroupValues.isOperationSucceeded()) {
        display(adGroupValues.getAdGroup());
      } else {
        SoapUtils.displayErrors(new AdGroupServiceErrorEntityFactory(adGroupValues.getError()), true);
      }
    }

    // Response
    return adGroupPageHolder.value.getValues();
  }

  /**
   * display AdGroup entity to stdout.
   *
   * @param adGroup AdGroup entity for display.
   */
  public static void display(AdGroup adGroup) {

    System.out.println("accountId = " + adGroup.getAccountId());
    System.out.println("campaignId = " + adGroup.getCampaignId());
    System.out.println("campaignName = " + adGroup.getCampaignName());
    System.out.println("adGroupId = " + adGroup.getAdGroupId());
    System.out.println("adGroupName = " + adGroup.getAdGroupName());
    System.out.println("userStatus = " + adGroup.getUserStatus());

    if (adGroup.getBiddingStrategyConfiguration() != null) {
      System.out.println("biddingStrategyConfiguration/biddingStrategyId = " + adGroup.getBiddingStrategyConfiguration().getBiddingStrategyId());
      System.out.println("biddingStrategyConfiguration/biddingStrategyName = " + adGroup.getBiddingStrategyConfiguration().getBiddingStrategyName());
      System.out.println("biddingStrategyConfiguration/biddingStrategyType = " + adGroup.getBiddingStrategyConfiguration().getBiddingStrategyType());
      System.out.println("biddingStrategyConfiguration/biddingStrategySource = " + adGroup.getBiddingStrategyConfiguration().getBiddingStrategySource());

      if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof EnhancedCpcBiddingScheme) {
        EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = (EnhancedCpcBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
        System.out.println("biddingStrategyConfiguration/biddingScheme(EnhancedCpcBiddingScheme)/biddingStrategyType = " + enhancedCpcBiddingScheme.getBiddingStrategyType());
      } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {
        PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = (PageOnePromotedBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
        System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/biddingStrategyType = " + pageOnePromotedBiddingScheme.getBiddingStrategyType());
        System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidCeiling = " + pageOnePromotedBiddingScheme.getBidCeiling());
        System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidMultiplier = " + pageOnePromotedBiddingScheme.getBidMultiplier());
        System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/bidChangesForRaisesOnly = " + pageOnePromotedBiddingScheme.getBidChangesForRaisesOnly());
        System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenBudgetConstrained = "
            + pageOnePromotedBiddingScheme.getRaiseBidWhenBudgetConstrained());
        System.out.println("biddingStrategyConfiguration/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenLowQualityScore = " + pageOnePromotedBiddingScheme.getRaiseBidWhenLowQualityScore());
      } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetCpaBiddingScheme) {
        TargetCpaBiddingScheme targetCpaBiddingScheme = (TargetCpaBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/biddingStrategyType = " + targetCpaBiddingScheme.getBiddingStrategyType());
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/targetCpa = " + targetCpaBiddingScheme.getTargetCpa());
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/bidCeiling = " + targetCpaBiddingScheme.getBidCeiling());
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetCpaBiddingScheme)/bidFloor = " + targetCpaBiddingScheme.getBidFloor());
      } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetSpendBiddingScheme) {
        TargetSpendBiddingScheme targetSpendBiddingScheme = (TargetSpendBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetSpendBiddingScheme)/biddingStrategyType = " + targetSpendBiddingScheme.getBiddingStrategyType());
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetSpendBiddingScheme)/bidCeiling = " + targetSpendBiddingScheme.getBidCeiling());
      } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof TargetRoasBiddingScheme) {
        TargetRoasBiddingScheme targetRoasBiddingScheme = (TargetRoasBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/biddingStrategyType = " + targetRoasBiddingScheme.getBiddingStrategyType());
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/targetRoas = " + targetRoasBiddingScheme.getTargetRoas());
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/bidCeiling = " + targetRoasBiddingScheme.getBidCeiling());
        System.out.println("biddingStrategyConfiguration/biddingScheme(TargetRoasBiddingScheme)/bidFloor = " + targetRoasBiddingScheme.getBidFloor());
      } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof ManualCpcBiddingScheme) {
        ManualCpcBiddingScheme manualCpcBiddingScheme = (ManualCpcBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
        System.out.println("biddingStrategyConfiguration/biddingScheme(ManualCpcBiddingScheme)/biddingStrategyType = " + manualCpcBiddingScheme.getBiddingStrategyType());
      } else if (adGroup.getBiddingStrategyConfiguration().getBiddingScheme() instanceof BudgetOptimizerBiddingScheme) {
        BudgetOptimizerBiddingScheme budgetOptimizerBiddingScheme = (BudgetOptimizerBiddingScheme) adGroup.getBiddingStrategyConfiguration().getBiddingScheme();
        System.out.println("biddingStrategyConfiguration/biddingScheme(BudgetOptimizerBiddingScheme)/biddingStrategyType = " + budgetOptimizerBiddingScheme.getBiddingStrategyType());
        System.out.println("biddingStrategyConfiguration/biddingScheme(BudgetOptimizerBiddingScheme)/bidCeiling = " + budgetOptimizerBiddingScheme.getBidCeiling());
      }

      if (adGroup.getBiddingStrategyConfiguration().getInitialBid() != null) {
        System.out.println("biddingStrategyConfiguration/initialBid/maxCpc = " + adGroup.getBiddingStrategyConfiguration().getInitialBid().getMaxCpc());
        System.out.println("biddingStrategyConfiguration/initialBid/bidSource = " + adGroup.getBiddingStrategyConfiguration().getInitialBid().getBidSource());
      }

      if (adGroup.getBiddingStrategyConfiguration().getParentBiddingStrategyConfigurations() != null) {
        for (BiddingStrategy biddingStrategy : adGroup.getBiddingStrategyConfiguration().getParentBiddingStrategyConfigurations()) {
          System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingStrategyId = " + biddingStrategy.getBiddingStrategyId());
          System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingStrategyName = " + biddingStrategy.getBiddingStrategyName());
          System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingStrategyType = " + biddingStrategy.getBiddingStrategyType());
          System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingStrategySource = " + biddingStrategy.getBiddingStrategySource());

          if (biddingStrategy.getBiddingScheme() instanceof EnhancedCpcBiddingScheme) {
            EnhancedCpcBiddingScheme enhancedCpcBiddingScheme = (EnhancedCpcBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(EnhancedCpcBiddingScheme)/biddingStrategyType = "
                + enhancedCpcBiddingScheme.getBiddingStrategyType());
          } else if (biddingStrategy.getBiddingScheme() instanceof PageOnePromotedBiddingScheme) {
            PageOnePromotedBiddingScheme pageOnePromotedBiddingScheme = (PageOnePromotedBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/biddingStrategyType = "
                + pageOnePromotedBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/bidCeiling = "
                + pageOnePromotedBiddingScheme.getBidCeiling());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/bidMultiplier = "
                + pageOnePromotedBiddingScheme.getBidMultiplier());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/bidChangesForRaisesOnly = "
                + pageOnePromotedBiddingScheme.getBidChangesForRaisesOnly());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenBudgetConstrained = "
                + pageOnePromotedBiddingScheme.getRaiseBidWhenBudgetConstrained());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(PageOnePromotedBiddingScheme)/raiseBidWhenLowQualityScore = "
                + pageOnePromotedBiddingScheme.getRaiseBidWhenLowQualityScore());
          } else if (biddingStrategy.getBiddingScheme() instanceof TargetCpaBiddingScheme) {
            TargetCpaBiddingScheme targetCpaBiddingScheme = (TargetCpaBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetCpaBiddingScheme)/biddingStrategyType = "
                + targetCpaBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetCpaBiddingScheme)/targetCpa = " + targetCpaBiddingScheme.getTargetCpa());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetCpaBiddingScheme)/bidCeiling = " + targetCpaBiddingScheme.getBidCeiling());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetCpaBiddingScheme)/bidFloor = " + targetCpaBiddingScheme.getBidFloor());
          } else if (biddingStrategy.getBiddingScheme() instanceof TargetSpendBiddingScheme) {
            TargetSpendBiddingScheme targetSpendBiddingScheme = (TargetSpendBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetSpendBiddingScheme)/biddingStrategyType = "
                + targetSpendBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetSpendBiddingScheme)/bidCeiling = " + targetSpendBiddingScheme.getBidCeiling());
          } else if (biddingStrategy.getBiddingScheme() instanceof TargetRoasBiddingScheme) {
            TargetRoasBiddingScheme targetRoasBiddingScheme = (TargetRoasBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetRoasBiddingScheme)/biddingStrategyType = "
                + targetRoasBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetRoasBiddingScheme)/targetRoas = " + targetRoasBiddingScheme.getTargetRoas());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetRoasBiddingScheme)/bidCeiling = " + targetRoasBiddingScheme.getBidCeiling());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(TargetRoasBiddingScheme)/bidFloor = " + targetRoasBiddingScheme.getBidFloor());
          } else if (biddingStrategy.getBiddingScheme() instanceof ManualCpcBiddingScheme) {
            ManualCpcBiddingScheme manualCpcBiddingScheme = (ManualCpcBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(ManualCpcBiddingScheme)/biddingStrategyType = "
                + manualCpcBiddingScheme.getBiddingStrategyType());
          } else if (biddingStrategy.getBiddingScheme() instanceof BudgetOptimizerBiddingScheme) {
            BudgetOptimizerBiddingScheme budgetOptimizerBiddingScheme = (BudgetOptimizerBiddingScheme) biddingStrategy.getBiddingScheme();
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(BudgetOptimizerBiddingScheme)/biddingStrategyType = "
                + budgetOptimizerBiddingScheme.getBiddingStrategyType());
            System.out.println("biddingStrategyConfiguration/parentBiddingStrategyConfigurations/biddingScheme(BudgetOptimizerBiddingScheme)/bidCeiling = "
                + budgetOptimizerBiddingScheme.getBidCeiling());
          }
        }
      }

      if (adGroup.getSettings() != null) {
        System.out.println("settings/criterionType = " + adGroup.getSettings().getCriterionType());
        System.out.println("settings/targetAll = " + ((TargetingSetting)adGroup.getSettings()).getTargetAll());
      }
    }

    System.out.println("---------");
  }

  /**
   * create sample request.
   *
   * @param accountId long
   * @param campaignId long
   * @param appCampaignId long
   * @param biddingStrategyId long
   * @return AdGroupOperation
   */
  public static AdGroupOperation createSampleAddRequest(long accountId, long campaignId, long appCampaignId, long biddingStrategyId) {
    // Set Operation
    AdGroupOperation operation = new AdGroupOperation();
    operation.setOperator(Operator.ADD);
    operation.setAccountId(accountId);

    // Set Bid
    Bid bit = new Bid();
    bit.setMaxCpc((long) 120);

    // Set AutoBidding
    BiddingStrategy autoBiddingStrategy = new BiddingStrategy();
    autoBiddingStrategy.setBiddingStrategyId(biddingStrategyId);
    autoBiddingStrategy.setInitialBid(bit);

    // Set ManualCpc
    BiddingStrategy manualCpcStrategy = new BiddingStrategy();
    manualCpcStrategy.setBiddingStrategyType(BiddingStrategyType.MANUAL_CPC);
    manualCpcStrategy.setInitialBid(bit);

    // Set Settings
    TargetingSetting targetingSetting = new TargetingSetting();
    targetingSetting.setCriterionType(CriterionType.TARGET_LIST);
    targetingSetting.setTargetAll(TargetAll.ACTIVE);

    // Set AutoBidding AdGroup
    AdGroup autoBiddingAdGroup = new AdGroup();
    autoBiddingAdGroup.setAccountId(accountId);
    autoBiddingAdGroup.setCampaignId(campaignId);
    autoBiddingAdGroup.setAdGroupName("SampleAutoBiddingAdGroup_CreateOn_" + SoapUtils.getCurrentTimestamp());
    autoBiddingAdGroup.setUserStatus(UserStatus.ACTIVE);
    autoBiddingAdGroup.setBiddingStrategyConfiguration(autoBiddingStrategy);
    autoBiddingAdGroup.setSettings(targetingSetting);

    // Set ManualCpc AdGroup
    AdGroup manualCpcAdGroup = new AdGroup();
    manualCpcAdGroup.setAccountId(accountId);
    manualCpcAdGroup.setCampaignId(campaignId);
    manualCpcAdGroup.setAdGroupName("SampleManualCpcAdGroup_CreateOn_" + SoapUtils.getCurrentTimestamp());
    manualCpcAdGroup.setUserStatus(UserStatus.ACTIVE);
    manualCpcAdGroup.setBiddingStrategyConfiguration(manualCpcStrategy);

    // Set App AdGroup
    AdGroup appAdGroup = new AdGroup();
    appAdGroup.setAccountId(accountId);
    appAdGroup.setCampaignId(appCampaignId);
    appAdGroup.setAdGroupName("SampleAppAdGroup_CreateOn_" + SoapUtils.getCurrentTimestamp());
    appAdGroup.setUserStatus(UserStatus.ACTIVE);

    operation.getOperand().addAll(Arrays.asList(autoBiddingAdGroup, manualCpcAdGroup, appAdGroup));

    return operation;
  }

  /**
   * create sample request.
   *
   * @param accountId long
   * @param biddingStrategyId long
   * @param adGroupValues AdGroupValues
   * @return AdGroupOperation
   */
  public static AdGroupOperation createSampleSetRequest(long accountId, long biddingStrategyId, List<AdGroupValues> adGroupValues) {
    // Set Operation
    AdGroupOperation operation = new AdGroupOperation();
    operation.setOperator(Operator.SET);
    operation.setAccountId(accountId);

    // Set Operand
    for (AdGroupValues adGroupValue : adGroupValues) {

      // Set Bid
      Bid bit = new Bid();
      bit.setMaxCpc((long) 200);

      // Set AutoBidding
      BiddingStrategy autoBiddingStrategy = new BiddingStrategy();
      autoBiddingStrategy.setBiddingStrategyId(biddingStrategyId);
      autoBiddingStrategy.setInitialBid(bit);

      // Set Settings
      TargetingSetting targetingSetting = new TargetingSetting();
      targetingSetting.setCriterionType(CriterionType.TARGET_LIST);
      targetingSetting.setTargetAll(TargetAll.DEACTIVE);

      // Set AutoBidding AdGroup
      AdGroup adGroup = new AdGroup();
      adGroup.setAccountId(adGroupValue.getAdGroup().getAccountId());
      adGroup.setCampaignId(adGroupValue.getAdGroup().getCampaignId());
      adGroup.setAdGroupId(adGroupValue.getAdGroup().getAdGroupId());
      adGroup.setCampaignName("Sample_UpdateOn_" + adGroupValue.getAdGroup().getAdGroupId() + "_" + SoapUtils.getCurrentTimestamp());
      adGroup.setUserStatus(UserStatus.PAUSED);
      // Change Auto Bidding Strategy
      adGroup.setBiddingStrategyConfiguration(autoBiddingStrategy);

      operation.getOperand().add(adGroup);
    }

    return operation;
  }

  /**
   * create sample request.
   *
   * @param accountId long
   * @param adGroupValues AdGroupValues
   * @return AdGroupOperation
   */
  public static AdGroupOperation createSampleRemoveRequest(long accountId, List<AdGroupValues> adGroupValues) {
    // Set Operation
    AdGroupOperation operation = new AdGroupOperation();
    operation.setOperator(Operator.REMOVE);
    operation.setAccountId(accountId);

    // Set Operand
    for (AdGroupValues adGroupValue : adGroupValues) {

      AdGroup adGroup = new AdGroup();
      adGroup.setAccountId(adGroupValue.getAdGroup().getAccountId());
      adGroup.setCampaignId(adGroupValue.getAdGroup().getCampaignId());
      adGroup.setAdGroupId(adGroupValue.getAdGroup().getAdGroupId());

      operation.getOperand().add(adGroup);
    }

    return operation;
  }

  /**
   * create sample request.
   *
   * @param accountId long
   * @param adGroupValues AdGroupValues
   * @return AdGroupSelector
   */
  public static AdGroupSelector createSampleGetRequest(long accountId, List<AdGroupValues> adGroupValues) {
    // Set Selector
    AdGroupSelector selector = new AdGroupSelector();
    selector.setAccountId(accountId);
    for (AdGroupValues adGroupValue : adGroupValues) {
      selector.getCampaignIds().add(adGroupValue.getAdGroup().getCampaignId());
      selector.getAdGroupIds().add(adGroupValue.getAdGroup().getAdGroupId());
    }
    selector.getUserStatuses().addAll(Arrays.asList(UserStatus.ACTIVE, UserStatus.PAUSED));
    Paging paging = new Paging();
    paging.setStartIndex(1);
    paging.setNumberResults(20);
    selector.setPaging(paging);

    return selector;
  }
}
