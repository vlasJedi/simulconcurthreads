package main.java.com.concur;

import main.java.com.concur.consumerproducersimple.Consumer;
import main.java.com.concur.consumerproducersimple.Producer;
import main.java.com.concur.consumerproducersimple.SharedStorage;
import main.java.com.concur.threadsynced.ResourceProxy;
import main.java.com.concur.threadsynced.SharedResource;
import main.java.com.concur.threadsynced.ThreadWork;

class Demo {
    public static void main(String[] args)  {
        /*(For more information on this topic, see Section 3.2.3
        in Doug Lea's "Concurrent Programming in Java (Second Edition)" (Addison-Wesley, 2000),
        or Item 50 in Joshua Bloch's "Effective Java Programming Language Guide" (Addison-Wesley, 2001).*/
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
        SharedStorage sharedStorage = new SharedStorage();
        Consumer consumer = new Consumer(sharedStorage);
        Producer producer = new Producer(sharedStorage);

        new Thread(producer).start();
        new Thread(consumer).start();

        System.out.println("Main thread finished");
    }

}