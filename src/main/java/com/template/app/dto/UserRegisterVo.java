package com.template.app.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录返回实体
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Data
@ApiModel(value = "UserRegisterVo", description = "根据原型去判断必填字段，文档就不写了")
public class UserRegisterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = "用户昵称")
	private String nickName;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "确认密码")
	private String rePassword;

}