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
package me.zhengjie.modules.smsServer.service.impl;

import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.smsServer.domain.ProjectInformation;
import me.zhengjie.modules.smsServer.repository.ProjectInformationRepository;
import me.zhengjie.modules.smsServer.service.ProjectInformationService;
import me.zhengjie.modules.smsServer.service.dto.ProjectInformationDto;
import me.zhengjie.modules.smsServer.service.dto.ProjectInformationQueryCriteria;
import me.zhengjie.modules.smsServer.service.mapstruct.ProjectInformationMapper;
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
 * @author wh
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-04-21
 **/
@Service
@RequiredArgsConstructor
public class ProjectInformationServiceImpl implements ProjectInformationService {

    private final ProjectInformationRepository projectInformationRepository;
    private final ProjectInformationMapper projectInformationMapper;

    @Override
    public Map<String, Object> queryAll(ProjectInformationQueryCriteria criteria, Pageable pageable) {
        Page<ProjectInformation> page = projectInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(projectInformationMapper::toDto));
    }

    @Override
    public List<ProjectInformationDto> queryAll(ProjectInformationQueryCriteria criteria) {
        return projectInformationMapper.toDto(projectInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public ProjectInformationDto findById(Integer id) {
        ProjectInformation projectInformation = projectInformationRepository.findById(id).orElseGet(ProjectInformation::new);
        ValidationUtil.isNull(projectInformation.getId(), "ProjectInformation", "id", id);
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
        ValidationUtil.isNull(projectInformation.getId(), "ProjectInformation", "id", resources.getId());
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
    public String generation() {
        String val = "";
        Random random = new Random();
        //用循环输出十九个字符进行拼接
        for (int i = 0; i < 21; i++) {
            // 本次循环是数字还是字母
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "number";
            // 字母
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 本次字母为大写还是小写（ASCII）
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                //生成这个字符
                val += (char) (choice + random.nextInt(26));
            }
            // 数字
            else if ("number".equalsIgnoreCase(charOrNum)) {
                //数字可以直接生成
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    @Override
    public String generation2() {
        String val = "";
        Random random = new Random();
        //用循环输出十九个字符进行拼接
        for (int i = 0; i < 12; i++) {
            // 本次循环是数字还是字母
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "number";
            // 字母
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 本次字母为大写还是小写（ASCII）
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                //生成这个字符
                val += (char) (choice + random.nextInt(26));
            }
            // 数字
            else if ("number".equalsIgnoreCase(charOrNum)) {
                //数字可以直接生成
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    @Override
    public void download(List<ProjectInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProjectInformationDto projectInformation : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" entryName", projectInformation.getEntryName());
            map.put("id", projectInformation.getAppid());
            map.put(" spreat", projectInformation.getSpreat());
            map.put(" spreat1", projectInformation.getSpreat1());
            map.put(" spreat2", projectInformation.getSpreat2());
            map.put(" spreat3", projectInformation.getSpreat3());
            map.put("密钥", projectInformation.getSecret());
            map.put("应用状态 （0 不允许发送  1 允许发送）", projectInformation.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
