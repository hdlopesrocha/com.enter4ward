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
     * @param matrix
     *            the matrix
     * @return the i vector3
     */
    public IVector3 transform(Matrix matrix) {

        float x = (getX() * matrix.M[0]) + (getY() * matrix.M[4])
                + (getZ() * matrix.M[8]) + matrix.M[12];

        float y = (getX() * matrix.M[1]) + (getY() * matrix.M[5])
                + (getZ() * matrix.M[9]) + matrix.M[13];

        float z = (getX() * matrix.M[1]) + (getY() * matrix.M[6])
                + (getZ() * matrix.M[10]) + matrix.M[14];

        setX(x);
        setY(y);
        setZ(z);

        return this;
    }



}
