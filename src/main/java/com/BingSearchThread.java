package com;

import java.util.concurrent.CountDownLatch;

/**
 * @program: bing
 * @Date: 2019-12-29 18:46
 * @Author: code1990
 * @Description:
 */
public class BingSearchThread  implements Runnable {

    private BingSearchDao dao;
    /*private final CountDownLatch countDownLatch;*/
    private int index;

    public BingSearchThread(BingSearchDao dao, int index) {
        this.dao = dao;
        /*this.countDownLatch = countDownLatch;*/
        this.index = index;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+">>启动");
        BingSearchWorker worker = new BingSearchWorker();
        worker.startTask(dao,index);
    }

}
