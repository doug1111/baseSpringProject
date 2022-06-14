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
	 * @param userId
	 * @return
	 */
	ResultDTO<UserDto> userDetail(Long userId);

	/**
	 * 注册
	 *
	 * @param userVo
	 * @return
	 */
	LoginDto register(UserRegisterVo userVo);


	/**
	 * 登录操作
	 * @param email 邮箱
	 * @param password 密码
	 * @param mobile 电话
	 * @param code 验证码
	 * @param rememberMe 记住我
	 * @return
	 */
	LoginDto doLogin(String email, String password, String mobile, String code, Boolean rememberMe);

	/**
	 * 忘记密码
	 * @param userVo
	 */
	void forgotPassword(UserRegisterVo userVo);

	/**
	 * 发送邮件验证码
	 * @param email
	 */
	void sendEmailCode(String email);

    /**
     * 发送手机验证码
     * @param areaCode
     * @param mobile
     */
	void sendMobileCode(String areaCode, String mobile);

    /**
     * 获取用户列表
     * @param page
     * @param pageSize
     * @param userName
     * @param startDate
     * @param endDate
     * @return IPage<UserDto>
     */
    IPage<UserDto> getAdminUserList(Integer page, Integer pageSize, Long userName, String startDate, String endDate);

    /**
     * 修改用户信息
     * @param userVo
     */
	void updateUser(UserRegisterVo userVo);
}