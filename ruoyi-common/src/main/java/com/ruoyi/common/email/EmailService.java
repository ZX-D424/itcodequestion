package com.ruoyi.common.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件发送服务类，用于发送简单文本邮件
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 构造函数，注入 JavaMailSender 实例
     * @param mailSender Spring 提供的邮件发送器
     */
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 发送简单文本邮件的方法
     * @param to 收件人邮箱地址
     * @param subject 邮件主题
     * @param text 邮件内容
     */
    public void sendSimpleMessage(String to, String subject, String text) {
        // 创建简单邮件消息对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        // 设置收件人邮箱地址
        message.setTo(to);
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setText(text);
        // 发送邮件
        mailSender.send(message);
    }
}

