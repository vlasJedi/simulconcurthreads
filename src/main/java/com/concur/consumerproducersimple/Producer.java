package main.java.com.concur.consumerproducersimple;

public class Producer implements Runnable {
    private final SharedStorage shared;
    private short lastValue;

    public Producer(SharedStorage shared) {
        this.shared = shared;
    }

    private Producer() { shared = null;}

    public void run() {
        // run constantly
        while(true) {
            System.out.println("Producer trying to put before lock");
            final long beforeTime = System.nanoTime() / 1000;
            // get lock
            synchronized (shared) {
                System.out.println("Producer lock obtained, time to take: " + (System.nanoTime() / 1000 - beforeTime));
                // need to stay in loop due to sporadic awakening
                while (!shared.canPut()) {
                    System.out.println("Producer can't put, going to wait state");
                    waitOn(shared);
                }
                System.out.println("Producer awoke, going to put value");
                shared.put((new Short(++lastValue)).toString());
                System.out.println("Producer put value is: " + lastValue);
                // if consumer in waiting state then awake it
                shared.notify();
            }
        }
    }

    private void waitOn(SharedStorage shared) {
        try {
            shared.wait();
        } catch (InterruptedException e) {
            System.out.println("Producer thread by something was interrupted");
        }
    }

    private void callSleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Producer thread by something was interrupted");
        }
    }
}
