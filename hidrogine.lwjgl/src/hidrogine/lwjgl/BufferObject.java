package hidrogine.lwjgl;

import hidrogine.math.api.IVector2;
import hidrogine.math.api.IVector3;

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

// TODO: Auto-generated Javadoc
/**
 * The Class BufferObject.
 */
public class BufferObject {

    /* (non-Javadoc)
     * @see hidrogine.lwjgl.IBufferObject#getMaterial()
     */
    public Material getMaterial() {
        return material;
    }

    /** The positions. */
    private ArrayList<IVector3> positions = new ArrayList<IVector3>();
    
    /** The normals. */
    private ArrayList<IVector3> normals = new ArrayList<IVector3>();
    
    /** The texture coords. */
    private ArrayList<IVector2> textureCoords = new ArrayList<IVector2>();

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
    
    /** The vbo id. */
    private int vboId;

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
     * @param vx the vx
     * @param vy the vy
     * @param vz the vz
     */
    public final void addPosition(final IVector3 vec) {
        positions.add(vec);

    }

    /* (non-Javadoc)
     * @see hidrogine.lwjgl.IBufferObject#addNormal(float, float, float)
     */
    public final void addNormal(final IVector3 vec) {
        normals.add(vec);
    }

    /* (non-Javadoc)
     * @see hidrogine.lwjgl.IBufferObject#addTextureCoord(float, float)
     */
    public final void addTextureCoord(final IVector2 vec) {
        textureCoords.add(vec);
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
        final ArrayList<Float> packedVector = new ArrayList<Float>();
        while (positions.size() > 0 && normals.size() > 0
                && textureCoords.size() > 0) {
        	IVector3 pos = positions.remove(0);
        	IVector3 nrm = normals.remove(0);
        	IVector2 tex = textureCoords.remove(0);

        	
        	packedVector.add(pos.getX());
            packedVector.add(pos.getY());
            packedVector.add(pos.getZ());
            packedVector.add(nrm.getX());
            packedVector.add(nrm.getY());
            packedVector.add(nrm.getZ());
            packedVector.add(tex.getX());
            packedVector.add(tex.getY());
        }

        indexCount = indexData.size();
        final FloatBuffer vertexBuffer = ByteBuffer
                .allocateDirect(packedVector.size() * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(toFloatArray(packedVector)).position(0);

        final ShortBuffer indexBuffer = ByteBuffer
                .allocateDirect(indexData.size() * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(toShortArray(indexData)).position(0);

        // Create a new Vertex Array Object in memory and select it (bind)
        vaoId = GL30.glGenVertexArrays();
        vboiId = GL15.glGenBuffers();
        vboId = GL15.glGenBuffers();
        indexData.clear();
        normals.clear();
        positions.clear();
        textureCoords.clear();

        GL30.glBindVertexArray(vaoId);
        // Create a new Vertex Buffer Object in memory and select it (bind)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer,
                GL15.GL_STATIC_DRAW);

        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, VertexData.positionElementCount,
                GL11.GL_FLOAT, false, VertexData.stride,
                VertexData.positionByteOffset);
        GL20.glVertexAttribPointer(1, VertexData.normalElementCount,
                GL11.GL_FLOAT, false, VertexData.stride,
                VertexData.normalByteOffset);
        GL20.glVertexAttribPointer(2, VertexData.textureElementCount,
                GL11.GL_FLOAT, false, VertexData.stride,
                VertexData.textureByteOffset);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);

        // Create a new VBO for the indices and select it (bind) - INDICES
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

    /* (non-Javadoc)
     * @see hidrogine.lwjgl.IBufferObject#bind(hidrogine.lwjgl.ShaderProgram)
     */
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
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);

    }

    /**
     * Draw.
     *
     * @param shader
     *            the shader
     */
    public final void draw(final ShaderProgram shader) {
        // Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Bind to the index VBO that has all the information about the
        // order of
        // the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

        shader.updateModelMatrix();
        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, indexCount,
                GL11.GL_UNSIGNED_SHORT, 0);

        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        shader.setMaterialAlpha(1f);

    }
}
