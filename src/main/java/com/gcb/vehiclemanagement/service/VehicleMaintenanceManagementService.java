package com.gcb.vehiclemanagement.service;

import com.gcb.vehiclemanagement.entity.ResultData;

public interface VehicleMaintenanceManagementService {

    ResultData applyVehicleMaintenance(long applicantId, String applicantName, String vehicleId, long driverId,
                                       String driverName, String startTime, String endTime, String destination,
                                       String cause, String maintenanceProvider,
                                       String entourage, String contactPhone);

    ResultData cancelMaintenanceApproval(String applyId, long approverId, String suggestion);

    ResultData getMaintenanceApplyInfo(long userId, int pageNum, int pageSize);

    ResultData passMaintenanceApproval(String applyId, long driverId, String driverName,
                                       long approverId, String suggestion);

    ResultData refuseMaintenanceApproval(String applyId, long approverId, String suggestion);

    ResultData confirmMaintenanceApplyInfo(String applyId, long driverId, String startTime, double startKilometers);

    ResultData confirmReturnMaintenanceVehicle(String applyId, String vehicleId, long driverId, String agent, String endTime, double oilFee,
                                               double maintenanceFee, double parkingFee, double forfeit, String paymentTime,
                                               String returnTime, String parkingPlace, double kilometers);
}
