package com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wei
 * @description
 * @date 2019/12/5
 */
public class BingSearchWorker {
    Logger logger = LoggerFactory.getLogger(BingApplication.class);
    private String userName = System.getProperty("user.name");
    private String filterPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\filter.txt";
    private String errPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\err.txt";
    private List<String> filterList = BingSearchUtil.readTxt(filterPath);
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
    int pageSize = 50;
    private String Main_url = "https://cn.bing.com/";
    private String resultXlsPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\";
    private String resultTxtPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\txt\\";
    private String tmpTxtPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\tmp\\tmp.txt";
    List<String> allList = new ArrayList<>();
    int filterSize = 0;
    public void startTask(WebDriver driver, String srcWord, int number, int page,int allCount) {
        List<String> urlList = getUrlList(srcWord);
        String fileNameXls = "bing_" + srcWord + ".xls";
        String fileNameTxt = "bing_" + srcWord + ".txt";
        if (page != 0) {
            List<String> tmpList = BingSearchUtil.readTxt(tmpTxtPath);
            System.out.println("备份数据恢复完成,共"+tmpList.size()+"条");
            logger.info("备份数据恢复完成,共"+tmpList.size()+"条");
            allList.addAll(tmpList);
        }
        System.out.println();
        System.out.println(sdf.format(new Date()) + "\t总共【"+allCount+"】个关键字,第【" + (number+1) + "】个关键字【" + srcWord +
                "】, 总共【"+pageSize+"】页爬取开始");
        logger.info(sdf.format(new Date()) + "\t总共【"+allCount+"】个关键字,第【" + (number+1) + "】个关键字【" + srcWord +
                "】, 总共【"+pageSize+"】页爬取开始");
        long startTime = System.currentTimeMillis();
        for (int i = page; i < urlList.size(); i++) {
            String url = urlList.get(i);
            driver.get(url);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BingSearchUtil.writeTxt(errPath, number + "_" + i);
            List list = driver.findElement(By.id("b_results")).findElements(By.className("b_algo"));
            List<String> resultList = getListInfo(list, srcWord, number, i,allCount);
            allList.addAll(resultList);
            if(i!=urlList.size()-1){
                BingSearchUtil.writeTxt(resultTxtPath + fileNameTxt, allList);
                BingSearchUtil.writeTxt(tmpTxtPath, allList);
                ExcelUtils.writeXls(resultXlsPath + fileNameXls, allList);
                System.out.println();
            }
        }
        BingSearchUtil.writeTxt(resultTxtPath + fileNameTxt, allList);
        BingSearchUtil.writeTxt(tmpTxtPath, allList);
        ExcelUtils.writeXls(resultXlsPath + fileNameXls, allList);
        BingSearchUtil.writeTxt(errPath, number + "_0");
        System.out.println();
        String filterInfo = "";
        if (filterSize != 0) {
            filterInfo = "过滤【" + filterSize + "】条,";
        }
        System.out.println(sdf.format(new Date()) + "\t总共【"+allCount+"】个关键字,第【" + (number+1) + "】个关键字【" + srcWord + "】,总共【"+pageSize+"】页,总共【" + allList.size() + "】条!" + filterInfo + "耗时" + ((System.currentTimeMillis() - startTime)) +
                "毫秒");
        logger.info(sdf.format(new Date()) + "\t总共【"+allCount+"】个关键字,第【" + (number+1) + "】个关键字【" + srcWord + "】,总共【"+pageSize+"】页,总共【" + allList.size() + "】条!" + filterInfo + "耗时" + ((System.currentTimeMillis() - startTime)) +
                "毫秒");
    }

    public String filterUrl(String href) {
        for (String str : filterList) {
            if (!href.contains(str.trim())) {
                return href;
            }
        }
        return null;
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

    public List<String> getListInfo(List<WebElement> list, String srcWord, int number, int page,int allCount) {
        List<String> resultList = new ArrayList<>();
        System.out.println("第【" + (number+1) + "】个关键字【" + srcWord +
                "】,第【" + (page + 1) + "】页爬取开始");
        logger.info("第【" + (number+1) + "】个关键字【" + srcWord +
                "】,第【" + (page + 1) + "】页爬取开始");
        long startTime = System.currentTimeMillis();

        for (int j = 0; j < list.size(); j++) {
            WebElement element = list.get(j);
            String text = element.getText();
            text = text.replaceAll("\n","").trim();
            text = text.replaceAll("\t","").trim();
            if(!"".equals(text.trim())){
                String title =element.findElement(By.tagName("h2")).getText();
                String href = element.findElement(By.tagName("a")).getAttribute("href");
                if (srcWord.contains(":.")) {
                    String split = srcWord.split(":")[1];
                    href = href.split(split)[0] + split;
                }
                if (null == filterUrl(href)) {
                    filterSize++;
                } else {
                    System.out.println(title + "\t" + href);
                    logger.info(title + "\t" + href);
                    resultList.add(title + "\t" + href);
                }
            }

        }

        System.out.println("第【" + (number+1) + "】个关键字【" + srcWord +
                "】,第【" + (page + 1) + "】页爬取结束,共"+list.size()+"条,耗时"+ ((System.currentTimeMillis() - startTime)) +
                "毫秒");
        logger.info("第【" + (number+1) + "】个关键字【" + srcWord +
                "】,第【" + (page + 1) + "】页爬取结束,共"+list.size()+"条,耗时"+ ((System.currentTimeMillis() - startTime)) +
                "毫秒");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /*@Test
    public void getInfo(){
        String url = "https://cn.bing.com/search?q=fasteners+site%3a.af&qs=HS&sc=8-0&cvid=3AA81546DEFF4F45BF3B7983338EFF6D&FORM=QBLH&sp=1";
        String keyword = "1_1_fasteners site:.af";
        WebDriver driver = BingSearchUtil.getDefaultChromeDriver(false);
        getPageInfo(driver,url,keyword);
    }*/

    /*@Test
    public void testInfo123() {
        String userName = System.getProperty("user.name");
        String kwPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\keyword.xls";
        List<String> kwList = ExcelUtils.readXls(kwPath);
        WebDriver driver = BingSearchUtil.getDefaultChromeDriver(false);
        for (int i = 0; i < kwList.size(); i++) {
            System.out.println(i);
            String srcWord = kwList.get(i);
            BingSearchWorker worker = new BingSearchWorker();
            worker.startTask(driver, srcWord, i, 0);
        }
    }*/
}

