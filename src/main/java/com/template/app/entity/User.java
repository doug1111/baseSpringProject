package com.template.app.entity;

import com.template.app.entity.base.BaseEntity;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author Doug Liu
 * @since 2022-12-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "user")
@ApiModel(value = "User对象", description = "用户信息")
public class User extends BaseEntity {

    @ApiModelProperty("昵称")
    @Field("nickname")
    private String nickname;

    @ApiModelProperty("密码")
    @Field("password")
    private String password;

    @ApiModelProperty("盐")
    @Field("salt")
    private String salt;

    @ApiModelProperty("生日")
    @Field("birthday")
    private Date birthday;

}
