package com.template.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.template.app.dto.UserDTO;
import com.template.app.entity.User;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author Doug Liu
 * @since 2022-12-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 检索用户
     * @param page     页面
     * @param userId   用户ID
     * @param keywords 关键词
     * @return IPage<UserDTO>
     */
    IPage<UserDTO> search(Page<Object> page, Long userId, String keywords);

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return UserDTO
     */
    UserDTO getUserInfo(Long userId);

}
