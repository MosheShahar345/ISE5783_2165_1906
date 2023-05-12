package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class represents a spotlight source, which is a type of point light source with a specific direction.
 */
public class SpotLight extends PointLight {

    /** The direction of the light. */
    private Vector direction;

    /**
     * The narrow beam value of the spotLight.
     */
    private double narrowBeam = 1;

    /**
     * Sets the narrow beam value of the spotLight.
     * @param narrowBeam the narrow beam value to set
     * @return this (Builder design pattern).
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * Constructs a spotlight with the specified intensity, position, and direction.
     * @param intensity the intensity of the spotlight
     * @param position  the position of the light source
     * @param direction the direction of the spotlight
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        Color Il = super.getIntensity(p);
        double projection = Math.max(0, direction.dotProduct(getL(p)));
        projection = Math.pow(projection, narrowBeam); // for a narrow beam
        return Il.scale(projection);
    }
}

