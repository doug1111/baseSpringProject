package com.template.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.template.app.entity.User;
import com.template.app.service.IAsyncService;
import com.template.app.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 异步服务类
 *
 * @author Doug Liu
 * @since 2022-06-14
 */
@Service
@RequiredArgsConstructor
public class AsyncServiceImpl implements IAsyncService {

    private final IUserService userService;

    @Override
    @Async
    public void createAsyncTask(Long userId) {
        List<User> userList = userService.list(Wrappers.<User>lambdaQuery().isNotNull(User::getNickname));
    }

}