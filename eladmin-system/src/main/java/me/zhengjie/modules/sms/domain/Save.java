package me.zhengjie.modules.sms.domain;


public class Save {
    private String appid; //appkey
    private String sign;  //签名id
    private String phone;  //发送手机号
    private String content;  //发送内容
    private String type;  //发送类型 0  单发  1 群发
    private Soap soap;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Soap getSoap() {
            if (soap==null) {
                soap = new Soap();
            }
            return soap;
    }

    public void setSoap(Soap soap) {
        this.soap = soap;
    }

    public Save(String appid, String sign, String phone, String content, String type) {
        this.appid = appid;
        this.sign = sign;
        this.phone = phone;
        this.content = content;
        this.type = type;
    }

    public Save() {
    }

    @Override
    public String toString() {
        return "Save{" +
                "appid='" + appid + '\'' +
                ", sign='" + sign + '\'' +
                ", phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
