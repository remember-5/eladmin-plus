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
package ${package}.service.impl;

import ${package}.domain.${className};
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
            <#if column_index = 1>
import com.remember5.core.exception.EntityExistException;
            </#if>
        </#if>
    </#list>
</#if>
import com.remember5.core.utils.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${package}.service.${className}Service;
import ${package}.domain.vo.${className}QueryCriteria;
import ${package}.mapper.${className}Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.remember5.security.utils.PageUtil;
import com.remember5.security.entity.PageResult;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.web.multipart.MultipartFile;
import com.remember5.core.enums.FileTypeEnum;

/**
* ${apiAlias}服务实现
* @author ${author}
* @date ${date}
**/
@Service
@RequiredArgsConstructor
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements ${className}Service {

    private final ${className}Mapper ${changeClassName}Mapper;

    @Override
    public PageResult<${className}> queryAll(${className}QueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(${changeClassName}Mapper.findAll(criteria, page));
    }

    @Override
    public List<${className}> queryAll(${className}QueryCriteria criteria){
        return ${changeClassName}Mapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(${className} resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className} resources) {
        ${className} ${changeClassName} = getById(resources.get${pkCapitalColName}());
        ${changeClassName}.copy(resources);
        saveOrUpdate(${changeClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<${pkColumnType}> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<${className}> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (${className} ${changeClassName} : all) {
            Map<String,Object> map = new LinkedHashMap<>();
        <#list columns as column>
            <#if column.columnKey != 'PRI'>
            <#if column.remark != ''>
            map.put("${column.remark}", ${changeClassName}.get${column.capitalColumnName}());
            <#else>
            map.put(" ${column.changeColumnName}",  ${changeClassName}.get${column.capitalColumnName}());
            </#if>
            </#if>
        </#list>
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional
    public void importData(MultipartFile file) throws IOException{
        if (FileUtil.checkFileType(file, FileTypeEnum.XLSX.suffix, FileTypeEnum.XLSX.mimeType)) {
            String fileName = IdUtil.simpleUUID() + ".xlsx";
            ExcelReader reader = ExcelUtil.getReader(FileUtil.inputStreamToFile(file.getResource().getInputStream(),fileName));
            List<${className}> readAll = reader.readAll(${className}.class);
            saveBatch(readAll);
        }
    }
}
