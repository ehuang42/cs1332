import java.util.Comparator;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Le-En Huang
 * @userid ehuang42
 * @GTID 903303142
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        } else {
            boolean swapped = true;
            int i = 0;
            while (i < arr.length - 1 && swapped) {
                swapped = false;
                for (int j = 0; j < arr.length - i - 1; j++) {
                    if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                        swap(j, j + 1, arr);
                        swapped = true;
                    }
                }
                i++;
            }
        }
    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        } else {
            for (int i = 1; i < arr.length; i++) {
                int j = i;
                while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                    swap(j - 1, j, arr);
                    j--;
                }
            }
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        } else {

            // splitting

            int length = arr.length;
            int midIndex = length / 2;
            if (length > 1) {

                T[] leftArray = (T[]) new Object[midIndex];
                T[] rightArray = (T[]) new Object[arr.length - midIndex];
                for (int i = 0; i < midIndex; i++) {
                    leftArray[i] = arr[i];
                }
                for (int i = 0; i < rightArray.length; i++) {
                    rightArray[i] = arr[midIndex];
                    midIndex++;
                }

                mergeSort(leftArray, comparator);
                mergeSort(rightArray, comparator);

                T[] sorted = merge(leftArray, rightArray, comparator, arr);
                int i = 0;
                for (T el: sorted) {
                    arr[i++] = el;
                }
            }
        }
    }

    /**
     * Helper method for merge sort. Accomplishes the merging part.
     *
     * @param <T>        data type to sort
     * @param leftArray  the leftArray to be merged
     * @param rightArray the rightArray to be merged
     * @param comparator the Comparator used to compare the data in arr
     * @param arr        the original array passed in
     * @return the sortedArray
     */

    private static <T> T[] merge(T[] leftArray, T[] rightArray,
                                Comparator<T> comparator, T[] arr) {
        int leftIndex = 0;
        int rightIndex = 0;
        int currentIndex = 0;

        T[] sortedArray = (T[]) new Object[arr.length];
        while (currentIndex < arr.length) {
            if (leftIndex == leftArray.length) {
                sortedArray[currentIndex] = rightArray[rightIndex];
                rightIndex++;
            } else if (rightIndex == rightArray.length) {
                sortedArray[currentIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                if (comparator.compare(leftArray[leftIndex],
                        rightArray[rightIndex]) <= 0) {
                    sortedArray[currentIndex] = leftArray[leftIndex];
                    leftIndex++;
                } else {
                    sortedArray[currentIndex] = rightArray[rightIndex];
                    rightIndex++;
                }
            }
            currentIndex++;
        }
        return sortedArray;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        } else {

            int max = findMax(arr);

            LinkedList<Integer>[] buckets = new LinkedList[19];
            for (int i = 0; i < 19; i++) {
                buckets[i] = new LinkedList<>();
            }

            int divisor = 1;
            while ((Math.abs(max) / divisor) > 0) {
                for (int el: arr) {
                    int bucketNum = (mod(el / divisor, 10)) + 9;
                    buckets[bucketNum].addLast(el);
                }
                int j = 0;
                for (int x = 0; x < 19; x++) {
                    while (!(buckets[x].isEmpty())) {
                        Integer num = buckets[x].pop();
                        arr[j++] = num;
                    }
                }
                divisor *= 10;
            }
        }
    }

    /**
     * Helper method for finding max value in array.
     *
     * @param  arr     the original array passed in
     * @return max     the max value found in array
     */
    private static int findMax(int[] arr) {
        int max = arr[0];
        for (int el: arr) {
            if (Math.abs(el) > Math.abs(max)) {
                max = el;
            } else if (el == Integer.MIN_VALUE) {
                return Integer.MAX_VALUE;
            }
        }
        return max;
    }

    /**
     * Helper method for modding ints in a special way.
     * If takes in negative number, the modded result
     * would also be negative.
     *
     * @param  a       the int to be modded
     * @param  modulo  the modulo for modding
     * @return max     the special mod value
     */
    private static int mod(int a, int modulo) {
        if (a < 0) {
            int result = -1 * ((-1 * a) % modulo);
            return result;
        } else {
            return (a % modulo);
        }
    }

    /**
     * Helper method for swapping two cells in array.
     *
     * @param  <T>      the type for objects in array
     * @param  ind1     the first cell to be swapped
     * @param  ind2     the second cell to be swapped
     * @param  arr      the original array passed in
     */
    private static <T> void swap(int ind1, int ind2, T[] arr) {
        T el1 = arr[ind1];
        T el2 = arr[ind2];
        arr[ind1] = el2;
        arr[ind2] = el1;
    }
}
//TODO
// public helper???
// MIN_VALUE doesn't work
