package com.enter4ward.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Triangle.
 */
public class Triangle {

	public Vector3 getA() {
		Pair2 pair = new Pair2(plane.getNormal());
		return pair.transform(a, plane);
	}

	public Vector3 getB() {
		Pair2 pair = new Pair2(plane.getNormal());
		return pair.transform(b, plane);

	}

	public Vector3 getC() {
		Pair2 pair = new Pair2(plane.getNormal());
		return pair.transform(c, plane);

	}

	/** The c. */
	private Vector2 a, b, c;

	private Plane plane;

	/**
	 * Instantiates a new triangle.
	 *
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @param c
	 *            the c
	 */
	public Triangle(Vector3 a, Vector3 b, Vector3 c) {
		this.plane = new Plane(a, b, c);
		Pair2 pair = new Pair2(plane.getNormal());
		this.a = pair.transform(a);
		this.b = pair.transform(b);
		this.c = pair.transform(c);

		System.out.println(a.toString() + "->" + pair.transform(this.a, this.plane).toString());

	}

	public Vector3 getNormal() {

		return plane.getNormal();

	}

	/**
	 * Contains.
	 *
	 * @param point
	 *            the point
	 * @return true, if successful
	 */
	public boolean contains(Vector3 point) {
		Vector3 aa = getA();
		Vector3 bb = getB();
		Vector3 cc = getC();

		float thisArea = getArea();
		float area1 = new Triangle(aa, bb, point).getArea();
		float area2 = new Triangle(aa, cc, point).getArea();
		float area3 = new Triangle(bb, cc, point).getArea();
		return area1 + area2 + area3 < thisArea + 0.001f;
	}

	/**
	 * Gets the area.
	 *
	 * @return the area
	 */
	public float getArea() {
		float abx = b.getX() - a.getX();
		float aby = b.getY() - a.getY();
		float acx = c.getX() - a.getX();
		float acy = c.getY() - a.getY();
		return 0.5f * (aby * acx - abx * acy);
	}

	// returns an interval of normalized time when an intersection existed 
	public Vector2 intersection(BoundingSphere sphere, Vector3 d) {
		// ===============================
		// [3.1] Swept Sphere Versus Plane
		// ===============================

		Vector3 n = plane.getNormal();
		Vector2 i = null;
		float d_dot_n = n.dot(d);
		float b_dot_n = n.getX() * sphere.getX() + n.getY() * sphere.getY() + n.getZ() * sphere.getZ();
		float r = sphere.getRadius();
		float dist = b_dot_n + plane.getDistance();

		
		// not moving towards the plane
		if (d_dot_n < 0) {
			return null;
		}
		// parallel motion
		else if (d_dot_n == 0) {
			// touching the plane during all motion
			if (dist <= r) {
				i =  new Vector2(0f, 1f);
			}
			// not touching the plane
			else {
				return null;
			}
		} else {
			float t1 = (-r - dist) / d_dot_n;
			float t2 = (r - dist) / d_dot_n;
			if (t2 > t1) {
				float t3 = t1;
				t1 = t2;
				t2 = t3;

			}
			if (t1 > 1 || t2 < 0) {
				return null;
			} else {
				if (t1 < 0)
					t1 = 0;
				if (t2 > 1)
					t2 = 1;
				i = new Vector2(t1, t2);
			}

		}
		
		// =================================
		// [3.2] Swept Circle Versus Polygon
		// =================================
	
		return i;
	}

	/**
	 * Gets the plane.
	 *
	 * @return the plane
	 */
	public Plane getPlane() {
		return plane;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{a:" + a + ", b:" + b + ", " + c + "}";
	}
}
