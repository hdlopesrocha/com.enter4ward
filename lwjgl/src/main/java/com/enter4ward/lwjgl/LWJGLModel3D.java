package com.enter4ward.lwjgl;

import java.io.FileNotFoundException;

import com.enter4ward.math.Group;
import com.enter4ward.math.IBufferBuilder;
import com.enter4ward.math.IBufferObject;
import com.enter4ward.math.IObject3D;
import com.enter4ward.math.ITextureLoader;
import com.enter4ward.math.Material;
import com.enter4ward.math.Matrix;
import com.enter4ward.math.Model3D;
import com.enter4ward.math.Quaternion;

// TODO: Auto-generated Javadoc
/**
 * The Class Model3D.
 */
public class LWJGLModel3D extends Model3D {

	/**
	 * Instantiates a new model3 d.
	 *
	 * @param geometry
	 *          the geometry
	 * @param scale
	 *          the scale
	 * @param builder
	 *          the builder
	 * @throws FileNotFoundException
	 *           the file not found exception
	 */

	
	
	public LWJGLModel3D(String filename, float scale, Quaternion quat, IBufferBuilder builder)
			throws FileNotFoundException {
		super(filename, scale, builder, quat);

		
		
		loadTextures();
	}

	public LWJGLModel3D(String filename, float scale, IBufferBuilder builder)
			throws FileNotFoundException {
		super(filename, scale, builder, new Quaternion().identity());

		loadTextures();
	}
	
	/**
	 * Load textures.
	 */
	public void loadTextures() {
		
		
		for (final Material m : materials.values()) {

			m.load(new ITextureLoader() {

				@Override
				public int load() {

					return new TextureLoader().loadTexture(m.filename);
				}
			});
		}
	}

	/**
	 * Draw.
	 *
	 * @param obj
	 *          the obj
	 * @param shader
	 *          the shader
	 * @param handler
	 *          the handler
	 */
	public void draw(IObject3D obj, ShaderProgram shader, DrawHandler handler) {
		for (Group g : groups) {
			for (IBufferObject ib : g.getBuffers()) {
				BufferObject b = (BufferObject) ib;

				b.bind(shader);
				Matrix mat = handler.onDraw(obj, g, b);
				if (mat != null) {
					shader.setModelMatrix(mat);
					b.draw(shader);
				}
			}
		}

	}
}
