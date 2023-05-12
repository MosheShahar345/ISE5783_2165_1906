package lighting;

import primitives.Color;
import java.util.Collections;

/**
 * This abstract class represents a light source.
 */
abstract class Light {

    /** The intensity of the light source */
    private Color intensity;

    /**
     * Constructs a light with the specified intensity.
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Retrieves the intensity of the light.
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
