package com.lyming.collections.predecessor;

import java.util.Vector;

/**
 * @ClassName VectorDemo
 * @Description  演示Vector，主要是看Vector源码
 * @Author lyming
 * @Date 2020/6/16 3:16 下午
 **/
public class VectorDemo {

    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("test");
        System.out.println(vector.get(0));
    }
}
