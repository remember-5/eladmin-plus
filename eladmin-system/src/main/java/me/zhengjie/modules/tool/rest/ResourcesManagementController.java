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
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tool.domain.ResourcesManagement;
import me.zhengjie.modules.tool.service.ResourcesManagementService;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementQueryCriteria;
import me.zhengjie.utils.FileUtil;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;


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

    @Log("导入数据模板")
    @ApiOperation("导入数据模板")
    @GetMapping(value = "/downloadTemplate")
    @PreAuthorize("@el.check('resourcesManagement:importData')")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        List<Object> rows = CollUtil.newArrayList(new ResourcesManagement());
        FileUtil.downloadTemplate(rows,response);
    }

    @Log("导入数据")
    @ApiOperation("导入数据")
    @PostMapping(value = "/importData")
    @PreAuthorize("@el.check('resourcesManagement:importData')")
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
