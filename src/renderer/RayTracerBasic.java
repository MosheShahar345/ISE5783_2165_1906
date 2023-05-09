package renderer;

import primitives.Color;
import primitives.Ray;
import primitives.Point;
import scene.Scene;

import java.util.List;

/**
 * The RayTracerBasic class is a concrete implementation of the abstract RayTracerBase class.
 * This class provides a basic implementation of the ray tracing algorithm.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructor that takes a scene object as an argument.
     * @param scene the scene to be rendered
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point point = ray.findClosestPoint(intersections);
        return calcColor(point);
    }

    /**
     * Calculates the color at the given point in the scene.
     * @param point the point in 3D space at which to calculate the color
     * @return the color at the given point
     */
    private Color calcColor(Point point) {
        Color color = scene.ambientLight.getIntensity();
        return color;
    }
}

