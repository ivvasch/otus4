package ru.otus4;

public class ThreadMain {

    public static void main(String[] args) {
        ThreadExperiment experiment = new ThreadExperiment();
        Thread third = new Thread(() -> experiment.printNumber2("3"));
        Thread forth = new Thread(() -> experiment.printNumber2("4"));
        third.start();
        forth.start();


    }

    public static class ThreadExperiment {

        private String second = "";

        public synchronized void printNumber2(String message) {
            int n = 0;
            int number = 10;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    while (second.equals(message)) {
                        this.wait();
                    }
                    second = message;
                    Thread.sleep(500);
                    this.notifyAll();
                    if (n < number) {
                        System.out.println(Thread.currentThread().getName() + " ==> " + ++n);
                    } else if (number < n) {
                        System.out.println(Thread.currentThread().getName() + " ==> " + --n);
                    }
                    number = n == number ? 0 : number;
                    if (n == 0) {
                        Thread.currentThread().interrupt();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
