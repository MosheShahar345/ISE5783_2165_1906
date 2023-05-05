package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** Testing CameraIntegration */
public class CameraIntegrationTest {

    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    @Test
    public void cameraRaySphereIntegration() {
        Camera cam1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1),
                new Vector(0, -1, 0));
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1),
                new Vector(0, -1, 0));

        // TC01: Small Sphere after VP (2 points)
        assertCountOfIntersections(cam1, new Sphere(1, new Point(0, 0, -3)), 2);

        // TC02: Big Sphere (18 points)
        assertCountOfIntersections(cam2, new Sphere(2.5, new Point(0, 0, -2.5)), 18);

        // TC03: Medium Sphere (10 points)
        assertCountOfIntersections(cam2, new Sphere(2, new Point(0, 0, -2)), 10);

        // TC04: Inside Sphere (9 points)
        assertCountOfIntersections(cam2, new Sphere(4, new Point(0, 0, -1)), 9);

        // TC05: Beyond Sphere before VP (0 points)
        assertCountOfIntersections(cam1, new Sphere(0.5, new Point(0, 0, 1)), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    public void cameraRayPlaneIntegration() {
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1),
                new Vector(0, -1, 0));

        // TC01: Plane against camera (9 points)
        assertCountOfIntersections(cam, new Plane(new Point(0, 0, -2), new Vector(0, 0, 1)), 9);

        // TC02: Plane with small angle (9 points)
        assertCountOfIntersections(cam, new Plane(new Point(0, 0, -2), new Vector(0, 1, 2)), 9);

        // TC03: Plane parallel to lower rays (6 points)
        assertCountOfIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6);

        // TC04: Beyond Plane (0 points)
        assertCountOfIntersections(cam, new Plane(new Point(0, 0, 3), new Vector(-1, 0, 0)), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    public void cameraRayTriangleIntegration() {
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1),
                new Vector(0, -1, 0));

        // TC01: Medium triangle (2 point)
        assertCountOfIntersections(cam, new Triangle(new Point(0, 20, -2), new Point(1, -1, -2),
                new Point(-1, -1, -2)), 2);

        // TC02: Small triangle (1 points)
        assertCountOfIntersections(cam, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2),
                new Point(-1, -1, -2)), 1);
    }

    /**
     * Test helper function to count the intersections and compare with expected value
     * @param cam camera for the test
     * @param geo 3D body to test the integration of the camera with
     * @param expected amount of intersections
     */
    private void assertCountOfIntersections(Camera cam, Intersectable geo, int expected) {
        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        int nX = 3;
        int nY = 3;
        int count = 0;

        // Loop through each point on the camera view plane
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                // Construct a ray from the camera to the current point on the view plane
                Ray ray = cam.constructRay(nX, nY, j, i);

                // Find the intersection points between the ray and the 3D body
                List<Point> intersectionPoints = geo.findIntersections(ray);

                // Add the number of intersection points found to the total count
                count += intersectionPoints != null ? intersectionPoints.size() : 0;
            }
        }
        // Compare the count of intersection points with the given expected value
        assertEquals(expected, count, "Wrong amount of intersections");
    }
}
