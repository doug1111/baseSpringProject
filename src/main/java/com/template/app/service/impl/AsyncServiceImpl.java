package com.template.app.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.template.app.entity.User;
import com.template.app.service.IAsyncService;
import com.template.app.service.IUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步服务类
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Service
@RequiredArgsConstructor
public class AsyncServiceImpl implements IAsyncService {


    private final IUserService iUserService;

    @Override
    @Async
    public void createRecommendAd(Long userId) {
        List<User> userList = iUserService.list(Wrappers.<User>lambdaQuery().isNotNull(User::getBirthday));
    }
}