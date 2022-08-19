package main.java.com.concur;

import main.java.com.concur.consumerproducersimple.Consumer;
import main.java.com.concur.consumerproducersimple.Producer;
import main.java.com.concur.consumerproducersimple.SharedStorage;
import main.java.com.concur.threadsynced.ResourceProxy;
import main.java.com.concur.threadsynced.SharedResource;
import main.java.com.concur.threadsynced.ThreadWork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Demo {
    public static void main(String[] args)  {
        /*(For more information on this topic, see Section 3.2.3
        in Doug Lea's "Concurrent Programming in Java (Second Edition)" (Addison-Wesley, 2000),
        or Item 50 in Joshua Bloch's "Effective Java Programming Language Guide" (Addison-Wesley, 2001).*/

        // Reads and writes are atomic for reference variables and for most primitive variables (all types except long and double).
        // Reads and writes are atomic for all variables declared volatile (including long and double variables).
        // Starvation - thread just can't obtain process time due to other greedy threads
        // Livelock - after lock is released several threads start compete for resource

        System.out.println("Main thread started");


        // thread synced case
        /*final SharedResource shared = new SharedResource();

        final ResourceProxy proxy = new ResourceProxy(shared);

        final String thread1Name = "Work 1";
        final String thread2Name = "Work 2";

        final Runnable work1 = new ThreadWork(thread1Name, proxy::executeAction);
        final Runnable work2 = new ThreadWork(thread2Name, proxy::executeAction);

        final Thread thread1 = new Thread(work1, thread1Name);
        final Thread thread2 = new Thread(work2, thread2Name);

        thread1.start();
        thread2.start();*/

        //consumer-producer simple case
//        SharedStorage sharedStorage = new SharedStorage();
//        Consumer consumer = new Consumer(sharedStorage);
//        Producer producer = new Producer(sharedStorage);
//
//        new Thread(producer).start();
//        new Thread(consumer).start();

        long start = System.currentTimeMillis();
        int numberOfThreads = 16;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        int numberOfTasks = 16;
        int counterInit = 0;
        SharedResource res = new SharedResource();

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfTasks; i++) {
            // variables in closure for lambdas should be final or kind of final - read only
            int j = i;
            futures.add(executor.submit(() -> {
                synchronized (res) {
                    System.out.println("Before increment is called and now is: " + res.getCounter());
                    res.increment();
                    System.out.println("Increment was called and now is: " + res.getCounter());
                }
                return j;
                // System.out.println("Current thread: " + Thread.currentThread() + " start took: " + (System.currentTimeMillis() - start));
            }));
        }
        boolean isTimeout = false;
        try {
            isTimeout = executor.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main was interrupted");
        } finally {
            executor.shutdown();
            futures.forEach((future) -> {
                try {
                    System.out.println("Thread number finished: " + future.get());
                } catch (Exception e) {
                    System.out.println("Some thread and its future is broken");
                }
            });
            System.out.println("Final counter is: " + res.getCounter());
            System.out.println("Pool finished with timeout ? - " + isTimeout);
        }


        System.out.println("Main thread finished");
    }

}