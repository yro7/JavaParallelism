package org.example;

import java.time.chrono.Era;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Eratosthene {

    private HashSet<Integer> nonPrimes;
    private final Integer bound;

    public static final Consumer<Integer> eratosthene = n -> new Eratosthene(n).compute();

    public Eratosthene(int n){
        this.bound = n;
        this.nonPrimes = new HashSet<>();
    }

    public int[] compute() {
        IntStream integerStream = IntStream.range(2,this.bound);
        int[] res = integerStream.filter(this::isPrimeSmart)
                        .toArray();
        System.out.print("number of primes between 1 and " + bound + " : " + res.length);
        return res;
    }

    private boolean isPrimeSmart(int integer) {
        if(nonPrimes.contains(integer)){
            return false;
        }

        int i = 2;
        while(i*integer < bound){
            nonPrimes.add(integer*i);
            i++;
        }

        return isPrime(integer);
    }

    private static boolean isPrime(int integer){
        for(int i = 2; i < integer; i++){
            if (integer % i == 0){
                return false;
            }
        }
        return true;
    }
}