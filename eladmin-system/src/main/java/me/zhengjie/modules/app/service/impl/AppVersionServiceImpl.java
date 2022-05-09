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
package me.zhengjie.modules.app.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.app.domain.AppVersion;
import me.zhengjie.modules.app.repository.AppVersionRepository;
import me.zhengjie.modules.app.service.AppVersionService;
import me.zhengjie.modules.app.service.dto.AppVersionDto;
import me.zhengjie.modules.app.service.dto.AppVersionQueryCriteria;
import me.zhengjie.modules.app.service.mapstruct.AppVersionMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author tianhh
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-05-03
 **/
@Service
@RequiredArgsConstructor
public class AppVersionServiceImpl implements AppVersionService {

    private final AppVersionRepository appVersionRepository;
    private final AppVersionMapper appVersionMapper;

    @Override
    public Map<String, Object> queryAll(AppVersionQueryCriteria criteria, Pageable pageable) {
        if (criteria != null) {
            criteria.setIsDeleted(false);
        } else {
            criteria = new AppVersionQueryCriteria();
            criteria.setIsDeleted(false);
        }
        AppVersionQueryCriteria finalCriteria = criteria;
        Page<AppVersion> page = appVersionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, finalCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(appVersionMapper::toDto));
    }

    @Override
    public List<AppVersionDto> queryAll(AppVersionQueryCriteria criteria) {
        if (criteria.getIsDeleted() == null) {
            criteria.setIsDeleted(false);
        }
        return appVersionMapper.toDto(appVersionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public AppVersionDto findById(Integer id) {
        AppVersion appVersion = appVersionRepository.findById(id).orElseGet(AppVersion::new);
        ValidationUtil.isNull(appVersion.getId(), "AppVersion", "id", id);
        return appVersionMapper.toDto(appVersion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppVersionDto create(AppVersion resources) {
        resources.setIsNew(true);
        AppVersionQueryCriteria appVersionQueryCriteria = new AppVersionQueryCriteria();
        appVersionQueryCriteria.setIsNew(true);
        appVersionQueryCriteria.setResType(resources.getResType());
        if (2 == resources.getResType()) {
            appVersionQueryCriteria.setVersionName(resources.getVersionName());
        }
        List<AppVersionDto> appVersionDtos = queryAll(appVersionQueryCriteria);
        for (AppVersionDto appVersionDto : appVersionDtos) {
            AppVersion appVersion1 = appVersionRepository.findById(appVersionDto.getId()).orElseGet(AppVersion::new);
            appVersion1.setIsNew(false);
            appVersionRepository.save(appVersion1);
        }

        resources.setIsDeleted(false);
        return appVersionMapper.toDto(appVersionRepository.save(resources));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AppVersion resources) {
        AppVersion appVersion = appVersionRepository.findById(resources.getId()).orElseGet(AppVersion::new);
        ValidationUtil.isNull(appVersion.getId(), "AppVersion", "id", resources.getId());
        //首先判断是否为最新版本
        if (resources.getIsNew()) {
            AppVersionQueryCriteria appVersionQueryCriteria = new AppVersionQueryCriteria();
            appVersionQueryCriteria.setIsNew(true);
            appVersionQueryCriteria.setResType(resources.getResType());
            List<AppVersionDto> appVersionDtos = queryAll(appVersionQueryCriteria);
            for (AppVersionDto appVersionDto : appVersionDtos) {
                AppVersion appVersion1 = appVersionRepository.findById(appVersionDto.getId()).orElseGet(AppVersion::new);
                appVersion1.setIsNew(false);
                appVersionRepository.save(appVersion1);
            }
        }
        appVersion.copy(resources);
        appVersionRepository.save(appVersion);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            Optional<AppVersion> byId = appVersionRepository.findById(id);
            AppVersion appVersion = byId.get();
            appVersion.setIsDeleted(true);
            appVersionRepository.save(appVersion);
        }

    }

    @Override
    public void download(List<AppVersionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AppVersionDto appVersion : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("版本号", appVersion.getVersionName());
            map.put("打包号", appVersion.getBuildCode());
            map.put("是否最新 1 历史 0 最新", appVersion.getIsNew());
            map.put("1 表示删除，0 表示未删除。", appVersion.getIsDeleted());
            map.put("升级说明", appVersion.getContent());
            map.put("下载链接", appVersion.getUrl());
            map.put("是否最新 1.历史 0 最新", appVersion.getCreateDate());
            map.put(" updateDate", appVersion.getUpdateDate());
            map.put("是否是必须更新", appVersion.getIsMust());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
