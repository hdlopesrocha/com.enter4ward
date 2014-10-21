package hidrogine.math;

import hidrogine.math.api.ISphere;
import hidrogine.math.api.IVector3;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class Sphere extends ISphere {

    /** The max. */
    private IVector3 position;

    /** The radius. */
    private float radius;

    /**
     * Instantiates a new sphere.
     *
     * @param center
     *            the center
     * @param radius
     *            the radius
     */
    public Sphere(IVector3 position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.ISphere#getCenter()
     */
    @Override
    public IVector3 getPosition() {
        return position;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.ISphere#setCenter(hidrogine.math.IVector3)
     */
    @Override
    public void setPosition(IVector3 position) {
        this.position = position;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.ISphere#getRadius()
     */
    @Override
    public float getRadius() {
        return radius;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.ISphere#setRadius(float)
     */
    @Override
    public void setRadius(float radius) {
        this.radius = radius;
    }


}
