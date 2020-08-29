package com.gcb.vehiclemanagement.service;

import com.gcb.vehiclemanagement.dao.VehicleInformationManagementMapper;
import com.gcb.vehiclemanagement.dao.VehicleMaintenanceManagementMapper;
import com.gcb.vehiclemanagement.dao.VehicleReservationMapper;
import com.gcb.vehiclemanagement.entity.*;
import com.gcb.vehiclemanagement.entity.format.MaintenanceApplyInfoFormat;
import com.gcb.vehiclemanagement.util.DateToStringConverter;
//import com.gcb.vehiclemanagement.util.PushNotificationUtil;
import com.gcb.vehiclemanagement.util.RandomUtil;
import com.gcb.vehiclemanagement.util.StringToDateConverter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VehicleMaintenanceManagementServiceImpl implements VehicleMaintenanceManagementService {

    @Autowired
    private VehicleMaintenanceManagementMapper vehicleMaintenanceManagementMapper;
    @Autowired
    private VehicleReservationMapper vehicleReservationMapper;
    @Autowired
    private VehicleInformationManagementMapper vehicleInformationManagementMapper;
//    @Autowired
//    private PushNotificationUtil pushNotificationUtil;
    private ResultData resultData;

    @Override
    @Transactional
    public ResultData applyVehicleMaintenance(long applicantId, String applicantName, String vehicleId, long driverId,
                                              String driverName, String startTime, String endTime, String destination,
                                              String cause, String maintenanceProvider,
                                              String entourage, String contactPhone) {
        resultData = new ResultData();
        startTime = startTime+":00";
//        endTime = endTime+":00";
        String role = vehicleReservationMapper.getUserRole_new(applicantId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        switch (role) {
            case "1":
            case "3":
            case "4":
            case "5":
                flag = true;
                break;
        }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有申请权限");
            return resultData;
        }
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        String applyId = RandomUtil.getRandom();
        Integer i = vehicleMaintenanceManagementMapper.applyVehicleMaintenance(applyId,applicantId,applicantName,
                vehicleId,driverId,driverName,converter.convert(currentDate),converter.convert(startTime),null,
                destination,cause,maintenanceProvider,entourage,contactPhone,"0","0","9");
        if (i >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //发起推送给后勤一级审批人
            List<Long> userIdList = vehicleReservationMapper.getUserIdByRole("3");
            List<String> nameList = new ArrayList<>();
            if (userIdList == null || userIdList.size() <= 0 ) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("信息推送失败");
                return resultData;
            }
            for (long id : userIdList) {
                nameList.add(vehicleReservationMapper.getLoginName(id));
            }
            MaintenanceApplyInfo maintenanceApplyInfo = vehicleMaintenanceManagementMapper.getMaintenanceApplyInfoByApplyId(applyId);
            if (maintenanceApplyInfo == null) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("信息推送失败");
                return resultData;
            }
//            String description = "申请单号："+applyId+
//                    "<br />申请人："+maintenanceApplyInfo.getApplicantName()+"<br />维保时间："+timeToString(maintenanceApplyInfo.getStartTime())+
//                    "<br />返回时间："+timeToString(maintenanceApplyInfo.getEndTime())+"<br />维保地点："+maintenanceApplyInfo.getDestination()+
//                    "<br />维保商："+maintenanceApplyInfo.getMaintenanceProvider()+"<br />联系电话："+maintenanceApplyInfo.getContactPhone();
//            pushNotificationUtil.push(nameList,"您有新的维保用车申请待审批",description,true);
            String description = "申请单号："+applyId+
                    "<br />申请人："+maintenanceApplyInfo.getApplicantName()+"<br />车牌号："+maintenanceApplyInfo.getVehicleId()+
                    "<br />司机："+maintenanceApplyInfo.getDriverName()+"<br />出行时间："+timeToString(maintenanceApplyInfo.getStartTime())+
                    "<br />维保地点："+maintenanceApplyInfo.getDestination()+
                    "<br />维保商："+maintenanceApplyInfo.getMaintenanceProvider()+"<br />联系电话："+maintenanceApplyInfo.getContactPhone()+
                    "<br />陪同人员："+maintenanceApplyInfo.getEntourage();
//            pushNotificationUtil.push(nameList,"您有新的维保用车申请待审批",description,true);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }
    @Override
    @Transactional
    public ResultData cancelMaintenanceApproval(String applyId, long approverId, String suggestion) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        //查询申请单
        MaintenanceApplyInfo maintenanceApplyInfo = vehicleMaintenanceManagementMapper.getMaintenanceApplyInfoByApplyId(applyId);
        //查询申请单状态
        String status = maintenanceApplyInfo.getStatus();
        //查询申请单司机是否确认
        String confirm = maintenanceApplyInfo.getConfirm();
        //查询申请单的申请人
        long applicantId = maintenanceApplyInfo.getApplicantId();
        //指派车辆车牌
        String vehicleId = maintenanceApplyInfo.getVehicleId();
        //指派司机id
        long driverId = maintenanceApplyInfo.getDriverId();
        //查询撤销人角色
        String role = vehicleReservationMapper.getUserRole_new(approverId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        if (!role.equals("1")&&!role.equals("3")&&!role.equals("4")&&!role.equals("5")) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("撤销申请失败,该用户无权撤销!");
            return resultData;
        }
        System.out.println("approverId:"+approverId);
        System.out.println("applicantId:"+applicantId);
        System.out.println(approverId!=applicantId);
        if (approverId!=applicantId){
            resultData.setStatusCode(400);
            resultData.setStatusRes("撤销申请失败,该用户不是申请人!");
            return resultData;
        }
//        if (confirm == "1"){
//            resultData.setStatusCode(400);
//            resultData.setStatusRes("撤销申请失败,申请单司机已确认!");
//            return resultData;
//        }
        //撤销
        Integer i = vehicleMaintenanceManagementMapper.refuseMaintenanceApproval(applyId,status,"5");
        if (vehicleId != null && !vehicleId.equals("")){
            Integer j = vehicleReservationMapper.updateVehicleStatus(vehicleId,"1");
        }
        if (driverId !=0){
            Integer k = vehicleReservationMapper.updateDriverStatus(maintenanceApplyInfo.getDriverId(),"3");
        }
        Integer l = vehicleMaintenanceManagementMapper.updateFinishValue(applyId,"9");
        Integer m = vehicleReservationMapper.insertApprovalHistory(applyId,approverId,suggestion,
                converter.convert(currentDate),"1","5");
        if (i >= 1 && l >= 1 && m >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //流程完成，将信息推送给审批人和秘书
            List<String> nameList = new ArrayList<>();
            List<ApprovalHistoryInfo> AHList = vehicleReservationMapper.getApprovalHistory(applyId);
            List<Long> ApprovalidList = new ArrayList<Long>();
            AHList.forEach(AHinfo -> ApprovalidList.add(AHinfo.getApproverId()));
            System.out.println(ApprovalidList);
            ApprovalidList.forEach(AHId -> nameList.add(vehicleReservationMapper.getLoginName(AHId)));
            String description = "撤销原因："+suggestion;
//            pushNotificationUtil.push(nameList,"您审批的维保用车申请已被撤销",description,true);
//            if (!status.equals("0")) {
//                List<UserDeptInfo> list = vehicleReservationMapper.getUserInfoByRole("6");
//                if (list == null || list.size() <= 0) {
//                    resultData.setStatusCode(400);
//                    resultData.setStatusRes("信息推送失败");
//                    return resultData;
//                }
//                List<String> loginNameList = new ArrayList<>();
//                for (UserDeptInfo userinfo : list){
//                    loginNameList.add(userinfo.getLoginName());
//                }
//                nameList.clear();
//                nameList.addAll(loginNameList);// 消息接收者的登录名（工号）
//                List<VehicleApplyInfo> vehicleApplyInfos = vehicleReservationMapper.getVehicleApplyInfoByApplyId(applyId);
//                if (vehicleApplyInfos == null || vehicleApplyInfos.size() <= 0) {
//                    resultData.setStatusCode(400);
//                    resultData.setStatusRes("信息推送失败");
//                    return resultData;
//                }
//                description = "申请单号："+applyId+"<br />申请人："+
//                        vehicleApplyInfo.getApplicantName()+"<br />申请部门："+vehicleApplyInfo.getApplyDepartment()+
//                        "<br />用车人："+vehicleApplyInfo.getVehicleUser()+"<br />陪同人员："+vehicleApplyInfo.getEntourage()+
//                        "<br />出行时间："+timeToString(vehicleApplyInfo.getStartTime())+"<br />返回时间："+timeToString(vehicleApplyInfo.getEndTime())+
//                        "<br />目的地："+vehicleApplyInfo.getDestination()+"<br />事由："+vehicleApplyInfo.getCause()+
//                        "<br />联系电话："+vehicleApplyInfo.getContactPhone()+"<br />撤销原因："+suggestion;
//                pushNotificationUtil.push(nameList,"用车申请已被撤销",description,false);
//            }
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }
    @Override
    @Transactional
    public ResultData getMaintenanceApplyInfo(long userId, int pageNum, int pageSize) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        Map<String,Object> map = new HashMap<>();
        if (role.equals("1")) {
            flag = true;
            PageHelper.startPage(pageNum,pageSize);
            List<MaintenanceApplyInfo> maintenanceApplyInfoListForDriver = vehicleMaintenanceManagementMapper.getMaintenanceApplyInfoForDriver(userId,"3");

            if (maintenanceApplyInfoListForDriver != null) {
                resultData.setStatusCode(200);
                resultData.setStatusRes("success");
                List<MaintenanceApplyInfoFormat> maintenanceApplyInfoFormatListForDriver = new ArrayList<>();
                for (MaintenanceApplyInfo info : maintenanceApplyInfoListForDriver) {
                    maintenanceApplyInfoFormatListForDriver.add(new MaintenanceApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                            info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),DateToStringConverter.getStringDate(info.getCreateTime()),
                            DateToStringConverter.getStringDate(info.getStartTime()), info.getEndTime(),
                            info.getDestination(),info.getCause(), info.getMaintenanceProvider(),
                            info.getEntourage(),info.getContactPhone(), info.getStatus(),info.getConfirm(),info.getFinish()));
                }
                map.put("allCountOfDriver",vehicleMaintenanceManagementMapper
                        .getMaintenanceApplyInfoForDriverCount(userId,"3"));
                map.put("maintenanceApplyInfoListForDriver",maintenanceApplyInfoFormatListForDriver);

            } else {
                resultData.setStatusCode(400);
                resultData.setStatusRes("fail");
                return resultData;
            }
        }
        if (role.equals("3") || role.equals("4") || role.equals("5")) {
            flag = true;
            PageHelper.startPage(pageNum,pageSize);
            List<MaintenanceApplyInfo> maintenanceApplyInfoList = vehicleMaintenanceManagementMapper.getAllMaintenanceApplyInfo();
            if (maintenanceApplyInfoList != null) {
                resultData.setStatusCode(200);
                resultData.setStatusRes("success");
                List<MaintenanceApplyInfoFormat> maintenanceApplyInfoFormatList = new ArrayList<>();
                for (MaintenanceApplyInfo info : maintenanceApplyInfoList) {
                    maintenanceApplyInfoFormatList.add(new MaintenanceApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                            info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),DateToStringConverter.getStringDate(info.getCreateTime()),
                            DateToStringConverter.getStringDate(info.getStartTime()), info.getEndTime(),
                            info.getDestination(),info.getCause(), info.getMaintenanceProvider(),
                            info.getEntourage(),info.getContactPhone(), info.getStatus(),info.getConfirm(),info.getFinish()));
                }
                map.put("allCountForLogistics",vehicleMaintenanceManagementMapper.getAllMaintenanceApplyInfoCount());
                map.put("maintenanceApplyInfoList",maintenanceApplyInfoFormatList);
            } else {
                resultData.setStatusCode(400);
                resultData.setStatusRes("fail");
                return resultData;
            }
        }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有查询权限");
            return resultData;
        }
        resultData.setInfo(map);
        return resultData;
    }

    @Override
    @Transactional
    public ResultData passMaintenanceApproval(String applyId, long driverId, String driverName,
                                              long approverId, String suggestion) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        String status = vehicleMaintenanceManagementMapper.getMaintenanceApplyStatus(applyId);
        String role = vehicleReservationMapper.getUserRole_new(approverId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        if (role.equals("3")) {
            flag = true;
            if (!status.equals("0")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("通过审批失败");
                return resultData;
            }
        }

        if (role.equals("4")) {
            flag = true;
            if (!status.equals("1")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("通过审批失败");
                return resultData;
            }
        }
        if (role.equals("5")) {
            flag = true;
            if (!status.equals("2")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("通过审批失败");
                return resultData;
            }
        }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有审批权限");
            return resultData;
        }
        Integer i = vehicleMaintenanceManagementMapper.passMaintenanceApproval(applyId,driverId,driverName,
                status,String.valueOf(Integer.parseInt(status)+1));
        Integer j = vehicleReservationMapper.insertApprovalHistory(applyId,approverId,suggestion,
                converter.convert(currentDate),"1","1");
        if (i >= 1 && j >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //发起推送给后勤二、三级审批人
            List<Long> userIdList = null;
            List<String> nameList = null;
            String description = null;
            MaintenanceApplyInfo maintenanceApplyInfo = vehicleMaintenanceManagementMapper.getMaintenanceApplyInfoByApplyId(applyId);
            if (maintenanceApplyInfo == null) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("信息推送失败");
                return resultData;
            }
            description = "申请单号："+applyId+
                    "<br />申请人："+maintenanceApplyInfo.getApplicantName()+"<br />车牌号："+maintenanceApplyInfo.getVehicleId()+
                    "<br />司机："+maintenanceApplyInfo.getDriverName()+"<br />出行时间："+timeToString(maintenanceApplyInfo.getStartTime())+
                    "<br />维保地点："+maintenanceApplyInfo.getDestination()+
                    "<br />维保商："+maintenanceApplyInfo.getMaintenanceProvider()+"<br />联系电话："+maintenanceApplyInfo.getContactPhone()+
                    "<br />陪同人员："+maintenanceApplyInfo.getEntourage();
            switch (status) {
                case "0": {
                    nameList = new ArrayList<>();
                    userIdList = vehicleReservationMapper.getUserIdByRole("4");
                    if (userIdList == null || userIdList.size() <= 0 ) {
                        resultData.setStatusCode(400);
                        resultData.setStatusRes("信息推送失败");
                        return resultData;
                    }
                    for (long id : userIdList) {
                        nameList.add(vehicleReservationMapper.getLoginName(id));
                    }
//                    pushNotificationUtil.push(nameList,"您有新的维保用车申请待审批",description,true);
                }
                break;
                case "1": {
                    nameList = new ArrayList<>();
                    userIdList = vehicleReservationMapper.getUserIdByRole("5");
                    if (userIdList == null || userIdList.size() <= 0 ) {
                        resultData.setStatusCode(400);
                        resultData.setStatusRes("信息推送失败");
                        return resultData;
                    }
                    for (long id : userIdList) {
                        nameList.add(vehicleReservationMapper.getLoginName(id));
                    }
//                    pushNotificationUtil.push(nameList,"您有新的维保用车申请待审批",description,true);
                }
                break;
                case "2": {
                    //流程完成，将信息推送给申请人
                    nameList = new ArrayList<>();
                    nameList.add(vehicleReservationMapper.getLoginName(vehicleMaintenanceManagementMapper.getApplicantIdByMaintenanceApplyId(applyId)));
//                    pushNotificationUtil.push(nameList,"您的维保用车申请已通过",description,true);
                    //申请已通过，将信息推送给司机
                    nameList.clear();
                    nameList.add(vehicleReservationMapper.getLoginName(vehicleMaintenanceManagementMapper.getDriverIdByMaintenanceApplyId(applyId)));
                    description = description+"<br />注意：请尽快联系申请人，并在当天出车前录入出车前信息，谢谢！";
//                    pushNotificationUtil.push(nameList,"您有新的出车任务，请留意",description,true);
                }
                break;
                default: {
                    resultData.setStatusCode(400);
                    resultData.setStatusRes("信息推送失败");
                    return resultData;
                }
            }
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData refuseMaintenanceApproval(String applyId, long approverId, String suggestion) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        String status = vehicleMaintenanceManagementMapper.getMaintenanceApplyStatus(applyId);
        String role = vehicleReservationMapper.getUserRole_new(approverId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        if (role.equals("3")) {
            flag = true;
            if (!status.equals("0")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("通过审批失败");
                return resultData;
            }
        }

        if (role.equals("4")) {
            flag = true;
            if (!status.equals("1")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("通过审批失败");
                return resultData;
            }
        }
        if (role.equals("5")) {
            flag = true;
            if (!status.equals("2")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("通过审批失败");
                return resultData;
            }
        }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有审批权限");
            return resultData;
        }
        Integer i = vehicleMaintenanceManagementMapper.refuseMaintenanceApproval(applyId,status,"9");
        Integer j = vehicleReservationMapper.insertApprovalHistory(applyId,approverId,suggestion,
                converter.convert(currentDate),"1","9");
        if (i >= 1 && j >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //流程完成，将信息推送给申请人
            List<String> nameList = new ArrayList<>();
            nameList.add(vehicleReservationMapper.getLoginName(vehicleMaintenanceManagementMapper.getApplicantIdByMaintenanceApplyId(applyId)));
            String description = "拒绝原因："+suggestion;
//            pushNotificationUtil.push(nameList,"您的维保用车申请已被拒绝",description,true);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData confirmMaintenanceApplyInfo(String applyId, long driverId, String startTime, double startKilometers) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        String role = vehicleReservationMapper.getUserRole_new(driverId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        if (role.equals("1")) {
            flag = true;
        }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有司机角色");
            return resultData;
        }
        Integer i = vehicleMaintenanceManagementMapper.confirmMaintenanceApplyInfo(applyId,"1","0");
        Integer j = vehicleReservationMapper.updateDriverStatus(driverId, "2");
        String vehicleId = vehicleMaintenanceManagementMapper.getVehicleIdByMaintenanceApplyTable(applyId);
        if (vehicleId == null) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该表单不存在");
            return resultData;
        }
        Integer k = vehicleReservationMapper.updateVehicleStatus(vehicleId,"2");
        Integer l = vehicleReservationMapper.insertApprovalHistory(applyId,driverId,"司机确认",
                converter.convert(currentDate),"0","1");
//        Integer l = vehicleInformationManagementMapper.inputFeeInfo(applyId,vehicleId,converter.convert(startTime),
//                startKilometers);
        if (i >= 1 && j >= 1 && k >= 1 && l >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData confirmReturnMaintenanceVehicle(String applyId, String vehicleId, long driverId, String agent, String endTime, double oilFee,
                                                      double maintenanceFee, double parkingFee, double forfeit, String paymentTime,
                                                      String returnTime, String parkingPlace, double endKilometers) {
        resultData = new ResultData();
        if (paymentTime.length()>0){
            paymentTime = paymentTime+":00";
        }
        if (endTime.length()>0) {
            endTime = endTime + ":00";
        }else{
            endTime = "";
        }
        returnTime = returnTime+":00";

        MaintenanceApplyInfo maintenanceApplyInfo = vehicleMaintenanceManagementMapper.getMaintenanceApplyInfoByApplyId(applyId);
        if (maintenanceApplyInfo == null) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        String role = vehicleReservationMapper.getUserRole_new(driverId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        if (role.equals("1")) {
            flag = true;
        }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有司机角色");
            return resultData;
        }
        StringToDateConverter converter = new StringToDateConverter();
        //修改申请表用车归还状态
        Integer i = vehicleMaintenanceManagementMapper.updateFinishValue(applyId,"1");
        //修改申请表,更新返回时间
        Integer n = vehicleMaintenanceManagementMapper.updateMaintenanceApplyEndTime(applyId,converter.convert(returnTime));
        //修改车辆表的车辆状态
        Integer j = vehicleReservationMapper.updateVehicleStatus(vehicleId,"1");
        //修改用户表的司机状态
        Integer k = vehicleReservationMapper.updateDriverStatus(driverId,"3");
        //修改车辆表,确认归还
        Integer l = vehicleReservationMapper.confirmReturnVehicle(vehicleId,converter.convert(returnTime),
                parkingPlace,endKilometers);
        //插入费用信息
        Integer m = vehicleInformationManagementMapper.inputFeeInfoWhenReturnVehicle(applyId,vehicleId,agent,maintenanceApplyInfo.getStartTime(),
                converter.convert(returnTime),oilFee,maintenanceFee,parkingFee,forfeit,null,parkingPlace,0,endKilometers);
        if (i >= 1 && j >= 1 && k >= 1 && l >= 1 && m >= 1 && n >=1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //用车归还完成，将信息推送给后勤中心
            List<Long> userIdList = new ArrayList<>();
            userIdList.addAll(vehicleReservationMapper.getUserIdByRole("3"));
            userIdList.addAll(vehicleReservationMapper.getUserIdByRole("4"));
            userIdList.addAll(vehicleReservationMapper.getUserIdByRole("5"));
            if (userIdList.size() <= 0) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("信息推送失败");
                return resultData;
            }
            List<String> nameList = new ArrayList<>();
            for (long id : userIdList) {
                nameList.add(vehicleReservationMapper.getLoginName(id));
            }

//            String description = "您好！单号为"+applyId+"的维保使用车辆已归还！<br />申请单号："+applyId+
//                    "<br />申请人："+maintenanceApplyInfo.getApplicantName()+"<br />车牌号："+maintenanceApplyInfo.getVehicleId()+
//                    "<br />司机："+maintenanceApplyInfo.getDriverName()+"<br />维保时间："+timeToString(maintenanceApplyInfo.getStartTime())+
//                    "<br />返回时间："+timeToString(maintenanceApplyInfo.getEndTime())+"<br />维保地点："+maintenanceApplyInfo.getDestination()+
//                    "<br />维保商："+maintenanceApplyInfo.getMaintenanceProvider()+"<br />联系电话："+maintenanceApplyInfo.getContactPhone();
            String description = "您好！单号为"+applyId+"的维保使用车辆已归还！<br />申请单号："+applyId+
                    "<br />申请人："+maintenanceApplyInfo.getApplicantName()+"<br />车牌号："+maintenanceApplyInfo.getVehicleId()+
                    "<br />司机："+maintenanceApplyInfo.getDriverName()+"<br />出行时间："+timeToString(maintenanceApplyInfo.getStartTime())+
                    "<br />归还时间："+returnTime+ "<br />维保地点："+maintenanceApplyInfo.getDestination()+
                    "<br />维保商："+maintenanceApplyInfo.getMaintenanceProvider()+"<br />联系电话："+maintenanceApplyInfo.getContactPhone()+
                    "<br />陪同人员："+maintenanceApplyInfo.getEntourage();
//            pushNotificationUtil.push(nameList,"出行任务已完成，车辆已归还",description,true);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }
    public String timeToString(Timestamp timestamp){
        String timeString = null;
        if (timestamp != null){
            timeString = timestamp.toString().substring(0,timestamp.toString().indexOf("."));
        }else {
            timeString = "";
        }

        return timeString;
    }
}
