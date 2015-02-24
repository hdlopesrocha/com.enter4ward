package hidrogine.lwjgl;

import hidrogine.math.Matrix;
import hidrogine.math.Vector2;
import hidrogine.math.Vector3;

import org.lwjgl.opengl.GL11;

public class DrawableBox {

	BufferObject obj = new BufferObject(false);

	public DrawableBox() {

		float[] packedVector = { 0, 0, 0, 0, 0, -1, 1, 0, 0, 1, 0, 0, 0, -1, 1,
				1, 1, 1, 0, 0, 0, -1, 0, 1, 1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 1,
				0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1,
				0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, -1, 0, 0, 0, 1, 0, 0, 0,
				-1, 0, 1, 0, 1, 0, 1, 0, -1, 0, 1, 1, 0, 0, 1, 0, -1, 0, 0, 1,
				1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0,
				0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1,
				0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0,
				1, 0, 1, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0, 1,
				-1, 0, 0, 1, 1, 0, 1, 1, -1, 0, 0, 0, 1 };
		short[] ii = { 0, 1, 1,		1, 1, 2, 
					   2, 3, 3,		3, 3, 0, 
					   4, 5, 5,		5, 5, 6, 
					   6, 7, 7,		7, 7, 4, 
					   8, 9, 9,		9, 9, 10, 
					   10, 11, 11,	11, 11, 8,
					   12, 13, 13,	13, 13, 14, 
					   14, 15, 15,	15, 15, 12, 
					   16, 17, 17,	17, 17, 18, 
					   18, 19, 19,	19, 19, 16, 
					   20, 21, 21,	21, 21, 22, 
					   22, 23, 23,	23, 23, 20 
					   };

		for (int i = 0; i < packedVector.length; i += 8) {
			obj.addPosition(new Vector3(packedVector[i + 0],
					packedVector[i + 1], packedVector[i + 2]));

			obj.addNormal(new Vector3(packedVector[i + 3], packedVector[i + 4],
					packedVector[i + 5]));
			obj.addTextureCoord(new Vector2(packedVector[i + 6],
					packedVector[i + 7]));

		}

		for (int i = 0; i < ii.length; ++i) {
			obj.addIndex(ii[i]);
		}

		obj.buildBuffer();
	}

	/**
	 * Draw.
	 *
	 * @param shader
	 *            the shader
	 */
	public final void draw(final ShaderProgram shader, Vector3 min,
			Vector3 max) {
		//System.out.println(min.toString()+" : "+max.toString());
		shader.setMaterialAlpha(0.2f);

		GL11.glDisable(GL11.GL_CULL_FACE);
		Vector3 dim = new Vector3(max).subtract(min);
		shader.setModelMatrix(new Matrix().createScale(dim).multiply(new Matrix().createTranslation(min)));
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		obj.draw(shader);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		shader.setModelMatrix(new Matrix().identity());

		GL11.glEnable(GL11.GL_CULL_FACE);
		
		
	}


	
}
