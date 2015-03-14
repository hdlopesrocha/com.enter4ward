package hidrogine.lwjgl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import hidrogine.math.IBufferBuilder;
import hidrogine.math.Group;
import hidrogine.math.IBufferObject;
import hidrogine.math.IObject3D;
import hidrogine.math.ITextureLoader;
import hidrogine.math.Material;
import hidrogine.math.Matrix;
import hidrogine.math.Vector3;

import org.lwjgl.opengl.GL11;

// TODO: Auto-generated Javadoc
/**
 * The Class Model3D.
 */
public class Model3D extends XModel3D {

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Model#draw(hidrogine.lwjgl.ShaderProgram)
	 */
	/** The box. */
	private static DrawableBox box = new DrawableBox();

	/**
	 * Instantiates a new model3 d.
	 *
	 * @param materials
	 *          the materials
	 * @param geometry
	 *          the geometry
	 * @param scale
	 *          the scale
	 * @throws FileNotFoundException 
	 */
	
	public Model3D(String materials, String geometry, float scale,
			IBufferBuilder builder) throws FileNotFoundException {
		super(new FileInputStream(materials), new FileInputStream(geometry), scale, builder);
		

		
		loadTextures();
	}

	public void loadTextures() {
		for (final Material m : materials.values()) {
			m.load(new ITextureLoader() {

				@Override
				public int load() {
					return TextureLoader.loadTexture(m.filename);
				}
			});
		}
	}

	/**
	 * Draw.
	 *
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

	private static final Vector3 TEMP_MIN = new Vector3();
	private static final Vector3 TEMP_MAX = new Vector3();

	/**
	 * Draw boxs.
	 *
	 * @param shader
	 *          the shader
	 */
	public void drawBoxs(ShaderProgram shader) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		for (Group g : groups) {
			box.draw(shader, TEMP_MIN.set(g).subtract(g.getRadius()), TEMP_MAX.set(g)
					.add(g.getRadius()));
		}
	}

}
