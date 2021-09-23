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
package me.zhengjie.modules.keyword.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.keyword.domain.Keywordfiltering;
import me.zhengjie.modules.keyword.repository.KeywordfilteringRepository;
import me.zhengjie.modules.keyword.service.KeywordfilteringService;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringDto;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringQueryCriteria;
import me.zhengjie.modules.keyword.service.mapstruct.KeywordfilteringMapper;
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
 * @author tianhh
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-04-21
 **/
@Service
@RequiredArgsConstructor
public class KeywordfilteringServiceImpl implements KeywordfilteringService {

    private final KeywordfilteringRepository keywordfilteringRepository;
    private final KeywordfilteringMapper keywordfilteringMapper;

    @Override
    public Map<String, Object> queryAll(KeywordfilteringQueryCriteria criteria, Pageable pageable) {
        if (criteria == null) {
            criteria = new KeywordfilteringQueryCriteria();
        }
        if (criteria.getIsDel() == null) {
            criteria.setIsDel(0);
        }
        KeywordfilteringQueryCriteria finalCriteria = criteria;
        Page<Keywordfiltering> page = keywordfilteringRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, finalCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(keywordfilteringMapper::toDto));
    }

    @Override
    public List<KeywordfilteringDto> queryAll(KeywordfilteringQueryCriteria criteria) {
        if (criteria == null) {
            criteria = new KeywordfilteringQueryCriteria();
        }
        if (criteria.getIsDel() == null) {
            criteria.setIsDel(0);
        }
        KeywordfilteringQueryCriteria finalCriteria = criteria;
        return keywordfilteringMapper.toDto(keywordfilteringRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, finalCriteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public KeywordfilteringDto findById(Long id) {
        Keywordfiltering keywordfiltering = keywordfilteringRepository.findById(id).orElseGet(Keywordfiltering::new);
        ValidationUtil.isNull(keywordfiltering.getId(), "Keywordfiltering", "id", id);
        return keywordfilteringMapper.toDto(keywordfiltering);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KeywordfilteringDto create(Keywordfiltering resources) {
        resources.setIsDel(0);
        return keywordfilteringMapper.toDto(keywordfilteringRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Keywordfiltering resources) {
        Keywordfiltering keywordfiltering = keywordfilteringRepository.findById(resources.getId()).orElseGet(Keywordfiltering::new);
        ValidationUtil.isNull(keywordfiltering.getId(), "Keywordfiltering", "id", resources.getId());
        keywordfiltering.copy(resources);
        keywordfilteringRepository.save(keywordfiltering);
    }

    @Override
    public void deleteAll(Long[] ids) {
        Keywordfiltering blacklist = null;
        for (Long id : ids) {
            blacklist = keywordfilteringRepository.getOne(id);
            blacklist.setIsDel(1);
            keywordfilteringRepository.save(blacklist);
        }
    }

    @Override
    public void download(List<KeywordfilteringDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (KeywordfilteringDto keywordfiltering : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("创建时间", keywordfiltering.getCreateTime());
            map.put("修改时间", keywordfiltering.getUpdateTime());
            map.put("是否逻辑删除", keywordfiltering.getIsDel());
            map.put("过滤表的名字", keywordfiltering.getName());
            map.put("过滤表的表述", keywordfiltering.getBlacklistDescribe());
            map.put("上传文件的url", keywordfiltering.getFileUrl());
            map.put("更新模式", keywordfiltering.getUpdateType());
            map.put("备用", keywordfiltering.getBy1());
            map.put("备用", keywordfiltering.getBy2());
            map.put("备用", keywordfiltering.getBy3());
            map.put("备用", keywordfiltering.getBy4());
            map.put("备用", keywordfiltering.getBy5());
            map.put("备用", keywordfiltering.getBy6());
            map.put("备用", keywordfiltering.getBy7());
            map.put("备用", keywordfiltering.getBy8());
            map.put("备用", keywordfiltering.getBy9());
            map.put("备用", keywordfiltering.getBy10());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
