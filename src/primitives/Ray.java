package primitives;

import java.util.Objects;

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
     * @param p0  the origin point of the ray
     * @param dir the direction vector of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        if (dir.length() != 1) { // if the vector is not normalized
            dir.normalize();
        }
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