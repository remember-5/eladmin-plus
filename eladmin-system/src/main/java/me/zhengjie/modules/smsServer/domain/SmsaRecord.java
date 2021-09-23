/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.smsServer.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wh
 * @website https://el-admin.vip
 * @description /
 * @date 2021-04-19
 **/
@Entity
@Data
@Table(name = "sms_journal")
public class SmsaRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "entry_name")
    @ApiModelProperty(value = "项目名称")
    private String entryName;

    @Column(name = "phone")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "send_time")
    @ApiModelProperty(value = "发送时间")
    private Timestamp sendTime;

    @Column(name = "send_status")
    @ApiModelProperty(value = "发送状态 0 待发送  1 已发送  2 发送失败")
    private String sendStatus;

    @Column(name = "uid")
    @ApiModelProperty(value = "uid")
    private String uid;

    @Column(name = "spreat1")
    @ApiModelProperty(value = "spreat1")
    private String spreat1;

    @Column(name = "spreat2")
    @ApiModelProperty(value = "spreat2")
    private String spreat2;

    @Column(name = "spreat3")
    @ApiModelProperty(value = "spreat3")
    private String spreat3;

    @Column(name = "appid")
    @ApiModelProperty(value = "appid")
    private String appid;

    @Column(name = "content")
    @ApiModelProperty(value = "content")
    private String content;

    @Column(name = "sign")
    @ApiModelProperty(value = "sign")
    private String sign;

    public void copy(SmsaRecord source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
