package primitives;

/**
 * Vector class represents a vector in 3D Cartesian coordinate system.
 * It extends the Point class, which holds the vector's (x, y, z) values.
 */
public class Vector extends Point {

    /**
     * Constructs a new Vector with given (x, y, z) values.
     * @param x the x coordinate of the vector.
     * @param y the y coordinate of the vector.
     * @param z the z coordinate of the vector.
     * @throws IllegalArgumentException if the vector's values are (0,0,0).
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector ZERO is not allowed");
        }
    }

    /**
     * Constructs a new Vector with given Double3 object.
     * @param double3 a Double3 object containing the (x, y, z) values of the vector.
     * @throws IllegalArgumentException if the vector's values are (0,0,0).
     */
    Vector(Double3 double3) {
        super(double3);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector ZERO is not allowed");
        }
    }

    /**
     * Adds the given vector to this vector and returns a new Vector object.
     * @param vector the vector to be added.
     * @return a new Vector object that is the sum of the two vectors.
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * Scales this vector by the given factor and returns a new Vector object.
     * @param d the factor to scale the vector by.
     * @return a new Vector object that is the scaled vector.
     */
    public Vector scale(double d) {
        return new Vector(this.xyz.d1 * d, this.xyz.d2 * d, this.xyz.d3 * d);
    }

    /**
     * Calculates the dot product of this vector with the given vector.
     * @param vector the vector to calculate the dot product with.
     * @return the dot product of the two vectors.
     */
    public double dotProduct(Vector vector) {
        return this.xyz.d1 * vector.xyz.d1 +
                this.xyz.d2 * vector.xyz.d2 +
                this.xyz.d3 * vector.xyz.d3;
    }

    /**
     * Calculates the cross product of this vector with the given vector.
     * @param vector the vector to calculate the cross product with.
     * @return a new Vector object that is the cross product of the two vectors.
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                this.xyz.d3 * vector.xyz.d1 - this.xyz.d1 * vector.xyz.d3,
                this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
    }

    /**
     * Calculates the squared length of this vector.
     * @return the squared length of this vector.
     */
    public double lengthSquared() {
        return this.xyz.d1 *this.xyz.d1
                + this.xyz.d2 *this.xyz.d2
                +this.xyz.d3 *this.xyz.d3;
    }

    /**
     * Calculates the length of this vector.
     * @return the length of this vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector and returns a new Vector object.
     * @return a new Vector object that is the normalized vector of this vector.
     * @throws ArithmeticException if the length of this vector is zero.
     */
    public Vector normalize(){
        double len = length();

        return new Vector(xyz.reduce(len));
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

