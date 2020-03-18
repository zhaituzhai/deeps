package com.zhaojm.basal.concurrent.runnable;

public class Printer implements Runnable {

    private static int taskCount = 0;
    private final int id = taskCount++;

    public Printer() {
        System.out.println("Printer started: id = " + id);
    }

    @Override
    public void run() {
        System.out.println("Stage 1..., id = " + id);
        Thread.yield();
        System.out.println("Stage 2..., id = " + id);
        Thread.yield();
        System.out.println("Stage 3..., id = " + id);
        Thread.yield();
        System.out.println("Stage end..., id = " + id);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Printer()).start();
        }

        //Printer started: id = 0
        //Printer started: id = 1
        //Printer started: id = 2
        //Printer started: id = 3
        //Stage 1..., id = 0
        //Printer started: id = 4
        //Stage 2..., id = 0
        //Stage 3..., id = 0
        //Stage end..., id = 0
        //Stage 1..., id = 1
        //Stage 1..., id = 2
        //Stage 1..., id = 3
        //Stage 2..., id = 2
        //Stage 2..., id = 1
        //Stage 1..., id = 4
        //Stage 2..., id = 3
        //Stage 3..., id = 2
        //Stage 3..., id = 1
        //Stage 2..., id = 4
        //Stage 3..., id = 3
        //Stage end..., id = 2
        //Stage end..., id = 1
        //Stage end..., id = 3
        //Stage 3..., id = 4
        //Stage end..., id = 4
    }
}
