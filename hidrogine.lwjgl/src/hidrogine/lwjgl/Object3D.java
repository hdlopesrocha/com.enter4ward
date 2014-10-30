package hidrogine.lwjgl;

import hidrogine.math.BoundingFrustum;
import hidrogine.math.BoundingSphere;
import hidrogine.math.ContainmentType;
import hidrogine.math.IModel3D;
import hidrogine.math.IObject3D;
import hidrogine.math.IVector3;
import hidrogine.math.Matrix;
import hidrogine.math.Vector3;

public class Object3D extends IObject3D{

	private IVector3 aceleration;
	private IVector3 velocity;
	
	
	public IVector3 getAceleration() {
		return aceleration;
	}

	public IVector3 getVelocity() {
		return velocity;
	}

	public Object3D(IVector3 position, IModel3D model) {
		super(position, model);
		aceleration = new Vector3().setY(-9.8f);
		velocity = new Vector3();
		
		// TODO Auto-generated constructor stub
	}

	public void update(float delta_t){
		velocity.add(new Vector3(aceleration).multiply(delta_t));
		getPosition().add(new Vector3(velocity).multiply(delta_t));
		
		if(getPosition().getY()<0){
			getPosition().setY(0);
			velocity.set(0, 0, 0);
		}
		
	}
	
	
	public void draw(ShaderProgram program, BoundingFrustum frustum){
		program.reset();

		final Matrix matrix = getModelMatrix();
		final Model3D model = (Model3D) getModel();
		for(final Group g : model.getGroups()){
			for(final BufferObject b : g.getBuffers()){
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
