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
package me.zhengjie.modules.cms.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.modules.column.domain.CmsColumn;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @website https://el-admin.vip
* @description /
* @author zhangenrong
* @date 2021-03-01
**/
@Entity
@Data
@Table(name="t_cms")
public class Cms implements Serializable {

    @Id
    @Column(name = "cms_id")
    @ApiModelProperty(value = "主键")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_id")
    @ApiModelProperty(value = "代理商id")
    private Long agentId;

    @OneToOne
    @JoinColumn(name = "column_id")
    @ApiModelProperty(value = "栏目")
    private CmsColumn column;

    @Column(name = "title")
    @ApiModelProperty(value = "内容标题")
    private String title;

    @Column(name = "author")
    @ApiModelProperty(value = "作者")
    private String author;

    @Column(name = "content")
    @ApiModelProperty(value = "发布内容")
    private String content;

    @Column(name = "cms_status")
    @ApiModelProperty(value = "文章状态")
    private Integer cmsStatus;

    @Column(name = "audit_person")
    @ApiModelProperty(value = "审核人")
    private String auditPerson;

    @Column(name = "audit_status")
    @ApiModelProperty(value = "审核状态")
    private Integer auditStatus;

    @Column(name = "audit_time")
    @ApiModelProperty(value = "审核时间")
    private Timestamp auditTime;

    @Column(name = "audit_proposal")
    @ApiModelProperty(value = "审核意见")
    private String auditProposal;

    @Column(name = "publish_time")
    @ApiModelProperty(value = "发布时间")
    private Timestamp publishTime;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "修改时间")
    private Timestamp updateTime;

    @Column(name = "is_deleted")
    @ApiModelProperty(value = "1 表示删除，0 表示未删除")
    private Integer isDeleted;

    @Column(name = "annex_url")
    @ApiModelProperty(value = "附件url")
    private String annexUrl;

    @Column(name = "by2")
    @ApiModelProperty(value = "备用字段")
    private String by2;

    @Column(name = "by3")
    @ApiModelProperty(value = "备用字段")
    private String by3;

    @Column(name = "by4")
    @ApiModelProperty(value = "备用字段")
    private String by4;

    @Column(name = "by5")
    @ApiModelProperty(value = "备用字段")
    private String by5;

    public void copy(Cms source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
