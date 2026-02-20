package com.nh.smart.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @description (描述)短信相关操作
 * @file_name SMSUtil.java
 * @version 1.0
 * @author cloud
 */
public class SMSUtils
{
	//产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	//产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	// 从环境变量或 JVM 参数读取，避免把密钥写入代码仓库
	private static final String accessKeyId = getConfig("ALIYUN_SMS_ACCESS_KEY_ID");
	private static final String accessKeySecret = getConfig("ALIYUN_SMS_ACCESS_KEY_SECRET");

	private static String getConfig(String key) {
		String v = System.getenv(key);
		if (v == null || v.trim().isEmpty()) {
			v = System.getProperty(key);
		}
		return v == null ? "" : v.trim();
	}

	//    @RequestMapping("getSsm")
//    @ResponseBody
	public static String getSsm(String phone,String code) {
		if (accessKeyId.isEmpty() || accessKeySecret.isEmpty()) {
			throw new IllegalStateException("Missing SMS credentials: set ALIYUN_SMS_ACCESS_KEY_ID and ALIYUN_SMS_ACCESS_KEY_SECRET");
		}

		//可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		//初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			e1.printStackTrace();
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);

		//随机生成六位验证码
		//code = (int)((Math.random()*9+1)*100000);
		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号
		request.setPhoneNumbers(phone);
		//必填:短信签名-可在短信控制台中找到，你在签名管理里的内容
		request.setSignName("汇立天下");
		//必填:短信模板-可在短信控制台中找到，你模板管理里的模板编号
		request.setTemplateCode("SMS_186360728");
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"code\":\""+code+"\"}");

		//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");

		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");

		//hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		//获取发送状态、判断是否成功
		String cod = sendSmsResponse.getCode();
		if(!cod.equals("OK")){
			return   "";
		}
		return String.valueOf(code);
	}


	// 验证码字符集
		private static final String[] words = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
			};
		// 字符数量
		private static final int SIZE = 6;

		/**
		 * 生成随机验证码及图片
		 */
		public static String createNum() {
			List <String>list = new ArrayList<String>();
			// 5.画随机字符
			Random ran = new Random();
			for (int i = 0; i <SIZE; i++) {
				// 取随机字符索引
				int n = ran.nextInt(words.length);
				list.add(words[n]);
			}
			String yang = list.get(0)+list.get(1)+list.get(2)+list.get(3);
			return yang;
		}


		public static void main(String[] args) throws IOException {

			System.out.println(createNum());
		}




}
