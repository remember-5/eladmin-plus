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
package me.zhengjie.modules.sms.repository;

import me.zhengjie.modules.sms.domain.SmsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
* @website https://el-admin.vip
* @author wh
* @date 2021-04-19
**/
public interface SmsRecordRepository extends JpaRepository<SmsRecord, Integer>, JpaSpecificationExecutor<SmsRecord> {
    @Query(value = "select *from sms_record where 1=1",nativeQuery = true)
    SmsRecord select();

    @Query(value = "select * from sms_record where send_status=0",nativeQuery = true)
    List<SmsRecord> selectStatus();

    /**
     * 修改状态为发送成功
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE sms_record SET send_status=1 WHERE id=?1",nativeQuery = true)
    int  updateStatus(int id);

    /**
     * 修改状态为发送失败
     * @return
     */
    @Transactional
    @Modifying
    @Query(value="update sms_record set send_status=2 where id=?1",nativeQuery = true)
    int updatestatefail(int id);
}
