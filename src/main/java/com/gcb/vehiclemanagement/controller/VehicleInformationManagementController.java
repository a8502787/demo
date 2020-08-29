package com.gcb.vehiclemanagement.controller;

import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.service.VehicleInformationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class VehicleInformationManagementController {

    @Autowired
    private VehicleInformationManagementService vehicleInformationManagementService;

    //查询车辆费用接口
    @PostMapping("/api/getVehicleFeeInfo_new")
    public ResultData getVehicleFeeInfo(@RequestBody Map<String,String> params) {
        return vehicleInformationManagementService.getVehicleFeeInfo(Long.parseLong(params.get("userId")),
                params.get("applyId"));
    }

    //获取所有车辆基本信息和费用信息接口
    @PostMapping("/api/getAllVehicleBaseAndFeeInfo_new")
    public ResultData getAllVehicleBaseAndFeeInfo(@RequestBody Map<String,String> params) {
        return vehicleInformationManagementService.getAllVehicleBaseAndFeeInfo(Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
    }

    //获取某车辆基本信息和费用信息接口
    @PostMapping("/api/getVehicleBaseAndFeeInfo_new")
    public ResultData getVehicleBaseAndFeeInfo(@RequestBody Map<String,String> params) {
        return vehicleInformationManagementService.getVehicleBaseAndFeeInfo(params.get("vehicleId"));
    }

    //车况查询接口
    @PostMapping("/api/inquireVehicleLatestCondition_new")
    public ResultData inquireVehicleLatestCondition(@RequestBody Map<String,String> params) {
        return vehicleInformationManagementService.inquireVehicleLatestCondition(Long.parseLong(params.get("userId")),
                params.get("vehicleId"));
    }
}
