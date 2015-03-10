package hidrogine.math;

// TODO: Auto-generated Javadoc
//MIT License - Copyright (C) The Mono.Xna Team
//This file is subject to the terms and conditions defined in
//file 'LICENSE.txt', which is part of this source code package.
/**
 * The Class Ray.
 */
public class Ray {

    /** The Direction. */
    private Vector3 direction;

    /** The Position. */
    private Vector3 position;

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public Vector3 getDirection() {
        return direction;
    }

    /**
     * Sets the direction.
     *
     * @param direction
     *            the new direction
     */
    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position
     *            the new position
     */
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Instantiates a new ray.
     *
     * @param Vector3
     *            the position
     * @param Vector32
     *            the direction
     */
    public Ray(Vector3 Vector3, Vector3 Vector32) {
        this.position = Vector3;
        this.direction = Vector32;
    }

    /**
     * Equals.
     *
     * @param other
     *            the other
     * @return true, if successful
     */
    public boolean equals(Ray other) {
        return this.position.equals(other.position)
                && this.direction.equals(other.direction);
    }

    // adapted from
    // http://www.scratchapixel.com/lessons/3d-basic-lessons/lesson-7-intersecting-simple-shapes/ray-box-intersection/
    /**
     * Intersects.
     *
     * @param box
     *            the box
     * @return the float
     */
    public Float intersects(BoundingBox box) {
        float Epsilon = 1e-6f;

        Float tMin = null, tMax = null;

        if (Math.abs(direction.getX()) < Epsilon) {
            if (position.getX() < box.getMinX()
                    || position.getX() > box.getMaxX())
                return null;
        } else {
            tMin = (box.getMinX() - position.getX()) / direction.getX();
            tMax = (box.getMaxX() - position.getX()) / direction.getX();

            if (tMin > tMax) {
                float temp = tMin;
                tMin = tMax;
                tMax = temp;
            }
        }

        if (Math.abs(direction.getY()) < Epsilon) {
            if (position.getY() < box.getMinY()
                    || position.getY() > box.getMaxY())
                return null;
        } else {
            float tMinY = (box.getMinY() - position.getY())
                    / direction.getY();
            float tMaxY = (box.getMaxY() - position.getY())
                    / direction.getY();

            if (tMinY > tMaxY) {
                float temp = tMinY;
                tMinY = tMaxY;
                tMaxY = temp;
            }

            if ((tMin != null && tMin > tMaxY)
                    || (tMax != null && tMinY > tMax))
                return null;

            if (tMin == null || tMinY > tMin)
                tMin = tMinY;
            if (tMax == null || tMaxY < tMax)
                tMax = tMaxY;
        }

        if (Math.abs(direction.getZ()) < Epsilon) {
            if (position.getZ() < box.getMinZ()
                    || position.getZ() > box.getMaxZ())
                return null;
        } else {
            float tMinZ = (box.getMinZ() - position.getZ())
                    / direction.getZ();
            float tMaxZ = (box.getMaxZ() - position.getZ())
                    / direction.getZ();

            if (tMinZ > tMaxZ) {
                float temp = tMinZ;
                tMinZ = tMaxZ;
                tMaxZ = temp;
            }

            if ((tMin != null && tMin > tMaxZ)
                    || (tMax != null && tMinZ > tMax))
                return null;

            if (tMin == null || tMinZ > tMin)
                tMin = tMinZ;
            if (tMax == null || tMaxZ < tMax)
                tMax = tMaxZ;
        }

        // having a positive tMin and a negative tMax means the ray is inside
        // the box
        // we expect the intesection distance to be 0 in that case
        if ((tMin != null && tMin < 0) && tMax > 0)
            return 0f;

        // a negative tMin means that the intersection point is behind the ray's
        // origin
        // we discard these as not hitting the AABB
        if (tMin < 0)
            return null;

        return tMin;
    }

    /*
     * public float? Intersects(BoundingFrustum frustum) { if (frustum == null)
     * { throw new ArgumentNullException("frustum"); }
     * 
     * return frustum.Intersects(this); }
     */

    /**
     * Intersects.
     *
     * @param plane
     *            the plane
     * @return the float
     */
    public Float intersects(Plane plane) {
        // Vector3 nrm = new Vector3(direction).normalize();

        float den = plane.dotNormal(direction);
        if (Math.abs(den) > 0.000001f) {
            return -(plane.getDistance() + plane.dotNormal(position)) / den;
        }
        return null;
    }

    /**
     * Intersects.
     *
     * @param triangle
     *            the triangle
     * @return the i vector3
     */
    public Float intersects(final Triangle triangle) {
        final Float distance = intersects(triangle.getPlane());
        // if (distance != null && distance<= direction.length()) {
        // System.out.println("this:"+toString());

        if (distance != null && distance >= 0 && distance <= 1f) {
            Vector3 intersection = new Vector3(position).addMultiply(direction,
                    distance);
            /*
             * System.out.println("dist:"+distance);
             * System.out.println("tri:"+triangle.toString());
             * System.out.println("ray:"+toString());
             * System.out.println("inter:"+intersection);
             */
            if (triangle.contains(intersection)) {

                return distance;
            }
        }
        return null;
    }

    /**
     * Intersects.
     *
     * @param sphere
     *            the sphere
     * @return the float
     */
    public Float intersects(BoundingSphere sphere) {
        // Find the vector between where the ray starts the the sphere's centre
        Vector3 difference = new Vector3(sphere.getCenter())
                .subtract(this.position);

        float differenceLengthSquared = difference.lengthSquared();
        float sphereRadiusSquared = sphere.getRadius() * sphere.getRadius();

        float distanceAlongRay;

        // If the distance between the ray start and the sphere's centre is less
        // than
        // the radius of the sphere, it means we've intersected. N.B. checking
        // the LengthSquared is faster.
        if (differenceLengthSquared < sphereRadiusSquared) {
            return 0.0f;
        }

        distanceAlongRay = direction.dot(difference);
        // If the ray is pointing away from the sphere then we don't ever
        // intersect
        if (distanceAlongRay < 0) {
            return null;
        }

        // Next we kinda use Pythagoras to check if we are within the bounds of
        // the sphere
        // if x = radius of sphere
        // if y = distance between ray position and sphere centre
        // if z = the distance we've travelled along the ray
        // if x^2 + z^2 - y^2 < 0, we do not intersect
        float dist = sphereRadiusSquared + distanceAlongRay * distanceAlongRay
                - differenceLengthSquared;

        return (dist < 0) ? null : distanceAlongRay - (float) Math.sqrt(dist);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "{{Position:" + position.toString() + " Direction:"
                + direction.toString() + "}}";
    }

}
