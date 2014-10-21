package hidrogine.math;

import hidrogine.math.api.IBox;
import hidrogine.math.api.IVector3;

// TODO: Auto-generated Javadoc
/**
 * The Class Box.
 */
public class Box extends IBox {

    /** The max. */
    private IVector3 min, max;

    /* (non-Javadoc)
     * @see hidrogine.math.IBox#getMin()
     */
    public IVector3 getMin() {
        return min;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.IBox#setMin(hidrogine.math.IVector3)
     */
    public void setMin(IVector3 min) {
        this.min = min;
    }

    /* (non-Javadoc)
     * @see hidrogine.math.IBox#getMax()
     */
    public IVector3 getMax() {
        return max;
    }

    /* (non-Javadoc)
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
    public Box(IVector3 min, IVector3 max) {
        this.min = min;
        this.max = max;
    }

}
