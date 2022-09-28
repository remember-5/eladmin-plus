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
package com.remember5.system.modules.tool.repository;

import com.remember5.system.modules.tool.domain.MessageNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fly
 * @website https://el-admin.vip
 * @date 2021-04-19
 **/
public interface MessageNotificationRepository extends JpaRepository<MessageNotification, Long>, JpaSpecificationExecutor<MessageNotification> {
    /**
     * 修改状态
     *
     * @param messageState 状态
     * @param id           消息id
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update MessageNotification set messageState = ?1 where id=?2 ")
    void updateById(Integer messageState, Long id);

    /**
     * 根据用户id查询状态 不为 已处理的通知消息
     *
     * @param userId 用户id
     * @return /
     */
    @Query(value = "select a.*  from t_message_notification a where a.message_state != 2 and a.user_id = ?1", nativeQuery = true)
    List<MessageNotification> findMessageByUserId(Long userId);
}
