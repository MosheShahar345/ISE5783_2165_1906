package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Testing Plane */
class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}.
     * */
    @Test
    void testGetNormal() {
        Plane pol = new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 1, 1).normalize();

        // TC01: Bad normal
        assertEquals(1d, pol.getNormal().length(), 0.00001, "Wrong normal length");

        // TC02: Ensuring the vector is normal
        assertTrue(pol.getNormal().equals(v) || pol.getNormal().equals(v.scale(-1.0)),
                "Wrong normal for plane");

    }

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}.
     * */
    @Test
    public void testConstructor() {
        // ============ Boundary Values Tests ==============

        // TC10: Two points are the same
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1),
                        new Point(1, 0, 0)), //
                "Two points are coincide, cannot construct a plane");

        // TC11: All the points lie on the same line
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(1, 1, 1), new Point(1, 1, 2),
                        new Point(1, 1, 3)), //
                "All points are collinear points");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}.
     * */
    @Test
    public void findIntersections(){
        Plane plane = new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0,0,1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is intersect the plane (1 point)
        List<Point> result = plane.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");

        // TC01: Ray's line is out of the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Ray's line out of plane");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC11: Ray's not included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1))),
                "Ray's line out of plane");

        // TC12: Ray's included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, -1, 1))),
                "Ray's line included in plane");

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray is orthogonal into the plane (1 point)
        result = plane.findIntersections(new Ray(new Point(-1, 1, -3), new Vector(1, 1, 1)));
        assertEquals(1, result.size(), "Ray's line intersect the plane");

        // TC14: Ray is orthogonal out of the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0, -1, 0),
                new Vector(-1, -1, -1))), "Ray's line out of the plane");

        // TC15: Ray is orthogonal before the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(2, 3, 0),
                new Vector(1, 1, 1))), "Ray's line before the plane");

        // TC16: Ray is orthogonal before and collinear to the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0.5, 0.5),
                new Vector(1, 1, 1))), "Ray's line before and collinear to the plane");

        // **** Group: Special cases
        // TC17: Ray is neither orthogonal nor parallel to and begins at the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0.5, 0.5),
                new Vector(1, 1, 0))), "Ray begins at the plane"); //??

        // **** Group: Special cases
        // TC17: Ray is neither orthogonal nor parallel to the plane and from plane's Q point
        assertNull(plane.findIntersections(new Ray(new Point(1, 0, 0),
                        new Vector(1, 1, 0))), "Must not be plane intersection");//??
    }
}