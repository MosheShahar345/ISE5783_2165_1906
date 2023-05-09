package scene;

import geometries.Geometries;
import primitives.Color;
import lighting.AmbientLight;

public class Scene {
    public String name;

    public Color background = Color.BLACK;

    public AmbientLight ambientLight = AmbientLight.NONE;

    public Geometries geometries;

    public Scene(String name) {
        this.name = name;
        this.geometries = new Geometries();
    }

    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
