package com.gcb.vehiclemanagement.controller;

import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.service.VehicleMaintenanceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class VehicleMaintenanceManagementController {

    @Autowired
    private VehicleMaintenanceManagementService vehicleMaintenanceManagementService;

    //维保用车信息录入接口
    @PostMapping("/api/applyVehicleMaintenance_new")
    public ResultData applyVehicleMaintenance(@RequestBody Map<String,String> params) {
        return vehicleMaintenanceManagementService.applyVehicleMaintenance(Long.parseLong(params.get("applicantId")),
                params.get("applicantName"),params.get("vehicleId"),Long.parseLong(params.get("driverId")),
                params.get("driverName"),params.get("startTime"),params.get("endTime"),
                params.get("destination"),params.get("cause"),params.get("maintenanceProvider"),
                params.get("entourage"),params.get("contactPhone"));
    }

    //获取维保用车申请信息发送给相关用户接口
    @PostMapping("/api/getMaintenanceApplyInfo_new")
    public ResultData getMaintenanceApplyInfo(@RequestBody Map<String,String> params) {
        return vehicleMaintenanceManagementService.getMaintenanceApplyInfo(Long.parseLong(params.get("userId")),
                Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
    }

    //后勤管理中心审批人通过审批接口
    @PostMapping("/api/passMaintenanceApproval_new")
    public ResultData passMaintenanceApproval(@RequestBody Map<String,String> params) {
        return vehicleMaintenanceManagementService.passMaintenanceApproval(params.get("applyId"),
                Long.parseLong(params.get("driverId")),params.get("driverName"),Long.parseLong(params.get("approverId")),
                params.get("suggestion"));
    }

    //后勤管理中心审批人拒绝审批接口
    @PostMapping("/api/refuseMaintenanceApproval_new")
    public ResultData refuseMaintenanceApproval(@RequestBody Map<String,String> params) {
        return vehicleMaintenanceManagementService.refuseMaintenanceApproval(params.get("applyId"),Long.parseLong(params.get("approverId")),
                params.get("suggestion"));
    }

    //司机或者后勤管理中心审批人撤销审批接口
    @PostMapping("/api/cancelMaintenanceApproval_new")
    public ResultData cancelMaintenanceApproval(@RequestBody Map<String,String> params) {
        return vehicleMaintenanceManagementService.cancelMaintenanceApproval(params.get("applyId"),Long.parseLong(params.get("approverId")),
                params.get("suggestion"));
    }

    //司机确认维保用车信息接口
    @PostMapping("/api/confirmMaintenanceApplyInfo_new")
    public ResultData confirmMaintenanceApplyInfo(@RequestBody Map<String,String> params) {
//        return vehicleMaintenanceManagementService.confirmMaintenanceApplyInfo(params.get("applyId"),Long.parseLong(params.get("driverId")),
//                params.get("startTime"),Double.parseDouble(params.get("startKilometers")));
        return vehicleMaintenanceManagementService.confirmMaintenanceApplyInfo(params.get("applyId"),Long.parseLong(params.get("driverId")),
                params.get("startTime"),0);
    }

    //维保用车归还确认接口
    @PostMapping("/api/confirmReturnMaintenanceVehicle_new")
    public ResultData confirmReturnMaintenanceVehicle(@RequestBody Map<String,String> params) {
//        return vehicleMaintenanceManagementService.confirmReturnMaintenanceVehicle(params.get("applyId"),params.get("vehicleId"),
//                Long.parseLong(params.get("driverId")),params.get("agent"),params.get("endTime"),
//                Double.parseDouble(params.get("oilFee")),Double.parseDouble(params.get("maintenanceFee")),
//                Double.parseDouble(params.get("parkingFee")),Double.parseDouble(params.get("forfeit")),
//                params.get("paymentTime"),params.get("returnTime"),params.get("parkingPlace"),
//                Double.parseDouble(params.get("kilometers")));
//    }
        return vehicleMaintenanceManagementService.confirmReturnMaintenanceVehicle(params.get("applyId"),params.get("vehicleId"),
                Long.parseLong(params.get("driverId")),params.get("agent"),params.get("endTime"),
                Double.parseDouble(params.get("oilFee")),Double.parseDouble(params.get("maintenanceFee")),
                0,0,
                params.get("paymentTime"),params.get("returnTime"),params.get("parkingPlace"),
                Double.parseDouble(params.get("kilometers")));
    }
}
