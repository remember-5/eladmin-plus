package me.zhengjie.modules.smsServer.domain;

import lombok.Data;

@Data
public class Information {
    private String appid; //appkey
    private String secret; //密钥 
    private String sign;  //签名id
    private String phone;  //发送手机号
    private String content;  //发送内容

}
