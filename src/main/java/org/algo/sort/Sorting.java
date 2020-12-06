package org.algo.sort;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Sorting {
    public static void main(String args[]) {

        int[] array1 = new int[]{ 7, 4, 5, 2};
        int[] array2 = new int[]{ 10, 8, 9, 7, 3, 2, 5, 6, 4, 1};
        int[] array3 = new int[]{ 10, 8, 958, 7, 3, 2, 4, 5, 4, 6};
        int[] array4 = new int[100];
        Random r = new Random();
        for (int i=0; i<100; i++) {
            array4[i] = r.nextInt(100);
        }
//        bubbleSort(array1);
//        bubbleSort(array2);
//        bubbleSort(array3);
//        bubbleSort(array4);

//        selectionSort(array1);
//        selectionSort(array2);
//        selectionSort(array3);
//        selectionSort(array4);

//        insertionSort(array1);
//        insertionSort(array2);
//        insertionSort(array3);
//        insertionSort(array4);

//        mergeSort(array1, 0, array1.length-1);
//        mergeSort(array2, 0, array2.length-1);
//        mergeSort(array3, 0, array3.length-1);
//        mergeSort(array4, 0, array4.length-1);

//        quickSort(array1, 0, array1.length-1);
//        quickSort(array2, 0, array2.length-1);
//        quickSort(array3, 0, array3.length-1);
//        quickSort(array4, 0, array4.length-1);

//        radixSort(array1, array1.length);
//        radixSort(array2, array2.length);
//        radixSort(array3, array3.length);
//        radixSort(array4, array4.length);

//        countingSort(array1);
//        countingSort(array2);
//        countingSort(array3);
//        countingSort(array4);

        heapSort(array1);
        heapSort(array2);
        heapSort(array3);
        heapSort(array4);

        printArray(array1);
        printArray(array2);
        printArray(array3);
        printArray(array4);
    }

    // Swap 2 by 2
    private static void bubbleSort(int[] array) {
        int tmp;
        boolean sorted;
        do {
            sorted = true;
            for (int i=0; i<array.length-1; i++) {
                if (array[i] > array[i+1]) {
                    tmp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = tmp;
                    sorted = false;
                }
            }
        } while (!sorted);
    }

    // Insert minimum at first place
    private static void selectionSort(int[] array) {
        int min;
        int tmp;
        for (int i=0; i<array.length-1; i++) {
            min = i;
            for (int j=i+1; j<array.length; j++) {
                if (array[min] > array[j]) {
                    min = j;
                }
            }
            if (min > i) {
                tmp = array[i];
                array[i] = array[min];
                array[min] = tmp;
            }
        }
    }

    // Move min to the left side by moving each element before it
    private static void insertionSort(int[] array) {
        int tmp;
        for (int i=1; i<array.length; i++) {
            tmp = array[i];
            int j = i;
            while (j>0 && tmp < array[j-1]) {
                array[j] = array[j-1];
                j--;
            }
            array[j] = tmp;
        }
    }

    private static void mergeSort(int[] array, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(array, start, mid);
            mergeSort(array, mid+1, end);
            merge(array, start, mid, end);
        }
    }

    private static void merge(int[] array, int start, int mid, int end) {
        int[] mergedArray = new int[end-start+1];
        int i=start, j=mid+1;
        for (int k=0; k<mergedArray.length; k++) {
            if (j > end || (i <= mid && array[i] < array[j])) {
                mergedArray[k] = array[i];
                i++;
            } else {
                mergedArray[k] = array[j];
                j++;
            }
        }

        for (int item : mergedArray) {
            array[start] = item;
            start++;
        }
    }

    private static void quickSort(int[] array, int start, int end) {
        if (start < end) {
            int pivot = partition(array, start, end);
            quickSort(array, start, pivot-1);
            quickSort(array, pivot+1, end);
        }
    }

    private static int partition(int[] array, int start, int end) {
        int random = start + ThreadLocalRandom.current().nextInt(end-start);
        swap(array, random, start);
        int pivot = array[start];
        int j = start+1;
        for (int i=start+1; i<=end; i++) {
            if (array[i] < pivot) {
                swap(array, i, j);
                j++;
            }
        }
        swap(array, j-1, start);
        return j-1;
    }

    private static void countingSort(int[] array) {
        int k = 0;
        for (int elem : array) {
            if ( k < elem) {
                k = elem;
            }
        }

        int[] auxArray = new int[k+1];
        for (int elem : array) {
            auxArray[elem]++;
        }

        int j = 0;
        for (int i=0; i<auxArray.length; i++) {
            int tmp = auxArray[i];
            while (tmp>0) {
                array[j] = i;
                tmp--;
                j++;
            }
        }
    }

    private static void radixSort(int[] array, int length) {
        int max = getMax(array);
        int div = 1;
        while (max > 0) {
            countRadix(array, length, div);
            div *= 10;
            max /= 10;
        }

    }

    private static void countRadix(int[] array, int length, int div) {
        int[] freq = new int[10];
        int[] output = new int[length];

        for (int elem : array) {
            freq[(elem/div)%10]++;
        }

        for (int i=1; i<freq.length; i++) {
            freq[i] += freq[i-1];
        }

        for (int i=length-1; i>=0; i--) {
            output[freq[(array[i]/div)%10]-1] = array[i];
            freq[(array[i]/div)%10]--;
        }

        for (int i=0; i<length; i++) {
            array[i] = output[i];
        }
    }

    private static void heapSort(int[] array) {
        int length = array.length;

        // Build Heap
        for (int i=length/2-1; i>=0; i--) {
            heapify(array, length, i);
        }

        // Move max to the end
        for (int i=length-1; i>0; i--) {
            swap(array, 0, i);

            // Rebuild heap without last max value
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int length, int i) {
        int largest = i;
        int left = 2*i + 1;
        int right = 2*i + 2;

        // Move largest to most left position
        if (left < length && array[left] > array[largest]) {
            largest = left;
        }

        if (right < length && array[right] > array[largest]) {
            largest = right;
        }

        // If largest was not in most left position, recursive call to compare with other positions on left side
        if (largest != i) {
            swap(array, largest, i);
            heapify(array, length, largest);
        }
    }

    private static int getMax(int[] array) {
        int max = array[0];
        for (int elem : array) {
            if (elem > max) {
                max = elem;
            }
        }
        return max;
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private static void printArray(int[] array) {
        StringBuilder sb = new StringBuilder("{");
        for (int i=0; i<array.length; i++) {
            sb.append(array[i]).append(",");
        }
        sb.setLength(sb.length()-1);
        sb.append("}");
        System.out.println(sb.toString());
    }
}
