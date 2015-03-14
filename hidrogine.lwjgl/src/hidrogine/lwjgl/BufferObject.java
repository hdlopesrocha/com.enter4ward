package hidrogine.lwjgl;

import hidrogine.math.IBufferObject;
import hidrogine.math.Material;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

// TODO: Auto-generated Javadoc
/**
 * The Class BufferObject.
 */
public class BufferObject extends IBufferObject {

	// The amount of bytes an element has
	/** The Constant elementBytes. */
	public static final int elementBytes = 4;

	// Elements per parameter
	/** The Constant positionElementCount. */
	public static final int positionElementCount = 3;

	/** The Constant normalElementCount. */
	public static final int normalElementCount = 3;

	/** The Constant textureElementCount. */
	public static final int textureElementCount = 2;

	// Bytes per parameter
	/** The Constant positionBytesCount. */
	public static final int positionBytesCount = positionElementCount
			* elementBytes;

	/** The Constant normalByteCount. */
	public static final int normalByteCount = normalElementCount * elementBytes;

	/** The Constant textureByteCount. */
	public static final int textureByteCount = textureElementCount * elementBytes;

	// Byte offsets per parameter
	/** The Constant positionByteOffset. */
	public static final int positionByteOffset = 0;

	/** The Constant normalByteOffset. */
	public static final int normalByteOffset = positionByteOffset
			+ positionBytesCount;

	/** The Constant textureByteOffset. */
	public static final int textureByteOffset = normalByteOffset
			+ normalByteCount;

	// The amount of elements that a vertex has
	/** The Constant elementCount. */
	public static final int elementCount = positionElementCount
			+ normalElementCount + textureElementCount;
	// The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
	/** The Constant stride. */
	public static final int stride = positionBytesCount + normalByteCount
			+ textureByteCount;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.IBufferObject#getMaterial()
	 */
	/**
	 * Gets the material.
	 *
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/** The material. */
	private Material material;


	/** The vao id. */
	private int vaoId;

	/** The vboi id. */
	private int vboiId;

	/** The vbo id. */
	private int vboId;


	/**
	 * Instantiates a new buffer object.
	 *
	 * @param explodeTriangles
	 *          the explode triangles
	 */
	public BufferObject(boolean explodeTriangles) {
		super(explodeTriangles);
	}

	/**
	 * Sets the material.
	 *
	 * @param f
	 *          the new material
	 */
	public final void setMaterial(final Material f) {
		material = f;
	}


	/**
	 * Builds the buffer.
	 */
	public final void buildBuffer() {
		super.buildBuffer();

		

		// Create a new Vertex Array Object in memory and select it (bind)
		vaoId = GL30.glGenVertexArrays();
		vboiId = GL15.glGenBuffers();
		vboId = GL15.glGenBuffers();



		GL30.glBindVertexArray(vaoId);
		// Create a new Vertex Buffer Object in memory and select it (bind)
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

		// Put the position coordinates in attribute list 0
		GL20.glVertexAttribPointer(0, positionElementCount, GL11.GL_FLOAT, false,
				stride, positionByteOffset);
		GL20.glVertexAttribPointer(1, normalElementCount, GL11.GL_FLOAT, false,
				stride, normalByteOffset);
		GL20.glVertexAttribPointer(2, textureElementCount, GL11.GL_FLOAT, false,
				stride, textureByteOffset);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		// Deselect (bind to 0) the VAO
		GL30.glBindVertexArray(0);

		// Create a new VBO for the indices and select it (bind) - INDICES
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer,
				GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.IBufferObject#bind(hidrogine.lwjgl.ShaderProgram)
	 */
	/**
	 * Bind.
	 *
	 * @param shader
	 *          the shader
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
				shader.setDiffuseColor(material.Kd[0], material.Kd[1], material.Kd[2]);
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
	 *          the shader
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

		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, indexCount, GL11.GL_UNSIGNED_SHORT,
				0);

		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}


}
