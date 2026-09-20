import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Experiment {

    private static final int WARMUP_RUNS = 3;
    private static final int MEASURED_RUNS = 5;

    private final Random rnd = new Random(12345);
    private PrintWriter out;

    public void runAll(String csvPath) throws IOException {
        out = new PrintWriter(csvPath);
        out.println("algorithm,input_type,n,time_ms,max_depth,comparisons,swaps");

        int[] sizes = {1_000, 10_000, 100_000, 1_000_000};
        String[] types = {"random", "sorted", "reversed", "duplicates"};

        for (int n : sizes) {
            for (String type : types) {
                int[] data = generate(type, n);
                runMergeSort(data, type, n);
                runQuickSort(data, type, n);
                runSelect(data, type, n);
            }
        }

        int[] cpSizes = {1_000, 10_000, 100_000};
        for (int n : cpSizes) {
            runClosestPair(n);
        }

        out.close();
        System.out.println("Results saved to " + csvPath);
    }


    private int[] generate(String type, int n) {
        int[] a = new int[n];
        switch (type) {
            case "random":
                for (int i = 0; i < n; i++) {
                    a[i] = rnd.nextInt();
                }
                break;
            case "sorted":
                for (int i = 0; i < n; i++) {
                    a[i] = i;
                }
                break;
            case "reversed":
                for (int i = 0; i < n; i++) {
                    a[i] = n - i;
                }
                break;
            case "duplicates":
                for (int i = 0; i < n; i++) {
                    a[i] = rnd.nextInt(10);
                }
                break;
            default:
                throw new IllegalArgumentException("unknown type: " + type);
        }
        return a;
    }


    private void runMergeSort(int[] data, String type, int n) {
        MergeSorter s = new MergeSorter();
        long[] times = new long[MEASURED_RUNS];
        for (int r = 0; r < WARMUP_RUNS + MEASURED_RUNS; r++) {
            int[] copy = data.clone();
            long start = System.nanoTime();
            s.sort(copy);
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                times[r - WARMUP_RUNS] = elapsed;
            }
        }
        write("MergeSort", type, n, median(times), s.maxDepth, s.comparisons, -1);
    }

    private void runQuickSort(int[] data, String type, int n) {
        QuickSorter s = new QuickSorter(1);
        long[] times = new long[MEASURED_RUNS];
        for (int r = 0; r < WARMUP_RUNS + MEASURED_RUNS; r++) {
            int[] copy = data.clone();
            long start = System.nanoTime();
            s.sort(copy);
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                times[r - WARMUP_RUNS] = elapsed;
            }
        }
        write("QuickSort", type, n, median(times), s.maxDepth, s.comparisons, s.swaps);
    }

    private void runSelect(int[] data, String type, int n) {
        DeterministicSelector s = new DeterministicSelector();
        int k = n / 2;                                  // медиана
        long[] times = new long[MEASURED_RUNS];
        for (int r = 0; r < WARMUP_RUNS + MEASURED_RUNS; r++) {
            int[] copy = data.clone();
            long start = System.nanoTime();
            s.select(copy, k);
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                times[r - WARMUP_RUNS] = elapsed;
            }
        }
        write("Select", type, n, median(times), s.maxDepth, s.comparisons, s.swaps);
    }

    private void runClosestPair(int n) {
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(rnd.nextDouble() * 1_000_000, rnd.nextDouble() * 1_000_000);
        }
        ClosestPairSolver s = new ClosestPairSolver();
        long[] times = new long[MEASURED_RUNS];
        for (int r = 0; r < WARMUP_RUNS + MEASURED_RUNS; r++) {
            long start = System.nanoTime();
            s.solve(pts);
            long elapsed = System.nanoTime() - start;
            if (r >= WARMUP_RUNS) {
                times[r - WARMUP_RUNS] = elapsed;
            }
        }
        write("ClosestPair", "random", n, median(times), s.maxDepth, s.distanceChecks, -1);
    }


    private long median(long[] times) {
        long[] copy = times.clone();
        Arrays.sort(copy);
        return copy[copy.length / 2];
    }

    private void write(String algo, String type, int n, long nanos,
                       int depth, long comparisons, long swaps) {
        double ms = nanos / 1_000_000.0;
        String line = algo + "," + type + "," + n + ","
                + String.format(java.util.Locale.US, "%.3f", ms) + ","
                + depth + "," + comparisons + "," + swaps;
        out.println(line);
        System.out.println(line);
    }
}
