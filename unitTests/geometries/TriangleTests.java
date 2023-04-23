package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
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
}