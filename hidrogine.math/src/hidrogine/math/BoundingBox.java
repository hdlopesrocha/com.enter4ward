package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingBox {

    /** The max. */
    private Vector3 min;
    private Vector3 len;

    /**
     * Contains.
     *
     * @param point
     *            the point
     * @return the containment type
     */
    public ContainmentType contains(final Vector3 point) {
        // first we get if point is out of box
        if (point.getX() < getMin().getX() || point.getX() > getMaxX() || point.getY() < getMin().getY()
                || point.getY() > getMaxY() || point.getZ() < getMin().getZ()
                || point.getZ() > getMaxZ()) {
            return ContainmentType.Disjoint;
        }// or if point is on box because coordonate of point is lesser or equal
        else if (point.getX() == getMin().getX() || point.getX() == getMaxX()
                || point.getY() == getMin().getY() || point.getY() == getMaxY()
                || point.getZ() == getMin().getZ() || point.getZ() == getMaxZ())
            return ContainmentType.Intersects;
        else
            return ContainmentType.Contains;
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
        return len.getX();
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
        return len.getY();
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
        return len.getZ();
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
        return getMin().getX() + len.getX() * 0.5f;
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
        return getMin().getY() + len.getY() * 0.5f;
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
        return getMin().getZ() + len.getZ() * 0.5f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#toString()
     */
    public String toString() {
        return "{Min:" + "{" + getMin().getX() + "," + getMin().getY() + "," + getMin().getZ() + "}, Len:" + "{"
                + len.toString() +"}";
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
        if (box.getMaxX() < getMin().getX() || box.getMin().getX() > getMaxX() || box.getMaxY() < getMin().getY()
                || box.getMin().getY() > getMaxY() || box.getMaxZ() < getMin().getZ() || box.getMin().getZ() > getMaxZ())
            return ContainmentType.Disjoint;

        if (box.getMin().getX() >= getMin().getX() && box.getMaxX() <= getMaxX() && box.getMin().getY() >= getMin().getY()
                && box.getMaxY() <= getMaxY() && box.getMin().getZ() >= getMin().getZ() && box.getMaxZ() <= getMaxZ())
            return ContainmentType.Contains;

        return ContainmentType.Intersects;
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
        float px, py, pz, nx, ny, nz;
        if (plane.getNormal().getX() >= 0) {
            px = getMaxX();
            nx = getMin().getX();
        } else {
            px = getMin().getX();
            nx = getMaxX();
        }
        if (plane.getNormal().getY() >= 0) {
            py = getMaxY();
            ny = getMin().getY();
        } else {
            py = getMin().getY();
            ny = getMaxY();
        }
        if (plane.getNormal().getZ() >= 0) {
            pz = getMaxZ();
            nz = getMin().getZ();
        } else {
            pz = getMin().getZ();
            nz = getMaxZ();
        }
        Vector3 nrm = plane.getNormal();
        float normal_dot_negative = nrm.getX() * nx + nrm.getY() * ny
                + nrm.getZ() * nz;

        float distance = normal_dot_negative + plane.getDistance();
        if (distance > 0) {
            return PlaneIntersectionType.Front;

        }

        float normal_dot_positive = nrm.getX() * px + nrm.getY() * py
                + nrm.getZ() * pz;

        distance = normal_dot_positive + plane.getDistance();
        if (distance < 0) {
            return PlaneIntersectionType.Back;

        }
        return PlaneIntersectionType.Intersecting;
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
    public ContainmentType contains(final BoundingSphere sphere) {
        final Vector3 center = sphere.getCenter();
        final float sx = center.getX();
        final float sy = center.getY();
        final float sz = center.getZ();
        final float sr = sphere.getRadius();

        if (sx - getMin().getX() >= sr && sy - getMin().getY() >= sr && sz - getMin().getZ() >= sr
                && getMaxX() - sx >= sr && getMaxY() - sy >= sr && getMaxZ() - sz >= sr)
            return ContainmentType.Contains;
        double dmin = 0;
        double e = sx - getMin().getX();
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sx - getMaxX();
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
            e = sy - getMaxY();
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
            e = sz - getMaxZ();
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
     * Only contains.
     *
     * @param sphere
     *            the sphere
     * @return true, if successful
     */
    public boolean onlyContains(final BoundingSphere sphere) {
        final Vector3 center = sphere.getCenter();
        final float sx = center.getX();
        final float sy = center.getY();
        final float sz = center.getZ();
        final float sr = sphere.getRadius();

        return (sx - getMin().getX() >= sr && sy - getMin().getY() >= sr && sz - getMin().getZ() >= sr
                && getMaxX() - sx >= sr && getMaxY() - sy >= sr && getMaxZ() - sz >= sr);
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
        // TODO: bad done here need a fix.
        // Because question is not frustum contain box but reverse and this is
        // not the same
        int i;
        Vector3[] corners = frustum.getCorners();
        // First we check if frustum is in box
        for (i = 0; i < corners.length; i++) {
            if (contains(corners[i]) == ContainmentType.Disjoint)
                break;
        }
        if (i == corners.length) // This means we checked all the corners and
                                 // they were all contain or instersect
            return ContainmentType.Contains;
        if (i != 0) // if i is not equal to zero, we can fastpath and say that
                    // this box intersects
            return ContainmentType.Intersects;
        // If we get here, it means the first (and only) point we checked was
        // actually contained in the frustum.
        // So we assume that all other points will also be contained. If one of
        // the points is disjoint, we can
        // exit immediately saying that the result is Intersects
        i++;
        for (; i < corners.length; i++) {
            if (contains(corners[i]) != ContainmentType.Contains)
                return ContainmentType.Intersects;
        }
        // If we get here, then we know all the points were actually contained,
        // therefore result is Contains
        return ContainmentType.Contains;
    }

    /**
     * Intersects.
     *
     * @param box
     *            the box
     * @return true, if successful
     */
    public boolean intersects(BoundingBox box) {
        if ((getMaxX() >= box.getMin().getX()) && (getMin().getX() <= box.getMaxX())) {
            return !((getMaxY() < box.getMin().getY()) || (getMin().getY() > box.getMaxY()))
                    && (getMaxZ() >= box.getMin().getZ()) && (getMin().getZ() <= box.getMaxZ());
        }
        return false;
    }



    public float getMinX() {
        return getMin().getX();
    }

    public void setMinX(float min) {
        this.getMin().setX(min);
    }

    public float getMinY() {
        return getMin().getY();
    }

    public void setMinY(float min) {
        this.getMin().setY(min);
    }

    public float getMinZ() {
        return getMin().getZ();
    }

    public void setMinZ(float min) {
        this.getMin().setZ(min);
    }

    public float getMaxX() {
        return getMin().getX()+len.getX();
    }

    public float getMaxY() {
        return getMin().getY() + len.getY();
    }

    public float getMaxZ() {
        return getMin().getZ()+len.getZ();
    }

    /**
     * Instantiates a new box.
     *
     * @param min
     *            the min
     * @param max
     *            the max
     */
    public BoundingBox(Vector3 min, Vector3 len) {
        this.min = min;
        this.len  =len;
    }

    /**
     * Instantiates a new bounding box.
     */
    public BoundingBox() {
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
        final Vector3 vector2 = new Vector3(Float.MAX_VALUE);
        final Vector3 vector1 = new Vector3(Float.MIN_VALUE);
        
        
        for (Vector3 vector3 : points) {
            vector2.min(vector3);
            vector1.max(vector3);
            empty = false;
        }
        
        final Vector3 len = vector2.subtract(vector1);
        

        return empty ? null : new BoundingBox(vector2,len);
    }

    /**
     * Creates the from sphere.
     *
     * @param sphere
     *            the sphere
     * @return the bounding box
     */
    public BoundingBox createFromSphere(BoundingSphere sphere) {
/*        final Vector3 center = sphere.getCenter();

        final Vector3 vector1 = new Vector3(sphere.getRadius());

        return new BoundingBox(center.getX() - vector1.getX(), center.getY()
                - vector1.getY(), center.getZ() - vector1.getZ(),
                new Vector3(vector1).multiply(0.5f)
                );
  */
    return null;    
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
/*
        return new BoundingBox(
                Math.min(original.min.getX(), additional.min.getX()),
                Math.min(original.min.getY(), additional.min.getY()), 
                Math.min(original.min.getZ(), additional.min.getZ()),
                Math.max(original.getMaxX(), additional.getMaxX()),
                Math.max(original.getMaxY(), additional.getMaxY()), 
                Math.max(original.getMaxZ(), additional.getMaxZ()));
  */
    return null;    
    }

    /**
     * Equals.
     *
     * @param other
     *            the other
     * @return true, if successful
     */
    public boolean equals(BoundingBox other) {
        return this.equals(other);
        // return (min.equals(other.min)) && (max.equals(other.max));
    }

    public Vector3 getMin() {
        return min;
    }

    public void setMin(Vector3 min) {
        this.min = min;
    }

}
