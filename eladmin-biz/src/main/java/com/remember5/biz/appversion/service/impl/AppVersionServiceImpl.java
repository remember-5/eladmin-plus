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
package com.remember5.biz.appversion.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.remember5.biz.appversion.domain.AppVersion;
import com.remember5.biz.appversion.repository.AppVersionRepository;
import com.remember5.biz.appversion.service.AppVersionService;
import com.remember5.biz.appversion.service.dto.AppVersionDto;
import com.remember5.biz.appversion.service.dto.AppVersionQueryCriteria;
import com.remember5.biz.appversion.service.mapstruct.AppVersionMapper;
import com.remember5.core.enums.FileTypeEnum;
import com.remember5.core.result.R;
import com.remember5.core.utils.FileUtil;
import com.remember5.core.utils.PageUtil;
import com.remember5.core.utils.ValidationUtil;
import com.remember5.security.utils.QueryHelp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.remember5.core.utils.FileUtil.checkFileType;

/**
* @description 服务实现
* @author fly
* @date 2023-04-06
**/
@Service
@RequiredArgsConstructor
public class AppVersionServiceImpl implements AppVersionService {

    private final AppVersionRepository appVersionRepository;
    private final AppVersionMapper appVersionMapper;

    @Override
    @Transactional
    public void importData(MultipartFile file) throws IOException{
        if (checkFileType(file, FileTypeEnum.XLSX.suffix, FileTypeEnum.XLSX.mimeType)) {
            String fileName = IdUtil.simpleUUID() + ".xlsx";
            ExcelReader reader = ExcelUtil.getReader(FileUtil.inputStreamToFile(file.getResource().getInputStream(),fileName));
            List<AppVersion> readAll = reader.readAll(AppVersion.class);
            appVersionRepository.saveAll(readAll);
        }
    }

    @Override
    public Map<String,Object> queryAll(AppVersionQueryCriteria criteria, Pageable pageable){
        Page<AppVersion> page = appVersionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(appVersionMapper::toDto));
    }

    @Override
    public List<AppVersionDto> queryAll(AppVersionQueryCriteria criteria){
        return appVersionMapper.toDto(appVersionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public AppVersionDto findById(Integer id) {
        AppVersion appVersion = appVersionRepository.findById(id).orElseGet(AppVersion::new);
        ValidationUtil.isNull(appVersion.getId(),"AppVersion","id",id);
        return appVersionMapper.toDto(appVersion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppVersionDto create(AppVersion resources) {
        return appVersionMapper.toDto(appVersionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AppVersion resources) {
        AppVersion appVersion = appVersionRepository.findById(resources.getId()).orElseGet(AppVersion::new);
        ValidationUtil.isNull( appVersion.getId(),"AppVersion","id",resources.getId());
        appVersion.copy(resources);
        appVersionRepository.save(appVersion);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            appVersionRepository.updateIsDeletedById(id);
        }
    }

    @Override
    public void download(List<AppVersionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AppVersionDto appVersion : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("app的唯一标识", appVersion.getAppId());
            map.put("app的名称", appVersion.getAppName());
            map.put("版本号 as 1.0.0", appVersion.getVersion());
            map.put("构建版本 as 400", appVersion.getBuild());
            map.put("版本标题 as 3.0版本隆重登场", appVersion.getTitle());
            map.put("版本更新内容,主要在app上展示", appVersion.getInfo());
            map.put("最低支持的版本 as 1.0.2", appVersion.getMinVersion());
            map.put("更新方式 forcibly = 强制更新, solicit = 弹窗确认更新, silent = 静默更新", appVersion.getUpdateType());
            map.put("平台 ios/android/app(ios&android)", appVersion.getPlatform());
            map.put("wgt", appVersion.getWgtUrl());
            map.put("apk", appVersion.getApkUrl());
            map.put("是否发布 0=未发布 1=发布", appVersion.getPublished());
            map.put("是否归档 0=未归档 1=归档 可在拦截器判断，归档的版本禁止提供服务", appVersion.getArchived());
            map.put("逻辑删除 0=未删除 1=删除", appVersion.getIsDeleted());
            map.put("最新版本 0=不是最新 1=最新", appVersion.getIsLatestRelease());
            map.put("更新时间", appVersion.getUpdateTime());
            map.put("创建时间", appVersion.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public R updateIsLatestRelease() {
        appVersionRepository.updateIsLatestRelease();
        return R.success();
    }
}
