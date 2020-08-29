package com.gcb.vehiclemanagement.service;

import com.gcb.vehiclemanagement.dao.VehicleInformationManagementMapper;
import com.gcb.vehiclemanagement.dao.VehicleReservationMapper;
import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.entity.VehicleBaseAndFeeInfo;
import com.gcb.vehiclemanagement.entity.VehicleBaseInfo;
import com.gcb.vehiclemanagement.entity.VehicleFeeInfo;
import com.gcb.vehiclemanagement.entity.format.VehicleBaseInfoFormat;
import com.gcb.vehiclemanagement.entity.format.VehicleFeeInfoFormat;
import com.gcb.vehiclemanagement.util.DateToStringConverter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VehicleInformationManagementServiceImpl implements VehicleInformationManagementService {

    @Autowired
    private VehicleInformationManagementMapper vehicleInformationManagementMapper;
    @Autowired
    private VehicleReservationMapper vehicleReservationMapper;
    private ResultData resultData;

    @Override
    @Transactional
    public ResultData getVehicleFeeInfo(long userId, String applyId) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
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
            resultData.setStatusRes("该用户没有查询权限");
            return resultData;
        }
        List<VehicleFeeInfo> vehicleFeeInfoList = vehicleInformationManagementMapper.getVehicleFeeInfoByApplyId(applyId);
        if (vehicleFeeInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<VehicleFeeInfoFormat> vehicleFeeInfoFormatList = new ArrayList<>();
            for (VehicleFeeInfo feeInfo : vehicleFeeInfoList) {
                vehicleFeeInfoFormatList.add(new VehicleFeeInfoFormat(feeInfo.getApplyId(),feeInfo.getVehicleId(),feeInfo.getAgent(),
                        DateToStringConverter.getStringDate(feeInfo.getStartTime()),feeInfo.getEndTime(),
                        feeInfo.getOilFee(),feeInfo.getMaintenanceFee(),feeInfo.getParkingFee(),feeInfo.getForfeit(),
                        DateToStringConverter.getStringDate(feeInfo.getPaymentTime()),feeInfo.getParkingPlace(),feeInfo.getStartKilometers(),
                        feeInfo.getEndKilometers()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("vehicleFeeInfoList",vehicleFeeInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getAllVehicleBaseAndFeeInfo(int pageNum, int pageSize) {
        resultData = new ResultData();
        PageHelper.startPage(pageNum,pageSize);
        List<VehicleBaseInfo> vehicleBaseInfoList = vehicleInformationManagementMapper.getAllVehicleBaseInfo();
        if (vehicleBaseInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<VehicleBaseAndFeeInfo> vehicleBaseAndFeeInfoList = new ArrayList<>();
            for (VehicleBaseInfo info : vehicleBaseInfoList) {
                VehicleBaseInfoFormat vehicleBaseInfoFormat = new VehicleBaseInfoFormat(info.getVehicleId(),info.getSeatNumber(),
                        info.getVehicleType(),info.getVehicleColor(), DateToStringConverter.getStringDate(info.getPurchaseTime()),
                        DateToStringConverter.getStringDate(info.getInsuranceStart()),info.getVehiclePhoto(),info.getStatus(),
                        DateToStringConverter.getStringDate(info.getReturnTime()),info.getParkingPlace(),info.getKilometers());
                List<VehicleFeeInfo> vehicleFeeInfoList = vehicleInformationManagementMapper.getVehicleFeeInfo(info.getVehicleId());
                List<VehicleFeeInfoFormat> vehicleFeeInfoFormatList = new ArrayList<>();
                if (vehicleFeeInfoList != null) {
                    for (VehicleFeeInfo feeInfo : vehicleFeeInfoList) {
                        vehicleFeeInfoFormatList.add(new VehicleFeeInfoFormat(feeInfo.getApplyId(),feeInfo.getVehicleId(),feeInfo.getAgent(),
                                DateToStringConverter.getStringDate(feeInfo.getStartTime()),feeInfo.getEndTime(),
                                feeInfo.getOilFee(),feeInfo.getMaintenanceFee(),feeInfo.getParkingFee(),feeInfo.getForfeit(),
                                DateToStringConverter.getStringDate(feeInfo.getPaymentTime()),feeInfo.getParkingPlace(),feeInfo.getStartKilometers(),
                                feeInfo.getEndKilometers()));
                    }
                }
                vehicleBaseAndFeeInfoList.add(new VehicleBaseAndFeeInfo(vehicleBaseInfoFormat,vehicleFeeInfoFormatList));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("allCount",vehicleInformationManagementMapper.getAllVehicleBaseInfoCount());
            map.put("vehicleBaseAndFeeInfoList",vehicleBaseAndFeeInfoList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getVehicleBaseAndFeeInfo(String vehicleId) {
        resultData = new ResultData();
        List<VehicleBaseInfo> vehicleBaseInfoList = vehicleInformationManagementMapper.getVehicleBaseInfo(vehicleId);
        if (vehicleBaseInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<VehicleBaseAndFeeInfo> vehicleBaseAndFeeInfoList = new ArrayList<>();
            for (VehicleBaseInfo info : vehicleBaseInfoList) {
                VehicleBaseInfoFormat vehicleBaseInfoFormat = new VehicleBaseInfoFormat(info.getVehicleId(),info.getSeatNumber(),
                        info.getVehicleType(),info.getVehicleColor(), DateToStringConverter.getStringDate(info.getPurchaseTime()),
                        DateToStringConverter.getStringDate(info.getInsuranceStart()),info.getVehiclePhoto(),info.getStatus(),
                        DateToStringConverter.getStringDate(info.getReturnTime()),info.getParkingPlace(),info.getKilometers());
                List<VehicleFeeInfo> vehicleFeeInfoList = vehicleInformationManagementMapper.getVehicleFeeInfo(info.getVehicleId());
                List<VehicleFeeInfoFormat> vehicleFeeInfoFormatList = new ArrayList<>();
                if (vehicleFeeInfoList != null) {
                    for (VehicleFeeInfo feeInfo : vehicleFeeInfoList) {
                        vehicleFeeInfoFormatList.add(new VehicleFeeInfoFormat(feeInfo.getApplyId(),feeInfo.getVehicleId(),feeInfo.getAgent(),
                                DateToStringConverter.getStringDate(feeInfo.getStartTime()),feeInfo.getEndTime(),
                                feeInfo.getOilFee(),feeInfo.getMaintenanceFee(),feeInfo.getParkingFee(),feeInfo.getForfeit(),
                                DateToStringConverter.getStringDate(feeInfo.getPaymentTime()),feeInfo.getParkingPlace(),feeInfo.getStartKilometers(),
                                feeInfo.getEndKilometers()));
                    }
                }
                vehicleBaseAndFeeInfoList.add(new VehicleBaseAndFeeInfo(vehicleBaseInfoFormat,vehicleFeeInfoFormatList));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("vehicleBaseAndFeeInfoList",vehicleBaseAndFeeInfoList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    public ResultData inquireVehicleLatestCondition(long userId, String vehicleId) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
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
            resultData.setStatusRes("该用户没有查询权限");
            return resultData;
        }
        List<VehicleBaseInfo> vehicleBaseInfoList = vehicleInformationManagementMapper.getVehicleBaseInfo(vehicleId);
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
            map.put("vehicleBaseInfoList",vehicleBaseInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }
}
