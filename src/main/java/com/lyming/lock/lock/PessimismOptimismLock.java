package com.lyming.lock.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName PessimismOptimismLock
 * @Description 悲观锁,乐观锁
 * @Author lyming
 * @Date 2020/6/13 11:06 下午
 **/
public class PessimismOptimismLock {

    int a;

    /**
     * 乐观,非互斥同步
     * @param args
     */
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }

    /**
     * 悲观,互斥同步
     */
    public synchronized void testMethod() {
        a++;
    }
}
