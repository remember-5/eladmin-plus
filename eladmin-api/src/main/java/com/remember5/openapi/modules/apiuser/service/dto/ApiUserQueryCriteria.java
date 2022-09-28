package com.remember5.openapi.modules.apiuser.service.dto;

import lombok.Data;
import com.remember5.core.annotation.Query;

/**
 * @author fly
 * @description
 * @date 2021/8/20 17:56
 */
@Data
public class ApiUserQueryCriteria {

    @Query
    private String phone;

    /**
     * 精确
     */
    @Query
    private Boolean isDeleted = false;

}
