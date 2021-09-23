package me.zhengjie.modules.smsServer.util;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.smsServer.domain.MoObject;
import me.zhengjie.modules.smsServer.domain.UserInformation;
import me.zhengjie.utils.SpringContextHolder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 大汉短信发送接口
 */
@Slf4j
public class DaHanSmsUtils {
	static UserInformation userInformation;

	private static final String SIGN = "【语音包订购】";
	private static final String URL = "http://www.dh3t.com/json/sms/Submit";
	private static final String BATCH_URL="http://www.dh3t.com/json/sms/BatchSubmit";
	private static final String MO_URL = "http://www.dh3t.com/json/sms/Deliver";
	private static final String REPORT_URL = "http://www.dh3t.com/json/sms/Report";

	/**
	 * 发送不同号码多个号码
	 * @param phones 手机号
	 * @param content  发送内容
	 * @param sign 签名
	 * @return
	 */
	public static String batchSubmit(List<String> phones, String content, String sign) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());// 日期
			List<Map<String,String>> paramList=new ArrayList<>();
			JSONObject js=new JSONObject();
			js.put("account",userInformation.getAccount());
			js.put("password", Encryption.md5Digest(userInformation.getPassword()));
			for (String phone:phones) {
				int num = 10000 + new Random().nextInt(900);
				String msgid = Encryption.md5Digest(date+num);// 下游订单号
				Map<String,String> data=new HashMap<>();
				data.put("msgid", msgid);
				data.put("phones", phone);
				data.put("content", content.replace("{}",phone.substring(7)));
				data.put("sign", sign);
				data.put("subcode", "");
				data.put("sendtime", "");
				paramList.add(data);
			}
			js.put("data",paramList);
			log.info("大汉三通接口短信---发送不同号码不同内容短信请求报文："+BATCH_URL+"="+js.toJSONString());
			String result = HttpRequest.post(BATCH_URL)
					.body(js.toJSONString())
					.execute().body();
			log.info("大汉三通接口短信---发送不同号码不同内容短信返回报文："+result);
			return result;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送下行  发送相同内容多个号码
	 * @param phone  手机号
	 * @param content 发送内容
	 * @param sign 签名
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String submitOrder(String phone, String content, String sign) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());// 日期
			int num = 10000 + new Random().nextInt(900);
			String msgid = Encryption.md5Digest(date+num);// 下游订单号
			JSONObject js = new JSONObject();
			js.put("account", userInformation.getAccount());
			js.put("password", Encryption.md5Digest(userInformation.getPassword()));
			js.put("msgid", msgid);
			js.put("phones", phone);
			js.put("content", content);
			js.put("sign", sign);
			js.put("subcode", "");
			js.put("sendtime", "");
			log.info("大汉三通接口短信---发送短信请求报文："+URL+"="+js.toJSONString());
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			PostMethod postMethod = new PostMethod(URL);
			postMethod.setRequestBody(js.toJSONString());
			client.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			log.info("大汉三通接口短信---发送短信返回报文："+result);
			return result;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送不同号码多个号码
	 * @return
	 */
	public static String batchSubmit(List<SmsParam> smsParams) {
		try {
			if (userInformation==null){
				UserInformation bean = SpringContextHolder.getBean(UserInformation.class);
				userInformation=bean;
			}
			JSONObject js=new JSONObject();
			js.put("account", userInformation.getAccount());
			js.put("password", Encryption.md5Digest(userInformation.getPassword()));
			js.put("data",smsParams);
			log.info("大汉三通接口短信---发送不同号码不同内容短信请求报文："+BATCH_URL+"="+js.toJSONString());
			String result = HttpRequest.post(BATCH_URL)
					.body(js.toJSONString())
					.execute().body();
			log.info("大汉三通接口短信---发送不同号码不同内容短信返回报文："+result);
			return result;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取上行
	 * @return
	 */

	public static List<MoObject> multiMo() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());// 日期
			int num = 10000 + new Random().nextInt(900);
			String msgid = Encryption.md5Digest(date+num);// 下游订单号
			JSONObject js = new JSONObject();
			js.put("account", userInformation.getAccount());
			js.put("password", Encryption.md5Digest(userInformation.getPassword()));
			log.info("大汉三通接口短信---发送短信请求报文："+MO_URL+"="+js.toJSONString());
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			PostMethod postMethod = new PostMethod(MO_URL);
			postMethod.setRequestBody(js.toJSONString());
			client.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			String delivers = JSONObject.parseObject(result).getString("delivers");
			log.info("大汉三通接口短信---发送短信返回报文："+delivers);
			if(null != delivers && !"".equals(delivers)){
				JSONArray array=JSONArray.parseArray(delivers);
				List<MoObject> list2 = array.toJavaList(MoObject.class);
				return list2;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 获取大汉推送下行状态
	 * @return
	 */

	public static List<MoObject> report() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = sdf.format(new Date());// 日期
			int num = 10000 + new Random().nextInt(900);
			String msgid = Encryption.md5Digest(date+num);// 下游订单号
			JSONObject js = new JSONObject();
			js.put("account", userInformation.getAccount());
			js.put("password", Encryption.md5Digest(userInformation.getPassword()));
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			PostMethod postMethod = new PostMethod(REPORT_URL);
			postMethod.setRequestBody(js.toJSONString());
			client.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			String resultStr = JSONObject.parseObject(result).getString("result");	//
			if(null != resultStr && "0".equals(resultStr)){
				log.info("大汉三通接口短信---发送短信请求报文："+REPORT_URL+"="+js.toJSONString());
				String reports = JSONObject.parseObject(result).getString("reports");
				log.info("大汉三通接口短信---发送短信返回报文："+result);
				if(null != reports && !"".equals(reports)){
					JSONArray array = JSONArray.parseArray(reports);
					List<MoObject> list2 = array.toJavaList( MoObject.class);
					return list2;
				}
			} else {
				System.out.println("无下行状态待获取！");
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		List<String> phones=new ArrayList<>();
		String a="尾号{},开启5G生活,http://aisulin.cn/activity/flows/";
		int size=a.length()-a.replaceAll("\\{}","").length();
		System.out.println(size);
	}
}
