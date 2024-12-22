package org.example;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
     //   Time(FibonnaciTask::classic, 50);
       // Time(n -> new ForkJoinPool().invoke(new FibonnaciTask(n)), 50);

        int[] array = Array.random(10, 100);
        Array.print(array);

        MergeSortTask task = new MergeSortTask(array);
        task.compute();
        Array.print(array);

    }

    public static <T,R> void Time(Function<T, R> function, T value){
        float before = System.nanoTime();
        R res = function.apply(value);
        System.out.println("Resultat : " + res);
        float after = System.nanoTime();
        float diff = (after-before)/1000000000;
        System.out.println("Elapsed time : " + diff);
    }

    public class IntStreamUser {

        public int test(){
            return IntStream.range(1,101)
                    .sum();
        }
    }

}