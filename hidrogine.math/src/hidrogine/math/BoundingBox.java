package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingBox  {

    /** The max. */
    private Vector3 min, max;


    
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
                || point.getY() < min.getY()
                || point.getY() > max.getY()
                || point.getZ() < min.getZ()
                || point.getZ() > max.getZ()) {
            return ContainmentType.Disjoint;
        }// or if point is on box because coordonate of point is lesser or equal
        else if (point.getX() == min.getX()
                || point.getX() == max.getX()
                || point.getY() == min.getY()
                || point.getY() == max.getY()
                || point.getZ() == min.getZ()
                || point.getZ() == max.getZ())
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
        return max.getX() - min.getX();
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
        return max.getY() - min.getY();
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
        return max.getZ() - min.getZ();
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
        return (min.getX() + max.getX()) * 0.5f;
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
        return (min.getY() + max.getY()) * 0.5f;
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
        return (min.getZ() + max.getZ()) * 0.5f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#toString()
     */
    public String toString() {
        return "{Min:" + min.toString() + " ,Max" + max.toString()
                + "}";
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
        float px,py,pz, nx,ny,nz;
        if (plane.getNormal().getX() >= 0) {
            px = max.getX();
            nx = min.getX();
        } else {
            px = min.getX();
            nx = max.getX();
        }
        if (plane.getNormal().getY() >= 0) {
            py = max.getY();
            ny = min.getY();
        } else {
            py = min.getY();
            ny = max.getY();
        }
        if (plane.getNormal().getZ() >= 0) {
            pz = max.getZ();
            nz = min.getZ();
        } else {
            pz = min.getZ();
            nz = max.getZ();
        }
        Vector3 nrm = plane.getNormal();
        float normal_dot_negative = nrm.getX()*nx+nrm.getY()*ny+nrm.getZ()*nz;
        
        float distance = normal_dot_negative + plane.getDistance();
        if (distance > 0) {
            return PlaneIntersectionType.Front;

        }
        
        float normal_dot_positive = nrm.getX()*px+nrm.getY()*py+nrm.getZ()*pz;

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

        
        if (sx - min.getX() >= sr && sy - min.getY() >= sr
                && sz - min.getZ() >= sr && max.getX() - sx >= sr
                && max.getY() - sy >= sr && max.getZ() - sz >= sr)
            return ContainmentType.Contains;
        double dmin = 0;
        double e = sx - min.getX();
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sx - max.getX();
            if (e > 0) {
                if (e > sr) {
                    return ContainmentType.Disjoint;
                }
                dmin += e * e;
            }
        }
        e = sy - min.getY();
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sy - max.getY();
            if (e > 0) {
                if (e > sr) {
                    return ContainmentType.Disjoint;
                }
                dmin += e * e;
            }
        }
        e = sz - min.getZ();
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sz - max.getZ();
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
        Vector3[] corners = frustum.getCorners();
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
     * Intersects.
     *
     * @param box
     *            the box
     * @return true, if successful
     */
    public boolean intersects(BoundingBox box) {
        if ((max.getX() >= box.min.getX())
                && (min.getX() <= box.max.getX())) {
            return !((max.getY() < box.min.getY()) || (min.getY() > box.max.getY())) && (max.getZ() >= box.min.getZ()) && (min.getZ() <= box.max.getZ());
        }
        return false;
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#min
     */
    public Vector3 getMin() {
        return min;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#setMin(hidrogine.math.Vector3)
     */
    public void setMin(Vector3 min) {
        this.min = min;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#max
     */
    public Vector3 getMax() {
        return max;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#setMax(hidrogine.math.Vector3)
     */
    public void setMax(Vector3 max) {
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
    public BoundingBox(Vector3 min, Vector3 max) {
        this.min = min;
        this.max = max;
    }

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
        Vector3 center = sphere.getCenter();
        
        Vector3 vector1 = new Vector3(sphere.getRadius());
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



}
