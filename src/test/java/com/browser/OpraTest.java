//package com.browser;
//
//import com.BingSearchUtil;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.opera.OperaDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @author wei
// * @description
// * @date 2019/12/17
// */
//public class OpraTest {
//    public WebDriver driver;
//
//    @Test
//    public void getInfo123() throws Exception {
//        System.setProperty("webdriver.opera.driver", "C:\\driver\\operadriver.exe");
//        long start = System.currentTimeMillis();
//        OperaDriver driver = new OperaDriver();
////        WebDriver driver = BingSearchUtil.getOperaDriver();
//        String url = "https://cn.bing.com/search?q=CONSTRUCTION%20FASTENERS%20factory&qs=n&form=QBRE&sp=-1&pq=construction%20fasteners%20factory&sc=0-30&sk=&cvid=DDE71B0383F7499B90FB113D0AC8025E&rdr=1&rdrig=1637B5F146254E43B2ABA49F020927CB";
//        driver.get(url);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        WebElement webElement = driver.findElement(By.id("su"));
//        System.out.println(webElement.getAttribute("value"));
//        System.out.println(System.currentTimeMillis() - start);
//    }
//
//    //启动IE浏览器
//
//    public WebDriver setupIE(String test_url) {
//
//        System.setProperty("webdriver.ie.driver", "C:\\driver\\IEDriverServer.exe");
//
//        //如下两行为对浏览器的特殊设置
//
//        DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
//
//        dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//
//        driver = new InternetExplorerDriver(dc);
//
//        driver.get(test_url);
//
//        driver.manage().window().maximize();
//
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
////        driver.s
//        return driver;
//
//    }
//
//    @Test
//    public void testInfo(){
//        setupIE("https://cn.bing.com/search?q=CONSTRUCTION%20FASTENERS%20factory&qs=n&form=QBRE&sp=-1&pq=construction%20fasteners%20factory&sc=0-30&sk=&cvid=DDE71B0383F7499B90FB113D0AC8025E&rdr=1&rdrig=1637B5F146254E43B2ABA49F020927CB");
//        System.out.println();
//    }
//}
//
