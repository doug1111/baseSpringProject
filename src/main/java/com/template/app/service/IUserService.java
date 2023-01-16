package com.template.app.service;

import com.template.app.dto.LoginDTO;
import com.template.app.dto.UserDTO;
import com.template.app.dto.UserRegisterVO;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author Doug Liu
 * @since 2022-12-20
 */
public interface IUserService {

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return UserDTO
     */
    UserDTO getUserDetail(String userId);

    /**
     * 注册
     *
     * @param userRegisterVO 用户注册信息
     * @return LoginDTO
     */
    LoginDTO register(UserRegisterVO userRegisterVO);

    /**
     * 登录操作
     *
     * @param nickname   昵称
     * @param password   密码
     * @param rememberMe 记住我
     * @return LoginDTO
     */
    LoginDTO doLogin(String nickname, String password, Boolean rememberMe);

    /**
     * 修改用户信息
     *
     * @param userRegisterVO 用户注册信息
     */
    void updateUser(UserRegisterVO userRegisterVO);

    /**
     * 检查用户昵称重名
     *
     * @param nickname 昵称
     */
    void checkNickname(String nickname);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return Boolean
     */
    Boolean deleteUser(String id);

    /**
     * 保存用户
     *
     * @param userInfo 用户信息
     * @return String
     */
    String saveUserInfo(Object userInfo);

}
