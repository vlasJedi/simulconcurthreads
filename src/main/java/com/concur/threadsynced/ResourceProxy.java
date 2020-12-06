package main.java.com.concur.threadsynced;

public class ResourceProxy {
    private final SharedResource shared;
    private final long creationTime = System.nanoTime() / 1000;
    private long prevTime;
    public ResourceProxy(SharedResource shared) {
        this.shared = shared;
    }
    public void executeAction() {
        for (int i = 0; i < 100; i++) {
            final short current;
            final short newC;
            final long beforeMutexTime = System.nanoTime() / 1000;
            if (i == 0) System.out.println("Initial iteration: thread creation time took around: "
                    + (beforeMutexTime - creationTime));
            synchronized (shared) {
                System.out.println("*****");
                logAccessExecutionTime(beforeMutexTime);
                current = shared.getCounter();
                newC = shared.increment();
            }
            System.out.printf("Current counter in shared resource is: %d\n", current);
            System.out.printf("Incremented, counter in shared resource is: %d\n", newC);;
            System.out.println("Was race condition occured: " + ((current + 1) != newC));
        }
    }

    private void logAccessExecutionTime(long beforeMutexTime) {
        final long currentTime = System.nanoTime() / 1000;
        System.out.printf("Thread: %s, access block: %d\n", Thread.currentThread().getName(), currentTime - beforeMutexTime);
        System.out.printf("Thread: %s, action delta: %d\n", Thread.currentThread().getName(), currentTime - prevTime);
        prevTime = currentTime;
    }
}
