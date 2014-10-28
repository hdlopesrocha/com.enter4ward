package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Camera.
 */
public class Camera {

    /** The matrix. */
    private Quaternion rotation;

    /** The position. */
    private Vector3 position;

    /** The projection matrix. */
    private Matrix projectionMatrix = null;

    /** The height. */
    private int width, height;

    /**
     * Gets the matrix.
     *
     * @return the matrix
     */
    public Matrix getViewMatrix() {

        IVector3 negativePos = new Vector3(position).multiply(-1f);
        return new Matrix().identity().createTranslation(negativePos)
                .multiply(new Matrix().createFromQuaternion(rotation));

    }

    /**
     * Gets the bounding frustum.
     *
     * @return the bounding frustum
     */
    public BoundingFrustum getBoundingFrustum() {
        return new BoundingFrustum(
                new Matrix(getViewMatrix()).multiply(getProjectionMatrix()));

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
        Quaternion newRot = Quaternion.createFromAxisAngle(
                new Vector3(x, y, z), w);
        rotation = newRot.multiply(rotation);
        rotation.normalize();
    }

    /**
     * Instantiates a new camera.
     *
     * @param w
     *            the w
     * @param h
     *            the h
     */
    public Camera(int w, int h) {
        position = new Vector3();
        rotation = Quaternion.identity();
        width = w;
        height = h;
        projectionMatrix = new Matrix().createPerspectiveFieldOfView(
                (float) Math.toRadians(45f), (float) w / (float) h, 0.1f, 100f);

    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Look at.
     *
     * @param posX
     *            the pos x
     * @param posY
     *            the pos y
     * @param posZ
     *            the pos z
     * @param lookAtX
     *            the look at x
     * @param lookAtY
     *            the look at y
     * @param lookAtZ
     *            the look at z
     */
    public void lookAt(float posX, float posY, float posZ, float lookAtX,
            float lookAtY, float lookAtZ) {

        position.set(posX, posY, posZ);
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
