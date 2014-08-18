package hidrogine.lwjgl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * The Class BufferObject.
 */
public class BufferObject {

    /** The vertex data. */
    private ArrayList<Float> vertexData = new ArrayList<Float>();

    /** The normal data. */
    private ArrayList<Float> normalData = new ArrayList<Float>();

    /** The texture data. */
    private ArrayList<Float> textureData = new ArrayList<Float>();

    /** The index data. */
    private ArrayList<Short> indexData = new ArrayList<Short>();

    /** The material. */
    private Material material;

    /** The index_count. */
    private int indexCount;

    /** The vao id. */
    private int vaoId;

    /** The vboi id. */
    private int vboiId;

    /**
     * Instantiates a new buffer object.
     */
    public BufferObject() {

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
     * @param f
     *            the f
     */
    public final void addVertex(final float f) {
        vertexData.add(f);
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
    public final void addVertex(final float x, final float y, final float z) {
        vertexData.add(x);
        vertexData.add(y);
        vertexData.add(z);
    }

    /**
     * Adds the normal.
     *
     * @param f
     *            the f
     */
    public final void addNormal(final float f) {
        normalData.add(f);
    }

    /**
     * Adds the normal.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @param z
     *            the z
     */
    public final void addNormal(final float x, final float y, final float z) {
        normalData.add(x);
        normalData.add(y);
        normalData.add(z);
    }

    /**
     * Adds the texture.
     *
     * @param f
     *            the f
     */
    public final void addTexture(final float f) {
        textureData.add(f);
    }

    /**
     * Adds the texture.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     */
    public final void addTexture(final float x, final float y) {
        textureData.add(x);
        textureData.add(y);
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

    /**
     * Builds the buffer.
     */
    public final void buildBuffer() {
        ArrayList<Float> packedVector = new ArrayList<Float>();
        while (vertexData.size() > 0 && normalData.size() > 0
                && textureData.size() > 0) {
            packedVector.add(vertexData.remove(0));
            packedVector.add(vertexData.remove(0));
            packedVector.add(vertexData.remove(0));
            packedVector.add(normalData.remove(0));
            packedVector.add(normalData.remove(0));
            packedVector.add(normalData.remove(0));
            packedVector.add(textureData.remove(0));
            packedVector.add(textureData.remove(0));
        }
        indexCount = indexData.size();
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(packedVector.size() * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(toFloatArray(packedVector)).position(0);

        ShortBuffer indexBuffer = ByteBuffer.allocateDirect(indexData.size() * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(toShortArray(indexData)).position(0);

        // CLEAR
        vertexData.clear();
        normalData.clear();
        textureData.clear();
        indexData.clear();
        vertexData = null;
        normalData = null;
        textureData = null;
        indexData = null;

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

    /**
     * Draw.
     *
     * @param shader
     *            the shader
     */
    public final void draw(final ShaderProgram shader) {

        // Bind the texture according to the set texture filter
        if (material != null) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, material.texture);
            // XXX shader.use();
            // XXX shader.bindTexture(material.texture);
            // Bind to the VAO that has all the information about the vertices
            GL30.glBindVertexArray(vaoId);
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            // Bind to the index VBO that has all the information about the
            // order of
            // the vertices
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

            // Draw the vertices
            GL11.glDrawElements(GL11.GL_TRIANGLES, indexCount,
                    GL11.GL_UNSIGNED_SHORT, 0);

            // Put everything back to default (deselect)
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);

        }
    }
}
