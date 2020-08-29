package com.gcb.vehiclemanagement.service;

import com.gcb.vehiclemanagement.entity.ResultData;

public interface VehicleInformationManagementService {

    ResultData getVehicleFeeInfo(long userId, String applyId);

    ResultData getAllVehicleBaseAndFeeInfo(int pageNum, int pageSize);

    ResultData getVehicleBaseAndFeeInfo(String vehicleId);

    ResultData inquireVehicleLatestCondition(long userId, String vehicleId);
}
