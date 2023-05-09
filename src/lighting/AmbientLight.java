package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {

    final private Color intensity;
    final public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public AmbientLight(Color Ia, Double3 Ka) {
        Color Ip = Ia.scale(Ka);
        this.intensity = Ip;
    }

    public AmbientLight(Color Ia, double Ka) {
        Color Ip = Ia.scale(Ka);
        this.intensity = Ip;
    }

    public Color getIntensity() {
        return intensity;
    }
}
