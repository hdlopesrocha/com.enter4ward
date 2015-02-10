package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Camera.
 */
public class Camera {

    /** The matrix. */
    private Quaternion rotation = new Quaternion();

    /** The position. */
    private Vector3 position;

    /** The projection matrix. */
    private Matrix projectionMatrix = null;


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
        rotation = new Quaternion().createFromAxisAngle(new Vector3(x, y, z), w).multiply(rotation);
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
    public Camera() {
        position = new Vector3();
        rotation = Quaternion.identity();
    
        projectionMatrix = new Matrix();
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
        Matrix mat = new Matrix().createLookAt(new Vector3(),new Vector3(lookAtX-posX,lookAtY-posY,lookAtZ-posZ).normalize(),new Vector3(0,1,0));  
        
        
        
        rotation.createFromRotationMatrix(mat);
    }
    
    public void update(int w, int h){
        projectionMatrix = new Matrix().createPerspectiveFieldOfView(
                (float) Math.toRadians(45f), (float) w / (float) h, 0.1f, 100f);
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
