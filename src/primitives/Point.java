package primitives;

import java.util.Objects;

/**
 * Point class represents a point in 3D Cartesian coordinate system
 */
public class Point {

    /** Double3 to represent a point with 3 double type coordinates */
    final Double3 xyz;

    /**
     * Constructs a new point with the specified coordinates.
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point(double x, double y, double z){
        xyz = new Double3(x,y,z);
    }

    /**
     * Constructs a new point with the specified Double3 object.
     * @param double3 the Double3 object containing the coordinates of the point
     */
    Point(Double3 double3){
        this(double3.d1, double3.d2, double3.d3);
    }

    /**
     * Returns the vector from this point to the specified point.
     * @param point the point to subtract from this point
     * @return the vector from this point to the specified point
     */
    public Vector subtract(Point point){
        return new Vector(this.xyz.subtract(point.xyz));
    }

    /**
     * Returns a new point that is the result of adding the specified vector to this point.
     * @param vector the vector to add to this point
     * @return the new point that is the result of adding the specified vector to this point
     */
    public Point add(Vector vector){
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * Returns the square of the distance between this point and the specified point.
     * @param point the point to calculate the distance to
     * @return the square of the distance between this point and the specified point
     */
    public double distanceSquared(Point point){
        return  (this.xyz.d1 - point.xyz.d1) * (this.xyz.d1 - point.xyz.d1) +
                (this.xyz.d2 - point.xyz.d2) * (this.xyz.d2 - point.xyz.d2) +
                (this.xyz.d3 - point.xyz.d3) * (this.xyz.d3 - point.xyz.d3);
    }

    /**
     * Returns the distance between this point and the specified point.
     * @param point the point to calculate the distance to
     * @return the distance between this point and the specified point
     */
    public double distance(Point point){
        return Math.sqrt(distanceSquared(point));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point other)) return false;
        return xyz.equals(other.xyz);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.xyz);
    }

    @Override
    public String toString(){
        return "Point: " + this.xyz;
    }
}