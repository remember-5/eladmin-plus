package com.remember5.core.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回
 * 不提供自定义code的方法，规范使用enums
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


    public static <T> R<T> success() {
        return success(REnum.A00000, null);
    }

    public static <T> R<T> success(T data) {
        return success(REnum.A00000, data);
    }

    public static <T> R<T> success(REnum rEnum, T data) {
        return success(rEnum.code, data, rEnum.message);
    }

    public static <T> R<T> success(String code, T data, String message) {
        final R<T> r = new R<>();
        r.code = code;
        r.data = data;
        r.message = message;
        return r;
    }

    public static <T> R<T> fail() {
        return fail(REnum.A0001);
    }


    public static <T> R<T> fail(REnum rEnum) {
        return fail(rEnum, null);
    }

    public static <T> R<T> fail(T data) {
        return fail(REnum.A0001, data);
    }

    public static <T> R<T> fail(REnum rEnum, T data) {
        R<T> r = new R<>();
        r.code = rEnum.code;
        r.data = data;
        r.message = rEnum.message;
        return r;
    }

}
