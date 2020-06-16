package com.lyming.lock.immutable;

/**
 * @ClassName FinalStringDemo1
 * @Description TODO
 * @Author lyming
 * @Date 2020/6/16 1:38 下午
 **/
public class FinalStringDemo1 {
    public static void main(String[] args) {
        String a = "wukong2";
        final String b = "wukong";
        String d = "wukong";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c));
        System.out.println((a == e));
    }
}
