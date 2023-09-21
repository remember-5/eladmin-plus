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
package com.remember5.system.modules.tool.rest;

import com.remember5.core.exception.BadRequestException;
import com.remember5.core.utils.FileUtil;
import com.remember5.system.modules.logging.annotation.Log;
import com.remember5.system.modules.tool.domain.LocalStorage;
import com.remember5.system.modules.tool.service.LocalStorageService;
import com.remember5.system.modules.tool.service.dto.LocalStorageQueryCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zheng Jie
 * @date 2019-09-05
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "工具：本地存储管理")
@RequestMapping("/api/localStorage")
public class LocalStorageController {

    private final LocalStorageService localStorageService;

    @Operation(summary = "查询文件")
    @GetMapping
    @PreAuthorize("@el.check('storage:list')")
    public ResponseEntity<Object> queryFile(LocalStorageQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(localStorageService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Operation(summary = "导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storage:list')")
    public void exportFile(HttpServletResponse response, LocalStorageQueryCriteria criteria) throws IOException {
        localStorageService.download(localStorageService.queryAll(criteria), response);
    }

    @Operation(summary = "上传文件")
    @PostMapping
    @PreAuthorize("@el.check('storage:add')")
    public ResponseEntity<Object> createFile(@RequestParam String name, @RequestParam("file") MultipartFile file) {
        localStorageService.create(name, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/pictures")
    @Operation(summary = "上传图片")
    public ResponseEntity<Object> uploadPicture(@RequestParam MultipartFile file) {
        // 判断文件是否为图片
        String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
        if (!FileUtil.IMAGE.equals(FileUtil.getFileType(suffix))) {
            throw new BadRequestException("只能上传图片");
        }
        LocalStorage localStorage = localStorageService.create(null, file);
        return new ResponseEntity<>(localStorage, HttpStatus.OK);
    }

    @Log("修改文件")
    @Operation(summary = "修改文件")
    @PutMapping
    @PreAuthorize("@el.check('storage:edit')")
    public ResponseEntity<Object> updateFile(@Validated @RequestBody LocalStorage resources) {
        localStorageService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除文件")
    @DeleteMapping
    @Operation(summary = "多选删除")
    public ResponseEntity<Object> deleteFile(@RequestBody Long[] ids) {
        localStorageService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
