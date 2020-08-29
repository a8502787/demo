package com.gcb.vehiclemanagement.dao;

import com.gcb.vehiclemanagement.entity.*;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VehicleReservationMapper {
    List<UserDeptInfo> getUserInfoByRole(String Role);

    String getRelationByUserId(long userId);

    Integer applyVehicle_new(String applyId, long applicantId, String applicantName, String departmentCode, String departmentName,
                         String vehicleUser,Date createTime, Date startTime, Date endTime, String destination,
                         String cause, String entourage, int peopleNumber, String contactPhone, String status, String confirm,
                         String finish,String picUrl);

    List<VehicleApplyInfo> getVehicleApplyInfoByApplicantId(long applicantId);

    Integer getVehicleApplyInfoByApplicantIdCount(long applicantId);

    List<VehicleApplyInfo> getVehicleApplyInfoForDriver(long driverId);

    Integer getVehicleApplyInfoForDriverCount(long driverId);

    List<VehicleApplyInfo> getAllVehicleApplyInfo();

    Integer getAllVehicleApplyInfoCount();

    String getVehicleApplyStatus(String applyId);

    String getVehicleApplyConfirm(String applyId);

    UserDeptInfo getUserInfoById(long userId);

    Integer passApprovalByDepartmentLeader(String applyId, String oldStatus, String status);

    Integer insertApprovalHistory(String applyId, long approverId, String suggestion,
                                  Date approvalTime, String type, String status);

    String getUserRole_new(long userId);

    Integer passApprovalByLogistics(String applyId, String vehicleId, Long driverId,
                                    String driverName, String oldStatus, String status);

    Integer refuseApproval(String applyId, String oldStatus, String status);

    Integer confirmVehicleApplyInfo(String applyId, String confirm, String finish);

    Integer updateDriverStatus(long driverId, String status);

    String getVehicleIdByVehicleApplyTable(String applyId);

    VehicleApplyInfo getVehicleApplyInfoByApplyId(String applyId);

    Integer updateVehicleStatus(String vehicleId, String status);

    Integer updateFinishValue(String applyId, String finish);

    Integer confirmReturnVehicle(String vehicleId, Date returnTime, String parkingPlace,
                                 Double kilometers);

    List<VehicleBaseInfo> getAvailableVehicleBaseInfo(String status);

    List<CommonData> getCommonData(String dataType);

    List<UserDeptInfo> getAvailableDriverBaseInfo(String status);

    List<ApprovalHistoryInfo> getApprovalHistory(String applyId);

    String getUserName(long userId);

    String getLoginName(long userId);

    List<Long> getUserIdByRole(String role);

    Long getApplicantIdByVehicleApplyId(String applyId);

    Long getDriverIdByVehicleApplyId(String applyId);

    Long getUserIdByLoginName(String loginName);

    Integer updateApplyBindPIC(String PICLoginName, List<String> ApplyLoginNameList);

    List<UserDeptInfo> getApplyByPIC(String PICLoginName);

    Integer updateApplyEndTime(String applyId, Date endTime);
}
