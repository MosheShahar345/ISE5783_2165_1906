package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This interface represents a light source.
 */
public interface LightSource {

    /**
     * Retrieves the intensity of the light at the specified point.
     * @param p the point at which to retrieve the intensity
     * @return the intensity of the light at the specified point
     */
    public Color getIntensity(Point p);

    /**
     * Retrieves the direction vector from the light source to the specified point.
     * @param p the point for which to retrieve the direction vector
     * @return the direction vector from the light source to the specified point
     */
    public Vector getL(Point p);
}

