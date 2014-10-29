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
	public void draw(ShaderProgram program, BoundingFrustum frustum){
		Matrix matrix = getModelMatrix();
		Model3D model = (Model3D) getModel();
		for(Group g : model.getGroups()){
			for(BufferObject b : g.getBuffers()){
				BoundingSphere sph = new BoundingSphere(b);
				sph.getCenter().add(matrix.getTranslation());
				if (frustum.contains(sph) != ContainmentType.Disjoint) {
					b.bind(program);
					program.setModelMatrix(matrix);	
					TheQuadExampleMoving.draws++;
					if(collided){
						program.setAmbientColor(1, 0, 0);
					}
					b.draw(program);
					program.setModelMatrix(IDENTITY);
				}
			}
		}		
	}
	
}
