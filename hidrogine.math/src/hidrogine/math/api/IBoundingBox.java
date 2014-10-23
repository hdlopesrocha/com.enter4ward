package hidrogine.math.api;

import hidrogine.math.BoundingSphere;
import hidrogine.math.ContainmentType;

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

    
    public ContainmentType contains(IVector3 point)
    {
        //first we get if point is out of box
        if (point.getX() < getMin().getX()
            || point.getX() > getMax().getX()
            || point.getY() < getMin().getY()
            || point.getY() > getMax().getY()
            || point.getZ() < getMin().getZ()
            || point.getZ() > getMax().getZ())
        {
            return ContainmentType.Disjoint;
        }//or if point is on box because coordonate of point is lesser or equal
        else if (point.getX() == getMin().getX()
            || point.getX() == getMax().getX()
            || point.getY() == getMin().getY()
            || point.getY() == getMax().getY()
            || point.getZ() == getMin().getZ()
            || point.getZ() == getMax().getZ())
            return ContainmentType.Intersects;
        else
            return ContainmentType.Contains;
    }
    
    
    public ContainmentType contains(BoundingSphere sphere)
    {
        if (sphere.getPosition().getX() - getMin().getX() > sphere.getRadius()
            && sphere.getPosition().getY() - getMin().getY() > sphere.getRadius()
            && sphere.getPosition().getZ() - getMin().getZ() > sphere.getRadius()
            && getMax().getX() - sphere.getPosition().getX() > sphere.getRadius()
            && getMax().getY() - sphere.getPosition().getY() > sphere.getRadius()
            && getMax().getZ() - sphere.getPosition().getZ() > sphere.getRadius())
            return ContainmentType.Contains;

        double dmin = 0;

        if (sphere.getPosition().getX() - getMin().getX() <= sphere.getRadius())
            dmin += (sphere.getPosition().getX() - getMin().getX()) * (sphere.getPosition().getX() - getMin().getX());
        else if (getMax().getX() - sphere.getPosition().getX() <= sphere.getRadius())
            dmin += (sphere.getPosition().getX() - getMax().getX()) * (sphere.getPosition().getX() - getMin().getX());
        if (sphere.getPosition().getY() - getMin().getY() <= sphere.getRadius())
            dmin += (sphere.getPosition().getY() - getMin().getY()) * (sphere.getPosition().getY() - getMin().getY());
        else if (getMax().getY() - sphere.getPosition().getY() <= sphere.getRadius())
            dmin += (sphere.getPosition().getY() - getMax().getY()) * (sphere.getPosition().getY() - getMax().getY());
        if (sphere.getPosition().getZ() - getMin().getZ() <= sphere.getRadius())
            dmin += (sphere.getPosition().getZ() - getMin().getZ()) * (sphere.getPosition().getZ() - getMin().getZ());
        else if (getMax().getZ() - sphere.getPosition().getZ() <= sphere.getRadius())
            dmin += (sphere.getPosition().getZ() - getMax().getZ()) * (sphere.getPosition().getZ() - getMax().getZ());

        if (dmin <= sphere.getRadius() * sphere.getRadius())
            return ContainmentType.Intersects;

        return ContainmentType.Disjoint;
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


}
