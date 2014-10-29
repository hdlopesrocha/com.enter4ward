package hidrogine.lwjgl;

import hidrogine.math.BoundingFrustum;
import hidrogine.math.BoundingSphere;
import hidrogine.math.ContainmentType;
import hidrogine.math.IModel3D;
import hidrogine.math.IObject3D;
import hidrogine.math.IVector3;
import hidrogine.math.Matrix;

public class Object3D extends IObject3D{

	public Object3D(IVector3 position, IModel3D model) {
		super(position, model);
		// TODO Auto-generated constructor stub
	}

	public void draw(ShaderProgram program, BoundingFrustum frustum){
		program.reset();

		final Matrix matrix = getModelMatrix();
		final Model3D model = (Model3D) getModel();
		for(Group g : model.getGroups()){
			for(BufferObject b : g.getBuffers()){
				final BoundingSphere sph = new BoundingSphere(b);
				sph.getCenter().add(matrix.getTranslation());
				if (frustum.contains(sph) != ContainmentType.Disjoint) {
					b.bind(program);
					program.setModelMatrix(matrix);	
					b.draw(program);
				}
			}
		}		
	}
	
}
