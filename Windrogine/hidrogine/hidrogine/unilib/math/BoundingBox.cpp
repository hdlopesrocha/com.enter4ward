#ifndef unilib_math_BoundingBox
#define unilib_math_BoundingBox

#include "Math.hpp"

namespace unilib
{

    BoundingBox::BoundingBox(){

    }

    BoundingBox::BoundingBox(Vector3 min, Vector3 max)
    {
        Min = min;
        Max = max;
    }

    ContainmentType::ContainmentType BoundingBox::Contains(BoundingBox box)
    {
        //test if all corner is in the same side of a face by just checking min and max
        if (box.Max.X < Min.X
            || box.Min.X > Max.X
            || box.Max.Y < Min.Y
            || box.Min.Y > Max.Y
            || box.Max.Z < Min.Z
            || box.Min.Z > Max.Z)
            return ContainmentType::Disjoint;


        if (box.Min.X >= Min.X
            && box.Max.X <= Max.X
            && box.Min.Y >= Min.Y
            && box.Max.Y <= Max.Y
            && box.Min.Z >= Min.Z
            && box.Max.Z <= Max.Z)
            return ContainmentType::Contains;

        return ContainmentType::Intersects;
    }


    ContainmentType::ContainmentType BoundingBox::Contains(BoundingFrustum frustum)
    {
        //TODO: bad done here need a fix. 
        //Because question is not frustum contain box but reverse and this is not the same
        unsigned int i;
        ContainmentType::ContainmentType contained;
        vector<Vector3> corners = frustum.GetCorners();

        // First we check if frustum is in box
        for (i = 0; i < corners.size(); i++)
        {
            contained = Contains(corners[i]);
            if (contained == ContainmentType::Disjoint)
                break;
        }

        if (i == corners.size()) // This means we checked all the corners and they were all contain or instersect
            return ContainmentType::Contains;

        if (i != 0)             // if i is not equal to zero, we can fastpath and say that this box intersects
            return ContainmentType::Intersects;


        // If we get here, it means the first (and only) point we checked was actually contained in the frustum.
        // So we assume that all other points will also be contained. If one of the points is disjoint, we can
        // exit immediately saying that the result is Intersects
        i++;
        for (; i < corners.size(); i++)
        {
            contained= Contains(corners[i]);
            if (contained != ContainmentType::Contains)
                return ContainmentType::Intersects;

        }

        // If we get here, then we know all the points were actually contained, therefore result is Contains
        return ContainmentType::Contains;
    }

    ContainmentType::ContainmentType BoundingBox::Contains(BoundingSphere sphere)
    {
        if (sphere.Center.X - Min.X > sphere.Radius
            && sphere.Center.Y - Min.Y > sphere.Radius
            && sphere.Center.Z - Min.Z > sphere.Radius
            && Max.X - sphere.Center.X > sphere.Radius
            && Max.Y - sphere.Center.Y > sphere.Radius
            && Max.Z - sphere.Center.Z > sphere.Radius)
            return ContainmentType::Contains;

        double dmin = 0;

        if (sphere.Center.X - Min.X <= sphere.Radius)
            dmin += (sphere.Center.X - Min.X) * (sphere.Center.X - Min.X);
        else if (Max.X - sphere.Center.X <= sphere.Radius)
            dmin += (sphere.Center.X - Max.X) * (sphere.Center.X - Max.X);
        if (sphere.Center.Y - Min.Y <= sphere.Radius)
            dmin += (sphere.Center.Y - Min.Y) * (sphere.Center.Y - Min.Y);
        else if (Max.Y - sphere.Center.Y <= sphere.Radius)
            dmin += (sphere.Center.Y - Max.Y) * (sphere.Center.Y - Max.Y);
        if (sphere.Center.Z - Min.Z <= sphere.Radius)
            dmin += (sphere.Center.Z - Min.Z) * (sphere.Center.Z - Min.Z);
        else if (Max.Z - sphere.Center.Z <= sphere.Radius)
            dmin += (sphere.Center.Z - Max.Z) * (sphere.Center.Z - Max.Z);

        if (dmin <= sphere.Radius * sphere.Radius)
            return ContainmentType::Intersects;

        return ContainmentType::Disjoint;
    }


    ContainmentType::ContainmentType BoundingBox::Contains(Vector3 point)
    {
        //first we get if point is out of box
        if (point.X < Min.X
            || point.X > Max.X
            || point.Y < Min.Y
            || point.Y > Max.Y
            || point.Z < Min.Z
            || point.Z > Max.Z)
        {
            return ContainmentType::Disjoint;
        }//or if point is on box because coordonate of point is lesser or equal
        else if (point.X == Min.X
            || point.X == Max.X
            || point.Y == Min.Y
            || point.Y == Max.Y
            || point.Z == Min.Z
            || point.Z == Max.Z)
            return ContainmentType::Intersects;
        else
            return ContainmentType::Contains;
    }
/*
    BoundingBox BoundingBox::CreateFromPoints(vector<Vector3> points)
    {
        // TODO: Just check that Count > 0
        bool empty = true;
        Vector3 vector2 = Vector3(float.MaxValue);
        Vector3 vector1 = Vector3(float.MinValue);
        
        foreach (Vector3 vector3 in points)
        {
            vector2 = Vector3.Min(vector2, vector3);
            vector1 = Vector3.Max(vector1, vector3);
            empty = false;
        }
        if (empty)
            throw "ArgumentException";

        return BoundingBox(vector2, vector1);
    }
*/
    BoundingBox BoundingBox::CreateFromSphere(BoundingSphere sphere)
    {
        Vector3 vector1 = Vector3(sphere.Radius);
        return BoundingBox(sphere.Center - vector1, sphere.Center + vector1);
    }

    BoundingBox BoundingBox::CreateMerged(BoundingBox original, BoundingBox additional)
    {
        return BoundingBox(
            Vector3::Min(original.Min, additional.Min), Vector3::Max(original.Max, additional.Max));
    }


    bool BoundingBox::Equals(BoundingBox other)
    {
        return (Min == other.Min) && (Max == other.Max);
    }


    vector<Vector3> BoundingBox::GetCorners()
    {
        vector<Vector3> ret;
        ret.push_back(Vector3(Min.X, Max.Y, Max.Z));
        ret.push_back(Vector3(Max.X, Max.Y, Max.Z));
        ret.push_back(Vector3(Max.X, Min.Y, Max.Z));
        ret.push_back(Vector3(Min.X, Min.Y, Max.Z));
        ret.push_back(Vector3(Min.X, Max.Y, Min.Z));
        ret.push_back(Vector3(Max.X, Max.Y, Min.Z));
        ret.push_back(Vector3(Max.X, Min.Y, Min.Z));
        ret.push_back(Vector3(Min.X, Min.Y, Min.Z));
        return ret;
    }

    int BoundingBox::GetHashCode()
    {
        return Min.GetHashCode() + Max.GetHashCode();
    }

    bool BoundingBox::Intersects(BoundingBox box)
    {
        if ((Max.X >= box.Min.X) && (Min.X <= box.Max.X))
        {
            if ((Max.Y < box.Min.Y) || (Min.Y > box.Max.Y))
                return false;
            return (Max.Z >= box.Min.Z) && (Min.Z <= box.Max.Z);
        }
        return false;
    }


    bool BoundingBox::Intersects(BoundingFrustum frustum)
    {
        return frustum.Intersects(*this);
    }

    bool BoundingBox::Intersects(BoundingSphere sphere)
    {
        if (sphere.Center.X - Min.X > sphere.Radius
            && sphere.Center.Y - Min.Y > sphere.Radius
            && sphere.Center.Z - Min.Z > sphere.Radius
            && Max.X - sphere.Center.X > sphere.Radius
            && Max.Y - sphere.Center.Y > sphere.Radius
            && Max.Z - sphere.Center.Z > sphere.Radius)
            return true;

        double dmin = 0;

        if (sphere.Center.X - Min.X <= sphere.Radius)
            dmin += (sphere.Center.X - Min.X) * (sphere.Center.X - Min.X);
        else if (Max.X - sphere.Center.X <= sphere.Radius)
            dmin += (sphere.Center.X - Max.X) * (sphere.Center.X - Max.X);

        if (sphere.Center.Y - Min.Y <= sphere.Radius)
            dmin += (sphere.Center.Y - Min.Y) * (sphere.Center.Y - Min.Y);
        else if (Max.Y - sphere.Center.Y <= sphere.Radius)
            dmin += (sphere.Center.Y - Max.Y) * (sphere.Center.Y - Max.Y);

        if (sphere.Center.Z - Min.Z <= sphere.Radius)
            dmin += (sphere.Center.Z - Min.Z) * (sphere.Center.Z - Min.Z);
        else if (Max.Z - sphere.Center.Z <= sphere.Radius)
            dmin += (sphere.Center.Z - Max.Z) * (sphere.Center.Z - Max.Z);

        if (dmin <= sphere.Radius * sphere.Radius)
            return true;

        return false;
    }

    PlaneIntersectionType::PlaneIntersectionType BoundingBox::Intersects(Plane plane)
    {
        //check all corner side of plane
        vector<Vector3> corners = GetCorners();
        float lastdistance = Vector3::Dot(plane.Normal, corners[0]) + plane.D;

        for (unsigned int i = 1; i < corners.size(); i++)
        {
            float distance = Vector3::Dot(plane.Normal, corners[i]) + plane.D;
            if ((distance <= 0.0 && lastdistance > 0.0) || (distance >= 0.0 && lastdistance < 0.0))
                return PlaneIntersectionType::Intersecting;
            lastdistance = distance;
        }

        if (lastdistance > 0.0)
            return PlaneIntersectionType::Front;

        return PlaneIntersectionType::Back;

    }

    float * BoundingBox::Intersects(Ray ray)
    {
        return ray.Intersects(*this);
    }

    bool BoundingBox::operator==(BoundingBox value){
       return (Min==value.Min && Max==value.Max);
    }


    bool BoundingBox::operator!=(BoundingBox value){
       return (Min!=value.Min || Max!=value.Max);
    }

    string BoundingBox::ToString(){
        stringstream out;
        out << "{{Min:" << Min.ToString() << " Max:" << Max.ToString() << "}}";
        return out.str();
    }

    
}

#endif