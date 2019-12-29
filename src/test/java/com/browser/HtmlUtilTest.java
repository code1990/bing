//package com.browser;
//
//import com.BingSearchUtil;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.junit.Test;
//
///**
// * @author wei
// * @description
// * @date 2019/12/17
// */
//public class HtmlUtilTest {
//
//
//    public static Document getDefaultBrowser(String url) throws Exception {
//        WebClient webClient = new WebClient();
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setActiveXNative(false);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        webClient.getOptions().setTimeout(10000);
//        HtmlPage htmlPage = null;
//        try {
//            htmlPage = webClient.getPage(url);
//            webClient.waitForBackgroundJavaScript(10000);
//            String htmlString = htmlPage.asXml();
//            return Jsoup.parse(htmlString);
//        } finally {
//            webClient.close();
//        }
//    }
//
//
//
//    @Test
//    public void getInfo123()throws Exception{
//        String url = "https://cn.bing.com/search?q=CONSTRUCTION%20FASTENERS%20factory&qs=n&form=QBRE&sp=-1&pq=construction%20fasteners%20factory&sc=0-30&sk=&cvid=DDE71B0383F7499B90FB113D0AC8025E";
//        Document document=getDefaultBrowser(url);
//        System.out.println(document.toString());
////        Element element =document.getElementById("b_results");
////        System.out.println("===="+element.text());
////        System.out.println(element.attr("value"));
//    }
//}
//
