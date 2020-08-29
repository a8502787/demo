package com.gcb.vehiclemanagement.util;

//import com.minxing.client.model.MxException;
//import com.minxing.client.ocu.Article;
//import com.minxing.client.ocu.ArticleMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Component
//public class MyAccount {
//
//    private Logger logger = LoggerFactory.getLogger(MyAccount.class);
//
//    @Autowired
//    private SendInfoUtil sendInfoUtil;
//
//    /**
//     * 封装数据，推送消息
//     * @param nameList 消息接收者的登录名（工号）集合
//     * @param title 标题
//     * @param description 消息的具体内容
//     * @param pageName 应用的一个页面名称
//     * @param appId  应用id
//     */
//    public void sendOcuMessage(List<String> nameList, String title, String description, String pageName, String appId) {
//        try {
//            ArticleMessage articleMessage = new ArticleMessage();
//            StringBuilder appUrlStr = new StringBuilder();
//            String appUrl = (appUrlStr.append("launchApp://" + appId).append("$$#/" + pageName)).toString();
//            logger.info("the appUrl is ==" + appUrl);
//            logger.info("the description is ====" + description);
//            articleMessage.addArticle(new Article(title, description, null, appUrl, appUrl));
//            String[] names = new String[nameList.size()];
//            for (int i = 0; i < nameList.size(); i++) {
//                names[i] = nameList.get(i);
//            }
//            sendInfoUtil.sendOcuMessageToUsers(names, articleMessage);
//        }  catch (MxException e) {
//            logger.error("send ocuMessage error", e);
//            e.printStackTrace();
//        } catch (Exception e) {
//            logger.error("send ocuMessage error", e);
//            e.printStackTrace();
//        }
//    }
//}
