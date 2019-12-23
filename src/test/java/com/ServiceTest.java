//package com;
//
//import com.MailService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ServiceTest {
//    Logger logger = LoggerFactory.getLogger(MailService.class);
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 E");
//    @Autowired
//    private MailService mailService;
//
//    /**
//     * 发送简单纯文本邮件
//     */
//    @Test
//    public void sendSimpleMail() {
//
//
//        String str = sdf2.format(new Date())+"\n关键字数量:"+2000+"\n网页数量:"+10000+"\n数据条数:"+13000+"\n过滤数量:"+100;
//        logger.info(str);
//        mailService.sendSimpleMail("961902118@qq.com", sdf.format(new Date())+"爬虫任务执行完成", "大家好，这是我用springboot进行发送邮件测试");
//        logger.info("邮件发送成功");
//        System.out.println(11111);
//        System.out.println();
//        assert 1!=1;
//    }
//
//
//}
