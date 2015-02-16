package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class BoundingBox extends IBoundingBox {

    /** The max. */
    private IVector3 min, max;

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getMin()
     */
    public IVector3 getMin() {
        return min;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#setMin(hidrogine.math.IVector3)
     */
    public void setMin(IVector3 min) {
        this.min = min;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#getMax()
     */
    public IVector3 getMax() {
        return max;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IBox#setMax(hidrogine.math.IVector3)
     */
    public void setMax(IVector3 max) {
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
    public BoundingBox(IVector3 min, IVector3 max) {
        this.min = min;
        this.max = max;
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
        IVector3 center = sphere.getCenter();
        
        IVector3 vector1 = new Vector3(sphere.getRadius());
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


    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.api.IBoundingBox#toString()
     */
    public String toString() {
        return "{{Min:" + min.toString() + " Max:" + max.toString() + "}}";
    }

}
