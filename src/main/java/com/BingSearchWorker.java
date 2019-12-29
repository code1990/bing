package com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei
 * @description
 * @date 2019/12/5
 */
//@Component
public class BingSearchWorker {

    private String userName = System.getProperty("user.name");
    private String filterPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\filter.txt";
    private String logPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\log.txt";
    private List<String> filterList = BingSearchUtil.readTxt(filterPath);
    private String Main_url = "https://cn.bing.com/";
    private int pageSize = 50;
    @Autowired
    private MailService mailService;

    public String filterUrl(String href) {
        String result = href;
        for (int i = 0; i < filterList.size(); i++) {
            String str = filterList.get(i).toLowerCase().trim();
            if (href.toLowerCase().contains(str)) {
                result = null;
                break;
            }
        }
        return result;
    }

    public List<String> getUrlList(String srcWord) {
        String keyword = BingSearchUtil.encodeChar(srcWord);
        List<String> urlList = new ArrayList<>();
        for (int j = 1; j <= pageSize; j++) {
            int searchNum = 1;
            if (j != 1) {
                searchNum = (j - 1) * 14;
            }
            String from = "PERE";
            if (j != 1 && j != 2) {
                from = from + (j - 2);
            }
            String url = Main_url + "?q=" + keyword + "&go=%e6%8f%90%e4%ba%a4&qs=ds&ensearch=1&first=" + searchNum + "&FORM=" + from;
            urlList.add(url);
        }
        return urlList;
    }

    public List<Map<String, String>> getResultList(List<WebElement> list, String srcWord, int pageNumber,int index) {
        List<Map<String, String>> resultList = new ArrayList<>();
        String info = BingSearchUtil.getBrowserName(index)+">>"+srcWord;
        for (int j = 0; j < list.size(); j++) {
            WebElement element = list.get(j);
            String text = element.getText();
            text = text.replaceAll("\n", "").trim();
            text = text.replaceAll("\t", "").trim();
            if (!"".equals(text.trim())) {
                String title = element.findElement(By.tagName("h2")).getText();
                String href = element.findElement(By.tagName("a")).getAttribute("href");
                int length  =href.replace("//","&&").indexOf("/");
                href=href.substring(0,length);
                if (null != filterUrl(href)) {
                    System.out.println(info+">>"+title + "\t" + href);
                    Map<String, String> map = new HashMap<>();
                    map.put("page_num", pageNumber + "");
                    map.put("page_title", text);
                    map.put("page_url", href);
                    resultList.add(map);
                }
            }

        }
        return resultList;
    }

    public List<Map<String, String>> startTask(WebDriver driver, String kw, int index) {
        String srcWord = kw.split(":")[1];
        String number = kw.split(":")[0];
        List<String> urlList = getUrlList(srcWord);
        long startTime = System.currentTimeMillis();
        System.out.println("第【" + number + "】关键字【" + srcWord + "】爬取开始>>>>>>>>>");
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
            driver.get(url);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WebElement> webList = driver.findElement(By.id("b_results")).findElements(By.className("b_algo"));
            list.addAll(getResultList(webList, srcWord, i,index));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("第【" + number + "】关键字【" + srcWord + "】爬取结束<<<<<<<<");
        System.out.println("耗时" + (System.currentTimeMillis() - startTime )/ 1000 + "秒");
        return list;

    }

    public void startTask(BingSearchDao dao,int index) {
        System.out.println("任务"+index+"启动");
        List<String> list = dao.getAllKwList(index);
        WebDriver driver = getDriver(index);
        for (int i = 0; i < list.size(); i++) {
            String kw = list.get(i).trim();
            List<Map<String, String>> resultList = startTask(driver, kw, index);
            String str = kw.split(":")[1];
            String tableName = "kw_" + str.replaceAll(" ", "_");
            dao.dropTable(tableName);
            dao.createTable(tableName);
            dao.batchInsertPage(tableName, resultList);
            dao.updateKwInfo(str, index);
            List<String> logList = BingSearchUtil.readTxt(logPath);
            logList.add(kw+">>ok");
            BingSearchUtil.writeTxt(logPath,logList);
            BingSearchUtil.killBrowser("chrome");
        }
        mailService.sendSimpleMail();
    }

    public WebDriver getDriver(int index) {
        WebDriver driver = null;
        if (index == 1) {
            driver = BingSearchUtil.getChrome(false);
        } else if (index == 2) {
            driver = BingSearchUtil.getFireFox(false);
        } else if (index == 3) {
            driver = BingSearchUtil.getIE();
        }
        return driver;
    }

}

