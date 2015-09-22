package hidrogine.lwjgl;

import hidrogine.math.Matrix;
import hidrogine.math.Vector3;

import org.lwjgl.opengl.GL11;

public class DrawableBox {

	BufferObject obj = new BufferObject(false);

	public DrawableBox() {

		float[] packedVector = { 0, 0, 0, 0, 0, -1, 1, 0, 0, 1, 0, 0, 0, -1, 1, 1,
				1, 1, 0, 0, 0, -1, 0, 1, 1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 1, 0,
				0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0,
				1, 0, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0, -1, 0, 1, 0, 1, 0, 1, 0, -1, 0,
				1, 1, 0, 0, 1, 0, -1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0,
				0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1,
				0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1,
				0, 0, 1, 0, 1, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0, 1, -1,
				0, 0, 1, 1, 0, 1, 1, -1, 0, 0, 0, 1 };
		short[] ii = { 0, 1, 1, 1, 1, 2, 2, 3, 3, 3, 3, 0, 4, 5, 5, 5, 5, 6, 6, 7,
				7, 7, 7, 4, 8, 9, 9, 9, 9, 10, 10, 11, 11, 11, 11, 8, 12, 13, 13, 13,
				13, 14, 14, 15, 15, 15, 15, 12, 16, 17, 17, 17, 17, 18, 18, 19, 19, 19,
				19, 16, 20, 21, 21, 21, 21, 22, 22, 23, 23, 23, 23, 20 };

		for (int i = 0; i < packedVector.length; i += 8) {
			obj.addVertex(packedVector[i + 0], packedVector[i + 1],
					packedVector[i + 2]);

			obj.addNormal(packedVector[i + 3], packedVector[i + 4],
					packedVector[i + 5]);
			obj.addTexture(packedVector[i + 6], packedVector[i + 7]);

		}

		for (int i = 0; i < ii.length; ++i) {
			obj.addIndex(ii[i]);
		}

		obj.buildBuffer();
	}

	private static final Vector3 TEMP_DIM = new Vector3();
	private static final Matrix TEMP_SCALE = new Matrix();

	/**
	 * Draw.
	 *
	 * @param shader
	 *          the shader
	 */
	public final void draw(final ShaderProgram shader, final Vector3 min,
			final Vector3 max) {
		// System.out.println(min.toString()+" : "+max.toString());
		shader.setMaterialAlpha(0.2f);

		GL11.glDisable(GL11.GL_CULL_FACE);
		TEMP_DIM.set(max).subtract(min);

		shader.setModelMatrix(TEMP_SCALE.createScale(TEMP_DIM).translate(min));
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		obj.draw(shader);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

		GL11.glEnable(GL11.GL_CULL_FACE);

	}

}
