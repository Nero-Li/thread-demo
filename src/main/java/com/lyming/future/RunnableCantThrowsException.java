package com.lyming.future;

/**
 * @ClassName RunnableCantThrowsException
 * @Description 在run方法中无法抛出checked Exception
 * @Author lyming
 * @Date 2020/6/20 10:26 下午
 **/
public class RunnableCantThrowsException {
    public void ddd() throws Exception {
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
