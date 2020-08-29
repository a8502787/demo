package com.gcb.vehiclemanagement.service;

import com.alibaba.fastjson.JSONArray;
import com.gcb.vehiclemanagement.entity.ResultData;

public interface VehicleReservationService {
    ResultData applyVehicle_new(long applicantId, String applicantName,
                            String vehicleUser, String startTime, String endTime, String destination,
                            String cause, String entourage, int peopleNumber, String contactPhone,String picUrl);

    ResultData getVehicleApplyInfo(long userId, int pageNum, int pageSize);

    ResultData passApprovalByIPC(String applyId, long approverId, String suggestion);

    ResultData passApprovalByLogistics(String applyId, String vehicleId, String driverId, String driverName,
                                       long approverId, String suggestion);

    ResultData refuseApproval(String applyId, long approverId, String suggestion);

    ResultData cancelApply(String applyId, long approverId, String suggestion);

    ResultData confirmVehicleApplyInfo(String applyId, long driverId, String startTime, double startKilometers);

    ResultData confirmReturnVehicle(String applyId, String vehicleId, long driverId, String agent, String endTime, double oilFee,
                                    double maintenanceFee, double parkingFee, double forfeit, String paymentTime,
                                    String returnTime, String parkingPlace, double kilometers);

    ResultData getAvailableVehicleBaseInfo(long userId);

    ResultData getCommonData(String dataType);

    ResultData getAvailableDriverBaseInfo(long userId);

    ResultData getApprovalHistory(long userId, String applyId);

    ResultData getUserName(long userId);

    ResultData getUserRole(long userId);

//    ResultData picUpload(MultipartFile file, HttpServletRequest request);

    ResultData getAllPIC();

    ResultData getAllApply();

    ResultData bindAppyAndPIC(String PICLoginName, JSONArray ApplyList);

    ResultData ApplyByPIC(String PICLoginName);
}
