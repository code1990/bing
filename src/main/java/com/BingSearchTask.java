package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author wei
 * @description
 * @date 2019/12/5
 */
@Component
@EnableScheduling
public class BingSearchTask {

    @Autowired
    private BingSearchDao dao;
    private String userName = System.getProperty("user.name");
    private String kwPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\keyword.txt";
    private String dropPath = "C:\\Users\\" + userName + "\\Desktop\\bing\\dropTable.txt";
    private List<String> kwList = BingSearchUtil.readTxt(kwPath);


    @PostConstruct
    public void job() {

        Long count1 = dao.checkKwInfo(1);
        Long count2 = dao.checkKwInfo(2);
        Long count3 = dao.checkKwInfo(3);
        List<String> dropList = dao.getAllPageInfoByType(kwList,"drop");
        BingSearchUtil.writeTxt(dropPath,dropList);
        if(count1==0 && count2==0&& count3==0){
            List<Map<String, String>> list = dao.getKwMapInfo(kwList);
            List<List<Map<String, String>>> mapList = dao.getListMapGroup(list,3);
            for (int i = 0; i <mapList.size() ; i++) {
                List<Map<String, String>> list2 = mapList.get(i);
                dao.insertKwInfo(list2,i+1);
            }
            List<String> createTableList = dao.getAllPageInfoByType(kwList, "create");
            for (int i=1; i<=3; i++) {
                Thread thread = new Thread(new BingSearchThread(dao,i));
                thread.setName(BingSearchUtil.getBrowserName(i));
                thread.start();
            }
        }else{
            String min1 = dao.getMinKwInfo(1);
            String min2 = dao.getMinKwInfo(2);
            String min3 = dao.getMinKwInfo(3);
            if(min1!=null && min2!=null && min3!=null){
                for (int i=1; i<=3; i++) {
                    Thread thread = new Thread(new BingSearchThread(dao,i));
                    thread.setName(BingSearchUtil.getBrowserName(i));
                    thread.start();
                }
            }else{
                if(min1!=null){
                    Thread thread = new Thread(new BingSearchThread(dao,1));
                    thread.setName(BingSearchUtil.getBrowserName(1));
                    thread.start();
                }
                if(min2!=null){
                    Thread thread = new Thread(new BingSearchThread(dao,2));
                    thread.setName(BingSearchUtil.getBrowserName(2));
                    thread.start();
                }
                if(min3!=null){
                    Thread thread = new Thread(new BingSearchThread(dao,3));
                    thread.setName(BingSearchUtil.getBrowserName(3));
                    thread.start();
                }
            }
        }

    }

}

