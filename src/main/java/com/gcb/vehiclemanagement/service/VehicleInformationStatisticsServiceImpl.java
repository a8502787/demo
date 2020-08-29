package com.gcb.vehiclemanagement.service;

import com.gcb.vehiclemanagement.dao.VehicleInformationStatisticsMapper;
import com.gcb.vehiclemanagement.dao.VehicleReservationMapper;
import com.gcb.vehiclemanagement.entity.MaintenanceApplyInfo;
import com.gcb.vehiclemanagement.entity.ResultData;
import com.gcb.vehiclemanagement.entity.VehicleApplyInfo;
import com.gcb.vehiclemanagement.entity.VehicleFeeInfo;
import com.gcb.vehiclemanagement.entity.format.MaintenanceApplyInfoFormat;
import com.gcb.vehiclemanagement.entity.format.VehicleApplyInfoFormat;
import com.gcb.vehiclemanagement.entity.format.VehicleFeeInfoFormat;
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
public class VehicleInformationStatisticsServiceImpl implements VehicleInformationStatisticsService {

    @Autowired
    private VehicleInformationStatisticsMapper vehicleInformationStatisticsMapper;
    @Autowired
    private VehicleReservationMapper vehicleReservationMapper;
    private ResultData resultData;

    @Override
    @Transactional
    public ResultData getVehicleUseInfoByCombinationQuery(String startTime, String endTime,
                                                          String vehicleId, String driverName,
                                                          String applyDepartment, String cause,
                                                          String vehicleUser, int pageNum, int pageSize) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        PageHelper.startPage(pageNum,pageSize);
        List<VehicleApplyInfo> vehicleApplyInfoList = vehicleInformationStatisticsMapper
                .getVehicleApplyInfoByCombinationQuery(converter.convert(startTime),converter.convert(endTime),
                        vehicleId,driverName,applyDepartment,cause,vehicleUser);
        PageHelper.startPage(pageNum,pageSize);
        List<MaintenanceApplyInfo> maintenanceApplyInfoList = vehicleInformationStatisticsMapper
                .getMaintenanceApplyInfoByCombinationQuery(converter.convert(startTime),converter.convert(endTime),
                        vehicleId,driverName,cause);
        if (vehicleApplyInfoList != null && maintenanceApplyInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
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
            List<MaintenanceApplyInfoFormat> maintenanceApplyInfoFormatList = new ArrayList<>();
            for (MaintenanceApplyInfo info : maintenanceApplyInfoList) {
                maintenanceApplyInfoFormatList.add(new MaintenanceApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                        info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),DateToStringConverter.getStringDate(info.getCreateTime()),
                        DateToStringConverter.getStringDate(info.getStartTime()), info.getEndTime(),
                        info.getDestination(),info.getCause(), info.getMaintenanceProvider(),
                        info.getEntourage(),info.getContactPhone(),info.getStatus(),info.getConfirm(),info.getFinish()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("allCountOfVehicleApplyInfo",vehicleInformationStatisticsMapper
                    .getVehicleApplyInfoByCombinationQueryCount(converter.convert(startTime),converter.convert(endTime),
                            vehicleId,driverName,applyDepartment,cause,vehicleUser));
            map.put("allCountOfMaintenanceApplyInfo",vehicleInformationStatisticsMapper
                    .getMaintenanceApplyInfoByCombinationQueryCount(converter.convert(startTime),converter.convert(endTime),
                            vehicleId,driverName,cause));
            map.put("vehicleApplyInfoList",vehicleApplyInfoFormatList);
            map.put("maintenanceApplyInfoList",maintenanceApplyInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getVehicleFeeInfoByCombinationQuery(String startTime, String endTime, String vehicleId,
                                                          int pageNum, int pageSize) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        PageHelper.startPage(pageNum,pageSize);
        if (endTime.length()>0) {
            endTime = endTime + ":00";
        }else{
            endTime = "";
        }
        if (startTime.length()>0) {
            startTime = startTime + ":00";
        }else{
            startTime = "";
        }
        List<VehicleFeeInfo> vehicleFeeInfoList = vehicleInformationStatisticsMapper
                .getVehicleFeeInfoByCombinationQuery(converter.convert(startTime),converter.convert(endTime),
                        vehicleId);
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
            map.put("allCount",vehicleInformationStatisticsMapper
                    .getVehicleFeeInfoByCombinationQueryCount(converter.convert(startTime),converter.convert(endTime),
                            vehicleId));
            map.put("vehicleFeeInfoList",vehicleFeeInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }
}
