package com.gcb.vehiclemanagement.dao;

import com.gcb.vehiclemanagement.entity.UserDeptInfo;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface EntryInformationMapper {

    Integer insertUserInfo_new(String loginName, String name, String deptCode, String deptId, String deptName,
                           Date driverBirthday, Date startDrivingDate, String drivingType,
                           String driverPhoto, String driverStatus, String role, String relation);

    Long getUserId(String loginName);

    Integer deleteUserInfo_new(long userId);


    UserDeptInfo getUserInfoByLoginName(String loginName);

    List<UserDeptInfo> getAllUserInfo_new();

    Integer getAllUserInfoCount_new();

    Integer updateDriverInfo_new(long userId, Date driverBirthday, Date startDrivingDate, String drivingType, String driverPhoto);

    Integer insertVehicleInfo(String vehicleId, int seatNumber, String vehicleType, String vehicleColor, Date purchaseTime,
                              Date insuranceStart, String vehiclePhoto, String status, Date returnTime, String parkingPlace,
                              double kilometers);

    Integer deleteVehicleInfo(String vehicleId);

    Integer updateVehicleInfo(String vehicleId, int seatNumber, String vehicleType, String vehicleColor, Date purchaseTime,
                              Date insuranceStart, String vehiclePhoto);

    Integer insertCommonData(String dataType, String data);

    Integer deleteCommonData(String dataType, String data);



}
