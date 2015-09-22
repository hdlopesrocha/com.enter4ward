import com.enter4ward.lwjgl.BufferObject;
import com.enter4ward.lwjgl.DrawableSphere;
import com.enter4ward.lwjgl.LWJGLModel3D;
import com.enter4ward.lwjgl.Object3D;
import com.enter4ward.lwjgl.ShaderProgram;
import com.enter4ward.math.BoundingFrustum;
import com.enter4ward.math.BoundingSphere;
import com.enter4ward.math.ContainmentType;
import com.enter4ward.math.Group;
import com.enter4ward.math.IBufferObject;
import com.enter4ward.math.Material;
import com.enter4ward.math.Matrix;
import com.enter4ward.math.Quaternion;
import com.enter4ward.math.Space;
import com.enter4ward.math.Vector3;

// TODO: Auto-generated Javadoc
/**
 * The Class MyCar3D.
 */
public class MyCar3D extends Object3D {

	/** The time. */
	private float time;
	/** The Constant ROTATION. */
	private static final DrawableSphere sphere = new DrawableSphere();

	/**
	 * Instantiates a new my car3 d.
	 *
	 * @param position
	 *          the position
	 * @param model
	 *          the model
	 */
	public MyCar3D(Vector3 position, LWJGLModel3D model) {
		super(position, model);
		getAceleration().set(0);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.lwjgl.Object3D#update(float, com.enter4ward.math.Space)
	 */
	public void update(float delta_t, Space space) {
		super.update(delta_t, space);

		getRotation().multiply(
				new Quaternion().createFromAxisAngle(new Vector3(0, 1, 0), -delta_t))
				.normalize();

		time += delta_t;
		update(space);

	};

	/** The Constant TEMP_CENTER. */
	private static final Vector3 TEMP_CENTER = new Vector3();

	/** The Constant TEMP_MATRIX. */
	private static final Matrix TEMP_MATRIX = new Matrix();

	/** The Constant TEMP_ROTATION. */
	private static final Quaternion TEMP_ROTATION = new Quaternion();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.lwjgl.Object3D#draw(com.enter4ward.lwjgl.ShaderProgram,
	 * com.enter4ward.math.BoundingFrustum)
	 */
	@Override
	public void draw(final ShaderProgram program, final BoundingFrustum frustum) {
		final Matrix modelMatrix = getModelMatrix();
		final LWJGLModel3D model = (LWJGLModel3D) getModel();

		for (final Group g : model.getGroups()) {

			final BoundingSphere groupSphere = new BoundingSphere(g);
			groupSphere.transform(getRotation()).add(getPosition());

			if (frustum.contains(groupSphere) != ContainmentType.Disjoint) {
				for (final IBufferObject ib : g.getBuffers()) {
					BufferObject b = (BufferObject) ib;
					final BoundingSphere bufferSphere = new BoundingSphere(b);
					bufferSphere.transform(getRotation()).add(getPosition());

					if (frustum.contains(bufferSphere) != ContainmentType.Disjoint) {
						program.reset();
						b.bind(program);
						final Material m = b.getMaterial();
						final Matrix matrix = TEMP_MATRIX.set(Matrix.IDENTITY);

						if (m.getName().equals("c0")) {
							program.setDiffuseColor(
									(float) (Math.sin(time) + 1) / 2f,
									(float) (Math.cos(time * Math.E / 2) + 1) / 2f,
									(float) (Math.sin(time * Math.PI / 2)
											* Math.cos(time * Math.PI / 2) + 1) / 2f);
						} else if (g.getName().startsWith("w") && g.getName().length() == 2) {
							final Vector3 center = TEMP_CENTER.set(g).multiply(-1f);
							matrix.createTranslation(center);

							TEMP_ROTATION.createFromYawPitchRoll(0f, time * 8, 0f);
							matrix.transform(TEMP_ROTATION);
							center.multiply(-1f);
							matrix.translate(center);
						}

						program.setModelMatrix(matrix.multiply(modelMatrix));
						TheQuadExampleMoving.draws++;
						b.draw(program);
					}
				}
				program.setModelMatrix(Matrix.IDENTITY);
				sphere.draw(program, groupSphere);

			}
		}
	}
}
