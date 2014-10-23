package hidrogine.math;

import hidrogine.math.api.IBoundingSphere;
import hidrogine.math.api.IVector3;

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

    /** The matrix. */
    private Matrix matrix;

    /** The planes. */
    private Plane[] planes = new Plane[6];

    /** The corners. */
    private IVector3[] corners;

    /** The Constant CornerCount. */
    public static final int CornerCount = 8;

    /**
     * Instantiates a new bounding frustum.
     */
    public BoundingFrustum() {
        matrix = new Matrix();
    }

    /**
     * Instantiates a new bounding frustum.
     *
     * @param value
     *            the value
     */
    public BoundingFrustum(Matrix value) {
        matrix = value;
        createPlanes();
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
     * Gets the matrix.
     *
     * @return the matrix
     */
    public Matrix GetMatrix() {
        return matrix;
    }

    /**
     * Sets the matrix.
     *
     * @param value
     *            the value
     */
    public void SetMatrix(Matrix value) {
        matrix = value;
        createPlanes(); // FIXME: The odds are the planes will be used a lot
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

    /**
     * Contains.
     *
     * @param box
     *            the box
     * @return the containment type
     */
    public ContainmentType contains(BoundingBox box) {
        // FIXME: Is this a bug?
        // If the bounding box is of W * D * H = 0, then return disjoint
        if (box.getMin().equals(box.getMax())) {
            return ContainmentType.Disjoint;
        }

        int i;
        ContainmentType contained;
        Vector3[] corners = box.getCorners();

        // First we assume completely disjoint. So if we find a point that is
        // contained, we break out of this loop
        for (i = 0; i < corners.length; i++) {
            contained = contains(corners[i]);
            if (contained != ContainmentType.Disjoint)
                break;
        }

        if (i == corners.length) // This means we checked all the corners and
                                 // they were all disjoint
        {
            return ContainmentType.Disjoint;
        }

        if (i != 0) // if i is not equal to zero, we can fastpath and say that
                    // this box intersects
        { // because we know at least one point is outside and one is inside.
            return ContainmentType.Intersects;
        }

        // If we get here, it means the first (and only) point we checked was
        // actually contained in the frustum.
        // So we assume that all other points will also be contained. If one of
        // the points is disjoint, we can
        // exit immediately saying that the result is Intersects
        i++;
        for (; i < corners.length; i++) {
            contained = contains(corners[i]);
            if (contained != ContainmentType.Contains) {
                return ContainmentType.Intersects;
            }
        }

        // If we get here, then we know all the points were actually contained,
        // therefore result is Contains
        return ContainmentType.Contains;

    }

    /**
     * Contains.
     *
     * @param sphere
     *            the sphere
     * @return the containment type
     */
    public ContainmentType contains(IBoundingSphere sphere) {
        float val;
        ContainmentType result = ContainmentType.Contains;

        for (int i = 0; i < 6; ++i) {
            val = PlaneHelper.perpendicularDistance(sphere.getPosition(),
                    planes[i]);
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
        return matrix == other.matrix;
    }

    /**
     * Gets the corners.
     *
     * @return the corners
     */
    public IVector3[] getCorners() {
        return corners;
    }

    /*
     * string ToString() { StringBuilder sb = StringBuilder(256);
     * sb.Append("{Near:"); sb.Append(planes[4].ToString()); sb.Append(" Far:");
     * sb.Append(planes[1].ToString()); sb.Append(" Left:");
     * sb.Append(planes[2].ToString()); sb.Append(" Right:");
     * sb.Append(planes[3].ToString()); sb.Append(" Top:");
     * sb.Append(planes[5].ToString()); sb.Append(" Bottom:");
     * sb.Append(planes[0].ToString()); sb.Append("}"); return sb.ToString(); }
     */
    /**
     * Creates the corners.
     */
    void createCorners() {
        corners = new Vector3[8];
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
     */
    void createPlanes() {
        // Pre-calculate the different planes needed
        planes[2] = new Plane(-matrix.M[3] - matrix.M[0], -matrix.M[7]
                - matrix.M[4], -matrix.M[11] - matrix.M[8], -matrix.M[15]
                - matrix.M[12]);

        planes[3] = new Plane(matrix.M[0] - matrix.M[3], matrix.M[4]
                - matrix.M[7], matrix.M[8] - matrix.M[11], matrix.M[12]
                - matrix.M[15]);

        planes[5] = new Plane(matrix.M[1] - matrix.M[3], matrix.M[5]
                - matrix.M[7], matrix.M[9] - matrix.M[11], matrix.M[13]
                - matrix.M[15]);

        planes[0] = new Plane(-matrix.M[3] - matrix.M[1], -matrix.M[7]
                - matrix.M[5], -matrix.M[11] - matrix.M[9], -matrix.M[15]
                - matrix.M[13]);

        planes[4] = new Plane(-matrix.M[2], -matrix.M[6], -matrix.M[10],
                -matrix.M[14]);

        planes[1] = new Plane(matrix.M[2] - matrix.M[3], matrix.M[6]
                - matrix.M[7], matrix.M[10] - matrix.M[11], matrix.M[14]
                - matrix.M[15]);

        normalizePlane(planes[2]);
        normalizePlane(planes[3]);
        normalizePlane(planes[5]);
        normalizePlane(planes[0]);
        normalizePlane(planes[4]);
        normalizePlane(planes[1]);
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
    public IVector3 intersectionPoint(Plane a, Plane b, Plane c) {
        // Formula used
        // d1 ( N2 * N3 ) + d2 ( N3 * N1 ) + d3 ( N1 * N2 )
        // P =
        // -------------------------------------------------------------------------
        // N1 . ( N2 * N3 )
        //
        // Note: N refers to the normal, d refers to the displacement. '.' means
        // dot product. '*' means cross product

        IVector3 v1, v2, v3;
        float f = -a.Normal.dot(new Vector3(b.Normal).cross(c.Normal));

        v1 = new Vector3(b.Normal).cross(c.Normal).multiply(a.D);
        v2 = new Vector3(c.Normal).cross(a.Normal).multiply(b.D);
        v3 = new Vector3(a.Normal).cross(b.Normal).multiply(c.D);

        Vector3 vec = new Vector3(v1.getX() + v2.getX() + v3.getX(), v1.getY()
                + v2.getY() + v3.getY(), v1.getZ() + v2.getZ() + v3.getZ());
        return vec.divide(f);
    }

    /**
     * Normalize plane.
     *
     * @param p
     *            the p
     */
    public void normalizePlane(Plane p) {
        float factor = 1f / p.Normal.length();
        p.Normal.multiply(factor);

        p.D *= factor;
    }

    /**
     * Intersects.
     *
     * @param boundingBox
     *            the bounding box
     * @return true, if successful
     */
    public boolean intersects(BoundingBox boundingBox) {
        throw new RuntimeException("Not Implemented!");
    }
}
