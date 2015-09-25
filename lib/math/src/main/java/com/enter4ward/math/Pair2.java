package com.enter4ward.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Triangle.
 */
public class Pair2 {
	private Vector3 u;
	private Vector3 v;
	
	// ===================================
	// [2] Projecting 3D Points on a Plane
	// ===================================
	
	public Pair2(Vector3 n){
		if(n.getX()>n.getY()){
			u = new Vector3(n.getZ(),0,-n.getX()).divide((float)Math.sqrt(n.getX()*n.getX()+n.getZ()*n.getZ()));
		}else{
			u = new Vector3(0,n.getZ(),-n.getY()).divide((float)Math.sqrt(n.getY()*n.getY()+n.getZ()*n.getZ()));		
		}
		v = new Vector3(n).cross(u);
		
		
	}
	
	public Vector2 transform(Vector3 point){
		return new Vector2(point.dot(u),point.dot(v));
	}
	
	public Vector3 transform(Vector2 point, Plane plane){
		return new Vector3().addMultiply(u, point.getX()).addMultiply(v, point.getY()).addMultiply(plane.getNormal(), -plane.getDistance());
	}
	
   
}
