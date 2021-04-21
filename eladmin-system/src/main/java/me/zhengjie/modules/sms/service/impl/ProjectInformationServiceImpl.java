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
package me.zhengjie.modules.sms.service.impl;

import me.zhengjie.modules.sms.domain.ProjectInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.sms.repository.ProjectInformationRepository;
import me.zhengjie.modules.sms.service.ProjectInformationService;
import me.zhengjie.modules.sms.service.dto.ProjectInformationDto;
import me.zhengjie.modules.sms.service.dto.ProjectInformationQueryCriteria;
import me.zhengjie.modules.sms.service.mapstruct.ProjectInformationMapper;
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
* @author wh
* @date 2021-04-19
**/
@Service
@RequiredArgsConstructor
public class ProjectInformationServiceImpl implements ProjectInformationService {

    private final ProjectInformationRepository projectInformationRepository;
    private final ProjectInformationMapper projectInformationMapper;

    @Override
    public Map<String,Object> queryAll(ProjectInformationQueryCriteria criteria, Pageable pageable){
        Page<ProjectInformation> page = projectInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(projectInformationMapper::toDto));
    }

    @Override
    public List<ProjectInformationDto> queryAll(ProjectInformationQueryCriteria criteria){
        return projectInformationMapper.toDto(projectInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ProjectInformationDto findById(Integer id) {
        ProjectInformation projectInformation = projectInformationRepository.findById(id).orElseGet(ProjectInformation::new);
        ValidationUtil.isNull(projectInformation.getId(),"ProjectInformation","id",id);
        return projectInformationMapper.toDto(projectInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProjectInformationDto create(ProjectInformation resources) {
        return projectInformationMapper.toDto(projectInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProjectInformation resources) {
        ProjectInformation projectInformation = projectInformationRepository.findById(resources.getId()).orElseGet(ProjectInformation::new);
        ValidationUtil.isNull( projectInformation.getId(),"ProjectInformation","id",resources.getId());
        projectInformation.copy(resources);
        projectInformationRepository.save(projectInformation);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            projectInformationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ProjectInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProjectInformationDto projectInformation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" entryName",  projectInformation.getEntryName());
            map.put("id", projectInformation.getAppid());
            map.put(" spreat",  projectInformation.getSpreat());
            map.put(" spreat1",  projectInformation.getSpreat1());
            map.put(" spreat2",  projectInformation.getSpreat2());
            map.put(" spreat3",  projectInformation.getSpreat3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}