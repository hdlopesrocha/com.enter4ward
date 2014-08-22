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
    private int normalMatrixLocation = 0;
    private int cameraDirectionLocation =0;
    
    private int cameraPositionLocation =0;
    private int materialShininessLocation = 0;
    private int materialAlphaLocation =0;
    private int materialSpecularLocation=0;

    private int opaqueLocation =0;
    private int ambientColorLocation = 0;
    private int diffuseColorLocation = 0;

    private int [] lightPositionLocation = new int[10];
    private int [] lightSpecularColorLocation = new int[10];

    private Vector3f [] lightPosition = new Vector3f[10];
    private Vector3f [] lightSpecularColor = new Vector3f[10];

    Stack<float[]> matrixStack = new Stack<float[]>();
    private FloatBuffer matrix44Buffer = null;


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
        GL20.glBindAttribLocation(pId, 0, "gl_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(pId, 1, "gl_Color");
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(pId, 2, "gl_TextureCoord");

        GL20.glLinkProgram(pId);
        GL20.glValidateProgram(pId);

        // Get matrices uniform locations
        projectionMatrixLocation = GL20.glGetUniformLocation(pId,
                "projectionMatrix");
        viewMatrixLocation = GL20.glGetUniformLocation(pId, "viewMatrix");
        modelMatrixLocation = GL20.glGetUniformLocation(pId, "modelMatrix");
        ambientColorLocation = GL20.glGetUniformLocation(pId, "ambientColor");
        diffuseColorLocation = GL20.glGetUniformLocation(pId, "diffuseColor");
        cameraPositionLocation= GL20.glGetUniformLocation(pId, "cameraPosition");
        opaqueLocation= GL20.glGetUniformLocation(pId, "opaque");
        cameraDirectionLocation= GL20.glGetUniformLocation(pId, "cameraDirection");        
        normalMatrixLocation = GL20.glGetUniformLocation(pId, "normalMatrix");
        
        // material locations
        materialShininessLocation= GL20.glGetUniformLocation(pId, "materialShininess");
        materialAlphaLocation= GL20.glGetUniformLocation(pId, "materialAlpha");
        materialSpecularLocation= GL20.glGetUniformLocation(pId, "materialSpecular");
        
        
        for(int i=0; i<10 ;++i){
            lightPositionLocation[i] = GL20.glGetUniformLocation(pId, "lightPosition["+i+"]");
            lightSpecularColorLocation[i] = GL20.glGetUniformLocation(pId, "lightSpecularColor["+i+"]");

        }
        setMaterialAlpha(1f);
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
        
        GL20.glUniform3f(cameraPositionLocation, camera.getPosition().x,camera.getPosition().y, camera.getPosition().z);

        
        Vector3f cameraDirection = camera.getDirection();
        GL20.glUniform3f(cameraDirectionLocation, cameraDirection.x,cameraDirection.y, cameraDirection.z);

        
        for(int i=0; i<10; ++i){
            Vector3f position = lightPosition[i];
            Vector3f specularColor = lightSpecularColor[i];
            
            if(position!=null){
                
                GL20.glUniform3f(lightPositionLocation[i], position.x,position.y, position.z);
                

            }
            if(specularColor!=null){
                GL20.glUniform3f(lightSpecularColorLocation[i], specularColor.x,specularColor.y, specularColor.z);
            }
        }
        
        modelView.invert().transpose();
        modelView.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(normalMatrixLocation, false, matrix44Buffer);

    }
    
    public void setMaterialAlpha(float value){
        GL20.glUseProgram(pId);
        GL20.glUniform1f(materialAlphaLocation, value);
    }
    
    
    public void setMaterialShininess(float value){
        GL20.glUseProgram(pId);
        GL20.glUniform1f(materialShininessLocation, value);
    }
    
    public void setAmbientColor(float r, float g, float b){
        GL20.glUseProgram(pId);

        GL20.glUniform3f(ambientColorLocation, r,g,b);    
    }

    public void setMaterialSpecular(float r, float g, float b){
        GL20.glUseProgram(pId);
        GL20.glUniform3f(materialSpecularLocation, r,g,b);    
    }
    
    
    public void setDiffuseColor(float r, float g, float b){
        GL20.glUseProgram(pId);
GL20.glUniform3f(diffuseColorLocation, r,g,b);
    }
    
    public void setLightPosition(int index,Vector3f lightPosition){
        this.lightPosition[index]=lightPosition;
    }
    public void setLightColor(int index,Vector3f lightColor){
        this.lightSpecularColor[index]=lightColor;
    }
    
    public void setOpaque(Boolean value){
        GL20.glUseProgram(pId);
       if(value)
           GL11.glEnable(GL11.GL_CULL_FACE);
       else
           GL11.glDisable(GL11.GL_CULL_FACE);

        GL20.glUniform1i(opaqueLocation, value?1:0);    
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
            System.err.println("Could not compile "+filename+".\n"+ GL20.glGetShaderInfoLog(shaderID, 9999));
            System.exit(-1);
        }

        
        return shaderID;
    }



}
