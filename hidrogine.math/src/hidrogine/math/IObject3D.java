package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class IObject3D.
 */
public abstract class IObject3D {

    /** The position. */
    private Vector3 position;

    /** The rotation. */
    private Quaternion rotation = new Quaternion().identity();

    /** The model. */
    private IModel3D model;

    /** The node. */
    private Space.Node node;

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
    public IObject3D(Vector3 position, IModel3D model) {
        this.position = position;
        this.model = model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.api.ISphere#getPosition()
     */
    /**
     * Gets the bounding sphere.
     *
     * @return the bounding sphere
     */
    public BoundingSphere getBoundingSphere() {
        // return new
        // Vector3(model.getContainer().getCenter()).transform(rotation).add(position);
        return new BoundingSphere(new Vector3(model.getContainer().getCenter())
                .transform(rotation).add(position), model.getContainer()
                .getRadius());

    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position
     *            the new position
     */
    public void setPosition(final Vector3 position) {
        this.position = position;
    }

    /**
     * Insert.
     *
     * @param space
     *            the space
     * @return the i object3 d
     */
    public IObject3D insert(final Space space) {
        node = space.insert(getBoundingSphere(), this);
        return this;
    }

    /**
     * Removes the.
     */
    public void remove() {
        node.remove(this);
    }

    /**
     * Update.
     *
     * @param space
     *            the space
     */
    public void update(Space space) {
        node = space.update(getBoundingSphere(), node, this);
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
