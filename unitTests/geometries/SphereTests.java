package geometries;

import org.junit.jupiter.api.Test;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;

/** Testing Sphere */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {

        Sphere sph = new Sphere(1, new Point(0, 0, 0));
        Vector v = new Vector(0, -1, 0).normalize();
        Point p = new Point(0, 1, 0);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Bad normal
        assertEquals(1d, sph.getNormal(p).length(), 0.00001, "Wrong normal length");

        // TC02: Ensuring the vector is normal
        assertEquals(sph.getNormal(p), v, "Wrong normal for sphere");
    }
}