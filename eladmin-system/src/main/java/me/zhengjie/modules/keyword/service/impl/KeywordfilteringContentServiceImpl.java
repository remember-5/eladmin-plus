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
import me.zhengjie.modules.keyword.domain.KeywordfilteringContent;
import me.zhengjie.modules.keyword.repository.KeywordfilteringContentRepository;
import me.zhengjie.modules.keyword.service.KeywordfilteringContentService;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringContentDto;
import me.zhengjie.modules.keyword.service.dto.KeywordfilteringContentQueryCriteria;
import me.zhengjie.modules.keyword.service.mapstruct.KeywordfilteringContentMapper;
import me.zhengjie.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author tianhh
* @date 2021-04-21
**/
@Service
@RequiredArgsConstructor
public class KeywordfilteringContentServiceImpl implements KeywordfilteringContentService {

    private final String TAG="KeywordfilteringContentServiceImpl";
    private final RedisUtils redisUtils;
    private final KeywordfilteringContentRepository keywordfilteringContentRepository;
    private final KeywordfilteringContentMapper keywordfilteringContentMapper;

    @Override
    public Map<String,Object> queryAll(KeywordfilteringContentQueryCriteria criteria, Pageable pageable){

        if (criteria==null){
            criteria=new KeywordfilteringContentQueryCriteria();
        }
        if (criteria.getIsDel()==null){
            criteria.setIsDel(0);
        }
        KeywordfilteringContentQueryCriteria finalCriteria = criteria;
        Page<KeywordfilteringContent> page = keywordfilteringContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, finalCriteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(keywordfilteringContentMapper::toDto));
    }
    @Override
    public Set<String> queryAll(){
        Set<String> strings=new HashSet<>();
        if (redisUtils.hasKey(TAG)){
            Set<Object> objectSet = redisUtils.sGet(TAG);
            objectSet.forEach(item->{
                strings.add(item.toString());
            });
            objectSet.clear();
            return strings;
        }
        KeywordfilteringContentQueryCriteria criteria=null;
        if (criteria==null){
            criteria=new KeywordfilteringContentQueryCriteria();
        }
        if (criteria.getIsDel()==null){
            criteria.setIsDel(0);
        }

        KeywordfilteringContentQueryCriteria finalCriteria = criteria;
        List<KeywordfilteringContentDto> keywordfilteringContentDtos = keywordfilteringContentMapper.toDto(keywordfilteringContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, finalCriteria, criteriaBuilder)));
        Set<String> set=new HashSet<>();
        keywordfilteringContentDtos.forEach(item->{
            if (!set.contains(item.getKeyword())){
                set.add(item.getKeyword());
            }
        });
        redisUtils.sSet(TAG,set.toArray());
        return set;
    }
    @Override
    public List<KeywordfilteringContentDto> queryAll(KeywordfilteringContentQueryCriteria criteria){
        if (criteria==null){
            criteria=new KeywordfilteringContentQueryCriteria();
        }
        if (criteria.getIsDel()==null){
            criteria.setIsDel(0);
        }
        KeywordfilteringContentQueryCriteria finalCriteria = criteria;
        return keywordfilteringContentMapper.toDto(keywordfilteringContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, finalCriteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public KeywordfilteringContentDto findById(Long id) {
        KeywordfilteringContent keywordfilteringContent = keywordfilteringContentRepository.findById(id).orElseGet(KeywordfilteringContent::new);
        ValidationUtil.isNull(keywordfilteringContent.getId(),"KeywordfilteringContent","id",id);
        return keywordfilteringContentMapper.toDto(keywordfilteringContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KeywordfilteringContentDto create(KeywordfilteringContent resources) {
        resources.setIsDel(0);
        KeywordfilteringContentDto keywordfilteringContentDto = keywordfilteringContentMapper.toDto(keywordfilteringContentRepository.save(resources));
        redisUtils.sSet(TAG,keywordfilteringContentDto.getKeyword());

        return keywordfilteringContentDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(KeywordfilteringContent resources) {
        KeywordfilteringContent keywordfilteringContent = keywordfilteringContentRepository.findById(resources.getId()).orElseGet(KeywordfilteringContent::new);
        ValidationUtil.isNull( keywordfilteringContent.getId(),"KeywordfilteringContent","id",resources.getId());
        keywordfilteringContent.copy(resources);
        keywordfilteringContentRepository.save(keywordfilteringContent);
    }

    @Override
    public void deleteAll(Long[] ids) {
        KeywordfilteringContent blacklist=null;
        for (Long id : ids) {
            blacklist=keywordfilteringContentRepository.getOne(id);
            blacklist.setIsDel(1);
            if (redisUtils.hasKey(TAG)) {
                redisUtils.setRemove(TAG,blacklist.getKeyword());
            }
            keywordfilteringContentRepository.save(blacklist);
        }
    }
    @Override
    public  boolean contains(String context){
        for (String s : queryAll()) {
            if (context.contains(s)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void download(List<KeywordfilteringContentDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (KeywordfilteringContentDto keywordfilteringContent : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("创建时间", keywordfilteringContent.getCreateTime());
            map.put("修改时间", keywordfilteringContent.getUpdateTime());
            map.put("是否逻辑删除", keywordfilteringContent.getIsDel());
            map.put("关键字", keywordfilteringContent.getKeyword());
            map.put("来自于文件", keywordfilteringContent.getUrl());
            map.put("关键词表id", keywordfilteringContent.getKeywordfilteringId());
            map.put("备用", keywordfilteringContent.getBy1());
            map.put("备用", keywordfilteringContent.getBy2());
            map.put("备用", keywordfilteringContent.getBy3());
            map.put("备用", keywordfilteringContent.getBy4());
            map.put("备用", keywordfilteringContent.getBy5());
            map.put("备用", keywordfilteringContent.getBy6());
            map.put("备用", keywordfilteringContent.getBy7());
            map.put("备用", keywordfilteringContent.getBy8());
            map.put("备用", keywordfilteringContent.getBy9());
            map.put("备用", keywordfilteringContent.getBy10());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
