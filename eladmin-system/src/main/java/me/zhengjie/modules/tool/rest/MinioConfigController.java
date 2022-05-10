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
import me.zhengjie.modules.tool.domain.MinioConfig;
import me.zhengjie.modules.tool.service.MinioConfigService;
import me.zhengjie.modules.tool.service.dto.MinioConfigQueryCriteria;
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
* @website https://el-admin.vip
* @author fly
* @date 2022-05-10
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "minio配置管理")
@RequestMapping("/api/minioConfig")
public class MinioConfigController {

    private final MinioConfigService minioConfigService;

    @GetMapping
    @Log("查询minio配置")
    @ApiOperation("查询minio配置")
    public ResponseEntity<Object> query(){
        return new ResponseEntity<>(minioConfigService.queryAll(),HttpStatus.OK);
    }

    @PutMapping
    @Log("修改minio配置")
    @ApiOperation("修改minio配置")
    @PreAuthorize("@el.check('minioConfig:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MinioConfig resources){
        minioConfigService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
