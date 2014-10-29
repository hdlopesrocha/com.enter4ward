package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingSphere extends IBoundingSphere {

    /** The max. */
    private IVector3 center;

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
        this.center = position;
        this.radius = radius;
    }

    /**
     * Instantiates a new bounding sphere.
     */
    public BoundingSphere(IBoundingSphere sph) {
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
    @Override
    public IVector3 getCenter() {
        return center;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.ISphere#setCenter(hidrogine.math.IVector3)
     */
    @Override
    public void setCenter(IVector3 position) {
        this.center = position;
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
