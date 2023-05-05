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

import lombok.Data;

import java.util.Date;

/**
 * 大汉接口返回信息
 *
 * @author wangjiahao
 * @date 2023/5/10 13:56
 */
@Data
public class MoProject {
    /**
     * SP编号
     */
    private String spId;

    /**
     * SP密码
     */
    private String spPassword;

    /**
     * SP服务代码
     */
    protected String spServiceCode;

    /**
     * 手机号码所属运营商代码
     */
    //protected String carrierCode;

    /**
     * 源地址（手机号码）
     */
    protected String sourceAddr;

    /**
     * 目的地址（长号码）
     */
    protected String destinationAddr;

    /**
     * 上行消息内容
     */
    protected String moContent;

    /**
     * SMPP协议的data_coding字段(消息编码格式)
     */
    protected Integer dataCoding;

    /**
     * SMPP协议的esm_class字段
     */
    protected Integer esmClass;

    /**
     * SMPP协议的protocol_id字段
     */
    protected Integer protocolId;

    /**
     * 上行接收时间
     */
    protected Date receiveTime;

    /**
     * SMS上行消息编号（短信平台返回的唯一序列号）
     */
    protected Long mlinkMoId;

    /**
     * Mlink接收运营商上行的时间
     */
    protected String mlinkMoTime;
}
