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

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.smsServer.domain.SmsaRecord;
import me.zhengjie.modules.smsServer.repository.SmsRecordaRepository;
import me.zhengjie.modules.smsServer.service.SmsRecordaService;
import me.zhengjie.modules.smsServer.service.dto.SmsRecordaDto;
import me.zhengjie.modules.smsServer.service.dto.SmsRecordaQueryCriteria;
import me.zhengjie.modules.smsServer.service.mapstruct.SmsRecordaMapper;
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
* @website https://el-admin.vip
* @description 服务实现
* @author wh
* @date 2021-04-19
**/
@Service
@RequiredArgsConstructor
public class SmsRecordaServiceImpl implements SmsRecordaService {

    private final SmsRecordaRepository smsRecordaRepository;
    private final SmsRecordaMapper smsRecordaMapper;

    @Override
    public Map<String,Object> queryAll(SmsRecordaQueryCriteria criteria, Pageable pageable){
        Page<SmsaRecord> page = smsRecordaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(smsRecordaMapper::toDto));
    }

    @Override
    public List<SmsRecordaDto> queryAll(SmsRecordaQueryCriteria criteria){
        return smsRecordaMapper.toDto(smsRecordaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SmsRecordaDto findById(Integer id) {
        SmsaRecord smsaRecord = smsRecordaRepository.findById(id).orElseGet(SmsaRecord::new);
        ValidationUtil.isNull(smsaRecord.getId(),"SmsaRecord","id",id);
        return smsRecordaMapper.toDto(smsaRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SmsRecordaDto create(SmsaRecord resources) {
        return smsRecordaMapper.toDto(smsRecordaRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SmsaRecord resources) {
        SmsaRecord smsaRecord = smsRecordaRepository.findById(resources.getId()).orElseGet(SmsaRecord::new);
        ValidationUtil.isNull( smsaRecord.getId(),"SmsaRecord","id",resources.getId());
        smsaRecord.copy(resources);
        smsRecordaRepository.save(smsaRecord);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            smsRecordaRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SmsRecordaDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SmsRecordaDto smsRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("项目名称", smsRecord.getEntryName());
            map.put("手机号", smsRecord.getPhone());
            map.put("创建时间", smsRecord.getCreateTime());
            map.put("发送时间", smsRecord.getSendTime());
            map.put("发送状态 0 待发送  1 已发送  2 发送失败", smsRecord.getSendStatus());
            map.put(" spreat",  smsRecord.getUid());
            map.put(" spreat1",  smsRecord.getSpreat1());
            map.put(" spreat2",  smsRecord.getSpreat2());
            map.put(" spreat3",  smsRecord.getSpreat3());
            map.put(" appid",  smsRecord.getAppid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    public static void main(String[] args) {

        System.err.println(DateUtil.lastWeek());
        System.err.println();

    }

    @Override
    public Object getrecordday() {
        String s = DateUtil.lastWeek().toDateStr();
        DateUtil.lastWeek();
       return smsRecordaRepository.getselectday(s);
    }

    @Override
    public Object getrecordweek() {
        List<Map<String, String>> smsRecordweek = smsRecordaRepository.getSmsRecordweek();
        return smsRecordweek;
    }

    @Override
    public Object getrecordmonth() {
        List<Map<String, String>> smsRecordMonth = smsRecordaRepository.getSmsRecordMonth();
        return smsRecordMonth;
    }

    @Override
    public List<Map<String,String>> getcustomTime(String start,String  end)
    {
        String s = DateUtil.lastWeek().toDateStr();
        return smsRecordaRepository.getcustomTime(s,start,end);
    }
}
