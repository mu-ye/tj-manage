package com.huan.demo.result;

import com.huan.demo.exception.AccessTokenExpiredException;
import com.huan.demo.exception.ExceptionEnum;
import com.huan.demo.exception.RefreshTokenExpiredException;
import com.huan.demo.exception.UserNameNotMatchPasswordException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * <p> 常用注解学习</p>
 *
 * <p>
 *     1. @RestControllerAdvice("com.huan.demo")
 *          1.1  统一拦截  @RequestMapping 作用的方法
 *          1.2  ("com.huan.demo") 参数中可以指定扫描的包
 *     2.  @ExceptionHandler(NoHandlerFoundException.class)
 *          2.1  异常捕获器，参数为指定异常；处理指定异常
 * </p>
 */

/**
 * 异常处理
 *
 * @author RCNJTECH
 * @date 2020/6/22 16:30
 */
@RestControllerAdvice("com.huan.demo")
public class GlobalExceptionHandler {

    /**
     * <p>
     *     常用异常捕获
     * </p>
     */

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return Result.error("1001", "API 不存在：" + e.getRequestURL());
    }

    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        String errorMessage = fieldErrorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("、"));
        return Result.error("1002", errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        String errorMessage = fieldErrorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("、"));
        return Result.error("1002", errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolationSet = e.getConstraintViolations();
        String errorMessage = constraintViolationSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("、"));
        return Result.error("1003", errorMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Result.error("1004", "缺少请求参数 " + e.getParameterName());

    }

    /**
     * 请求方法不受支持
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<String> handleHttpRequestMethodNotSupportedException(UserNameNotMatchPasswordException e){
        return Result.error(ExceptionEnum.HttpRequestMethodNotSupportedException);
    }

    /**
     * 请求方法参数错误
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<String> handleMethodArgumentTypeMismatchException(UserNameNotMatchPasswordException e){
        return Result.error(ExceptionEnum.MethodArgumentTypeMismatchException);
    }

    /**
     * 登录失败返回异常
     * @param e
     * @return
     */
    @ExceptionHandler(UserNameNotMatchPasswordException.class)
    public Result<String> handleUserNameNotMatchPasswordException(UserNameNotMatchPasswordException e){
        return Result.error(ExceptionEnum.UserNameNotMatchPasswordException);
    }

    /**
     * accessToken过期refreshToken未过期
     * @param e
     * @return
     */
    @ExceptionHandler(AccessTokenExpiredException.class)
    Result<String> handleAccessTokenExpiredException(AccessTokenExpiredException e){
        return Result.error(ExceptionEnum.AccessTokenExpiredException);
    }

    /**
     * refreshToken 已过期
     * @param e
     * @return
     */
    @ExceptionHandler(RefreshTokenExpiredException.class)
    Result<String> handleRefreshTokenExpiredException(RefreshTokenExpiredException e){
        return Result.error(ExceptionEnum.RefreshTokenExpiredException);
    }

    /**
     * 异常处理
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handle(Exception e) {


            return Result.error("1001", e.getMessage());

    }

}
