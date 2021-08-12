package me.zhengjie.modules.smsServer.util;

import lombok.Data;

/**
 * @author tanzs
 * @date 2021/3/5
 * @desc  大汉发送不同内容不同号码入参封装
 */
@Data
public class SmsParam {

    private String msgid;

    private String phones;

    private String content;

    private String sign;

    private String subcode="";

    private String sendtime="";
}
