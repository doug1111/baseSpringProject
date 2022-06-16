package com.template.app.controller.admin;

import com.template.app.mongoEntity.AdminUser;
import com.template.common.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理相关API
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Slf4j
@Api(tags = { "管理相关API" })
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final MongoTemplate mongoTemplate;

    @PostMapping("/createAdminUser")
    @ApiOperation(value = "create admin user")
    public ResultDTO<AdminUser> createAdminUser(@RequestBody AdminUser adminUserVo) {
        mongoTemplate.save(adminUserVo);
        return ResultDTO.success(adminUserVo);
    }

}
