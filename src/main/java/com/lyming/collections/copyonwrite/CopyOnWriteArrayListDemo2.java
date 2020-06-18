package com.lyming.collections.copyonwrite;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName CopyOnWriteArrayListDemo2
 * @Description 对比两个迭代器
 * @Author lyming
 * @Date 2020/6/18 4:38 下午
 **/
public class CopyOnWriteArrayListDemo2 {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});

        System.out.println(list);

        Iterator<Integer> itr1 = list.iterator();

        list.remove(2);
        Thread.sleep(1000);
        System.out.println(list);

        Iterator<Integer> itr2 = list.iterator();
        //itr1还是感知不到修改,所以取决于诞生时间,不取决于迭代时间
        itr1.forEachRemaining(System.out::println);
        itr2.forEachRemaining(System.out::println);

    }
}
