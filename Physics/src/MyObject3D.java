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

	private static final Matrix IDENTITY = new Matrix().identity();

	public MyObject3D(IVector3 position, Model3D model) {
		super(position, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(ShaderProgram program, BoundingFrustum frustum) {
		Matrix matrix = getModelMatrix();
		Model3D model = (Model3D) getModel();

		if (collided) {
			program.setAmbientColor(1, 0, 0);
		} else {
			program.setAmbientColor(0, 0, 0);
		}

		for (Group g : model.getGroups()) {
			final BoundingSphere groupSphere = new BoundingSphere(g);
			groupSphere.getCenter().transform(getRotation());

			if (frustum.contains(groupSphere) != ContainmentType.Disjoint) {

				for (BufferObject b : g.getBuffers()) {
					final BoundingSphere bufferSphere = new BoundingSphere(b);
					bufferSphere.getCenter().transform(getRotation());

					if (frustum.contains(bufferSphere) != ContainmentType.Disjoint) {
						b.bind(program);
						program.setModelMatrix(matrix);
						TheQuadExampleMoving.draws++;
						b.draw(program);
						program.setModelMatrix(IDENTITY);
					}
				}
			}
		}

		program.setAmbientColor(0, 0, 0);

	}

}
