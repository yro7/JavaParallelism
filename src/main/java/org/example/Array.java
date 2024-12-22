package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class Array {


    public static void print(int[] array){
        System.out.println("Printing array " + array);
        Arrays.stream(array).forEach(i -> System.out.print(i + " "));
        System.out.println();
    }

    public static int[] random(int length, int bound){
        Random random = new Random();
        return IntStream.range(0, length)
                .map(i -> random.nextInt(bound))
                .toArray();
    }

    public static int[] random(int n){
        return random(n,(int) Math.pow(2,32) - 1);
    }


        public static Long classic(int n){
            if(n <= 1) return (long) n;
            Long n1 = classic(n-1);
            Long n2 = classic(n-2);
            return n1 + n2;
        }
    }

