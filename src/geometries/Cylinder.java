package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate system.
 * A cylinder is a tube with a given height.
 */
public class Cylinder extends Tube {

    /** height of the tube */
    final private double height;

    /**
     * Creates a new Cylinder object with the given radius, axis ray, and height.
     * @param radius   the radius of the cylinder
     * @param axisRay  the axis ray of the cylinder
     * @param height   the height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    /**
     * @return the height of this cylinder
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        Point topP = p0.add(v.scale(height));

        // Check if the given point is on the top of the cylinder
        if (point.equals(topP) || isZero(v.dotProduct(point.subtract(topP)))){
            return v.normalize();
        }

        // Check if the given point is on the bottom of the cylinder
        if (point.equals(p0) || isZero(v.dotProduct(point.subtract(p0)))){
            return v.normalize();
        }

        // If neither of them then compute like a tube
        return super.getNormal(point);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}