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
package com.remember5.system.modules.cms.service.impl;

import com.remember5.system.modules.cms.repository.CmsRepository;
import com.remember5.system.modules.cms.service.dto.CmsQueryCriteria;
import com.remember5.system.modules.cms.service.mapstruct.CmsMapper;
import lombok.RequiredArgsConstructor;
import com.remember5.system.modules.cms.domain.Cms;
import com.remember5.system.modules.cms.service.CmsService;
import com.remember5.system.modules.cms.service.dto.CmsDto;
import com.remember5.system.modules.column.service.dto.CmsColumnDto;
import com.remember5.system.modules.column.service.impl.CmsColumnServiceImpl;
import com.remember5.core.utils.FileUtil;
import com.remember5.core.utils.PageUtil;
import com.remember5.core.utils.QueryHelp;
import com.remember5.core.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.remember5.core.utils.SecurityUtils.getCurrentUsername;


/**
 * @author zhangenrong
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-03-01
 **/
@Service
@RequiredArgsConstructor
public class CmsServiceImpl implements CmsService {

    private final CmsRepository cmsRepository;
    private final CmsMapper cmsMapper;

    private final CmsColumnServiceImpl cmsColumnService;

    @Override
    public Map<String, Object> queryAll(CmsQueryCriteria criteria, Pageable pageable) {
        Page<Cms> page = cmsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(cmsMapper::toDto));
    }

    @Override
    public List<CmsDto> queryAll(CmsQueryCriteria criteria) {
        return cmsMapper.toDto(cmsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsDto findById(Long id) {
        Cms cms = cmsRepository.findById(id).orElseGet(Cms::new);
        ValidationUtil.isNull(cms.getId(), "Cms", "id", id);
        return cmsMapper.toDto(cms);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsDto create(Cms resources) {
        Cms save = cmsRepository.save(resources);
        return cmsMapper.toDto(save);
    }

    /**
     * 通过栏目id获取栏目组成的字符串以,分割
     *
     * @param id
     * @return
     */
    private String getColumnNames(Long id) {
        List<CmsColumnDto> cmsColumnDtos = cmsColumnService.columnsTreeData(id);
        StringBuilder stringBuffer = new StringBuilder();
        for (CmsColumnDto cmsColumnDto : cmsColumnDtos) {
            stringBuffer.append(cmsColumnDto.getColumnName());
            stringBuffer.append(',');
        }
        return stringBuffer.toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Cms resources) {
        Cms cms = cmsRepository.findById(resources.getId()).orElseGet(Cms::new);
        ValidationUtil.isNull(cms.getId(), "Cms", "id", resources.getId());
        cms.copy(resources);
        cmsRepository.save(cms);
    }

    @Override
    public void audit(Cms resources) {
        // 审核人 审核时间
        resources.setAuditPerson(getCurrentUsername());
        resources.setAuditTime(new Timestamp(System.currentTimeMillis()));
        cmsRepository.save(resources);
    }

    @Override
    public void publish(Cms resources) {
        Cms cms = cmsRepository.findById(resources.getId()).orElseGet(Cms::new);
        cms.copy(resources);
        // 发布状态
        cms.setCmsStatus(1);
        cms.setPublishTime(new Timestamp(System.currentTimeMillis()));
        cmsRepository.save(cms);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
//            cmsRepository.updateById(id);
        }
    }

    @Override
    public void download(List<CmsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsDto cms : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("栏目", cms.getColumnId());
            map.put("作者", cms.getAuthor());
            map.put("内容标题", cms.getTitle());
            map.put("发布内容", cms.getContent());
            map.put("审核人", cms.getAuditPerson());
            map.put("审核状态", cms.getAuditStatus());
            map.put("审核时间", cms.getAuditTime());
            map.put("发布时间", cms.getPublishTime());
            map.put("创建时间", cms.getCreateTime());
            map.put("修改时间", cms.getUpdateTime());
            map.put("是否删除，0否，1是", cms.getIsDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
