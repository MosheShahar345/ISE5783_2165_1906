package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Testing Triangle */
class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {

        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(2, 3, 4);
        Point p3 = new Point(3, 4, 5);
        Vector v = new Vector(1,-3, 2).normalize();
        Triangle tri = new Triangle(p1, p2, p3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Bad normal
        assertEquals(1d, tri.getNormal(p1).length(), 0.00001,
                "Wrong normal length");

        // TC02: Ensuring the vector is normal
        assertTrue(tri.getNormal(p1).equals(v) || tri.getNormal(p1).equals(v.scale(-1.0)),
                "Wrong normal for triangle");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Triangle tri = new Triangle(new Point(1, 0, 0), new Point(-1, 0, 0),
                new Point(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Inside triangle (1 point)
        List<Point> result = tri.findIntersections(
                new Ray(new Point(0, 0.5, -1), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");

        // TC02: Outside against edge (0 points)
        assertNull(tri.findIntersections(new Ray(new Point(0, -0.5, -1), new Vector(0, 0, 1))),
                "Ray's outside against edge, there is no intersection point");

        // TC03: Outside against vertex (0 points)
        assertNull(tri.findIntersections(new Ray(new Point(1.5, -0.25, -1), new Vector(0, 0, 1))),
                "Ray's outside against vertex, there is no intersection point");

        // =============== Boundary Values Tests ==================

        // TC11: On edge
        assertNull(tri.findIntersections(new Ray(new Point(0.5, 0, -1), new Vector(0, 0, 1))),
                "Ray's lying on edge, there is no intersection point");

        // TC12: In vertex
        assertNull(tri.findIntersections(new Ray(new Point(1, 0, -1), new Vector(0, 0, 1))),
                "Ray's lying in vertex, there is no intersection point");

        // TC13: On edge's continuation
        assertNull(tri.findIntersections(new Ray(new Point(1.5, 0, -1), new Vector(0, 0, 1))),
                "Ray's lying on edge's continuation, there is no intersection point");
    }
}