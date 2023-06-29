package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry abstract represents a geometric shape in 3D Cartesian coordinate system.
 * It provides a method to calculate the normal vector of a point on the shape.
 */
public abstract class Geometry extends Intersectable {

    /**
     * Emission color default is black.
     */
    protected Color emission = Color.BLACK;

    /**
     * The material of the object.
     */
    private Material material = new Material();

    /**
     * Gets the color emission of the object.
     * @return the color emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Gets the material of the object.
     * @return the material of the object
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the color emission of the object.
     * @param emission the color emission to set
     * @return this (Builder design pattern)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Sets the material of the geometry.
     * @param material the material to set
     * @return this (Builder design pattern)
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Returns the normal vector of a point on the geometry shape.
     * @param point the point on the geometry shape
     * @return the normal vector of the point
     */
    public abstract Vector getNormal(Point point);
}

