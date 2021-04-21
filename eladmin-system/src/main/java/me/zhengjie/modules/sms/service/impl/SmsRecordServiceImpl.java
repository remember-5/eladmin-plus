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

import me.zhengjie.modules.sms.domain.SmsRecord;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.sms.repository.SmsRecordRepository;
import me.zhengjie.modules.sms.service.SmsRecordService;
import me.zhengjie.modules.sms.service.dto.SmsRecordDto;
import me.zhengjie.modules.sms.service.dto.SmsRecordQueryCriteria;
import me.zhengjie.modules.sms.service.mapstruct.SmsRecordMapper;
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
public class SmsRecordServiceImpl implements SmsRecordService {

    private final SmsRecordRepository smsRecordRepository;
    private final SmsRecordMapper smsRecordMapper;

    @Override
    public Map<String,Object> queryAll(SmsRecordQueryCriteria criteria, Pageable pageable){
        Page<SmsRecord> page = smsRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(smsRecordMapper::toDto));
    }

    @Override
    public List<SmsRecordDto> queryAll(SmsRecordQueryCriteria criteria){
        return smsRecordMapper.toDto(smsRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SmsRecordDto findById(Integer id) {
        SmsRecord smsRecord = smsRecordRepository.findById(id).orElseGet(SmsRecord::new);
        ValidationUtil.isNull(smsRecord.getId(),"SmsRecord","id",id);
        return smsRecordMapper.toDto(smsRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsRecordDto create(SmsRecord resources) {
        return smsRecordMapper.toDto(smsRecordRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SmsRecord resources) {
        SmsRecord smsRecord = smsRecordRepository.findById(resources.getId()).orElseGet(SmsRecord::new);
        ValidationUtil.isNull( smsRecord.getId(),"SmsRecord","id",resources.getId());
        smsRecord.copy(resources);
        smsRecordRepository.save(smsRecord);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            smsRecordRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SmsRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SmsRecordDto smsRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("项目名称", smsRecord.getEntryName());
            map.put("手机号", smsRecord.getPhone());
            map.put("创建时间", smsRecord.getCreateTime());
            map.put("发送时间", smsRecord.getSendTime());
            map.put("发送状态 0 待发送  1 已发送  2 发送失败", smsRecord.getSendStatus());
            map.put(" spreat",  smsRecord.getSpreat());
            map.put(" spreat1",  smsRecord.getSpreat1());
            map.put(" spreat2",  smsRecord.getSpreat2());
            map.put(" spreat3",  smsRecord.getSpreat3());
            map.put(" appid",  smsRecord.getAppid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}