package com.enter4ward.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Vector3.
 */
public class Vector3 {

	/** The x. */
	private float x;

	/** The y. */
	private float y;

	/** The y. */
	private float z;

	/**
	 * Instantiates a new vector3.
	 */
	public Vector3() {
		x = 0;
		y = 0;
		z = 0;
	}

	/**
	 * Instantiates a new vector3.
	 *
	 * @param xx
	 *            the x
	 * @param yy
	 *            the y
	 * @param zz
	 *            the zz
	 */
	public Vector3(final float xx, final float yy, final float zz) {
		x = xx;
		y = yy;
		z = zz;
	}

	/**
	 * Instantiates a new vector3.
	 *
	 * @param vec
	 *            the vec
	 */
	public Vector3(final Vector3 vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
	}

	/**
	 * Instantiates a new vector3.
	 *
	 * @param value
	 *            the value
	 */
	public Vector3(float value) {
		x = value;
		y = value;
		z = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#getZ()
	 */

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public final float getZ() {
		return z;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#setZ(float)
	 */
	/**
	 * Sets the z.
	 *
	 * @param zz
	 *            the zz
	 * @return the vector3
	 */
	public final Vector3 setZ(final float zz) {
		z = zz;

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#getX()
	 */
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public final float getX() {
		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#setX(float)
	 */
	/**
	 * Sets the x.
	 *
	 * @param xx
	 *            the xx
	 * @return the vector3
	 */
	public Vector3 setX(final float xx) {
		x = xx;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#y
	 */
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public final float getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#setY(float)
	 */
	/**
	 * Sets the y.
	 *
	 * @param yy
	 *            the yy
	 * @return the vector3
	 */
	public final Vector3 setY(final float yy) {
		y = yy;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#add(com.enter4ward.math.Vector3)
	 */
	/**
	 * Adds the.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 add(final Vector3 vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	/**
	 * Adds the multiply.
	 *
	 * @param vec
	 *            the vec
	 * @param factor
	 *            the factor
	 * @return the vector3
	 */
	public final Vector3 addMultiply(final Vector3 vec, float factor) {
		x += vec.x * factor;
		y += vec.y * factor;
		z += vec.z * factor;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#addX(float)
	 */
	/**
	 * Adds the x.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 addX(final float vec) {
		x += vec;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#addY(float)
	 */
	/**
	 * Adds the y.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 addY(final float vec) {
		y += vec;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#addZ(float)
	 */
	/**
	 * Adds the z.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 addZ(final float vec) {
		z += vec;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#subtract(com.enter4ward.math.Vector3)
	 */
	/**
	 * Subtract.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 subtract(final Vector3 vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	/**
	 * Subtract.
	 *
	 * @param vec
	 *            the vec
	 * @return the vector3
	 */
	public final Vector3 subtract(final float vec) {
		x -= vec;
		y -= vec;
		z -= vec;
		return this;
	}

	/**
	 * Adds the.
	 *
	 * @param vec
	 *            the vec
	 * @return the vector3
	 */
	public final Vector3 add(final float vec) {
		x += vec;
		y += vec;
		z += vec;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#multiply(com.enter4ward.math.Vector3)
	 */
	/**
	 * Multiply.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 multiply(final Vector3 vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	/**
	 * Sets the.
	 *
	 * @param posX
	 *            the pos x
	 * @param posY
	 *            the pos y
	 * @param posZ
	 *            the pos z
	 * @return the i vector3
	 */
	public Vector3 set(float posX, float posY, float posZ) {
		x = posX;
		y = posY;
		z = posZ;
		return this;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Vector3) {
			Vector3 vec = (Vector3) arg0;
			return x == vec.getX() && y == vec.getY() && z == vec.getZ();
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		Float f = x * 3 + y * 5 + z * 7;
		return f.hashCode();
	}

	/**
	 * Sets the.
	 *
	 * @param pos
	 *            the pos
	 * @return the i vector3
	 */
	public Vector3 set(float pos) {
		x = y = z = pos;
		return this;
	}

	/**
	 * Multiply.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 multiply(final float vec) {
		x *= vec;
		y *= vec;
		z *= vec;
		return this;
	}

	/**
	 * Normalize.
	 *
	 * @return the i vector3
	 */
	public Vector3 normalize() {
		return divide(length());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#divide(com.enter4ward.math.Vector3)
	 */
	/**
	 * Divide.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 divide(final Vector3 vec) {
		x /= vec.x;
		y /= vec.y;
		z /= vec.z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#divide(float)
	 */
	/**
	 * Divide.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public final Vector3 divide(final float vec) {
		x /= vec;
		y /= vec;
		z /= vec;
		return this;
	}

	/**
	 * Sets the.
	 *
	 * @param vec
	 *            the vec
	 * @return the i vector3
	 */
	public Vector3 set(Vector3 vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
		return this;
	}

	/**
	 * Dot.
	 *
	 * @param v
	 *            the v
	 * @return the float
	 */
	public final float dot(final Vector3 v) {
		return x * v.x + y * v.y + z * v.z;
	}

	/**
	 * Cross.
	 *
	 * @param v
	 *            the v
	 * @return the i vector3
	 */
	public final Vector3 cross(final Vector3 v) {
		float cx = y * v.z - v.y * z;
		float cy = v.x * z - x * v.z;
		float cz = x * v.y - v.x * y;
		x = cx;
		y = cy;
		z = cz;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#length()
	 */
	/**
	 * Length.
	 *
	 * @return the double
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Length squared.
	 *
	 * @return the float
	 */
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#distance(com.enter4ward.math.Vector3)
	 */
	/**
	 * Distance.
	 *
	 * @param vec
	 *            the vec
	 * @return the double
	 */
	public double distance(Vector3 vec) {
		float dx = x - vec.x;
		float dy = y - vec.y;
		float dz = z - vec.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	/**
	 * Distance.
	 *
	 * @param vec
	 *            the vec
	 * @return the double
	 */
	public double distanceSquared(Vector3 vec) {
		float dx = x - vec.x;
		float dy = y - vec.y;
		float dz = z - vec.z;
		return dx * dx + dy * dy + dz * dz;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enter4ward.math.Vector3#toString()
	 */
	public String toString() {
		return x + "," + y + "," + z;
	}

	/**
	 * Max.
	 *
	 * @param value2
	 *            the value2
	 * @return the i vector3
	 */
	public Vector3 max(Vector3 value2) {

		x = Math.max(x, value2.x);
		y = Math.max(y, value2.y);
		z = Math.max(z, value2.z);
		return this;
	}

	/**
	 * Min.
	 *
	 * @param min2
	 *            the min2
	 * @return the i vector3
	 */
	public Vector3 min(Vector3 min2) {

		x = Math.min(x, min2.x);
		y = Math.min(y, min2.y);
		z = Math.min(z, min2.z);
		return this;
	}

	/**
	 * Transform.
	 *
	 * @param rotation
	 *            the rotation
	 * @return the i vector3
	 */

	public Vector3 transform(Quaternion rotation) {
		float tx = 2 * (rotation.getY() * z - rotation.getZ() * y);
		float ty = 2 * (rotation.getZ() * x - rotation.getX() * z);
		float tz = 2 * (rotation.getX() * y - rotation.getY() * x);
		x += tx * rotation.getW() + (rotation.getY() * tz - rotation.getZ() * ty);
		y += ty * rotation.getW() + (rotation.getZ() * tx - rotation.getX() * tz);
		z += tz * rotation.getW() + (rotation.getX() * ty - rotation.getY() * tx);
		return this;
	}

	/**
	 * Transform.
	 *
	 * @param matrix
	 *            the matrix
	 * @return the i vector3
	 */
	public Vector3 transform(Matrix matrix) {

		float tx = (x * matrix.M[0]) + (y * matrix.M[4]) + (z * matrix.M[8]) + matrix.M[12];

		float ty = (x * matrix.M[1]) + (y * matrix.M[5]) + (z * matrix.M[9]) + matrix.M[13];

		float tz = (x * matrix.M[1]) + (y * matrix.M[6]) + (z * matrix.M[10]) + matrix.M[14];

		x = tx;
		y = ty;
		z = tz;

		return this;
	}

	/**
	 * Reflect.
	 *
	 * @param normal
	 *            the normal
	 * @return the vector3
	 */
	public Vector3 reflect(Vector3 normal) {

		return addMultiply(normal, -2.0f * dot(normal));
	}


	public Vector3 slide(Vector3 normal) {		
		return addMultiply(normal, -dot(normal));
	}

	/**
	 * Clear.
	 *
	 * @return the vector3
	 */
	public Vector3 clear() {
		x = y = z = 0;
		return this;
	}

	private static final Vector3 TEMP_VEC1 = new Vector3();
	private static final Vector3 TEMP_VEC2 = new Vector3();
	private static final Vector3 TEMP_VEC3 = new Vector3();

	/**
	 * Intersection point.
	 *
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @param c
	 *            the c
	 * @return the i vector3
	 */
	public Vector3 intersectionPoint(Plane a, Plane b, Plane c) {
		// Formula used
		// d1 ( N2 * N3 ) + d2 ( N3 * N1 ) + d3 ( N1 * N2 )
		// P =
		// -------------------------------------------------------------------------
		// N1 . ( N2 * N3 )
		//
		// Note: N refers to the normal, d refers to the displacement. '.' means
		// dot product. '*' means cross product
		float f = -a.getNormal().dot(TEMP_VEC1.set(b.getNormal()).cross(c.getNormal()));
		set(b.getNormal()).cross(c.getNormal()).multiply(a.getDistance());
		Vector3 v2 = TEMP_VEC2.set(c.getNormal()).cross(a.getNormal()).multiply(b.getDistance());
		Vector3 v3 = TEMP_VEC3.set(a.getNormal()).cross(b.getNormal()).multiply(c.getDistance());

		return add(v2).add(v3).divide(f);
	}

}
