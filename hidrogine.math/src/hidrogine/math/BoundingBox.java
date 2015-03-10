package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingBox {

    /** The max. */
    private float minX, minY, minZ;
    private float maxX, maxY, maxZ;

    /**
     * Contains.
     *
     * @param point
     *            the point
     * @return the containment type
     */
    public ContainmentType contains(final Vector3 point) {
        // first we get if point is out of box
        if (point.getX() < minX || point.getX() > maxX || point.getY() < minY
                || point.getY() > maxY || point.getZ() < minZ
                || point.getZ() > maxZ) {
            return ContainmentType.Disjoint;
        }// or if point is on box because coordonate of point is lesser or equal
        else if (point.getX() == minX || point.getX() == maxX
                || point.getY() == minY || point.getY() == maxY
                || point.getZ() == minZ || point.getZ() == maxZ)
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
        return maxX - minX;
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
        return maxY - minY;
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
        return maxZ - minZ;
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
        return (minX + maxX) * 0.5f;
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
        return (minY + maxY) * 0.5f;
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
        return (minZ + maxZ) * 0.5f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#toString()
     */
    public String toString() {
        return "{Min:" + "{" + minX + "," + minY + "," + minZ + "}, Max" + "{"
                + maxX + "," + maxY + "," + maxZ + "}}";
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
        if (box.maxX < minX || box.minX > maxX || box.maxY < minY
                || box.minY > maxY || box.maxZ < minZ || box.minZ > maxZ)
            return ContainmentType.Disjoint;

        if (box.minX >= minX && box.maxX <= maxX && box.minY >= minY
                && box.maxY <= maxY && box.minZ >= minZ && box.maxZ <= maxZ)
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
            px = maxX;
            nx = minX;
        } else {
            px = minX;
            nx = maxX;
        }
        if (plane.getNormal().getY() >= 0) {
            py = maxY;
            ny = minY;
        } else {
            py = minY;
            ny = maxY;
        }
        if (plane.getNormal().getZ() >= 0) {
            pz = maxZ;
            nz = minZ;
        } else {
            pz = minZ;
            nz = maxZ;
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

        if (sx - minX >= sr && sy - minY >= sr && sz - minZ >= sr
                && maxX - sx >= sr && maxY - sy >= sr && maxZ - sz >= sr)
            return ContainmentType.Contains;
        double dmin = 0;
        double e = sx - minX;
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sx - maxX;
            if (e > 0) {
                if (e > sr) {
                    return ContainmentType.Disjoint;
                }
                dmin += e * e;
            }
        }
        e = sy - minY;
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sy - maxY;
            if (e > 0) {
                if (e > sr) {
                    return ContainmentType.Disjoint;
                }
                dmin += e * e;
            }
        }
        e = sz - minZ;
        if (e < 0) {
            if (e < -sr) {
                return ContainmentType.Disjoint;
            }
            dmin += e * e;
        } else {
            e = sz - maxZ;
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

        return (sx - minX >= sr && sy - minY >= sr && sz - minZ >= sr
                && maxX - sx >= sr && maxY - sy >= sr && maxZ - sz >= sr);
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
        if ((maxX >= box.minX) && (minX <= box.maxX)) {
            return !((maxY < box.minY) || (minY > box.maxY))
                    && (maxZ >= box.minZ) && (minZ <= box.maxZ);
        }
        return false;
    }



    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMinZ() {
        return minZ;
    }

    public void setMinZ(float minZ) {
        this.minZ = minZ;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(float maxZ) {
        this.maxZ = maxZ;
    }

    /**
     * Instantiates a new box.
     *
     * @param min
     *            the min
     * @param max
     *            the max
     */
    public BoundingBox(float minX, float minY, float minZ, float maxX,
            float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

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

        return empty ? null : new BoundingBox(vector2.getX(), vector2.getY(),
                vector2.getZ(), vector1.getX(), vector1.getY(), vector1.getZ());
    }

    /**
     * Creates the from sphere.
     *
     * @param sphere
     *            the sphere
     * @return the bounding box
     */
    public BoundingBox createFromSphere(BoundingSphere sphere) {
        final Vector3 center = sphere.getCenter();

        final Vector3 vector1 = new Vector3(sphere.getRadius());

        return new BoundingBox(center.getX() - vector1.getX(), center.getY()
                - vector1.getY(), center.getZ() - vector1.getZ(), center.getX()
                + vector1.getX(), center.getY() + vector1.getY(), center.getZ()
                + vector1.getZ());
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

        return new BoundingBox(
                Math.min(original.minX, additional.minX),
                Math.min(original.minY, additional.minY), 
                Math.min(original.minZ, additional.minZ),
                Math.max(original.maxX, additional.maxX),
                Math.max(original.maxY, additional.maxY), 
                Math.max(original.maxZ, additional.maxZ));
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

}
