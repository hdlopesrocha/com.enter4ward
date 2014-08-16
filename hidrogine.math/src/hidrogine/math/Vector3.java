package hidrogine.math;

/**
 * The Class Vector3.
 */
public class Vector3 {

    /** The x. */
    private float x;

    /** The y. */
    private float y;

    /** The y. */
    private float z;

    /**
     * Instantiates a new vector3.
     */
    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * Instantiates a new vector3.
     *
     * @param xx
     *            the x
     * @param yy
     *            the y
     * @param zz
     *            the zz
     */
    public Vector3(final float xx, final float yy, final float zz) {
        x = xx;
        y = yy;
        z = zz;
    }

    /**
     * Instantiates a new vector3.
     *
     * @param vec
     *            the vec
     */
    public Vector3(final Vector3 vec) {
        x = vec.getX();
        y = vec.getY();
        z = vec.getZ();
    }

    /**
     * Gets the z.
     *
     * @return the z
     */
    public final float getZ() {
        return z;
    }

    /**
     * Sets the z.
     *
     * @param zz
     *            the new z
     */
    public final void setZ(final float zz) {
        z = zz;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public final float getX() {
        return x;
    }

    /**
     * Sets the x.
     *
     * @param xx
     *            the new x
     */
    public final void setX(final float xx) {
        x = xx;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public final float getY() {
        return y;
    }

    /**
     * Sets the y.
     *
     * @param yy
     *            the new y
     */
    public final void setY(final float yy) {
        y = yy;
    }

    /**
     * Adds the.
     *
     * @param vec
     *            the vec
     * @return the vector3
     */
    public final Vector3 add(final Vector3 vec) {
        x += vec.getX();
        y += vec.getY();
        z += vec.getZ();
        return this;
    }

    /**
     * Subtract.
     *
     * @param vec
     *            the vec
     * @return the vector3
     */
    public final Vector3 subtract(final Vector3 vec) {
        x -= vec.getX();
        y -= vec.getY();
        z -= vec.getZ();
        return this;
    }

    /**
     * Multiply.
     *
     * @param vec
     *            the vec
     * @return the vector3
     */
    public final Vector3 multiply(final Vector3 vec) {
        x *= vec.getX();
        y *= vec.getY();
        z *= vec.getZ();
        return this;
    }

    /**
     * Divide.
     *
     * @param vec
     *            the vec
     * @return the vector3
     */
    public final Vector3 divide(final Vector3 vec) {
        x /= vec.getX();
        y /= vec.getY();
        z /= vec.getZ();
        return this;
    }

    /**
     * Dot.
     *
     * @param v1
     *            the v1
     * @param v2
     *            the v2
     * @return the float
     */
    public static final float dot(final Vector3 v1, final Vector3 v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ()
                * v2.getZ();
    }


    /**
     * Cross.
     *
     * @param v1            the v1
     * @param v2            the v2
     * @param out the out
     */
    public static final void cross(final Vector3 v1, final Vector3 v2,
            final Vector3 out) {
        out.setX(v1.getY() * v2.getZ() - v2.getY() * v1.getZ());
        out.setX(v2.getX() * v1.getZ() - v1.getX() * v2.getZ());
        out.setX(v1.getX() * v2.getY() - v2.getX() * v1.getY());
    }

    /**
     * Cross.
     *
     * @param v1
     *            the v1
     * @param v2
     *            the v2
     * @return the vector3
     */
    public static final Vector3 cross(final Vector3 v1, final Vector3 v2) {
        Vector3 out = new Vector3();
        cross(v1, v2, out);
        return out;
    }
}
