package org.example;

import java.util.function.Consumer;
import java.util.function.Function;

class MergeSortTask extends SortingTask {
    private final int start;
    private final int end;
    private final int[] temp;

    public static final Consumer<Integer> parallel = n -> new MergeSortTask(n).use();
    public static final Consumer<Integer> classic = n -> new MergeSortTask(n).useClassic();


    MergeSortTask(int[] array, int start, int end, int[] temp) {
        super(array);
        this.start = start;
        this.end = end;
        this.temp = temp;
    }

    MergeSortTask(int[] array) {
        super(array);
        this.start = 0;
        // Exclusive bound to avoid seg fault
        this.end = array.length - 1;
        this.temp = new int[array.length];
    }

    MergeSortTask(int arrayLength) {
        super(Array.decreasing(arrayLength));
        this.start = 0;
        // Exclusive bound to avoid seg fault
        this.end = arrayLength - 1;
        this.temp = new int[arrayLength];
    }


    @Override
    public void compute() {
        if (start < end) {
            int mid = start + (end - start) / 2;  // Avoid potential overflow

            // Sort left and right halves
            MergeSortTask taskLeft = new MergeSortTask(array,start,mid, temp);
            MergeSortTask taskRight = new MergeSortTask(array,mid+1,end, temp);
            invokeAll(taskLeft, taskRight);
            // Merge the sorted halves
            merge(array, temp, start, mid, end);
        }
    }

    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        // Copy both halves into the temporary array
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;      // First half start index
        int j = mid + 1;   // Second half start index
        int k = left;      // Current position in original array

        // Compare and merge back to original array
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of left half
        while (i <= mid) {
            arr[k] = temp[i];
            k++;
            i++;
        }

        // Note: No need to copy remaining elements of right half
        // as they are already in their correct positions
    }

    /**
     * A classic merge sort that does not use parallelism
     */
    public void computeClassic() {

        if (array == null || array.length <= 1) {
            return;
        }

        int[] temp = new int[array.length];
        mergeSortHelper(array, temp, 0, array.length - 1);
    }

    private static void mergeSortHelper(int[] arr, int[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;  // Avoid potential overflow

            // Sort left and right halves
            mergeSortHelper(arr, temp, left, mid);
            mergeSortHelper(arr, temp, mid + 1, right);

            // Merge the sorted halves
            merge(arr, temp, left, mid, right);
        }
    }

}
