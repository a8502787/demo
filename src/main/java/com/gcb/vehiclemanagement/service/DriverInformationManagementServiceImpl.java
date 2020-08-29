package com.gcb.vehiclemanagement.service;

import com.gcb.vehiclemanagement.dao.DriverInformationManagementMapper;
import com.gcb.vehiclemanagement.dao.VehicleReservationMapper;
import com.gcb.vehiclemanagement.entity.*;
import com.gcb.vehiclemanagement.entity.format.MaintenanceApplyInfoFormat;
import com.gcb.vehiclemanagement.entity.format.UserDeptInfoFormat;
import com.gcb.vehiclemanagement.entity.format.VehicleApplyInfoFormat;
import com.gcb.vehiclemanagement.util.DateToStringConverter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverInformationManagementServiceImpl implements DriverInformationManagementService {

    @Autowired
    private DriverInformationManagementMapper driverInformationManagementMapper;
    @Autowired
    private VehicleReservationMapper vehicleReservationMapper;

    private ResultData resultData;

    @Override
    @Transactional
    public ResultData getAllDriverBaseInfo_new(int pageNum, int pageSize) {
        resultData = new ResultData();
        PageHelper.startPage(pageNum,pageSize);
        List<UserDeptInfo> driverBaseInfoList = driverInformationManagementMapper.getAllDriverBaseInfo_new("1");
        if (driverBaseInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<UserDeptInfoFormat> driverBaseInfoFormatList = new ArrayList<>();
            for (UserDeptInfo info : driverBaseInfoList) {
                driverBaseInfoFormatList.add(new UserDeptInfoFormat(info.getUserId(),info.getName(),info.getDeptName(),info.getLoginName(),
                        DateToStringConverter.getStringDate(info.getDriverBirthday()),DateToStringConverter.getStringDate(info.getStartDrivingDate()),
                        info.getDrivingType(),info.getDriverPhoto(),info.getDriverStatus(),info.getRole(),info.getRelation()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("allCount",driverInformationManagementMapper.getAllDriverBaseInfoCount_new("1"));
            map.put("driverBaseInfoList",driverBaseInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getDriverBaseInfo_new(long driverId) {
        List<UserDeptInfo> driverBaseInfoList = driverInformationManagementMapper.getDriverBaseInfo_new(driverId,"1");
        if (driverBaseInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<UserDeptInfoFormat> driverBaseInfoFormatList = new ArrayList<>();
            for (UserDeptInfo info : driverBaseInfoList) {
                String userName = vehicleReservationMapper.getUserName(info.getUserId());
                driverBaseInfoFormatList.add(new UserDeptInfoFormat(info.getUserId(),userName,info.getDeptName(),info.getLoginName(),
                        DateToStringConverter.getStringDate(info.getDriverBirthday()),DateToStringConverter.getStringDate(info.getStartDrivingDate()),
                        info.getDrivingType(),info.getDriverPhoto(),info.getDriverStatus(),info.getRole(),info.getRelation()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("driverBaseInfoList",driverBaseInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData inquireDriverCondition_new(long driverId) {
        resultData = new ResultData();
        String status = driverInformationManagementMapper.getDriverStatus_new(driverId);
        if (status == null || status.equals("")) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该司机状态信息不存在");
            return resultData;
        }
        resultData.setStatusCode(200);
        resultData.setStatusRes("success");
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        if (status.equals("2")) {
            List<VehicleApplyInfo> vehicleApplyInfoList = driverInformationManagementMapper.getVehicleApplyInfo(driverId,"0");
            if (vehicleApplyInfoList != null && vehicleApplyInfoList.size() > 0) {
                List<VehicleApplyInfoFormat> vehicleApplyInfoFormatList = new ArrayList<>();
                for (VehicleApplyInfo info : vehicleApplyInfoList) {
                    vehicleApplyInfoFormatList.add(new VehicleApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                            info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),info.getDepartmentCode(),
                            info.getApplyDepartment(), info.getVehicleUser(),DateToStringConverter.getStringDate(info.getCreateTime()),
                            DateToStringConverter.getStringDate(info.getStartTime()),
                            info.getEndTime(),info.getDestination(),
                            info.getCause(),info.getEntourage(),info.getPeopleNumber(),
                            info.getContactPhone(),info.getStatus(),info.getConfirm(),info.getFinish(),info.getPicUrl()));
                }
                map.put("mode","0");//0为预约用车，1为维保用车
                map.put("vehicleApplyInfoList",vehicleApplyInfoFormatList);
                resultData.setInfo(map);
                return resultData;
            }

            List<MaintenanceApplyInfo> maintenanceApplyInfoList = driverInformationManagementMapper.getMaintenanceApplyInfo(driverId,"0");
            if (maintenanceApplyInfoList != null && maintenanceApplyInfoList.size() > 0) {
                List<MaintenanceApplyInfoFormat> maintenanceApplyInfoFormatList = new ArrayList<>();
                for (MaintenanceApplyInfo info : maintenanceApplyInfoList) {
                    maintenanceApplyInfoFormatList.add(new MaintenanceApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                            info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),DateToStringConverter.getStringDate(info.getCreateTime()),
                            DateToStringConverter.getStringDate(info.getStartTime()), info.getEndTime(),
                            info.getDestination(),info.getCause(), info.getMaintenanceProvider(),
                            info.getEntourage(),info.getContactPhone(), info.getStatus(),info.getConfirm(),info.getFinish()));
                }
                map.put("mode","1");//0为预约用车，1为维保用车
                map.put("maintenanceApplyInfoList",maintenanceApplyInfoFormatList);
                resultData.setInfo(map);
                return resultData;
            }
        }
        resultData.setInfo(map);
        return resultData;
    }

    @Override
    @Transactional
    public ResultData test() {
        resultData = new ResultData();
        Map<String,Object> map = new HashMap<>();
        Integer id = 1;
        Demo demo = driverInformationManagementMapper.getDemo(id);
        map.put("demo",demo.getTest());
        resultData.setInfo(map);
        return resultData;
    }
}
