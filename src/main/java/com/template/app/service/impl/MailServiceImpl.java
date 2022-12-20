package com.template.app.service.impl;

import com.template.app.service.IMailService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 邮件服务类
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Service
@Slf4j
public class MailServiceImpl implements IMailService {

	private static final String PROJECT = "XX应用 验证码";

	private static final String TEMPLATE = "【mining】您的验证码为：%s，该验证码5分钟内有效，请勿泄露于他人。";

	@Override
	@Async
	public void sendSimpleMailMessage(String to, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom(sender);
		message.setTo(to);
		message.setSubject(PROJECT);
		message.setText(String.format(TEMPLATE, content));
		try {
//			mailSender.send(message);
		} catch (Exception e) {
			log.error("发送简单邮件时发生异常!", e);
		}
	}

}