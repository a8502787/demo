package com.gcb.vehiclemanagement.util;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Component
//public class PushNotificationUtil {
//
//    @Autowired
//    private MyAccount myAccount;
//
//    public void push(List<String> nameList, String title, String description,boolean clickFlag) {
//        String pageName = "index"; // 要调起应用的一个页面的名字，由前端人员提供。如果不指定，默认调应用的主页面
//        String appId = "vehicle"; // 应用id，即点击推送消息要调起的应用的id，新建应用的时候就会确定
//        if (clickFlag) {
//            myAccount.sendOcuMessage(nameList, title, description, pageName, appId);
//        } else {
//            myAccount.sendOcuMessage(nameList, title, description, pageName, null);
//        }
//    }
//}
