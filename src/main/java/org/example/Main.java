package org.example;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
     //   Time(FibonnaciTask::classic, 50);
       // Time(n -> new ForkJoinPool().invoke(new FibonnaciTask(n)), 50);

     //   Time(n -> new MergeSortTask(n).use(), 10_000_000);
      //  Time(n -> new MergeSortTask(n).useClassic(), 10_000_000);

        float diff = compare(n -> new MergeSortTask(n).use(),
                        n -> new MergeSortTask(n).useClassic(),
                100_000_000);
        System.out.println("diff : " + diff);

    }

    public static <T,R> float compare(Function<T,R> functionA, Function<T,R> functionB, T value){
        float timeA = Time(functionA, value);
        float timeB = Time(functionB, value);
        return timeB - timeA;
    }

    public static <T,R> float Time(Function<T, R> function, T value){
        float before = System.nanoTime();
        R res = function.apply(value);
        System.out.println("Resultat : " + res);
        float after = System.nanoTime();
        float diff = (after-before)/1000000000;
        System.out.println("Elapsed time : " + diff);
        return diff;
    }

    public class IntStreamUser {

        public int test(){
            return IntStream.range(1,101)
                    .sum();
        }
    }

}