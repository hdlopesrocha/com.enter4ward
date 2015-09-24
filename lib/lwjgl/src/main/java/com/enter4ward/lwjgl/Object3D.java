package com.enter4ward.lwjgl;

import com.enter4ward.math.BoundingFrustum;
import com.enter4ward.math.BoundingSphere;
import com.enter4ward.math.ContainmentType;
import com.enter4ward.math.Group;
import com.enter4ward.math.IBufferObject;
import com.enter4ward.math.IModel3D;
import com.enter4ward.math.IObject3D;
import com.enter4ward.math.IntersectionInfo;
import com.enter4ward.math.Matrix;
import com.enter4ward.math.Ray;
import com.enter4ward.math.RayCollisionHandler;
import com.enter4ward.math.Space;
import com.enter4ward.math.Vector3;

public class Object3D extends IObject3D implements RayCollisionHandler {

	private Vector3 aceleration;
	private Vector3 velocity;
	

	public Vector3 getAceleration() {
		return aceleration;
	}

	public Vector3 getVelocity() {
		return velocity;
	}

	public Object3D(Vector3 position, IModel3D model) {
		super(position, model);
		aceleration = new Vector3().setY(-9.8f);
		velocity = new Vector3();
		// TODO Auto-generated constructor stub
	}

	private static final Ray TEMP_RAY = new Ray(new Vector3(),new Vector3());

	public void update(float delta_t, Space space) {

		velocity.addMultiply(aceleration, delta_t);
		int i =0;
		while (delta_t > 0 && i++ < 5) {
			TEMP_RAY.getPosition().set(getPosition());
			TEMP_RAY.getDirection().set(velocity).multiply(delta_t);
			IntersectionInfo inter = space.handleRayCollisions(TEMP_RAY, this);
			if (inter == null) {
				getPosition().add(TEMP_RAY.getDirection());
				break;
			} else {
				float maxShift = TEMP_RAY.getDirection().length();

				Vector3 normal = inter.triangle.getNormal();
				// XXX da barraca quando comeca a subir
				getPosition().addMultiply(TEMP_RAY.getDirection(), 0.9f*inter.distance);
			
				// slide direction
				velocity.slide(normal);
				delta_t -= delta_t* inter.distance / maxShift;
			}
		}

		update(space);
	}


	

	private static BoundingSphere TEMP_SPHERE_DRAW = new BoundingSphere();

	public void draw(ShaderProgram program, BoundingFrustum frustum) {
		program.reset();

		final Matrix matrix = getModelMatrix();
		final LWJGLModel3D model = (LWJGLModel3D) getModel();
		for (final Group g : model.getGroups()) {
			for (final IBufferObject ib : g.getBuffers()) {
				BufferObject b = (BufferObject) ib;
				final BoundingSphere sph = TEMP_SPHERE_DRAW.set(b);
				sph.add(matrix.getTranslation());
				if (frustum.contains(sph) != ContainmentType.Disjoint) {
					b.bind(program);
					program.setModelMatrix(matrix);
					b.draw(program);
				}
			}
		}
	}

	// O facto de ter colidido aqui, nao faz com que tenha realmente colidido, um objecto mais proximo pode estar a frente

	@Override
	public IntersectionInfo onObjectCollision(Space space, Ray ray, Object obj) {
		IntersectionInfo collided = null;

		if (obj != this) {
			Object3D obj3D = (Object3D) obj;
			BoundingSphere sph = obj3D.getBoundingSphere();
			Float inter = ray.intersects(sph);

			if (inter != null && inter <= 1) {
				return obj3D.closestTriangle(ray);
			}
		}
		return collided;
	}
}
