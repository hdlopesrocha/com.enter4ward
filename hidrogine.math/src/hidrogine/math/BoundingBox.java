package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingBox extends IBoundingBox {

    /** The max. */
    private IVector3 min, max;

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getMin()
     */
    public IVector3 getMin() {
        return min;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#setMin(hidrogine.math.IVector3)
     */
    public void setMin(IVector3 min) {
        this.min = min;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getMax()
     */
    public IVector3 getMax() {
        return max;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#setMax(hidrogine.math.IVector3)
     */
    public void setMax(IVector3 max) {
        this.max = max;
    }

    /**
     * Instantiates a new box.
     *
     * @param min
     *            the min
     * @param max
     *            the max
     */
    public BoundingBox(IVector3 min, IVector3 max) {
        this.min = min;
        this.max = max;
    }


    /**
     * Contains.
     *
     * @param box
     *            the box
     * @return the containment type
     */
    public ContainmentType contains(BoundingBox box) {
        // test if all corner is in the same side of a face by just checking min
        // and max
        if (box.max.getX() < min.getX() || box.min.getX() > max.getX()
                || box.max.getY() < min.getY() || box.min.getY() > max.getY()
                || box.max.getZ() < min.getZ() || box.min.getZ() > max.getZ())
            return ContainmentType.Disjoint;

        if (box.min.getX() >= min.getX() && box.max.getX() <= max.getX()
                && box.min.getY() >= min.getY() && box.max.getY() <= max.getY()
                && box.min.getZ() >= min.getZ() && box.max.getZ() <= max.getZ())
            return ContainmentType.Contains;

        return ContainmentType.Intersects;
    }

    
    /**
     * Contains.
     *
     * @author MonoGame
     * 
     * @param frustum
     *            the frustum
     * @return the containment type
     */
    public ContainmentType contains(BoundingFrustum frustum) {
        //TODO: bad done here need a fix.
        //Because question is not frustum contain box but reverse and this is not the same
        int i;
        IVector3[] corners = frustum.getCorners();
        // First we check if frustum is in box
        for (i = 0; i < corners.length; i++) {
            if (contains(corners[i]) == ContainmentType.Disjoint)
                break;
        }
        if (i == corners.length) // This means we checked all the corners and they were all contain or instersect
            return ContainmentType.Contains;
        if (i != 0) // if i is not equal to zero, we can fastpath and say that this box intersects
            return ContainmentType.Intersects;
        // If we get here, it means the first (and only) point we checked was actually contained in the frustum.
        // So we assume that all other points will also be contained. If one of the points is disjoint, we can
        // exit immediately saying that the result is Intersects
        i++;
        for (; i < corners.length; i++) {
            if (contains( corners[i]) != ContainmentType.Contains)
                return ContainmentType.Intersects;
        }
        // If we get here, then we know all the points were actually contained, therefore result is Contains
        return ContainmentType.Contains;
    }

    /**
     * Contains.
     *
     * @author MonoGame
     * 
     * @param sphere
     *            the sphere
     * @return the containment type
     */
    public ContainmentType contains(IBoundingSphere sphere) {
        IVector3 center = sphere.getCenter();
        float sx = center.getX();
        float sy = center.getY();
        float sz = center.getZ();
        float sr = sphere.getRadius();

        if (sx - getMin().getX() >= sr && sy - getMin().getY() >= sr
                && sz - getMin().getZ() >= sr && getMax().getX() - sx >= sr
                && getMax().getY() - sy >= sr && getMax().getZ() - sz >= sr)
            return ContainmentType.Contains;
        double dmin = 0;
        double e = sx - getMin().getX();
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sx - getMax().getX();
            if (e > 0) {
                if (e > sr) {
                    return ContainmentType.Disjoint;
                }
                dmin += e * e;
            }
        }
        e = sy - getMin().getY();
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sy - getMax().getY();
            if (e > 0) {
                if (e > sr) {
                    return ContainmentType.Disjoint;
                }
                dmin += e * e;
            }
        }
        e = sz - getMin().getZ();
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sz - getMax().getZ();
            if (e > 0) {
                if (e > sr) {
                    return ContainmentType.Disjoint;
                }
                dmin += e * e;
            }
        }
        if (dmin <= sr * sr)
            return ContainmentType.Intersects;
        return ContainmentType.Disjoint;
    }

    /**
     * Contains.
     *
     * @param sphere
     *            the sphere
     * @return the containment type
     */
    public ContainmentType containsOld(IBoundingSphere sphere) {
        IVector3 center = sphere.getCenter();
        float sx = center.getX();
        float sy = center.getY();
        float sz = center.getZ();
        float sr = sphere.getRadius();

        if (sx - min.getX() > sr && sy - min.getY() > sr
                && sz - min.getZ() > sr && max.getX() - sx > sr
                && max.getY() - sy > sr && max.getZ() - sz > sr)
            return ContainmentType.Contains;

        double dmin = 0;

        if (sx - min.getX() <= sr)
            dmin += (sx - min.getX()) * (sx - min.getX());
        else if (max.getX() - sx <= sr)
            dmin += (sx - max.getX()) * (sx - max.getX());
        if (sy - min.getY() <= sr)
            dmin += (sy - min.getY()) * (sy - min.getY());
        else if (max.getY() - sy <= sr)
            dmin += (sy - max.getY()) * (sy - max.getY());
        if (sz - min.getZ() <= sr)
            dmin += (sz - min.getZ()) * (sz - min.getZ());
        else if (max.getZ() - sz <= sr)
            dmin += (sz - max.getZ()) * (sz - max.getZ());

        if (dmin <= sr * sr)
            return ContainmentType.Intersects;

        return ContainmentType.Disjoint;
    }

    /**
     * Contains.
     *
     * @param point
     *            the point
     * @return the containment type
     */
    public ContainmentType contains(Vector3 point) {
        // first we get if point is out of box
        if (point.getX() < min.getX() || point.getX() > max.getX()
                || point.getY() < min.getY() || point.getY() > max.getY()
                || point.getZ() < min.getZ() || point.getZ() > max.getZ()) {
            return ContainmentType.Disjoint;
        }// or if point is on box because coordinate of point is lesser or equal
        else if (point.getX() == min.getX() || point.getX() == max.getX()
                || point.getY() == min.getY() || point.getY() == max.getY()
                || point.getZ() == min.getZ() || point.getZ() == max.getZ())
            return ContainmentType.Intersects;
        else
            return ContainmentType.Contains;
    }

    /**
     * Creates the from points.
     *
     * @param points
     *            the points
     * @return the bounding box
     */
    public BoundingBox createFromPoints(Vector3[] points) {
        // TODO: Just check that Count > 0
        boolean empty = true;
        Vector3 vector2 = new Vector3(Float.MAX_VALUE);
        Vector3 vector1 = new Vector3(Float.MIN_VALUE);

        for (Vector3 vector3 : points) {
            vector2.min(vector3);
            vector1.max(vector3);
            empty = false;
        }

        return empty ? null : new BoundingBox(vector2, vector1);
    }

    /**
     * Creates the from sphere.
     *
     * @param sphere
     *            the sphere
     * @return the bounding box
     */
    public BoundingBox createFromSphere(BoundingSphere sphere) {
        IVector3 center = sphere.getCenter();
        
        IVector3 vector1 = new Vector3(sphere.getRadius());
        return new BoundingBox(
                new Vector3(center).subtract(vector1), new Vector3(center).add(vector1));
    }

    /**
     * Creates the merged.
     *
     * @param original
     *            the original
     * @param additional
     *            the additional
     * @return the bounding box
     */
    public BoundingBox createMerged(BoundingBox original, BoundingBox additional) {
        return new BoundingBox(new Vector3(original.min).min(additional.min),
                new Vector3(original.max).max(additional.max));
    }

    /**
     * Equals.
     *
     * @param other
     *            the other
     * @return true, if successful
     */
    public boolean equals(BoundingBox other) {
        return (min.equals(other.min)) && (max.equals(other.max));
    }

    /**
     * Intersects.
     *
     * @param box
     *            the box
     * @return true, if successful
     */
    public boolean intersects(BoundingBox box) {
        if ((max.getX() >= box.min.getX()) && (min.getX() <= box.max.getX())) {
            return !((max.getY() < box.min.getY()) || (min.getY() > box.max
                    .getY()))
                    && (max.getZ() >= box.min.getZ())
                    && (min.getZ() <= box.max.getZ());
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.api.IBoundingBox#toString()
     */
    public String toString() {
        return "{{Min:" + min.toString() + " Max:" + max.toString() + "}}";
    }

}
