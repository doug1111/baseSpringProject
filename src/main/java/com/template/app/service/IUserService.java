package com.template.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.template.app.dto.LoginDto;
import com.template.app.dto.UserDto;
import com.template.app.dto.UserRegisterVo;
import com.template.app.entity.User;
import com.template.common.ResultDTO;

/**
 * 用户服务类
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
public interface IUserService extends IService<User> {

	/**
	 * 获取用户详情
	 *
	 * @param userId 用户ID
	 * @return UserDto
	 */
    UserDto getUserDetail(Long userId);

	/**
	 * 注册
	 *
	 * @param userVo 用户注册信息
	 * @return LoginDto
	 */
	LoginDto register(UserRegisterVo userVo);

	/**
	 * 登录操作
	 * @param nickname 昵称
	 * @param password 密码
	 * @param rememberMe 记住我
	 * @return LoginDto
	 */
	LoginDto doLogin(String nickname, String password, Boolean rememberMe);

    /**
     * 修改用户信息
     * @param userVo 用户注册信息
     */
	void updateUser(UserRegisterVo userVo);

    /**
     * 检查用户昵称重名
     * @param nickName 昵称
     */
    void checkNickName(String nickName);
}