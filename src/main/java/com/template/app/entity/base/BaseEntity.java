package com.template.app.entity.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 默认实体
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "ID")
	private Long id;

	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private Timestamp createTime;

	@TableField(fill = FieldFill.UPDATE)
	@ApiModelProperty(value = "更新时间")
	private Timestamp updateTime;

	@ApiModelProperty(value = "删除 0否 1是")
	private Integer deleteFlag;

}