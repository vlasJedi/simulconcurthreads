package main.java.com.concur.compfutures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class CompletableFutures {
    private final ExecutorService executorService = new ForkJoinPool(4);
    public void run() {
        System.out.println("Number of cores that JVM sees: " + Runtime.getRuntime().availableProcessors());
        final long init = System.nanoTime();
        final int roundsNumber = 64;
        for (int i = 0; i < roundsNumber; i++) runCpuIntense();
        System.out.println("Thread name: " + Thread.currentThread().getName() +
                " Time taken for sync sout: " + (System.nanoTime() - init) / 1000000);
        final long init1 = System.nanoTime();
        CompletableFuture<?>[] arrFutures = new CompletableFuture<?>[roundsNumber];
        for (int i = 0; i < roundsNumber; i++) arrFutures[i] = asyncRunCpuIntense();
        CompletableFuture.allOf(arrFutures).thenRun(() -> {
            System.out.println("Thread name: " + Thread.currentThread().getName() +
                    " Time taken for async sout: " + (System.nanoTime() - init1) / 1000000);
        });
    }

    public void runSout() {
        System.out.println("Hello world from: " + Thread.currentThread().getName());
    }

    public CompletableFuture<Void> runAsyncSout() {
        return CompletableFuture.runAsync(this::runSout, this.executorService);
    }

    public void runCpuIntense() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {}
    }

    public CompletableFuture<Void> asyncRunCpuIntense() {
        return CompletableFuture.runAsync(this::runCpuIntense);
    }
}
