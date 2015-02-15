package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class IObject3D.
 */
public abstract class IObject3D extends IBoundingSphere {

    /** The position. */
    private IVector3 position;

    /** The rotation. */
    private Quaternion rotation = new Quaternion().identity();

    /** The model. */
    private IModel3D model;

    /** The node. */
    private SpaceNode node;

    /**
     * Gets the model matrix.
     *
     * @return the model matrix
     */
    public Matrix getModelMatrix() {
        return new Matrix().createFromQuaternion(rotation).translate(position);
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public IModel3D getModel() {
        return model;
    }

    /**
     * Sets the model.
     *
     * @param model
     *            the new model
     */
    public void setModel(IModel3D model) {
        this.model = model;
    }

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
    public IVector3 getCenter() {
        // return new
        // Vector3(model.getContainer().getCenter()).transform(rotation).add(position);
        return new Matrix().createTranslation(model.getContainer().getCenter())
                .multiply(getModelMatrix()).getTranslation();
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public IVector3 getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position
     *            the new position
     */
    public void setPosition(IVector3 position) {
        this.position = position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.api.ISphere#setPosition(hidrogine.math.api.IVector3)
     */
    public void setCenter(IVector3 position) {
        throw new RuntimeException("Readonly property!");
    }

    /**
     * Insert.
     *
     * @param space
     *            the space
     */
    public IObject3D insert(Space space) {
        node = space.insert(this);
        return this;
    }

    /**
     * Removes the.
     */
    public void remove() {
        node.remove(this);
    }

    /**
     * Gets the node.
     *
     * @return the node
     */
    protected SpaceNode getNode() {
        return node;
    }

    /**
     * Update.
     *
     * @param space
     *            the space
     */
    public void update(Space space) {
        node = space.update(this, node);
    }

    /**
     * Gets the rotation.
     *
     * @return the rotation
     */
    public Quaternion getRotation() {
        return rotation;
    }

}
