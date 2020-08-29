package com.gcb.vehiclemanagement.controller;

import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.service.VehicleInformationStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class VehicleInformationStatisticsController {

    @Autowired
    private VehicleInformationStatisticsService vehicleInformationStatisticsService;

    //多条件组合查询出车明细接口
    @PostMapping("/api/getVehicleUseInfoByCombinationQuery_new")
    public ResultData getVehicleUseInfoByCombinationQuery(@RequestBody Map<String,String> params) {
        return vehicleInformationStatisticsService.getVehicleUseInfoByCombinationQuery(
                params.get("startTime"),params.get("endTime"),params.get("vehicleId"),params.get("driverName"),
                params.get("applyDepartment"),params.get("cause"),params.get("vehicleUser"),
                Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
    }

    //多条件组合查询出车明费用细接口
    @PostMapping("/api/getVehicleFeeInfoByCombinationQuery_new")
    public ResultData getVehicleFeeInfoByCombinationQuery(@RequestBody Map<String,String> params) {
        return vehicleInformationStatisticsService.getVehicleFeeInfoByCombinationQuery(
                params.get("startTime"),params.get("endTime"),params.get("vehicleId"),
                Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
    }
}
