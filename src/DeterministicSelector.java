public class DeterministicSelector {

    public long comparisons;
    public long swaps;
    public int maxDepth;

    public int select(int[] a, int k) {
        if (a.length == 0) {
            throw new IllegalArgumentException("array is empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k out of range: " + k);
        }
        comparisons = 0;
        swaps = 0;
        maxDepth = 0;
        return select(a, 0, a.length - 1, k, 1);
    }

    private int select(int[] a, int lo, int hi, int k, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }

        while (true) {
            if (lo == hi) {
                return a[lo];
            }

            int pivotValue = medianOfMedians(a, lo, hi, depth);

            int lt = lo;
            int gt = hi;
            int i = lo;
            while (i <= gt) {
                comparisons++;
                if (a[i] < pivotValue) {
                    swap(a, lt, i);
                    lt++;
                    i++;
                } else {
                    comparisons++;
                    if (a[i] > pivotValue) {
                        swap(a, i, gt);
                        gt--;
                    } else {
                        i++;
                    }
                }
            }

            if (k < lt) {
                hi = lt - 1;
            } else if (k > gt) {
                lo = gt + 1;
            } else {
                return pivotValue;
            }
        }
    }


    private int medianOfMedians(int[] a, int lo, int hi, int depth) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertionSort(a, lo, hi);
            return a[lo + (n - 1) / 2];
        }


        int numGroups = 0;
        for (int start = lo; start <= hi; start += 5) {
            int end = Math.min(start + 4, hi);
            insertionSort(a, start, end);
            int median = start + (end - start) / 2;
            swap(a, lo + numGroups, median);
            numGroups++;
        }

        int mid = numGroups / 2;
        if (numGroups % 2 == 0) {
            mid = mid - 1;
        }
        return select(a, lo, lo + numGroups - 1, lo + mid, depth + 1);
    }

    private void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }

    private void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        swaps++;
    }
}