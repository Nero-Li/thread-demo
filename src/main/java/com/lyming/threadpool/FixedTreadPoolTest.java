package com.lyming.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName FixedTreadPoolTest
 * @Description 演示FixedTreadPool
 * @Author lyming
 * @Date 2020/6/9 10:47 下午
 **/
public class FixedTreadPoolTest {

    public static void main(String[] args) {
        //用的是LinkedBlockingQueue,所以任务可以无限堆叠,一定情况下会造成OOM
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}
