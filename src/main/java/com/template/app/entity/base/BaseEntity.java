package com.template.app.entity.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;


/**
 * 默认实体
 *
 * @author Doug Liu
 * @since 2022-06-14
 */
@Data
@ApiModel(value = "BaseEntity对象", description = "基础实体信息")
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @Field("_id")
    @Id
    private ObjectId id;

    @ApiModelProperty(value = "创建时间")
    @Field("createTime")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @Field("updateTime")
    private Date updateTime;

    @ApiModelProperty(value = "删除 0否 1是")
    @Field("deleteFlag")
    private Integer deleteFlag;

}