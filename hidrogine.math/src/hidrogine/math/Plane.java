package hidrogine.math;

import hidrogine.math.api.IVector3;

public class Plane {

    public IVector3 Normal;
    public float D;
    
    public Plane()
    {
    }

    public Plane(Vector3 normal, float d)
    {
        Normal = normal;
        D = d;
    }

    public Plane(Vector3 a, Vector3 b, Vector3 c)
    {
        Vector3 ab = (Vector3) new Vector3(b).subtract(a);
        Vector3 ac = (Vector3) new Vector3(c).subtract(a);

        Normal = new Vector3(ab).cross(ac);
        D = -(Normal.dot(a));
        Normal.normalize();
    }

    public Plane(float a, float b, float c, float d)
    {
        this(new Vector3(a, b, c), d);
    }
/*
    Plane(Vector4 value)
    {
        *this = Plane(Vector3(value.X, value.Y, value.Z), value.W);
    }    
    float dot(Vector4 value)
    {
        return ((((Normal.X * value.X) + (Normal.Y * value.Y)) + (Normal.Z * value.Z)) + (D * value.W));
    }
*/
    public float dotCoordinate(Vector3 value)
    {
        return ((((Normal.getX() * value.getX()) + (Normal.getY() * value.getY())) + (Normal.getZ() * value.getZ())) + D);
    }

    public float dotNormal(Vector3 value)
    {
        return (((Normal.getX() * value.getX()) + (Normal.getY() * value.getY())) + (Normal.getZ() * value.getZ()));
    }

    public void normalize()
    {
                float factor;
                Normal.normalize();
                factor = (float)Math.sqrt(Normal.getX() * Normal.getX() + Normal.getY() * Normal.getY() + Normal.getZ() * Normal.getZ()) / 
                (float)Math.sqrt(Normal.getX() * Normal.getX() + Normal.getY() * Normal.getY() + Normal.getZ() * Normal.getZ());
                D = D * factor;
    }

 
    public boolean equals(Plane other)
    {
        return ((Normal == other.Normal) && (D == other.D));
    }

    public PlaneIntersectionType intersects(BoundingBox box)
    {
        return box.intersects(this);
    }

    public PlaneIntersectionType intersects(BoundingFrustum frustum)
    {
        return frustum.intersects(this);
    }

    public PlaneIntersectionType intersects(BoundingSphere sphere)
    {
        return sphere.intersects(this);
    }

    public String toString()
    {
        return "{{Notmal:" + Normal.toString() + " D:" + D + "}}";
           
    }


    
}
