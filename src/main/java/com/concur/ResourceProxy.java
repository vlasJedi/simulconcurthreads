package main.java.com.concur;

public class ResourceProxy {
    private final SharedResource shared;
    public ResourceProxy(SharedResource shared) {
        this.shared = shared;
    }
    public void executeAction() {
        for (int i = 0; i < 100; i++) {
            int current = shared.getCounter();
            System.out.printf("Current counter in shared resource is: %d\n", current);
            int newC = shared.increment();
            System.out.printf("Incremented, counter in shared resource is: %d\n", newC);;
            System.out.println("Is race condition occured: " + ((current + 1) != newC));
        }
    }
}
