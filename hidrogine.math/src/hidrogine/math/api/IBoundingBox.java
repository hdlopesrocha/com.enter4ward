package hidrogine.math.api;

// TODO: Auto-generated Javadoc
/**
 * The Class IBox.
 */
public abstract class IBoundingBox {

    /**
     * Gets the getMin().
     *
     * @return the getMin()
     */
    public abstract IVector3 getMin();

    /**
     * Sets the getMin().
     *
     * @param min
     *            the new min
     */
    public abstract void setMin(IVector3 min);

    /**
     * Gets the getMax().
     *
     * @return the getMax()
     */
    public abstract IVector3 getMax();

    /**
     * Sets the getMax().
     *
     * @param max
     *            the new max
     */
    public abstract void setMax(IVector3 max);

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#contains(hidrogine.math.IVector3)
     */
    /**
     * Contains.
     *
     * @param vec
     *            the vec
     * @return true, if successful
     */
    public boolean contains(IVector3 vec) {
        return vec.getX() >= getMin().getX() && vec.getX() <= getMax().getX()
                && vec.getY() >= getMin().getY()
                && vec.getY() <= getMax().getY()
                && vec.getZ() >= getMin().getZ()
                && vec.getZ() <= getMax().getZ();
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getLengthX()
     */
    /**
     * Gets the length x.
     *
     * @return the length x
     */
    public float getLengthX() {
        return getMax().getX() - getMin().getX();
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getLengthY()
     */
    /**
     * Gets the length y.
     *
     * @return the length y
     */
    public float getLengthY() {
        return getMax().getY() - getMin().getY();
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getLengthZ()
     */
    /**
     * Gets the length z.
     *
     * @return the length z
     */
    public float getLengthZ() {
        return getMax().getZ() - getMin().getZ();
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getCenterX()
     */
    /**
     * Gets the center x.
     *
     * @return the center x
     */
    public float getCenterX() {
        return (getMin().getX() + getLengthX()) / 2f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getCenterY()
     */
    /**
     * Gets the center y.
     *
     * @return the center y
     */
    public float getCenterY() {
        return (getMin().getY() + getLengthY()) / 2f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getCenterZ()
     */
    /**
     * Gets the center z.
     *
     * @return the center z
     */
    public float getCenterZ() {
        return (getMin().getZ() + getLengthZ()) / 2f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#toString()
     */
    public String toString() {
        return "{Min:" + getMin().toString() + " ,Max" + getMax().toString()
                + "}";
    }

    /**
     * Contains.
     *
     * @param obj
     *            the obj
     * @return true, if successful
     */
    public boolean contains(IBoundingSphere obj) {
        return (obj.getPosition().getX() - getMin().getX() > obj.getRadius()
                && obj.getPosition().getY() - getMin().getY() > obj.getRadius()
                && obj.getPosition().getZ() - getMin().getZ() > obj.getRadius()
                && getMax().getX() - obj.getPosition().getX() > obj.getRadius()
                && getMax().getY() - obj.getPosition().getY() > obj.getRadius() && getMax()
                .getZ() - obj.getPosition().getZ() > obj.getRadius());

    }

}
