package com.lyming.immutable;

/**
 * @ClassName FinalStringDemo2
 * @Description TODO
 * @Author lyming
 * @Date 2020/6/16 1:40 下午
 **/
public class FinalStringDemo2 {
    public static void main(String[] args) {
        String a = "wukong2";
        final String b = getDashixiong();
        String c = b + 2;
        System.out.println(a == c);

    }

    private static String getDashixiong() {
        return "wukong";
    }
}
