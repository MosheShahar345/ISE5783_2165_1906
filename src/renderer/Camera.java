package renderer;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Color;
import java.util.*;
import static primitives.Util.*;

/**
 * The Camera class represents a view of the geometric world captured through a view plane,
 * producing graphic views using ray-object intersections.
 * The camera's orientation is determined by its orthogonal right, up,
 * and front directions relative to the original x,y,z axis.
 */

public class Camera {

    /**
     * The camera location
     */
    private Point p0;

    /**
     * X axis vector
     */
    private Vector vTo;

    /**
     * Y axis vector
     */
    private Vector vUp;

    /**
     * Z axis vector
     */
    private Vector vRight;

    /**
     * Object's actual distance from the camera center
     */
    private double distance;

    /**
     * Object's actual width
     */
    private double width;

    /**
     * Object's actual height
     */
    private double height;

    /**
     * The ImageWriter object used to save the rendered image.
     */
    private ImageWriter imageWriter;

    /**
     * The RayTracerBase object used to trace rays in the scene.
     */
    private RayTracerBase rayTracer;

    /**
     * Flag indicating whether depth of field is enabled.
     */
    private Boolean dof = false;

    /**
     * Radius of the aperture for depth of field.
     */
    private double apertureRadius = 0;

    /**
     * Focal length for depth of field.
     */
    private double focalLength = 0;

    /**
     * List of points within the aperture for depth of field.
     */
    private List<Point> dofPoints = null;

    /**
     * Density of points within the aperture for depth of field.
     */
    private int density;

    private boolean adaptive = false;

    private int multithreading = 1;

    public Camera setAdaptive(boolean adaptive) {
        this.adaptive = adaptive;
        return this;
    }

    public Camera setMultithreading(int multithreading) {
        this.multithreading = multithreading;
        return this;
    }

    /**
     * Sets the ImageWriter object for this camera.
     * @param imageWriter the ImageWriter object to be set
     * @return this (Builder design pattern)
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the RayTracerBase object for this camera.
     * @param rayTracer the RayTracerBase object to be set
     * @return this (Builder design pattern)
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the flag indicating whether depth of field is enabled.
     * @return The depth of field flag.
     */
    public Boolean getDof() {
        return dof;
    }

    /**
     * Returns the radius of the aperture for depth of field.
     * @return The aperture radius.
     */
    public double getApertureRadius() {
        return apertureRadius;
    }

    /**
     * Returns the focal length for depth of field.
     * @return The focal length.
     */
    public double getFocalLength() {
        return focalLength;
    }

    /**
     * Returns the list of points within the aperture for depth of field.
     * @return The list of depth of field points.
     */
    public List<Point> getDofPoints() {
        return dofPoints;
    }

    /**
     * Returns the density of points within the aperture for depth of field.
     * @return The density of depth of field points.
     */
    public int getDensity() {
        return density;
    }

    /**
     * Sets the flag indicating whether depth of field is enabled.
     * @param dof The depth of field flag to set.
     * @return this (Builder design pattern)
     */
    public Camera setDof(Boolean dof) {
        this.dof = dof;
        return this;
    }

    /**
     * Sets the radius of the aperture for depth of field.
     * @param apertureRadius The aperture radius to set.
     * @return this (Builder design pattern)
     */
    public Camera setApertureRadius(double apertureRadius) {
        this.apertureRadius = apertureRadius;
        return this;
    }

    /**
     * Sets the focal length for depth of field.
     * @param focalLength The focal length to set.
     * @return this (Builder design pattern)
     */
    public Camera setFocalLength(double focalLength) {
        this.focalLength = focalLength;
        return this;
    }

    /**
     * Sets the list of points within the aperture for depth of field.
     * @param dofPoints The list of depth of field points to set.
     * @return this (Builder design pattern)
     */
    public Camera setDofPoints(List<Point> dofPoints) {
        this.dofPoints = dofPoints;
        return this;
    }

    /**
     * Sets the density of points within the aperture for depth of field.
     * @param density The density of depth of field points to set.
     * @return this (Builder design pattern)
     */
    public Camera setDensity(int density) {
        this.density = density;
        return this;
    }

    /**
     * Constructor for the Camera class.
     * @param p0 camera's location
     * @param vTo X axis vector
     * @param vUp Y axis vector
     * @throws IllegalArgumentException if vTo and vUp are not orthogonal
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vTo and vUp are not orthogonal");
        }
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        vRight = this.vTo.crossProduct(this.vUp);
    }

    /**
     * Set the distance for the View Plane.
     * @param distance from camera to view plane
     * @return this (Builder design pattern)
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Set the width and height for the View Plane
     * @param width The width of the View Plane
     * @param height The height of the View Plane
     * @return this (Builder design pattern)
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Constructs a ray from the camera through the specified
     * pixel located at (j, i) in View Plane.
     * @param nX The number of pixels in the x direction on the View Plane.
     * @param nY The number of pixels in the y direction on the View Plane.
     * @param j The x coordinate of the pixel being traced (0 <= j < nX).
     * @param i The y coordinate of the pixel being traced (0 <= i < nY).
     * @return A Ray object that represents the ray from the camera through the specified pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Image center
        Point Pc = p0.add(vTo.scale(distance));

        // Ratio (pixel width & height)
        double Ry = height / nY;
        double Rx = width / nX;

        // Pixel[i,j] center
        Point pIJ = Pc;

        // Delta values for moving on the View Plane
        double yI = -(i - (nY - 1) / 2d) * Ry;
        double xJ = (j - (nX - 1) / 2d) * Rx;

        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        // Vector from camera's lens in the direction of point(i,j) in the View Plane
        Vector vIJ = pIJ.subtract(p0);

        return new Ray(p0, vIJ);
    }

    /**
     * Renders the image by tracing rays for each pixel and writing the corresponding color to the image.
     * @return this (Builder design pattern)
     * @throws MissingResourceException if the imageWriter or rayTracer field is null.
     */
    public Camera renderImage() {
        try {
            // Check if the imageWriter field is not null, otherwise throw a MissingResourceException
            if (imageWriter == null) {
                throw new MissingResourceException("Empty field", ImageWriter.class.getName(), "");
            }
            // Check if the rayTracer field is not null, otherwise throw a MissingResourceException
            if (rayTracer == null) {
                throw new MissingResourceException("Empty field", RayTracerBase.class.getName(), "");
            }
        }
        catch (UnsupportedOperationException e) {
            // If any other exception is thrown during the execution of the function, catch it and throw an UnsupportedOperationException
            throw new UnsupportedOperationException("Not implemented yet" + e.getClass());
        }

        // Get the dimensions of the image from the imageWriter object
        int nY = imageWriter.getNy();
        int nX = imageWriter.getNx();

        if (dof) { // If depth of field is active
            dofPoints = Point.pointsInAperture(p0, vUp, vRight, density, apertureRadius);
            // Loop over each pixel in the image and call the castBeamRay function with the pixel coordinates
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    castBeamRay(nX ,nY, i, j, dofPoints);
                }
            }
        }
        else {
            // Loop over each pixel in the image and call the castRay function with the pixel coordinates
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    castRay(nX ,nY, i, j);
                }
            }
        }

        // Return the instance of the Camera object to support chaining with other methods
        return this;
    }

    /**
     * Casts a ray from the camera's position through a specific pixel in the view plane,
     * calculates the color of the intersection point with the scene and writes it to
     * the corresponding pixel in the image.
     * @param nX The number of pixels in the horizontal axis.
     * @param nY The number of pixels in the vertical axis.
     * @param i The horizontal coordinate of the pixel.
     * @param j The vertical coordinate of the pixel.
     */
    private void castRay(int nX, int nY, int i, int j) {
        // Construct a ray from the camera's position through the specified pixel in the view plane
        Ray ray = constructRay(nX, nY, j, i);
        // Use the rayTracer object to calculate the color of the intersection point with the scene
        Color color = rayTracer.traceRay(ray);
        // Write the color to the corresponding pixel in the image using the imageWriter object
        imageWriter.writePixel(j, i, color);
    }

    /**
     * Casts a beam ray through a specific pixel on the view plane and traces it to calculate the color.
     * @param nX The total number of pixels along the X-axis of the image.
     * @param nY The total number of pixels along the Y-axis of the image.
     * @param i The Y-coordinate of the pixel to cast the ray through.
     * @param j The X-coordinate of the pixel to cast the ray through.
     * @param points The list of points within the aperture for depth of field.
     */
    private void castBeamRay(int nX, int nY, int i, int j, List<Point> points) {
        // Get the point of a ray from the camera's position through the specified pixel in the view plane
        Point point = constructRay(nX, nY, j, i).getPoint(focalLength);
        // Use the rayTracer object to calculate the average color of the beam of rays
        Color color = rayTracer.traceBeam(Ray.beamOfRays(points, point));
        // Write the color to the corresponding pixel in the image using the imageWriter object
        imageWriter.writePixel(j, i, color);
    }

    /**
     * Prints a grid of lines with a given interval and color on the image.
     * If the ImageWriter is null, a MissingResourceException is thrown.
     * The method loops through the image and sets the pixels corresponding to the
     * grid lines to the given color.
     * @param interval The interval between grid lines
     * @param color The color of the grid lines
     * @throws MissingResourceException If the ImageWriter object is null
     */
    public void printGrid(int interval, Color color) {
        // Throw an exception if the ImageWriter object is null
        if (imageWriter == null) {
            throw new MissingResourceException("Empty field", ImageWriter.class.getName(), "");
        }

        // Loop through the image and set the pixels corresponding to the grid lines to the given color
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                // If the pixel is on a grid line (either horizontal or vertical)
                if (i % interval == 0 || j % interval == 0) {
                    // Set the pixel to the specified color
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Writes the rendered image to an image file using the image writer object.
     * Throws a MissingResourceException if the imageWriter object is null.
     * @throws MissingResourceException If the ImageWriter object is null.
     */
    public void writeToImage() {
        // Throw an exception if the ImageWriter object is null
        if (imageWriter == null) {
            throw new MissingResourceException("Empty field", ImageWriter.class.getName(), "");
        }

        // Call the writeToImage method of the ImageWriter object to write the image to a file
        imageWriter.writeToImage();
    }

    /**
     * Rotate camera through axis and angle of rotation
     * @param axis Axis of rotation
     * @param theta Angle of rotation (degrees)
     */
    public void cameraRotation(Vector axis, double theta)
    {
        // Rotate all vector's using Vector.rotateVector Method
        if (theta == 0) return; // No rotation
        vUp = vUp.vectorRotation(axis, theta);
        vRight = vRight.vectorRotation(axis, theta);
        vTo = vTo.vectorRotation(axis, theta);
    }

    /**
     * Move camera (move point of view of the camera)
     * @param move {@link Vector} Vertical distance
     */
    public void moveCamera(Vector move) {
        // Move Point0 according to params
        Point newPoint = new Point(p0.getXyz());
        newPoint=  newPoint.add(move);
        p0 = newPoint;
    }

    public Point getP0() {
        return this.p0;
    }

    public Camera setP0(Point p) {
        this.p0 = p;
        return this;
    }
}
