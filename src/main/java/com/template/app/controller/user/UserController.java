package com.template.app.controller.user;


import com.template.app.dto.UserDto;
import com.template.util.BusinessCheck;
import com.template.util.ContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.template.app.dto.UserRegisterVo;
import com.template.app.service.IUserService;
import com.template.common.ResultDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文档示例
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

	private final IUserService userService;

	@PostMapping("/updateUser")
	@ApiOperation(value = "POST实体请求示例")
	public void updateUser(@RequestBody UserRegisterVo userVo) {
		log.info("请求方法开始-->方法名:【updateUser】-->参数:userVo = {}", userVo.toString());
		userVo.setId(ContextUtil.getContext().getId());
		userService.updateUser(userVo);
	}

	@GetMapping("/getUser")
	@ApiOperation(value = "GET请求示例")
	public UserDto getUserInfo() {
		log.info("请求方法开始-->方法名:【getUserInfo】-->参数:userId = {}", ContextUtil.getContext().getId());
        return userService.getUserDetail(ContextUtil.getContext().getId());
	}


	@GetMapping("/checkNickName")
	@ApiOperation(value = "GET普通传参示例")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "nickName", value = "昵称", dataTypeClass = String.class, required = true)
	})
	public void checkNickName(String nickName) {
        userService.checkNickName(nickName);
	}

}