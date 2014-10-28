package hidrogine.math;

// TODO: Auto-generated Javadoc
/**
 * The Class Vector3.
 */
public class Vector3 extends IVector3 {

    /** The x. */
    private float x;

    /** The y. */
    private float y;

    /** The y. */
    private float z;

    /**
     * Instantiates a new vector3.
     */
    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * Instantiates a new vector3.
     *
     * @param xx
     *            the x
     * @param yy
     *            the y
     * @param zz
     *            the zz
     */
    public Vector3(final float xx, final float yy, final float zz) {
        x = xx;
        y = yy;
        z = zz;
    }

    /**
     * Instantiates a new vector3.
     *
     * @param vec
     *            the vec
     */
    public Vector3(final IVector3 vec) {
        x = vec.getX();
        y = vec.getY();
        z = vec.getZ();
    }

    /**
     * Instantiates a new vector3.
     *
     * @param value
     *            the value
     */
    public Vector3(float value) {
        x = value;
        y = value;
        z = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#getZ()
     */
    @Override
    public final float getZ() {
        return z;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#setZ(float)
     */
    @Override
    public final IVector3 setZ(final float zz) {
        z = zz;

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#getX()
     */
    @Override
    public final float getX() {
        return x;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#setX(float)
     */
    @Override
    public IVector3 setX(final float xx) {
        x = xx;
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#getY()
     */
    @Override
    public final float getY() {
        return y;
    }

    /*
     * (non-Javadoc)
     * 
     * @see hidrogine.math.IVector3#setY(float)
     */
    @Override
    public final IVector3 setY(final float yy) {
        y = yy;
        return this;
    }

    /**
     * Transform.
     *
     * @param frontVec
     *            the front vec
     * @param matrix
     *            the matrix
     * @return the i vector3
     */
    public static IVector3 transform(IVector3 frontVec, Matrix matrix) {
        Vector3 result;

        result = new Vector3((frontVec.getX() * matrix.M[0])
                + (frontVec.getY() * matrix.M[4])
                + (frontVec.getZ() * matrix.M[8]) + matrix.M[12],
                (frontVec.getX() * matrix.M[1])
                        + (frontVec.getY() * matrix.M[5])
                        + (frontVec.getZ() * matrix.M[9]) + matrix.M[13],
                (frontVec.getX() * matrix.M[1])
                        + (frontVec.getY() * matrix.M[6])
                        + (frontVec.getZ() * matrix.M[10]) + matrix.M[14]);

        return result;
    }

    /**
     * Transform.
     *
     * @param value
     *            the value
     * @param rotation
     *            the rotation
     * @return the i vector3
     */
    public static IVector3 transform(IVector3 value, Quaternion rotation) {
        Vector3 u = new Vector3(rotation.getX(), rotation.getY(),
                rotation.getZ());
        float s = rotation.getW();
        IVector3 p1 = new Vector3(u).multiply(2f).multiply(u.dot(value));
        IVector3 p2 = new Vector3(value).multiply(s * s - u.dot(u));
        IVector3 p3 = new Vector3(u).cross(value).multiply(2.0f * s);
        return p1.add(p2).add(p3);
    }

}
