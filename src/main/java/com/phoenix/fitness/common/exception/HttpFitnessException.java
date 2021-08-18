package com.phoenix.fitness.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.phoenix.fitness.common.constant.HttpExceptionEnum;

@Getter
@NoArgsConstructor
public class HttpFitnessException extends RuntimeException {

    private HttpExceptionEnum exceptionEnum;

    public HttpFitnessException(HttpExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.exceptionEnum = exceptionEnum;
    }
}
