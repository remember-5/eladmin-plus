/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.sms.utils;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.remember5.sms.properties.DaHanProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangjiahao
 * @date 2023/5/10 10:53
 */
@Slf4j
@RequiredArgsConstructor
public class DaHanUtils {

    private final DaHanProperties daHanProperties;

    /**
     * 发送短信接口
     */
    private static final String URL = "http://www.dh3t.com/json/sms/Submit";
    /**
     * 批量发送短信
     */
    private static final String BATCH_URL = "http://www.dh3t.com/json/sms/BatchSubmit";
    /**
     * 发出地址
     */
    private static final String MO_URL = "http://www.dh3t.com/json/sms/Deliver";
    /**
     * 报告地址
     */
    private static final String REPORT_URL = "http://www.dh3t.com/json/sms/Report";
    /**
     * 日期格式
     */
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");


    /**
     * 发送短信
     *
     * @param phone   手机号
     * @param content 内容
     * @param sign    签名
     * @return /
     */
    public String submit(String phone, String content, String sign) {
        try {
            String date = SIMPLE_DATE_FORMAT.format(new Date());
            int num = 10000 + new Random().nextInt(900);
            // 下游订单号
            String msgid = Encryption.md5Digest(date + num);
            JSONObject js = new JSONObject();
            js.put("account", daHanProperties.getAccount());
            js.put("password", Encryption.md5Digest(daHanProperties.getPassword()));
            js.put("msgid", msgid);
            js.put("phones", phone);
            js.put("content", content);
            js.put("sign", sign);
            js.put("subcode", "");
            js.put("sendtime", "");
            log.info("大汉三通接口短信---发送短信请求报文：{} = {}", URL, js.toString());
            final String body = HttpRequest.post(URL).body(js.toString()).execute().body();
            log.info("大汉三通接口短信---发送短信返回报文：{}", body);
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量发送不同号码
     *
     * @param phones  手机号
     * @param content 内容
     * @param sign    签名
     * @return /
     */
    public String batchSubmit(List<String> phones, String content, String sign) {
        try {
            // 日期
            String date = SIMPLE_DATE_FORMAT.format(new Date());
            List<Map<String, String>> paramList = new ArrayList<>();
            JSONObject js = new JSONObject();
            js.put("account", daHanProperties.getAccount());
            js.put("password", Encryption.md5Digest(daHanProperties.getPassword()));
            for (String phone : phones) {
                int num = 10000 + new Random().nextInt(900);
                // 下游订单号
                String msgid = Encryption.md5Digest(date + num);
                Map<String, String> data = new HashMap<>();
                data.put("msgid", msgid);
                data.put("phones", phone);
                data.put("content", content.replace("{}", phone.substring(7)));
                data.put("sign", sign);
                data.put("subcode", "");
                data.put("sendtime", "");
                paramList.add(data);
            }
            js.put("data", paramList);
            log.info("大汉三通接口短信---发送不同号码不同内容短信请求报文：{} = {}", BATCH_URL, js.toString());
            String result = HttpRequest.post(BATCH_URL)
                    .body(js.toString())
                    .execute().body();
            log.info("大汉三通接口短信---发送不同号码不同内容短信返回报文：{}", result);
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
     *
     * @return
     */
    public JSONArray MultiMo() {
        try {
            String date = SIMPLE_DATE_FORMAT.format(new Date());
            int num = 10000 + new Random().nextInt(900);
            // 下游订单号
            String msgid = Encryption.md5Digest(date + num);
            JSONObject js = new JSONObject();
            js.put("account", daHanProperties.getAccount());
            js.put("password", Encryption.md5Digest(daHanProperties.getPassword()));
            log.info("大汉三通接口短信---发送短信请求报文：" + MO_URL + "=" + js.toString());
            final String body = HttpRequest.post(MO_URL).body(js.toString()).execute().body();
            String delivers = JSON.parseObject(body).getString("delivers");
            log.info("大汉三通接口短信---发送短信返回报文：{}", delivers);
            if (null != delivers && !"".equals(delivers)) {
                return JSONArray.parseArray(delivers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取大汉推送下行状态
     *
     * @return
     */
    public JSONArray report() {
        try {
            String date = SIMPLE_DATE_FORMAT.format(new Date());
            int num = 10000 + new Random().nextInt(900);
            // 下游订单号
            String msgid = Encryption.md5Digest(date + num);
            JSONObject js = new JSONObject();
            js.put("account", daHanProperties.getAccount());
            js.put("password", Encryption.md5Digest(daHanProperties.getPassword()));
            final String body = HttpRequest.post(REPORT_URL).body(js.toString()).execute().body();
            String resultStr = JSON.parseObject(body).getString("result");
            if (null != resultStr && "0".equals(resultStr)) {
                log.info("大汉三通接口短信---发送短信请求报文：{} = {}", REPORT_URL, js.toJSONString());
                String reports = JSONObject.parseObject(resultStr).getString("reports");
                log.info("大汉三通接口短信---发送短信返回报文：{}", resultStr);
                if (null != reports && !"".equals(reports)) {
                    return JSONArray.parseArray(reports);
                }
            } else {
                System.out.println("无下行状态待获取！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
