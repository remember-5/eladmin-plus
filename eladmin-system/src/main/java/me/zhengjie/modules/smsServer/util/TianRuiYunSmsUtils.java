package me.zhengjie.modules.smsServer.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.smsServer.domain.MoObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 天瑞云短信发送接口
 */
@Slf4j
public class TianRuiYunSmsUtils {
    private static final String ACCESSKEY = "ys7ymqi3pmXacqCW";
    private static final String SECRET = "R1i4xRK8cbQfIChrNxDz1BytxIPPlUMH";
    private static final String URL = "http://api.1cloudsp.com/api/v2/send";
    private static final String URLUPPER = "http://api.1cloudsp.com/report/up";

    /**
     * 发送下行，不同号码，相同相同短信内容
     *
     * @param sign    短信签名
     * @param mobile  手机号
     * @param content 短信内容
     * @return
     */
    public static String TianRuidown(String mobile, String content, String sign) {
        Map object = new HashMap<>();
        object.put("accesskey", ACCESSKEY);
        object.put("secret", SECRET);
        object.put("sign", sign);
        object.put("mobile", mobile);
        object.put("content", content);
        object.put("scheduleSendTime", "");
        log.info("天瑞云接口短信---发送短信请求报文：" + URL + "=" + object.toString());
        String result = HttpRequest.post(URL).header("content-type", "application/x-www-form-urlencoded").form(object).execute().body();
        log.info("天瑞云接口短信---发送短信返回报文：" + result);
        return result;
    }

    /**
     * 发送下行，不同号码，不同短信
     *
     * @param sign    短信签名
     * @param mobile  不同手机号
     * @param content 不同短信内容
     * @return
     */
    public static String TianRuidown(List<String> mobile, String content, String sign) {
        Map object = new HashMap<>();
        object.put("accesskey", ACCESSKEY);
        object.put("secret", SECRET);
        object.put("sign", sign);
        JSONObject jsonObject = new JSONObject();
        for (String phone : mobile) {
            String Wphone = phone.substring(7);
            jsonObject.put(phone, content.replace("{}", Wphone));
        }
        object.put("data", jsonObject);
        object.put("scheduleSendTime", "");
        log.info("天瑞云接口短信---发送短信请求报文：" + URL + "=" + object);
        String result = HttpRequest.post(URL).header("content-type", "application/x-www-form-urlencoded").form(object).execute().body();
        log.info("天瑞云接口短信---发送短信返回报文：" + result);
        return result;
    }

    /**
     * 短信群发接口
     *
     * @param smsParams 封装的jsonObject 里面含两个参数 手机号,内容
     * @param sign      签名
     * @return
     */
    public static String TianRuidownpublic(JSONObject smsParams, String sign) {
        JSONObject object = new JSONObject();
        object.put("accesskey", ACCESSKEY);
        object.put("secret", SECRET);
        object.put("sign", sign);
        object.put("data", smsParams);
        object.put("scheduleSendTime", "");
        log.info("天瑞云接口短信---发送短信请求报文：" + URL + "=" + object.toJSONString());
        String result = HttpRequest.post(URL).header("content-type", "application/x-www-form-urlencoded").form(object).execute().body();
        log.info("天瑞云接口短信---发送短信返回报文：" + result);
        return result;
    }


    /**
     * 上行回复
     *
     * @return
     */
    public static List<MoObject> TianRuiupstream() {
        try {
            JSONObject object = new JSONObject();
            object.put("accesskey", ACCESSKEY);
            object.put("secret", SECRET);
            log.info("天瑞云接口短信---上行短信请求报文：" + URLUPPER + "=" + object.toJSONString());
            String result = HttpRequest.post(URLUPPER).header("content-type", "application/x-www-form-urlencoded").form(object).execute().body();
            log.info("天瑞云接口短信---上行短信返回报文：" + result);
            String upstream = JSONObject.parseObject(result).getString("data");
            if (null != upstream && "".equals(upstream)) {
                JSONArray jsonArray = JSONArray.parseArray(upstream);


                List<MoObject> moObjects = jsonArray.toJavaList(MoObject.class);
                return moObjects;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
