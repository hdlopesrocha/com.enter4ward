package hidrogine.lwjgl;

import hidrogine.math.Vector2;
import hidrogine.math.Vector3;

import org.lwjgl.opengl.GL11;

public class Grid {
	BufferObject obj = new BufferObject(false);

	private short getIndex(int x, int y) {
		return (short) ((x + size) + (y + size) * (size * 2));
	}

	int size;

	public Grid(int size) {
		this.size = size;
		for (int x = -size; x < size; ++x) {
			for (int y = -size; y < size; ++y) {
				obj.addPosition(new Vector3(x, y, 0));
				obj.addNormal(new Vector3(0f, 0f, 1f));
				obj.addTextureCoord(new Vector2((float) x, (float) y));
			}
		}

		for (int x = -size; x < size - 1; ++x) {
			for (int y = -size; y < size - 1; ++y) {
				obj.addIndex(getIndex(x, y));
				obj.addIndex(getIndex(x + 1, y + 1));
				obj.addIndex(getIndex(x + 1, y));
				obj.addIndex(getIndex(x, y));
				obj.addIndex(getIndex(x, y + 1));
				obj.addIndex(getIndex(x + 1, y + 1));
			}
		}
		obj.buildBuffer();
	}

	/**
	 * Draw.
	 * 
	 * @param shader
	 *            the shader
	 */
	public final void draw(final ShaderProgram shader) {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		shader.setAmbientColor(1f, 1f, 1f);
		obj.draw(shader);
		shader.setAmbientColor(0f, 0f, 0f);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

}
