package com;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wei
 * @description
 * @date 2019/12/5
 */
@Component
@EnableScheduling
public class BingSearchTask {
    private String userName = System.getProperty("user.name");
    private String kwPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\keyword.xls";
    private String errPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\err.txt";
    private String stopBat = "C:\\Users\\" + userName + "\\Desktop\\bing\\stop.bat";
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 EEEE HH:mm:ss:SSS");
    private List<String> kwList = ExcelUtils.readXls(kwPath);
    int index = 1;
    int allCount= kwList.size();
    int pageSize = 30;
    @Scheduled(cron = "0/20 * * * * ?")
    @PostConstruct
    public void job() {
        int start = 0;
        int page = 0;
        int size = 0;

        String restartInfo = BingSearchUtil.readTxt(errPath).get(0);
        if (!"0_0".equals(restartInfo.trim())) {
            start = new Integer(restartInfo.split("_")[0]);
            page = new Integer(restartInfo.split("_")[1]);
        }
        if ((start == allCount-1) && (page==pageSize-1)) {
            System.out.println("请设置【"+errPath+"】文件内容:0_0,开始新的任务");
            return;
        }
        long startTime = System.currentTimeMillis();
        try {
            System.out.println(sdf.format(new Date()) + "关键字数量为【" + kwList.size() + "】");

            System.out.println(sdf.format(new Date()) + " 任务第" + index + "次启动!");
            WebDriver driver = BingSearchUtil.getDefaultChromeDriver(false);
            System.out.println(start+"\t"+kwList.size());
            for (int i = start; i < kwList.size(); i++) {
//                if(page<pageSize){
                    String srcWord = kwList.get(i).trim();
                    BingSearchWorker worker = new BingSearchWorker();
                    worker.startTask(driver, srcWord, i, page,allCount);
                    page=0;
//                }
            }
            BingSearchUtil.writeTxt(errPath,(allCount-1)+"_"+(pageSize-1));
            BingSearchUtil.killChrome();
            WindowsUtil.main(null);
        } catch (Exception e) {
            WindowsUtil.main(null);
            System.out.println(sdf.format(new Date()) + "\t任务运行异常【" + e.toString() + "】,20s后自动恢复.....");
            index++;
            BingSearchUtil.killChrome();
        }
        System.out.println(sdf.format(new Date()) + "\t任务总共耗时约" + ((System.currentTimeMillis() - startTime) / 1000) + "秒");
    }

}

