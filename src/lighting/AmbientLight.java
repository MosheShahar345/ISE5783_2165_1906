package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * This class represents an ambient light source that uniformly illuminates the scene.
 */
public class AmbientLight extends Light {

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
        super(Ia.scale(Ka));
    }

    /**
     * Constructs a new AmbientLight object with the specified intensity and ambient coefficient.
     * @param Ia the intensity of the ambient light source
     * @param Ka the ambient coefficient
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
}