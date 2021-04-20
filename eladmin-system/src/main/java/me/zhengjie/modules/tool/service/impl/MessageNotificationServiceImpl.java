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

import me.zhengjie.modules.tool.domain.MessageNotification;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.tool.repository.MessageNotificationRepository;
import me.zhengjie.modules.tool.service.MessageNotificationService;
import me.zhengjie.modules.tool.service.dto.MessageNotificationDto;
import me.zhengjie.modules.tool.service.dto.MessageNotificationQueryCriteria;
import me.zhengjie.modules.tool.service.mapstruct.MessageNotificationMapper;
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

import static me.zhengjie.utils.SecurityUtils.getCurrentUserId;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author fly
* @date 2021-04-19
**/
@Service
@RequiredArgsConstructor
public class MessageNotificationServiceImpl implements MessageNotificationService {

    private final MessageNotificationRepository messageNotificationRepository;
    private final MessageNotificationMapper messageNotificationMapper;

    @Override
    public Map<String,Object> queryAll(MessageNotificationQueryCriteria criteria, Pageable pageable){
        Page<MessageNotification> page = messageNotificationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(messageNotificationMapper::toDto));
    }

    @Override
    public List<MessageNotificationDto> queryAll(MessageNotificationQueryCriteria criteria){
        return messageNotificationMapper.toDto(messageNotificationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public List<MessageNotificationDto> findMessageByUserId(){
        return messageNotificationMapper.toDto(messageNotificationRepository.findMessageByUserId(getCurrentUserId()));
    }

    @Override
    @Transactional
    public MessageNotificationDto findById(Long id) {
        MessageNotification messageNotification = messageNotificationRepository.findById(id).orElseGet(MessageNotification::new);
        ValidationUtil.isNull(messageNotification.getId(),"MessageNotification","id",id);
        return messageNotificationMapper.toDto(messageNotification);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageNotificationDto create(MessageNotification resources) {
        return messageNotificationMapper.toDto(messageNotificationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MessageNotification resources) {
        MessageNotification messageNotification = messageNotificationRepository.findById(resources.getId()).orElseGet(MessageNotification::new);
        ValidationUtil.isNull( messageNotification.getId(),"MessageNotification","id",resources.getId());
        messageNotification.copy(resources);
        messageNotificationRepository.save(messageNotification);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            messageNotificationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<MessageNotificationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MessageNotificationDto messageNotification : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("消息通知的用户", messageNotification.getUserId());
            map.put("内容简要", messageNotification.getBriefly());
            map.put("内容详情", messageNotification.getDetails());
            map.put("消息级别(0紧急/1普通)", messageNotification.getMessageLevel());
            map.put("标签(类型:0错误/1普通通知/2待办)", messageNotification.getMessageLabel());
            map.put("状态(0未开始/1进行中/2已处理)", messageNotification.getMessageState());
            map.put("创建时间", messageNotification.getCreateDate());
            map.put("修改时间", messageNotification.getUpdateDate());
            map.put("1 表示删除，0 表示未删除", messageNotification.getIsDeleted());
            map.put(" by1",  messageNotification.getBy1());
            map.put(" by2",  messageNotification.getBy2());
            map.put(" by3",  messageNotification.getBy3());
            map.put(" by4",  messageNotification.getBy4());
            map.put(" by5",  messageNotification.getBy5());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
