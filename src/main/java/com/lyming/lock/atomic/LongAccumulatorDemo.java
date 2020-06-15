package com.lyming.lock.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * @ClassName LongAccumulatorDemo
 * @Description 演示LongAccumulator的用法
 * @Author lyming
 * @Date 2020/6/16 1:17 上午
 **/
public class LongAccumulatorDemo {
    public static void main(String[] args) {
        LongAccumulator accumulator = new LongAccumulator((x, y) ->  x + y, 0);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        IntStream.range(1, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println(accumulator.getThenReset());
    }
}
