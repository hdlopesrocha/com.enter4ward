package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingSphere extends IBoundingSphere {

    /** The max. */
    private IVector3 position;

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
    public BoundingSphere(IVector3 position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    /**
     * Instantiates a new bounding sphere.
     */
    public BoundingSphere() {
        this.position = new Vector3();
        this.radius = 0f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#getCenter()
     */
    @Override
    public IVector3 getPosition() {
        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#setCenter(hidrogine.math.IVector3)
     */
    @Override
    public void setPosition(IVector3 position) {
        this.position = position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#getRadius()
     */
    @Override
    public float getRadius() {
        return radius;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#setRadius(float)
     */
    @Override
    public void setRadius(float radius) {
        this.radius = radius;
    }

}
