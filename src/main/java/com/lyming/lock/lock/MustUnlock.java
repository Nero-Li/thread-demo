package com.lyming.lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName MustUnlock
 * @Description Lock不会像Synchronized一样,异常的时候自动释放锁
 * 所有最佳实践是在finally中主动释放锁
 * @Author lyming
 * @Date 2020/6/13 6:58 下午
 **/
public class MustUnlock {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            //获取本锁保护的资源
            System.out.println(Thread.currentThread().getName()+"开始执行任务");
        }finally {
            lock.unlock();
        }
    }
}
