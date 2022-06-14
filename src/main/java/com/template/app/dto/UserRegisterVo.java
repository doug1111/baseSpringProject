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

	@ApiModelProperty(value = "自我介绍")
	private String description;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "确认密码")
	private String rePassword;

	@ApiModelProperty(value = "联系电话")
	private String mobile;

	@ApiModelProperty(value = "验证码")
	private String code;

	@ApiModelProperty(value = "生日")
	private String birthday;

	@ApiModelProperty(value = "注册IP")
	private String registerIp;

	@ApiModelProperty(value = "性别 0女 1男")
	private Integer gender;

	@ApiModelProperty(value = "兴趣标签(逗号分隔的ID字符串)")
	private String interests;

	@ApiModelProperty(value = "头像图片")
	private String headPortrait;

	@ApiModelProperty(value = "语言(zh_CH或en_US) 默认中文")
	private String language;

	@ApiModelProperty(value = "主题（0白色  1黑色）默认白色")
	private String theme;

	@ApiModelProperty(value = "区号")
	private String areaCode;

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

	@ApiModelProperty(value = "第三方类型(SINA、WECHAT、GOOGLE等)")
	private String thirdType;

	@ApiModelProperty(value = "openId")
	private String openId;
}