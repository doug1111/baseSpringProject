package com.template.app.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.template.app.dto.UserDto;
import com.template.app.entity.User;
import org.apache.ibatis.annotations.Param;


/**
 * 用户信息Mapper
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 检索用户
	 * @param objectPage
	 * @param userId
	 * @param keywords
	 * @return
	 */
	IPage<UserDto> search(Page<Object> objectPage, Long userId, String keywords);

	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	UserDto getUserInfo(Long userId);

    /**
     * 根据用户Id和电话获取用户
     * @param userId        用户ID
     * @param purchaserInfo 用户电话
     * @return
     */
    List<User> getUserByIdAndMobile(@Param("userId") Long userId, @Param("purchaserInfo") String purchaserInfo);
    
    /**
     * 用户列表
     * @param page
     * @param userName
     * @param startDate
     * @param endDate
     * @return IPage<UserDto>
     */
    IPage<UserDto> getAdminUserList(Page<UserDto> page, Long userName, String startDate, String endDate);
}