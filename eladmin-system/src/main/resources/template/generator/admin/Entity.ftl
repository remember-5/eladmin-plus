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
package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.hutool.core.bean.copier.CopyOptions;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;
<#if auto>
import com.baomidou.mybatisplus.annotation.IdType;
</#if>
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.*;

/**
* ${apiAlias}
* @author ${author}
* @date ${date}
**/
@Data
@TableName("${tableName}")
@Schema(description = "${apiAlias}")
public class ${className} implements Serializable {
<#if columns??>
    <#list columns as column>

    <#if column.columnKey = 'PRI'>
    @TableId(value = "${column.columnName}"<#if auto>, type = IdType.AUTO</#if>)
    </#if>
    <#if column.istNotNull && column.columnKey != 'PRI'>
        <#if column.columnType = 'String'>
            <#if column.remark != ''>
    @NotBlank(message = "${column.remark}不能为空")
            <#else>
    @NotBlank(message = "${column.changeColumnName}不能为空")
            </#if>
        <#else>
        <#if column.remark != ''>
    @NotNull(message = "${column.remark}不能为空")
        <#else>
    @NotNull(message = "${column.changeColumnName}不能为空")
        </#if>
        </#if>
    </#if>
    <#if column.remark != ''>
    @Schema(description = "${column.remark}")
    <#else>
    @Schema(description = "${column.changeColumnName}")
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
