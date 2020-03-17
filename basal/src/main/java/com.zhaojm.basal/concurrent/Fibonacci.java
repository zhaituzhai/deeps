package com.zhaojm.basal.concurrent;

//import net.mindview.util.*;

public class Fibonacci implements Runnable {
    private static int taskCount = 0;
    private final int id = taskCount++;

    @Override
    public void run() {

    }
}
