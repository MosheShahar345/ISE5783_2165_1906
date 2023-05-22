package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

/**
 * The Triangle class represents a two-dimensional triangle in a 3D Cartesian coordinate system.
 * It extends the Polygon class, which is a collection of connected vertices.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a Triangle object with the given three vertices.
     * @param p1 the first vertex of the triangle
     * @param p2 the second vertex of the triangle
     * @param p3 the third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = plane.findGeoIntersections(ray, maxDistance);
        if (intersections == null) {
            return null;
        }

        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // Calculate the vectors from the starting point of the ray to each vertex of the triangle
        Vector v1 = this.vertices.get(0).subtract(p0);
        Vector v2 = this.vertices.get(1).subtract(p0);
        Vector v3 = this.vertices.get(2).subtract(p0);

        // Calculate the normal vectors of each edge of the triangle
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Calculate the dot product between each edge's
        // normal vector and the ray's direction vector
        double res1 = alignZero(v.dotProduct(n1));
        double res2 = alignZero(v.dotProduct(n2));
        double res3 = alignZero(v.dotProduct(n3));

        // If any dot product is zero, the ray is parallel to the
        // triangle's plane and there is no intersection
        if (isZero(res1) || isZero(res2) || isZero(res3)){
            return null;
        }

        // If all three dot products have the same sign, the ray
        // intersects the triangle's plane at a point inside the triangle
        if ((res1 > 0 && res2 > 0 && res3 > 0) || (res1 < 0 && res2 < 0 && res3 < 0)){
            return List.of(new GeoPoint(this, intersections.get(0).point));
        }

        return null;
    }
}

