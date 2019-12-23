//package com.browser;
//
//import com.BingSearchUtil;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//
///**
// * @author wei
// * @description
// * @date 2019/12/17
// */
//public class ChromeTest {
//    @Test
//    public void getInfo123() throws Exception{
//        long start = System.currentTimeMillis();
//        WebDriver driver = BingSearchUtil.getChrome(true);
//        String url = "http://www.baidu.com";
//        driver.get(url);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        WebElement webElement = driver.findElement(By.id("su"));
//        System.out.println(webElement.getAttribute("value"));
//        System.out.println(System.currentTimeMillis()-start);
//    }
//}
//
