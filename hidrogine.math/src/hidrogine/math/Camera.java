package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Camera.
 */
public class Camera extends BoundingFrustum{

    /** The matrix. */
    private Quaternion rotation = new Quaternion();

    /** The position. */
    private Vector3 position;

    /** The projection matrix. */
    private Matrix projectionMatrix = null;

    /** The near. */
    private float far, near;

    private static final Vector3 TEMP_NEGATIVE = new Vector3();
    private static final Matrix TEMP_TRANSLATION = new Matrix();
    private static final Matrix TEMP_MVP = new Matrix();

    
    /**
     * Gets the matrix.
     *
     * @return the matrix
     */
    public Matrix getViewMatrix() {

        TEMP_NEGATIVE.set(position).multiply(-1f);
        return TEMP_TRANSLATION.identity().createTranslation(TEMP_NEGATIVE).transform(rotation);

    }

    /**
     * Gets the bounding frustum.
     *
     * @return the bounding frustum
     */
    public void update() {
        TEMP_MVP.set(getViewMatrix()).multiply(getProjectionMatrix());
        createPlanes(TEMP_MVP);
        createCorners();
    }

    /**
     * Rotate.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @param z
     *            the z
     * @param w
     *            the w
     */
    public void rotate(float x, float y, float z, float w) {
        rotation = new Quaternion()
                .createFromAxisAngle(new Vector3(x, y, z), w)
                .multiply(rotation);
        rotation.normalize();
    }

    /**
     * Instantiates a new camera.
     *
     * @param near
     *            the near
     * @param far
     *            the far
     */
    public Camera(float near, float far) {
        position = new Vector3();
        rotation = new Quaternion().identity();
        this.far = far;
        this.near = near;
        projectionMatrix = new Matrix();
    }

    /**
     * Gets the rotation.
     *
     * @return the rotation
     */
    public Quaternion getRotation() {
        return rotation;
    }

    /**
     * Look at.
     *
     * @param pos
     *            the pos
     * @param lookAt
     *            the look at
     * @param up
     *            the up
     */
    public void lookAt(Vector3 pos, Vector3 lookAt, Vector3 up) {
        position.set(pos);
        Matrix mat = new Matrix().createLookAt(new Vector3(), new Vector3(
                lookAt).subtract(pos).normalize(), up);

        rotation.createFromRotationMatrix(mat).normalize();
    }

    /**
     * Update.
     *
     * @param w
     *            the w
     * @param h
     *            the h
     */
    public void update(int w, int h) {
        projectionMatrix = new Matrix().createPerspectiveFieldOfView(
                (float) Math.toRadians(45f), (float) w / (float) h, near, far);
    }

    /**
     * Gets the near.
     *
     * @return the near
     */
    public float getNear() {
        return near;
    }

    /**
     * Gets the far.
     *
     * @return the far
     */
    public float getFar() {
        return far;
    }

    /**
     * Move.
     *
     * @param change
     *            the change
     */
    public void move(Vector3 change) {
        position.add(change);
    }

    /**
     * Gets the projection matrix.
     *
     * @return the projection matrix
     */
    public Matrix getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Move.
     *
     * @param front
     *            the front
     * @param down
     *            the down
     * @param right
     *            the left
     */
    public void move(float front, float down, float right) {
        Matrix trans = new Matrix().createFromQuaternion(rotation).invert();
        if (front != 0) {
            position.add(new Vector3(trans.getForward()).multiply(front));
        }
        if (down != 0) {
            position.add(new Vector3(trans.getDown()).multiply(down));
        }
        if (right != 0) {
            position.add(new Vector3(trans.getLeft()).multiply(right));
        }
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Vector3 getPosition() {
        // TODO Auto-generated method stub
        return position;
    }

}
