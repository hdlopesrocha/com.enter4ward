package hidrogine.math;

import hidrogine.math.api.IVector2;

// TODO: Auto-generated Javadoc
/**
 * The Class Vector2.
 */
public class Vector2 extends IVector2 {

    /** The x. */
    private float x;

    /** The y. */
    private float y;

    /**
     * Instantiates a new vector2.
     */
    public Vector2() {
        x = 0;
        y = 0;
    }

    /**
     * Instantiates a new vector2.
     *
     * @param xx
     *            the x
     * @param yy
     *            the y
     */
    public Vector2(final float xx, final float yy) {
        x = xx;
        y = yy;
    }

    /**
     * Instantiates a new vector2.
     *
     * @param vec
     *            the vec
     */
    public Vector2(final IVector2 vec) {
        x = vec.getX();
        y = vec.getY();
    }

    /* (non-Javadoc)
     * @see hidrogine.math.IVector2#getX()
     */
    @Override
    public final float getX() {
        return x;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.IVector2#setX(float)
     */
    @Override
    public final void setX(final float xx) {
        x = xx;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.IVector2#getY()
     */
    @Override
    public final float getY() {
        return y;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.IVector2#setY(float)
     */
    @Override
    public final void setY(final float yy) {
        y = yy;
    }



}
