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
	private float time;
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
		// System.out.println("update");
		time = delta_t;
		velocity.addMultiply(aceleration, delta_t);

		Ray ray = new Ray(getPosition(), new Vector3(velocity).multiply(delta_t));
		
		if(!space.handleRayCollisions(ray, this)){
			getPosition().addMultiply(velocity, delta_t);
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
	public boolean onObjectCollision(final Ray ray,final IBoundingSphere obj) {
		
		final float vel = velocity.length();
		final float delta = ray.getDirection().length();
		
		Float inter = null;
		Triangle triangle = null;
		
		if (!equals(obj)) {
			// System.out.println("*************** PSEUDO-COLLISION");
			// XXX - they hit and they are different
			final Object3D obj3d = (Object3D) obj;
			final Model3D model = (Model3D) obj3d.getModel();

			for (Group g : model.getGroups()) {
				for (BufferObject b : g.getBuffers()) {
					for (Triangle t : b.getTriangles()) {
						final Float i = ray.intersects(t);
						if(i!=null && (inter==null || i<inter)) {
							inter = i;
							triangle = t;	
						}
					}
				}
			}
			
			if (inter != null) {
				IVector3 n = triangle.getPlane().getNormal().normalize();
				getPosition().addMultiply(ray.getDirection(),inter).addMultiply(n, 0.01f);
				ray.getDirection().add(n.multiply(-ray.getDirection().dot(n)));
				velocity.set(ray.getDirection()).multiply(vel/delta);
				
				time -= inter/time;
				if(time>0){
					onObjectCollision(ray,obj3d);					
				}
				// R=V-2N(V.N)
				/*
				 float v_dot_n = ray.getDirection().dot(n);
				 IVector3 n2 = new Vector3(n).multiply(v_dot_n*2);
				 IVector3 v2n = new
				 Vector3(ray.getDirection()).subtract(n2).normalize(); float len = velocity.length();
				 velocity.set(v2n).multiply(len);
				 */
		
				return true;
			}

			
			
		}
		return false;
	}
}
