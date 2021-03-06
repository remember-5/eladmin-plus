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
package me.zhengjie.modules.app.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.app.domain.AppVersion;
import me.zhengjie.modules.app.service.AppVersionService;
import me.zhengjie.modules.app.service.dto.AppVersionDto;
import me.zhengjie.modules.app.service.dto.AppVersionQueryCriteria;
import me.zhengjie.modules.app.utils.UniappUtil;
import me.zhengjie.modules.minio.service.MinioService;
import me.zhengjie.repository.LogRepository;
import me.zhengjie.result.R;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author tianhh
 * @website https://el-admin.vip
 * @date 2021-05-03
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "app??????????????????")
@RequestMapping("/api/version")
public class AppVersionController {

    private final AppVersionService appVersionService;
    private final MinioService minIOService;
    private final LogRepository logRepository;

    @Log("????????????")
    @ApiOperation("????????????")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('version:list')")
    public void download(HttpServletResponse response, AppVersionQueryCriteria criteria) throws IOException {
        appVersionService.download(appVersionService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("??????app????????????")
    @ApiOperation("??????app????????????")
    @PreAuthorize("@el.check('version:list')")
    public ResponseEntity<Object> query(AppVersionQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(appVersionService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    public static String getHeader(String name) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request != null ? request.getHeader(name) : null;
    }

    @GetMapping("getwgtversion")
    @Log("??????app wgt????????????")
    @ApiOperation("??????app wgt????????????")
    public ResponseEntity<Object> query() {
        AppVersionQueryCriteria criteria = new AppVersionQueryCriteria();
        criteria.setVersionName(getHeader("version"));
        criteria.setBuildCode(getHeader("versionCode"));
        List<AppVersionDto> appVersionDtos = appVersionService.queryAll(criteria);
        if (appVersionDtos.size() > 0) {
            if (appVersionDtos.get(0).getIsNew()) {
                return new ResponseEntity<>("????????????????????????", HttpStatus.OK);
            } else {
                AppVersionQueryCriteria appVersionQueryCriteria = new AppVersionQueryCriteria();
                appVersionQueryCriteria.setVersionName(criteria.getVersionName());
                appVersionQueryCriteria.setResType(2);
                appVersionQueryCriteria.setIsNew(true);
                return new ResponseEntity<>(appVersionService.queryAll(appVersionQueryCriteria), HttpStatus.OK);
            }
        } else {
            throw new BadRequestException("???????????????app??????");
        }
    }

    @PostMapping("addwgt")
    @Log("??????app????????????")
    @ApiOperation("??????app????????????")
    public ResponseEntity<Object> create(@RequestParam("file") MultipartFile file) {

        AppVersion appVersion = new AppVersion();
        appVersion.setIsNew(true);
        appVersion.setResType(2);
        UniappUtil.zipFileRead(file, appVersion);
        AppVersionQueryCriteria appVersionQueryCriteria = new AppVersionQueryCriteria();
        appVersionQueryCriteria.setBuildCode(appVersion.getBuildCode());
        appVersionQueryCriteria.setVersionName(appVersion.getVersionName());
        List<AppVersionDto> appVersionDtos = appVersionService.queryAll(appVersionQueryCriteria);
        appVersion.setIsMust(false);
        if (appVersionDtos.size() > 0) {
            me.zhengjie.domain.Log log = new me.zhengjie.domain.Log();
            log.setLogType("ERROR");
            log.setDescription(String.format("?????????????????? VerisonName:%s VerisonCode:%s", appVersion.getVersionName(), appVersion.getBuildCode()));
            logRepository.save(log);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        R r = minIOService.uploadFile(file);
        String s = r.getData().toString();
        appVersion.setUrl(s);
        AppVersionDto appVersionDto = appVersionService.create(appVersion);
        return new ResponseEntity<>(appVersionDto, HttpStatus.CREATED);
    }

    @PostMapping
    @Log("??????app????????????")
    @ApiOperation("??????app????????????")
    @PreAuthorize("@el.check('version:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody AppVersion resources) {
        AppVersionQueryCriteria appVersionQueryCriteria = new AppVersionQueryCriteria();
        appVersionQueryCriteria.setResType(resources.getResType());
        appVersionQueryCriteria.setVersionName(resources.getVersionName());
        if (resources.getResType() == 2) {
            appVersionQueryCriteria.setBuildCode(resources.getBuildCode());
        }
        List<AppVersionDto> all = appVersionService.queryAll(appVersionQueryCriteria);
        if (all.size() > 0) {
            throw new BadRequestException("???????????????");
        }
        return new ResponseEntity<>(appVersionService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("??????app????????????")
    @ApiOperation("??????app????????????")
    @PreAuthorize("@el.check('version:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody AppVersion resources) {
        appVersionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("??????app????????????")
    @ApiOperation("??????app????????????")
    @PreAuthorize("@el.check('version:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        appVersionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}