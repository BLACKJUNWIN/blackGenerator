package com.black.common.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Component
public class MailUtils {
    private static JavaMailSender javaMailSender;

    public static void sendVerifyCode(String toMail,String code){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("初心搜:邮箱验证");
            helper.setText("<button><a href='https://blackjun.cn/Beginner/'>你好邮箱测试</a></button>",true);
            helper.setFrom("彭骏豪" + '<' + "blackjunemail@qq.com" + '>');
            helper.setTo(toMail);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
