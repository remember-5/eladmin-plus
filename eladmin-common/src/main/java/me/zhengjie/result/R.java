package me.zhengjie.result;

import java.util.HashMap;

/**
 * @author wangjiahao
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public R message(String message) {
        this.put("message", message);
        return this;
    }

    public R data(Object data) {
        this.put("data", data);
        return this;
    }

    public R code(String code) {
        this.put("code", code);
        return this;
    }

    public R resultEnum(ResultEnum resultEnum) {
        this.put("code", resultEnum.code);
        this.put("message", resultEnum.message);
        return this;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
