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
            // this is lock by object reference so any java Object has this
            synchronized (shared) {
                System.out.println("Consumer after obtaining lock, time to take: " + (System.nanoTime() / 1000 - beforeLock));
                while(!shared.has()) {
                    System.out.println("Consumer going to wait state");
                    waitOn(shared);
                }
                System.out.println("Consumer awoke");
                // simulate that consumer takes time to read a value
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
            // This is an exception that sleep throws when another thread interrupts the current thread while sleep is active
            // What if a thread goes a long time without invoking a method that throws InterruptedException?
            // Then it must periodically invoke Thread.interrupted, which returns true if an interrupt has been received.
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            // Just start work
            System.out.println("Consumer was interrupted by something");
        }
    }
}
