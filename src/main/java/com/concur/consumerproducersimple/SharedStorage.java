package main.java.com.concur.consumerproducersimple;

import java.util.LinkedList;
import java.util.Queue;

public class SharedStorage {
    private final Queue<String> queue = new LinkedList<>();
    private final short size;

    public SharedStorage(short size) {
        this.size = size;
    }

    public SharedStorage() {
        this((short)4);
    }

    public String get() {
        if (queue.isEmpty()) return null;
        return queue.poll();
    }

    public boolean has() {
        return !queue.isEmpty();
    }

    public void put(String value) {
        if (!canPut()) return;
        queue.add(value);
    }

    public boolean canPut() {
        return queue.size() < size;
    }
}
