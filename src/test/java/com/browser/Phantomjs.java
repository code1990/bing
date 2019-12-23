//package com.browser;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
///**
// * @program: bing
// * @Date: 2019-12-18 22:24
// * @Author: code1990
// * @Description:
// */
//public class Phantomjs {
//    public static String getAjaxCotnent(String url) throws IOException {
//        Runtime rt = Runtime.getRuntime();
//        Process p = rt.exec("C:\\driver\\phantomjs.exe C:\\Users\\admin\\Desktop\\code.js "+url);
//        //这里我的codes.js是保存在c盘下面的phantomjs目录
//        InputStream is = p.getInputStream();
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        StringBuffer sbf = new StringBuffer();
//        String tmp = "";
//        while((tmp = br.readLine())!=null){
//            sbf.append(tmp);
//        }
//        System.out.println(sbf.toString());
//        return sbf.toString();
//    }
//
//    public static void main(String[] args) throws IOException {
//        String name= System.getProperty("user.dir");
//        System.out.println(name);
//        getAjaxCotnent("https://www.jianshu.com/p/41d5e08af0a7");
//    }
//}
