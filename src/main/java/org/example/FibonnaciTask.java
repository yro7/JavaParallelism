package org.example;

import java.util.concurrent.RecursiveTask;

public class FibonnaciTask extends RecursiveTask<Long> {

    private final int n;

    FibonnaciTask(int n) {
        this.n = n;
    }

    @Override
    public Long compute() {
        if(n <= 1) return (long) n;
        FibonnaciTask f1 = new FibonnaciTask(n-1);
        FibonnaciTask f2 = new FibonnaciTask(n-2);
        f1.fork();
        return f2.compute() + f1.join();
    }

    public static Long classic(int n){
        if(n <= 1) return (long) n;
        Long n1 = classic(n-1);
        Long n2 = classic(n-2);
        return n1 + n2;
    }
}