package org.example;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.io.*;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    /**
     * How many times should the {@link Main#Time(Consumer, Object)} function loop before starting to measure the timings.
     * Allows to warm up the JVM to account for its optimizations and have precise timings
     */
    public static final int WARMUP_RUNS = 10;
    /**
     * How many times the {@link Main#Time(Consumer, Object)} function should measure the timings to make an average.
     **/

    public static final int MEASUREMENT_RUNS = 20;

    /**
     * How many times the {@link Main#graphData(List, Consumer[])} function should invoke the {@link Main#TimesInRange(List, Consumer)}
     * function before actually measuring the timings.
     */
    public static final int WARMUP_FUNCTION = 10;

    public static void main(String[] args) {

        //graphData(0,33,1,FibonnaciTask.parallelism,FibonnaciTask::classic);
        List<Integer> inputs = getList(1000,20_000,40);


        saveExecutionsTime(MergeSortTask.parallel,inputs, "java-21-parallel");
        saveExecutionsTime(MergeSortTask.classic,inputs, "java-21-classic");

/**

       List<TreeMap<Integer,Double>> executionsTimes = loadExecutionTimes(
                "java-22-parallel",
                "java-22-classic",
                "java-23-parallel",
                "java-23-classic"
        );

       graphData(executionsTimes);

 **/

    }

    /**
     * Compares the time of execution between 2 functions that accept the same input type T, for a same input T.
     * @param functionA
     * @param functionB
     * @param value
     * @return
     * @param <T>
     */
    public static <T> double compare(Consumer<T> functionA, Consumer<T> functionB, T value){
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
     */
    public static <T> double Time(Consumer<T> function, T value){

        // Warm-up runs to account for JVM optimizations
        for (int i = 0; i < WARMUP_RUNS; i++) {
            function.accept(value);
        }

        // Actual measurements : run the function MEASUREMENT_RUNS times to get an average evaluation time.
        double totalTime = 0;
        for (int i = 0; i < MEASUREMENT_RUNS; i++) {
            double before = System.nanoTime();
            function.accept(value);
            double after = System.nanoTime();
            totalTime += (after - before);
        }

        return (totalTime/MEASUREMENT_RUNS)/1_000_000_000;
    }


    /**
     * Get execution times for a Function Integer -> R applied for each value between a and b
     * @param function
     * @return
     */
    public static <T extends Comparable<T>> TreeMap<T,Double> TimesInRange(List<T> values, Consumer<T> function){
        TreeMap<T,Double> res = new TreeMap<>();
        double resi;
        for(T value : values){
            resi = Time(function,value);
            res.put(value,resi);
        }
        return res;
    }

    public static void graphData(int a, int b, int step, Consumer<Integer>... functions){
        graphData(getList(a,b,step), functions);
    }

    /**
     * Return a list of integer ranging from a to b with step
     * @param a
     * @param b
     * @param step
     * @return
     */
    public static List<Integer> getList(int a, int b, int step){
        List<Integer> values = new ArrayList<>();
        for(int i = a; i < b; i += step){
            values.add(i);
        }
        return values;
    }

    public static void graphData(int n, int numberOfTimes, Consumer<Integer>... functions){
        graphData(new ArrayList<>(Collections.nCopies(numberOfTimes,n)), functions);
    }

    public static <T> void graphData(List<TreeMap<T,Double>> treeMapList){

        // Create Chart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Performance Comparison")
                .xAxisTitle("n =")
                .yAxisTitle("Time (s)")
                .build();

        // Add series to chart
        int i = 0;
        for (TreeMap<T,Double> tree : treeMapList) {
            List<T> dataX = new ArrayList<>(tree.keySet());
            List<Double> dataY = new ArrayList<>(tree.values());
            String name = "Data " + i;
            if(i == 0) name = "Java 22 Parallel Version";
            if(i == 1) name = "Java 22 Classic";
            if(i == 2) name = "Java 23 Parallel";
            if(i == 3) name = "Java 23 Classic";
            chart.addSeries(name, dataX, dataY);
            i++;
        }

        // Customize chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setMarkerSize(8);

        // Show it
        new SwingWrapper(chart).displayChart();
    }
    /**
     * Generic data grapher for a list of input values extending Comparable<T></T>
     * @param values
     * @param functions
     * @param <T>
     */
    public static <T extends Comparable<T>> void graphData(List<T> values, Consumer<T>... functions) {
        // Each function has a List<T> of input values T : in the end we have a List<List<T>>
        List<TreeMap<T,Double>> treeMapList = new ArrayList<>();
        for (Consumer<T> function : functions) {
            TreeMap<T, Double> data = new TreeMap<>();
            for(int i = 0; i < WARMUP_FUNCTION; i ++){
                data = TimesInRange(values, function);
            }

            treeMapList.add(data);
        }

        graphData(treeMapList);
    }

    public static <T> String getFunctionName(Consumer<T> function) {
        return "";
    }

    public static <T extends Comparable<T>> void saveExecutionsTime(Consumer<T> function,List<T> inputs, String fileName){
        TreeMap<T,Double> dataToSave = TimesInRange(inputs,function);
         try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
         oos.writeObject(dataToSave);
         System.out.println("data " + fileName + " saved successfully!");
         } catch (IOException e) {
         throw new RuntimeException(e);
         }

    }

    public static <T> TreeMap<T,Double> loadExecutionTimes(String fileName){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (TreeMap<T, Double>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<TreeMap<T,Double>> loadExecutionTimes(String... fileName){
        List<TreeMap<T,Double>> res = new ArrayList<>();
        for(String file : fileName){
            res.add(loadExecutionTimes(file));
        }
        return res;
    }

}