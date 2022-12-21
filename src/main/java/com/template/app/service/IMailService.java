package com.template.app.service;

/**
 * 邮件服务类
 *
 * @author Doug Liu
 * @since 2022-06-14
 */
public interface IMailService {

    /**
     * 发送邮件
     *
     * @param to      发送地址
     * @param content 内容
     */
    void sendSimpleMailMessage(String to, String content);

}