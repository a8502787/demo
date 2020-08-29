package com.gcb.vehiclemanagement.controller;

import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.service.DriverInformationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class DriverInformationManagementController {

    @Autowired
    private DriverInformationManagementService driverInformationManagementService;

    //获取所有司机基本信息接口
    @PostMapping("/api/getAllDriverBaseInfo_new")
    public ResultData getAllDriverBaseInfo_new(@RequestBody Map<String,String> params) {
        return driverInformationManagementService.getAllDriverBaseInfo_new(Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
    }

    //获取司机基本信息接口
    @PostMapping("/api/getDriverBaseInfo_new")
    public ResultData getDriverBaseInfo_new(@RequestBody Map<String,String> params) {
        return driverInformationManagementService.getDriverBaseInfo_new(Long.parseLong(params.get("driverId")));
    }


    //司机状况查询接口
    @PostMapping("/api/inquireDriverCondition_new")
    public ResultData inquireDriverCondition_new(@RequestBody Map<String,String> params) {
        return driverInformationManagementService.inquireDriverCondition_new(Long.parseLong(params.get("driverId")));
    }

    @PostMapping("/api/test")
    public ResultData test() {
        return driverInformationManagementService.test();
    }
}
