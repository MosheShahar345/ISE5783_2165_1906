package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
     * The maximum level of recursive color calculation.
     * This constant determines the maximum number of recursive calls
     * to calculate the color of a point in the scene.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum coefficient threshold for color calculation.
     * This constant determines the minimum value of the coefficient
     * below which the color calculation is considered negligible
     * and will not be further processed recursively.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * An initial coefficient for the probability of the contribution to reflection
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.getBackground() : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color for a given GeoPoint and Ray.
     * @param gp  The GeoPoint for which the color is being calculated.
     * @param ray The Ray used for calculating the color.
     * @return The calculated color.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        //return scene.ambientLight.getIntensity().add(calcLocalEffects(gp, ray));
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());
    }

    /**
     * Calculates the color for a given intersection, ray, level, and coefficient.
     * @param intersection The GeoPoint intersection.
     * @param ray          The Ray used for calculating the color.
     * @param level        The recursion level.
     * @param k            The coefficient.
     * @return The calculated color.
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculates the global effects for a given GeoPoint, Ray, recursion level, and coefficient.
     * @param gp    The GeoPoint.
     * @param ray   The Ray used for calculating the global effects.
     * @param level The recursion level.
     * @param k     The coefficient.
     * @return The calculated color representing the global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.kR, kkr = k.product(kr);
        Ray reflectedRay = constructReflectedRay(gp, v, n);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(reflectedRay, level - 1, kr, kkr)).scale(kr);
        }
        Double3 kt = material.kT, kkt = k .product(kt);
        Ray refractedRay = constructRefractedRay(gp, v, n);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(refractedRay, level - 1, kt, kkt)).scale(kt);
        }
        return color;
    }

    /**
     * Calculates the global effect for a given Ray, recursion level, coefficient, and coefficient product.
     * @param ray   The Ray used for calculating the global effect.
     * @param level The recursion level.
     * @param k     The coefficient.
     * @param kx    The coefficient product.
     * @return The calculated color representing the global effect.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.getBackground().scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))
                ? Color.BLACK : calcColor(gp, ray, level - 1, kkx);
    }

    /**
     * Constructs a reflected Ray for a given GeoPoint, incoming Vector, and surface normal Vector.
     * @param gp The GeoPoint.
     * @param v  The incoming Vector.
     * @param n  The surface normal Vector.
     * @return The constructed reflected Ray, or null if the incoming Vector is parallel to the surface.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        double vn = alignZero(v.dotProduct(n));
        if (vn == 0) {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(gp.point, r, n);
    }

    /**
     * Constructs a refracted Ray for a given GeoPoint, incoming Vector, and surface normal Vector.
     * @param gp The GeoPoint.
     * @param v  The incoming Vector.
     * @param n  The surface normal Vector.
     * @return The constructed refracted Ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

    /**
     * Finds the closest intersection GeoPoint for a given Ray.
     * @param ray The Ray for which to find the closest intersection.
     * @return The closest intersection GeoPoint, or null if no intersection is found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * Calculates the local effects for a given GeoPoint, Ray, and coefficient.
     * @param gp  The GeoPoint.
     * @param ray The Ray used for calculating the local effects.
     * @param k   The coefficient.
     * @return The calculated color representing the local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Double3 ktr = transparency(gp,lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
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
        Double3 shine = kS.scale(Math.pow(vr, shininess));
        return iL.scale(shine);
    }

    /**
     * Checks if the given point is unshaded by other objects in the scene in the direction of the light source.
     * @param gp          the intersection point
     * @param l           the direction vector towards the light source
     * @param n           the surface normal vector at the intersection point
     * @param lightSource the light source
     * @return true if the point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        // Calculate the direction from the point to the light source
        Vector lightDirection = l.scale(-1); // From point to light source

        // Create a ray from the offset point towards the light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        // Find intersections of the ray with the scene geometries
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay);

        // If no intersections are found, the point is unshaded
        if (intersections == null) return true;

        // Check if any intersection point is closer to the light source than the current point
        for (var geoPoint : intersections) {
            if (geoPoint.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K)) {
                if (gp.point.distance(geoPoint.point) < lightSource.getDistance(gp.point)) return false;
            }
        }

        return true;
    }

    /**
     * Calculates the transparency coefficient for a given GeoPoint, LightSource, light direction, and surface normal.
     * @param geoPoint The GeoPoint.
     * @param ls       The LightSource.
     * @param l        The light direction vector.
     * @param n        The surface normal vector.
     * @return The transparency coefficient.
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {

        Vector lightDirection = l.scale(-1); // From point to light source

        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        double maxDistance = ls.getDistance(geoPoint.point);

        // Find intersections of the ray with the scene geometries
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);

        if (intersections == null) return Double3.ONE;

        Double3 ktr = Double3.ONE;

        for (var gp : intersections) {
            // Accumulate the transparency coefficient based on the materials of the intersected geometries
            ktr = gp.geometry.getMaterial().kT.product(ktr);
            // If the accumulated transparency coefficient is lower than the threshold, return complete transparency
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }

        return ktr; // Return the transparency coefficient
    }

    @Override
    public Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints) {
        if (Width < minWidth * 2 || Height < minHeight * 2) {
            return this.traceRay(new Ray(cameraLoc, centerP.subtract(cameraLoc))) ;
        }

        List<Point> nextCenterPList = new LinkedList<>();
        List<Point> cornersList = new LinkedList<>();
        List<Color> colorList = new LinkedList<>();
        Point tempCorner;
        Ray tempRay;
        for (int i = -1; i <= 1; i += 2){
            for (int j = -1; j <= 1; j += 2) {
                tempCorner = centerP.add(Vright.scale(i * Width / 2)).add(Vup.scale(j * Height / 2));
                cornersList.add(tempCorner);
                if (prePoints == null || !isInList(prePoints, tempCorner)) {
                    tempRay = new Ray(cameraLoc, tempCorner.subtract(cameraLoc));
                    nextCenterPList.add(centerP.add(Vright.scale(i * Width / 4)).add(Vup.scale(j * Height / 4)));
                    colorList.add(traceRay(tempRay));
                }
            }
        }


        if (nextCenterPList.size() == 0) {
            return Color.BLACK;
        }

        boolean isAllEquals = true;
        Color tempColor = colorList.get(0);
        for (Color color : colorList) {
            if (!tempColor.equals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            return tempColor;

        tempColor = Color.BLACK;
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveSuperSamplingRec(center, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup, cornersList));
        }
        return tempColor.reduce(nextCenterPList.size());
    }

    private boolean isInList(List<Point> pointsList, Point point) {
        for (Point tempPoint : pointsList) {
            if(point.equals(tempPoint))
                return true;
        }
        return false;
    }
}

