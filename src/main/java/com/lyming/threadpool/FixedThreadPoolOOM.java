package com.lyming.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName FixedThreadPoolOOM
 * @Description 演示FixedThreadPool出错的情况
 * @Author lyming
 * @Date 2020/6/9 10:57 下午
 **/
public class FixedThreadPoolOOM {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new SubThread());
        }
    }
}

class SubThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(100000);

        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}