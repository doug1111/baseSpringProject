package com.template.common;


import com.template.util.MessageUtil;
import lombok.Getter;

/**
 * 业务自定义异常
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
@Getter
public class BizException extends RuntimeException {

	private BaseEnum rstEnum;
	private Integer code;
	private String message;

	public BizException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BizException(BaseEnum rstEnum) {
		super(rstEnum.getMessage());
		this.rstEnum = rstEnum;
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