package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wei
 * @description
 * @date 2019/12/10
 */
@Service
public class MailService {
    Logger logger = LoggerFactory.getLogger(BingApplication.class);
    @Value("${spring.mail.username}")
    private String from;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 EEEE HH:mm:ss:SSS");

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);//收信人
        message.setSubject(subject);//主题
        message.setText(content);//内容
        message.setFrom(from);//发信人

        mailSender.send(message);
    }

    public void sendSimpleMail() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 E");

        sendSimpleMail("961902118@qq.com", sdf.format(new Date())+"爬虫任务执行完成", "数据爬取完成");
        logger.info("邮件发送成功！");
        System.out.println("邮件发送成功！");
    }
}

