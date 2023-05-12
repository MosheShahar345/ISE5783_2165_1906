package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;
import java.util.Objects;

/**
 * Intersectable class represents intersection points
 */
public abstract class Intersectable {

    /**
     * Represents a geometric point associated with a specific geometry.
     */
    public static class GeoPoint {

        /**
         * The geometry associated with the point.
         */
        public Geometry geometry;

        /**
         * The geometric point.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint object with the specified geometry and point.
         * @param geometry the geometry associated with the point
         * @param point    the geometric point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds the intersections of the ray with the geometry.
     * @param ray the ray to find intersections with
     * @return a list of intersection points, or null if no intersections were found
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the geometric intersections of the ray with the geometry.
     * @param ray the ray to find geometric intersections with
     * @return a list of geometric intersection points
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method for finding geometric intersections of the ray with the geometry.
     * @param ray the ray to find geometric intersections with
     * @return a list of geometric intersection points
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
