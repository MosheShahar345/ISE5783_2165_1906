package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * Intersectable class represents intersection points
 */
public interface Intersectable {

    /***
     * Returns the intersection points between a Ray and a Geo-object,
     * implemented differently depending on the Geo-object type.
     * An empty list is returned if there are no intersections.
     * @param ray
     * @return a list of points that intersect the Geo-object
     */
    public List<Point> findIntersections(Ray ray);
}
