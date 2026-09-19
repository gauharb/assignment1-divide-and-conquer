import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rnd = new Random(42);
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = rnd.nextInt(10000);
        }
        int[] expected = a.clone();
        Arrays.sort(expected);

        MergeSorter sorter = new MergeSorter();
        sorter.sort(a);

        System.out.println("Correct: " + Arrays.equals(a, expected));
        System.out.println("Comparisons: " + sorter.comparisons);
        System.out.println("Max depth: " + sorter.maxDepth);
    }
}