package com.lyming.lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName CinemaReadWrite
 * @Description TODO
 * @Author lyming
 * @Date 2020/6/14 1:37 下午
 **/
public class CinemaReadWrite {

    //创建读写锁
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    /**
     * 读取
     */
    private static void read(){
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"得到了读锁,正在读取");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放了读锁");
            readLock.unlock();
        }
    }

    private static void write(){
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"得到了写锁,正在写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放了写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(()->read(),"Thread1").start();
        new Thread(()->read(),"Thread2").start();
        new Thread(()->write(),"Thread3").start();
        new Thread(()->write(),"Thread4").start();
    }
}
