package com.template.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.template.app.dto.LoginDto;
import com.template.app.dto.UserDto;
import com.template.app.dto.UserRegisterVo;
import com.template.app.entity.User;
import com.template.app.entity.enums.CustomConstants;
import com.template.app.mapper.UserMapper;
import com.template.app.service.IMailService;
import com.template.app.service.IUserService;
import com.template.common.BizException;
import com.template.common.ResultDTO;
import com.template.util.BusinessCheck;
import com.template.util.DateUtil;
import com.template.util.MathUtil;
import com.template.util.PasswordUtil;
import com.template.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用户信息 服务实现类
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	/** 普通用户菜单 **/
	@Value("${permission.user.menu}")
	private String userMenuList;

	/** 普通接口权限 **/
	@Value("${permission.user.uri}")
	private List<String> userUriList;

	/**
	 * 过期时间24小时
	 */
	private static final long EXPIRE_TIME = 24 * 60 * 60;

	/**
	 * 一个月过期时间
	 */
	private static final long LONG_EXPIRE_TIME = 30 * 24 * 60 * 60;

	@Resource
    RedisUtil redisUtil;

	@Override
	public UserDto getUserDetail(Long userId) {
		UserDto userDto = this.baseMapper.getUserInfo(userId);
		BusinessCheck.trueThrow(userDto == null, 20002);
		return userDto;
	}

	@Override
	public LoginDto register(UserRegisterVo userVo) {
		User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickName, userVo.getNickName()), false);
		BusinessCheck.trueThrow(user != null, 20007);
		User checkName = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickName, userVo.getNickName()), false);
		BusinessCheck.trueThrow(checkName != null, 20012);
		user = new User();
        user.setNickName(userVo.getNickName());
        user.setPassword(userVo.getPassword());
		user = this.setPassword(user);
		this.save(user);
		return createUserToRedis(user, false);
	}

	@Override
	public LoginDto doLogin(String nickname, String password, Boolean rememberMe) {
		if (StringUtils.isNotBlank(nickname) && StringUtils.isNotBlank(password)) {
            User user = this.getOne(Wrappers.<User>lambdaQuery()
                    .eq(User::getNickName, nickname));
			BusinessCheck.trueThrow(user == null, 20002);
			BusinessCheck.trueThrow(!PasswordUtil.validatePassword(password, user.getSalt(), user.getPassword()), 20010);
			return createUserToRedis(user, rememberMe);
		} else {
			throw new BizException(20009);
		}
	}

	@Override
	public void updateUser(UserRegisterVo userVo) {
		User user = this.getById(userVo.getId());
		BusinessCheck.trueThrow(user == null, 20002);
		if (!user.getNickName().equals(userVo.getNickName())) {
			User checkName = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickName, userVo.getNickName()), false);
			BusinessCheck.trueThrow(checkName != null, 20012);
		}
		user.setNickName(userVo.getNickName());
		this.updateById(user);
	}

    @Override
    public void checkNickName(String nickName) {
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickName, nickName), false);
        BusinessCheck.trueThrow(user != null, 20012);
    }

    private LoginDto createUserToRedis(User user, Boolean rememberMe) {
        UUID uuid = UUID.randomUUID();
        String token = CustomConstants.User.TOKEN_KEY + uuid;
        LoginDto loginDto = new LoginDto();
        loginDto.setId(user.getId());
        loginDto.setNickName(user.getNickName());
        loginDto.setToken(uuid.toString());
        //普通用户权限
        loginDto.setApiList(userUriList);
        //普通用户菜单
        loginDto.setMenuList(userMenuList);
        Map<String, Object> map = BeanUtil.beanToMap(loginDto);
        if (rememberMe != null && rememberMe) {
            redisUtil.hmset(token, map, LONG_EXPIRE_TIME);
        } else {
            redisUtil.hmset(token, map, EXPIRE_TIME);
        }
        //给前端返回的时候清空api列表
        loginDto.setApiList(null);
        return loginDto;
    }

    private User setPassword(User user) {
        String salt = PasswordUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(encrypt(user));
        return user;
    }

    private String encrypt(User user) {
        return PasswordUtil.encryptPassword(user.getPassword(), user.getSalt());
    }

}