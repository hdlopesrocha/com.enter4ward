#ifndef unilib_math_BoundingFrustum
#define unilib_math_BoundingFrustum

#include "Math.hpp"

namespace unilib
{

    BoundingFrustum::BoundingFrustum(){

    }

 
    BoundingFrustum::BoundingFrustum(Matrix value)
    {
        matrix = value;
        CreatePlanes();
        CreateCorners();
    }

 
    Plane BoundingFrustum::Bottom()
    {
        return planes[0];
    }

    Plane BoundingFrustum::Far()
    {
        return planes[1];
    }

    Plane BoundingFrustum::Left()
    {
        return planes[2];
    }

    Matrix BoundingFrustum::GetMatrix()
    {
        return matrix; 
    }

    void BoundingFrustum::SetMatrix(Matrix value){
        matrix = value;
        CreatePlanes();    // FIXME: The odds are the planes will be used a lot more often than the matrix
        CreateCorners();   // is updated, so this should help performance. I hope ;)
    }

    Plane BoundingFrustum::Near()
    {
        return planes[4];
    }

    Plane BoundingFrustum::Right()
    {
        return planes[3];
    }

    Plane BoundingFrustum::Top()
    {
        return planes[5];
    }

    bool BoundingFrustum::operator ==(BoundingFrustum value)
    {
        return matrix == value.matrix;
    }

    bool BoundingFrustum::operator!=(BoundingFrustum value)
    {
        return matrix != value.matrix;
    }

    ContainmentType::ContainmentType BoundingFrustum::Contains(BoundingBox box)
    {
        // FIXME: Is this a bug?
        // If the bounding box is of W * D * H = 0, then return disjoint
        if (box.Min == box.Max)
        {
            return ContainmentType::Disjoint;
        }

        unsigned int i;
        ContainmentType::ContainmentType contained;
        vector<Vector3> corners = box.GetCorners();

        // First we assume completely disjoint. So if we find a point that is contained, we break out of this loop
        for (i = 0; i < corners.size(); i++)
        {
            contained = Contains(corners[i]);
            if (contained != ContainmentType::Disjoint)
                break;
        }

        if (i == corners.size()) // This means we checked all the corners and they were all disjoint
        {
            return ContainmentType::Disjoint;
        }

        if (i != 0)             // if i is not equal to zero, we can fastpath and say that this box intersects
        {                       // because we know at least one point is outside and one is inside.
            return ContainmentType::Intersects;
        }

        // If we get here, it means the first (and only) point we checked was actually contained in the frustum.
        // So we assume that all other points will also be contained. If one of the points is disjoint, we can
        // exit immediately saying that the result is Intersects
        i++;
        for (; i < corners.size(); i++)
        {
            contained = Contains(corners[i]);
            if (contained != ContainmentType::Contains)
            {
                return ContainmentType::Intersects;
            }
        }

        // If we get here, then we know all the points were actually contained, therefore result is Contains
        return ContainmentType::Contains;
        
    }
    

    // TODO: Implement this
    ContainmentType::ContainmentType BoundingFrustum::Contains(BoundingFrustum frustum)
    {
        if (*this == frustum)                // We check to see if the two frustums are equal
            return ContainmentType::Contains;// If they are, there's no need to go any further.

        throw "NotImplementedException";
    }

    ContainmentType::ContainmentType BoundingFrustum::Contains(BoundingSphere sphere)
    {
        float val;
        ContainmentType::ContainmentType result = ContainmentType::Contains;

        for(int i=0 ; i < 6; ++i){
            val = PlaneHelper::PerpendicularDistance(sphere.Center, planes[i]);
            if (val < -sphere.Radius)
                return ContainmentType::Disjoint;
            else if (val < sphere.Radius)
                result = ContainmentType::Intersects;
        }
        return result;
        
    }


    ContainmentType::ContainmentType BoundingFrustum::Contains(Vector3 point)
    {
        // If a point is on the POSITIVE side of the plane, then the point is not contained within the frustum
        for(int i=0 ; i < 6 ; ++i){
            // Check the planes[i]
            if (PlaneHelper::ClassifyPoint(point, planes[i]) > 0)
                return ContainmentType::Disjoint;
        }
        // If we get here, it means that the point was on the correct side of each plane to be
        // contained. Therefore this point is contained
        return ContainmentType::Contains;
    }


    bool BoundingFrustum::Equals(BoundingFrustum other)
    {
        return matrix == other.matrix;
    }

    vector<Vector3> BoundingFrustum::GetCorners()
    {
        return corners;
    }

    int BoundingFrustum::GetHashCode()
    {
        return matrix.GetHashCode();
    }

    bool BoundingFrustum::Intersects(BoundingBox box)
    {
        throw "NotImplementedException";
    }

    bool BoundingFrustum::Intersects(BoundingFrustum frustum)
    {
        throw "NotImplementedException";
    }

    bool BoundingFrustum::Intersects(BoundingSphere sphere)
    {
        throw "NotImplementedException";
    }

    PlaneIntersectionType::PlaneIntersectionType BoundingFrustum::Intersects(Plane plane)
    {
        throw "NotImplementedException";
    }

    float* BoundingFrustum::Intersects(Ray ray)
    {
        throw "NotImplementedException";
    }
/*
    string ToString()
    {
        StringBuilder sb = StringBuilder(256);
        sb.Append("{Near:");
        sb.Append(planes[4].ToString());
        sb.Append(" Far:");
        sb.Append(planes[1].ToString());
        sb.Append(" Left:");
        sb.Append(planes[2].ToString());
        sb.Append(" Right:");
        sb.Append(planes[3].ToString());
        sb.Append(" Top:");
        sb.Append(planes[5].ToString());
        sb.Append(" Bottom:");
        sb.Append(planes[0].ToString());
        sb.Append("}");
        return sb.ToString();
    }
*/
    void BoundingFrustum::CreateCorners()
    {
        corners.clear();
        corners.push_back(IntersectionPoint(planes[4], planes[2], planes[5]));
        corners.push_back(IntersectionPoint(planes[4], planes[3], planes[5]));
        corners.push_back(IntersectionPoint(planes[4], planes[3], planes[0]));
        corners.push_back(IntersectionPoint(planes[4], planes[2], planes[0]));
        corners.push_back(IntersectionPoint(planes[1], planes[2], planes[5]));
        corners.push_back(IntersectionPoint(planes[1], planes[3], planes[5]));
        corners.push_back(IntersectionPoint(planes[1], planes[3], planes[0]));
        corners.push_back(IntersectionPoint(planes[1], planes[2], planes[0]));
    }

    void BoundingFrustum::CreatePlanes()
    {
        // Pre-calculate the different planes needed
        planes[2] = Plane(-matrix.M[3] - matrix.M[0], -matrix.M[7] - matrix.M[4],
                              -matrix.M[11] - matrix.M[8], -matrix.M[15] - matrix.M[12]);

        planes[3] = Plane(matrix.M[0] - matrix.M[3], matrix.M[4] - matrix.M[7],
                               matrix.M[8] - matrix.M[11], matrix.M[12] - matrix.M[15]);

        planes[5] = Plane(matrix.M[1] - matrix.M[3], matrix.M[5] - matrix.M[7],
                             matrix.M[9] - matrix.M[11], matrix.M[13] - matrix.M[15]);

        planes[0] = Plane(-matrix.M[3] - matrix.M[1], -matrix.M[7] - matrix.M[5],
                                -matrix.M[11] - matrix.M[9], -matrix.M[15] - matrix.M[13]);

        planes[4] = Plane(-matrix.M[2], -matrix.M[6], -matrix.M[10], -matrix.M[14]);


        planes[1] = Plane(matrix.M[2] - matrix.M[3], matrix.M[6] - matrix.M[7],
                             matrix.M[10] - matrix.M[11], matrix.M[14] - matrix.M[15]);

        NormalizePlane(&planes[2]);
        NormalizePlane(&planes[3]);
        NormalizePlane(&planes[5]);
        NormalizePlane(&planes[0]);
        NormalizePlane(&planes[4]);
        NormalizePlane(&planes[1]);
    }

    Vector3 BoundingFrustum::IntersectionPoint(Plane a, Plane b, Plane c)
    {
        // Formula used
        //                d1 ( N2 * N3 ) + d2 ( N3 * N1 ) + d3 ( N1 * N2 )
        //P = 	-------------------------------------------------------------------------
        //                             N1 . ( N2 * N3 )
        //
        // Note: N refers to the normal, d refers to the displacement. '.' means dot product. '*' means cross product

        Vector3 v1, v2, v3;
        float f = -Vector3::Dot(a.Normal, Vector3::Cross(b.Normal, c.Normal));

        v1 = Vector3::Cross(b.Normal, c.Normal)*a.D;
        v2 = Vector3::Cross(c.Normal, a.Normal)*b.D;
        v3 = Vector3::Cross(a.Normal, b.Normal)*c.D;

        Vector3 vec = Vector3(v1.X + v2.X + v3.X, v1.Y + v2.Y + v3.Y, v1.Z + v2.Z + v3.Z);
        return vec / f;
    }
    
    void BoundingFrustum::NormalizePlane(Plane * p)
    {
        float factor = 1.0 / p->Normal.Length();
        p->Normal.X *= factor;
        p->Normal.Y *= factor;
        p->Normal.Z *= factor;
        p->D *= factor;
    }
}

#endif
