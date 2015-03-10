package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingSphere {

    /** The max. */
    private Vector3 center;

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
        this.center = position;
        this.radius = radius;
    }

    /**
     * Instantiates a new bounding sphere.
     *
     * @param sph
     *            the sph
     */
    public BoundingSphere(BoundingSphere sph) {
        this.center = new Vector3(sph.getCenter());
        this.radius = sph.getRadius();
    }

    /**
     * Instantiates a new bounding sphere.
     */
    public BoundingSphere() {
        this.center = new Vector3();
        this.radius = 0f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#getCenter()
     */
    /**
     * Gets the center.
     *
     * @return the center
     */
    public Vector3 getCenter() {
        return center;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#setCenter(hidrogine.math.Vector3)
     */
    /**
     * Sets the center.
     *
     * @param position
     *            the new center
     */
    public void setCenter(Vector3 position) {
        this.center = position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#getRadius()
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
     * @see hidrogine.math.ISphere#setRadius(float)
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
     * Contains.
     *
     * @param vec
     *            the vec
     * @return true, if successful
     */
    public boolean contains(Vector3 vec) {
        return getCenter().distanceSquared(vec) <= getRadius() * getRadius();
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
        Vector3 center = getCenter();
        center.setX((minX + maxX) / 2f);
        center.setY((minY + maxY) / 2f);
        center.setZ((minZ + maxZ) / 2f);

        setRadius((float) (Math
                .sqrt((maxX - minX) * (maxX - minX) + (maxY - minY)
                        * (maxY - minY) + (maxZ - minZ) * (maxZ - minZ)) / 2d));
        return this;
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
        return getCenter().distance(sphere.getCenter()) < getRadius()
                + sphere.getRadius();
    }

}
