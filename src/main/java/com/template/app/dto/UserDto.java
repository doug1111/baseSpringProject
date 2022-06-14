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
@ApiModel(value = "用户信息", description = "根据原型去判断必填字段，文档就不写了")
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
	private Long id;

	@ApiModelProperty(value = "用户昵称")
	private String nickName;

	@ApiModelProperty(value = "自我介绍")
	private String description;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "性别 0女 1男")
	private Integer gender;

	@ApiModelProperty(value = "注册IP")
	private String registerIp;

	@ApiModelProperty(value = "生日")
	private String birthday;

	@ApiModelProperty(value = "头像图片")
	private String headPortrait;

	@ApiModelProperty(value = "资料页横幅")
	private String banner;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "帖子数量")
	private Integer contentCount;

	@ApiModelProperty(value = "关注数量")
	private Integer followCount;

	@ApiModelProperty(value = "粉丝数量")
	private Integer fansCount;

	@ApiModelProperty(value = "总积分")
	private BigDecimal points;

	@ApiModelProperty(value = "状态 0 封禁 1正常")
	private Integer status;

	@ApiModelProperty(value = "最后登录时间")
	private Date loginTime;

	@ApiModelProperty(value = "是否关注/是否粉丝   1是 0否  2互相关注")
	private Integer followingFlag;

	private Integer fansFlag;

	@ApiModelProperty(value = "是否拉黑   1是 0否")
	private Integer blackFlag;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "语言(zh_CH或en_US) 默认中文")
	private String language;

	@ApiModelProperty(value = "主题（0白色  1黑色）默认白色")
	private String theme;

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

	@ApiModelProperty(value = "区号")
	private String areaCode;

	@ApiModelProperty(value = "订单数量")
	private Integer orderCount;

	@ApiModelProperty(value = "总算力")
	private String powerNumber;

	@ApiModelProperty(value = "BTC收益")
	private BigDecimal btcAmount;

	@ApiModelProperty(value = "ETH收益")
	private BigDecimal ethAmount;

	@ApiModelProperty(value = "用户是否已完善资料(true需要补充 false已完善)")
	private Boolean checkUserInfo;

}