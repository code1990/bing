//package com;
//
//import org.junit.Test;
//import org.openqa.selenium.WebDriver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author wei
// * @description
// * @date 2019/12/11
// */
//public class Ticket implements Runnable {
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
//    private static int kwNum = 0;
//    private static List<String> list = new ArrayList<>();
//    int page=0;
//    private boolean flag = true;
//    WebDriver chromeDriver = BingSearchUtil.getChrome(false);
//    WebDriver firefoxDriver = BingSearchUtil.getFireFox(false);
//    private synchronized void sale() {
//        if (kwNum >= kwList.size()) {
//            flag = false;
//            return;
//        }
////        WebDriver driver;
////        if(kwNum%2==0){
////            driver=chromeDriver;
////        }else {
////            driver=firefoxDriver;
////        }
//
////        String srcWord = kwList.get(kwNum).trim();
////        BingSearchWorker worker = new BingSearchWorker();
////        worker.startTask(chromeDriver, srcWord, kwNum, page, allCount);
////        kwNum++;
////        if(kwNum++<kwList.size()){
////            String srcWord2 = kwList.get(kwNum).trim();
////            BingSearchWorker worker2 = new BingSearchWorker();
////            worker.startTask(firefoxDriver, srcWord2, kwNum, page, allCount);
////            System.out.println(Thread.currentThread().getName() + "爬取了第【" + (kwNum + 1) + "】个关键字【" + kwList.get(kwNum) + "】，还剩【" +
////                    (allCount - kwNum - 1) + "】个关键字。");
////        }
////        page = 0;
////        kwNum++;
//    }
//
//    @Override
//    public void run() {
//
//        while (flag) {
//            sale();
//            try {
//
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Test
//    public void testInfo() {
//        for (int i = 0; i < kwList.size(); i++) {
//            System.out.println(kwList.get(i));
//        }
//    }
//
//    public static void main(String[] args) {
//
//        Ticket t = new Ticket();
//        Thread th1 = new Thread(t, "Firefox浏览器");
//        Thread th2 = new Thread(t, "Chrome浏览器");
////        Thread th3 = new Thread(t,"IE浏览器");
////        Thread th4 = new Thread(t,"Edge浏览器");
////        Thread th5 = new Thread(t,"Opera浏览器");
////        Thread th6 = new Thread(t,"PhantomJS浏览器");
//
//        th1.start();
//        th2.start();
////        th3.start();
////        th4.start();
////        th5.start();
////        th6.start();
//
//    }
//
//}
//
