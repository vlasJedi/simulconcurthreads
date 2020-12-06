package main.java.com.concur.threadsynced;

public class SharedResource {
    private short counter;
    synchronized public short  getCounter() {
        return counter;
    }

    synchronized public void  setCounter(short number) {
        counter = number;
    }

    synchronized public short  increment() {
        return ++counter;
    }
}
