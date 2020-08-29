package com.gcb.vehiclemanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.service.EntryInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class EntryInformationController {

    @Autowired
    private EntryInformationService entryInformationService;

    //用户信息录入接口
    @PostMapping("/api/insertUserInfo_new")
    public ResultData insertUserInfo_new(@RequestBody JSONObject params) {
        return entryInformationService.insertUserInfo_new(params.getJSONArray("userInfoList"));
    }

    //用户信息删除接口
    @PostMapping("/api/deleteUserInfo_new")
    public ResultData deleteUserInfo_new(@RequestBody Map<String,String> params) {
        return entryInformationService.deleteUserInfo_new(Long.parseLong(params.get("userId")));
    }

    //所有用户信息查询接口
    @PostMapping("/api/getAllUserInfo_new")
    public ResultData getAllUserInfo_new(@RequestBody Map<String,String> params) {
        return entryInformationService.getAllUserInfo_new(Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
    }

    //司机信息编辑接口
    @PostMapping("/api/updateDriverInfo_new")
    public ResultData updateDriverInfo_new(@RequestBody Map<String,String> params) {
        return entryInformationService.updateDriverInfo_new(Long.parseLong(params.get("userId")),
                params.get("driverBirthday"),params.get("startDrivingDate"),params.get("drivingType"),
                params.get("driverPhoto"));
    }

    //车辆信息录入接口
    @PostMapping("/api/insertVehicleInfo_new")
    public ResultData insertVehicleInfo(@RequestBody Map<String,String> params) {
        return entryInformationService.insertVehicleInfo(params.get("vehicleId"),
                Integer.parseInt(params.get("seatNumber")),params.get("vehicleType"),params.get("vehicleColor"),params.get("purchaseTime"),
                params.get("insuranceStart"),params.get("vehiclePhoto"), params.get("status"),params.get("returnTime"),
                params.get("parkingPlace"),Double.parseDouble(params.get("kilometers")));
    }

    //车辆信息删除接口
    @PostMapping("/api/deleteVehicleInfo_new")
    public ResultData deleteVehicleInfo(@RequestBody Map<String,String> params) {
        return entryInformationService.deleteVehicleInfo(params.get("vehicleId"));
    }

    //获取所有车辆基本信息接口
    @PostMapping("/api/getAllVehicleInfo_new")
    public ResultData getAllVehicleInfo(@RequestBody Map<String,String> params) {
        return entryInformationService.getAllVehicleInfo(Long.parseLong(params.get("userId")),
                Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
    }

    //获取所有车辆基本信息接口
    @PostMapping("/api/updateVehicleInfo_new")
    public ResultData updateVehicleInfo(@RequestBody Map<String,String> params) {
        return entryInformationService.updateVehicleInfo(params.get("vehicleId"),
                Integer.parseInt(params.get("seatNumber")),params.get("vehicleType"),params.get("vehicleColor"),
                params.get("purchaseTime"),params.get("insuranceStart"),params.get("vehiclePhoto"));
    }

    //常用信息录入接口
    @PostMapping("/api/insertCommonData_new")
    public ResultData insertCommonData(@RequestBody Map<String,String> params) {
        return entryInformationService.insertCommonData(params.get("dataType"), params.get("data"));
    }

    //常用信息删除接口
    @PostMapping("/api/deleteCommonData_new")
    public ResultData deleteCommonData_new(@RequestBody Map<String,String> params) {
        return entryInformationService.deleteCommonData(params.get("dataType"), params.get("data"));
    }

    //传入loginName,返回对应的userid
    @PostMapping("/api/loginNameToUserIdAndRole_new")
    public ResultData loginNameToUserIdAndRole_new(@RequestBody Map<String,String> params) {
        return entryInformationService.loginNameToUserIdAndRole_new(params.get("loginName"));
    }
}
