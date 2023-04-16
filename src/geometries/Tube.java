package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Tube class represents a two-dimensional tube in a 3D Cartesian coordinate system.
 * It extends the RadialGeometry class and implements the Geometry interface.
 */
public class Tube extends RadialGeometry {

    /** The axis ray of the tube */
    protected Ray axisRay;

    /**
     * Constructs a Tube object with the given radius and axis ray.
     * @param radius   the radius of the tube
     * @param axisRay  the axis ray of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * @return the axis ray of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}