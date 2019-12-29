//package com;
//
//import org.openqa.selenium.WebDriver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author wei
// * @description
// * @date 2019/12/5
// */
//@Component
//@EnableScheduling
//public class BingSearchTaskbak {
//    Logger logger = LoggerFactory.getLogger(BingApplication.class);
//    private String userName = System.getProperty("user.name");
//    private String kwPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\keyword.xls";
//    private String errPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\err.txt";
//    private String stopBat = "C:\\Users\\" + userName + "\\Desktop\\bing\\stop.bat";
//    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 EEEE HH:mm:ss:SSS");
//    private List<String> kwList = ExcelUtils.readXls(kwPath);
//    int index = 1;
//    int allCount = kwList.size();
//    int pageSize = 50;
//
//    @Autowired
//    private MailService mailService;
//
//    @Scheduled(cron = "0/20 * * * * ?")
//    @PostConstruct
//    public void job() {
//        int start = 0;
//        int page = 0;
//
//        String restartInfo = BingSearchUtil.readTxt(errPath).get(0);
//        if (!"0_0".equals(restartInfo.trim())) {
//            start = new Integer(restartInfo.split("_")[0]);
//            page = new Integer(restartInfo.split("_")[1]);
//        }
//        if ((start == allCount - 1) && (page == pageSize - 1)) {
//            System.out.println("请设置【" + errPath + "】文件内容:0_0,开始新的任务");
//            logger.info("请设置【" + errPath + "】文件内容:0_0,开始新的任务");
//            return;
//        }
//        long startTime = System.currentTimeMillis();
//        try {
//            System.out.println(sdf.format(new Date()) + "关键字数量为【" + kwList.size() + "】");
//            logger.info(sdf.format(new Date()) + "关键字数量为【" + kwList.size() + "】");
//
//            System.out.println(sdf.format(new Date()) + " 任务第" + index + "次启动!");
//            logger.info(sdf.format(new Date()) + " 任务第" + index + "次启动!");
//            WebDriver driver = BingSearchUtil.getChrome(false);
//            for (int i = start; i < kwList.size(); i++) {
//                String srcWord = kwList.get(i).trim();
//                BingSearchWorker worker = new BingSearchWorker();
//                worker.startTask(driver, srcWord, i, page, allCount);
//                page = 0;
//            }
//            sendSimpleMail();
//            BingSearchUtil.writeTxt(errPath, "0_0");
//            BingSearchUtil.killChrome();
//            WindowsUtil.main(null);
//        } catch (Exception e) {
//            WindowsUtil.main(null);
//            System.out.println(sdf.format(new Date()) + "\t任务运行异常【" + e.toString() + "】,20s后自动恢复.....");
//            logger.info(sdf.format(new Date()) + "\t任务运行异常【" + e.toString() + "】,20s后自动恢复.....");
//            index++;
//            BingSearchUtil.killChrome();
//        }
//        System.out.println(sdf.format(new Date()) + "\t任务总共耗时约" + ((System.currentTimeMillis() - startTime) / 1000) + "秒");
//        logger.info(sdf.format(new Date()) + "\t任务总共耗时约" + ((System.currentTimeMillis() - startTime) / 1000) + "秒");
//    }
//
//    public void sendSimpleMail() {
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 E");
//
//        String str = sdf2.format(new Date())+"\n关键字数量:"+allCount
//                +"\n网页数量:"+pageSize*allCount+"\n数据条数:约"+pageSize*allCount*13+"\n过滤数量:未统计";
//        logger.info(str);
//        mailService.sendSimpleMail("961902118@qq.com", sdf.format(new Date())+"爬虫任务执行完成", "大家好，这是我用springboot进行发送邮件测试");
//        logger.info("邮件发送成功！");
//        System.out.println("邮件发送成功！");
//    }
//}
//
