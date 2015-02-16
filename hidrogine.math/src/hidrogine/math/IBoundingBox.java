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
        return (getMin().getX() + getMax().getX()) * 0.5f;
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
        return (getMin().getY() + getMax().getY()) * 0.5f;
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
        return (getMin().getZ() + getMax().getZ()) * 0.5f;
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
     * @param box
     *            the box
     * @return the containment type
     */
    public ContainmentType contains(IBoundingBox box) {
        // test if all corner is in the same side of a face by just checking min
        // and max
        if (box.getMax().getX() < getMin().getX() || box.getMin().getX() > getMax().getX()
                || box.getMax().getY() < getMin().getY() || box.getMin().getY() > getMax().getY()
                || box.getMax().getZ() < getMin().getZ() || box.getMin().getZ() > getMax().getZ())
            return ContainmentType.Disjoint;

        if (box.getMin().getX() >= getMin().getX() && box.getMax().getX() <= getMax().getX()
                && box.getMin().getY() >= getMin().getY() && box.getMax().getY() <= getMax().getY()
                && box.getMin().getZ() >= getMin().getZ() && box.getMax().getZ() <= getMax().getZ())
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
            px = getMax().getX();
            nx = getMin().getX();
        } else {
            px = getMin().getX();
            nx = getMax().getX();
        }
        if (plane.getNormal().getY() >= 0) {
            py = getMax().getY();
            ny = getMin().getY();
        } else {
            py = getMin().getY();
            ny = getMax().getY();
        }
        if (plane.getNormal().getZ() >= 0) {
            pz = getMax().getZ();
            nz = getMin().getZ();
        } else {
            pz = getMin().getZ();
            nz = getMax().getZ();
        }
        IVector3 nrm = plane.getNormal();
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
     * Intersects.
     *
     * @param box
     *            the box
     * @return true, if successful
     */
    public boolean intersects(IBoundingBox box) {
        if ((getMax().getX() >= box.getMin().getX())
                && (getMin().getX() <= box.getMax().getX())) {
            return !((getMax().getY() < box.getMin().getY()) || (getMin().getY() > box.getMax().getY())) && (getMax().getZ() >= box.getMin().getZ()) && (getMin().getZ() <= box.getMax().getZ());
        }
        return false;
    }

}
