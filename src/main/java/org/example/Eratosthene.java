package org.example;

import java.util.BitSet;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Eratosthene {
    private final BitSet nonPrimes;
    private final int bound;

    public static final Consumer<Integer> eratosthene = Eratosthene::new;

    public Eratosthene(int n) {
        this.bound = n;
        this.nonPrimes = new BitSet(n);
        compute();
    }

    public int[] compute() {
        // Mark 0 and 1 as non-prime
        nonPrimes.set(0);
        nonPrimes.set(1);

        // Main sieve algorithm
        for (int i = 2; i <= Math.sqrt(bound); i++) {
            if (!nonPrimes.get(i)) {
                // Mark all multiples of i as non-prime
                for (int j = i * i; j < bound; j += i) {
                    nonPrimes.set(j);
                }
            }
        }

        // Create array of prime numbers using stream
        int[] primes = IntStream.range(0, bound)
                .filter(i -> !nonPrimes.get(i))
                .toArray();

        System.out.printf("Number of primes between 1 and %d: %d%n", bound, primes.length);
        return primes;
    }
}