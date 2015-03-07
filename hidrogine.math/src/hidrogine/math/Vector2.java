package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Vector2.
 */
public class Vector2  {

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
    public Vector2(final Vector2 vec) {
        x = vec.x;
        y = vec.y;
    }


    public final float getX() {
        return x;
    }


    public final void setX(final float xx) {
        x = xx;
    }


    public final float getY() {
        return y;
    }


    public final void setY(final float yy) {
        y = yy;
    }



    public final Vector2 add(final Vector2 vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector2#subtract(hidrogine.math.IVector2)
     */
    /**
     * Subtract.
     *
     * @param vec
     *            the vec
     * @return the i vector2
     */
    public final Vector2 subtract(final Vector2 vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector2#multiply(hidrogine.math.IVector2)
     */
    /**
     * Multiply.
     *
     * @param vec
     *            the vec
     * @return the i vector2
     */
    public final Vector2 multiply(final Vector2 vec) {
        x = vec.x;
        y = vec.y;
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector2#divide(hidrogine.math.IVector2)
     */
    /**
     * Divide.
     *
     * @param vec
     *            the vec
     * @return the i vector2
     */
    public final Vector2 divide(final Vector2 vec) {
        x /= vec.x;
        y /= vec.y;
        return this;
    }

    
}
