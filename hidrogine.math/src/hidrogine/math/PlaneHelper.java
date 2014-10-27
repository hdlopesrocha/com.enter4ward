package hidrogine.math;


public class PlaneHelper {
    /// <summary>
    /// Returns a value indicating what side (positive/negative) of a plane a point is
    /// </summary>
    /// <param name="point">The point to check with</param>
    /// <param name="plane">The plane to check against</param>
    /// <returns>Greater than zero if on the positive side, less than zero if on the negative size, 0 otherwise</returns>
    public static float classifyPoint(IVector3 point, Plane plane)
    {
        return point.getX() * plane.getNormal().getX() + point.getY() * plane.getNormal().getY() + point.getZ() * plane.getNormal().getZ() + plane.getDistance();
    }

    /// <summary>
    /// Returns the perpendicular distance from a point to a plane
    /// </summary>
    /// <param name="point">The point to check</param>
    /// <param name="plane">The place to check</param>
    /// <returns>The perpendicular distance from the point to the plane</returns>
    public static float perpendicularDistance(IVector3 point, Plane plane)
    {
        // dist = (ax + by + cz + d) / sqrt(a*a + b*b + c*c)
        return -(plane.getDistance()+ plane.getNormal().dot(point));
    }
}
