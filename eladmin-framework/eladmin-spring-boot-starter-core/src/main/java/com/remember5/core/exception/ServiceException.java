package com.remember5.core.exception;

import com.remember5.core.result.REnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类
 * service 层抛出的异常
 *
 * @author wangjiahao
 * @date 2022/4/26 11:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    /**
     * 错误信息
     */
    private REnum rEnum;

    public ServiceException(REnum result) {
        this.rEnum = result;
    }

}
