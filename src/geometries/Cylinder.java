package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class represents a cylinder in 3D Cartesian coordinate system.
 * A cylinder is a tube with a given height.
 */
public class Cylinder extends Tube {

    /** height of the tube */
    private double height;

    /**
     * Creates a new Cylinder object with the given radius, axis ray, and height.
     * @param radius   the radius of the cylinder
     * @param axisRay  the axis ray of the cylinder
     * @param height   the height of the cylinder
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    /**
     * @return the height of this cylinder
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}