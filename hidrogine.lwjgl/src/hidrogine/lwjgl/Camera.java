package hidrogine.lwjgl;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

// TODO: Auto-generated Javadoc
/**
 * The Class Camera.
 */
public class Camera {

    /** The matrix. */
    private Quaternion rotation;

    /** The position. */
    private Vector3f position;

    /** The projection matrix. */
    private Matrix4f projectionMatrix = null;

    /** The model matrix. */
    private Matrix4f modelMatrix = null;

    /** The height. */
    private int width, height;

    /**
     * Gets the matrix.
     *
     * @return the matrix
     */
    public Matrix4f getViewMatrix() {
        Matrix4f result = convertQuaternionToMatrix4f(rotation);
        result = result.translate(new Vector3f(-position.x,-position.y,-position.z));
        return result;
    }

    private static Matrix4f convertQuaternionToMatrix4f(Quaternion q) {
        Matrix4f matrix = new Matrix4f();
        matrix.m00 = 1.0f - 2.0f * (q.getY() * q.getY() + q.getZ() * q.getZ());
        matrix.m01 = 2.0f * (q.getX() * q.getY() + q.getZ() * q.getW());
        matrix.m02 = 2.0f * (q.getX() * q.getZ() - q.getY() * q.getW());
        matrix.m03 = 0.0f;

        // Second row
        matrix.m10 = 2.0f * (q.getX() * q.getY() - q.getZ() * q.getW());
        matrix.m11 = 1.0f - 2.0f * (q.getX() * q.getX() + q.getZ() * q.getZ());
        matrix.m12 = 2.0f * (q.getZ() * q.getY() + q.getX() * q.getW());
        matrix.m13 = 0.0f;

        // Third row
        matrix.m20 = 2.0f * (q.getX() * q.getZ() + q.getY() * q.getW());
        matrix.m21 = 2.0f * (q.getY() * q.getZ() - q.getX() * q.getW());
        matrix.m22 = 1.0f - 2.0f * (q.getX() * q.getX() + q.getY() * q.getY());
        matrix.m23 = 0.0f;

        // Fourth row
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        matrix.m33 = 1.0f;

        return matrix;
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
        Quaternion newRot = new Quaternion().setIdentity();
        newRot.setFromAxisAngle(new Vector4f(x, y, z, w));
        Quaternion res = new Quaternion();
        Quaternion.mul(newRot, rotation, res);
        res.normalise();
        rotation = res;
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
        position = new Vector3f();
        rotation = new Quaternion().setIdentity();
        width = w;
        height = h;
        modelMatrix = new Matrix4f();

        projectionMatrix = createProjectionMatrix(w, h);
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
    public void move(Vector3f change) {
        position.x += change.x;
        position.y += change.y;
        position.z += change.z;
    }

    /**
     * Creates the projection matrix.
     *
     * @param width
     *            the width
     * @param height
     *            the height
     * @return the matrix4f
     */
    private static Matrix4f createProjectionMatrix(int width, int height) {
        // Setup projection matrix
        Matrix4f matrix = new Matrix4f();
        float fieldOfView = 45f;
        float aspectRatio = (float) width / (float) height;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = (float) (1f / Math
                .tan(Math.toRadians(fieldOfView / 2f)));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        matrix.m00 = x_scale;
        matrix.m11 = y_scale;
        matrix.m22 = -((far_plane + near_plane) / frustum_length);
        matrix.m23 = -1;
        matrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        matrix.m33 = 0;
        return matrix;

    }

    /**
     * Gets the projection matrix.
     *
     * @return the projection matrix
     */
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Gets the model matrix.
     *
     * @return the model matrix
     */
    public Matrix4f getModelMatrix() {
        return modelMatrix;
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
        Matrix4f trans = convertQuaternionToMatrix4f(rotation);
        trans.invert();
        if (front != 0) {
            Vector4f frontVec = new Vector4f(0, 0, 1, 0);
            Matrix4f.transform(trans, frontVec, frontVec);
            frontVec.scale(front);
            position.translate(frontVec.x, frontVec.y, frontVec.z);
        }

        if (down != 0) {
            Vector4f downVec = new Vector4f(0, 1, 0, 0);
            Matrix4f.transform(trans, downVec, downVec);
            downVec.scale(down);
            position.translate(downVec.x, downVec.y, downVec.z);
        }

        if (left != 0) {
            Vector4f leftVec = new Vector4f(1, 0, 0, 0);
            Matrix4f.transform(trans, leftVec, leftVec);
            leftVec.scale(left);
            position.translate(leftVec.x, leftVec.y, leftVec.z);
        }
    }

    public Vector3f getPosition() {
        // TODO Auto-generated method stub
        return position;
    }

    public Vector3f getDirection() {
        Matrix4f trans = convertQuaternionToMatrix4f(rotation);
        trans.invert();
        Vector4f frontVec = new Vector4f(0, 0, 1, 0);
        Matrix4f.transform(trans, frontVec, frontVec);
        return new Vector3f(frontVec.x, frontVec.y, frontVec.z);
    }
}
