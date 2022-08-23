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
package me.zhengjie.modules.tool.service;

import me.zhengjie.modules.tool.domain.MessageNotification;
import me.zhengjie.modules.tool.service.dto.MessageNotificationDto;
import me.zhengjie.modules.tool.service.dto.MessageNotificationQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author fly
 * @website https://el-admin.vip
 * @description 服务接口
 * @date 2021-04-19
 **/
public interface MessageNotificationService {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(MessageNotificationQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<MessageNotificationDto>
     */
    List<MessageNotificationDto> queryAll(MessageNotificationQueryCriteria criteria);

    /**
     * 根据用户id查询状态 不为 已处理的通知消息
     *
     * @return /
     */
    List<MessageNotificationDto> findMessageByUserId();

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return MessageNotificationDto
     */
    MessageNotificationDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return MessageNotificationDto
     */
    MessageNotificationDto create(MessageNotification resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(MessageNotification resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MessageNotificationDto> all, HttpServletResponse response) throws IOException;
}
