package com.juuu.core;

import org.junit.Test;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * By using ContextHandler, thread can easily save their context and to use in different methods.
 * Developed specifically for multithreading programs.
 */
public class ContextHandlerTests {

    private final Integer THREAD_NUM = 10;
    CountDownLatch latch = new CountDownLatch(THREAD_NUM);

    @Test
    public void test() throws InterruptedException {
        for(int i=0;i<THREAD_NUM;i++){
            new Thread(this::begin).start();
        }
        latch.await();
    }

    /**
     * initialize private context
     */
    public void begin(){
        String id = UUID.randomUUID().toString();
        Integer age = new Random().nextInt(10);
        ContextHandler.set("id",id);
        ContextHandler.set("age",age);
        A();
        B();
        latch.countDown();
    }

    public void A(){
        System.out.println(Thread.currentThread().getName() + "'s id:" + ContextHandler.get("id"));
    }

    public void B(){
        System.out.println(Thread.currentThread().getName() + "'s id:" + ContextHandler.get("age"));
    }
}
