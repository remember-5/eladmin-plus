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
package me.zhengjie.modules.sms.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.sms.domain.ProjectInformation;
import me.zhengjie.modules.sms.service.ProjectInformationService;
import me.zhengjie.modules.sms.service.dto.ProjectInformationQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author wh
* @date 2021-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "项目信息管理")
@RequestMapping("/api/projectInformation")
public class ProjectInformationController {

    private final ProjectInformationService projectInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('projectInformation:list')")
    public void download(HttpServletResponse response, ProjectInformationQueryCriteria criteria) throws IOException {
        projectInformationService.download(projectInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询项目信息")
    @ApiOperation("查询项目信息")
    @PreAuthorize("@el.check('projectInformation:list')")
    public ResponseEntity<Object> query(ProjectInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(projectInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增项目信息")
    @ApiOperation("新增项目信息")
    @PreAuthorize("@el.check('projectInformation:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ProjectInformation resources){
        return new ResponseEntity<>(projectInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改项目信息")
    @ApiOperation("修改项目信息")
    @PreAuthorize("@el.check('projectInformation:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ProjectInformation resources){
        projectInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除项目信息")
    @ApiOperation("删除项目信息")
    @PreAuthorize("@el.check('projectInformation:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        projectInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}