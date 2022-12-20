package com.template.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Data
@ApiModel(value = "用户信息", description = "用户信息")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
	private Long id;

	@ApiModelProperty(value = "用户昵称")
	private String nickName;

}