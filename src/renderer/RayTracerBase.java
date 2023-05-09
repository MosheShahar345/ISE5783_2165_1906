package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase is an abstract class that provides the basic functionality for tracing rays in a scene.
 * The concrete subclasses of RayTracerBase implement specific ray tracing algorithms.
 */
public abstract class RayTracerBase {

    /**
     * The scene to be rendered.
     */
    protected Scene scene;

    /**
     * Constructor that takes a scene object as an argument.
     * @param scene the scene to be rendered
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract method that traces a given ray in the scene and returns the color at the intersection point.
     * @param ray the ray to be traced
     * @return the color at the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
