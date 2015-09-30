package adSample;

import error.impl.CampaignTargetServiceErrorEntityFactory;
import jp.yahooapis.ss.V5.CampaignTargetService.*;
import jp.yahooapis.ss.V5.CampaignTargetService.Error;
import util.SoapUtils;

import javax.xml.ws.Holder;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Program for CampaignTargetService.
 * Copyright (C) 2012 Yahoo Japan Corporation. All Rights Reserved.
 */
public class CampaignTargetServiceSample {

    /**
     * main method for CampaignTargetServiceSample
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            long accountId = SoapUtils.getAccountId();
            long campaignId = SoapUtils.getCampaignId();

            // CampaignTargetService ADD
            List<CampaignTargetValues> campaignTargetValues = add(accountId, campaignId);

            // CampaignTargetService GET
            get(accountId, campaignTargetValues);

            // CampaignTargetService SET
            set(accountId, campaignTargetValues);

            // CampaignTargetService REMOVE
            remove(accountId, campaignTargetValues);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sample Program for CampaignTargetService ADD.
     *
     * @param accountId  Account ID
     * @param campaignId Campaign ID
     * @return CampaignTargetValues
     * @throws Exception
     */
    public static List<CampaignTargetValues> add(long accountId, long campaignId) throws Exception {

        // Set ScheduleTarget
        ScheduleTarget scheduleTarget = new ScheduleTarget();
        scheduleTarget.setTargetType(TargetType.SCHEDULE);
        scheduleTarget.setDayOfWeek(DayOfWeek.MONDAY);
        scheduleTarget.setStartHour(10);
        scheduleTarget.setStartMinute(MinuteOfHour.ZERO);
        scheduleTarget.setEndHour(11);
        scheduleTarget.setEndMinute(MinuteOfHour.THIRTY);

        CampaignTarget campaignScheduleTarget = new CampaignTarget();
        campaignScheduleTarget.setAccountId(accountId);
        campaignScheduleTarget.setCampaignId(campaignId);
        campaignScheduleTarget.setTarget(scheduleTarget);
        campaignScheduleTarget.setBidMultiplier(1.0);

        // Set LocationTarget
        LocationTarget locationTarget = new LocationTarget();
        locationTarget.setTargetId("JP-13-0048");
        locationTarget.setTargetType(TargetType.LOCATION);
        locationTarget.setExcludedType(ExcludedType.INCLUDED);

        CampaignTarget campaignLocationTarget = new CampaignTarget();
        campaignLocationTarget.setAccountId(accountId);
        campaignLocationTarget.setCampaignId(campaignId);
        campaignLocationTarget.setTarget(locationTarget);
        campaignLocationTarget.setBidMultiplier(0.95);

        // Set NetworkTarget
        NetworkTarget networkTarget = new NetworkTarget();
        networkTarget.setTargetType(TargetType.NETWORK);
        networkTarget.setNetworkCoverageType(NetworkCoverageType.YAHOO_SEARCH);

        CampaignTarget campaignNetworkTarget = new CampaignTarget();
        campaignNetworkTarget.setAccountId(accountId);
        campaignNetworkTarget.setCampaignId(campaignId);
        campaignNetworkTarget.setTarget(networkTarget);

        // Set Operation
        CampaignTargetOperation campaignTargetOperation = new CampaignTargetOperation();
        campaignTargetOperation.setAccountId(accountId);
        campaignTargetOperation.setOperator(Operator.ADD);
        campaignTargetOperation.getOperand().addAll(Arrays.asList(
                campaignScheduleTarget,
                campaignLocationTarget,
                campaignNetworkTarget
        ));

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignTargetService::mutate(ADD)");
        System.out.println("############################################");

        Holder<CampaignTargetReturnValue> campaignTargetReturnValueHolder = new Holder<CampaignTargetReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignTargetServiceInterface campaignTargetService = SoapUtils.createServiceInterface(CampaignTargetServiceInterface.class, CampaignTargetService.class);
        campaignTargetService.mutate(campaignTargetOperation, campaignTargetReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignTargetServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignTargetReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignTargetService Add");
        }

        // Display
        for (CampaignTargetValues campaignTargetValues : campaignTargetReturnValueHolder.value.getValues()) {
            if (campaignTargetValues.isOperationSucceeded()) {
                display(campaignTargetValues.getCampaignTarget());
            } else {
                SoapUtils.displayErrors(new CampaignTargetServiceErrorEntityFactory(campaignTargetValues.getError()), true);
            }
        }

        // Response
        return campaignTargetReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for CampaignTargetService SET.
     *
     * @param accountId                Account ID
     * @param CampaignTargetValuesList CampaignTargetValues entity for set.
     * @return CampaignTargetValues
     * @throws Exception
     */
    public static List<CampaignTargetValues> set(long accountId, List<CampaignTargetValues> CampaignTargetValuesList) throws Exception {

        // Set Operation
        CampaignTargetOperation campaignTargetOperation = new CampaignTargetOperation();
        campaignTargetOperation.setOperator(Operator.SET);
        campaignTargetOperation.setAccountId(accountId);

        // Set Operand
        for (CampaignTargetValues campaignTargetValues : CampaignTargetValuesList) {

            CampaignTarget campaignTarget = new CampaignTarget();

            campaignTarget.setAccountId(campaignTargetValues.getCampaignTarget().getAccountId());
            campaignTarget.setCampaignId(campaignTargetValues.getCampaignTarget().getCampaignId());

            // Set Target
            if (TargetType.SCHEDULE.equals(campaignTargetValues.getCampaignTarget().getTarget().getTargetType())) {

                // Set ScheduleTarget
                ScheduleTarget scheduleTarget = new ScheduleTarget();
                scheduleTarget.setTargetId(campaignTargetValues.getCampaignTarget().getTarget().getTargetId());
                scheduleTarget.setTargetType(TargetType.SCHEDULE);

                campaignTarget.setTarget(scheduleTarget);
                campaignTarget.setBidMultiplier(0.5);

            } else if (TargetType.LOCATION.equals(campaignTargetValues.getCampaignTarget().getTarget().getTargetType())) {

                // Set LocationTarget
                LocationTarget locationTarget = new LocationTarget();
                locationTarget.setTargetId(campaignTargetValues.getCampaignTarget().getTarget().getTargetId());
                locationTarget.setTargetType(TargetType.LOCATION);

                campaignTarget.setTarget(locationTarget);
                campaignTarget.setBidMultiplier(0.5);

            } else if (TargetType.PLATFORM.equals(campaignTargetValues.getCampaignTarget().getTarget().getTargetType())) {

                // Set PlatformTarget
                PlatformTarget platformTarget = new PlatformTarget();
                platformTarget.setTargetId(campaignTargetValues.getCampaignTarget().getTarget().getTargetId());
                platformTarget.setTargetType(TargetType.PLATFORM);

                campaignTarget.setTarget(platformTarget);

            } else if (TargetType.NETWORK.equals(campaignTargetValues.getCampaignTarget().getTarget().getTargetType())) {
                // Can not update NetworkTarget
                campaignTarget = null;
            }

            if (campaignTarget != null) {
                campaignTargetOperation.getOperand().add(campaignTarget);
            }
        }

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignTargetService::mutate(SET)");
        System.out.println("############################################");

        Holder<CampaignTargetReturnValue> campaignTargetReturnValueHolder = new Holder<CampaignTargetReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignTargetServiceInterface campaignTargetService = SoapUtils.createServiceInterface(CampaignTargetServiceInterface.class, CampaignTargetService.class);
        campaignTargetService.mutate(campaignTargetOperation, campaignTargetReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignTargetServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignTargetReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignTargetService Set");
        }

        // Display
        for (CampaignTargetValues campaignTargetValues : campaignTargetReturnValueHolder.value.getValues()) {
            if (campaignTargetValues.isOperationSucceeded()) {
                display(campaignTargetValues.getCampaignTarget());
            } else {
                SoapUtils.displayErrors(new CampaignTargetServiceErrorEntityFactory(campaignTargetValues.getError()), true);
            }
        }

        // Response
        return campaignTargetReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for CampaignTargetService REMOVE.
     *
     * @param accountId                Account ID
     * @param campaignTargetValuesList CampaignTargetValues entity for remove.
     * @return CampaignTargetValues
     * @throws Exception
     */
    public static List<CampaignTargetValues> remove(long accountId, List<CampaignTargetValues> campaignTargetValuesList) throws Exception {

        // Set Operation
        CampaignTargetOperation campaignTargetOperation = new CampaignTargetOperation();
        campaignTargetOperation.setOperator(Operator.REMOVE);
        campaignTargetOperation.setAccountId(accountId);

        // Set Operand
        for (CampaignTargetValues campaignTargetValues : campaignTargetValuesList) {

            CampaignTarget campaignTarget = new CampaignTarget();

            campaignTarget.setAccountId(campaignTargetValues.getCampaignTarget().getAccountId());
            campaignTarget.setCampaignId(campaignTargetValues.getCampaignTarget().getCampaignId());

            // Set Target
            if (TargetType.SCHEDULE.equals(campaignTargetValues.getCampaignTarget().getTarget().getTargetType())) {

                // Set ScheduleTarget
                ScheduleTarget scheduleTarget = new ScheduleTarget();
                scheduleTarget.setTargetId(campaignTargetValues.getCampaignTarget().getTarget().getTargetId());
                scheduleTarget.setTargetType(TargetType.SCHEDULE);

                campaignTarget.setTarget(scheduleTarget);

            } else if (TargetType.LOCATION.equals(campaignTargetValues.getCampaignTarget().getTarget().getTargetType())) {

                // Set LocationTarget
                LocationTarget locationTarget = new LocationTarget();
                locationTarget.setTargetId(campaignTargetValues.getCampaignTarget().getTarget().getTargetId());
                locationTarget.setTargetType(TargetType.LOCATION);

                campaignTarget.setTarget(locationTarget);

            } else if (TargetType.PLATFORM.equals(campaignTargetValues.getCampaignTarget().getTarget().getTargetType())) {

                // Set PlatformTarget
                PlatformTarget platformTarget = new PlatformTarget();
                platformTarget.setTargetId(campaignTargetValues.getCampaignTarget().getTarget().getTargetId());
                platformTarget.setTargetType(TargetType.PLATFORM);

                campaignTarget.setTarget(platformTarget);

            } else if (TargetType.NETWORK.equals(campaignTargetValues.getCampaignTarget().getTarget().getTargetType())) {

                // Set NetworkTarget
                NetworkTarget networkTarget = new NetworkTarget();
                networkTarget.setTargetId(campaignTargetValues.getCampaignTarget().getTarget().getTargetId());
                networkTarget.setTargetType(TargetType.NETWORK);

                campaignTarget.setTarget(networkTarget);
            }

            campaignTargetOperation.getOperand().add(campaignTarget);
        }

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignTargetService::mutate(REMOVE)");
        System.out.println("############################################");

        Holder<CampaignTargetReturnValue> campaignTargetReturnValueHolder = new Holder<CampaignTargetReturnValue>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignTargetServiceInterface campaignTargetService = SoapUtils.createServiceInterface(CampaignTargetServiceInterface.class, CampaignTargetService.class);
        campaignTargetService.mutate(campaignTargetOperation, campaignTargetReturnValueHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignTargetServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignTargetReturnValueHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignTargetService Remove");
        }

        // Display
        for (CampaignTargetValues campaignTargetValues : campaignTargetReturnValueHolder.value.getValues()) {
            if (campaignTargetValues.isOperationSucceeded()) {
                display(campaignTargetValues.getCampaignTarget());
            } else {
                SoapUtils.displayErrors(new CampaignTargetServiceErrorEntityFactory(campaignTargetValues.getError()), true);
            }
        }

        // Response
        return campaignTargetReturnValueHolder.value.getValues();
    }

    /**
     * Sample Program for CampaignTargetService GET.
     *
     * @param accountId                Account ID
     * @param campaignTargetValuesList CampaignTargetValues entity for get.
     * @return CampaignTargetValues
     * @throws Exception
     */
    public static List get(long accountId, List<CampaignTargetValues> campaignTargetValuesList) throws Exception {

        // Set Selector
        CampaignTargetSelector campaignTargetSelector = new CampaignTargetSelector();
        campaignTargetSelector.setAccountId(accountId);
        for (CampaignTargetValues campaignTargetValues : campaignTargetValuesList) {
            campaignTargetSelector.getCampaignIds().add((campaignTargetValues.getCampaignTarget().getCampaignId()));
        }
        Paging paging = new Paging();
        paging.setStartIndex(1);
        paging.setNumberResults(20);
        campaignTargetSelector.setPaging(paging);

        // Call API
        System.out.println("############################################");
        System.out.println("CampaignTargetService::get");
        System.out.println("############################################");

        Holder<CampaignTargetPage> campaignTargetPageHolder = new Holder<CampaignTargetPage>();
        Holder<List<Error>> errorHolder = new Holder<List<Error>>();
        CampaignTargetServiceInterface campaignTargetService = SoapUtils.createServiceInterface(CampaignTargetServiceInterface.class, CampaignTargetService.class);
        campaignTargetService.get(campaignTargetSelector, campaignTargetPageHolder, errorHolder);

        // Error
        if (errorHolder.value != null && errorHolder.value.size() > 0) {
            SoapUtils.displayErrors(new CampaignTargetServiceErrorEntityFactory(errorHolder.value), true);
        }
        if (campaignTargetPageHolder.value == null) {
            throw new Exception("NoDataResponse:CampaignTargetService Get");
        }

        // Display
        for (CampaignTargetValues campaignTargetValues : campaignTargetPageHolder.value.getValues()) {
            if (campaignTargetValues.isOperationSucceeded()) {
                display(campaignTargetValues.getCampaignTarget());
            } else {
                SoapUtils.displayErrors(new CampaignTargetServiceErrorEntityFactory(campaignTargetValues.getError()), true);
            }
        }

        // Response
        return campaignTargetPageHolder.value.getValues();
    }

    /**
     * display CampaignTarget entity to stdout.
     *
     * @param campaignTarget CampaignTarget entity for display.
     */
    public static void display(CampaignTarget campaignTarget) {

        System.out.println("accountId = " + campaignTarget.getAccountId());
        System.out.println("campaignId = " + campaignTarget.getCampaignId());
        if (campaignTarget.getTarget() != null) {
            System.out.println("campaignTarget---------");
            System.out.println("campaignTarget/accountId = " + campaignTarget.getAccountId());
            System.out.println("campaignTarget/campaignId = " + campaignTarget.getCampaignId());
            System.out.println("campaignTarget/campaignName = " + campaignTarget.getCampaignName());
            System.out.println("campaignTarget/bidMultiplier = " + campaignTarget.getBidMultiplier());
            System.out.println("campaignTarget/target/targetType = " + campaignTarget.getTarget().getTargetType());
            System.out.println("campaignTarget/target/targetId = " + campaignTarget.getTarget().getTargetId());
            if (campaignTarget.getTarget() instanceof ScheduleTarget) {
                ScheduleTarget scheduleTarget = (ScheduleTarget) campaignTarget.getTarget();
                System.out.println("target(ScheduleTarget)---------");
                System.out.println("target(ScheduleTarget)/dayOfWeek = " + scheduleTarget.getDayOfWeek());
                System.out.println("target(ScheduleTarget)/startHour = " + scheduleTarget.getStartHour());
                System.out.println("target(ScheduleTarget)/startMinute = " + scheduleTarget.getStartMinute());
                System.out.println("target(ScheduleTarget)/endHour = " + scheduleTarget.getEndHour());
                System.out.println("target(ScheduleTarget)/endMinute = " + scheduleTarget.getEndMinute());
            } else if (campaignTarget.getTarget() instanceof LocationTarget) {
                LocationTarget locationTarget = (LocationTarget) campaignTarget.getTarget();
                System.out.println("target(LocationTarget)---------");
                System.out.println("target(LocationTarget)/excludedType = " + locationTarget.getExcludedType());
            } else if (campaignTarget.getTarget() instanceof NetworkTarget) {
                NetworkTarget networkTarget = (NetworkTarget) campaignTarget.getTarget();
                System.out.println("target(NetworkTarget)---------");
                System.out.println("target(NetworkTarget)/networkCoverageType = " + networkTarget.getNetworkCoverageType());
            } else if (campaignTarget.getTarget() instanceof PlatformTarget) {
                PlatformTarget platformTarget = (PlatformTarget) campaignTarget.getTarget();
                System.out.println("target(PlatformTarget)---------");
                System.out.println("target(PlatformTarget)/platformType = " + platformTarget.getPlatformType());
            }
        }
        System.out.println("---------");
    }
}
