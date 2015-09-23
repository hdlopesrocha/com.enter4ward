package com.enter4ward.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Triangle.
 */
public class Triangle {

    /** The c. */
    private Vector3 a, b, c;

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
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Contains.
     *
     * @param point
     *            the point
     * @return true, if successful
     */
    public boolean contains(Vector3 point) {
        float thisArea = getArea();
        float area1 = new Triangle(a, b, point).getArea();
        float area2 = new Triangle(a, c, point).getArea();
        float area3 = new Triangle(b, c, point).getArea();
        return area1 + area2 + area3 < thisArea + 0.0001f;
    }

    /**
     * Gets the area.
     *
     * @return the area
     */
    public float getArea() {

        // 1. calculate vectors e1 and e2 (which can be of
        // type Node, too) from N[0]->N[1] and N[0]->N[2] :

        float e1x = b.getX() - a.getX();
        float e1y = b.getY() - a.getY();
        float e1z = b.getZ() - a.getZ();

        float e2x = c.getX() - a.getX();
        float e2y = c.getY() - a.getY();
        float e2z = c.getZ() - a.getZ();

        // 2. calculate e3 = e1 x e2 (cross product) :

        float e3x = e1y * e2z - e1z * e2y;
        float e3y = e1z * e2x - e1x * e2z;
        float e3z = e1x * e2y - e1y * e2x;

        // 3. the triangle area is the half length of the
        // normal vector:

        return (float) (0.5f * Math.sqrt(e3x * e3x + e3y * e3y + e3z * e3z));

    }

    /**
     * Gets the plane.
     *
     * @return the plane
     */
    public Plane getPlane() {
        return new Plane(a, b, c);
    }

private static final Vector3 TEMP_VECTOR = new Vector3();
private static final Vector3 TEMP_NORMAL = new Vector3();

    public Vector3 getNormal(){
        
        Vector3 ac = TEMP_VECTOR.set(c).subtract(a);
        return TEMP_NORMAL.set(b).subtract(a).cross(ac).normalize();
        
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
