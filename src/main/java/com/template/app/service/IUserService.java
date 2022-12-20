package com.template.app.service;

import com.template.app.dto.LoginDTO;
import com.template.app.dto.UserDTO;
import com.template.app.dto.UserRegisterVO;
import com.template.app.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author Doug Liu
 * @since 2022-12-20
 */
public interface IUserService extends IService<User> {

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return UserDTO
     */
    UserDTO getUserDetail(Long userId);

    /**
     * 注册
     *
     * @param userVo 用户注册信息
     * @return LoginDTO
     */
    LoginDTO register(UserRegisterVO userVo);

    /**
     * 登录操作
     * @param nickname 昵称
     * @param password 密码
     * @param rememberMe 记住我
     * @return LoginDTO
     */
    LoginDTO doLogin(String nickname, String password, Boolean rememberMe);

    /**
     * 修改用户信息
     * @param userVo 用户注册信息
     */
    void updateUser(UserRegisterVO userVo);

    /**
     * 检查用户昵称重名
     * @param nickName 昵称
     */
    void checkNickName(String nickName);
}
