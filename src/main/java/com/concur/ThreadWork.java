package main.java.com.concur;

public class ThreadWork implements Runnable {
        private final String name;
        private final ThreadWorkFace work;
        public ThreadWork(String name, ThreadWorkFace work) {
            this.name = name;
            this.work = work;
        }
        @Override
        public void run() {
            work.startWork();
        }
}
