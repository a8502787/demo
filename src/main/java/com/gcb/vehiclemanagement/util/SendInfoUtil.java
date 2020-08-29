package com.gcb.vehiclemanagement.util;

//import com.minxing.client.app.AppAccount;
//import com.minxing.client.app.OcuMessageSendResult;
//import com.minxing.client.ocu.ArticleMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SendInfoUtil {
//
//	private Logger logger = LoggerFactory.getLogger(SendInfoUtil.class);
//
//	@Value("${sandbox.acctoken}")
//	private String accToken;   // 接入端的token，敏行平台新建接入端可得
//	@Value("${sandbox.domain}")
//	private String domain;     // 测试环境的IP，端口
//	@Value("${sandbox.networkid}")
//	private String networkId;  // 固定为2
//	@Value("${sandbox.ocuid}")
//	private String ocuId;      // 新建公众号平台输入的id
//	@Value("${sandbox.ocusecret}")
//	private String ocuSecret;  // 新建公众号平台生成的ocuSecret
//
//	/**
//	 * 调sdk的接口推送消息
//	 * @param names
//	 * @param articleMessage
//	 */
//	public void sendOcuMessageToUsers(String[] names, ArticleMessage articleMessage) {
//		logger.info("+++++domain: "+domain+"ocuId: "+ocuId);
//		try {
//			AppAccount account = AppAccount.loginByAccessToken(domain, accToken); // 连接
//			// 推送
//			OcuMessageSendResult ocuMessageSendResult = account.sendOcuMessageToUsers(networkId, names,articleMessage, ocuId, ocuSecret);
//			logger.info(">>>domain: "+domain+" ocuId: "+ocuId+" messageId: "+ocuMessageSendResult.getMessageId());
//		} catch (Exception e) {
//			logger.error("send ocuMessage error", e);
//			e.printStackTrace();
//		}
//	}
//}