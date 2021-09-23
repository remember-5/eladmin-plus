package me.zhengjie.modules.smsServer.util;


import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 *九鸽短信发送
 */
@Slf4j
public class JiuGeSmsUtils {

    private static final String OPERATORID = "shsl2020";

    private static final String PASSWORD = "slwl#2020";

    private static final String POST_URL ="http://39.104.175.91:5001/ws/sendSms";

    private static final String DELIVERY_URL ="http://39.104.175.91:5001/ws/getDelivery";

    private static  final String GET_BALANCE_URL ="http://39.104.175.91:5001/ws/getBalance";

    /**
     *发送下行短信
     * @param mobiles 手机号
     * @param message 短信内容
     * @param sign 短信签名
     * @return
     */
    public static String sendSms(String mobiles,String message,String sign){
        Map<String,Object> map=new HashMap<>();
        map.put("operatorId",OPERATORID);
        map.put("password",PASSWORD);
        map.put("sendMethod","0");
        map.put("mobiles",mobiles);
        map.put("message",sign+message);
        log.info("九鸽接口短信---发送短信请求报文："+ POST_URL +"="+map);
        String post = HttpUtil.post(POST_URL, map);
        log.info("九鸽接口短信---发送短信返回报文："+post);
        return post;
    }
    /**
    /* @param mobiles 手机号
     * @param message 短信内容
     * @param wapPushUrl 发送WapPush地址
     * @return
     *   SendMethod=101时,调用sendSmsMethod方法
    * */
    public static String sendSmsMethod(String mobiles,String message,String wapPushUrl){
        Map<String,Object> map=new HashMap<>();
        map.put("operatorId",OPERATORID);
        map.put("password",PASSWORD);
        map.put("sendMethod","101");
        map.put("mobiles",mobiles);
        map.put("message",message);
        map.put("wapPushUrl",wapPushUrl);
        String post = HttpUtil.post(POST_URL, map);
        return post;
    }

    /**
     * 查询下行短信发送状态
     *@param requestIdentifier 请求标示
     *@return
     */
    public static String getDelivery(String requestIdentifier){
        Map<String,Object> map=new HashMap<>();
        map.put("operatorId",OPERATORID);
        map.put("password",PASSWORD);
        map.put("requestIdentifier",requestIdentifier);
        System.out.println("入参："+map);
        String post = HttpUtil.post(DELIVERY_URL, map);
        return post;
    }

    /**
     * 接收短信
     */
    public static String getReceived(){
        Map<String,Object> map=new HashMap<>();
        map.put("operatorId",OPERATORID);
        map.put("password",PASSWORD);
        //map.put("channelId",channelId);
        System.out.println("入参："+map);
        String post = HttpUtil.post(GET_BALANCE_URL, map);
        return post;
    }

}
