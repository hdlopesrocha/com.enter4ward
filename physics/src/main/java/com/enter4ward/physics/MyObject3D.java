import com.enter4ward.lwjgl.BufferObject;
import com.enter4ward.lwjgl.LWJGLModel3D;
import com.enter4ward.lwjgl.Object3D;
import com.enter4ward.lwjgl.ShaderProgram;
import com.enter4ward.math.BoundingFrustum;
import com.enter4ward.math.BoundingSphere;
import com.enter4ward.math.ContainmentType;
import com.enter4ward.math.Group;
import com.enter4ward.math.IBufferObject;
import com.enter4ward.math.Matrix;
import com.enter4ward.math.Vector3;

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
	 * @see com.enter4ward.lwjgl.Object3D#draw(com.enter4ward.lwjgl.ShaderProgram,
	 * com.enter4ward.math.BoundingFrustum)
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
