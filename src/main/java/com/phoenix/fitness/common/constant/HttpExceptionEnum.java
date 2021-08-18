package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpExceptionEnum {
    NOT_FOUND(404, "请求路径不存在，请检查路径是否正确"),
    BAD_REQUEST(400,"错误的请求！"),
    INTERNAL_SERVER_ERROR(500,"服务器内部错误！"),
    TOKEN_IS_EMPTY(401,"token为空"),
    TOKEN_IS_EXPIRED(401,"token失效"),
    ;
    private final int httpCode;
    private final String msg;

}
