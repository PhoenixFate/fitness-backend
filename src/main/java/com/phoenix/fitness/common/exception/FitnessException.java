package com.phoenix.fitness.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.phoenix.fitness.common.constant.ExceptionEnum;

@Getter
@NoArgsConstructor
public class FitnessException extends RuntimeException{

    private ExceptionEnum exceptionEnum;

    public FitnessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.exceptionEnum = exceptionEnum;
    }
}
