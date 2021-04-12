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

import me.zhengjie.modules.tool.domain.ResourcesManagement;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.tool.repository.ResourcesManagementRepository;
import me.zhengjie.modules.tool.service.ResourcesManagementService;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementDto;
import me.zhengjie.modules.tool.service.dto.ResourcesManagementQueryCriteria;
import me.zhengjie.modules.tool.service.mapstruct.ResourcesManagementMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
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
* @date 2021-04-12
**/
@Service
@RequiredArgsConstructor
public class ResourcesManagementServiceImpl implements ResourcesManagementService {

    private final ResourcesManagementRepository resourcesManagementRepository;
    private final ResourcesManagementMapper resourcesManagementMapper;

    @Override
    public Map<String,Object> queryAll(ResourcesManagementQueryCriteria criteria, Pageable pageable){
        Page<ResourcesManagement> page = resourcesManagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(resourcesManagementMapper::toDto));
    }

    @Override
    public List<ResourcesManagementDto> queryAll(ResourcesManagementQueryCriteria criteria){
        return resourcesManagementMapper.toDto(resourcesManagementRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ResourcesManagementDto findById(Long id) {
        ResourcesManagement resourcesManagement = resourcesManagementRepository.findById(id).orElseGet(ResourcesManagement::new);
        ValidationUtil.isNull(resourcesManagement.getId(),"ResourcesManagement","id",id);
        return resourcesManagementMapper.toDto(resourcesManagement);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourcesManagementDto create(ResourcesManagement resources) {
        // 初始化状态 默认为0 禁用状态
        resources.setEnabled(0);
        return resourcesManagementMapper.toDto(resourcesManagementRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ResourcesManagement resources) {
        ResourcesManagement resourcesManagement = resourcesManagementRepository.findById(resources.getId()).orElseGet(ResourcesManagement::new);
        ValidationUtil.isNull( resourcesManagement.getId(),"ResourcesManagement","id",resources.getId());
        resourcesManagement.copy(resources);
        resourcesManagementRepository.save(resourcesManagement);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editEnabled(ResourcesManagement resources) {
        Integer enabled = resources.getEnabled();
        if (enabled == 0){
            // 启用状态
            resources.setEnabled(1);
            // 禁用其他启用
            ResourcesManagement resourcesManagement = resourcesManagementRepository.findByEnabled();
            resourcesManagementRepository.updateById(resourcesManagement.getId());
            // TODO 销毁Bean
            // TODO 注入Bean

        }else {
            // 禁用状态
            resources.setEnabled(0);
            // TODO 销毁Bean

        }
        ResourcesManagement resourcesManagement = resourcesManagementRepository.findById(resources.getId()).orElseGet(ResourcesManagement::new);
        ValidationUtil.isNull( resourcesManagement.getId(),"ResourcesManagement","id",resources.getId());
        resourcesManagement.copy(resources);
        resourcesManagementRepository.save(resourcesManagement);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            resourcesManagementRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ResourcesManagementDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ResourcesManagementDto resourcesManagement : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("分类", resourcesManagement.getType());
            map.put("资源编号", resourcesManagement.getNum());
            map.put("资源地址", resourcesManagement.getUrl());
            map.put("端口号", resourcesManagement.getPort());
            map.put("空间名", resourcesManagement.getBucket());
            map.put("accessKey", resourcesManagement.getAccesskey());
            map.put("secretKey", resourcesManagement.getSecretkey());
            map.put("启用状态(1启用/0禁用)", resourcesManagement.getEnabled());
            map.put("备注", resourcesManagement.getRemarks());
            map.put("创建时间", resourcesManagement.getCreateDate());
            map.put("修改时间", resourcesManagement.getUpdateDate());
            map.put("1 表示删除，0 表示未删除", resourcesManagement.getIsDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
