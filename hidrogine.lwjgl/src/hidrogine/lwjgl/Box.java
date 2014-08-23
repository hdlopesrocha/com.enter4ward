package hidrogine.lwjgl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

public class Box {
    private int vaoId, vboiId, indexCount;
    
    public Box(){
        
        float [] packedVector ={0,0,0,0,0,-1,1,0,0,1,0,0,0,-1,1,1,1,1,0,0,0,-1,0,1,1,0,0,0,0,-1,0,0,0,0,1,0,0,1,0,0,1,0,1,0,0,1,1,0,1,1,1,0,0,1,1,1,0,1,1,0,0,1,0,1,0,0,0,0,-1,0,0,0,1,0,0,0,-1,0,1,0,1,0,1,0,-1,0,1,1,0,0,1,0,-1,0,0,1,1,0,0,1,0,0,0,0,1,1,0,1,0,0,1,0,1,1,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,0,0,1,0,0,0,0,1,0,0,1,0,1,0,0,1,1,0,1,0,1,1,1,1,1,0,1,0,0,1,0,1,0,-1,0,0,0,0,0,0,0,-1,0,0,1,0,0,0,1,-1,0,0,1,1,0,1,1,-1,0,0,0,1};
        short[] ii = {0,1,2,2,3,0,4,5,6,6,7,4,8,9,10,10,11,8,12,13,14,14,15,12,16,17,18,18,19,16,20,21,22,22,23,20};
        indexCount = ii.length;
        
        FloatBuffer vertexBuffer = ByteBuffer
                .allocateDirect(packedVector.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(packedVector).position(0);

        ShortBuffer indexBuffer = ByteBuffer
                .allocateDirect(ii.length * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(ii).position(0);

        // Create a new Vertex Array Object in memory and select it (bind)
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Create a new Vertex Buffer Object in memory and select it (bind)
        int vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer,
                GL15.GL_STREAM_DRAW);

        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, VertexData.positionElementCount,
                GL11.GL_FLOAT, false, VertexData.stride,
                VertexData.positionByteOffset);
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, VertexData.normalElementCount,
                GL11.GL_FLOAT, false, VertexData.stride,
                VertexData.normalByteOffset);
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, VertexData.textureElementCount,
                GL11.GL_FLOAT, false, VertexData.stride,
                VertexData.textureByteOffset);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);

        // Create a new VBO for the indices and select it (bind) - INDICES
        vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer,
                GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

    }
 
    
    /**
     * Draw.
     *
     * @param shader
     *            the shader
     */
    public final void draw(final ShaderProgram shader,Vector3f min, Vector3f max) {
        Vector3f dim = new Vector3f();
        Vector3f.sub(max, min, dim);
        shader.pushMatrix();
        shader.getModelMatrix().translate(min);
        shader.getModelMatrix().scale(dim);
        shader.updateModelMatrix();
        
        // Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Bind to the index VBO that has all the information about the
        // order of
        // the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, indexCount,
                GL11.GL_UNSIGNED_SHORT, 0);

        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        shader.setMaterialAlpha(1f);
        shader.popMatrix();
        
        shader.updateModelMatrix();
        
        
    }
    
}
