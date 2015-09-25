package com.enter4ward.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Plane.
 */
public class Plane {

    /** The normal. */
    private Vector3 normal;

    /** The distance. */
    private float distance;

    /**
     * Instantiates a new plane.
     */
    public Plane() {
        normal = new Vector3();
    }

    /**
     * Instantiates a new plane.
     *
     * @param normal
     *            the normal
     * @param d
     *            the d
     */
    public Plane(Vector3 normal, float d) {
        this.normal = normal;
        this.distance = d;
    }

    private static final Vector3 TEMP_VECTOR = new Vector3();

    /**
     * Instantiates a new plane.
     *
     * @param a
     *            the a
     * @param b
     *            the b
     * @param c
     *            the c
     */
    public Plane(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 ac = TEMP_VECTOR.set(c).subtract(a);
        this.normal = new Vector3(b).subtract(a);
        this.normal.cross(ac).normalize();
        distance = -normal.dot(a);
        // Ax + By + Cz + d = 0

        // NOT NECESSARY
        // float len = normal.length();
        // normal.divide(len);
        // distance /= len;

        // normal.normalize();

    }

    /**
     * Instantiates a new plane.
     *
     * @param a
     *            the a
     * @param b
     *            the b
     * @param c
     *            the c
     * @param d
     *            the d
     */
    public Plane(float a, float b, float c, float d) {
        this(new Vector3(a, b, c), d);
    }

    public Plane set(float a, float b, float c, float d) {
        this.normal.set(a, b, c);
        this.distance = d;
        return this;
    }

    /*
     * Plane(Vector4 value) {this = Plane(Vector3(value.X, value.Y, value.Z),
     * value.W); } float dot(Vector4 value) { return ((((Normal.X * value.X) +
     * (Normal.Y * value.Y)) + (Normal.Z * value.Z)) + (D * value.W)); }
     */
    /**
     * Dot coordinate.
     *
     * @param vCorner
     *            the value
     * @return the float
     */
    public float dotCoordinate(Vector3 vCorner) {
        return ((((normal.getX() * vCorner.getX()) + (normal.getY() * vCorner
                .getY())) + (normal.getZ() * vCorner.getZ())) + distance);
    }

    /**
     * Gets the normal.
     *
     * @return the normal
     */
    public Vector3 getNormal() {
        return normal;
    }

    /**
     * Gets the distance.
     *
     * @return the distance
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Dot normal.
     *
     * @param direction
     *            the value
     * @return the float
     */
    public float dotNormal(Vector3 direction) {
        return (((normal.getX() * direction.getX()) + (normal.getY() * direction
                .getY())) + (normal.getZ() * direction.getZ()));
    }

    /**
     * Normalize.
     */
    public void normalize() {
        float factor = 1f / normal.length();
        normal.normalize();
        distance *= factor;
        toString();
    }

    /**
     * Equals.
     *
     * @param other
     *            the other
     * @return true, if successful
     */
    public boolean equals(Object obj) {
        if (obj instanceof Plane) {
            Plane other = (Plane) obj;
            return ((normal == other.normal) && (distance == other.distance));
        }
        return false;
    }

    /**
     * Intersects.
     *
     * @param box
     *            the box
     * @return the plane intersection type
     */
    public PlaneIntersectionType intersects(BoundingBox box) {
        return box.intersects(this);
    }

    /**
     * Intersects.
     *
     * @param frustum
     *            the frustum
     * @return the plane intersection type
     */
    public PlaneIntersectionType intersects(BoundingFrustum frustum) {
        return frustum.intersects(this);
    }

    /**
     * Intersects.
     *
     * @param sphere
     *            the sphere
     * @return the plane intersection type
     */
    public PlaneIntersectionType intersects(BoundingSphere sphere) {
        return sphere.intersects(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "{{Notmal:" + normal.toString() + " D:" + distance + "}}";

    }

}
