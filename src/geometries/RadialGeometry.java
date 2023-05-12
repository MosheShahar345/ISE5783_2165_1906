package geometries;

/**
 * The RadialGeometry abstract class represents a two-dimensional radial-geometry shape
 * in a 3D Cartesian coordinate system. It implements the Geometry interface.
 */
public abstract class RadialGeometry extends Geometry {

    /** The radius of the shape */
    final protected double radius;

    /**
     * Constructs a RadialGeometry object with the given radius.
     * @param radius the radius of the shape
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * Returns the radius of the shape.
     * @return the radius of the shape
     */
    public double getRadius() {
        return radius;
    }
}