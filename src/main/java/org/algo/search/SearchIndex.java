package org.algo.search;

public class SearchIndex {
    public static void main(String[] args) {
        int[] sortedArray1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] sortedArray2 = {1, 2, 3};
        int[] sortedArray3 = new int[100];
        for (int i=0; i<100; i++) {
            sortedArray3[i] = i*2;
        }

        System.out.println("Binary search:");
        System.out.println("Index de 5 dans sortedArray1: " + binarySearch(5, sortedArray1));
        System.out.println("Index de 9 dans sortedArray1: " + binarySearch(9, sortedArray1));
        System.out.println("Index de 1 dans sortedArray1: " + binarySearch(1, sortedArray2));
        System.out.println("Index de 2 dans sortedArray2: " + binarySearch(2, sortedArray2));
        System.out.println("Index de 2 dans sortedArray3: " + binarySearch(2, sortedArray3));
        System.out.println("Index de 1 dans sortedArray3: " + binarySearch(1, sortedArray3));
        System.out.println("Index de 88 dans sortedArray3: " + binarySearch(88, sortedArray3));
        System.out.println("Index de 100 dans sortedArray3: " + binarySearch(100, sortedArray3));
        System.out.println("Index de 166 dans sortedArray3: " + binarySearch(166, sortedArray3));

        System.out.println("Ternary search:");
        System.out.println("Index de 5 dans sortedArray1: " + ternarySearch(5, sortedArray1));
        System.out.println("Index de 9 dans sortedArray1: " + ternarySearch(9, sortedArray1));
        System.out.println("Index de 1 dans sortedArray1: " + ternarySearch(1, sortedArray2));
        System.out.println("Index de 2 dans sortedArray2: " + ternarySearch(2, sortedArray2));
        System.out.println("Index de 2 dans sortedArray3: " + ternarySearch(2, sortedArray3));
        System.out.println("Index de 1 dans sortedArray3: " + ternarySearch(1, sortedArray3));
        System.out.println("Index de 88 dans sortedArray3: " + ternarySearch(88, sortedArray3));
        System.out.println("Index de 100 dans sortedArray3: " + ternarySearch(100, sortedArray3));
        System.out.println("Index de 166 dans sortedArray3: " + ternarySearch(166, sortedArray3));
    }

    private static int binarySearch(int number, int[] sortedArray) {
        int min = 0;
        int max = sortedArray.length-1;
        while (min <= max) {
            int mid = (max + min) / 2;
            if (sortedArray[mid] == number) {
                return mid;
            } else if (sortedArray[mid] < number) {
                min = mid + 1;
            } else {
                max = mid - 1;
            }
        }
        return -1;
    }

    private static int ternarySearch(int number, int[] sortedArray) {
        int min = 0;
        int max = sortedArray.length-1;
        while (min <= max) {
            int mid1 = min + (max - min) / 3;
            int mid2 = max - (max - min) / 3;
            if (sortedArray[mid1] == number) {
                return mid1;
            } else if (sortedArray[mid2] == number) {
                return mid2;
            } else if (sortedArray[mid1] > number){
                max = mid1 - 1;
            } else if (sortedArray[mid2] < number) {
                min = mid2 + 1;
            } else {
                min = mid1 + 1;
                max = mid2 - 1;
            }
        }
        return -1;
    }
}
