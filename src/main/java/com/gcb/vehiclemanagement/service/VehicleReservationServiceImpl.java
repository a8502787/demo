package com.gcb.vehiclemanagement.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gcb.vehiclemanagement.dao.EntryInformationMapper;
import com.gcb.vehiclemanagement.dao.VehicleInformationManagementMapper;
import com.gcb.vehiclemanagement.dao.VehicleReservationMapper;
import com.gcb.vehiclemanagement.entity.*;
import com.gcb.vehiclemanagement.entity.format.ApprovalHistoryInfoFormat;
import com.gcb.vehiclemanagement.entity.format.UserDeptInfoFormat;
import com.gcb.vehiclemanagement.entity.format.VehicleApplyInfoFormat;
import com.gcb.vehiclemanagement.entity.format.VehicleBaseInfoFormat;
import com.gcb.vehiclemanagement.util.DateToStringConverter;
import com.gcb.vehiclemanagement.util.RandomUtil;
import com.gcb.vehiclemanagement.util.StringToDateConverter;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Component
public class VehicleReservationServiceImpl implements VehicleReservationService {
//
//    @Value("${upload.picture.server.username}")
//    private String USERNAME;// 服务器用户名
//    @Value("${upload.picture.server.password}")
//    private String PASSWORD;// 服务器密码
//    @Value("${upload.picture.server.ip}")
//    private String IP; // 服务器IP
//    @Value("${upload.picture.server.port}")
//    private String PORT; // 服务器端口
//    @Value("${upload.picture.server.directory}")
//    private String UPLOAD_DIRECTORY; // 文件上传目录
//    @Value("${upload.picture.server.url.prefix}")
//    private String URL_PREFIX; // 访问图片的前缀

    Logger logger = LoggerFactory.getLogger(VehicleReservationServiceImpl.class);

    @Autowired
    private VehicleReservationMapper vehicleReservationMapper;
    @Autowired
    private VehicleInformationManagementMapper vehicleInformationManagementMapper;
//    @Autowired
//    private PushNotificationUtil pushNotificationUtil;
    @Autowired
    private EntryInformationMapper entryInformationMapper;
    private ResultData resultData;

    @Override
    @Transactional
    public ResultData applyVehicle_new(long applicantId, String applicantName, String vehicleUser,
                                       String startTime, String endTime, String destination,
                                       String cause, String entourage, int peopleNumber, String contactPhone,String picUrl) {
        resultData = new ResultData();
        //判断该申请人是否有指定负责人
        String relation = vehicleReservationMapper.getRelationByUserId(applicantId);
        if (relation == null||relation.length()<=0){
            resultData.setStatusCode(400);
            resultData.setStatusRes("没有指定负责人,请联系后勤管理中心管理员!");
            return resultData;
        }
        startTime = startTime+":00";
//        endTime = endTime+":00";
        String role = vehicleReservationMapper.getUserRole_new(applicantId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        if (role.equals("0")) {
            flag = true;
        }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有用车申请权限");
            return resultData;
        }
        StringToDateConverter converter = new StringToDateConverter();
        // 通过申请人userid找到userinfo
        UserDeptInfo userinfo = vehicleReservationMapper.getUserInfoById(applicantId);
        //通过申请人Relation找到申请人对应的负责人userid
        Long PICid = vehicleReservationMapper.getUserIdByLoginName(userinfo.getRelation());
        if(PICid == null){
            resultData.setStatusCode(400);
            resultData.setStatusRes("没有找到负责人");
            return resultData;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        String applyId = RandomUtil.getRandom();
        Integer i = vehicleReservationMapper.applyVehicle_new(applyId,applicantId,applicantName,userinfo.getDeptCode(),
                userinfo.getDeptName(),vehicleUser,converter.convert(currentDate),converter.convert(startTime),null,
                destination,cause,entourage,peopleNumber,contactPhone,"0","0","9",picUrl);
        if (i >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //发起推送给申请人对应的负责人
            List<String> nameList = new ArrayList<>();
            //通过负责人userid查询loginName
            nameList.add(vehicleReservationMapper.getLoginName(PICid));
//            nameList.add(vehicleReservationMapper.getLoginName(vehicleReservationMapper.getDepartmentLeaderId(department_code))); // 消息接收者的登录名（工号）
            VehicleApplyInfo vehicleApplyInfo = vehicleReservationMapper.getVehicleApplyInfoByApplyId(applyId);
            if (vehicleApplyInfo == null) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("信息推送失败");
                return resultData;
            }
//            String description = "申请单号："+applyId+"<br />申请人："+
//                    vehicleApplyInfos.get(0).getApplicantName()+"<br />申请部门："+vehicleApplyInfos.get(0).getApplyDepartment()+
//                    "<br />用车人："+vehicleApplyInfos.get(0).getVehicleUser()+"<br />陪同人员："+vehicleApplyInfos.get(0).getEntourage()+
//                    "<br />出行时间："+timeToString(vehicleApplyInfos.get(0).getStartTime())+ "<br />返回时间："+timeToString(vehicleApplyInfos.get(0).getEndTime())+
//                    "<br />目的地："+vehicleApplyInfos.get(0).getDestination()+ "<br />事由："+vehicleApplyInfos.get(0).getCause()+
//                    "<br />联系电话："+vehicleApplyInfos.get(0).getContactPhone();
//            String description = "申请单号："+applyId+"<br />申请人："+
//                    vehicleApplyInfo.getApplicantName()+"<br />申请部门："+vehicleApplyInfo.getApplyDepartment()+
//                    "<br />用车人："+vehicleApplyInfo.getVehicleUser()+"<br />陪同人员："+vehicleApplyInfo.getEntourage()+
//                    "<br />出行时间："+timeToString(vehicleApplyInfo.getStartTime())+
//                    "<br />目的地："+vehicleApplyInfo.getDestination()+"<br />用车人数："+vehicleApplyInfo.getPeopleNumber() +
//                    "<br />事由："+vehicleApplyInfo.getCause();
//            System.out.println("nameList:"+nameList);
//            pushNotificationUtil.push(nameList,"您有新的用车申请待审批",description,true);
//            System.out.println("description:"+description);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getVehicleApplyInfo(long userId, int pageNum, int pageSize) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        Map<String,Object> map = new HashMap<>();
        if (role.equals("0")) {
            flag = true;
            PageHelper.startPage(pageNum,pageSize);
            List<VehicleApplyInfo> vehicleApplyInfoList = vehicleReservationMapper.getVehicleApplyInfoByApplicantId(userId);
            if (vehicleApplyInfoList != null) {
                resultData.setStatusCode(200);
                resultData.setStatusRes("success");
                List<VehicleApplyInfoFormat> vehicleApplyInfoFormatList = new ArrayList<>();
                for (VehicleApplyInfo info : vehicleApplyInfoList) {
                    vehicleApplyInfoFormatList.add(new VehicleApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                            info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),info.getDepartmentCode(),
                            info.getApplyDepartment(), info.getVehicleUser(), DateToStringConverter.getStringDate(info.getCreateTime()),
                            DateToStringConverter.getStringDate(info.getStartTime()),
                            info.getEndTime(),info.getDestination(),
                            info.getCause(),info.getEntourage(),info.getPeopleNumber(),
                            info.getContactPhone(),info.getStatus(),info.getConfirm(),info.getFinish(),info.getPicUrl()));
                }
                map.put("allCountForApplicant",vehicleReservationMapper.getVehicleApplyInfoByApplicantIdCount(userId));
                map.put("vehicleApplyInfoListForApplicant",vehicleApplyInfoFormatList);
            } else {
                resultData.setStatusCode(400);
                resultData.setStatusRes("fail");
                return resultData;
            }
        }


        if (role.equals("1")) {
            flag = true;
            PageHelper.startPage(pageNum,pageSize);
            List<VehicleApplyInfo> vehicleApplyInfoList = vehicleReservationMapper.getVehicleApplyInfoForDriver(userId);
            if (vehicleApplyInfoList != null) {
                resultData.setStatusCode(200);
                resultData.setStatusRes("success");
                List<VehicleApplyInfoFormat> vehicleApplyInfoFormatList = new ArrayList<>();
                for (VehicleApplyInfo info : vehicleApplyInfoList) {
                    vehicleApplyInfoFormatList.add(new VehicleApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                            info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),info.getDepartmentCode(),
                            info.getApplyDepartment(), info.getVehicleUser(), DateToStringConverter.getStringDate(info.getCreateTime()),
                            DateToStringConverter.getStringDate(info.getStartTime()),
                            info.getEndTime(),info.getDestination(),
                            info.getCause(),info.getEntourage(),info.getPeopleNumber(),
                            info.getContactPhone(),info.getStatus(),info.getConfirm(),info.getFinish(),info.getPicUrl()));
                }
                map.put("allCountForDriver",vehicleReservationMapper.getVehicleApplyInfoForDriverCount(userId));
                map.put("vehicleApplyInfoListForDriver",vehicleApplyInfoFormatList);
            } else {
                resultData.setStatusCode(400);
                resultData.setStatusRes("fail");
                return resultData;
            }
        }


        if (role.equals("2")) {
            flag = true;
            //查询该负责人对应的申请人
            String loginNaem = vehicleReservationMapper.getLoginName(userId);
            List<UserDeptInfo> Apply = vehicleReservationMapper.getApplyByPIC(loginNaem);
            if (Apply == null||Apply.size()<=0){
                resultData.setStatusCode(400);
                resultData.setStatusRes("没有指定申请人,请联系后勤管理中心管理员!");
                return resultData;
            }
            PageHelper.startPage(pageNum,pageSize);
            List<VehicleApplyInfo> vehicleApplyInfoListTotal = new ArrayList<>();
            for (UserDeptInfo userinfo:Apply){
                List<VehicleApplyInfo> vehicleApplyInfoList = vehicleReservationMapper
                        .getVehicleApplyInfoByApplicantId(userinfo.getUserId());
                vehicleApplyInfoListTotal.addAll(vehicleApplyInfoList);
            }
            if (vehicleApplyInfoListTotal != null) {
                resultData.setStatusCode(200);
                resultData.setStatusRes("success");
                List<VehicleApplyInfoFormat> vehicleApplyInfoFormatList = new ArrayList<>();
                for (VehicleApplyInfo info : vehicleApplyInfoListTotal) {
                    vehicleApplyInfoFormatList.add(new VehicleApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                            info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),info.getDepartmentCode(),
                            info.getApplyDepartment(), info.getVehicleUser(),DateToStringConverter.getStringDate(info.getCreateTime()),
                            DateToStringConverter.getStringDate(info.getStartTime()),
                            info.getEndTime(),info.getDestination(),
                            info.getCause(),info.getEntourage(),info.getPeopleNumber(),
                            info.getContactPhone(),info.getStatus(),info.getConfirm(),info.getFinish(),info.getPicUrl()));
                }
                map.put("allCountForPIC",vehicleApplyInfoListTotal.size());
                map.put("vehicleApplyInfoListForPIC",vehicleApplyInfoFormatList);
            } else {
                resultData.setStatusCode(400);
                resultData.setStatusRes("fail");
                return resultData;
            }
        }

        if (role.equals("3") || role.equals("4") || role.equals("5")) {
            flag = true;
            PageHelper.startPage(pageNum,pageSize);
            List<VehicleApplyInfo> vehicleApplyInfoList = vehicleReservationMapper.getAllVehicleApplyInfo();
            if (vehicleApplyInfoList != null) {
                resultData.setStatusCode(200);
                resultData.setStatusRes("success");
                List<VehicleApplyInfoFormat> vehicleApplyInfoFormatList = new ArrayList<>();
                for (VehicleApplyInfo info : vehicleApplyInfoList) {
                    vehicleApplyInfoFormatList.add(new VehicleApplyInfoFormat(info.getApplyId(),info.getApplicantId(),
                            info.getApplicantName(),info.getVehicleId(),info.getDriverId(),info.getDriverName(),info.getDepartmentCode(),
                            info.getApplyDepartment(), info.getVehicleUser(), DateToStringConverter.getStringDate(info.getCreateTime()),
                            DateToStringConverter.getStringDate(info.getStartTime()),
                            info.getEndTime(),info.getDestination(),
                            info.getCause(),info.getEntourage(),info.getPeopleNumber(),
                            info.getContactPhone(),info.getStatus(),info.getConfirm(),info.getFinish(),info.getPicUrl()));
                }
                map.put("allCountForLogistics",vehicleReservationMapper.getAllVehicleApplyInfoCount());
                map.put("vehicleApplyInfoListForLogistics",vehicleApplyInfoFormatList);
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
    public ResultData passApprovalByIPC(String applyId, long approverId, String suggestion) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(approverId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        if (role.equals("2")) {
            flag = true;
        }

        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户不是负责人");
            return resultData;
        }
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        String status = vehicleReservationMapper.getVehicleApplyStatus(applyId);
        if (!status.equals("0")) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("通过审批失败");
            return resultData;
        }
        Integer i = vehicleReservationMapper.passApprovalByDepartmentLeader(applyId,status,
                String.valueOf(Integer.parseInt(status)+1));
        Integer j = vehicleReservationMapper.insertApprovalHistory(applyId,approverId,suggestion,
                converter.convert(currentDate),"0","1");
        if (i >= 1 && j >= 1) {
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
            VehicleApplyInfo vehicleApplyInfo = vehicleReservationMapper.getVehicleApplyInfoByApplyId(applyId);
            if (vehicleApplyInfo == null) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("信息推送失败");
                return resultData;
            }
            System.out.println(vehicleApplyInfo.getEndTime());
//            String description = "申请单号："+applyId+"<br />申请人："+
//                    vehicleApplyInfo.getApplicantName()+"<br />申请部门："+vehicleApplyInfo.getApplyDepartment()+
//                    "<br />用车人："+vehicleApplyInfo.getVehicleUser()+"<br />陪同人员："+vehicleApplyInfo.getEntourage()+
//                    "<br />出行时间："+timeToString(vehicleApplyInfo.getStartTime())+
//                    "<br />目的地："+vehicleApplyInfo.getDestination()+"<br />用车人数："+vehicleApplyInfo.getPeopleNumber()+
//                    "<br />事由："+vehicleApplyInfo.getCause();
//            System.out.println("nameList:"+nameList);
//            pushNotificationUtil.push(nameList,"您有新的用车申请待审批",description,true);
            //发起推送给秘书
            nameList.clear();
            List<UserDeptInfo> list = vehicleReservationMapper.getUserInfoByRole("6");
            if (list == null || list.size() <= 0) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("信息推送失败");
                return resultData;
            }
            List<String> loginNameList = new ArrayList<>();
            for (UserDeptInfo userinfo : list){
                System.out.println("userinfo:"+userinfo.getLoginName());
                loginNameList.add(userinfo.getLoginName());
            }
            nameList.addAll(loginNameList);// 消息接收者的登录名（工号）
            System.out.println("nameList:"+nameList);
//            description = description+"<br />注意：待后勤管理中心审批过后，会将最终的审批结果发送给您！";
//            pushNotificationUtil.push(nameList,"收到一条用车申请，请注意",description,false);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData passApprovalByLogistics(String applyId, String vehicleId, String driverId, String driverName,
                                              long approverId, String suggestion) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        String status = vehicleReservationMapper.getVehicleApplyStatus(applyId);
        String role = vehicleReservationMapper.getUserRole_new(approverId);
        System.out.println();
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        if (role.equals("3")) {
            flag = true;
            if (!status.equals("1")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("通过审批失败");
                return resultData;
            }
        }
        if (role.equals("4")) {
            flag = true;
            if (!status.equals("2")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("通过审批失败");
                return resultData;
            }
        }
        if (role.equals("5")) {
            flag = true;
            if (!status.equals("3")) {
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
        Long driverId_long = null;
        if (driverId.length()>0){
            driverId_long = Long.parseLong(driverId);
        }
        Integer i = vehicleReservationMapper.passApprovalByLogistics(applyId,vehicleId,driverId_long,driverName,
                status,String.valueOf(Integer.parseInt(status)+1));
        Integer j = vehicleReservationMapper.insertApprovalHistory(applyId,approverId,suggestion,
                converter.convert(currentDate),"0","1");
        if (i >= 1 && j >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //发起推送给后勤二、三级审批人
            List<Long> userIdList = null;
            List<String> nameList = null;
            String description = null;
            VehicleApplyInfo vehicleApplyInfo = vehicleReservationMapper.getVehicleApplyInfoByApplyId(applyId);
            if (vehicleApplyInfo == null) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("信息推送失败");
                return resultData;
            }
            description = "申请单号："+applyId+"<br />申请人："+
                    vehicleApplyInfo.getApplicantName()+"<br />申请部门："+vehicleApplyInfo.getApplyDepartment()+
                    "<br />用车人："+vehicleApplyInfo.getVehicleUser()+"<br />陪同人员："+vehicleApplyInfo.getEntourage()+
                    "<br />出行时间："+timeToString(vehicleApplyInfo.getStartTime())+
                    "<br />目的地："+vehicleApplyInfo.getDestination()+"<br />用车人数："+vehicleApplyInfo.getPeopleNumber()+
                    "<br />事由："+vehicleApplyInfo.getCause();
            switch (status) {
                case "1": {
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
//                    pushNotificationUtil.push(nameList,"您有新的用车申请待审批",description,true);
                }
                    break;
                case "2": {
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
//                    pushNotificationUtil.push(nameList,"您有新的用车申请待审批",description,true);
                }
                    break;
                case "3": {
                    //流程完成，将信息推送给申请人和秘书
                    nameList = new ArrayList<>();
                    nameList.add(vehicleReservationMapper.getLoginName(vehicleReservationMapper.getApplicantIdByVehicleApplyId(applyId)));
//                    pushNotificationUtil.push(nameList,"您的用车申请审批已通过",description,true);
                    List<UserDeptInfo> list = vehicleReservationMapper.getUserInfoByRole("6");
                    if (list == null || list.size() <= 0) {
                        resultData.setStatusCode(400);
                        resultData.setStatusRes("信息推送失败");
                        return resultData;
                    }
                    List<String> loginNameList = new ArrayList<>();
                    for (UserDeptInfo userinfo : list){
                        loginNameList.add(userinfo.getLoginName());
                    }
                    nameList.clear();
                    nameList.addAll(loginNameList);// 消息接收者的登录名（工号）
//                    pushNotificationUtil.push(nameList,"用车申请审批通过",description,false);
                    //申请已通过，将信息推送给司机
                    nameList.clear();
                    nameList.add(vehicleReservationMapper.getLoginName(vehicleReservationMapper.getDriverIdByVehicleApplyId(applyId)));
                    description = description+"<br />注意：请尽快联系用车人，并在当天出车前录入出车前信息，谢谢！";
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
    public ResultData refuseApproval(String applyId, long approverId, String suggestion) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        String role = vehicleReservationMapper.getUserRole_new(approverId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        String VehicleApplyStatus = vehicleReservationMapper.getVehicleApplyStatus(applyId);
        boolean flag = false;
        if (role.equals("2")) {
            flag = true;
            if (!VehicleApplyStatus.equals("0")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("拒绝审批失败");
                return resultData;
            }
        }
        if (role.equals("3")) {
            flag = true;
            if (!VehicleApplyStatus.equals("1")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("拒绝审批失败");
                return resultData;
            }
        }
        if (role.equals("4")) {
            flag = true;
            if (!VehicleApplyStatus.equals("2")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("拒绝审批失败");
                return resultData;
            }
        }
        if (role.equals("5")) {
            flag = true;
            if (!VehicleApplyStatus.equals("3")) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("拒绝审批失败");
                return resultData;
            }
        }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有审批权限");
            return resultData;
        }
        Integer i = vehicleReservationMapper.refuseApproval(applyId,VehicleApplyStatus,"9");
        Integer j = vehicleReservationMapper.insertApprovalHistory(applyId,approverId,suggestion,
                converter.convert(currentDate),"0","9");
        if (i >= 1 && j >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //流程完成，将信息推送给申请人和秘书
            List<String> nameList = new ArrayList<>();
            nameList.add(vehicleReservationMapper.getLoginName(vehicleReservationMapper.getApplicantIdByVehicleApplyId(applyId)));
            String description = "拒绝原因："+suggestion;
//            pushNotificationUtil.push(nameList,"您的用车申请已被拒绝",description,true);
            if (!VehicleApplyStatus.equals("0")) {
                List<UserDeptInfo> list = vehicleReservationMapper.getUserInfoByRole("6");
                if (list == null || list.size() <= 0) {
                    resultData.setStatusCode(400);
                    resultData.setStatusRes("信息推送失败");
                    return resultData;
                }
                List<String> loginNameList = new ArrayList<>();
                for (UserDeptInfo userinfo : list){
                    loginNameList.add(userinfo.getLoginName());
                }
                nameList.clear();
                nameList.addAll(loginNameList);// 消息接收者的登录名（工号）
                VehicleApplyInfo vehicleApplyInfo = vehicleReservationMapper.getVehicleApplyInfoByApplyId(applyId);
                if (vehicleApplyInfo == null) {
                    resultData.setStatusCode(400);
                    resultData.setStatusRes("信息推送失败");
                    return resultData;
                }
                description = "申请单号："+applyId+"<br />申请人："+
                        vehicleApplyInfo.getApplicantName()+"<br />申请部门："+vehicleApplyInfo.getApplyDepartment()+
                        "<br />用车人："+vehicleApplyInfo.getVehicleUser()+"<br />陪同人员："+vehicleApplyInfo.getEntourage()+
                        "<br />出行时间："+timeToString(vehicleApplyInfo.getStartTime())+
                        "<br />目的地："+vehicleApplyInfo.getDestination()+"<br />用车人数："+vehicleApplyInfo.getPeopleNumber()+
                        "<br />事由："+vehicleApplyInfo.getCause()+
                        "<br />联系电话："+vehicleApplyInfo.getContactPhone()+"<br />拒绝原因："+suggestion;
//                pushNotificationUtil.push(nameList,"用车申请已被拒绝",description,false);
            }
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData cancelApply(String applyId, long approverId, String suggestion) {
        resultData = new ResultData();
        StringToDateConverter converter = new StringToDateConverter();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String currentDate = df.format(new Date());
        VehicleApplyInfo vehicleApplyInfo = vehicleReservationMapper.getVehicleApplyInfoByApplyId(applyId);
        if (vehicleApplyInfo == null) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("信息推送失败");
            return resultData;
        }
        //查询申请单状态
        String status = vehicleApplyInfo.getStatus();
        //查询申请单司机是否确认
        String confirm = vehicleApplyInfo.getConfirm();
        //指派车辆车牌
        String vehicleId = vehicleApplyInfo.getVehicleId();
        //指派司机id
        long driverId = vehicleApplyInfo.getDriverId();
        //查询撤销人角色
        String role = vehicleReservationMapper.getUserRole_new(approverId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }

        if (!role.equals("0")) {
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
        Integer i = vehicleReservationMapper.refuseApproval(applyId,status,"5");
        if (vehicleId != null && !vehicleId.equals("")){
            Integer j = vehicleReservationMapper.updateVehicleStatus(vehicleId,"1");
        }
        if (driverId !=0){
            Integer k = vehicleReservationMapper.updateDriverStatus(driverId,"3");
        }
        Integer l = vehicleReservationMapper.updateFinishValue(applyId,"9");
        Integer m = vehicleReservationMapper.insertApprovalHistory(applyId,approverId,suggestion,
                converter.convert(currentDate),"0","5");

        if (i >= 1 && l >= 1 && m >= 1) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            //流程完成，将信息推送给审批人和秘书
            List<String> nameList = new ArrayList<>();
            List<String> DriverNameList = new ArrayList<>();
            List<ApprovalHistoryInfo> AHList = new ArrayList<>();
            AHList = vehicleReservationMapper.getApprovalHistory(applyId);
            String description = "申请单号："+applyId+"<br />申请人："+
                    vehicleApplyInfo.getApplicantName()+"<br />申请部门："+vehicleApplyInfo.getApplyDepartment()+
                    "<br />用车人："+vehicleApplyInfo.getVehicleUser()+"<br />陪同人员："+vehicleApplyInfo.getEntourage()+
                    "<br />出行时间："+timeToString(vehicleApplyInfo.getStartTime())+
                    "<br />目的地："+vehicleApplyInfo.getDestination()+"<br />用车人数："+vehicleApplyInfo.getPeopleNumber()+
                    "<br />事由："+vehicleApplyInfo.getCause()+
                    "<br />联系电话："+vehicleApplyInfo.getContactPhone()+"<br />撤销原因："+suggestion;
            if (AHList.size()>0){
                List<Long> ApprovalIdList = new ArrayList<Long>();
                AHList.forEach(AHinfo -> ApprovalIdList.add(AHinfo.getApproverId()));
                System.out.println(ApprovalIdList);
                //判断司机
                List<UserDeptInfo> UserList = new ArrayList<>();
                ApprovalIdList.forEach(AHId -> UserList.add(vehicleReservationMapper.getUserInfoById(AHId)));

                for (UserDeptInfo userInfo:UserList){
                    if (userInfo.getRole().equals("1")){
                        DriverNameList.add(userInfo.getLoginName());
//                        pushNotificationUtil.push(DriverNameList,"您的出车任务已被撤销",description,true);
                    }else{
                        nameList.add(userInfo.getLoginName());
                    }
                }
//                pushNotificationUtil.push(nameList,"您审批的用车申请已被撤销",description,true);
            }

            if (!status.equals("0")) {
                List<UserDeptInfo> list = vehicleReservationMapper.getUserInfoByRole("6");
                if (list == null || list.size() <= 0) {
                    resultData.setStatusCode(400);
                    resultData.setStatusRes("信息推送失败");
                    return resultData;
                }
                List<String> loginNameList = new ArrayList<>();
                for (UserDeptInfo userinfo : list){
                    loginNameList.add(userinfo.getLoginName());
                }
                nameList.clear();
                nameList.addAll(loginNameList);// 消息接收者的登录名（工号）

//                pushNotificationUtil.push(nameList,"用车申请已被撤销",description,false);
            }
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData confirmVehicleApplyInfo(String applyId, long driverId, String startTime, double startKilometers) {
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
        Integer i = vehicleReservationMapper.confirmVehicleApplyInfo(applyId,"1","0");

        Integer j = vehicleReservationMapper.updateDriverStatus(driverId, "2");
        String vehicleId = vehicleReservationMapper.getVehicleIdByVehicleApplyTable(applyId);
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
        if (i >= 1 && j >= 1 && k >= 1 && l>=1) {
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
    public ResultData confirmReturnVehicle(String applyId, String vehicleId, long driverId, String agent, String endTime, double oilFee,
                                           double maintenanceFee, double parkingFee, double forfeit, String paymentTime,
                                           String returnTime, String parkingPlace, double endKilometers) {
        resultData = new ResultData();
//        if (paymentTime.length()>0){
//            paymentTime = paymentTime+":00";
//        }
        if (endTime.length()>0){
            endTime = endTime+":00";
        }else {
            endTime = "";
        }
        returnTime = returnTime+":00";
        VehicleApplyInfo vehicleApplyInfo = vehicleReservationMapper.getVehicleApplyInfoByApplyId(applyId);
        if (vehicleApplyInfo == null) {
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
        Integer i = vehicleReservationMapper.updateFinishValue(applyId,"1");
        Integer n = vehicleReservationMapper.updateApplyEndTime(applyId,converter.convert(returnTime));
        Integer j = vehicleReservationMapper.updateVehicleStatus(vehicleId,"1");
        Integer k = vehicleReservationMapper.updateDriverStatus(driverId,"3");
        Integer l = vehicleReservationMapper.confirmReturnVehicle(vehicleId,converter.convert(returnTime),
                parkingPlace,endKilometers);
        Integer m = vehicleInformationManagementMapper.inputFeeInfoWhenReturnVehicle(applyId,vehicleId,agent,vehicleApplyInfo.getStartTime(),
                converter.convert(returnTime), oilFee,maintenanceFee,parkingFee,forfeit,null,parkingPlace,0,endKilometers);
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
            String description = "您好！单号为"+applyId+"的出行任务已完成！<br />申请单号："+applyId+"<br />申请人："+
                    vehicleApplyInfo.getApplicantName()+"<br />申请部门："+vehicleApplyInfo.getApplyDepartment()+
                    "<br />用车人："+vehicleApplyInfo.getVehicleUser()+"<br />陪同人员："+vehicleApplyInfo.getEntourage()+
                    "<br />车牌号："+vehicleApplyInfo.getVehicleId()+
                    "<br />司机："+vehicleApplyInfo.getDriverName()+"<br />出行时间："+timeToString(vehicleApplyInfo.getStartTime())+
                    "<br />归还时间："+returnTime+
                    "<br />目的地："+vehicleApplyInfo.getDestination()+"<br />用车人数："+vehicleApplyInfo.getPeopleNumber()+
                    "<br />事由："+vehicleApplyInfo.getCause();
//            pushNotificationUtil.push(nameList,"出行任务已完成，车辆已归还",description,true);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getAvailableVehicleBaseInfo(long userId) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        switch (role) {
            case "0":
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
        List<VehicleBaseInfo> vehicleBaseInfoList = vehicleReservationMapper.getAvailableVehicleBaseInfo("1");
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

    @Override
    @Transactional
    public ResultData getCommonData(String dataType) {
        resultData = new ResultData();
        List<CommonData> commonDataList = vehicleReservationMapper.getCommonData(dataType);
        if (commonDataList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            Map<String,Object> map = new HashMap<>();
            map.put("commonDataList",commonDataList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getAvailableDriverBaseInfo(long userId) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
        switch (role) {
            case "0":
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
        List<UserDeptInfo> userBaseInfoList = vehicleReservationMapper.getAvailableDriverBaseInfo("3");
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
            map.put("driverBaseInfoList", userBaseInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getApprovalHistory(long userId, String applyId) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
        if (role == null || role.length() == 0) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
            return resultData;
        }
        boolean flag = false;
            switch (role) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    flag = true;
                    break;
            }
        if (!flag) {
            resultData.setStatusCode(400);
            resultData.setStatusRes("该用户没有任何角色");
            return resultData;
        }
        List<ApprovalHistoryInfo> approvalHistoryInfoList = vehicleReservationMapper.getApprovalHistory(applyId);
        if (approvalHistoryInfoList != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<ApprovalHistoryInfoFormat> approvalHistoryInfoFormatList = new ArrayList<>();
            for (ApprovalHistoryInfo info : approvalHistoryInfoList) {
                String approverName = vehicleReservationMapper.getUserName(info.getApproverId());
                approvalHistoryInfoFormatList.add(new ApprovalHistoryInfoFormat(info.getApplyId(),info.getApproverId(),
                        approverName,info.getSuggestion(),DateToStringConverter.getStringDate(info.getApprovalTime()),
                        info.getType(),info.getStatus()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("approvalHistoryInfoList",approvalHistoryInfoFormatList);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getUserName(long userId) {
        resultData = new ResultData();
        String userName = vehicleReservationMapper.getUserName(userId);
        if (userName != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            Map<String,Object> map = new HashMap<>();
            map.put("userName",userName);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getUserRole(long userId) {
        resultData = new ResultData();
        String role = vehicleReservationMapper.getUserRole_new(userId);
        if (role != null || role.length() != 0) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            Map<String,Object> map = new HashMap<>();
            map.put("role",role);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }


    /**
     * 获取图片后缀
     *
     * @param originalFilename
     * @return
     */
    private String getPictureSuffix(String originalFilename) {
        int i = originalFilename.indexOf(".");
        String str = originalFilename.substring(i + 1, originalFilename.length());
        return str;
    }

//    @Override
//    @Transactional
//    public ResultData picUpload(MultipartFile multipartFile, HttpServletRequest request) {
//        resultData = new ResultData();
//        // 判断文件是否为空
//        try {
//            if (!multipartFile.isEmpty()) {
//                logger.info(">>>>>>>>>>开始上传图片>>>>>>>>>>");
//
//                // 获取图片后缀
//                String suffix = getPictureSuffix(multipartFile.getOriginalFilename());
//
//                // 重新生成图片名字
//                String newName = UUID.randomUUID().toString() + "." + suffix;
//
//                // 文件保存目录
//                String dir = request.getSession().getServletContext().getRealPath("/");
//
//                // 文件保存路径
//                String filePath = dir + newName;
//                logger.info(">>>>>>>>>>文件保存路径=" + filePath);
//
//                // 转存文件
//                multipartFile.transferTo(new File(filePath));
//
//                // 文件新的保存路径
//                String newName2 = UUID.randomUUID().toString() + ".jpg";
//                String newFilePath = dir + newName2;
//
//                // 压缩图片
//                Thumbnails.of(filePath).size(800, 800).outputQuality(1f).toFile(newFilePath);
//                // 上传文件
//                SFTPUtil sftp = new SFTPUtil(USERNAME, PASSWORD, IP, Integer.parseInt(PORT));
//                sftp.login();
//                File file = new File(newFilePath);
//                InputStream is = new FileInputStream(file);
//
//                sftp.upload(UPLOAD_DIRECTORY, newName2, is);
//                sftp.logout();
//                String url = URL_PREFIX + newName2;
//                logger.info(">>>>>>>>>>访问图片的url=" + url);
//                logger.info(">>>>>>>>>>结束上传图片>>>>>>>>>>");
//                resultData.setStatusCode(200);
//                resultData.setStatusRes("success");
//                Map<String,Object> map = new HashMap<>();
//                map.put("url",url);
//                resultData.setInfo(map);
//                return resultData;
//            } else {
//                logger.error(">>>>>>>>>>上传图片为空>>>>>>>>>>");
//                resultData.setStatusCode(400);
//                resultData.setStatusRes("上传图片为空");
//                return resultData;
//            }
//        } catch (Exception e) {
//            logger.error(">>>>>>>>>>上传图片出现异常", e.getMessage());
//            e.printStackTrace();
//            resultData.setStatusCode(500);
//            resultData.setStatusRes("上传图片出现异常"+e.getMessage());
//            return resultData;
//        }
//    }

    public String timeToString(Timestamp timestamp){
        String timeString = null;
        if (timestamp != null) {
            timeString = timestamp.toString().substring(0, timestamp.toString().indexOf("."));
        }else {
            timeString = "";
        }
        return timeString;
    }

    @Override
    @Transactional
    public ResultData getAllPIC() {
        resultData = new ResultData();
        List<UserDeptInfo> userInfo = vehicleReservationMapper.getUserInfoByRole("2");
        if (userInfo != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<UserDeptInfoFormat> UserDeptInfoFormat = new ArrayList<>();
            for (UserDeptInfo info : userInfo) {
                UserDeptInfoFormat.add(new UserDeptInfoFormat(info.getUserId(),
                        info.getName(),info.getDeptName(),info.getLoginName(),
                        DateToStringConverter.getStringDate(info.getDriverBirthday()),DateToStringConverter.getStringDate(info.getStartDrivingDate()),
                        info.getDrivingType(),info.getDriverPhoto(),info.getDriverStatus(),
                        info.getRole(),info.getRelation()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("UserInfoFormatList",UserDeptInfoFormat);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }

    @Override
    @Transactional
    public ResultData getAllApply() {
        resultData = new ResultData();
        List<UserDeptInfo> userInfo = vehicleReservationMapper.getUserInfoByRole("0");
        if (userInfo != null) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<UserDeptInfoFormat> UserDeptInfoFormat = new ArrayList<>();
            for (UserDeptInfo info : userInfo) {
                if (info.getRelation().length()==0||info.getRelation()=="") {
                    UserDeptInfoFormat.add(new UserDeptInfoFormat(info.getUserId(),
                            info.getName(),info.getDeptName(),info.getLoginName(),
                            DateToStringConverter.getStringDate(info.getDriverBirthday()),DateToStringConverter.getStringDate(info.getStartDrivingDate()),
                            info.getDrivingType(),info.getDriverPhoto(),info.getDriverStatus(),
                            info.getRole(),info.getRelation()));
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("UserInfoFormatList",UserDeptInfoFormat);
            resultData.setInfo(map);
        } else {
            resultData.setStatusCode(400);
            resultData.setStatusRes("fail");
        }
        return resultData;
    }
    @Override
    @Transactional
    public ResultData bindAppyAndPIC(String PICLoginName,JSONArray ApplyList) {
        resultData = new ResultData();
        List<String> ApplyLoginNameList = JSONObject.parseArray(ApplyList.toJSONString(),String.class);
        if (ApplyList == null || ApplyList.size() <= 0) {
            //已绑定该负责人的申请人
            List<UserDeptInfo> userinfoList = vehicleReservationMapper.getApplyByPIC(PICLoginName);
            List<String> loginNameList = new ArrayList<>();
            for (UserDeptInfo userinfo:userinfoList){
                loginNameList.add(userinfo.getLoginName());
            }
            if (loginNameList != null || loginNameList.size() > 0){
                Integer flag = vehicleReservationMapper.updateApplyBindPIC("",loginNameList);
                if (flag < 1) {
                    resultData.setStatusCode(400);
                    resultData.setStatusRes("fail");
                    return resultData;
                }
            }
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            return resultData;
        }else{
            //已绑定该负责人的申请人
            List<UserDeptInfo> userinfoList = vehicleReservationMapper.getApplyByPIC(PICLoginName);
            List<String> loginNameList = new ArrayList<>();
            for (UserDeptInfo userinfo:userinfoList){
                loginNameList.add(userinfo.getLoginName());
            }
            if (loginNameList.size() > 0){
                Integer flag = vehicleReservationMapper.updateApplyBindPIC("",loginNameList);
                if (flag < 1) {
                    resultData.setStatusCode(400);
                    resultData.setStatusRes("fail");
                    return resultData;
                }
            }
            Integer flag = vehicleReservationMapper.updateApplyBindPIC(PICLoginName,ApplyLoginNameList);
            if (flag < 1) {
                resultData.setStatusCode(400);
                resultData.setStatusRes("fail");
                return resultData;
            }
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            return resultData;
        }
    }

    @Override
    @Transactional
    public ResultData ApplyByPIC(String PICLoginName) {
        resultData = new ResultData();
        if (PICLoginName == null||PICLoginName.length()<=0){
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            Map<String,Object> map = new HashMap<>();
            map.put("UserInfoFormatList",new ArrayList<>());
            resultData.setInfo(map);
            return resultData;
        }
        List<UserDeptInfo> userinfo = vehicleReservationMapper.getApplyByPIC(PICLoginName);
        if (userinfo == null || userinfo.size() <= 0) {
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            Map<String,Object> map = new HashMap<>();
            map.put("UserInfoFormatList",new ArrayList<>());
            resultData.setInfo(map);
            return resultData;
        }else{
            resultData.setStatusCode(200);
            resultData.setStatusRes("success");
            List<UserDeptInfoFormat> UserDeptInfoFormat = new ArrayList<>();
            for (UserDeptInfo info : userinfo) {
                UserDeptInfoFormat.add(new UserDeptInfoFormat(info.getUserId(),
                        info.getName(),info.getDeptName(),info.getLoginName(),
                        DateToStringConverter.getStringDate(info.getDriverBirthday()),DateToStringConverter.getStringDate(info.getStartDrivingDate()),
                        info.getDrivingType(),info.getDriverPhoto(),info.getDriverStatus(),
                        info.getRole(),info.getRelation()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("UserInfoFormatList",UserDeptInfoFormat);
            resultData.setInfo(map);
        }
        return resultData;
    }
}



