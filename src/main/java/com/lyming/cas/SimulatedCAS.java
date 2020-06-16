package com.lyming.cas;

/**
 * @ClassName SimulatedCAS
 * @Description 模拟CAS操作，等价代码
 * @Author lyming
 * @Date 2020/6/16 12:11 下午
 **/
public class SimulatedCAS {
    private volatile int value;

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }
}
