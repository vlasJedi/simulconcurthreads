package main.java.com.concur;

class Demo {
    public static void main(String[] args)  {
        System.out.println("Main thread started");

        SharedResource shared = new SharedResource();

        ResourceProxy proxy = new ResourceProxy(shared);

        Runnable work1 = new ThreadWork("Work 1", proxy::executeAction);
        Runnable work2 = new ThreadWork("Work 2", proxy::executeAction);

        Thread thread1 = new Thread(work1);
        Thread thread2 = new Thread(work2);

        thread1.start();
        thread2.start();

        System.out.println("Main thread finished");
    }

}