package org.example;

import java.util.concurrent.RecursiveTask;

public abstract class SortingTask extends RecursiveTask<Void> {

    public final int[] array;

    protected SortingTask(int[] array) {
        this.array = array;
    }

    public abstract Void compute();

}
