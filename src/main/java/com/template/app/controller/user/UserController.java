package com.template.app.controller.user;

import com.template.app.dto.LoginDTO;
import com.template.app.dto.UserDTO;
import com.template.app.dto.UserRegisterVO;
import com.template.app.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author Doug Liu
 * @since 2022-12-20
 */
@Api(tags = {"文档示例"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final IUserService userService;

    @PostMapping("/updateUser")
    @ApiOperation(value = "POST实体请求示例，更新用户")
    public void updateUser(@RequestBody UserRegisterVO userRegisterVO) {
        log.info("请求方法开始-->方法名:【updateUser】-->参数:userRegisterVO = {}", userRegisterVO.toString());
//        userRegisterVO.setId(ContextUtil.getContext().getId());
        userService.updateUser(userRegisterVO);
    }

    @PostMapping("/register")
    @ApiOperation(value = "POST实体请求示例，创建用户")
    public LoginDTO register(@RequestBody UserRegisterVO userRegisterVO) {
        log.info("请求方法开始-->方法名:【register】-->参数:userRegisterVO = {}", userRegisterVO.toString());
//        userRegisterVO.setId(ContextUtil.getContext().getId());
        return userService.register(userRegisterVO);
    }

    @GetMapping("/getUser")
    @ApiOperation(value = "GET请求示例，获取用户")
    public UserDTO getUserInfo() {
//        log.info("请求方法开始-->方法名:【getUserInfo】-->参数:userId = {}", ContextUtil.getContext().getId());
//        return userService.getUserDetail(ContextUtil.getContext().getId());
        return new UserDTO();
    }

    @GetMapping("/checkNickname")
    @ApiOperation(value = "GET普通传参示例，校验用户昵称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "昵称", dataTypeClass = String.class, required = true)
    })
    public void checkNickname(@RequestParam String nickname) {
        userService.checkNickname(nickname);
    }

}
