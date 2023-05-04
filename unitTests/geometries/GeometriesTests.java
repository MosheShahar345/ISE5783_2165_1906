package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Testing Geometries */
class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Geometries geo = new Geometries();

        // =============== Boundary Values Tests ==================

        // TC11: Empty collection
        assertNull(geo.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0))),
                "Must not be intersections");

        geo.add(new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                new Sphere(1d, new Point(0, 1, 0)));
        
        // TC12: No intersection with any shape
        assertNull(geo.findIntersections(new Ray(new Point(4, 0, 0), new Vector(-4, 3, 4))),
                "Must not be intersections");

        // TC13: Intersection with one shape only
        assertEquals(1, geo.findIntersections(new Ray(new Point(1.5, 1.5, 2),
                        new Vector(-2, -2, 1.5))).size(), "Must be only one intersection");

        // TC14: Intersection with all shapes
        assertEquals(4, geo.findIntersections(new Ray(new Point(2, 2, 0.5),
                        new Vector(-1, -1, 0))).size(),
                "Must be four intersections. (2 in sphere, 1 in plane, 1 in triangle)");

        // ============ Equivalence Partitions Tests ==============

        // TC01: Intersection with couple shapes but not all
        assertEquals(3, geo.findIntersections(new Ray(new Point(2, 2, 0),
                        new Vector(-2,-1,0.5))).size(),
                "Must be three intersections. (2 in sphere, 1 in plane)");
    }
}