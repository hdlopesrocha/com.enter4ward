package hidrogine.lwjgl;

import hidrogine.math.BoundingFrustum;
import hidrogine.math.BoundingSphere;
import hidrogine.math.ContainmentType;
import hidrogine.math.IBoundingSphere;
import hidrogine.math.IModel3D;
import hidrogine.math.IObject3D;
import hidrogine.math.IVector3;
import hidrogine.math.Matrix;
import hidrogine.math.Ray;
import hidrogine.math.RayCollisionHandler;
import hidrogine.math.Space;
import hidrogine.math.Triangle;
import hidrogine.math.Vector3;

public class Object3D extends IObject3D implements RayCollisionHandler {

	private IVector3 aceleration;
	private IVector3 velocity;
	private boolean collided;

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

	public void update(float delta_t, Space space) {
		//System.out.println("update");

		velocity.addMultiply(aceleration, delta_t);

		collided = false;

		IVector3 deltaV = new Vector3(velocity).multiply(delta_t);
		space.handleRayCollisions(new Ray(getPosition(), deltaV),this);
	
		if (!collided) {
			getPosition().add(deltaV);
		}
		
		update(space);
	}

	public void draw(ShaderProgram program, BoundingFrustum frustum) {
		program.reset();

		final Matrix matrix = getModelMatrix();
		final Model3D model = (Model3D) getModel();
		for (final Group g : model.getGroups()) {
			for (final BufferObject b : g.getBuffers()) {
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

	@Override
	public void onObjectCollision(final Ray ray, final IBoundingSphere obj) {
		if (!equals(obj)) {
          //  System.out.println("*************** PSEUDO-COLLISION");
// XXX - they hit and they are different
			final Object3D obj3d = (Object3D) obj;
			final Model3D model = (Model3D) obj3d.getModel();
		
			for(Group g : model.getGroups()){
				for(BufferObject b : g.getBuffers()){
					for(Triangle t : b.getTriangles()){
						final Float inter = ray.intersects(t);
						// XXX - aparently null
						if(inter!=null){
							IVector3 n = t.getPlane().getNormal();
						//	R=V-2N(V.N)
							float v_dot_n = ray.getDirection().dot(n);
							IVector3 n2 = new Vector3(n).multiply(v_dot_n*2);
							IVector3 v2n = new Vector3(ray.getDirection()).subtract(n2).normalize();
							float len = velocity.length();
							
								velocity.set(v2n).multiply(len);
									
						//	getPosition().set(new Vector3(ray.getDirection()).multiply(inter).add(ray.getPosition()));
							//velocity.set(0, 0, 0);
							System.out.println("BBAAAAAAAAAMMMMM!!!!!!!!!!!");
							collided = true;
							return;
						}
					}
				}
			}
		}
	}
}
