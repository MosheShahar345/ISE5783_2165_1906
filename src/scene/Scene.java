package scene;

import geometries.Geometries;
import primitives.Color;
import lighting.AmbientLight;

/**
 * This class represents a scene in the 3D environment, containing various objects and properties.
 */
public class Scene {

    /**
     * The name of the scene.
     */
    public String name;

    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;

    /**
     * The ambient light of the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * The geometries contained in the scene.
     */
    public Geometries geometries;

    /**
     * Creates a new scene with the given name.
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
        this.geometries = new Geometries();
    }

    /**
     * Sets the name of the scene.
     * @param name The name of the scene.
     * @return this (Builder design pattern).
     */
    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the background color of the scene.
     * @param background The background color of the scene.
     * @return this (Builder design pattern).
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     * @param ambientLight The ambient light of the scene.
     * @return this (Builder design pattern).
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries contained in the scene.
     * @param geometries The geometries contained in the scene.
     * @return this (Builder design pattern).
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}