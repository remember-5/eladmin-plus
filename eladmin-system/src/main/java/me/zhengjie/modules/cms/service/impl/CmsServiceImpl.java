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
package me.zhengjie.modules.cms.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.cms.domain.Cms;
import me.zhengjie.modules.cms.repository.CmsRepository;
import me.zhengjie.modules.cms.service.CmsService;
import me.zhengjie.modules.cms.service.dto.CmsDto;
import me.zhengjie.modules.cms.service.dto.CmsQueryCriteria;
import me.zhengjie.modules.cms.service.mapstruct.CmsMapper;
import me.zhengjie.modules.column.service.dto.CmsColumnDto;
import me.zhengjie.modules.column.service.impl.CmsColumnServiceImpl;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static me.zhengjie.utils.SecurityUtils.getCurrentUsername;


/**
* @website https://el-admin.vip
* @description 服务实现
* @author zhangenrong
* @date 2021-03-01
**/
@Service
@RequiredArgsConstructor
public class CmsServiceImpl implements CmsService {

    private final CmsRepository cmsRepository;
    private final CmsMapper cmsMapper;

    private final CmsColumnServiceImpl cmsColumnService;
    @Override
    public Map<String,Object> queryAll(CmsQueryCriteria criteria, Pageable pageable){
        Page<Cms> page = cmsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(cmsMapper::toDto));
    }

    @Override
    public List<CmsDto> queryAll(CmsQueryCriteria criteria){
        return cmsMapper.toDto(cmsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CmsDto findById(Long id) {
        Cms cms = cmsRepository.findById(id).orElseGet(Cms::new);
        ValidationUtil.isNull(cms.getId(),"Cms","id",id);
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
     * @param id
     * @return
     */
    private String getColumnNames(Long id){
        List<CmsColumnDto> cmsColumnDtos = cmsColumnService.columnsTreeData(id);
        StringBuffer stringBuffer=new StringBuffer();
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
        ValidationUtil.isNull( cms.getId(),"Cms","id",resources.getId());
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
            cmsRepository.updateById(id);
        }
    }

    @Override
    public void download(List<CmsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsDto cms : all) {
            Map<String,Object> map = new LinkedHashMap<>();
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
