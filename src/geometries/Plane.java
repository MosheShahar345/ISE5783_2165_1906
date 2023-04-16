package geometries;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * The Plane class represents a two-dimensional plane in a 3D Cartesian coordinate system.
 * It implements the Geometry interface.
 */
public class Plane implements Geometry {

    /** The base point of the plane */
    final private Point p0;

    /** The normal vector of the plane */
   final  private Vector normal;

    /**
     * Constructs a Plane object with the given base point and normal vector.
     * If the normal vector is not already normalized, it is normalized before being stored.
     * @param p0 the base point of the plane
     * @param normal the normal vector of the plane
     */
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        if (!isZero(normal.length() - 1d)) { // if the vector is not normalized
            normal.normalize();
        }
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
        this.p0 = p1;

        Vector N = (p1.subtract(p2)).crossProduct(p1.subtract(p3));   // AB X AC

        //right hand rule
        this.normal = N.normalize();
    }

    /**
     * @return the base point of the plane
     */
    public Point getP0() {
        return p0;
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
}