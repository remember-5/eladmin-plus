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
package me.zhengjie.modules.column.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.column.domain.CmsColumn;
import me.zhengjie.modules.column.repository.CmsColumnRepository;
import me.zhengjie.modules.column.service.CmsColumnService;
import me.zhengjie.modules.column.service.dto.CmsColumnDto;
import me.zhengjie.modules.column.service.dto.CmsColumnQueryCriteria;
import me.zhengjie.modules.column.service.mapstruct.CmsColumnMapper;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
* 服务实现
* @author fly
* @date 2021-03-02
**/
@Service
@RequiredArgsConstructor
public class CmsColumnServiceImpl implements CmsColumnService {

    private final CmsColumnRepository cmsColumnRepository;
    private final CmsColumnMapper cmsColumnMapper;

    @Override
    public Map<String,Object> queryAll(CmsColumnQueryCriteria criteria, Pageable pageable){
        Page<CmsColumn> page = cmsColumnRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(cmsColumnMapper::toDto));
    }

    @Override
    public List<CmsColumnDto> queryAll(CmsColumnQueryCriteria criteria){
        return cmsColumnMapper.toDto(cmsColumnRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    public List<CmsColumnDto> columnsTreeData(Long columnFid){
        List<CmsColumnDto> cmsColumns = cmsColumnMapper.toDto(cmsColumnRepository.queryTreeData(columnFid));
        if (cmsColumns != null && cmsColumns.size() > 0){
            for (CmsColumnDto column : cmsColumns) {
                // 取当前栏目id 做父id 查询栏目子集
                column.setColumns(columnsTreeData(column.getId()));
            }
            return cmsColumns;
        }
        return null;
    }
    @Override
    public List<CmsColumnDto> queryTreeData(){
        // 查询顶级类目(项目)
        CmsColumnQueryCriteria criteria = new CmsColumnQueryCriteria();
        criteria.setFid(-1L);
        List<CmsColumnDto> cmsColumns = cmsColumnMapper.toDto(cmsColumnRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
        if (cmsColumns != null && !cmsColumns.isEmpty()){
            for (CmsColumnDto column : cmsColumns) {
                // 取当前栏目id 做父id 查询栏目子集
                column.setColumns(columnsTreeData(column.getId()));
            }
        }
        return cmsColumns;
    }

    @Override
    @Transactional
    public CmsColumnDto findById(Long id) {
        CmsColumn cmsColumn = cmsColumnRepository.findById(id).orElseGet(CmsColumn::new);
        ValidationUtil.isNull(cmsColumn.getId(),"CmsColumn","id",id);
        return cmsColumnMapper.toDto(cmsColumn);
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsColumnDto create(CmsColumn resources) {
        resources.setIsDeleted(0);
        CmsColumnDto cmsColumnDto = cmsColumnMapper.toDto(cmsColumnRepository.save(resources));
        return cmsColumnDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CmsColumnDto createFirstLevelColumn(CmsColumn resources) {
        resources.setFid(-1L);
        resources.setIsDeleted(0);
        return cmsColumnMapper.toDto(cmsColumnRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CmsColumn resources) {
        CmsColumn cmsColumn = cmsColumnRepository.findById(resources.getId()).orElseGet(CmsColumn::new);
        ValidationUtil.isNull( cmsColumn.getId(),"CmsColumn","id",resources.getId());
        cmsColumn.copy(resources);
        cmsColumnRepository.save(cmsColumn);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            cmsColumnRepository.updateById(id);
        }
    }

    @Override
    public void download(List<CmsColumnDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CmsColumnDto cmsColumn : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("父id", cmsColumn.getFid());
            map.put("栏目名", cmsColumn.getColumnName());
            map.put("创建时间", cmsColumn.getCreateTime());
            map.put("修改时间", cmsColumn.getUpdateTime());
            map.put("1 表示删除，0 表示未删除", cmsColumn.getIsDeleted());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
