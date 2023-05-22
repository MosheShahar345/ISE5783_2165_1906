package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

/**
 * The Sphere class represents a two-dimensional sphere in a 3D Cartesian coordinate system.
 * It extends the RadialGeometry class and implements the Geometry interface.
 */
public class Sphere extends RadialGeometry {

    /** The center of the sphere */
    final private Point center;

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
        Vector v = point.subtract(center);
        return v.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        double tm = 0, d = 0;
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        if(alignZero(radius - maxDistance) > 0){
            return null;
        }

        // Ensuring the reference point of ray is not the center of the sphere
        if (!this.center.equals(p0)) {
            Vector u = this.center.subtract(ray.getP0());
            tm = v.dotProduct(u);
            d = Math.sqrt(u.dotProduct(u) - tm * tm);
        }

        // Check if the distance from ray to center is bigger than the radius
        if (d >= this.radius) {
            return null; // There are no intersections
        }

        double th = Math.sqrt(this.radius * this.radius - d * d);
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        // There are two points intersecting
        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
            return List.of(
                    new GeoPoint(this, ray.getPoint(t1)),
                    new GeoPoint(this, ray.getPoint(t2))
            );
        }
        if (t1 > 0 && t2 <= 0 && alignZero(t1 - maxDistance) <= 0) { // There is only one point intersecting (p1)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        }
        if (t2 > 0 && t1 <= 0 && alignZero(t2 - maxDistance) <= 0) { // There is only one point intersecting (p2)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }

        return null;
    }
}

