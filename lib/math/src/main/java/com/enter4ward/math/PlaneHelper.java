package com.enter4ward.math;

// TODO: Auto-generated Javadoc
/**
 * The Class PlaneHelper.
 */
public class PlaneHelper {

    /**
     * Classify point.
     *
     * @param point
     *            the point
     * @param plane
     *            the plane
     * @return the float
     */
    public static float classifyPoint(Vector3 point, Plane plane) {
        return point.getX() * plane.getNormal().getX() + point.getY()
                * plane.getNormal().getY() + point.getZ()
                * plane.getNormal().getZ() + plane.getDistance();
    }

    /**
     * Perpendicular distance.
     *
     * @param point
     *            the point
     * @param plane
     *            the plane
     * @return the float
     */
    public static float perpendicularDistance(Vector3 point, Plane plane) {
        // dist = (ax + by + cz + d) / sqrt(a*a + b*b + c*c)
        return -(plane.getDistance() + plane.getNormal().dot(point));
    }
}
