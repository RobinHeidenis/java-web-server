package com.Counter;

public class Counter {
    private static Counter instance;
    private int globalCounter = 0;

    public static Counter getInstance() {
        if (instance == null) instance = new Counter();
        return instance;
    }

    public int getGlobalCounter() {
        return globalCounter;
    }

    public int addToGlobalCounter() {
        return globalCounter++;
    }
}
