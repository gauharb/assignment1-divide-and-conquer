import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    public long distanceChecks;
    public int maxDepth;

    private Point[] byY;
    private Point best1;
    private Point best2;
    private double bestDist;

    public Point[] solve(Point[] points) {
        distanceChecks = 0;
        maxDepth = 0;
        best1 = null;
        best2 = null;
        bestDist = Double.POSITIVE_INFINITY;

        if (points.length < 2) {
            return null;
        }

        Point[] pts = points.clone();
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        byY = new Point[pts.length];
        rec(pts, 0, pts.length - 1, 1);
        return new Point[]{best1, best2};
    }

    public double getBestDistance() {
        return bestDist;
    }

    private void rec(Point[] pts, int lo, int hi, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        int n = hi - lo + 1;


        if (n <= 3) {
            for (int i = lo; i <= hi; i++) {
                for (int j = i + 1; j <= hi; j++) {
                    check(pts[i], pts[j]);
                }
            }
            sortByY(pts, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        double midX = pts[mid].x;

        rec(pts, lo, mid, depth + 1);
        rec(pts, mid + 1, hi, depth + 1);

        mergeByY(pts, lo, mid, hi);

        int size = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pts[i].x - midX) < bestDist) {
                byY[size++] = pts[i];
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && byY[j].y - byY[i].y < bestDist; j++) {
                check(byY[i], byY[j]);
            }
        }
    }

    private void check(Point p, Point q) {
        distanceChecks++;
        double d = Point.distance(p, q);
        if (d < bestDist) {
            bestDist = d;
            best1 = p;
            best2 = q;
        }
    }

    private void sortByY(Point[] pts, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            Point key = pts[i];
            int j = i - 1;
            while (j >= lo && pts[j].y > key.y) {
                pts[j + 1] = pts[j];
                j--;
            }
            pts[j + 1] = key;
        }
    }

    private void mergeByY(Point[] pts, int lo, int mid, int hi) {
        System.arraycopy(pts, lo, byY, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                pts[k] = byY[j++];
            } else if (j > hi) {
                pts[k] = byY[i++];
            } else if (byY[j].y < byY[i].y) {
                pts[k] = byY[j++];
            } else {
                pts[k] = byY[i++];
            }
        }
    }
}