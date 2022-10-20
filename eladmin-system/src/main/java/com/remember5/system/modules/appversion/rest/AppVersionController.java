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
package com.remember5.system.modules.appversion.rest;

import cn.hutool.core.util.ObjectUtil;
import com.remember5.biz.appversion.domain.AppVersion;
import com.remember5.biz.appversion.service.AppVersionService;
import com.remember5.biz.appversion.service.dto.AppVersionDto;
import com.remember5.biz.appversion.service.dto.AppVersionQueryCriteria;
import com.remember5.core.annotation.rest.AnonymousPostMapping;
import com.remember5.core.eneity.AppInformation;
import com.remember5.core.properties.UniAppProperties;
import com.remember5.core.result.R;
import com.remember5.core.result.REnum;
import com.remember5.core.utils.UniAppUtils;
import com.remember5.logging.annotation.Log;
import com.remember5.system.modules.minio.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * @author fly
 * @date 2022-12-07
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "app版本管理")
@RequestMapping("/api/appVersion")
public class AppVersionController {

    private final AppVersionService appVersionService;
    private final UniAppProperties uniAppProperties;
    private final MinioService minioService;


//    @GetMapping("/update")
//    @Log("更新app")
//    @ApiOperation("更新app")
//    public R<AppVersion> update() {
//        return appVersionService.update();
//        return null;
//    }


    @GetMapping
    @Log("查询app版本")
    @ApiOperation("查询app版本")
    @PreAuthorize("@el.check('appVersion:list')")
    public ResponseEntity<Object> queryAppVersion(AppVersionQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(appVersionService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增app版本")
    @ApiOperation("新增app版本")
    @PreAuthorize("@el.check('appVersion:add')")
    public ResponseEntity<Object> createAppVersion(@Validated @RequestBody AppVersion resources) {
        return new ResponseEntity<>(appVersionService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改app版本")
    @ApiOperation("修改app版本")
    @PreAuthorize("@el.check('appVersion:edit')")
    public ResponseEntity<Object> updateAppVersion(@Validated @RequestBody AppVersion resources) {
        appVersionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除app版本")
    @ApiOperation("删除app版本")
    @PreAuthorize("@el.check('appVersion:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAppVersion(@RequestBody Integer[] ids) {
        appVersionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("新增app版本")
    @AnonymousPostMapping(value = "/addWgt")
    @ApiOperation("新增app版本")
    public ResponseEntity<Object> create(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) {
        // 校验key是否和后台配置的key匹配，匹配则执行添加操作
        if (!uniAppProperties.getSecretKey().equals(key)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 1. 解析wgt的包获取 AppInformation
        AppInformation appInformation = UniAppUtils.readZipFile(file);
        if (ObjectUtil.isNull(appInformation)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 2. 判断是否已经存在版本 T 返回已存在，F 继续判断
        AppVersionQueryCriteria criteria = new AppVersionQueryCriteria();
        criteria.setVersion(appInformation.getVersion());
        criteria.setBuild(Long.parseLong(appInformation.getBuild()));
        List<AppVersionDto> appVersionDto = appVersionService.queryAll(criteria);
        if (!appVersionDto.isEmpty()){
            return new ResponseEntity<>("该版本已存在", HttpStatus.OK);
        }
        // 3. 判断 version是否已存在，存在，则把发布字段改成true 否则为false，需要后台手动发布，
        AppVersionQueryCriteria criteria2 = new AppVersionQueryCriteria();
        criteria.setVersion(appInformation.getVersion());
        List<AppVersionDto> appVersionDto2 = appVersionService.queryAll(criteria2);
        // 上传文件
        R r = minioService.uploadFile(file);
        if (!Objects.equals(r.getCode(), REnum.A00000.code)){
            return new ResponseEntity<>("上传文件服务异常", HttpStatus.OK);
        }
        String url =  r.getData().toString();
        AppVersion newAppVersion = AppVersion.builder()
                .appId(appInformation.getAppid())
                .appName(appInformation.getName())
                .version(appInformation.getVersion())
                .build(Long.parseLong(appInformation.getBuild()))
                .title(appInformation.getName())
                .info(appInformation.getDescription())
                // 更新方式 update_type默认为solicit
                .updateType("solicit")
                // 平台默认为 all
                .platform("all")
                .wgtUrl(url)
                // 新的大版本 需要手动发布 0 未发布 1 发布
                .published(appVersionDto2.isEmpty() ? 0 : 1)
                // 0 未归档 1 归档
                .archived(0)
                // 0 未删除 1 已删除
                .isDeleted(0)
                // 0 非最新版本 1 最新版本
                .isLatestRelease(1)
                .build();
        // 修改其他版本为非最新版本
        appVersionService.updateIsLatestRelease();
        // 4. 执行插入
        AppVersionDto appVersion = appVersionService.create(newAppVersion);
        return new ResponseEntity<>(appVersion, HttpStatus.OK);
    }


}
