package com.template.config;


import java.util.Date;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import org.springframework.stereotype.Component;

/**
 * 自动填充添加时间和修改时间
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
        Date today = new Date();
		setFieldValByName("createTime", today, metaObject);
        setFieldValByName("updateTime", today, metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		setFieldValByName("updateTime", new Date(), metaObject);
	}
}
