package com.example.demo.web.controller.test;

import org.springframework.stereotype.Controller;

@Controller
public class Test {


    public static void main(String[] args) {
        System.out.println("进入线程"+Thread.currentThread().getName());
        Test test = new Test();
        Thread thread1 = new Thread(test.new MyThread());
        thread1.start();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"等待");
            thread1.join();
            System.out.println("线程"+Thread.currentThread().getName()+"继续执行");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    int i = 1;
    private Object object = new Object();

    class MyThread implements Runnable{

        @Override
        public void run() {
            System.out.println("进入线程"+Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO: handle exception
            }
            System.out.println("线程"+Thread.currentThread().getName()+"执行完毕");
        }
        }

}
