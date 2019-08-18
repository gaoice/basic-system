package com.gaoice.system.common.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AsyncTest {

    /**
     * 记录每个线程操作 AtomicInteger 的次数
     */
    private ThreadLocal<Integer> local = new ThreadLocal<>();

    private AtomicInteger atomicInteger = new AtomicInteger(10000000);

    /**
     * ThreadLocal，AtomicInteger，Async 方法 测试
     */
    @Async
    public void test() {
        local.set(0);
        System.out.println("AsyncTest.test() decrement begin:" + Thread.currentThread());
        for (; atomicInteger.get() > 0; ) {
            atomicInteger.decrementAndGet();
            local.set(local.get() + 1);
        }
        System.out.println("AsyncTest.test() decrement end:" + Thread.currentThread());
        System.out.println("atomicInteger:" + atomicInteger.get() + " local:" + local.get());
        System.out.println("AsyncTest.test() increment begin:" + Thread.currentThread());
        for (; local.get() > 0; ) {
            atomicInteger.incrementAndGet();
            local.set(local.get() - 1);
        }
        System.out.println("AsyncTest.test() increment end:" + Thread.currentThread());
        System.out.println("atomicInteger:" + atomicInteger.get() + " local:" + local.get());
    }

}
