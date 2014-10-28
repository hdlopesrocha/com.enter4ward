package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Plane.
 */
public class Plane {

    /** The normal. */
    private IVector3 normal;

    /** The distance. */
    private float distance;

    /**
     * Instantiates a new plane.
     */
    public Plane() {
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
        Vector3 ab = (Vector3) new Vector3(b).subtract(a);
        Vector3 ac = (Vector3) new Vector3(c).subtract(a);
        normal = ab.cross(ac);
        this.distance = -(normal.dot(a));
        normal.normalize();

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
    public float dotCoordinate(IVector3 vCorner) {
        return ((((normal.getX() * vCorner.getX()) + (normal.getY() * vCorner
                .getY())) + (normal.getZ() * vCorner.getZ())) + distance);
    }

    /**
     * Gets the normal.
     *
     * @return the normal
     */
    public IVector3 getNormal() {
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
     * @param value
     *            the value
     * @return the float
     */
    public float dotNormal(Vector3 value) {
        return (((normal.getX() * value.getX()) + (normal.getY() * value.getY())) + (normal
                .getZ() * value.getZ()));
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
    public boolean equals(Plane other) {
        return ((normal == other.normal) && (distance == other.distance));
    }

    /**
     * Intersects.
     *
     * @param box
     *            the box
     * @return the plane intersection type
     */
    public PlaneIntersectionType intersects(IBoundingBox box) {
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
    public PlaneIntersectionType intersects(IBoundingSphere sphere) {
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
