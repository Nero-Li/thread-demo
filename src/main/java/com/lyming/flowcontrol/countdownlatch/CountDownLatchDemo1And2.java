package com.lyming.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CountDownLatchDemo1And2
 * @Description 模拟100米跑步，5名选手都准备好了，只等裁判员一声令下，所有人同时开始跑步。当所有人都到终点后，比赛结束。
 * @Author lyming
 * @Date 2020/6/18 9:08 下午
 **/
public class CountDownLatchDemo1And2 {

    public static void main(String[] args) throws InterruptedException {
        //裁判员
        CountDownLatch begin = new CountDownLatch(1);
        //结束标识
        CountDownLatch end = new CountDownLatch(5);
        //线程池
        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            //优化编号
            final int no =i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("No." + no + "准备完毕，等待发令枪");
                    try {
                        begin.await();
                        System.out.println("No." + no + "开始跑步了");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("No." + no + "跑到终点了");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        end.countDown();
                    }
                }
            };
            service.submit(runnable);
        }
        //裁判员检查发令枪...
        Thread.sleep(5000);
        System.out.println("发令枪响，比赛开始！");
        begin.countDown();
        end.await();
        System.out.println("所有人到达终点，比赛结束");
    }
}
