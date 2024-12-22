package org.example;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
     //   Time(FibonnaciTask::classic, 50);
       // Time(n -> new ForkJoinPool().invoke(new FibonnaciTask(n)), 50);

     //   Time(n -> new MergeSortTask(n).use(), 10_000_000);
      //  Time(n -> new MergeSortTask(n).useClassic(), 10_000_000);

        double diff = compare(n -> new MergeSortTask(n).use(),
                        n -> new MergeSortTask(n).useClassic(),
                1_000_000);
        System.out.println("diff : " + diff);

        double[] res = TimesInRange(n -> new MergeSortTask(n).useClassic(),100);
        System.out.println(Arrays.toString(res));

    }

    /**
     * Compares the time of execution between 2 functions living in the same space T -> R for a same input T.
     * @param functionA
     * @param functionB
     * @param value
     * @return
     * @param <T>
     * @param <R>
     */
    public static <T,R> double compare(Function<T,R> functionA, Function<T,R> functionB, T value){
        double timeA = Time(functionA, value);
        double timeB = Time(functionB, value);
        return timeB - timeA;
    }

    /**
     * Get execution time of a Function T -> R applied on a value.
     * @param function
     * @param value
     * @return
     * @param <T>
     * @param <R>
     */
    public static <T,R> double Time(Function<T, R> function, T value){
        double before = System.nanoTime();
        function.apply(value);
        double after = System.nanoTime();
        return (after-before)/1000000000;
    }

    /**
     * Get execution times for a Function Integer -> R applied for each value between 1 and n
     * @param function
     * @return
     * @param <R>
     */
    public static <R> double[] TimesInRange(Function<Integer,R> function, int n){
        double[] res = new double[n];
        IntStream.range(1,n)
                .forEach(i -> res[i] = Time(function, i));
        return res;
    }


    public <R> void graphTimes(Function<Integer,R> int a, int b, int pas){
        double[] times = this.getTimeFromRange(a,b, pas);
        int size = data.size();
        double[] xData = new double[size];
        double[] yData = new double[size];
        final int[] i = {0};
        data.forEach((integer, aLong) -> {
            xData[i[0]] = integer;
            yData[i[0]] = aLong;
            i[0]++;
        });


        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "n =", "Temps en ns", "y(x)", xData, yData);

        // Show it
        new SwingWrapper(chart).displayChart();
    }


}