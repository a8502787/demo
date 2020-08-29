package com.gcb.vehiclemanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.service.VehicleReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VehicleReservationController {

    @Autowired
    private VehicleReservationService vehicleReservationService;

    //预约用车信息录入接口
    @PostMapping("/api/applyVehicle_new")
    public ResultData applyVehicle_new(@RequestBody Map<String,String> params) {
        return vehicleReservationService.applyVehicle_new(Long.parseLong(params.get("applicantId")),params.get("applicantName"),
                params.get("vehicleUser"),params.get("startTime"),params.get("endTime"),
                params.get("destination"),params.get("cause"),params.get("entourage"),
                Integer.parseInt(params.get("peopleNumber")),params.get("contactPhone"),params.get("picUrl"));
    }

    //获取预约用车信息发送给相关用户接口
    @PostMapping("/api/getVehicleApplyInfo_new")
    public ResultData getVehicleApplyInfo(@RequestBody Map<String,String> params) {
        return vehicleReservationService.getVehicleApplyInfo(Long.parseLong(params.get("userId")),
                Integer.parseInt(params.get("pageNum")), Integer.parseInt(params.get("pageSize")));
    }

    //负责人通过审批接口
    @PostMapping("/api/passApprovalByIPC_new")
    public ResultData passApprovalByIPC(@RequestBody Map<String,String> params) {
        return vehicleReservationService.passApprovalByIPC(params.get("applyId"),
                Long.parseLong(params.get("approverId")), params.get("suggestion"));
    }

    //后勤管理中心审批人通过审批接口
    @PostMapping("/api/passApprovalByLogistics_new")
    public ResultData passApprovalByLogistics(@RequestBody Map<String,String> params) {
        return vehicleReservationService.passApprovalByLogistics(params.get("applyId"),params.get("vehicleId"),
                params.get("driverId"),params.get("driverName"),Long.parseLong(params.get("approverId")),
                params.get("suggestion"));
    }

    //后勤管理中心人员拒绝审批接口
    @PostMapping("/api/refuseApproval_new")
    public ResultData refuseApproval(@RequestBody Map<String,String> params) {
        return vehicleReservationService.refuseApproval(params.get("applyId"),Long.parseLong(params.get("approverId")),
                params.get("suggestion"));
    }

    //司机确认预约用车信息接口
    @PostMapping("/api/confirmVehicleApplyInfo_new")
    public ResultData confirmVehicleApplyInfo(@RequestBody Map<String,String> params) {
//        return vehicleReservationService.confirmVehicleApplyInfo(params.get("applyId"),Long.parseLong(params.get("driverId")),
//                params.get("startTime"),Double.parseDouble(params.get("startKilometers")));
        return vehicleReservationService.confirmVehicleApplyInfo(params.get("applyId"),Long.parseLong(params.get("driverId")),
                params.get("startTime"),0);
    }

    //用车归还确认
    @PostMapping("/api/confirmReturnVehicle_new")
    public ResultData confirmReturnVehicle(@RequestBody Map<String,String> params) {
//        return vehicleReservationService.confirmReturnVehicle(params.get("applyId"),params.get("vehicleId"),
//                Long.parseLong(params.get("driverId")),params.get("agent"),params.get("endTime"),
//                Double.parseDouble(params.get("oilFee")),Double.parseDouble(params.get("maintenanceFee")),
//                Double.parseDouble(params.get("parkingFee")),Double.parseDouble(params.get("forfeit")),
//                params.get("paymentTime"),params.get("returnTime"),params.get("parkingPlace"),
//                Double.parseDouble(params.get("kilometers")));
        return vehicleReservationService.confirmReturnVehicle(params.get("applyId"),params.get("vehicleId"),
                Long.parseLong(params.get("driverId")),params.get("agent"),params.get("endTime"),
                0,0,
                Double.parseDouble(params.get("parkingFee")),0,
                params.get("paymentTime"),params.get("returnTime"),params.get("parkingPlace"),
                Double.parseDouble(params.get("kilometers")));
    }

    //获取可用车辆信息发送给后勤审批人接口
    @PostMapping("/api/getAvailableVehicleBaseInfo_new")
    public ResultData getAvailableVehicleBaseInfo(@RequestBody Map<String,String> params) {
        return vehicleReservationService.getAvailableVehicleBaseInfo(Long.parseLong(params.get("userId")));
    }

    //获取常用地点和事由接口
    @PostMapping("/api/getCommonData_new")
    public ResultData getCommonData(@RequestBody Map<String,String> params) {
        return vehicleReservationService.getCommonData(params.get("dataType"));
    }

    //获取可用司机信息发送给后勤审批人接口
    @PostMapping("/api/getAvailableDriverBaseInfo_new")
    public ResultData getAvailableDriverBaseInfo(@RequestBody Map<String,String> params) {
        return vehicleReservationService.getAvailableDriverBaseInfo(Long.parseLong(params.get("userId")));
    }

    //获取审批历史记录接口
    @PostMapping("/api/getApprovalHistory_new")
    public ResultData getApprovalHistory(@RequestBody Map<String,String> params) {
        return vehicleReservationService.getApprovalHistory(Long.parseLong(params.get("userId")),params.get("applyId"));
    }

    //获取用户名
    @PostMapping("/api/getUserName_new")
    public ResultData getUserName(@RequestBody Map<String,String> params) {
        return vehicleReservationService.getUserName(Long.parseLong(params.get("userId")));
    }

    //获取用户角色
    @PostMapping("/api/getUserRole_new")
    public ResultData getUserRole(@RequestBody Map<String,String> params) {
        return vehicleReservationService.getUserRole(Long.parseLong(params.get("userId")));
    }

    //申请部门人员撤销申请接口
    @PostMapping("/api/cancelApply_new")
    public ResultData cancelApply(@RequestBody Map<String,String> params) {
        return vehicleReservationService.cancelApply(params.get("applyId"),Long.parseLong(params.get("approverId")),
                params.get("suggestion"));
    }

    //上传图片
//    @PostMapping("/api/picUpload_new")
//    public ResultData picUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
//        return vehicleReservationService.picUpload(file, request);
//    }

    //查询所有部门负责人
    @GetMapping("/api/getAllPIC_new")
    public ResultData getAllPIC() {
        return vehicleReservationService.getAllPIC();
    }

    //查询所有未绑定负责人的申请人
    @GetMapping("/api/getAllApply_new")
    public ResultData getAllApply() {
        return vehicleReservationService.getAllApply();
    }

    //绑定申请人和负责人(申请人可多个,负责人一个)
    @PostMapping("/api/bindAppyAndPIC_new")
    public ResultData bindAppyAndPIC(@RequestBody JSONObject params) {
        return vehicleReservationService.bindAppyAndPIC(String.valueOf(params.get("PICLoginName")), params.getJSONArray("ApplyLoginName"));
    }

    @PostMapping("/api/ApplyByPIC_new")
    public ResultData ApplyByPIC(@RequestBody Map<String,String> params) {
        return vehicleReservationService.ApplyByPIC(params.get("PICLoginName"));
    }
}
