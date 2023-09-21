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
package com.remember5.system.modules.cms.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.remember5.system.modules.column.domain.CmsColumn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author zhangenrong
 * @website https://el-admin.vip
 * @description /
 * @date 2021-03-01
 **/
@Entity
@Data
@Table(name = "t_cms")
public class Cms implements Serializable {

    @Id
    @Column(name = "cms_id")
    @Schema(description = "主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_id")
    @Schema(description = "代理商id")
    private Long agentId;

    @OneToOne
    @JoinColumn(name = "column_id")
    @Schema(description = "栏目")
    private CmsColumn column;

    @Column(name = "title")
    @Schema(description = "内容标题")
    private String title;

    @Column(name = "author")
    @Schema(description = "作者")
    private String author;

    @Column(name = "content")
    @Schema(description = "发布内容")
    private String content;

    @Column(name = "cms_status")
    @Schema(description = "文章状态")
    private Integer cmsStatus;

    @Column(name = "audit_person")
    @Schema(description = "审核人")
    private String auditPerson;

    @Column(name = "audit_status")
    @Schema(description = "审核状态")
    private Integer auditStatus;

    @Column(name = "audit_time")
    @Schema(description = "审核时间")
    private Timestamp auditTime;

    @Column(name = "audit_proposal")
    @Schema(description = "审核意见")
    private String auditProposal;

    @Column(name = "publish_time")
    @Schema(description = "发布时间")
    private Timestamp publishTime;

    @Column(name = "create_time")
    @CreationTimestamp
    @Schema(description = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @Schema(description = "修改时间")
    private Timestamp updateTime;

    @Column(name = "is_deleted")
    @Schema(description = "1 表示删除，0 表示未删除")
    private Boolean isDeleted;

    @Column(name = "annex_url")
    @Schema(description = "附件url")
    private String annexUrl;

    public void copy(Cms source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
