package com.template.app.service;

/**
 * 异步服务类
 *
 * @author Doug Liu
 * @since 2022-06-14
 */
public interface IAsyncService {

    /**
     * 执行异步任务
     *
     * @param userId 用户ID
     */
    void createAsyncTask(Long userId);

}
