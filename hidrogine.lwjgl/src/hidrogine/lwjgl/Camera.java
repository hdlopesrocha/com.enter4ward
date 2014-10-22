package hidrogine.lwjgl;
import hidrogine.math.Matrix;
import hidrogine.math.Quaternion;
import hidrogine.math.Vector3;
import hidrogine.math.api.IVector3;

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
       return Matrix.multiply( Matrix.createTranslation( negativePos),Matrix.createFromQuaternion(rotation));
        
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
    	Quaternion newRot = Quaternion.createFromAxisAngle(new Vector3(x, y, z), w);
        rotation = Quaternion.multiply(newRot, rotation);
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

        projectionMatrix = Matrix.createPerspectiveFieldOfView((float)Math.toRadians(45f), (float)w/(float)h, 0.1f, 100f);
   
      
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
     * @param left
     *            the left
     */
    public void move(float front, float down, float left) {
        Matrix trans = Matrix.invert(Matrix.createFromQuaternion(rotation));
      
        if (front != 0) {
            IVector3 frontVec = new Vector3(0, 0, 1);
            frontVec = Vector3.transform( frontVec,trans);
            frontVec.multiply(front);
            position.add(frontVec);
        }

        if (down != 0) {
            IVector3 downVec = new Vector3(0, 1, 0);
            downVec = Vector3.transform(downVec,trans);
            downVec.multiply(down);
            position.add(downVec);
        }

        if (left != 0) {
            IVector3 leftVec = new Vector3(1, 0, 0);
            leftVec = Vector3.transform(leftVec,trans);
            leftVec.multiply(left);
            position.add(leftVec);
        }
    }

    public Vector3 getPosition() {
        // TODO Auto-generated method stub
        return position;
    }


}
