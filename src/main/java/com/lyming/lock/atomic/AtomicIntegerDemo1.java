package com.lyming.lock.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName AtomicIntegerDemo1
 * @Description 演示AtomicInteger的基本用法，对比非原子类的线程安全问题，使用了原子类之后，不需要加锁，也可以保证线程安全
 * @Author lyming
 * @Date 2020/6/15 3:50 下午
 **/
public class AtomicIntegerDemo1 implements Runnable {
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public void incrementAtomic() {
        atomicInteger.getAndIncrement();
    }

    //volatile保证可见性但是不保证原子性
    private static volatile int basicCount = 0;

    /**
     * 加了Synchronize才能保证线程安全
     */
    public synchronized void incrementBasic() {
        basicCount++;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo1 r = new AtomicIntegerDemo1();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("原子类的结果：" + atomicInteger.get());
        System.out.println("普通变量的结果：" + basicCount);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }
}
