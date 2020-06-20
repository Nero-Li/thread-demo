package com.lyming.aqs;

import java.util.AbstractQueue;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @ClassName OneShotLatch
 * @Description 自己用AQS实现一个简单协作器
 * @Author lyming
 * @Date 2020/6/20 9:51 下午
 **/
public class OneShotLatch {

    private final Sync sync = new Sync();

    public void await(){
        sync.tryAcquireShared(0);
    }

    public void signal() {
        sync.tryReleaseShared(0);
    }

    private class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected int tryAcquireShared(int arg) {
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        OneShotLatch oneShotLatch = new OneShotLatch();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"尝试获取latch，获取失败那就等待");
                    oneShotLatch.await();
                    System.out.println("开闸放行"+Thread.currentThread().getName()+"继续运行");
                }
            }).start();
        }
        Thread.sleep(5000);
        oneShotLatch.signal();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"尝试获取latch，获取失败那就等待");
                oneShotLatch.await();
                System.out.println("开闸放行"+Thread.currentThread().getName()+"继续运行");
            }
        }).start();
    }
}
