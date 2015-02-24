package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class BoundingFrustum.
 */
public class BoundingFrustum {

    /**
     * Intersects.
     *
     * @param plane
     *            the plane
     * @return the plane intersection type
     */
    public PlaneIntersectionType intersects(Plane plane) {
        // TODO Auto-generated method stub
        return null;
    }

    /** The planes. */
    private Plane[] planes = new Plane[6];

    /** The corners. */
    private Vector3[] corners = new Vector3[8];

    /** The Constant CornerCount. */
    public static final int CornerCount = 8;

    /**
     * Instantiates a new bounding frustum.
     */
    public BoundingFrustum() {

    }

    /**
     * Instantiates a new bounding frustum.
     *
     * @param value
     *            the value
     */

    public BoundingFrustum(Matrix value) {
        createPlanes(value);

        createCorners();

    }

    /**
     * Bottom.
     *
     * @return the plane
     */
    public Plane Bottom() {
        return planes[0];
    }

    /**
     * Far.
     *
     * @return the plane
     */
    public Plane Far() {
        return planes[1];
    }

    /**
     * Left.
     *
     * @return the plane
     */
    public Plane Left() {
        return planes[2];
    }

    /**
     * Sets the matrix.
     *
     * @param value
     *            the value
     */
    public void setMatrix(Matrix value) {
        createPlanes(value); // FIXME: The odds are the planes will be used a
                             // lot
        // more often than the matrix
        createCorners(); // is updated, so this should help performance. I hope
                         // ;)
    }

    /**
     * Near.
     *
     * @return the plane
     */
    public Plane Near() {
        return planes[4];
    }

    /**
     * Right.
     *
     * @return the plane
     */
    public Plane Right() {
        return planes[3];
    }

    /**
     * Top.
     *
     * @return the plane
     */
    public Plane Top() {
        return planes[5];
    }

    // MONOGAME
    /**
     * Contains.
     *
     * @param box
     *            the box
     * @return the containment type
     */
    public ContainmentType contains(BoundingBox box) {
        Boolean intersects = false;
        for (int i = 0; i < 6; ++i) {
            PlaneIntersectionType planeIntersectionType = box
                    .intersects(this.planes[i]);
            if (planeIntersectionType == PlaneIntersectionType.Front) {

                return ContainmentType.Disjoint;
            }
            if (planeIntersectionType == PlaneIntersectionType.Intersecting) {
                intersects = true;
            }

        }
        return intersects ? ContainmentType.Intersects
                : ContainmentType.Contains;
    }

    /**
     * Contains.
     *
     * @param sphere
     *            the sphere
     * @return the containment type
     */
    public ContainmentType contains(BoundingSphere sphere) {
        float val;
        final Vector3 center = sphere.getCenter();
        ContainmentType result = ContainmentType.Contains;

        for (int i = 0; i < 6; ++i) {
            val = PlaneHelper.perpendicularDistance(center, planes[i]);
            if (val < -sphere.getRadius())
                return ContainmentType.Disjoint;
            else if (val < sphere.getRadius())
                result = ContainmentType.Intersects;
        }
        return result;

    }

    /**
     * Contains.
     *
     * @param point
     *            the point
     * @return the containment type
     */
    public ContainmentType contains(Vector3 point) {
        // If a point is on the POSITIVE side of the plane, then the point is
        // not contained within the frustum
        for (int i = 0; i < 6; ++i) {
            // Check the planes[i]
            if (PlaneHelper.classifyPoint(point, planes[i]) > 0)
                return ContainmentType.Disjoint;
        }
        // If we get here, it means that the point was on the correct side of
        // each plane to be
        // contained. Therefore this point is contained
        return ContainmentType.Contains;
    }

    /**
     * Equals.
     *
     * @param other
     *            the other
     * @return true, if successful
     */
    public boolean equals(BoundingFrustum other) {
        return this == other;
    }

    /**
     * Gets the corners.
     *
     * @return the corners
     */
    public Vector3[] getCorners() {
        return corners;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "{Near:" + planes[4].toString() + " Far:" + planes[1].toString()
                + " Left:" + planes[2].toString() + " Right:"
                + planes[3].toString() + " Top:" + planes[5].toString()
                + " Bottom:" + planes[0].toString() + "}";

    }

    /**
     * Creates the corners.
     */
    void createCorners() {
        corners[0] = intersectionPoint(planes[4], planes[2], planes[5]);
        corners[1] = intersectionPoint(planes[4], planes[3], planes[5]);
        corners[2] = intersectionPoint(planes[4], planes[3], planes[0]);
        corners[3] = intersectionPoint(planes[4], planes[2], planes[0]);
        corners[4] = intersectionPoint(planes[1], planes[2], planes[5]);
        corners[5] = intersectionPoint(planes[1], planes[3], planes[5]);
        corners[6] = intersectionPoint(planes[1], planes[3], planes[0]);
        corners[7] = intersectionPoint(planes[1], planes[2], planes[0]);
    }

    /**
     * Creates the planes.
     *
     * @param matrix
     *            the matrix
     */
    void createPlanes(Matrix matrix) {
        // Pre-calculate the different planes needed
        planes[0] = new Plane(-matrix.M[3] - matrix.M[1], -matrix.M[7]
                - matrix.M[5], -matrix.M[11] - matrix.M[9], -matrix.M[15]
                - matrix.M[13]);

        planes[1] = new Plane(matrix.M[2] - matrix.M[3], matrix.M[6]
                - matrix.M[7], matrix.M[10] - matrix.M[11], matrix.M[14]
                - matrix.M[15]);

        planes[2] = new Plane(-matrix.M[3] - matrix.M[0], -matrix.M[7]
                - matrix.M[4], -matrix.M[11] - matrix.M[8], -matrix.M[15]
                - matrix.M[12]);

        planes[3] = new Plane(matrix.M[0] - matrix.M[3], matrix.M[4]
                - matrix.M[7], matrix.M[8] - matrix.M[11], matrix.M[12]
                - matrix.M[15]);

        planes[4] = new Plane(-matrix.M[2], -matrix.M[6], -matrix.M[10],
                -matrix.M[14]);

        planes[5] = new Plane(matrix.M[1] - matrix.M[3], matrix.M[5]
                - matrix.M[7], matrix.M[9] - matrix.M[11], matrix.M[13]
                - matrix.M[15]);

        for (int i = 0; i < 6; ++i) {
            planes[i].normalize();
        }
    }

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
        float f = -a.getNormal().dot(
                new Vector3(b.getNormal()).cross(c.getNormal()));
        Vector3 v1 = new Vector3(b.getNormal()).cross(c.getNormal()).multiply(
                a.getDistance());
        Vector3 v2 = new Vector3(c.getNormal()).cross(a.getNormal()).multiply(
                b.getDistance());
        Vector3 v3 = new Vector3(a.getNormal()).cross(b.getNormal()).multiply(
                c.getDistance());
        return v1.add(v2).add(v3).divide(f);
    }

}
