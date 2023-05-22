package renderer;

import static java.awt.Color.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows (with transparency)
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spotLight
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500),
                        new Vector(-1, -1, -2)).setKl(0.0004).setKq(0.0000006)); //

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotLight
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1),
                new Vector(0, 1, 0)).setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150),
                new Vector(-1, -1, -4)).setKl(0.00001).setKq(0.000005)); //

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spotLight with
     * a partially transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1),
                new Vector(0, 1, 0)).setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140),
                        new Point(75, 75, -150)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)).setMaterial(
                        new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0),
                new Vector(0, 0, -1)).setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles and three spheres, illuminated by multiple light sources.
     * Includes transparency and reflection properties on the spheres, and a sphere inside a sphere.
     */
    @Test
    public void myTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1),
                new Vector(0, 1, 0)).setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.09)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140),
                        new Point(75, 75, -150)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.09)),
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)).setMaterial(
                        new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.5)),
                new Sphere(40d, new Point(-40, -30, -80)).setEmission(new Color(RED)).setMaterial(
                        new Material().setKd(0.4).setKs(0.4).setShininess(50).setKt(0.8)),
                new Sphere(20d, new Point(-40, -30, -80)).setEmission(new Color(YELLOW)).setMaterial(
                        new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.8))
        );

        scene.lights.add(new SpotLight(new Color(400, 400, 400), new Point(-40, -30, 0),
                new Vector(0, 0, -1)).setKl(0.0005).setKq(0.0005));
        scene.lights.add(new SpotLight(new Color(800, 800, 800), new Point(60, 50, 0),
                new Vector(0, 0, -1)).setKl(0.0005).setKq(0.0005));
        scene.lights.add(new DirectionalLight(new Color(50, 50, 50), new Vector(-1, -1, -1)));

        ImageWriter imageWriter = new ImageWriter("myTestTrianglesAndSpheres", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}
