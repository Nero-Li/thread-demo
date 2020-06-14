package com.lyming.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName GetHoldCount
 * @Description 获取锁的次数
 * @Author lyming
 * @Date 2020/6/13 11:33 下午
 **/
public class GetHoldCount {
    private  static ReentrantLock lock =  new ReentrantLock();

    public static void main(String[] args) {
        System.out.println(lock.getHoldCount());
        lock.lock();
        System.out.println(lock.getHoldCount());
        lock.lock();
        System.out.println(lock.getHoldCount());
        lock.lock();
        System.out.println(lock.getHoldCount());
        lock.unlock();
        System.out.println(lock.getHoldCount());
        lock.unlock();
        System.out.println(lock.getHoldCount());
        lock.unlock();
        System.out.println(lock.getHoldCount());
    }
}
