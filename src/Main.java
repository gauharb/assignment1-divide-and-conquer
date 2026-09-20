import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rnd = new Random(42);
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = rnd.nextInt(10000);
        }

        // MergeSort
        int[] m = a.clone();
        int[] expected = a.clone();
        Arrays.sort(expected);
        MergeSorter ms = new MergeSorter();
        ms.sort(m);
        System.out.println("MergeSort correct: " + Arrays.equals(m, expected));
        System.out.println("  comparisons: " + ms.comparisons + ", depth: " + ms.maxDepth);

        // QuickSort
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

        // Deterministic Select
        Random selRnd = new Random(7);
        int failures = 0;
        for (int t = 0; t < 100; t++) {
            int n = 1 + selRnd.nextInt(2000);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = selRnd.nextInt(500);
            }
            int k = selRnd.nextInt(n);
            int[] sortedCopy = arr.clone();
            Arrays.sort(sortedCopy);

            DeterministicSelector sel = new DeterministicSelector();
            int result = sel.select(arr.clone(), k);
            if (result != sortedCopy[k]) {
                failures++;
                System.out.println("FAIL n=" + n + " k=" + k);
            }
        }
        System.out.println("Select: 100 tests, failures: " + failures);
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