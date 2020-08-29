package com.gcb.vehiclemanagement.dao;

import com.gcb.vehiclemanagement.entity.MaintenanceApplyInfo;
import com.gcb.vehiclemanagement.entity.VehicleApplyInfo;
import com.gcb.vehiclemanagement.entity.VehicleFeeInfo;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VehicleInformationStatisticsMapper {

    List<VehicleApplyInfo> getVehicleApplyInfoByCombinationQuery(Date startTime, Date endTime, String vehicleId,
                                                               String driverName, String applyDepartment,
                                                               String cause, String vehicleUser);

    Integer getVehicleApplyInfoByCombinationQueryCount(Date startTime, Date endTime, String vehicleId,
                                                       String driverName, String applyDepartment,
                                                       String cause, String vehicleUser);

    List<MaintenanceApplyInfo> getMaintenanceApplyInfoByCombinationQuery(Date startTime, Date endTime, String vehicleId,
                                                                         String driverName, String cause);

    Integer getMaintenanceApplyInfoByCombinationQueryCount(Date startTime, Date endTime, String vehicleId,
                                                           String driverName, String cause);

    List<VehicleFeeInfo> getVehicleFeeInfoByCombinationQuery(Date startTime, Date endTime, String vehicleId);

    Integer getVehicleFeeInfoByCombinationQueryCount(Date startTime, Date endTime, String vehicleId);
}
