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
package me.zhengjie.modules.cms.service.dto;

import lombok.Data;
import me.zhengjie.modules.column.domain.CmsColumn;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author zhangenrong
 * @website https://el-admin.vip
 * @description /
 * @date 2021-03-01
 **/
@Data
public class CmsDto implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 栏目
     */
    private Long columnId;
    private CmsColumn column;

    /**
     * 内容标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 文章状态
     */
    private Integer cmsStatus;

    /**
     * 审核人
     */
    private String auditPerson;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 审核时间
     */
    private Timestamp auditTime;

    /**
     * 审核意见
     */
    private String auditProposal;

    /**
     * 发布时间
     */
    private Timestamp publishTime;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 修改时间
     */
    private Timestamp updateTime;

    /**
     * 1 表示删除，0 表示未删除
     */
    private Boolean isDeleted;

    /**
     * 附件url
     */
    private String annexUrl;

    /**
     * 备用字段
     */
    private String by2;

    /**
     * 备用字段
     */
    private String by3;

    /**
     * 备用字段
     */
    private String by4;

    /**
     * 备用字段
     */
    private String by5;

}
