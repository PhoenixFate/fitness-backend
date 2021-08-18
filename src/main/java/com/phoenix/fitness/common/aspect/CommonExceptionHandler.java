package com.phoenix.fitness.common.aspect;

import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.constant.HttpExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.exception.HttpFitnessException;
import com.phoenix.fitness.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.phoenix.fitness.common.utils.R;
import java.util.List;

/**
 * 异常处理器
 * 默认情况下 拦截所有的controller
 *
 * @author Mark sm516116978@outlook.com
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * 校验错误拦截处理
     *
     * @param exception 表单校验错误
     * @return 错误信息
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<R> validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p -> {
                FieldError fieldError = (FieldError) p;
                System.out.println(fieldError);
                errorMessage.append(p.getDefaultMessage()).append(";");
            });
        }
        log.error(exception.getMessage(),exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error(errorMessage.toString()));
    }

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return ResponseEntity
     */
    @ExceptionHandler(FitnessException.class)
    public ResponseEntity<R> handlerException(FitnessException e) {
        log.error(e.getMessage(),e);
        ExceptionEnum exceptionEnum = e.getExceptionEnum();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error(exceptionEnum));
    }

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return ResponseEntity
     */
    @ExceptionHandler(HttpFitnessException.class)
    public ResponseEntity<R> handlerException(HttpFitnessException e) {
        log.error(e.getMessage(),e);
        HttpExceptionEnum exceptionEnum = e.getExceptionEnum();
        return ResponseEntity.status(exceptionEnum.getHttpCode()).body(R.error(exceptionEnum));
    }

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return ResponseEntity
     */
    @ExceptionHandler(RRException.class)
    public ResponseEntity<R> handlerException(RRException e) {
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error(e.getCode(),e.getMsg()));
    }

    /**
     * 请求不存在异常
     *
     * @param e 请求不存在异常
     * @return ResponseEntity
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<R> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(R.error(ExceptionEnum.NOT_FOUND));
    }

    /**
     * 数据库中已存在该记录
     *
     * @param e 数据库中已存在该记录
     * @return ResponseEntity
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<R> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(R.error(ExceptionEnum.DUPLICATE_KEY));
    }

    /**
     * 没有权限
     *
     * @param e 没有权限
     * @return ResponseEntity
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<R> handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(R.error(ExceptionEnum.AUTHORIZATION_FAILURE));
    }

    /**
     * 请求方法错误
     *
     * @param e 请求方法错误
     * @return R
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<R> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.error(ExceptionEnum.REQUEST_METHOD_TYPE_ERROR));
    }

    /**
     * 请求体为空or请求参数类型错误
     *
     * @param e 请求体为空or请求参数类型错误
     * @return R
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<R> handleException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.error(ExceptionEnum.REQUEST_BODY_EMPTY_ERROR));
    }

    /**
     * 请求参数类型错误
     *
     * @param e 请求参数类型错误
     * @return R
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<R> handleException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.error(ExceptionEnum.REQUEST_ARGUMENT_TYPE_ERROR));
    }

    /**
     * 处理所有异常
     *
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<R> handleOtherException(Exception e) {
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error(ExceptionEnum.INTERNAL_SERVER_ERROR));
    }
}
