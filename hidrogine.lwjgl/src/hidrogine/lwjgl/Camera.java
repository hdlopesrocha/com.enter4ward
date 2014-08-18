package hidrogine.lwjgl;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

// TODO: Auto-generated Javadoc
/**
 * The Class Camera.
 */
public class Camera {

    /** The matrix. */
    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix = null;
    private Matrix4f modelMatrix = null;

    /** The height. */
    private int width, height;

    /**
     * Gets the matrix.
     *
     * @return the matrix
     */
    public Matrix4f getViewMatrix() {
        return viewMatrix;
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
        width = w;
        height = h;
        modelMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        viewMatrix = Matrix4f.setIdentity(viewMatrix);
        projectionMatrix= createProjectionMatrix(w,h);
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
            float lookAtY, float lookAtZ, float upX, float upY, float upZ) {
        Vector3f dir = new Vector3f(lookAtX - posX, lookAtY - posY, lookAtZ
                - posZ);
        Vector3f up = new Vector3f(upX, upY, upZ);
        Vector3f right = new Vector3f();
        dir.normalise();

        Vector3f.cross(dir, up, right);
        right.normalise();

        Vector3f.cross(right, dir, up);
        up.normalise();

        Matrix4f aux = new Matrix4f();

        viewMatrix = new Matrix4f();
        viewMatrix.m00 = right.getX();
        viewMatrix.m01 = right.getY();
        viewMatrix.m02 = right.getZ();
        viewMatrix.m03 = 0.0f;

        viewMatrix.m10 = up.getX();
        viewMatrix.m11 = up.getY();
        viewMatrix.m12 = up.getZ();
        viewMatrix.m13 = 0.0f;

        viewMatrix.m20 = -dir.getX();
        viewMatrix.m21 = -dir.getY();
        viewMatrix.m22 = -dir.getZ();
        viewMatrix.m23 = 0.0f;

        viewMatrix.m30 = 0.0f;
        viewMatrix.m31 = 0.0f;
        viewMatrix.m32 = 0.0f;
        viewMatrix.m33 = 1.0f;

        // setup aux as a translation matrix by placing positions in the last
        // column
        aux.m30 = -posX;
        aux.m31 = -posY;
        aux.m32 = -posZ;

        // multiplication(in fact translation) viewMatrix with aux
        Matrix4f.mul(viewMatrix, aux, viewMatrix);
        
    }
    
    
    private static Matrix4f createProjectionMatrix(int width, int height){
        // Setup projection matrix
        Matrix4f matrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float) width / (float) height;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = Utils.coTangent(Utils.degreesToRadians(fieldOfView / 2f));
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
    
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }
}
