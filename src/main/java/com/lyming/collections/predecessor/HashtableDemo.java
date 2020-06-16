package com.lyming.collections.predecessor;

import java.util.Hashtable;

/**
 * @ClassName HashtableDemo
 * @Description TODO
 * @Author lyming
 * @Date 2020/6/16 3:20 下午
 **/
public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("学完以后跳槽涨薪幅度", "80%");
        System.out.println(hashtable.get("学完以后跳槽涨薪幅度"));
    }
}
