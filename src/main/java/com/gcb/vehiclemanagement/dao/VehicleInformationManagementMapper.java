package com.gcb.vehiclemanagement.dao;

import com.gcb.vehiclemanagement.entity.VehicleBaseInfo;
import com.gcb.vehiclemanagement.entity.VehicleFeeInfo;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface VehicleInformationManagementMapper {

    Integer inputFeeInfo(String applyId, String vehicleId, Date startTime, double startKilometers);

    Integer inputFeeInfoWhenReturnVehicle(String applyId,String vehicleId, String agent, Date startTime,Date endTime, double oilFee, double maintenanceFee,
                                          double parkingFee, double forfeit, Date paymentTime, String parkingPlace, double startKilometers, double endKilometers);

    List<VehicleFeeInfo> getVehicleFeeInfoByApplyId(String applyId);

    List<VehicleBaseInfo> getAllVehicleBaseInfo();

    Integer getAllVehicleBaseInfoCount();

    List<VehicleFeeInfo> getVehicleFeeInfo(String vehicleId);

    List<VehicleBaseInfo> getVehicleBaseInfo(String vehicleId);
}
