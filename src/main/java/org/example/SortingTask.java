package org.example;

import java.util.concurrent.RecursiveTask;

public abstract class SortingTask extends RecursiveTask<Void> {

    public final int[] array;

    protected SortingTask(int[] array) {
        this.array = array;
    }

    // Has to use a Void wrapper because each sorters extends RecursiveTask<Void> and has to return a Void
    public abstract Void compute();
    public abstract void computeClassic();

    public Void use(){
        System.out.println("Array before sorting : " );
        Array.printFirst(this.array, 10);
        compute();
        System.out.println("Array after sorting : " );
        Array.printFirst(this.array, 10);
        return null;
    }

    public Void useClassic(){
        System.out.println("Array before sorting : " );
        Array.printFirst(this.array, 10);
        computeClassic();
        System.out.println("Array after sorting : " );
        Array.printFirst(this.array, 10);
        return null;
    }

}
