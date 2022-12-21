package com.template.config.advice;

import com.template.common.BizException;
import com.template.common.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return Object
     */
    @ExceptionHandler(value = BizException.class)
    public Object handleCustomException(BizException e) {
        log.error("BizException===>{}", e.getMessage());
        return ResultDTO.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理系统异常
     *
     * @param e 系统异常
     * @return Object
     */
    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception e) {
        log.error("SYSTEM_EXCEPTION===>{}", e.getMessage());
        e.printStackTrace();
        return ResultDTO.fail(10001);
    }

}