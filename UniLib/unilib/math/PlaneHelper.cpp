#ifndef unilib_math_PlaneHelper
#define unilib_math_PlaneHelper

#include "Math.hpp"

namespace unilib
{

    /// <summary>
    /// Returns a value indicating what side (positive/negative) of a plane a point is
    /// </summary>
    /// <param name="point">The point to check with</param>
    /// <param name="plane">The plane to check against</param>
    /// <returns>Greater than zero if on the positive side, less than zero if on the negative size, 0 otherwise</returns>
    float PlaneHelper::ClassifyPoint(Vector3 point, Plane plane)
    {
        return point.X * plane.Normal.X + point.Y * plane.Normal.Y + point.Z * plane.Normal.Z + plane.D;
    }

    /// <summary>
    /// Returns the perpendicular distance from a point to a plane
    /// </summary>
    /// <param name="point">The point to check</param>
    /// <param name="plane">The place to check</param>
    /// <returns>The perpendicular distance from the point to the plane</returns>
    float PlaneHelper::PerpendicularDistance(Vector3 point, Plane plane)
    {
        // dist = (ax + by + cz + d) / sqrt(a*a + b*b + c*c)
        float result = plane.D+ Vector3::Dot(plane.Normal,point);
        return -result;
    }

}
#endif