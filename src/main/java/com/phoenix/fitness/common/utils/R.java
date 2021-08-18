package com.phoenix.fitness.common.utils;

import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.constant.HttpExceptionEnum;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sm516116978@outlook.com
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public R(String key, Object value) {
        put("code", 0);
        put("msg", "success");
        super.put(key, value);
    }

    public R(Object value) {
        put("code", 0);
        put("msg", "success");
        super.put("data", value);
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(ExceptionEnum exceptionEnum) {
        R r = new R();
        r.put("code", exceptionEnum.getCode());
        r.put("msg", exceptionEnum.getMsg());
        return r;
    }

    public static R error(HttpExceptionEnum exceptionEnum) {
        R r = new R();
        r.put("code", exceptionEnum.getHttpCode());
        r.put("msg", exceptionEnum.getMsg());
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
