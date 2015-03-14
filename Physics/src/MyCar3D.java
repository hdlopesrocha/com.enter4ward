import hidrogine.lwjgl.BufferObject;
import hidrogine.lwjgl.DrawableSphere;
import hidrogine.lwjgl.Material;
import hidrogine.lwjgl.Model3D;
import hidrogine.lwjgl.Object3D;
import hidrogine.lwjgl.ShaderProgram;
import hidrogine.math.BoundingFrustum;
import hidrogine.math.BoundingSphere;
import hidrogine.math.ContainmentType;
import hidrogine.math.Group;
import hidrogine.math.IBufferObject;
import hidrogine.math.Matrix;
import hidrogine.math.Space;
import hidrogine.math.Vector3;

public class MyCar3D extends Object3D {

	private float time;
	/** The Constant ROTATION. */
	private static final DrawableSphere sphere = new DrawableSphere();

	public MyCar3D(Vector3 position, Model3D model) {
		super(position, model);
		getAceleration().set(0);
		// TODO Auto-generated constructor stub
	}

	public void update(float delta_t, Space space) {
		super.update(delta_t,space);
		time += delta_t;
		update(space);

	};
	
	private static final Vector3 TEMP_CENTER = new Vector3();
	private static final Matrix TEMP_MATRIX = new Matrix();
	private static final Matrix TEMP_ROTATION = new Matrix();

	
	
	@Override
	public void draw(final ShaderProgram program, final BoundingFrustum frustum) {
		final Matrix modelMatrix = getModelMatrix();
		final Model3D model = (Model3D) getModel();

		for (final Group g : model.getGroups()) {

			final BoundingSphere groupSphere = new BoundingSphere(g);
			groupSphere.transform(getRotation()).add(getPosition());

			if (frustum.contains(groupSphere) != ContainmentType.Disjoint) {
				for (final IBufferObject ib : g.getBuffers()) {
					BufferObject b = (BufferObject) ib;
					final BoundingSphere bufferSphere = new BoundingSphere(b);
					bufferSphere.transform(getRotation())
							.add(getPosition());

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
						}
						else if (g.getName().startsWith("w")
								&& g.getName().length() == 2) {
							final Vector3 center = TEMP_CENTER.set(g)
									.multiply(-1f);
							matrix.createTranslation(center);
							matrix.multiply(TEMP_ROTATION.createRotationX(time * 8));
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
