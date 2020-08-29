package com.gcb.vehiclemanagement.service;

import com.alibaba.fastjson.JSONArray;
import com.gcb.vehiclemanagement.entity.ResultData;

public interface EntryInformationService {


    ResultData insertUserInfo_new(JSONArray userInfoList);

    ResultData deleteUserInfo_new(long userId);


    ResultData getAllUserInfo_new(int pageNum, int pageSize);

    ResultData updateDriverInfo_new(long userId, String driverBirthday, String startDrivingDate,
                                String drivingType, String driverPhoto);

    ResultData insertVehicleInfo(String vehicleId, int seatNumber, String vehicleType, String vehicleColor,
                                 String purchaseTime, String insuranceStart, String vehiclePhoto, String status,
                                 String returnTime, String parkingPlace, double kilometers);

    ResultData deleteVehicleInfo(String vehicleId);

    ResultData getAllVehicleInfo(long userId, int pageNum, int pageSize);

    ResultData updateVehicleInfo(String vehicleId, int seatNumber, String vehicleType, String vehicleColor,
                                 String purchaseTime, String insuranceStart, String vehiclePhoto);

    ResultData insertCommonData(String dataType, String data);

    ResultData deleteCommonData(String dataType, String data);

    ResultData loginNameToUserIdAndRole_new(String loginName);
}
