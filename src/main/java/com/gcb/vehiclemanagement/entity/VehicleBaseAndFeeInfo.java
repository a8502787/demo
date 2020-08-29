package com.gcb.vehiclemanagement.entity;

import com.gcb.vehiclemanagement.entity.format.VehicleBaseInfoFormat;
import com.gcb.vehiclemanagement.entity.format.VehicleFeeInfoFormat;
import java.util.List;

public class VehicleBaseAndFeeInfo {
    private VehicleBaseInfoFormat vehicleBaseInfo;
    private List<VehicleFeeInfoFormat> vehicleFeeInfoList;

    public VehicleBaseAndFeeInfo(VehicleBaseInfoFormat vehicleBaseInfo, List<VehicleFeeInfoFormat> vehicleFeeInfoList) {
        this.vehicleBaseInfo = vehicleBaseInfo;
        this.vehicleFeeInfoList = vehicleFeeInfoList;
    }

    public VehicleBaseInfoFormat getVehicleBaseInfo() {
        return vehicleBaseInfo;
    }

    public void setVehicleBaseInfo(VehicleBaseInfoFormat vehicleBaseInfo) {
        this.vehicleBaseInfo = vehicleBaseInfo;
    }

    public List<VehicleFeeInfoFormat> getVehicleFeeInfoList() {
        return vehicleFeeInfoList;
    }

    public void setVehicleFeeInfoList(List<VehicleFeeInfoFormat> vehicleFeeInfoList) {
        this.vehicleFeeInfoList = vehicleFeeInfoList;
    }
}
