package com;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.annotations.VisibleForTesting;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author wei
 * @description
 * @date 2019/12/5
 */
public class BingSearchUtil {
    static {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        System.setProperty("webdriver.gecko.driver","C:\\driver\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin","F:\\soft\\Firefox\\firefox.exe");
        System.setProperty("webdriver.ie.driver", "C:\\driver\\IEDriverServer.exe");
        //控制台关闭日志
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }
    public static void writeTxt(String filePath, String content)  {
        FileWriter fw = null;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(filePath + "写入ok!");

    }

    public static void writeTxt(String filePath, List<String> list) {
        FileWriter fw = null;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            StringBuilder sb = new StringBuilder();
            for (String str : list) {
                sb.append(str + "\n");
            }
            fw.write(sb.toString());
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getName() + ">>>备份写入ok!");

    }

    public static List<String> readTxt(String filePath)  {
        List<String> resultList = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        resultList = new ArrayList<String>();
        File file = new File(filePath);
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String str = "";

            while ((str = bufferedReader.readLine()) != null) {
                if (!"".equals(str.trim())) {
                    resultList.add(str);
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static WebDriver getChrome(boolean show){
        //设置浏览器可选项
        ChromeOptions options = new ChromeOptions();
        if(!show){
            //隐藏浏览器
            options.addArguments("--headless");
        }
        //单进程运行Google Chrome
        options.addArguments("–single-process");
        //禁止加载图片
        options.addArguments("-–disable-images");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-javascript");
        options.addArguments("blink-settings=imagesEnabled=false");
        //禁止加载Css
        return new ChromeDriver(options);
    }

    public static void killBrowser(String type){
        String command = "taskkill /F /IM ";
        String command2 = "";
        if(type.equals("iexplorer")){
            command = command+"iexplorer.exe";
        } else if(type.equals("safari")){
            command = command+"safari.exe";
        }else if(type.equals("firefox")){
            command = command+"firefox.exe";
        }else if(type.equals("chrome")){
            command = command+"chrome.exe";
            command2="tskill chromedriver";
        }
        try {
            Runtime.getRuntime().exec(command);
            if (!"".equals(command2)){
                Runtime.getRuntime().exec(command2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String encodeChar(String keyword){
        keyword = keyword.replaceAll(" ","+");
        keyword = keyword.replaceAll("\"","%22");
        keyword = keyword.replaceAll("#","%23");
        keyword = keyword.replaceAll("%","%25");
        keyword = keyword.replaceAll("&","%26");
        keyword = keyword.replaceAll("\\(","%28");
        keyword = keyword.replaceAll("\\)","%29");
        keyword = keyword.replaceAll("\\+","%2B");
        keyword = keyword.replaceAll(",","%2C");
        keyword = keyword.replaceAll("/","%2F");
        keyword = keyword.replaceAll(":","%3A");
        keyword = keyword.replaceAll(";","%3B");
        keyword = keyword.replaceAll("<","%3C");
        keyword = keyword.replaceAll("=","%3D");
        keyword = keyword.replaceAll(">","%3E");
        keyword = keyword.replaceAll("\\?","%3F");
        keyword = keyword.replaceAll("@","%40");
        keyword = keyword.replaceAll("\\|","%7C");
        return keyword;
    }

    /**
     *  provide 3 methods to set firefox options
     * @param show
     * @return
     */
    public static WebDriver getFireFox(boolean show){
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        firefoxOptions.setHeadless(!show);
        firefoxOptions.addArguments("--disable-gpu");
        firefoxOptions.addArguments("window-size=1200x600");
        firefoxOptions.addPreference("permissions.default.image","2");
        firefoxOptions.addPreference("permissions.default.stylesheet","2");
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
        return driver;
    }

    public static WebDriver getIE(){


        //如下两行为对浏览器的特殊设置

        DesiredCapabilities dc = DesiredCapabilities.internetExplorer();

        dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        return new InternetExplorerDriver(dc);

    }


    public static String getBrowserName(int index){
        String name="";
        if(index ==1){
            name="Chrome浏览器";
        } else if(index ==2){
            name="Firefox浏览器";
        } else if(index ==3){
            name="IE浏览器";
        }
        return name;
    }
}

