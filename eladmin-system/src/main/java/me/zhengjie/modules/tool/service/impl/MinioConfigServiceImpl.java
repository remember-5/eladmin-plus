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
package me.zhengjie.modules.tool.service.impl;

import cn.hutool.core.util.ObjectUtil;
import me.zhengjie.modules.tool.domain.MinioConfig;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.tool.repository.MinioConfigRepository;
import me.zhengjie.modules.tool.service.MinioConfigService;
import me.zhengjie.modules.tool.service.dto.MinioConfigDto;
import me.zhengjie.modules.tool.service.dto.MinioConfigQueryCriteria;
import me.zhengjie.modules.tool.service.mapstruct.MinioConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author fly
* @date 2022-05-10
**/
@Service
@RequiredArgsConstructor
public class MinioConfigServiceImpl implements MinioConfigService {

    private final MinioConfigRepository minioConfigRepository;
    private final MinioConfigMapper minioConfigMapper;

    @Override
    public List<MinioConfigDto> queryAll(){
        return minioConfigMapper.toDto(minioConfigRepository.findAll());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MinioConfig resources) {
        if (ObjectUtil.isNotNull(resources.getId())) {
            MinioConfig minioConfig = minioConfigRepository.findById(resources.getId()).orElseGet(MinioConfig::new);
            ValidationUtil.isNull( minioConfig.getId(),"MinioConfig","id",resources.getId());
            minioConfig.copy(resources);
            minioConfigRepository.save(minioConfig);
        }
        minioConfigRepository.save(resources);
    }

}
