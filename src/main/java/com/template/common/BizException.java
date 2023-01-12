package com.template.common;


import com.template.util.MessageUtil;
import lombok.Getter;

/**
 * 业务自定义异常
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
@Getter
public class BizException extends RuntimeException {

    /**
     * 异常代码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String message;

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(Integer code) {
        this.code = code;
        this.message = MessageUtil.get(String.valueOf(code));
    }

    public BizException(String message) {
        super(message);
        this.code = 100001;
        this.message = message;
    }

}