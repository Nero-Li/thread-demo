package com.lyming.lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName LockInterruptibly
 * @Description TODO
 * @Author lyming
 * @Date 2020/6/13 7:46 下午
 **/
public class LockInterruptibly implements Runnable{

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        LockInterruptibly lockInterruptibly = new LockInterruptibly();
        Thread t1 = new Thread(lockInterruptibly);
        Thread t2 = new Thread(lockInterruptibly);
        t2.start();
        t1.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"尝试获取锁");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "获取到了锁");
                //为了不让第二个线程轻易获取到锁
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"睡眠期间被中断了");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "获得锁期间被中断了");
        }
    }
}
