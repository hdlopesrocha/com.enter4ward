package hidrogine.math.api;

import hidrogine.math.Plane;
import hidrogine.math.PlaneIntersectionType;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISphere.
 */
public abstract class IBoundingSphere {

    /**
     * Gets the radius.
     *
     * @return the radius
     */
    public abstract float getRadius();

    /**
     * Sets the radius.
     *
     * @param radius
     *            the new radius
     */
    public abstract void setRadius(float radius);

    /**
     * Sets the position.
     *
     * @param position
     *            the new position
     */
    public abstract void setPosition(IVector3 position);

    /**
     * Gets the position.
     *
     * @return the position
     */
    public abstract IVector3 getPosition();

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#contains(hidrogine.math.IVector3)
     */
    /**
     * Contains.
     *
     * @param vec
     *            the vec
     * @return true, if successful
     */
    public boolean contains(IVector3 vec) {
        return getPosition().distance(vec) <= getRadius();
    }

    /**
     * Creates the from points.
     *
     * @param points
     *            the points
     * @return the sphere
     */
    public IBoundingSphere createFromPoints(Iterable<IVector3> points) {
        float maxX = 0, maxY = 0, maxZ = 0, minX = 0, minY = 0, minZ = 0;

        boolean inited = false;

        for (IVector3 vec : points) {
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
        getPosition().setX((minX + maxX) / 2f);
        getPosition().setY((minY + maxY) / 2f);
        getPosition().setZ((minZ + maxZ) / 2f);

        setRadius((float) (Math
                .sqrt((maxX - minX) * (maxX - minX) + (maxY - minY)
                        * (maxY - minY) + (maxZ - minZ) * (maxZ - minZ)) / 2d));
        return this;
    }

    public PlaneIntersectionType intersects(Plane plane) {
        // TODO Auto-generated method stub
        return plane.intersects(this);
    }
}
