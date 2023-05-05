package renderer;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
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
}
