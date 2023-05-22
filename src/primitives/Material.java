package primitives;

/**
 * This class represents the material properties of an object.
 */
public class Material {

    /**
     * The diffuse reflection coefficients for red, green, and blue components.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * The specular reflection coefficients for red, green, and blue components.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * The shininess value of the material.
     */
    public int nShininess;

    /**
     * The transmission coefficient for transparency.
     * Represents the attenuation factor for transparency.
     * kt - "Transparency Attenuation Coefficient"
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient for reflectivity.
     * Represents the attenuation factor for reflectivity.
     * kr - "Reflectivity Attenuation Coefficient"
     */
    public Double3 kR = Double3.ZERO;

    /**
     * Sets the transmission coefficient for transparency.
     * This coefficient represents the attenuation factor for transparency.
     * @param kT the transmission coefficient to set
     * @return this (Builder design pattern).
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets the reflection coefficient for reflectivity.
     * This coefficient represents the attenuation factor for reflectivity.
     * @param kR the reflection coefficient to set
     * @return this (Builder design pattern).
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Sets the transmission coefficient for transparency.
     * This coefficient represents the attenuation factor for transparency.
     * @param kT the transmission coefficient to set
     * @return this (Builder design pattern).
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Sets the reflection coefficient for reflectivity.
     * This coefficient represents the attenuation factor for reflectivity.
     * @param kR the reflection coefficient to set
     * @return this (Builder design pattern).
     */
    public Material setKr(double kR) {
        this.kR =new Double3(kR);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficients of the material.
     * @param kD the diffuse reflection coefficients to set
     * @return this (Builder design pattern).
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the specular reflection coefficients of the material.
     * @param kS the specular reflection coefficients to set
     * @return this (Builder design pattern).
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material with a single value for all components.
     * @param kD the diffuse reflection coefficient to set
     * @return this (Builder design pattern).
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material with a single value for all components.
     * @param kS the specular reflection coefficient to set
     * @return this (Builder design pattern).
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess value of the material.
     * @param nShininess the shininess value to set
     * @return this (Builder design pattern).
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
