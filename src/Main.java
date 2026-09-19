import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rnd = new Random(42);
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = rnd.nextInt(10000);
        }

        int[] m = a.clone();
        int[] expected = a.clone();
        Arrays.sort(expected);
        MergeSorter ms = new MergeSorter();
        ms.sort(m);
        System.out.println("MergeSort correct: " + Arrays.equals(m, expected));
        System.out.println("  comparisons: " + ms.comparisons + ", depth: " + ms.maxDepth);

        testQuick("random", a);

        int[] sorted = a.clone();
        Arrays.sort(sorted);
        testQuick("sorted", sorted);

        int[] reversed = new int[1000];
        for (int i = 0; i < reversed.length; i++) {
            reversed[i] = 1000 - i;
        }
        testQuick("reversed", reversed);

        int[] dups = new int[1000];
        for (int i = 0; i < dups.length; i++) {
            dups[i] = rnd.nextInt(5);
        }
        testQuick("duplicates", dups);

        testQuick("empty", new int[0]);
        testQuick("single", new int[]{7});
    }

    static void testQuick(String name, int[] input) {
        int[] copy = input.clone();
        int[] expected = input.clone();
        Arrays.sort(expected);
        QuickSorter qs = new QuickSorter(1);
        qs.sort(copy);
        System.out.println("QuickSort " + name + " correct: " + Arrays.equals(copy, expected)
                + ", comparisons: " + qs.comparisons
                + ", swaps: " + qs.swaps
                + ", depth: " + qs.maxDepth);
    }
}