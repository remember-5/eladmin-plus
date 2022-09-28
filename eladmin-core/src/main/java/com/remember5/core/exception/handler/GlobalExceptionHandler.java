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
package com.remember5.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import com.remember5.core.exception.BadRequestException;
import com.remember5.core.exception.BaseException;
import com.remember5.core.exception.EntityExistException;
import com.remember5.core.exception.EntityNotFoundException;
import com.remember5.core.result.R;
import com.remember5.core.result.REnum;
import com.remember5.core.utils.ThrowableUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleException(Throwable e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialsException(BadCredentialsException e) {
        // 打印堆栈信息
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        return buildResponseEntity(ApiError.error(message));
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ApiError> badRequestException(BadRequestException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getStatus(), e.getMessage()));
    }

    /**
     * 处理 EntityExist
     */
    @ExceptionHandler(value = EntityExistException.class)
    public ResponseEntity<ApiError> entityExistException(EntityExistException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiError> entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(ApiError.error(NOT_FOUND.value(), e.getMessage()));
    }

    /**
     * 处理所有接口数据验证异常,优先级会最高
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
//        log.error(ThrowableUtil.getStackTrace(e));
//        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
//        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//        String msg = "不能为空";
//        if (msg.equals(message)) {
//            message = str[1] + ":" + message;
//        }
//        return buildResponseEntity(ApiError.error(message));

        // 拼接错误
        StringBuilder detailMessage = new StringBuilder();
        for (ObjectError objectError : e.getAllErrors()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中，这个不包含报错字段
            detailMessage.append(((DefaultMessageSourceResolvable) objectError.getArguments()[0]).getDefaultMessage());
            detailMessage.append(":");
            // 拼接内容到其中
            detailMessage.append(objectError.getDefaultMessage());
        }
        return R.fail(REnum.A0400.code, null, detailMessage.toString());
    }

    /**
     * 统一返回
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }

    /**
     * 原生javax.validation报错信息
     *
     * @param ex 报错详情
     * @return /
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R constraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException ex) {
        log.debug("[constraintViolationExceptionHandler]", ex);
        // 拼接错误
        StringBuilder detailMessage = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中，这个不包含报错字段
            detailMessage.append(constraintViolation.getPropertyPath());
            detailMessage.append(":");
            detailMessage.append(constraintViolation.getMessage());
        }
        // 包装 CommonResult 结果
        return R.fail(REnum.A0400);
    }

    /**
     * org.springframework.validation 异常信息捕捉
     *
     * @param ex 报错详情
     * @return /
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public R bindExceptionHandler(BindException ex) {
        log.debug("[bindExceptionHandler]", ex);
        // 拼接错误
        StringBuilder detailMessage = new StringBuilder();
        for (ObjectError objectError : ex.getAllErrors()) {
            // 使用 ; 分隔多个错误
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            // 拼接内容到其中
            detailMessage.append(objectError.getDefaultMessage());
        }
        // 包装 CommonResult 结果
        return R.fail(REnum.A0400.code, null, detailMessage.toString());
    }

    /**
     * 全局自定义异常拦截
     *
     * @param e BaseException 里面传入ResultEnum
     * @return RestResult
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public R exception(BaseException e) {
        log.error("系统内部异常，异常信息", e);
        return e.getR();
    }


}
