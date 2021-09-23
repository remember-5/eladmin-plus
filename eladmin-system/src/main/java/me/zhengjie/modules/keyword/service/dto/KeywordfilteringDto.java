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
package me.zhengjie.modules.keyword.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author tianhh
 * @website https://el-admin.vip
 * @description /
 * @date 2021-04-21
 **/
@Data
public class KeywordfilteringDto implements Serializable {

    private Long id;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 修改时间
     */
    private Timestamp updateTime;

    /**
     * 是否逻辑删除
     */
    private Integer isDel;

    /**
     * 过滤表的名字
     */
    private String name;

    /**
     * 过滤表的表述
     */
    private String blacklistDescribe;

    /**
     * 上传文件的url
     */
    private String fileUrl;

    /**
     * 更新模式
     */
    private String updateType;

    /**
     * 备用
     */
    private String by1;

    /**
     * 备用
     */
    private String by2;

    /**
     * 备用
     */
    private String by3;

    /**
     * 备用
     */
    private String by4;

    /**
     * 备用
     */
    private String by5;

    /**
     * 备用
     */
    private String by6;

    /**
     * 备用
     */
    private String by7;

    /**
     * 备用
     */
    private String by8;

    /**
     * 备用
     */
    private String by9;

    /**
     * 备用
     */
    private String by10;
}
