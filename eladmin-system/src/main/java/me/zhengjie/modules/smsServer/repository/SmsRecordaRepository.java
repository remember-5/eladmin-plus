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
package me.zhengjie.modules.smsServer.repository;

import me.zhengjie.modules.smsServer.domain.SmsaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author wh
 * @website https://el-admin.vip
 * @date 2021-04-19
 **/
public interface SmsRecordaRepository extends JpaRepository<SmsaRecord, Integer>, JpaSpecificationExecutor<SmsaRecord> {

    @Query(value = "select * from SMS_JOURNAL where send_status=0", nativeQuery = true)
    List<SmsaRecord> selectStatus();


    /**
     * 修改状态为发送成功
     *
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE SMS_JOURNAL SET send_status=1 WHERE id=?1", nativeQuery = true)
    int updateStatus(int id);

    /**
     * 修改状态为发送失败
     *
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update SMS_JOURNAL set send_status=2 where id=?1", nativeQuery = true)
    int updatestatefail(int id);

    /**
     * 查询每日发送总量
     *
     * @return
     */
    @Query(value = "\t\tSELECT DATE_FORMAT(create_time,'%Y-%m-%d') AS createTime,\n" +
            "            COUNT(*) AS count\n" +
            "             FROM sms_journal WHERE ?1 <= DATE_FORMAT(create_time,'%Y-%m-%d') and send_status=1 GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d') ORDER BY DATE_FORMAT(create_time,'%Y-%m-%d')", nativeQuery = true)
    List<Map<String, String>> getselectday(String time);


    @Query(value = "\t\tSELECT DATE_FORMAT(create_time,'%Y-%m-%d'),COUNT(*) FROM sms_journal WHERE\n" +
            "\t\t '2021-04-16' <= DATE_FORMAT(create_time,'%Y-%m-%d')  GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d') ORDER BY DATE_FORMAT(create_time,'%Y-%m-%d') LIMIT 7", nativeQuery = true)
    List<SmsaRecord> getrecordday();


    @Query(value = "SELECT WEEK(create_time,1) As createTime,\n" +
            "            COUNT(1) As count\n" +
            "             FROM sms_journal GROUP BY WEEK(create_time,1) ORDER BY WEEK(create_time,1);", nativeQuery = true)
    List<Map<String, String>> getSmsRecordweek();


    @Query(value = "select DATE_FORMAT( create_time, '%m' ) AS createTime,count(*) AS count from sms_journal WHERE DATE_FORMAT( create_time, '%Y' )= DATE_FORMAT( now(), '%Y' ) GROUP BY DATE_FORMAT( create_time, '%m' ) ORDER BY DATE_FORMAT( create_time, '%m' )", nativeQuery = true)
    List<Map<String, String>> getSmsRecordMonth();

    @Query(value = "SELECT DATE_FORMAT(create_time,'%Y-%m-%d') AS createTime,\n" +
            "                        COUNT(*) AS count\n" +
            "                         FROM sms_journal WHERE ?1 <= DATE_FORMAT(create_time,'%Y-%m-%d')\n" +
            "                          and send_status=1 AND DATE_FORMAT(create_time,'%Y-%m-%d') \n" +
            "                          BETWEEN ?2 AND ?3 \n" +
            "                          GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')\n" +
            "                          ORDER BY DATE_FORMAT(create_time,'%Y-%m-%d')", nativeQuery = true)
    List<Map<String, String>> getcustomTime(String time, String start, String end);
}
