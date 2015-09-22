package com.enter4ward.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingSphere extends Vector3 {

    /** The radius. */
    private float radius;

    /**
     * Instantiates a new sphere.
     *
     * @param position
     *            the position
     * @param radius
     *            the radius
     */
    public BoundingSphere(Vector3 position, float radius) {
        set(position);
        this.radius = radius;
    }

    /**
     * Instantiates a new bounding sphere.
     *
     * @param sph
     *            the sph
     */
    public BoundingSphere(BoundingSphere sph) {
        set(sph);
        this.radius = sph.getRadius();
    }

    /**
     * Instantiates a new bounding sphere.
     */
    public BoundingSphere() {
        this.radius = 0f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enter4ward.math.ISphere#getRadius()
     */
    /**
     * Gets the radius.
     *
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.enter4ward.math.ISphere#setRadius(float)
     */
    /**
     * Sets the radius.
     *
     * @param radius
     *            the new radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * Sets the radius.
     *
     * @param radius
     *            the new radius
     * @return
     */
    public BoundingSphere set(final BoundingSphere sph) {
        this.radius = sph.getRadius();
        this.setX(sph.getX());
        this.setY(sph.getY());
        this.setZ(sph.getZ());
        return this;
    }

    /**
     * Contains.
     *
     * @param vec
     *            the vec
     * @return true, if successful
     */
    public boolean contains(Vector3 vec) {
        return distanceSquared(vec) <= getRadius() * getRadius();
    }

    /**
     * Intersects.
     *
     * @param plane
     *            the plane
     * @return the plane intersection type
     */
    public PlaneIntersectionType intersects(Plane plane) {
        // TODO Auto-generated method stub
        return plane.intersects(this);
    }

    /**
     * Intersects.
     *
     * @param sphere
     *            the sphere
     * @return the boolean
     */
    public Boolean intersects(BoundingSphere sphere) {
        return distance(sphere) < getRadius() + sphere.getRadius();
    }

    /**
     * Creates the from points.
     *
     * @param points
     *            the points
     * @return the sphere
     */
    public BoundingSphere createFromPoints(Iterable<Vector3> points) {
        float maxX = 0, maxY = 0, maxZ = 0, minX = 0, minY = 0, minZ = 0;

        boolean inited = false;

        for (Vector3 vec : points) {
            if (!inited) {
                minX = maxX = vec.getX();
                minY = maxY = vec.getY();
                minZ = maxZ = vec.getZ();

                inited = true;
            }
            minX = Math.min(minX, vec.getX());
            minY = Math.min(minY, vec.getY());
            minZ = Math.min(minZ, vec.getZ());
            maxX = Math.max(maxX, vec.getX());
            maxY = Math.max(maxY, vec.getY());
            maxZ = Math.max(maxZ, vec.getZ());
        }
        setX((minX + maxX) / 2f);
        setY((minY + maxY) / 2f);
        setZ((minZ + maxZ) / 2f);

        setRadius(0f);

        for (Vector3 vec : points) {
            float dist = (float) vec.distance(this);
            setRadius(Math.max(getRadius(), dist));
        }

        return this;
    }
}
