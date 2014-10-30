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
    private Vector3 Direction;

    /** The Position. */
    private Vector3 Position;

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public Vector3 getDirection() {
        return Direction;
    }

    /**
     * Sets the direction.
     *
     * @param direction
     *            the new direction
     */
    public void setDirection(Vector3 direction) {
        Direction = direction;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Vector3 getPosition() {
        return Position;
    }

    /**
     * Sets the position.
     *
     * @param position
     *            the new position
     */
    public void setPosition(Vector3 position) {
        Position = position;
    }

    /**
     * Instantiates a new ray.
     *
     * @param position
     *            the position
     * @param direction
     *            the direction
     */
    public Ray(Vector3 position, Vector3 direction) {
        this.Position = position;
        this.Direction = direction;
    }

    /**
     * Equals.
     *
     * @param other
     *            the other
     * @return true, if successful
     */
    public boolean equals(Ray other) {
        return this.Position.equals(other.Position)
                && this.Direction.equals(other.Direction);
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

        if (Math.abs(Direction.getX()) < Epsilon) {
            if (Position.getX() < box.getMin().getX()
                    || Position.getX() > box.getMax().getX())
                return null;
        } else {
            tMin = (box.getMin().getX() - Position.getX()) / Direction.getX();
            tMax = (box.getMax().getX() - Position.getX()) / Direction.getX();

            if (tMin > tMax) {
                float temp = tMin;
                tMin = tMax;
                tMax = temp;
            }
        }

        if (Math.abs(Direction.getY()) < Epsilon) {
            if (Position.getY() < box.getMin().getY()
                    || Position.getY() > box.getMax().getY())
                return null;
        } else {
            float tMinY = (box.getMin().getY() - Position.getY())
                    / Direction.getY();
            float tMaxY = (box.getMax().getY() - Position.getY())
                    / Direction.getY();

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

        if (Math.abs(Direction.getZ()) < Epsilon) {
            if (Position.getZ() < box.getMin().getZ()
                    || Position.getZ() > box.getMax().getZ())
                return null;
        } else {
            float tMinZ = (box.getMin().getZ() - Position.getZ())
                    / Direction.getZ();
            float tMaxZ = (box.getMax().getZ() - Position.getZ())
                    / Direction.getZ();

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
        Float result = null;
        float den = Direction.dot(plane.getNormal());
        if (Math.abs(den) < 0.00001f) {
            return null;

        }

        result = (-plane.getDistance() - plane.getNormal().dot(Position)) / den;

        if (result < 0.0f) {
            if (result < -0.00001f) {
                return null;
            }

            result = 0.0f;
        }
        return result;
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
        IVector3 difference = new Vector3(sphere.getCenter())
                .subtract(this.Position);

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

        distanceAlongRay = Direction.dot(difference);
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
        return "{{Position:" + Position.toString() + " Direction:"
                + Direction.toString() + "}}";
    }

}
