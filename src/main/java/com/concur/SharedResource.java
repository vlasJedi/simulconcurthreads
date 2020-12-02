package main.java.com.concur;

public class SharedResource {
    private int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int number) {
        counter = number;
    }

    public int increment() {
        return ++counter;
    }
}
