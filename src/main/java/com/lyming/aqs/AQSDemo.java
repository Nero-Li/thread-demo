package com.lyming.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName AQSDemo
 * @Description TODO
 * @Author lyming
 * @Date 2020/6/19 11:37 下午
 **/
public class AQSDemo {
    public static void main(String[] args) {
        new Semaphore(5);
        new CountDownLatch(5);
        new ReentrantLock();
    }
}
