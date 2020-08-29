package com.gcb.vehiclemanagement.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gcb.vehiclemanagement.dao.EntryInformationMapper;
import com.gcb.vehiclemanagement.dao.VehicleInformationManagementMapper;
import com.gcb.vehiclemanagement.dao.VehicleReservationMapper;
import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.entity.UserDeptInfo;
import com.gcb.vehiclemanagement.entity.VehicleBaseInfo;
import com.gcb.vehiclemanagement.entity.format.UserDeptInfoFormat;
import com.gcb.vehiclemanagement.entity.format.VehicleBaseInfoFormat;
import com.gcb.vehiclemanagement.util.DateToStringConverter;
import com.gcb.vehiclemanagement.util.StringToDateConverter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntryInformationServiceImpl implements EntryInformationService {

    @Autowired
    private EntryInformationMapper entryInformationMapper;
    @Autowired
    private VehicleReservationMapper vehicleReservationMapper;
    @Autowired
    private VehicleInformationManagementMapper vehicleInformationManagementMapper;
    private ResultData resultData;

    @Override
    @Transactional
    public ResultData insertUserInfo_new(JSONArray userInfoList) {
        resultData = new ResultData();
        if (userInfoList == null || userInfoList.size() <= 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        StringToDateConverter converter = new StringToDateConverter();
        for (int k = 0;k < userInfoList.size();++k) {
            JSONObject object = userInfoList.getJSONObject(k);
            String loginName = object.getString("loginName");
            UserDeptInfo userinfoList = entryInformationMapper.getUserInfoByLoginName(loginName);
            if (userinfoList != null){
                resultData.setStatusCode(400);
                resultData.setStatusRes("用户已存在!");
                return resultData;
            }
            Integer i = entryInformationMapper.insertUserInfo_new(object.getString("loginName"),object.getString("name"),object.getString("deptCode"),
                    object.getString("deptId"),object.getString("deptName"),converter.convert(object.getString("driverBirthday")),
                    converter.convert(object.getString("startDrivingDate")),
                    object.getString("drivingType"),object.getString("driverPhoto"),
                    object.getString("driverStatus"),object.getString("role"),object.getString("relation"));
            if (i < 1) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("fail");
                return resultData;
            }
        }
        resultData.setStatusCode(200);
        resultData.setStatusRes("success");
        return resultData;
    }


    @Override
    @Transactional
    public ResultData deleteUserInfo_new(long userId) {
        resultData = new ResultData();
        UserDeptInfo userInfo = vehicleReservationMapper.getUserInfoById(userId);
        System.out.println(userInfo.getRole());
        System.out.println(userInfo.getRole().equals("2"));
        if (userInfo == null){
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
            //删除的是负责人
        }else if(userInfo.getRole().equals("2")) {
            //查询该负责人绑定的申请人列表
            List<UserDeptInfo> ApplyUserList = vehicleReservationMapper.getApplyByPIC(userInfo.getLoginName());
            if (ApplyUserList != null || ApplyUserList.size() > 0) {
                Integer i = entryInformationMapper.deleteUserInfo_new(userId);
                if (i >= 1) {
                    resultData.setStatusCode(200);
                    resultData.setStatusRes("success");
                    List<String> ApplyUserName = new ArrayList<>();
                    for (UserDeptInfo ApplyUser : ApplyUserList) {
                        ApplyUserName.add(ApplyUser.getLoginName());
                    }
                    //删除成功,把申请人的relation归空
                    Integer j = vehicleReservationMapper.updateApplyBindPIC("", ApplyUserName);
                    return resultData;
                } else {
                    resultData.setStatusCode(400);
                    resultData.setStatusRes("fail");
                    return resultData;
                }
            }
        }
        Integer i = entryInformationMapper.deleteUserInfo_new(userId);
        if (i >= 1) {
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
    public ResultData getAllUserInfo_new(int pageNum, int pageSize) {
        resultData = new ResultData();
        PageHelper.startPage(pageNum,pageSize);
        List<UserDeptInfo> userBaseInfoList = entryInformationMapper.getAllUserInfo_new();
        if (userBaseInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<UserDeptInfoFormat> userBaseInfoFormatList = new ArrayList<>();
            for (UserDeptInfo info : userBaseInfoList) {
                userBaseInfoFormatList.add(new UserDeptInfoFormat(info.getUserId(),info.getName(),info.getDeptName(),info.getLoginName(),
                        DateToStringConverter.getStringDate(info.getDriverBirthday()),DateToStringConverter.getStringDate(info.getStartDrivingDate()),
                        info.getDrivingType(),info.getDriverPhoto(),info.getDriverStatus(),info.getRole(),info.getRelation()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("allCount",entryInformationMapper.getAllUserInfoCount_new());
            map.put("userBaseInfoList", userBaseInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData updateDriverInfo_new(long userId, String driverBirthday, String startDrivingDate,
                                       String drivingType, String driverPhoto) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
        if (role.length() == 0 || !role.equals("1")) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有司机角色");
            return resultData;
        }
        StringToDateConverter converter = new StringToDateConverter();
        Integer i = entryInformationMapper.updateDriverInfo_new(userId,converter.convert(driverBirthday),
                converter.convert(startDrivingDate),drivingType,driverPhoto);
        if (i >= 1) {
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
    public ResultData insertVehicleInfo(String vehicleId, int seatNumber, String vehicleType, String vehicleColor,
                                        String purchaseTime, String insuranceStart, String vehiclePhoto, String status,
                                        String returnTime, String parkingPlace, double kilometers) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        Integer i = entryInformationMapper.insertVehicleInfo(vehicleId,seatNumber,vehicleType,vehicleColor,converter.convert(purchaseTime),
                converter.convert(insuranceStart),vehiclePhoto,status,converter.convert(returnTime),parkingPlace,kilometers);
        if (i >= 1) {
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
    public ResultData deleteVehicleInfo(String vehicleId) {
        resultData = new ResultData();
        Integer i = entryInformationMapper.deleteVehicleInfo(vehicleId);
        if (i >= 1) {
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
    public ResultData getAllVehicleInfo(long userId, int pageNum, int pageSize) {
        resultData = new ResultData();
        PageHelper.startPage(pageNum,pageSize);
        List<VehicleBaseInfo> vehicleBaseInfoList = vehicleInformationManagementMapper.getAllVehicleBaseInfo();
        if (vehicleBaseInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<VehicleBaseInfoFormat> vehicleBaseInfoFormatList = new ArrayList<>();
            for (VehicleBaseInfo info : vehicleBaseInfoList) {
                vehicleBaseInfoFormatList.add(new VehicleBaseInfoFormat(info.getVehicleId(),info.getSeatNumber(),
                        info.getVehicleType(),info.getVehicleColor(),DateToStringConverter.getStringDate(info.getPurchaseTime()),
                        DateToStringConverter.getStringDate(info.getInsuranceStart()),info.getVehiclePhoto(),info.getStatus(),
                        DateToStringConverter.getStringDate(info.getReturnTime()),info.getParkingPlace(),info.getKilometers()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("allCount",vehicleInformationManagementMapper.getAllVehicleBaseInfoCount());
            map.put("vehicleBaseInfoList",vehicleBaseInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData updateVehicleInfo(String vehicleId, int seatNumber, String vehicleType, String vehicleColor,
                                        String purchaseTime, String insuranceStart, String vehiclePhoto) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        Integer i = entryInformationMapper.updateVehicleInfo(vehicleId,seatNumber,vehicleType,vehicleColor,converter.convert(purchaseTime),
                converter.convert(insuranceStart),vehiclePhoto);
        if (i >= 1) {
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
    public ResultData insertCommonData(String dataType, String data) {
        resultData = new ResultData();
        Integer i = entryInformationMapper.insertCommonData(dataType,data);
        if (i >= 1) {
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
    public ResultData deleteCommonData(String dataType, String data) {
        resultData = new ResultData();
        Integer i = entryInformationMapper.deleteCommonData(dataType,data);
        if (i >= 1) {
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
    public ResultData loginNameToUserIdAndRole_new(String loginName) {
        resultData = new ResultData();
        System.out.println(loginName);
        UserDeptInfo userinfo = entryInformationMapper.getUserInfoByLoginName(loginName);
        System.out.println(userinfo);
        if (userinfo != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            Map<String,Object> map = new HashMap<>();
            map.put("userinfo",userinfo);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(401);
            resultData.setStatusRes("你没有该应用的访问权限，如有需要，请联系管理员!");
        }
        return resultData;
    }
}
