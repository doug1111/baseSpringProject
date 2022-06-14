package com.template.app.dto;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(value = "登录返回实体")
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
	private Long id;

	@ApiModelProperty(value = "用户昵称")
	private String nickName;

	@ApiModelProperty(value = "token")
	private String token;

	@ApiModelProperty(value = "权限")
	private String roleId;

	@ApiModelProperty(value = "用户是否已完善资料(true需要补充 false已完善)")
	private Boolean checkUserInfo;

	@ApiModelProperty(value = "菜单列表")
	private String menuList;

	@ApiModelProperty(value = "接口列表")
	private List<String> apiList;
}