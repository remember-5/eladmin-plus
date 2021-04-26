package me.zhengjie.modules.smsServer.domain;

import lombok.Data;

@Data
public class Save {
    private String appid; //appkey
    private String sign;  //签名id
    private String phone;  //发送手机号
    private String content;  //发送内容
    private String type;  //发送类型 0  单发  1 群发
    private Soap soap;
    public Soap getSoap() {
        if (soap==null) {
            soap = new Soap();
        }
        return soap;
    }

    public void setSoap(Soap soap) {
        this.soap = soap;
    }

    public void setSoap() {
    }
}
