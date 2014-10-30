import hidrogine.lwjgl.BufferObject;
import hidrogine.lwjgl.Group;
import hidrogine.lwjgl.Model3D;
import hidrogine.lwjgl.Object3D;
import hidrogine.lwjgl.ShaderProgram;
import hidrogine.math.BoundingFrustum;
import hidrogine.math.BoundingSphere;
import hidrogine.math.ContainmentType;
import hidrogine.math.IVector3;
import hidrogine.math.Matrix;

public class MyObject3D extends Object3D {

	public boolean collided = false;

	public MyObject3D(IVector3 position, Model3D model) {
		super(position, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(ShaderProgram program, BoundingFrustum frustum) {

		program.reset();
		final Matrix modelMatrix = getModelMatrix();
		final Model3D model = (Model3D) getModel();

		program.setAmbientColor(collided? 1:0, 0, 0);
		program.setModelMatrix(modelMatrix);

		for (final Group g : model.getGroups()) {
			final BoundingSphere groupSphere = new BoundingSphere(g);
			groupSphere.getCenter().transform(getRotation()).add(getPosition());
			if (frustum.contains(groupSphere) != ContainmentType.Disjoint) {
				for (final BufferObject b : g.getBuffers()) {
					b.bind(program);
					final BoundingSphere bufferSphere = new BoundingSphere(b);
					bufferSphere.getCenter().transform(getRotation()).add(getPosition());
					if (frustum.contains(bufferSphere) != ContainmentType.Disjoint) {
						TheQuadExampleMoving.draws++;
						b.draw(program);
					}
				}
			}
		}
	}

}