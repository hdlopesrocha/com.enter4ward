import hidrogine.lwjgl.BufferObject;
import hidrogine.lwjgl.LWJGLModel3D;
import hidrogine.lwjgl.Object3D;
import hidrogine.lwjgl.ShaderProgram;
import hidrogine.math.BoundingFrustum;
import hidrogine.math.BoundingSphere;
import hidrogine.math.ContainmentType;
import hidrogine.math.Group;
import hidrogine.math.IBufferObject;
import hidrogine.math.Matrix;
import hidrogine.math.Vector3;

// TODO: Auto-generated Javadoc
/**
 * The Class MyObject3D.
 */
public class MyObject3D extends Object3D {

	/** The collided. */
	public boolean collided = false;

	/**
	 * Instantiates a new my object3 d.
	 *
	 * @param position
	 *          the position
	 * @param model
	 *          the model
	 */
	public MyObject3D(Vector3 position, LWJGLModel3D model) {
		super(position, model);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hidrogine.lwjgl.Object3D#draw(hidrogine.lwjgl.ShaderProgram,
	 * hidrogine.math.BoundingFrustum)
	 */
	@Override
	public void draw(ShaderProgram program, BoundingFrustum frustum) {

		program.reset();
		final Matrix modelMatrix = getModelMatrix();
		final LWJGLModel3D model = (LWJGLModel3D) getModel();

		program.setAmbientColor(collided ? 1 : 0, 0, 0);
		program.setModelMatrix(modelMatrix);

		for (final Group g : model.getGroups()) {
			final BoundingSphere groupSphere = new BoundingSphere(g);
			groupSphere.transform(getRotation()).add(getPosition());
			if (frustum.contains(groupSphere) != ContainmentType.Disjoint) {
				for (final IBufferObject ib : g.getBuffers()) {
					BufferObject b = (BufferObject) ib;
					b.bind(program);
					final BoundingSphere bufferSphere = new BoundingSphere(b);
					bufferSphere.transform(getRotation()).add(getPosition());
					if (frustum.contains(bufferSphere) != ContainmentType.Disjoint) {
						TheQuadExampleMoving.draws++;
						b.draw(program);
					}
				}
			}
		}
	}

}
