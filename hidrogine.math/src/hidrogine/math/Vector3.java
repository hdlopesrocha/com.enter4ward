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
}
