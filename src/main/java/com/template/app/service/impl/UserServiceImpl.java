package com.template.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.template.app.dto.LoginDTO;
import com.template.app.dto.UserDTO;
import com.template.app.dto.UserRegisterVO;
import com.template.app.entity.User;
import com.template.app.entity.enums.CustomConstants;
import com.template.app.mapper.UserMapper;
import com.template.app.service.IUserService;
import com.template.common.BizException;
import com.template.util.BeanUtil;
import com.template.util.BusinessCheck;
import com.template.util.PasswordUtil;
import com.template.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Doug Liu
 * @since 2022-12-20
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 过期时间24小时
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60;

    /**
     * 一个月过期时间
     */
    private static final long LONG_EXPIRE_TIME = 30 * 24 * 60 * 60;

    /**
     * 普通用户菜单
     **/
    @Value("${permission.user.menu}")
    private String userMenuList;

    /**
     * 普通接口权限
     **/
    @Value("${permission.user.uri}")
    private List<String> userUriList;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public UserDTO getUserDetail(Long userId) {
        UserDTO userDTO = this.baseMapper.getUserInfo(userId);
        BusinessCheck.trueThrow(userDTO == null, 20002);
        return userDTO;
    }

    @Override
    public LoginDTO register(UserRegisterVO userRegisterVO) {
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickname, userRegisterVO.getNickname()), false);
        BusinessCheck.trueThrow(user != null, 20007);
        User checkName = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickname, userRegisterVO.getNickname()), false);
        BusinessCheck.trueThrow(checkName != null, 20012);
        user = new User();
        user.setNickname(userRegisterVO.getNickname());
        setPassword(user, userRegisterVO.getPassword());
        this.save(user);
        return createUserToRedis(user, false);
    }

    @Override
    public LoginDTO doLogin(String nickname, String password, Boolean rememberMe) {
        if (StringUtils.isNotBlank(nickname) && StringUtils.isNotBlank(password)) {
            User user = this.getOne(Wrappers.<User>lambdaQuery()
                    .eq(User::getNickname, nickname));
            BusinessCheck.trueThrow(user == null, 20002);
            BusinessCheck.trueThrow(!PasswordUtil.validatePassword(password, user.getSalt(), user.getPassword()), 20010);
            return createUserToRedis(user, rememberMe);
        } else {
            throw new BizException(20009);
        }
    }

    @Override
    public void updateUser(UserRegisterVO userRegisterVO) {
        User user = this.getById(userRegisterVO.getId());
        BusinessCheck.trueThrow(user == null, 20002);
        if (!user.getNickname().equals(userRegisterVO.getNickname())) {
            User checkName = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickname, userRegisterVO.getNickname()), false);
            BusinessCheck.trueThrow(checkName != null, 20012);
        }
        user.setNickname(userRegisterVO.getNickname());
        this.updateById(user);
    }

    @Override
    public void checkNickname(String nickname) {
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickname, nickname), false);
        BusinessCheck.trueThrow(user != null, 20012);
    }

    private LoginDTO createUserToRedis(User user, Boolean rememberMe) {
        UUID uuid = UUID.randomUUID();
        String token = CustomConstants.User.TOKEN_KEY + uuid;
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(user.getId());
        loginDTO.setNickname(user.getNickname());
        loginDTO.setToken(uuid.toString());
        //普通用户权限
        loginDTO.setApiList(userUriList);
        //普通用户菜单
        loginDTO.setMenuList(userMenuList);
        Map<String, Object> map = BeanUtil.beanToMap(loginDTO);
        if (rememberMe != null && rememberMe) {
            redisUtil.hmset(token, map, LONG_EXPIRE_TIME);
        } else {
            redisUtil.hmset(token, map, EXPIRE_TIME);
        }
        //给前端返回的时候清空api列表
        loginDTO.setApiList(null);
        return loginDTO;
    }

    private void setPassword(User user, String password) {
        user.setPassword(password);
        String salt = PasswordUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(encrypt(user));
    }

    private String encrypt(User user) {
        return PasswordUtil.encryptPassword(user.getPassword(), user.getSalt());
    }

}
