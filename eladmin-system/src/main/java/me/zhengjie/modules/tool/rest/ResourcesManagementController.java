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
package me.zhengjie.modules.tool.rest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tool.domain.MessageNotification;
import me.zhengjie.modules.tool.domain.ResourcesManagement;
import me.zhengjie.modules.tool.service.ResourcesManagementService;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementQueryCriteria;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import static me.zhengjie.utils.FileUtil.SYS_TEM_DIR;

/**
 * @author fly
 * @website https://el-admin.vip
 * @date 2021-04-12
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "存储资源管理")
@RequestMapping("/api/resourcesManagement")
public class ResourcesManagementController {

    private final ResourcesManagementService resourcesManagementService;

    @GetMapping(value = "/downloadTemplate")
    public void testExcel(HttpServletResponse response) throws IOException {
        List<ResourcesManagement> rows = CollUtil.newArrayList(new ResourcesManagement());
        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //template.xlsx是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition","attachment;filename=template.xlsx");
        ServletOutputStream out=response.getOutputStream();
        //out为OutputStream，需要写出到的目标流
        writer.flush(out, true);
        //关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    @PostMapping(value = "/importData")
    public ResponseEntity<Object> importData(@RequestParam("file") MultipartFile file) throws IOException{
        resourcesManagementService.importData(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('resourcesManagement:list')")
    public void download(HttpServletResponse response, ResourcesManagementQueryCriteria criteria) throws IOException {
        resourcesManagementService.download(resourcesManagementService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询存储资源")
    @ApiOperation("查询存储资源")
    @PreAuthorize("@el.check('resourcesManagement:list')")
    public ResponseEntity<Object> query(ResourcesManagementQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(resourcesManagementService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增存储资源")
    @ApiOperation("新增存储资源")
    @PreAuthorize("@el.check('resourcesManagement:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ResourcesManagement resources) {
        return new ResponseEntity<>(resourcesManagementService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改存储资源")
    @ApiOperation("修改存储资源")
    @PreAuthorize("@el.check('resourcesManagement:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ResourcesManagement resources) {
        resourcesManagementService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/editEnabled")
    @Log("修改存储资源状态")
    @ApiOperation("修改存储资源状态")
    @PreAuthorize("@el.check('resourcesManagement:switchs')")
    public ResponseEntity<Object> editEnabled(@Validated @RequestBody ResourcesManagement resources) {
        resourcesManagementService.editEnabled(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除存储资源")
    @ApiOperation("删除存储资源")
    @PreAuthorize("@el.check('resourcesManagement:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        resourcesManagementService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
