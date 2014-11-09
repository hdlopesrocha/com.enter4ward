#ifndef unilib_math_Ray
#define unilib_math_Ray


#include "Math.hpp"

namespace unilib
{
  
    Ray::Ray(Vector3 position, Vector3 direction)
    {
        Position = position;
        Direction = direction;
    }


    
    bool Ray::Equals(Ray other)
    {
        return Position.Equals(other.Position) && Direction.Equals(other.Direction);
    }
    
    int Ray::GetHashCode()
    {
        return Position.GetHashCode() + Direction.GetHashCode();
    }
    
    float* Ray::Intersects(BoundingBox box)
    {
		//first test if start in box
		if (Position.X >= box.Min.X
			&& Position.X <= box.Max.X
			&& Position.Y >= box.Min.Y
			&& Position.Y <= box.Max.Y
			&& Position.Z >= box.Min.Z
			&& Position.Z <= box.Max.Z)
			return new float(0.0);// here we concidere cube is full and origine is in cube so intersect at origine

		//Second we check each face
		Vector3 maxT = Vector3(-1.0);
		//Vector3 minT = Vector3(-1.0f);
		//calcul intersection with each faces
		if (Position.X < box.Min.X && Direction.X != 0.0)
			maxT.X = (box.Min.X - Position.X) / Direction.X;
		else if (Position.X > box.Max.X && Direction.X != 0.0)
			maxT.X = (box.Max.X - Position.X) / Direction.X;
		if (Position.Y < box.Min.Y && Direction.Y != 0.0)
			maxT.Y = (box.Min.Y - Position.Y) / Direction.Y;
		else if (Position.Y > box.Max.Y && Direction.Y != 0.0)
			maxT.Y = (box.Max.Y - Position.Y) / Direction.Y;
		if (Position.Z < box.Min.Z && Direction.Z != 0.0)
			maxT.Z = (box.Min.Z - Position.Z) / Direction.Z;
		else if (Position.Z > box.Max.Z && Direction.Z != 0.0)
			maxT.Z = (box.Max.Z - Position.Z) / Direction.Z;

		//get the maximum maxT
		if (maxT.X > maxT.Y && maxT.X > maxT.Z)
		{
			if (maxT.X < 0.0)
				return NULL;// ray go on opposite of face
			//coordonate of hit point of face of cube
			float coord = Position.Z + maxT.X * Direction.Z;
			// if hit point coord ( intersect face with ray) is out of other plane coord it miss 
			if (coord < box.Min.Z || coord > box.Max.Z)
				return NULL;
			coord = Position.Y + maxT.X * Direction.Y;
			if (coord < box.Min.Y || coord > box.Max.Y)
				return NULL;
			return new float(maxT.X);
		}
		if (maxT.Y > maxT.X && maxT.Y > maxT.Z)
		{
			if (maxT.Y < 0.0)
				return NULL;// ray go on opposite of face
			//coordonate of hit point of face of cube
			float coord = Position.Z + maxT.Y * Direction.Z;
			// if hit point coord ( intersect face with ray) is out of other plane coord it miss 
			if (coord < box.Min.Z || coord > box.Max.Z)
				return NULL;
			coord = Position.X + maxT.Y * Direction.X;
			if (coord < box.Min.X || coord > box.Max.X)
				return NULL;
			return new float(maxT.Y);
		}
		else //Z
		{
			if (maxT.Z < 0.0)
				return NULL;// ray go on opposite of face
			//coordonate of hit point of face of cube
			float coord = Position.X + maxT.Z * Direction.X;
			// if hit point coord ( intersect face with ray) is out of other plane coord it miss 
			if (coord < box.Min.X || coord > box.Max.X)
				return NULL;
			coord = Position.Y + maxT.Z * Direction.Y;
			if (coord < box.Min.Y || coord > box.Max.Y)
				return NULL;
			return new float(maxT.Z);
		}
    }

   
    float* Ray::Intersects(BoundingFrustum frustum)
    {
        throw "NotImplementedException";
    }

    float * Ray::Intersects(BoundingSphere sphere)
    {
        // Find the vector between where the ray starts the the sphere's centre
        Vector3 difference = sphere.Center - Position;

        float differenceLengthSquared = difference.LengthSquared();
        float sphereRadiusSquared = sphere.Radius * sphere.Radius;

        float distanceAlongRay;

        // If the distance between the ray start and the sphere's centre is less than
        // the radius of the sphere, it means we've intersected. N.B. checking the LengthSquared is faster.
        if (differenceLengthSquared < sphereRadiusSquared)
        {
            return new float(0.0);
        }

        distanceAlongRay = Vector3::Dot(Direction, difference);
        // If the ray is pointing away from the sphere then we don't ever intersect
        if (distanceAlongRay < 0)
        {
            return NULL;
        }

        // Next we kinda use Pythagoras to check if we are within the bounds of the sphere
        // if x = radius of sphere
        // if y = distance between ray position and sphere centre
        // if z = the distance we've travelled along the ray
        // if x^2 + z^2 - y^2 < 0, we do not intersect
        float dist = sphereRadiusSquared + distanceAlongRay * distanceAlongRay - differenceLengthSquared;
       return (dist < 0) ? NULL : new float(distanceAlongRay - (float)sqrt(dist));
    }

    float Ray::Intersects(Plane plane)
    {
    	float v_dot_n = Vector3::Dot(Direction,plane.Normal);
        float t = -1;
        if(v_dot_n<0){
        	t = -(Vector3::Dot(Position,plane.Normal)+plane.D)/v_dot_n;
        }
        return t;
    }


    bool Ray::operator==(Ray value){
       return (Position==value.Position && Direction==value.Direction);
    }


    bool Ray::operator!=(Ray value){
       return (Position!=value.Position || Direction!=value.Direction);
    }

    string Ray::ToString(){
        stringstream out;
        out << "{{Position:" << Position.ToString() << " Direction:" << Direction.ToString() << "}}";
        return out.str();
    }


}

#endif
