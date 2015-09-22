package com.enter4ward.math;

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


    
    /**
     * Gets the matrix.
     *
     * @return the matrix
     */
    private static final Vector3 TEMP_NEGATIVE = new Vector3();
    private static final Matrix TEMP_TRANSLATE = new Matrix();
    private static final Matrix TEMP_MVP = new Matrix();

    
    public Matrix getViewMatrix() {
        TEMP_NEGATIVE.set(position).multiply(-1f);
        return TEMP_TRANSLATE.createTranslation(TEMP_NEGATIVE).transform(rotation);

    }

    /**
     * Gets the bounding frustum.
     *
     * @return the bounding frustum
     */
    public void update() {
        Matrix mvp = TEMP_MVP;
    	
        mvp.set(getViewMatrix()).multiply(getProjectionMatrix());
        createPlanes(mvp);
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
        Quaternion quat = Quaternion.temp().createFromAxisAngle(x, y, z, w).multiply(rotation);
        rotation.set(quat).normalize();
     
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

    private static final Vector3 TEMP_POSITION = new Vector3();
    private static final Vector3 TEMP_DIRECTION = new Vector3();
    private static final Matrix TEMP_MATRIX = new Matrix();
    
    
    public void lookAt(Vector3 pos, Vector3 lookAt, Vector3 up) {
        position.set(pos);
        TEMP_MATRIX.createLookAt(TEMP_POSITION.clear(), TEMP_DIRECTION.set(lookAt).subtract(pos).normalize(), up);
        rotation.createFromRotationMatrix(TEMP_MATRIX).normalize();
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
        projectionMatrix.createPerspectiveFieldOfView(
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
    
    private static final Matrix TEMP_ROTATION = new Matrix();
    
    
    public void move(float front, float down, float right) {
        final Matrix trans = TEMP_ROTATION.createFromQuaternion(rotation).invert();
        if (front != 0) {
            position.addMultiply(trans.getForward(),front);
        }
        if (down != 0) {
            position.addMultiply(trans.getDown(),down);
        }
        if (right != 0) {
            position.addMultiply(trans.getLeft(),right);
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
