import java.util.Random;

public class QuickSorter {

    public long comparisons;
    public long swaps;
    public int maxDepth;

    private final Random rnd;

    public QuickSorter() {
        this.rnd = new Random();
    }

    public QuickSorter(long seed) {
        this.rnd = new Random(seed);
    }

    public void sort(int[] a) {
        comparisons = 0;
        swaps = 0;
        maxDepth = 0;
        if (a.length < 2) {
            return;
        }
        quickSort(a, 0, a.length - 1, 1);
    }

    private void quickSort(int[] a, int lo, int hi, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        while (lo < hi) {

            int pivot = a[lo + rnd.nextInt(hi - lo + 1)];


            int lt = lo;
            int gt = hi;
            int i = lo;
            while (i <= gt) {
                comparisons++;
                if (a[i] < pivot) {
                    swap(a, lt, i);
                    lt++;
                    i++;
                } else {
                    comparisons++;
                    if (a[i] > pivot) {
                        swap(a, i, gt);
                        gt--;
                    } else {
                        i++;
                    }
                }
            }

            int leftSize = lt - lo;
            int rightSize = hi - gt;
            if (leftSize < rightSize) {
                quickSort(a, lo, lt - 1, depth + 1);
                lo = gt + 1;
            } else {
                quickSort(a, gt + 1, hi, depth + 1);
                hi = lt - 1;
            }
        }
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        swaps++;
    }
}