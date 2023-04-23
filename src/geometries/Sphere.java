package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Sphere class represents a two-dimensional sphere in a 3D Cartesian coordinate system.
 * It extends the RadialGeometry class and implements the Geometry interface.
 */
public class Sphere extends RadialGeometry {

    /** The center of the sphere */
    private Point center;

    /**
     * Constructs a Sphere object with the given radius and center point.
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * @return the center point of the sphere
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector v = center.subtract(point);
        return v.normalize();
    }
}