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
package me.zhengjie.modules.keyword.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author tianhh
 * @website https://el-admin.vip
 * @description /
 * @date 2021-04-21
 **/
@Entity
@Data
@Table(name = "t_keywordfiltering_content")
public class KeywordfilteringContent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "修改时间")
    private Timestamp updateTime;

    @Column(name = "is_del", nullable = false)
    @NotNull
    @ApiModelProperty(value = "是否逻辑删除")
    private Integer isDel;
    @ExcelProperty("关键字")
    @Column(name = "keyword")
    @ApiModelProperty(value = "关键字")
    private String keyword;

    @Column(name = "url")
    @ApiModelProperty(value = "来自于文件")
    private String url;

    @Column(name = "keywordfiltering_id")
    @ApiModelProperty(value = "关键词表id")
    private Long keywordfilteringId;

    @Column(name = "by1")
    @ApiModelProperty(value = "备用")
    private String by1;

    @Column(name = "by2")
    @ApiModelProperty(value = "备用")
    private String by2;

    @Column(name = "by3")
    @ApiModelProperty(value = "备用")
    private String by3;

    @Column(name = "by4")
    @ApiModelProperty(value = "备用")
    private String by4;

    @Column(name = "by5")
    @ApiModelProperty(value = "备用")
    private String by5;

    @Column(name = "by6")
    @ApiModelProperty(value = "备用")
    private String by6;

    @Column(name = "by7")
    @ApiModelProperty(value = "备用")
    private String by7;

    @Column(name = "by8")
    @ApiModelProperty(value = "备用")
    private String by8;

    @Column(name = "by9")
    @ApiModelProperty(value = "备用")
    private String by9;

    @Column(name = "by10")
    @ApiModelProperty(value = "备用")
    private String by10;

    public void copy(KeywordfilteringContent source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
