package com.template.app.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.template.app.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户信息
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("temp_user")
@ApiModel(value = "User对象", description = "用户信息")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "盐")
	private String salt;

	@ApiModelProperty(value = "超级权限")
	private String admin;

	@ApiModelProperty(value = "自我介绍")
	private String description;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "区号")
	private String areaCode;

	@ApiModelProperty(value = "性别 0女 1男")
	private Integer gender;

	@ApiModelProperty(value = "类型 0普通用户  1管理员")
	private Integer userType;

	@ApiModelProperty(value = "状态 0 封禁 1正常")
	private Integer status;

	@ApiModelProperty(value = "注册IP")
	private String registerIp;

	@ApiModelProperty(value = "生日")
	private Date birthday;

	@ApiModelProperty(value = "头像图片")
	private String headPortrait;

	@ApiModelProperty(value = "资料页横幅")
	private String banner;

	@ApiModelProperty(value = "地区（0境外  1国内）")
	private Integer region;

	@ApiModelProperty(value = "国家")
	private String country;

	@ApiModelProperty(value = "国家名")
	private String countryName;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "省名")
	private String provinceName;

	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "市名")
	private String cityName;

	@ApiModelProperty(value = "区/县")
	private String district;

	@ApiModelProperty(value = "区/县名")
	private String districtName;

	@ApiModelProperty(value = "用户私钥")
	private String privateKey;

	@ApiModelProperty(value = "语言(zh_CH或en_US) 默认中文")
	private String language;

	@ApiModelProperty(value = "主题（0白色  1黑色）默认白色")
	private String theme;

}