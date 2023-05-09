package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.List;

/** Testing Ray */
class RayTests {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(java.util.List<Point>)}.
     */
    @Test
    void testFindClosestPoint() {

        Ray ray = new Ray(new Point(1,0,0), new Vector(1, 0, 0));
        Point p1 = new Point(0.5, 0, 0);
        Point p2 = new Point(1, 1, 0);
        Point p3 = new Point(1, 3, 1);
        List<Point> result = new LinkedList<>();

        // =============== Boundary Values Tests ==================

        // TC11: Empty list given as parameter (0 points)
        assertNull(ray.findClosestPoint(result), "Empty list as parameter -> 0 points");

        // TC12: The first point is the closest to p0
        result.add(p1);
        result.add(p2);
        result.add(p3);
        assertEquals(result.get(0), ray.findClosestPoint(result), "The point is not the closest");

        // TC13: The last point is the closest to p0
        result.clear();
        result.add(p3);
        result.add(p2);
        result.add(p1);
        assertEquals(result.get(2), ray.findClosestPoint(result), "The point is not the closest");

        // ============ Equivalence Partitions Tests ==============
        // TC01: The middle point is the closest to p0
        result.clear();
        result.add(p3);
        result.add(p1);
        result.add(p2);
        assertEquals(result.get(1), ray.findClosestPoint(result), "The point is not the closest");
    }
}