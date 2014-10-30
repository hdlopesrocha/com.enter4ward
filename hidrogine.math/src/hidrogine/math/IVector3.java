package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class IVector3.
 */
public abstract class IVector3 {

    /**
     * Gets the z.
     *
     * @return the z
     */
    public abstract float getZ();

    /**
     * Sets the z.
     *
     * @param zz
     *            the new z
     * @return the vector3
     */
    public abstract IVector3 setZ(float zz);

    /**
     * Gets the x.
     *
     * @return the x
     */
    public abstract float getX();

    /**
     * Sets the x.
     *
     * @param xx
     *            the new x
     * @return the vector3
     */
    public abstract IVector3 setX(float xx);

    /**
     * Gets the y.
     *
     * @return the y
     */
    public abstract float getY();

    /**
     * Sets the y.
     *
     * @param yy
     *            the new y
     * @return the vector3
     */
    public abstract IVector3 setY(float yy);

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#add(hidrogine.math.IVector3)
     */
    /**
     * Adds the.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 add(final IVector3 vec) {
        setX(getX() + vec.getX());
        setY(getY() + vec.getY());
        setZ(getZ() + vec.getZ());
        return this;
    }

    public final IVector3 addMultiply(final IVector3 vec, float factor) {
        setX(getX() + vec.getX()*factor);
        setY(getY() + vec.getY()*factor);
        setZ(getZ() + vec.getZ()*factor);
        return this;
    }

    
    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#addX(float)
     */
    /**
     * Adds the x.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 addX(final float vec) {
        setX(getX() + vec);
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#addY(float)
     */
    /**
     * Adds the y.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 addY(final float vec) {
        setY(getY() + vec);
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#addZ(float)
     */
    /**
     * Adds the z.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 addZ(final float vec) {
        setZ(getZ() + vec);
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#subtract(hidrogine.math.IVector3)
     */
    /**
     * Subtract.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 subtract(final IVector3 vec) {
        setX(getX() - vec.getX());
        setY(getY() - vec.getY());
        setZ(getZ() - vec.getZ());
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#multiply(hidrogine.math.IVector3)
     */
    /**
     * Multiply.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 multiply(final IVector3 vec) {
        setX(getX() * vec.getX());
        setY(getY() * vec.getY());
        setZ(getZ() * vec.getZ());
        return this;
    }

    /**
     * Sets the.
     *
     * @param posX
     *            the pos x
     * @param posY
     *            the pos y
     * @param posZ
     *            the pos z
     * @return the i vector3
     */
    public IVector3 set(float posX, float posY, float posZ) {

        setX(posX);
        setY(posY);
        setZ(posZ);
        return this;
    }

    /**
     * Multiply.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 multiply(final float vec) {
        setX(getX() * vec);
        setY(getY() * vec);
        setZ(getZ() * vec);
        return this;
    }

    /**
     * Normalize.
     *
     * @return the i vector3
     */
    public IVector3 normalize() {
        divide(length());
        return this;

    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#divide(hidrogine.math.IVector3)
     */
    /**
     * Divide.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 divide(final IVector3 vec) {
        setX(getX() / vec.getX());
        setY(getY() / vec.getY());
        setZ(getZ() / vec.getZ());
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#divide(float)
     */
    /**
     * Divide.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public final IVector3 divide(final float vec) {
        setX(getX() / vec);
        setY(getY() / vec);
        setZ(getZ() / vec);
        return this;
    }

    /**
     * Sets the.
     *
     * @param vec
     *            the vec
     * @return the i vector3
     */
    public IVector3 set(IVector3 vec) {
        setX(vec.getX());
        setY(vec.getY());
        setZ(vec.getZ());
        return this;
    }

    /**
     * Dot.
     *
     * @param v
     *            the v
     * @return the float
     */
    public final float dot(final IVector3 v) {
        return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ();
    }

    /**
     * Cross.
     *
     * @param v
     *            the v
     * @return the i vector3
     */
    public final IVector3 cross(final IVector3 v) {
        float x = getY() * v.getZ() - v.getY() * getZ();
        float y = v.getX() * getZ() - getX() * v.getZ();
        float z = getX() * v.getY() - v.getX() * getY();
        setX(x);
        setY(y);
        setZ(z);
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#length()
     */
    /**
     * Length.
     *
     * @return the double
     */
    public float length() {
        return (float) Math.sqrt(getX() * getX() + getY() * getY() + getZ()
                * getZ());
    }

    public float lengthSquared() {
        return getX() * getX() + getY() * getY() + getZ()
                * getZ();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#distance(hidrogine.math.IVector3)
     */
    /**
     * Distance.
     *
     * @param vec
     *            the vec
     * @return the double
     */
    public double distance(IVector3 vec) {
        float x = getX() - vec.getX();
        float y = getY() - vec.getY();
        float z = getZ() - vec.getZ();
        return Math.sqrt(x * x + y * y + z * z);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#toString()
     */
    public String toString() {
        return "{X=" + getX() + ", Y=" + getY() + ", Z=" + getZ() + "}";
    }

    /**
     * Max.
     *
     * @param value2
     *            the value2
     * @return the i vector3
     */
    public IVector3 max(IVector3 value2) {

        setX(Math.max(getX(), value2.getX()));
        setY(Math.max(getY(), value2.getY()));
        setZ(Math.max(getZ(), value2.getZ()));
        return this;
    }

    /**
     * Min.
     *
     * @param min2
     *            the min2
     * @return the i vector3
     */
    public IVector3 min(IVector3 min2) {

        setX(Math.min(getX(), min2.getX()));
        setY(Math.min(getY(), min2.getY()));
        setZ(Math.min(getZ(), min2.getZ()));
        return this;
    }
    
    /**
     * Transform.
     *
     * @param rotation
     *            the rotation
     * @return the i vector3
     */

    public IVector3 transform(Quaternion rotation) {
        float x = 2 * (rotation.getY() * getZ() - rotation.getZ() * getY());
        float y = 2 * (rotation.getZ() * getX() - rotation.getX() * getZ());
        float z = 2 * (rotation.getX() * getY() - rotation.getY() * getX());
        float rx = getX() + x * rotation.getW()
                + (rotation.getY() * z - rotation.getZ() * y);
        float ry = getY() + y * rotation.getW()
                + (rotation.getZ() * x - rotation.getX() * z);
        float rz = getZ() + z * rotation.getW()
                + (rotation.getX() * y - rotation.getY() * x);
        setX(rx);
        setY(ry);
        setZ(rz);
        return this;
    }
}
