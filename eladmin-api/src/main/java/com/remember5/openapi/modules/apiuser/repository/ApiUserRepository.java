package com.remember5.openapi.modules.apiuser.repository;

import com.remember5.openapi.modules.apiuser.domain.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author fly
 * @description
 * @date 2021/8/20 17:57
 */
public interface ApiUserRepository extends JpaRepository<ApiUser, Long>, JpaSpecificationExecutor<ApiUser> {

    /**
     * 根据手机号查询用户信息
     *
     * @param phone 手机号
     * @return /
     */
    ApiUser findByPhone(String phone);

    /**
     * 查看是否存在手机号
     *
     * @param phone 手机号
     * @return 存在的数量
     */
    Long countByPhone(String phone);

}
