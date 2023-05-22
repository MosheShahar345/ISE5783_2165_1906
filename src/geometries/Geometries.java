package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A class for a collection of geometric shapes
 */
public class Geometries extends Intersectable {

    /**
     * A list of intersectable shapes.
     */
    private List<Intersectable> intersectables;

    /**
     * Constructs a new, empty Geometries shape.
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }

    /**
     * Constructs a new Geometries shape and adds the
     * specified Intersectable shapes to it.
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        this();
        add(geometries);
    }

    /**
     * Adds one or more Intersectable shapes to the list of geometries.
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(intersectables, geometries);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable item : intersectables) {
            List<GeoPoint> listItem = item.findGeoIntersections(ray, maxDistance);
            if (listItem != null) {
                if (result == null)
                    result = new LinkedList<>();
                result.addAll(listItem);
            }
        }
        return result;
    }
}
