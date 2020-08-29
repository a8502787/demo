package com.gcb.vehiclemanagement.service;

import com.gcb.vehiclemanagement.entity.ResultData;

public interface DriverInformationManagementService {

    ResultData getAllDriverBaseInfo_new(int pageNum, int pageSize);

    ResultData getDriverBaseInfo_new(long driverId);

    ResultData inquireDriverCondition_new(long driverId);

    ResultData test();
}
