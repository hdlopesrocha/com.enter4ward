package hidrogine.math.api;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISphere.
 */
public abstract class ISphere {

    /**
     * Gets the radius.
     *
     * @return the radius
     */
    public abstract float getRadius();

    /**
     * Sets the radius.
     *
     * @param radius
     *            the new radius
     */
    public abstract void setRadius(float radius);

    /**
     * Sets the position.
     *
     * @param position
     *            the new position
     */
    public abstract void setPosition(IVector3 position);

    /**
     * Gets the position.
     *
     * @return the position
     */
    public abstract IVector3 getPosition();

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#contains(hidrogine.math.IVector3)
     */
    /**
     * Contains.
     *
     * @param vec
     *            the vec
     * @return true, if successful
     */
    public boolean contains(IVector3 vec) {
        return getPosition().distance(vec) <= getRadius();
    }

}
