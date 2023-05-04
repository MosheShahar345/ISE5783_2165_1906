package primitives;

import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Ray class represents a ray in 3D Cartesian coordinate system
 */
public class Ray {

    /** Point of reference */
    private Point p0;

    /** Vector of the ray*/
    private Vector dir;

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
}