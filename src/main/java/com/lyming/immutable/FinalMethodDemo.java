package com.lyming.immutable;

/**
 * @ClassName FinalMethodDemo
 * @Description final的方法
 * @Author lyming
 * @Date 2020/6/16 1:08 下午
 **/
public class FinalMethodDemo {

    public void drink() {

    }

    public final void eat() {

    }

    public static void sleep() {
    }
}

class SubClass extends FinalMethodDemo {

    @Override
    public void drink() {
        super.drink();
        eat();
    }

    //    public final void eat() {
//
//    }
    public static void sleep() {
    }

}
