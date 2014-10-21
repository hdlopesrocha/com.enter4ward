package hidrogine.math.api;

// TODO: Auto-generated Javadoc
/**
 * The Class IVector2.
 */
public abstract class IVector2 {

    /**
     * Gets the x.
     *
     * @return the x
     */
    public abstract float getX();

    /**
     * Sets the x.
     *
     * @param xx
     *            the new x
     */
    public abstract void setX(float xx);

    /**
     * Gets the y.
     *
     * @return the y
     */
    public abstract float getY();

    /**
     * Sets the y.
     *
     * @param yy
     *            the new y
     */
    public abstract void setY(float yy);

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector2#add(hidrogine.math.IVector2)
     */
    /**
     * Adds the.
     *
     * @param vec
     *            the vec
     * @return the i vector2
     */
    public final IVector2 add(final IVector2 vec) {
        setX(getX() + vec.getX());
        setY(getY() + vec.getY());
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
    public final IVector2 subtract(final IVector2 vec) {
        setX(getX() - vec.getX());
        setY(getY() - vec.getY());
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
    public final IVector2 multiply(final IVector2 vec) {
        setX(getX() * vec.getX());
        setY(getY() * vec.getY());
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
    public final IVector2 divide(final IVector2 vec) {
        setX(getX() / vec.getX());
        setY(getY() / vec.getY());
        return this;
    }

}
