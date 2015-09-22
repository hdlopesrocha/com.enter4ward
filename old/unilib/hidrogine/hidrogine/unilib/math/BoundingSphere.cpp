#ifndef unilib_math_BoundingSphere
#define unilib_math_BoundingSphere


#include "Math.hpp"

namespace unilib
{
    BoundingSphere::BoundingSphere()
    {
    }

    BoundingSphere::BoundingSphere(Vector3 center, float radius)
    {
        Center = center;
        Radius = radius;
    }

    BoundingSphere BoundingSphere::Transform(Matrix matrix)
    {
        BoundingSphere sphere = BoundingSphere();
        sphere.Center = Vector3::Transform(Center, matrix);
        sphere.Radius = Radius * ((float)sqrt((double)max(((matrix.M[0] * matrix.M[0]) + (matrix.M[1] * matrix.M[1])) + (matrix.M[2] * matrix.M[2]), max(((matrix.M[4] * matrix.M[4]) + (matrix.M[5] * matrix.M[5])) + (matrix.M[6] * matrix.M[6]), ((matrix.M[8] * matrix.M[8]) + (matrix.M[9] * matrix.M[9])) + (matrix.M[10] * matrix.M[10])))));
        return sphere;
    }

    ContainmentType::ContainmentType BoundingSphere::Contains(BoundingBox box)
    {
        //check if all corner is in sphere
        bool inside = true;
        vector<Vector3> corners = box.GetCorners();

        for (unsigned int i=0 ; i < corners.size() ; ++i)
        {
            if (BoundingSphere::Contains(corners[i]) == ContainmentType::Disjoint)
            {
                inside = false;
                break;
            }
        }

        if (inside)
            return ContainmentType::Contains;

        //check if the distance from sphere center to cube face < radius
        double dmin = 0;

        if (Center.X < box.Min.X)
			dmin += (Center.X - box.Min.X) * (Center.X - box.Min.X);

		else if (Center.X > box.Max.X)
				dmin += (Center.X - box.Max.X) * (Center.X - box.Max.X);

		if (Center.Y < box.Min.Y)
			dmin += (Center.Y - box.Min.Y) * (Center.Y - box.Min.Y);

		else if (Center.Y > box.Max.Y)
			dmin += (Center.Y - box.Max.Y) * (Center.Y - box.Max.Y);

		if (Center.Z < box.Min.Z)
			dmin += (Center.Z - box.Min.Z) * (Center.Z - box.Min.Z);

		else if (Center.Z > box.Max.Z)
			dmin += (Center.Z - box.Max.Z) * (Center.Z - box.Max.Z);

		if (dmin <= Radius * Radius) 
			return ContainmentType::Intersects;
        
        //else disjoint
        return ContainmentType::Disjoint;

    }

    
    ContainmentType::ContainmentType BoundingSphere::Contains(BoundingFrustum frustum)
    {
        //check if all corner is in sphere
        bool inside = true;

        vector<Vector3> corners = frustum.GetCorners();
        for (unsigned int i=0 ; i < corners.size() ; ++i)
        {
            if (Contains(corners[i]) == ContainmentType::Disjoint)
            {
                inside = false;
                break;
            }
        }
        if (inside)
            return ContainmentType::Contains;

        //check if the distance from sphere center to frustrum face < radius
        double dmin = 0;
        //TODO : calcul dmin

        if (dmin <= Radius * Radius)
            return ContainmentType::Intersects;

        //else disjoint
        return ContainmentType::Disjoint;
    }

    ContainmentType::ContainmentType BoundingSphere::Contains(BoundingSphere sphere)
    {
        float val = Vector3::Distance(sphere.Center, Center);

        if (val > sphere.Radius + Radius)
            return ContainmentType::Disjoint;

        else if (val <= Radius - sphere.Radius)
            return ContainmentType::Contains;

        else
            return ContainmentType::Intersects;
    }


    ContainmentType::ContainmentType BoundingSphere::Contains(Vector3 point)
    {
        float distance = Vector3::Distance(point, Center);

        if (distance > Radius)
            return ContainmentType::Disjoint;

        else if (distance < Radius)
            return ContainmentType::Contains;

        return ContainmentType::Intersects;
    }

    BoundingSphere BoundingSphere::CreateFromBoundingBox(BoundingBox box)
    {
        // Find the center of the box.
        Vector3 center = Vector3((box.Min.X + box.Max.X) / 2.0,
                                     (box.Min.Y + box.Max.Y) / 2.0,
                                     (box.Min.Z + box.Max.Z) / 2.0);

        // Find the distance between the center and one of the corners of the box.
        float radius = Vector3::Distance(center, box.Max);

        return BoundingSphere(center, radius);
    }

    BoundingSphere BoundingSphere::CreateFromFrustum(BoundingFrustum frustum)
    {
        return BoundingSphere::CreateFromPoints(frustum.GetCorners());
    }

    BoundingSphere BoundingSphere::CreateFromPoints(vector<Vector3> points)
    {
        float radius = 0;
        Vector3 min, max, center;
        if(points.size()>0){
            min = max = points[0];

            for (unsigned int i=0; i < points.size() ; ++i)
            {
                min.X = MathHelper::Min(min.X,points[i].X);
                min.Y = MathHelper::Min(min.Y,points[i].Y);
                min.Z = MathHelper::Min(min.Z,points[i].Z);

                max.X = MathHelper::Max(max.X,points[i].X);
                max.Y = MathHelper::Max(max.Y,points[i].Y);
                max.Z = MathHelper::Max(max.Z,points[i].Z);
            }
            
            center = (min + max)/2.0;
            radius = (max - min).Length()/2.0;
        }
        return BoundingSphere(center, radius);
    }

    BoundingSphere BoundingSphere::CreateMerged(BoundingSphere original, BoundingSphere additional)
    {
        Vector3 ocenterToaCenter = Vector3::Subtract(additional.Center, original.Center);
        float distance = ocenterToaCenter.Length();
        if (distance <= original.Radius + additional.Radius)//intersect
        {
            if (distance <= original.Radius - additional.Radius)//original contain additional
                return original;
            if (distance <= additional.Radius - original.Radius)//additional contain original
                return additional;
        }

        //else find center of sphere and radius
        float leftRadius = max(original.Radius - distance, additional.Radius);
        float Rightradius = max(original.Radius + distance, additional.Radius);
        ocenterToaCenter = ocenterToaCenter + (ocenterToaCenter*((leftRadius - Rightradius) / (2 * ocenterToaCenter.Length())));//oCenterToResultCenter
        
        BoundingSphere result = BoundingSphere();
        result.Center = original.Center + ocenterToaCenter;
        result.Radius = (leftRadius + Rightradius) / 2;
        return result;
    }

    bool BoundingSphere::Equals(BoundingSphere other)
    {
        return Center == other.Center && Radius == other.Radius;
    }

    int BoundingSphere::GetHashCode()
    {
        return Center.GetHashCode() + Radius;
    }

    bool BoundingSphere::Intersects(BoundingBox box)
    {
		return box.Intersects(*this);
    }

    bool BoundingSphere::Intersects(BoundingFrustum frustum)
    {
        throw "NotImplementedException";
    }

    bool BoundingSphere::Intersects(BoundingSphere sphere)
    {
        float val = Vector3::Distance(sphere.Center, Center);
		if (val > sphere.Radius + Radius)
			return false;
		return true;
    }

    PlaneIntersectionType::PlaneIntersectionType BoundingSphere::Intersects(Plane plane)
    {
		float distance = Vector3::Dot(plane.Normal, Center) + plane.D;
		if (distance > Radius)
			return PlaneIntersectionType::Front;
		if (distance < -Radius)
			return PlaneIntersectionType::Back;
		//else it intersect
		return PlaneIntersectionType::Intersecting;
    }

    float * BoundingSphere::Intersects(Ray ray)
    {
        return ray.Intersects(*this);
    }

    bool BoundingSphere::operator==(BoundingSphere value){
       return (Center==value.Center && Radius==value.Radius);
    }


    bool BoundingSphere::operator!=(BoundingSphere value){
       return (Center!=value.Center || Radius!=value.Radius);
    }

    string BoundingSphere::ToString()
    {
        stringstream out;
        out << "{{Center:" << Center.ToString() << " Radius:" << Radius << "}}";
        return out.str();
    }

}

#endif