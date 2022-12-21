package com.template.app.controller.user;

import com.template.app.dto.UserDTO;
import com.template.app.dto.UserRegisterVO;
import com.template.app.service.IUserService;
import com.template.util.ContextUtil;
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
    @ApiOperation(value = "POST实体请求示例")
    public void updateUser(@RequestBody UserRegisterVO userRegisterVO) {
        log.info("请求方法开始-->方法名:【updateUser】-->参数:userRegisterVO = {}", userRegisterVO.toString());
        userRegisterVO.setId(ContextUtil.getContext().getId());
        userService.updateUser(userRegisterVO);
    }

    @GetMapping("/getUser")
    @ApiOperation(value = "GET请求示例")
    public UserDTO getUserInfo() {
        log.info("请求方法开始-->方法名:【getUserInfo】-->参数:userId = {}", ContextUtil.getContext().getId());
        return userService.getUserDetail(ContextUtil.getContext().getId());
    }


    @GetMapping("/checkNickname")
    @ApiOperation(value = "GET普通传参示例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "昵称", dataTypeClass = String.class, required = true)
    })
    public void checkNickname(String nickname) {
        userService.checkNickname(nickname);
    }

}
