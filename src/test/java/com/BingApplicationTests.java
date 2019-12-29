//package com;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author wei
// * @description
// * @date 2019/12/10
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BingApplicationTests {
////    @Autowired
////    private BingSearchDao bingSearchDao;
//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//    @Test
//    public void contextLoads() {
//        System.out.println(1);
//    }
//
//
//    @Test
//    public void testInfo(){
//        List<Map<String,String>> entities = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            Map<String,String> map = new HashMap<>();
//            map.put(":page_num","1");
//            map.put(":page_title","1");
//            map.put(":page_url","1");
//            entities.add(map);
//        }
//        String insertSql="INSERT INTO hello(page_num,page_title,page_url,create_time) VALUES (:page_num,:page_title,:page_url,now());";
//        insertSql = insertSql.replace("hello","kw01_hello");
//        SqlParameterSource[] params =       SqlParameterSourceUtils.createBatch(entities.toArray());
//        namedParameterJdbcTemplate.batchUpdate(insertSql, params);
////        killChrome();
//    }
//}