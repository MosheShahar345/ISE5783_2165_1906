package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class represents a point light source.
 */
public class PointLight extends Light implements LightSource {

    /** The position of the light source. */
    private Point position;

    /** The constant attenuation factor. */
    private double kC = 1;

    /** The linear attenuation factor. */
    private double kL = 0;

    /** The quadratic attenuation factor. */
    private double kQ = 0;

    /**
     * Constructs a point light with the specified intensity, position.
     * @param intensity the intensity of the point light
     * @param position  the position of the light source
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor of the point light.
     * @param kC the constant attenuation factor
     * @return this (Builder design pattern).
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor of the point light.
     * @param kL the linear attenuation factor
     * @return this (Builder design pattern).
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor of the point light.
     * @param kQ the quadratic attenuation factor
     * @return this (Builder design pattern).
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        Color I0 = getIntensity();
        double distance = p.distance(position);
        double distanceSquared = distance * distance;
        double factor = kC + (kL * distance) + (kQ * distanceSquared);
        return I0.reduce(factor);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(this.position);
    }
}
