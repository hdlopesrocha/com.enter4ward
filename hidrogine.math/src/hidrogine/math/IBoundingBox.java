package hidrogine.math;

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

    /**
     * Contains.
     *
     * @param point
     *            the point
     * @return the containment type
     */
    public ContainmentType contains(IVector3 point) {
        // first we get if point is out of box
        if (point.getX() < getMin().getX() || point.getX() > getMax().getX()
                || point.getY() < getMin().getY()
                || point.getY() > getMax().getY()
                || point.getZ() < getMin().getZ()
                || point.getZ() > getMax().getZ()) {
            return ContainmentType.Disjoint;
        }// or if point is on box because coordonate of point is lesser or equal
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

    /**
     * Contains.
     *
     * @param sphere
     *            the sphere
     * @return the containment type
     */
    public ContainmentType contains(BoundingSphere sphere) {
        if (sphere.getCenter().getX() - getMin().getX() > sphere.getRadius()
                && sphere.getCenter().getY() - getMin().getY() > sphere
                        .getRadius()
                && sphere.getCenter().getZ() - getMin().getZ() > sphere
                        .getRadius()
                && getMax().getX() - sphere.getCenter().getX() > sphere
                        .getRadius()
                && getMax().getY() - sphere.getCenter().getY() > sphere
                        .getRadius()
                && getMax().getZ() - sphere.getCenter().getZ() > sphere
                        .getRadius())
            return ContainmentType.Contains;

        double dmin = 0;

        if (sphere.getCenter().getX() - getMin().getX() <= sphere.getRadius())
            dmin += (sphere.getCenter().getX() - getMin().getX())
                    * (sphere.getCenter().getX() - getMin().getX());
        else if (getMax().getX() - sphere.getCenter().getX() <= sphere
                .getRadius())
            dmin += (sphere.getCenter().getX() - getMax().getX())
                    * (sphere.getCenter().getX() - getMin().getX());
        if (sphere.getCenter().getY() - getMin().getY() <= sphere.getRadius())
            dmin += (sphere.getCenter().getY() - getMin().getY())
                    * (sphere.getCenter().getY() - getMin().getY());
        else if (getMax().getY() - sphere.getCenter().getY() <= sphere
                .getRadius())
            dmin += (sphere.getCenter().getY() - getMax().getY())
                    * (sphere.getCenter().getY() - getMax().getY());
        if (sphere.getCenter().getZ() - getMin().getZ() <= sphere.getRadius())
            dmin += (sphere.getCenter().getZ() - getMin().getZ())
                    * (sphere.getCenter().getZ() - getMin().getZ());
        else if (getMax().getZ() - sphere.getCenter().getZ() <= sphere
                .getRadius())
            dmin += (sphere.getCenter().getZ() - getMax().getZ())
                    * (sphere.getCenter().getZ() - getMax().getZ());

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
        return getMin().getX() + getLengthX() / 2f;
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
        return getMin().getY() + getLengthY() / 2f;
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
        return getMin().getZ() + getLengthZ() / 2f;
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

 

    // MONOGAME
    /**
     * Intersects.
     *
     * @param plane
     *            the plane
     * @return the plane intersection type
     */
    public PlaneIntersectionType intersects(Plane plane) {
        // See
        // http://zach.in.tu-clausthal.de/teaching/cg_literatur/lighthouse3d_view_frustum_culling/index.html
        Vector3 positiveVertex = new Vector3();
        Vector3 negativeVertex = new Vector3();
        if (plane.getNormal().getX() >= 0) {
            positiveVertex.setX(getMax().getX());
            negativeVertex.setX(getMin().getX());
        } else {
            positiveVertex.setX(getMin().getX());
            negativeVertex.setX(getMax().getX());
        }
        if (plane.getNormal().getY() >= 0) {
            positiveVertex.setY(getMax().getY());
            negativeVertex.setY(getMin().getY());
        } else {
            positiveVertex.setY(getMin().getY());
            negativeVertex.setY(getMax().getY());
        }
        if (plane.getNormal().getZ() >= 0) {
            positiveVertex.setZ(getMax().getZ());
            negativeVertex.setZ(getMin().getZ());
        } else {
            positiveVertex.setZ(getMin().getZ());
            negativeVertex.setZ(getMax().getZ());
        }
        float distance = plane.getNormal().dot(negativeVertex)
                + plane.getDistance();
        if (distance > 0) {
            return PlaneIntersectionType.Front;

        }
        distance = plane.getNormal().dot(positiveVertex) + plane.getDistance();
        if (distance < 0) {
            return PlaneIntersectionType.Back;

        }
        return PlaneIntersectionType.Intersecting;
    }

    /**
     * Intersects.
     *
     * @param sphere
     *            the sphere
     * @return true, if successful
     */
    public boolean intersects(BoundingSphere sphere) {
        if (sphere.getCenter().getX() - getMin().getX() > sphere.getRadius()
                && sphere.getCenter().getY() - getMin().getY() > sphere
                        .getRadius()
                && sphere.getCenter().getZ() - getMin().getZ() > sphere
                        .getRadius()
                && getMax().getX() - sphere.getCenter().getX() > sphere
                        .getRadius()
                && getMax().getY() - sphere.getCenter().getY() > sphere
                        .getRadius()
                && getMax().getZ() - sphere.getCenter().getZ() > sphere
                        .getRadius())
            return true;

        double dmin = 0;

        if (sphere.getCenter().getX() - getMin().getX() <= sphere.getRadius())
            dmin += (sphere.getCenter().getX() - getMin().getX())
                    * (sphere.getCenter().getX() - getMin().getX());
        else if (getMax().getX() - sphere.getCenter().getX() <= sphere
                .getRadius())
            dmin += (sphere.getCenter().getX() - getMax().getX())
                    * (sphere.getCenter().getX() - getMax().getX());

        if (sphere.getCenter().getY() - getMin().getY() <= sphere.getRadius())
            dmin += (sphere.getCenter().getY() - getMin().getY())
                    * (sphere.getCenter().getY() - getMin().getY());
        else if (getMax().getY() - sphere.getCenter().getY() <= sphere
                .getRadius())
            dmin += (sphere.getCenter().getY() - getMax().getY())
                    * (sphere.getCenter().getY() - getMax().getY());

        if (sphere.getCenter().getZ() - getMin().getZ() <= sphere.getRadius())
            dmin += (sphere.getCenter().getZ() - getMin().getZ())
                    * (sphere.getCenter().getZ() - getMin().getZ());
        else if (getMax().getZ() - sphere.getCenter().getZ() <= sphere
                .getRadius())
            dmin += (sphere.getCenter().getZ() - getMax().getZ())
                    * (sphere.getCenter().getZ() - getMax().getZ());

        return dmin <= sphere.getRadius() * sphere.getRadius();

    }

    /**
     * Intersects.
     *
     * @param box
     *            the box
     * @return true, if successful
     */
    public boolean intersects(BoundingBox box) {
        if ((getMax().getX() >= box.getMin().getX())
                && (getMin().getX() <= box.getMax().getX())) {
            return !((getMax().getY() < box.getMin().getY()) || (getMin().getY() > box.getMax().getY())) && (getMax().getZ() >= box.getMin().getZ()) && (getMin().getZ() <= box.getMax().getZ());
        }
        return false;
    }

}
