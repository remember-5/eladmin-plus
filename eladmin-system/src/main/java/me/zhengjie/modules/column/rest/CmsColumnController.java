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
package me.zhengjie.modules.column.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.column.domain.CmsColumn;
import me.zhengjie.modules.column.service.CmsColumnService;
import me.zhengjie.modules.column.service.dto.CmsColumnQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @website https://el-admin.vip
* @author fly
* @date 2021-03-02
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "栏目管理")
@RequestMapping("/api/cmsColumn")
public class CmsColumnController {

    private final CmsColumnService cmsColumnService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsColumn:list')")
    public void download(HttpServletResponse response, CmsColumnQueryCriteria criteria) throws IOException {
        cmsColumnService.download(cmsColumnService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询栏目管理")
    @ApiOperation("查询栏目管理")
    @PreAuthorize("@el.check('cmsColumn:list')")
    public ResponseEntity<Object> query(CmsColumnQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cmsColumnService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/query")
    @Log("查询子栏目")
    @ApiOperation("查询子栏目")
    public ResponseEntity<Object> query(CmsColumnQueryCriteria criteria){
        return new ResponseEntity<>(cmsColumnService.queryAll(criteria),HttpStatus.OK);
    }

    @GetMapping(value = "/getTree")
    @Log("查询栏目树")
    @ApiOperation("查询栏目树")
    @PreAuthorize("@el.check('cmsColumn:list')")
    public ResponseEntity<Object> queryTree(){
        return new ResponseEntity<>(cmsColumnService.queryTreeData(),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增栏目管理")
    @ApiOperation("新增栏目管理")
    @PreAuthorize("@el.check('cmsColumn:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CmsColumn resources){
        return new ResponseEntity<>(cmsColumnService.create(resources),HttpStatus.CREATED);
    }

    @PostMapping(value = "/addFirstLevelColumn")
    @Log("新增顶级栏目")
    @ApiOperation("新增顶级栏目")
    @PreAuthorize("@el.check('cmsColumn:addFirstLevelColumn')")
    public ResponseEntity<Object> createFirstLevelColumn(@Validated @RequestBody CmsColumn resources){
        return new ResponseEntity<>(cmsColumnService.createFirstLevelColumn(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改栏目管理")
    @ApiOperation("修改栏目管理")
    @PreAuthorize("@el.check('cmsColumn:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CmsColumn resources){
        cmsColumnService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除栏目管理")
    @ApiOperation("删除栏目管理")
    @PreAuthorize("@el.check('cmsColumn:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        cmsColumnService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
