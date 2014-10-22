package hidrogine.math.api;

// TODO: Auto-generated Javadoc
/**
 * The Class IObject3D.
 */
public abstract class IObject3D extends ISphere {

    /** The position. */
    private IVector3 position;

    /** The model. */
    private IModel3D model;

    /**
     * Instantiates a new i object3 d.
     *
     * @param position
     *            the position
     * @param model
     *            the model
     */
    public IObject3D(IVector3 position, IModel3D model) {
        this.position = position;
        this.model = model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.api.ISphere#getRadius()
     */
    public float getRadius() {
        return model.getContainer().getRadius();
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.api.ISphere#setRadius(float)
     */
    public void setRadius(float radius) {
        throw new RuntimeException("Readonly property!");
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.api.ISphere#getPosition()
     */
    public IVector3 getPosition() {
        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.api.ISphere#setPosition(hidrogine.math.api.IVector3)
     */
    public void setPosition(IVector3 position) {
        this.position = position;
    }

}
