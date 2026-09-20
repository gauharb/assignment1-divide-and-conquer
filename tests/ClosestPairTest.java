import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClosestPairTest {

    private static double bruteForce(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                best = Math.min(best, Point.distance(pts[i], pts[j]));
            }
        }
        return best;
    }

    @Test
    void matchesBruteForceOnRandomInputs() {
        Random rnd = new Random(4);
        for (int t = 0; t < 30; t++) {
            int n = 2 + rnd.nextInt(2000);
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
            }
            ClosestPairSolver solver = new ClosestPairSolver();
            solver.solve(pts);
            assertEquals(bruteForce(pts), solver.getBestDistance(), 1e-9, "n=" + n);
        }
    }

    @Test
    void handlesDuplicatePoints() {
        Point[] pts = {new Point(1, 1), new Point(5, 5), new Point(1, 1), new Point(9, 2)};
        ClosestPairSolver solver = new ClosestPairSolver();
        solver.solve(pts);
        assertEquals(0.0, solver.getBestDistance(), 1e-12);
    }

    @Test
    void twoPoints() {
        ClosestPairSolver solver = new ClosestPairSolver();
        solver.solve(new Point[]{new Point(0, 0), new Point(3, 4)});
        assertEquals(5.0, solver.getBestDistance(), 1e-12);
    }

    @Test
    void fewerThanTwoPointsReturnsNull() {
        ClosestPairSolver solver = new ClosestPairSolver();
        assertNull(solver.solve(new Point[0]));
        assertNull(solver.solve(new Point[]{new Point(1, 1)}));
    }

    @Test
    void allPointsOnVerticalLine() {

        Point[] pts = new Point[100];
        for (int i = 0; i < pts.length; i++) {
            pts[i] = new Point(7, i * 3);
        }
        ClosestPairSolver solver = new ClosestPairSolver();
        solver.solve(pts);
        assertEquals(3.0, solver.getBestDistance(), 1e-12);
    }
}
