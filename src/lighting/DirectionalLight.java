package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class represents a directional light source.
 */
public class DirectionalLight extends Light implements LightSource {

    /** The direction of the light. */
    private Vector direction;

    /**
     * Constructs a directional light with the specified intensity and direction.
     * @param intensity the intensity of the directional light
     * @param direction the direction of the light
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
