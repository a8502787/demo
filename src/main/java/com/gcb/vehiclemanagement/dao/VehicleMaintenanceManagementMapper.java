package com.gcb.vehiclemanagement.dao;

import com.gcb.vehiclemanagement.entity.MaintenanceApplyInfo;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VehicleMaintenanceManagementMapper {

    Integer applyVehicleMaintenance(String applyId, long applicantId, String applicantName, String vehicleId,
                                    long driverId, String driverName, Date createTime, Date startTime, Date endTime,
                                    String destination, String cause, String maintenanceProvider,
                                    String entourage, String contactPhone,
                                    String status, String confirm, String finish);

    List<MaintenanceApplyInfo> getMaintenanceApplyInfoForApplicant(long applicantId);

    Integer getMaintenanceApplyInfoForApplicantCount(long applicantId);

    List<MaintenanceApplyInfo> getMaintenanceApplyInfoForDriver(long driverId, String status);

    Integer getMaintenanceApplyInfoForDriverCount(long driverId, String status);

    List<MaintenanceApplyInfo> getAllMaintenanceApplyInfo();

    Integer getAllMaintenanceApplyInfoCount();

    String getMaintenanceApplyStatus(String applyId);

    Integer passMaintenanceApproval(String applyId, long driverId,
                                    String driverName, String oldStatus, String status);

    Integer refuseMaintenanceApproval(String applyId, String oldStatus, String status);

    Integer confirmMaintenanceApplyInfo(String applyId, String confirm, String finish);

    String getVehicleIdByMaintenanceApplyTable(String applyId);

    Integer updateFinishValue(String applyId, String finish);

    Long getApplicantIdByMaintenanceApplyId(String applyId);

    Long getDriverIdByMaintenanceApplyId(String applyId);

    MaintenanceApplyInfo getMaintenanceApplyInfoByApplyId(String applyId);

    String getMaintenanceApplyConfirm(String applyId);

    Integer updateMaintenanceApplyEndTime(String applyId, Date endTime);
}
