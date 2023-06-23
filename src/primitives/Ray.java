package primitives;

import java.util.LinkedList;
import java.util.Objects;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * Ray class represents a ray in 3D Cartesian coordinate system
 */
public class Ray {

    /** Point of reference */
    final private Point p0;

    /** Vector of the ray */
    final private Vector dir;

    /** The delta value used for moving ray's point. */
    private static final double DELTA = 0.1;

    /**
     * Constructs a Ray object with the given origin point and direction vector.
     * @param p0 the origin point of the ray
     * @param dir the direction vector of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        double d = alignZero(dir.length() - 1);
        if (!isZero(d)) { // if the vector is not normalized
            this.dir = dir.normalize();
        }
        else
            this.dir = dir;
    }

    /**
     * Constructs a Ray object with the given head, direction, and normal vectors.
     * @param head      the starting point of the ray
     * @param direction the direction vector of the ray
     * @param normal    the normal vector used for calculations
     */
    public Ray(Point head, Vector direction, Vector normal) {
        this.dir = direction.normalize();
        double nv = normal.dotProduct(this.dir);
        if (isZero(nv)) {
           this.p0 = head;
        }
        else {
            Vector delta = normal.scale(nv > 0 ? DELTA : -DELTA);
            this.p0 = head.add(delta);
        }
    }

    /**
     * @return the origin point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * @return the direction vector of the ray
     */
    public Vector getDir() {
        return dir;
    }

    /***
     * Returns a new point that is located at a distance of delta along the direction
     * of this line segment, starting from point p0.
     * @param delta the distance from p0 along the line segment direction
     * @return a new Point object representing the point located at delta along the line segment
     */
    public Point getPoint(double delta) {
        if (isZero(delta)) {
            return p0;
        }
        return p0.add(dir.scale(delta));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ray other)) return false;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                " p0=" + p0 +
                "  dir=" + dir +
                '}';
    }

    /**
     * Find the closest point in a given list of points to a reference point p0
     * @param geoPoints a list of points to search from
     * @return the closest point to point reference of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        // Check if the list is empty and return null if it is
        if (geoPoints.isEmpty())
            return null;

        // Initialize the closest point to the first point in the list
        GeoPoint closest = geoPoints.get(0);

        // Iterate through all the points in the list
        for (var point : geoPoints) {
            // if the distance between the current point and the reference point p0 is less than the distance
            // between the closest point and the reference point p0, set the current point as the closest point
            if (point.point.distance(p0) < closest.point.distance(p0))
                closest = point;
        }
        // return the closest point
        return closest;
    }

    /**
     * Finds the closest point from the given list of points.
     * @param points the list of points to search from
     * @return the closest point, or null if the input list is null or empty
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null :
                findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Generates a beam of rays from a list of points towards a target point.
     * @param points The list of points representing the source positions of the rays.
     * @param p The target point towards which the rays are directed.
     * @return A list of rays pointing from the source points towards the target point.
     */
    public static List<Ray> beamOfRays(List<Point> points, Point p) {
        List<Ray> rays = new LinkedList<>();

        // Generate rays from source points to the target point
        for (Point point : points) {
            rays.add(new Ray(point, p.subtract(point)));
        }

        return rays;
    }
}