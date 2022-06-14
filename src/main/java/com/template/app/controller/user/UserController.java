package com.template.app.controller.user;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.template.util.BusinessCheck;
import com.template.util.ContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.template.app.dto.UserRegisterVo;
import com.template.app.entity.User;
import com.template.app.service.IUserService;
import com.template.common.ResultDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文档实力
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Api(tags = { "文档示例" })
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	private final IUserService iUserService;

	@PostMapping("/updateUser")
	@ApiOperation(value = "POST实体请求示例")
	public void updateUser(@RequestBody UserRegisterVo userVo) {
		log.info("请求方法开始-->方法名:【updateUser】-->参数:userVo = {}", userVo.toString());
		userVo.setId(ContextUtil.getContext().getId());
		iUserService.updateUser(userVo);
	}

	@GetMapping("/update")
	@ApiOperation(value = "GET大于5个参数请求示例")
	public ResultDTO<UserRegisterVo> update(UserRegisterVo userVo) {
		log.info("请求方法开始-->方法名:【updateUser】-->参数:userVo = {}", userVo.toString());
		userVo.setId(ContextUtil.getContext().getId());
		iUserService.updateUser(userVo);
		return ResultDTO.success(userVo);
	}


	@GetMapping("/checkNickName")
	@ApiOperation(value = "GET普通传参示例")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "nickName", value = "昵称", dataTypeClass = String.class, required = true)
	})
	public void checkNickName(String nickName) {
		User user = iUserService.getOne(Wrappers.<User>lambdaQuery().eq(User::getNickName, nickName), false);
		BusinessCheck.trueThrow(user != null, 20012);
	}

	@GetMapping("/sendEmailCode/{email}")
	@ApiOperation(value = "GET方法URL传参示例")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "email", value = "邮箱", dataTypeClass = String.class, required = true)
	})
	public void sendEmailCode(@PathVariable String email) {
		iUserService.sendEmailCode(email);
	}

}