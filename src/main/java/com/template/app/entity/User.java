package com.template.app.entity;

import com.template.app.entity.base.BaseEntity;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author Doug Liu
 * @since 2022-12-20
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "用户信息")
public class User extends BaseEntity {

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("生日")
    private Timestamp birthday;
}
