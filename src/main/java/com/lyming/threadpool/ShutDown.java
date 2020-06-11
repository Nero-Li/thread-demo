package com.lyming.threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ShutDown
 * @Description 演示关闭线程池
 * @Author lyming
 * @Date 2020/6/10 6:40 下午
 **/
public class ShutDown {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new ShutDownTask());
        }
        Thread.sleep(3500);
        List<Runnable> runnableList = executorService.shutdownNow();
//        System.out.println(executorService.isShutdown());
//        executorService.shutdown();
//        System.out.println(executorService.isShutdown());
//        System.out.println(executorService.isTerminated());
//        boolean flag = executorService.awaitTermination(10, TimeUnit.SECONDS);
        //会阻塞到这里
//        System.out.println("awaitTermination:"+flag);
//        Thread.sleep(10000);
//        System.out.println(executorService.isTerminated());

        //提交新任务报错
//        executorService.execute(new ShutDownTask());
    }

}

class ShutDownTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("被中断了"+Thread.currentThread().getName());
        }
    }
}
