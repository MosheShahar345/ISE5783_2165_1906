package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * The Tube class represents a two-dimensional tube in a 3D Cartesian coordinate system.
 * It extends the RadialGeometry class and implements the Geometry interface.
 */
public class Tube extends RadialGeometry {

    /** The axis ray of the tube */
    final protected Ray axisRay;

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
    public Vector getNormal(Point point) { // point = (1, 0, 1)
        Vector v = axisRay.getDir(); // (1, 0, 0)
        Point P0 = axisRay.getP0(); // (0, 0, 0)

        double t = v.dotProduct(point.subtract(P0)); // t = v * (P - P0) = 1
        if(isZero(t)) { // check if the projection length is not 0
            throw new IllegalArgumentException("(P - P0) is orthogonal to v");
        }
        Point O = P0.add(v.scale(t)); // O = P0 + t * v = (1, 0, 0)

        Vector N = point.subtract(O); // N = P - O = (0, 0, 1)

        return N.normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}