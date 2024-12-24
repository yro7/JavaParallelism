package org.example;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

public abstract class SortingTask extends RecursiveAction {

    public final int[] array;

    protected SortingTask(int[] array) {
        this.array = array;
    }

    // Has to use a Void wrapper because each sorters extends RecursiveTask<Void> and has to return a Void
    public abstract void compute();
    public abstract void computeClassic();

    public Void use(){
        use(false);
        return null;
    }

    public void use(boolean debug){
        if(debug) System.out.println("Array before sorting : " );
        if(debug) Array.printFirst(this.array, 10);
        compute();
        if(debug) System.out.println("Array after sorting : " );
        if(debug) Array.printFirst(this.array, 10);
    }

    public void useClassic(boolean debug){
        if(debug) System.out.println("Array before sorting : " );
        if(debug) Array.printFirst(this.array, 10);
        computeClassic();
        if(debug) System.out.println("Array after sorting : " );
        if(debug) Array.printFirst(this.array, 10);
    }

    public void useClassic(){
        useClassic(false);
    }


}
