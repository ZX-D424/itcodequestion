package com.ruoyi.common.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送服务类，支持发送 HTML 格式邮件
 */
@Service
public class HtmlEmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;
    /**
     * 构造函数，注入 JavaMailSender 实例
     * @param mailSender Spring 提供的邮件发送器
     */
    @Autowired
    public HtmlEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 发送 HTML 格式邮件的方法
     * @param to 收件人邮箱地址
     * @param subject 邮件主题
     * @param htmlContent HTML 格式的邮件内容
     * @throws MessagingException 处理邮件消息时可能抛出的异常
     */
    public void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException {
        // 创建 MimeMessage 对象
        MimeMessage message = mailSender.createMimeMessage();
        // 创建 MimeMessageHelper 对象，第二个参数 true 表示支持多部分内容
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        message.setFrom(from);
        // 设置收件人邮箱地址
        helper.setTo(to);
        // 设置邮件主题
        helper.setSubject(subject);
        // 设置邮件内容为 HTML 格式
        helper.setText(htmlContent, true);
        // 发送邮件
        mailSender.send(message);
    }
}
