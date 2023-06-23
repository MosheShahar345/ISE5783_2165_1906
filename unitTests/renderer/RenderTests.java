package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 */
public class RenderTests {

    /**
     * Produce a scene with basic 3D model and render it into a png image with a grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene.SceneBuilder("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                        new Double3(1, 1, 1))) //
                .setBackground(new Color(75, 127, 90)).build();

        scene.getGeometries().add(new Sphere(50d, new Point(0, 0, -100)),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100),
                        new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100),
                        new Point(100, -100, -100))); // down
        // right
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(YELLOW));
        camera.writeToImage();
    }

    // For stage 6 - please disregard in stage 5

    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene.SceneBuilder("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2))).build(); //

        scene.getGeometries().add( // center
                new Sphere(50, new Point(0, 0, -100)),
                // up left
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100),
                        new Point(-100, 100, -100)).setEmission(new Color(GREEN)),
                // down left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)).setEmission(new Color(RED)),
                // down right
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100),
                        new Point(100, -100, -100)).setEmission(new Color(BLUE)));

        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("color render test", 1001, 1001))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(WHITE));
        camera.writeToImage();
    }

    /**
     * Tests the depth of field effect with three spheres in the scene.
     */
    @Test
    public void threeSpheresDof() {
        // Create the scene
        Scene scene = new Scene.SceneBuilder("Test def")
                .setAmbientLight(new AmbientLight(new Color(30, 30, 30), 0.16)).build();

        // Create the camera with depth of field settings
        Camera camera = new Camera(
                new Point(0, 20, 2500),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(200, 200).setVPDistance(850).setDof(true).setFocalLength(1600).setApertureRadius(20).setDensity(9);

        // Add a plane to the scene
        scene.getGeometries().add(
                new Plane(
                        new Point(0, 0, 0),
                        new Vector(0, 0, 1))
                        .setEmission(new Color(0, 20, 20))
                        .setMaterial(new Material()
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(60)
                                .setKr(0.2)
                        ));

        // Add three spheres to the scene
        scene.getGeometries().add(
                new Sphere(70, new Point(-100, 0, 300)).setMaterial(new Material()
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKr(0.3)
                ),

                new Sphere(70, new Point(0, 0, 900)).setMaterial(new Material()
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKr(0.3)
                ),

                new Sphere(70, new Point(100, 0, 1500)).setMaterial(new Material()
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKr(0.3)
                )
        );

        // Add a directional light to the scene
        scene.getLights().add(
                new DirectionalLight(new Color(255, 0, 255), new Vector(1, 0, 0))
        );

        // Create an image writer
        ImageWriter imageWriter = new ImageWriter("depthOfFieldThreeSpheres", 1200, 1200);

        // Set the image writer and ray tracer for the camera
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of sphere and cube in a room, illuminated by two narrow beamed spotLights.
     * Includes transparency and reflection properties of the room, and the geometries inside it.
     */
    @Test
    public void myTestRoomWithSphereAndCube() {
        Scene scene = new Scene.SceneBuilder("Test scene").build();

        Camera camera = new Camera(new Point(0, 0, 350), new Vector(0, 0, -1),
                new Vector(0, 1, 0)).setVPSize(150, 150).setVPDistance(150)
                .setDof(false).setFocalLength(50).setApertureRadius(20).setDensity(5);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.01));

        //------------Geometries------------//
        scene.getGeometries().add(
                //------------Polygons for the room------------//
                new Polygon( // Front wall
                        new Point(150, 150, -150),
                        new Point(150,-150,-150),
                        new Point(-150,-150,-150),
                        new Point(-150,150,-150)).setEmission(
                        new Color(GRAY)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(10)),

                new Polygon( // Right wall
                        new Point(150,150,-150),
                        new Point(150,-150,-150),
                        new Point(150,-150,150),
                        new Point(150,150,150)).setEmission(
                        new Color(GREEN)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(10)),

                new Polygon( // Left wall
                        new Point(-150,150,-150),
                        new Point(-150,-150,-150),
                        new Point(-150,-150,150),
                        new Point(-150,150,150)).setEmission(
                        new Color(RED)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(10)),

                new Polygon( // Ceiling
                        new Point(150,150,-150),
                        new Point(-150,150,-150),
                        new Point(-150,150,150),
                        new Point(150,150,150)).setEmission(
                        new Color(GRAY)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(10)),

                new Polygon( // Floor
                        new Point(-150,-150,-150),
                        new Point(150,-150,-150),
                        new Point(150,-150,150),
                        new Point(-150,-150,150)).setEmission(
                        new Color(GRAY)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(10).setKr(0.1)),

                //------------Geometries inside the room------------//
                // Sphere on the left side of the room
                new Sphere(53,
                        new Point(-83,-98,-90)).setEmission(new Color(50, 50, 224)).setMaterial(
                        new Material().setKr(0.3).setKd(0.2).setKs(0.2).setShininess(100)),

                // Polygons for the cube
                new Polygon(
                        new Point(60,-30,45),
                        new Point(-15,-30,-30),
                        new Point(60,-30,-106),
                        new Point(136,-30,-30)
                ).setMaterial(new Material()
                        .setKr(0.4)
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKt(0.1)
                ).setEmission(new Color(200,10,60)),

                new Polygon(
                        new Point(60,-150,45),
                        new Point(-15,-150,-30),
                        new Point(60,-150,-106),
                        new Point(136,-150,-30)
                ).setMaterial(new Material()
                        .setKr(0.4)
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKt(0.1)
                ).setEmission(new Color(200,10,60)),

                new Polygon(
                        new Point(60,-150,45),
                        new Point(60,-30,45),
                        new Point(-15,-30,-30),
                        new Point(-15,-150,-30)
                ).setMaterial(new Material()
                        .setKr(0.4)
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKt(0.1)
                ).setEmission(new Color(200,10,60)),

                new Polygon(
                        new Point(60,-150,45),
                        new Point(60,-30,45),
                        new Point(136,-30,-30),
                        new Point(136,-150,-30)
                ).setMaterial(new Material()
                        .setKr(0.4)
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKt(0.1)
                ).setEmission(new Color(200,10,60)),

                new Polygon(
                        new Point(136,-150,-30),
                        new Point(136,-30,-30),
                        new Point(60,-30,-106),
                        new Point(60,-150,-106)
                ).setMaterial(new Material()
                        .setKr(0.4)
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKt(0.1)
                ).setEmission(new Color(200,10,60)),

                new Polygon(
                        new Point(60,-150,-106),
                        new Point(60,-30,-106),
                        new Point(-15,-30,-30),
                        new Point(-15,-150,-30)
                ).setMaterial(new Material()
                        .setKr(0.4)
                        .setKd(0.6)
                        .setKs(0.4)
                        .setShininess(100)
                        .setKt(0.1)
                ).setEmission(new Color(200,10,60))
        );

        //------------LightSources------------//
        scene.getLights().add( // Spot from the left
                new SpotLight(new Color(GRAY), new Point(-130, 0, 150), new Vector(1,0,-1)).setNarrowBeam(3)
        );
        scene.getLights().add( // Spot from the right
                new SpotLight(new Color(GRAY), new Point(130, 100, 150), new Vector(-1,0,-1)).setNarrowBeam(3)
        );

        //------------Write to image------------//
        ImageWriter imageWriter = new ImageWriter("roomWithSphereAndCube", 1200, 1200);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

//   /** Test for XML based scene - for bonus */
//   @Test
//   public void basicRenderXml() {
//      Scene  scene  = new Scene("XML Test scene");
//      // enter XML file name and parse from XML file into scene object
//      // using the code you added in appropriate packages
//      // ...
//      // NB: unit tests is not the correct place to put XML parsing code
//
//      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))     //
//         .setVPDistance(100)                                                                //
//         .setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
//         .setRayTracer(new RayTracerBasic(scene));
//      camera.renderImage();
//      camera.printGrid(100, new Color(YELLOW));
//      camera.writeToImage();
//   }
}
