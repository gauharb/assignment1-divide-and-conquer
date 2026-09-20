import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SelectTest {

    @Test
    void hundredRandomTests() {
        Random rnd = new Random(3);
        for (int t = 0; t < 100; t++) {
            int n = 1 + rnd.nextInt(2000);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(1000);
            }
            int k = rnd.nextInt(n);

            int[] sorted = a.clone();
            Arrays.sort(sorted);

            int result = new DeterministicSelector().select(a.clone(), k);
            assertEquals(sorted[k], result, "n=" + n + " k=" + k);
        }
    }

    @Test
    void minimumAndMaximum() {
        int[] a = {9, 3, 7, 1, 8, 2, 6};
        assertEquals(1, new DeterministicSelector().select(a.clone(), 0));
        assertEquals(9, new DeterministicSelector().select(a.clone(), a.length - 1));
    }

    @Test
    void singleElement() {
        assertEquals(5, new DeterministicSelector().select(new int[]{5}, 0));
    }

    @Test
    void allEqual() {
        int[] a = new int[500];
        Arrays.fill(a, 4);
        assertEquals(4, new DeterministicSelector().select(a, 250));
    }

    @Test
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> new DeterministicSelector().select(new int[0], 0));
        assertThrows(IllegalArgumentException.class,
                () -> new DeterministicSelector().select(new int[]{1, 2, 3}, 3));
        assertThrows(IllegalArgumentException.class,
                () -> new DeterministicSelector().select(new int[]{1, 2, 3}, -1));
    }
}