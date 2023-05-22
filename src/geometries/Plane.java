package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents a two-dimensional plane in a 3D Cartesian coordinate system.
 * It implements the Geometry interface.
 */
public class Plane extends Geometry {

     /** The base point of the plane */
     final private Point q0;

     /** The normal vector of the plane */
     final private Vector normal;

    /**
     * Constructs a Plane object with the given base point and normal vector.
     * If the normal vector is not already normalized, it is normalized before being stored.
     * @param p0 the base point of the plane
     * @param normal the normal vector of the plane
     */
    public Plane(Point p0, Vector normal) {
        this.q0 = p0;
        double d = alignZero(normal.length() - 1);
        if (!isZero(d)) { // if the vector is not normalized
            this.normal = normal.normalize();
        }
        else
            this.normal = normal;
    }

    /**
     * Constructs a Plane object given three points on the plane.
     * The normal vector is calculated by taking the cross product of two vectors
     * formed by subtracting one point from the other two points.
     * @param p1 a point on the plane
     * @param p2 another point on the plane
     * @param p3 a third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3){
        this.q0 = p1;

        Vector u = p1.subtract(p2); // AB
        Vector v = p1.subtract(p3); // AC

        Vector n = u.crossProduct(v); // AB x AC

        this.normal = n.normalize(); // right hand rule
    }

    /**
     * @return referenced 3D point of the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector v = ray.getDir();
        Point p0 = ray.getP0();
        Vector n = normal;

        // Check if the point of the ray is the reference point of the plane
        if (q0.equals(p0)){
            return null;
        }

        double nv = alignZero(n.dotProduct(v)); // Denominator

        // Ray's lying in the plane axis
        if (isZero(nv)) {
            return null;
        }

        double nP0Q0 = alignZero(n.dotProduct(q0.subtract(p0))); // Numerator

        // Ray's parallel to the plane
        if (isZero(nP0Q0)) {
            return null;
        }

        double t = alignZero(nP0Q0 / nv);

        if (t < 0 || alignZero(t - maxDistance) > 0) {
            return null;
        }

        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}