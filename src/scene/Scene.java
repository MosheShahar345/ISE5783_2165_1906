package scene;

import geometries.Geometries;
import lighting.LightSource;
import primitives.Color;
import lighting.AmbientLight;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * This class represents a scene in a 3D environment, containing various objects and properties.
 */
public class Scene {

    /**
     * The name of the scene.
     */
    private final String name;

    /**
     * The background color of the scene.
     */
    private final Color background;

    /**
     * The ambient light of the scene.
     */
    private AmbientLight ambientLight;

    /**
     * The geometries contained in the scene.
     */
    private final Geometries geometries;

    /**
     * The list of light sources.
     */
    private final List<LightSource> lights;

    /**
     * Constructs a Scene object using the SceneBuilder.
     * @param builder The SceneBuilder object used to construct the Scene.
     */
    private Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
        lights = builder.lights;
    }

    /**
     * Sets the ambient light of the scene.
     * @param ambientLight The ambient light to set.
     * @return The updated Scene object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Returns the name of the scene.
     * @return The name of the scene.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the background color of the scene.
     * @return The background color of the scene.
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Returns the ambient light of the scene.
     * @return The ambient light of the scene.
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * Returns the geometries contained in the scene.
     * @return The geometries contained in the scene.
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * Returns the list of light sources in the scene.
     * @return The list of light sources in the scene.
     */
    public List<LightSource> getLights() {
        return lights;
    }

    /**
     * The builder class for constructing Scene objects.
     */
    public static class SceneBuilder {

        /**
         * The name of the scene. Required field.
         */
        private final String name;

        /**
         * The list of light sources in the scene. Default value is an empty list.
         */
        private List<LightSource> lights = new LinkedList<>();

        /**
         * The background color of the scene. Default value is black.
         */
        private Color background = Color.BLACK;

        /**
         * The ambient light of the scene. Default value is none.
         */
        private AmbientLight ambientLight = AmbientLight.NONE;

        /**
         * The geometries contained in the scene. Default value is an empty Geometries object.
         */
        private Geometries geometries = new Geometries();

        /**
         * Constructs a SceneBuilder object with the given scene name.
         * @param name The name of the scene.
         */
        public SceneBuilder(String name) {
            this.name = name;
        }

        /**
         * Sets the background color of the scene.
         * @param background The background color to set.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * Sets the list of light sources in the scene.
         * @param lights The list of light sources to set.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        /**
         * Sets the ambient light of the scene.
         * @param ambientLight The ambient light to set.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * Sets the geometries contained in the scene.
         * @param geometries The geometries to set.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * Builds and returns the Scene object. Validates the required resources before constructing the scene.
         * @return The constructed Scene object.
         * @throws MissingResourceException if the name or geometries resource is missing.
         */
        public Scene build() {
            if (this.name == null) {
                throw new MissingResourceException("Missing resources", String.class.getName(), "");
            }
            if (this.geometries == null) {
                throw new MissingResourceException("Missing resources", Geometries.class.getName(), "");
            }
            return new Scene(this);
        }
    }
}