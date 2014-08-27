package hidrogine.lwjgl;


import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GLContext;

/**
 * The Class BufferObject.
 */
public class VertexBufferObject implements IBufferObject {

    public Material getMaterial() {
        return material;
    }

    ArrayList<Float> positions = new ArrayList<Float>();
    ArrayList<Float> normals = new ArrayList<Float>();

    ArrayList<Float> textureCoords = new ArrayList<Float>();

    /** The index data. */
    private ArrayList<Short> indexData = new ArrayList<Short>();

    /** The material. */
    private Material material;

    /** The index_count. */
    private int indexBufferSize;

    private int indexBufferID;
    private int vertexBufferID;
    private int normalBufferID;
    private int textureBufferID;

    /**
     * Instantiates a new buffer object.
     * 
     * @param subGroupName
     */
    public VertexBufferObject() {
    }

    /**
     * Sets the material.
     *
     * @param f
     *            the new material
     */
    public final void setMaterial(final Material f) {
        material = f;
    }

    /**
     * Adds the vertex.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @param z
     *            the z
     */
    public final void addPosition(final float vx, final float vy, final float vz) {
        positions.add(vx);
        positions.add(vy);
        positions.add(vz);

    }

    public final void addNormal(final float nx, final float ny, final float nz) {
        normals.add(nx);
        normals.add(ny);
        normals.add(nz);
     }

    
    public final void addTextureCoord(final float tx,
            final float ty) {

        textureCoords.add(tx);
        textureCoords.add(1 - ty);
    }

    
    
    /**
     * Adds the index.
     *
     * @param f
     *            the f
     */
    public final void addIndex(final short f) {
        indexData.add(f);
    }

    public void bufferData(int id, FloatBuffer buffer) {
        if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
            glBindBufferARB(
                    GL_ARRAY_BUFFER_ARB, id);
            glBufferDataARB(
                    GL_ARRAY_BUFFER_ARB, buffer,
                    GL_STATIC_DRAW_ARB);
        }
    }

    public void bufferData(int id, ShortBuffer buffer) {
        if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
            glBindBufferARB(
                    GL_ARRAY_BUFFER_ARB, id);
            glBufferDataARB(
                    GL_ARRAY_BUFFER_ARB, buffer,
                    GL_STATIC_DRAW_ARB);
        }
    }

    
    public static int createVBOID() {
        if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
            IntBuffer buffer = BufferUtils.createIntBuffer(1);
            glGenBuffersARB(buffer);
            return buffer.get(0);
        }
        else {
            System.out.println("MERDA");
        }
        return 0;
    }


    /**
     * Builds the buffer.
     */
    public final void buildBuffer() {
        indexBufferID = createVBOID();
        vertexBufferID = createVBOID();
        normalBufferID = createVBOID();
        textureBufferID = createVBOID();
        
        indexBufferSize = indexData.size();
        FloatBuffer postitionBuffer = ByteBuffer.allocateDirect(positions.size() * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        postitionBuffer.put(toFloatArray(positions)).flip();

        FloatBuffer normalBuffer = ByteBuffer.allocateDirect(normals.size() * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        normalBuffer.put(toFloatArray(normals)).flip();

        FloatBuffer textureBuffer = ByteBuffer.allocateDirect(textureCoords.size() * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureBuffer.put(toFloatArray(textureCoords)).flip();

        
        ShortBuffer indexBuffer = ByteBuffer
                .allocateDirect(indexData.size() * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(toShortArray(indexData)).flip();

        // CLEAR
  
        indexData.clear();
        indexData = null;

        bufferData(vertexBufferID,postitionBuffer);
        bufferData(textureBufferID,textureBuffer);
        bufferData(normalBufferID,normalBuffer);
        bufferData(indexBufferID,indexBuffer);
        
    }

    /**
     * To float array.
     *
     * @param list
     *            the list
     * @return the float[]
     */
    private static float[] toFloatArray(final ArrayList<Float> list) {
        float[] ret = new float[list.size()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    /**
     * To short array.
     *
     * @param list
     *            the list
     * @return the short[]
     */
    private static short[] toShortArray(final ArrayList<Short> list) {
        short[] ret = new short[list.size()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    public final void bind(final ShaderProgram shader) {
        int tex = material != null ? material.texture : 0;
        // Bind the texture according to the set texture filter
        if (material != null) {
            if (material.Ns != null)
                shader.setMaterialShininess(material.Ns);
            if (material.Ks != null)
                shader.setMaterialSpecular(material.Ks[0], material.Ks[1],
                        material.Ks[2]);
            if (material.Kd != null)
                shader.setDiffuseColor(material.Kd[0], material.Kd[1],
                        material.Kd[2]);
            if (material.d != null)
                shader.setMaterialAlpha(material.d);

        }

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, tex);

    }

    /**
     * Draw.
     *
     * @param shader
     *            the shader
     */
    public final void draw(final ShaderProgram shader) {
shader.use();
        shader.updateModelMatrix();
        // Draw the vertices
        // Vertices
        
        glEnableClientState(GL_VERTEX_ARRAY);
        glBindBufferARB( GL_ARRAY_BUFFER_ARB, vertexBufferID);
        glVertexPointer(3, GL_FLOAT, 0, 0);

        
        glEnableClientState(GL_NORMAL_ARRAY);
        glBindBufferARB(GL_ARRAY_BUFFER_ARB, normalBufferID);
        glColorPointer(3, GL_FLOAT, 0, 0);

        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glBindBufferARB(GL_ARRAY_BUFFER_ARB, textureBufferID);
        glColorPointer(2, GL_FLOAT, 0, 0);

        
        glBindBufferARB(
                GL_ELEMENT_ARRAY_BUFFER_ARB,
                indexBufferID);
        glDrawElements(GL_TRIANGLES, indexBufferSize/3, GL_UNSIGNED_SHORT, 0L);

        shader.setMaterialAlpha(1f);

        
        
        
        
        
        
        
        
    }
}
