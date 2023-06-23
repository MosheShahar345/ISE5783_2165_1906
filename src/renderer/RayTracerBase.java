package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

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

    /**
     * Traces a beam of rays and returns the average color of the traced rays.
     * @param rays The beam of rays to trace.
     * @return The average color of the traced rays.
     */
    public Color traceBeam(List<Ray> rays) {
        Color averageColor = Color.BLACK;
        for (Ray ray : rays) {
            averageColor = averageColor.add(traceRay(ray));
        }
        return averageColor.scale(1.0 / rays.size());
    }

    public abstract Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector vRight, Vector vUp, List<Point> prePoints);
}
