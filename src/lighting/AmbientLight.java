package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * This class represents an ambient light source that uniformly illuminates the scene.
 */
public class AmbientLight {

    /**
     * The intensity of the ambient light source.
     */
    final private Color intensity;

    /**
     * A constant representing no ambient light.
     */
    final public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructs a new AmbientLight object with the specified intensity and ambient coefficient.
     * @param Ia the intensity of the ambient light source
     * @param Ka the ambient coefficient
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        Color Ip = Ia.scale(Ka);
        this.intensity = Ip;
    }

    /**
     * Constructs a new AmbientLight object with the specified intensity and ambient coefficient.
     * @param Ia the intensity of the ambient light source
     * @param Ka the ambient coefficient
     */
    public AmbientLight(Color Ia, double Ka) {
        Color Ip = Ia.scale(Ka);
        this.intensity = Ip;
    }

    /**
     * Gets the intensity of the ambient light source.
     * @return the intensity of the ambient light source
     */
    public Color getIntensity() {
        return intensity;
    }
}