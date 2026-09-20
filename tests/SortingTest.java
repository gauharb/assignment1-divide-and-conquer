import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SortingTest {

    private void checkBoth(int[] input) {
        int[] expected = input.clone();
        Arrays.sort(expected);

        int[] m = input.clone();
        new MergeSorter().sort(m);
        assertArrayEquals(expected, m, "MergeSort failed");

        int[] q = input.clone();
        new QuickSorter(1).sort(q);
        assertArrayEquals(expected, q, "QuickSort failed");
    }

    @Test
    void emptyArray() {
        checkBoth(new int[0]);
    }

    @Test
    void singleElement() {
        checkBoth(new int[]{42});
    }

    @Test
    void twoElements() {
        checkBoth(new int[]{2, 1});
    }

    @Test
    void randomArrays() {
        Random rnd = new Random(1);
        for (int t = 0; t < 50; t++) {
            int[] a = new int[rnd.nextInt(3000)];
            for (int i = 0; i < a.length; i++) {
                a[i] = rnd.nextInt();
            }
            checkBoth(a);
        }
    }

    @Test
    void sortedArray() {
        int[] a = new int[2000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        checkBoth(a);
    }

    @Test
    void reverseSortedArray() {
        int[] a = new int[2000];
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }
        checkBoth(a);
    }

    @Test
    void duplicateHeavyArray() {
        Random rnd = new Random(2);
        int[] a = new int[2000];
        for (int i = 0; i < a.length; i++) {
            a[i] = rnd.nextInt(3);
        }
        checkBoth(a);
    }

    @Test
    void allEqual() {
        int[] a = new int[1000];
        Arrays.fill(a, 5);
        checkBoth(a);
    }

    @Test
    void negativeNumbers() {
        checkBoth(new int[]{-5, 3, -1, 0, -100, 99, -5});
    }
}
