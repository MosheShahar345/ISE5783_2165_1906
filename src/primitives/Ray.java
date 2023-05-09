package primitives;

import java.util.Objects;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import java.util.List;

/**
 * Ray class represents a ray in 3D Cartesian coordinate system
 */
public class Ray {

    /** Point of reference */
    final private Point p0;

    /** Vector of the ray*/
    final private Vector dir;

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
     * @param list a list of points to search from
     * @return the closest point to point reference of the ray
     */
    public Point findClosestPoint(List<Point> list) {
        // Check if the list is empty and return null if it is
        if (list.isEmpty())
            return null;

        // Initialize the closest point to the first point in the list
        Point closest = list.get(0);

        // Iterate through all the points in the list
        for (Point point : list) {
            // if the distance between the current point and the reference point p0 is less than the distance
            // between the closest point and the reference point p0, set the current point as the closest point
            if (point.distance(p0) < closest.distance(p0))
                closest = point;
        }
        // return the closest point
        return closest;
    }
}