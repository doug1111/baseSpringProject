package com.template.app.service.impl;

import com.template.app.service.IAsyncService;
import com.template.app.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步服务类
 *
 * @author Doug Liu
 * @since 2022-06-14
 */
@Service
@RequiredArgsConstructor
public class AsyncServiceImpl implements IAsyncService {

    @Override
    @Async
    public void createAsyncTask(Long userId) {
        System.out.println("Start Asynchronous task.");
    }

}