package com.gcb.vehiclemanagement.service;

import com.gcb.vehiclemanagement.entity.ResultData;

public interface VehicleInformationStatisticsService {

    ResultData getVehicleUseInfoByCombinationQuery(String startTime, String endTime,
                                                   String vehicleId, String driverName,
                                                   String applyDepartment, String cause,
                                                   String vehicleUser, int pageNum, int pageSize);

    ResultData getVehicleFeeInfoByCombinationQuery(String startTime, String endTime,
                                                   String vehicleId, int pageNum, int pageSize);
}
