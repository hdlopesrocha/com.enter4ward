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
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;


public class ShaderProgram {

    public int mPositionHandle;
    public int mNormalHandle;
    public int mTextureCoordinateHandle;
    private int projectionMatrixLocation = 0;
    private int viewMatrixLocation = 0;
    private int modelMatrixLocation = 0;

    private int ambientColorLocation = 0;
    private int [] lightPositionLocation = new int[10];
    private Vector3f [] lightPosition = new Vector3f[10];

    
    Stack<float[]> matrixStack = new Stack<float[]>();
    private FloatBuffer matrix44Buffer = null;
    private Vector3f ambientColor = new Vector3f();
    private int pId = 0;
    
    public ShaderProgram(String vertexShader, String fragShader) {
        // Create a FloatBuffer with the proper size to store our matrices later
        matrix44Buffer = BufferUtils.createFloatBuffer(16);
        
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
        ambientColorLocation = GL20.glGetUniformLocation(pId, "ambientColor");
        for(int i=0; i<10 ;++i){
            lightPositionLocation[i] = GL20.glGetUniformLocation(pId, "light"+i+"Position");
        }

    }


    public void setAmbientColor(float r, float g, float b){
        ambientColor.set(r, g, b);
    }
    
    /**
     * Use default shader.
     */
    public void use() {
        GL20.glUseProgram(pId);
    }
    
    

   
    
    /**
     * Sets the projection matrix.
     */
    public void update(Camera camera) {
        
        Matrix4f modelView = new Matrix4f();
        Matrix4f.mul(camera.getViewMatrix(),camera.getModelMatrix(), modelView);
        // Upload matrices to the uniform variables
        GL20.glUseProgram(pId);

        camera.getProjectionMatrix().store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);

        camera.getViewMatrix().store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);

        camera.getModelMatrix().store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);
        
        GL20.glUniform3f(ambientColorLocation, ambientColor.x,ambientColor.y, ambientColor.z);

        
        for(int i=0; i<10; ++i){
            Vector3f pos = lightPosition[i];
            if(pos!=null){
                
                Vector4f newPos = new Vector4f();
                Matrix4f.transform(modelView, new Vector4f(pos.x,pos.y,pos.z, 0.0f), newPos);
                GL20.glUniform3f(lightPositionLocation[i], newPos.x,newPos.y, newPos.z);
            }
        }
        
    }
    
    public void setLightPosition(int index,Vector3f lightPosition){
        this.lightPosition[index]=lightPosition;
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



}
