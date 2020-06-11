package com.lyming.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName ThreadLocalNormalUsage06
 * @Description 演示ThreadLocal用法2：避免传递参数的麻烦
 * @Author lyming
 * @Date 2020/6/11 1:03 上午
 **/
public class ThreadLocalNormalUsage06 {

    public static void main(String[] args) {
        new Service1().process("");

    }
}

class Service1 {

    public void process(String name) {
        User user = new User("超哥");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {

    public void process() {
        User user = UserContextHolder.holder.get();
        //一个Thread可以持有多个ThreadLocal对象
        SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        System.out.println("Service2拿到用户名：" + user.name);
        System.out.println(simpleDateFormat.format(new Date()));
//        remove的用法
//        UserContextHolder.holder.remove();
        new Service3().process();
    }
}

class Service3 {

    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名：" + user.name);
        UserContextHolder.holder.remove();
    }
}

class UserContextHolder {

    public static ThreadLocal<User> holder = new ThreadLocal<>();


}

class User {

    String name;

    public User(String name) {
        this.name = name;
    }
}
