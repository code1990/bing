package com.browser;

import org.junit.Test;

/**
 * @program: bing
 * @Date: 2019-12-29 17:02
 * @Author: code1990
 * @Description:
 */
public class StringTest {
    String str = "https://aboveboardelectronics.com/camcar/ifasteners_construction.html";
    @Test
    public void testInfo(){
        int length  =str.replace("//","&&").indexOf("/");
        System.out.println(str.substring(0,length));
    }

    @Test
    public void getInfo(){
//        ExecutorService executor = Executors.newFixedThreadPool(3);
//        for (int i = 1; i <= 3; i++) {
//            executor.execute(new ParameterizedThread<>(i, (p) -> {
//                System.out.println(p.toString());
//            }));
//        }
        /*ThreadPoolExecutor executor = new ThreadPoolExecutor(6, 10, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 100; i++) {
            executor.execute(new ParameterizedThread<>(i, (p) -> {
                System.out.println(p.toString());
            }));
        }*/
    }
}
