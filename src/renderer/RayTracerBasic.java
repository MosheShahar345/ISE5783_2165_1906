package renderer;

import geometries.Intersectable.GeoPoint;
import geometries.Triangle;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import java.util.List;

import static primitives.Util.alignZero;

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

    /**
     * The delta value used for moving the rays for shadows.
     */
    private static final double DELTA = 0.1;

    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at the given intersection point with the provided ray.
     * @param gp  the intersection point
     * @param ray the ray
     * @return the calculated color
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp, ray));
    }

    /**
     * diffusion/specular calculation of the local effects of the light
     * sources on the given intersection point.
     * @param gp  the intersection point
     * @param ray the ray
     * @return the calculated color from local effects
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                if (unshaded(gp, l, n, lightSource, nl)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(
                            calcDiffusive(material.kD, nl, iL),
                            calcSpecular(material.kS, n, l, nl, v, iL, material.nShininess)
                    );
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive reflection of light on the surface.
     * @param kD  the diffuse reflection coefficients
     * @param nl  the dot product of the surface normal and light vector
     * @param iL  the intensity of the light
     * @return the calculated diffusive color
     */
    private Color calcDiffusive(Double3 kD, double nl, Color iL) {
        return iL.scale(kD.scale(Math.abs(nl)));
    }

    /**
     * Calculates the specular reflection of light on the surface.
     * @param kS        the specular reflection coefficients
     * @param n         the surface normal vector
     * @param l         the light vector
     * @param nl        the dot product of the surface normal and light vector
     * @param v         the view vector
     * @param iL        the intensity of the light
     * @param shininess the shininess value of the material
     * @return the calculated specular color
     */
    private Color calcSpecular(Double3 kS, Vector n, Vector l, double nl, Vector v, Color iL, int shininess) {
        Vector r = l.subtract(n.scale(nl * 2));
        double vr = -alignZero(v.dotProduct(r));
        if (vr <= 0) return Color.BLACK;
        Double3 shin = kS.scale(Math.pow(vr, shininess));
        return iL.scale(shin);
    }

    /**
     * Checks if the given point is unshaded by other objects in the scene in the direction of the light source.
     * @param gp          the intersection point
     * @param l           the direction vector towards the light source
     * @param n           the surface normal vector at the intersection point
     * @param lightSource the light source
     * @param nl          the dot product of the surface normal and light vector
     * @return true if the point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nl) {
        // Calculate the direction from the point to the light source
        Vector lightDirection = l.scale(-1); // From point to light source

        // Calculate an epsilon vector to prevent self-shadowing artifacts
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);

        // Calculate the point slightly offset from the intersection point
        Point point = gp.point.add(epsVector);

        // Create a ray from the offset point towards the light source
        Ray lightRay = new Ray(point, lightDirection);

        // Find intersections of the ray with the scene geometries
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        // If no intersections are found, the point is unshaded
        if (intersections == null) return true;

        // Check if any intersection point is closer to the light source than the current point
        for (var geoPoint : intersections) {
            if (gp.point.distance(geoPoint.point) < lightSource.getDistance(gp.point)) return false;
        }

        return true;
    }
}

