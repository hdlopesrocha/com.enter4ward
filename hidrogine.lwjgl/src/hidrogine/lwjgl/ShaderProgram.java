package hidrogine.lwjgl;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Stack;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;


public class ShaderProgram {

    public int mPositionHandle;
    public int mNormalHandle;
    public int mTextureCoordinateHandle;
    private int projectionMatrixLocation = 0;
    private int viewMatrixLocation = 0;
    private int modelMatrixLocation = 0;
    Stack<float[]> matrixStack = new Stack<float[]>();
    private Matrix4f projectionMatrix = null;
    private Matrix4f viewMatrix = null;
    private Matrix4f modelMatrix = null;
    private FloatBuffer matrix44Buffer = null;
    private int pId = 0;
    
    public ShaderProgram(String vertexShader, String fragShader, int width, int height) {
        // Load the vertex shader
        int vsId = this.loadShader("vertex.glsl", GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        int fsId = this.loadShader("fragment.glsl", GL20.GL_FRAGMENT_SHADER);

        // Create a new shader program that links both shaders
        pId = GL20.glCreateProgram();
        GL20.glAttachShader(pId, vsId);
        GL20.glAttachShader(pId, fsId);

        // Position information will be attribute 0
        GL20.glBindAttribLocation(pId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "in_Color");
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");

        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);

        // Get matrices uniform locations
        projectionMatrixLocation = GL20.glGetUniformLocation(pId,
                "projectionMatrix");
        viewMatrixLocation = GL20.glGetUniformLocation(pId, "viewMatrix");
        modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");
    
        setupMatrices(width,height);

    }
    /**
     * Gets the view matrix.
     *
     * @return the view matrix
     */
    public Matrix4f getViewMatrix() {
        return viewMatrix;
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
     * Use default shader.
     */
    public void use() {
        GL20.glUseProgram(pId);
    }
    
    /**
     * Sets the projection matrix.
     *
     * @param projectionMatrix
     *            the new projection matrix
     */
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    /**
     * Sets the view matrix.
     *
     * @param viewMatrix
     *            the new view matrix
     */
    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    /**
     * Sets the model matrix.
     *
     * @param modelMatrix
     *            the new model matrix
     */
    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    
    /**
     * Sets the projection matrix.
     */
    public void update() {
        // Upload matrices to the uniform variables
        GL20.glUseProgram(pId);

        projectionMatrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);

        viewMatrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);

        modelMatrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);
        
    }
    

    /**
     * Load shader.
     *
     * @param filename
     *            the filename
     * @param type
     *            the type
     * @return the int
     */
    private int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }

        return shaderID;
    }

    /**
     * Setup matrices.
     */
    private void setupMatrices(int width, int height) {
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float) width / (float) height;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = Utils.coTangent(Utils.degreesToRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        projectionMatrix.m33 = 0;

        // Setup view matrix
        viewMatrix = new Matrix4f();

        // Setup model matrix
        modelMatrix = new Matrix4f();

        // Create a FloatBuffer with the proper size to store our matrices later
        matrix44Buffer = BufferUtils.createFloatBuffer(16);
    }
    
    /**
     * Gets the projection matrix.
     *
     * @return the projection matrix
     */
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
