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

	/** 广告主用户菜单 **/
	@Value("${permission.admin.menu}")
	private String adminMenuList;

	/** 广告主接口权限 **/
	@Value("${permission.admin.uri}")
	private List<String> adminUriList;

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

	/**
	 * 验证码时间长度
	 */
	private static final long CODE_CATCH_TIME = 5 * 60;

	/**
	 * 一个月过期时间
	 */
	private static final String CHINA_CODE = "86";

	/**
	 * 万能验证码
	 */
	private final String DEFAULT_CODE = "999999";

	@Resource
    RedisUtil redisUtil;

	@Autowired
	private IMailService iMailService;

	@Override
	public ResultDTO<UserDto> userDetail(Long userId) {
		UserDto userDto = this.baseMapper.getUserInfo(userId);
		BusinessCheck.trueThrow(userDto == null, 20002);
		//判断用户是否需要完善资料
		userDto.setCheckUserInfo(userDto.getBirthday() == null);
		return ResultDTO.success(userDto);
	}

	@Override
	public LoginDto register(UserRegisterVo userVo) {
		String code = (String) redisUtil.get(CustomConstants.User.REGISTER_EMAIL_KEY + userVo.getEmail());
		if (!DEFAULT_CODE.equals(userVo.getCode())) {
			BusinessCheck.trueThrow(StringUtils.isBlank(code) || !userVo.getCode().equals(code), 20008);
			redisUtil.del(CustomConstants.User.REGISTER_EMAIL_KEY + userVo.getEmail());
		}
		User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, userVo.getEmail()), false);
		BusinessCheck.trueThrow(user != null, 20007);
		User checkName = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickName, userVo.getNickName()), false);
		BusinessCheck.trueThrow(checkName != null, 20012);
		user = new User();
		if (StringUtils.isNotBlank(userVo.getNickName())) {
			user.setNickName(userVo.getNickName());
		} else {
			user.setNickName(userVo.getEmail());
		}
		user.setHeadPortrait(userVo.getHeadPortrait());
		user.setEmail(userVo.getEmail());
		user.setPassword(userVo.getPassword());
		user.setAdmin(userVo.getPassword());
		user.setUserType(0);
		user.setRegisterIp(userVo.getRegisterIp());
		user.setAreaCode(userVo.getAreaCode());
		user.setLanguage("zh_CN");
		user.setTheme("0");
		user.setPrivateKey(UUID.randomUUID().toString().toUpperCase());
		user = this.setPassword(user);
		this.save(user);
		return createUserToRedis(user, false);
	}

	private LoginDto createUserToRedis(User user, Boolean rememberMe) {
		UUID uuid = UUID.randomUUID();
		String token = CustomConstants.User.TOKEN_KEY + uuid;
		LoginDto loginDto = new LoginDto();
		loginDto.setId(user.getId());
		loginDto.setNickName(user.getNickName());
		loginDto.setToken(uuid.toString());
		loginDto.setRoleId(user.getUserType().toString());
		//判断用户是否需要完善资料
		if (user.getBirthday() != null) {
			loginDto.setCheckUserInfo(false);
		} else {
			loginDto.setCheckUserInfo(true);
		}
		//根据角色配置菜单
		if (user.getUserType() == 1) {
			//管理员权限
			loginDto.setApiList(adminUriList);
			//管理员菜单
			loginDto.setMenuList(adminMenuList);
		} else {
			//普通用户权限
			loginDto.setApiList(userUriList);
			//普通用户菜单
			loginDto.setMenuList(userMenuList);
		}
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

	public User setPassword(User user) {
		String salt = PasswordUtil.generateSalt();
		user.setSalt(salt);
		user.setPassword(encrypt(user));
		return user;
	}

	public String encrypt(User user) {
		return PasswordUtil.encryptPassword(user.getPassword(), user.getSalt());
	}

	@Override
	public LoginDto doLogin(String email, String password, String mobile, String code, Boolean rememberMe) {
		if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password)) {
			User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, email), false);
			BusinessCheck.trueThrow(user == null, 20002);
			BusinessCheck.trueThrow(user.getStatus() == 0, 20011);
			BusinessCheck.trueThrow(!PasswordUtil.validatePassword(password, user.getSalt(), user.getPassword()), 20010);
			return createUserToRedis(user, rememberMe);
		} else if (StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(code)) {
			User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getMobile, mobile), false);
			BusinessCheck.trueThrow(user == null, 20002);
			BusinessCheck.trueThrow(user.getStatus() == 0, 20011);
			String redisCode = (String) redisUtil.get(CustomConstants.User.REGISTER_MOBILE_KEY + mobile);
			if (!DEFAULT_CODE.equals(code)) {
				BusinessCheck.trueThrow(StringUtils.isBlank(code) || !code.equals(redisCode), 20008);
				redisUtil.del(CustomConstants.User.REGISTER_MOBILE_KEY + mobile);
			}
			return createUserToRedis(user, rememberMe);
		} else {
			throw new BizException(20009);
		}
	}

	@Override
	public void forgotPassword(UserRegisterVo userVo) {
		User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, userVo.getEmail()), false);
		BusinessCheck.trueThrow(user == null, 20002);
		String code = (String) redisUtil.get(CustomConstants.User.REGISTER_EMAIL_KEY + userVo.getEmail());
		if (!DEFAULT_CODE.equals(userVo.getCode())) {
			BusinessCheck.trueThrow(StringUtils.isBlank(code) || !userVo.getCode().equals(code), 20008);
			redisUtil.del(CustomConstants.User.REGISTER_EMAIL_KEY + userVo.getEmail());
		}
		user.setPassword(userVo.getPassword());
		user = this.setPassword(user);
		this.updateById(user);
	}

	@Override
	public void sendEmailCode(String email) {
		String code = MathUtil.randomDigitNumber(6);
		redisUtil.set(CustomConstants.User.REGISTER_EMAIL_KEY + email, code, CODE_CATCH_TIME);
		log.debug("邮件验证码发送成功,验证邮箱是：{},验证码是：{}", email, code);
		//执行发送邮箱
		iMailService.sendSimpleMailMessage(email, code);
	}

	@Override
	public void sendMobileCode(String areaCode, String mobile) {
		String code = MathUtil.randomDigitNumber(6);
		redisUtil.set(CustomConstants.User.REGISTER_MOBILE_KEY + mobile, code, CODE_CATCH_TIME);
		log.debug("手机验证码发送成功,验证手机是：{},验证码是：{}", mobile, code);
	}

	@Override
	public IPage<UserDto> getAdminUserList(Integer page, Integer pageSize, Long userName, String startDate, String endDate) {
		return this.baseMapper.getAdminUserList(new Page<>(page, pageSize), userName, startDate, endDate);
	}

	@Override
	public void updateUser(UserRegisterVo userVo) {
		User user = this.getById(userVo.getId());
		BusinessCheck.trueThrow(user == null, 20002);
		if (!user.getNickName().equals(userVo.getNickName())) {
			User checkName = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickName, userVo.getNickName()), false);
			BusinessCheck.trueThrow(checkName != null, 20012);
		}
		//如果填了邮件验证码就该邮箱
		if (StringUtils.isNotBlank(userVo.getCode()) && StringUtils.isNotBlank(userVo.getEmail())) {
			String code = (String) redisUtil.get(CustomConstants.User.REGISTER_EMAIL_KEY + userVo.getEmail());
			if (!DEFAULT_CODE.equals(userVo.getCode())) {
				BusinessCheck.trueThrow(StringUtils.isBlank(code) || !userVo.getCode().equals(code), 20008);
				redisUtil.del(CustomConstants.User.REGISTER_EMAIL_KEY + userVo.getEmail());
			}
			user.setEmail(userVo.getEmail());
			//如果填了密码就修改密码
			if (StringUtils.isNotBlank(userVo.getPassword())) {
				user.setPassword(userVo.getPassword());
				this.setPassword(user);
			}
		}
		//如果填了手机验证码就改手机
		if (StringUtils.isNotBlank(userVo.getCode()) && StringUtils.isNotBlank(userVo.getMobile())) {
			String code = (String) redisUtil.get(CustomConstants.User.REGISTER_MOBILE_KEY + userVo.getMobile());
			if (!DEFAULT_CODE.equals(userVo.getCode())) {
				BusinessCheck.trueThrow(StringUtils.isBlank(code) || !userVo.getCode().equals(code), 20008);
				redisUtil.del(CustomConstants.User.REGISTER_MOBILE_KEY + userVo.getMobile());
			}
			user.setMobile(userVo.getMobile());
			user.setAreaCode(userVo.getAreaCode());
			//如果填了密码就修改密码
			if (StringUtils.isNotBlank(userVo.getPassword())) {
				user.setPassword(userVo.getPassword());
				this.setPassword(user);
			}
		}
		if (userVo.getBirthday() != null) {
			user.setBirthday(DateUtil.stringToDateShort(userVo.getBirthday()));
		}
		user.setGender(userVo.getGender());
		user.setDescription(userVo.getDescription());
		user.setNickName(userVo.getNickName());
		user.setBanner(userVo.getBanner());
		user.setHeadPortrait(userVo.getHeadPortrait());
		user.setTheme(userVo.getTheme());
		user.setLanguage(userVo.getLanguage());
		user.setRegion(userVo.getRegion());
		user.setCountry(userVo.getCountry());
		user.setCountryName(userVo.getCountryName());
		user.setProvince(userVo.getProvince());
		user.setProvinceName(userVo.getProvinceName());
		user.setCity(userVo.getCity());
		user.setCityName(userVo.getCityName());
		user.setDistrict(userVo.getDistrict());
		user.setDistrictName(userVo.getDistrictName());
		this.updateById(user);
	}
}