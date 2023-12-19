package com.remember5.core.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回
 * 不提供自定义code的方法，规范使用enums
 * 不提供public构造，必须使用R.success() 或者 R.fail() 来描述是成功还是失败
 *
 * @author wangjiahao
 * @date 2021/12/30
 */
@Data
public class R<T> implements Serializable {

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回信息
     */
    private String message;

    public R() {
    }

    private R(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> R<T> success() {
        return success(null);
    }

    public static <T> R<T> success(T data) {
        return success(REnum.A00000, data);
    }

    public static <T> R<T> success(REnum rEnum, T data) {
        return success(rEnum.code, data, rEnum.message);
    }

    public static <T> R<T> success(String code, T data, String message) {
        return new R<>(code, data, message);
    }

    public static <T> R<T> fail() {
        return fail(REnum.A0001);
    }

    public static <T> R<T> fail(REnum rEnum) {
        return fail(rEnum.code, null, rEnum.message);
    }

    public static <T> R<T> fail(REnum rEnum, T data) {
        return fail(rEnum.code, data, rEnum.message);
    }

    public static <T> R<T> fail(String code, T data, String message) {
        return new R<>(code, data, message);
    }

}
