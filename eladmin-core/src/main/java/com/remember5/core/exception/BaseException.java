package com.remember5.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.remember5.core.result.R;

/**
 * 自定义异常类
 *
 * @author wangjiahao
 * @date 2022/4/26 11:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private R r;

    public BaseException(R r) {
        this.r = r;
    }
}
