package main.java.com.concur.consumerproducersimple;

public class Consumer implements Runnable{
    private final SharedStorage shared;

    public Consumer(SharedStorage shared) {
        this.shared = shared;
    }

    private Consumer() {shared = null;}

    public void run() {
        while(true) {
            final long beforeLock = System.nanoTime() / 1000;
            System.out.println("Consumer before obtaining lock");
            synchronized (shared) {
                System.out.println("Consumer after obtaining lock, time to take: " + (System.nanoTime() / 1000 - beforeLock));
                while(!shared.has()) {
                    System.out.println("Consumer going to wait state");
                    waitOn(shared);
                }
                System.out.println("Consumer awoke");
                callSleep();
                System.out.println("Consumer consumes the: " + shared.get());
                shared.notify();
            }
        }
    }

    private void waitOn(SharedStorage shared) {
        try {
            shared.wait();
        } catch (InterruptedException e) {
            System.out.println("Consumer was interrupted by something");
        }
    }

    private void callSleep() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Consumer was interrupted by something");
        }
    }
}
