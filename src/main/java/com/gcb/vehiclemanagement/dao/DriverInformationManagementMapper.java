package com.gcb.vehiclemanagement.dao;

import com.gcb.vehiclemanagement.entity.Demo;
import com.gcb.vehiclemanagement.entity.MaintenanceApplyInfo;
import com.gcb.vehiclemanagement.entity.UserDeptInfo;
import com.gcb.vehiclemanagement.entity.VehicleApplyInfo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DriverInformationManagementMapper {

    List<UserDeptInfo> getAllDriverBaseInfo_new(String role);

    Integer getAllDriverBaseInfoCount_new(String role);

    List<UserDeptInfo> getDriverBaseInfo_new(long driverId, String role);

    String getDriverStatus_new(long driverId);

    List<VehicleApplyInfo> getVehicleApplyInfo(long driverId, String finish);

    List<MaintenanceApplyInfo> getMaintenanceApplyInfo(long driverId, String finish);

    Demo getDemo(Integer id);
}
