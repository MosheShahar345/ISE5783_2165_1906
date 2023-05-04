package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric shape in 3D Cartesian coordinate system.
 * It provides a method to calculate the normal vector of a point on the shape.
 */
public interface Geometry extends Intersectable {

    /**
     * Returns the normal vector of a point on the geometry shape.
     * @param point the point on the geometry shape
     * @return the normal vector of the point
     */
    public Vector getNormal(Point point);
}

