#ifndef unilib_math_Plane
#define unilib_math_Plane

#include "Math.hpp"

namespace unilib
{
    Plane::Plane()
    {
    }

    Plane::Plane(Vector3 normal, float d)
    {
        Normal = normal;
        D = d;
    }

    Plane::Plane(Vector3 a, Vector3 b, Vector3 c)
    {
        Vector3 ab = b - a;
        Vector3 ac = c - a;

        Vector3 cross = Vector3::Cross(ab, ac);
        Normal = Vector3::Normalize(cross);
        D = -(Vector3::Dot(cross, a));
    }

    Plane::Plane(float a, float b, float c, float d)
    {
        *this = Plane(Vector3(a, b, c), d);
    }

    Plane::Plane(Vector4 value)
    {
        *this = Plane(Vector3(value.X, value.Y, value.Z), value.W);
    }    

    float Plane::Dot(Vector4 value)
    {
        return ((((Normal.X * value.X) + (Normal.Y * value.Y)) + (Normal.Z * value.Z)) + (D * value.W));
    }

    float Plane::DotCoordinate(Vector3 value)
    {
        return ((((Normal.X * value.X) + (Normal.Y * value.Y)) + (Normal.Z * value.Z)) + D);
    }

    float Plane::DotNormal(Vector3 value)
    {
        return (((Normal.X * value.X) + (Normal.Y * value.Y)) + (Normal.Z * value.Z));
    }

    void Plane::Normalize()
    {
		float factor;
		Normal = Vector3::Normalize(Normal);
		factor = (float)sqrt(Normal.X * Normal.X + Normal.Y * Normal.Y + Normal.Z * Normal.Z) / 
		(float)sqrt(Normal.X * Normal.X + Normal.Y * Normal.Y + Normal.Z * Normal.Z);
		D = D * factor;
    }

    Plane Plane::Normalize(Plane value)
    {
		Plane result;
        float factor;
        result.Normal = Vector3::Normalize(value.Normal);
        factor = (float)sqrt(result.Normal.X * result.Normal.X + result.Normal.Y * result.Normal.Y + result.Normal.Z * result.Normal.Z) / 
        (float)sqrt(value.Normal.X * value.Normal.X + value.Normal.Y * value.Normal.Y + value.Normal.Z * value.Normal.Z);
        result.D = value.D * factor;
		return result;
    }


    bool Plane::operator==(Plane value){
       return (Normal==value.Normal && D==value.D);
    }


    bool Plane::operator!=(Plane value){
       return (Normal!=value.Normal || D!=value.D);
    }

    bool Plane::Equals(Plane other)
    {
        return ((Normal == other.Normal) && (D == other.D));
    }

    int Plane::GetHashCode()
    {
        return Normal.GetHashCode() + D;
    }

    PlaneIntersectionType::PlaneIntersectionType Plane::Intersects(BoundingBox box)
    {
        return box.Intersects(*this);
    }

    PlaneIntersectionType::PlaneIntersectionType Plane::Intersects(BoundingFrustum frustum)
    {
        return frustum.Intersects(*this);
    }

    PlaneIntersectionType::PlaneIntersectionType Plane::Intersects(BoundingSphere sphere)
    {
        return sphere.Intersects(*this);
    }

    string Plane::ToString()
    {
        stringstream out;
        out << "{{Notmal:" << Normal.ToString() << " D:" << D << "}}";
        return out.str();    
    }


}

#endif
