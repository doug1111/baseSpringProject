package com.template.common;

/**
 * RestAPI 返回数据代码与信息封装
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
public interface BaseEnum {

	/**
	 * 获取结果代码
	 * @return String
	 */
	Integer getCode();

	/**
	 * 获取结果信息
	 * @return String
	 */
	String getMessage();

}
