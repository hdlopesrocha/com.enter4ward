package hidrogine.math;

import hidrogine.math.api.IVector3;

// TODO: Auto-generated Javadoc
/**
 * The Class Quaternion.
 */
class Quaternion {

    /** The w. */
    private float X, Y, Z, W;

    /**
     * Instantiates a new quaternion.
     */
   public Quaternion() {
        X = Y = Z = W = 0;
    }

    /**
     * Instantiates a new quaternion.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @param z
     *            the z
     * @param w
     *            the w
     */
    public Quaternion(float x, float y, float z, float w) {
        X = x;
        Y = y;
        Z = z;
        W = w;
    }

    /**
     * Instantiates a new quaternion.
     *
     * @param vectorPart
     *            the vector part
     * @param scalarPart
     *            the scalar part
     */
    public Quaternion(IVector3 vectorPart, float scalarPart) {
        X = vectorPart.getX();
        Y = vectorPart.getY();
        Z = vectorPart.getZ();
        W = scalarPart;
    }

    /**
     * Identity.
     *
     * @return the quaternion
     */
    public Quaternion identity() {
        return new Quaternion(0, 0, 0, 1);
    }

    /**
     * Adds the.
     *
     * @param quaternion1
     *            the quaternion1
     * @param quaternion2
     *            the quaternion2
     * @return the quaternion
     */
    public Quaternion add(Quaternion quaternion1, Quaternion quaternion2) {
        quaternion1.X += quaternion2.X;
        quaternion1.Y += quaternion2.Y;
        quaternion1.Z += quaternion2.Z;
        quaternion1.W += quaternion2.W;
        return quaternion1;
    }

    /**
     * Concatenate.
     *
     * @param value1
     *            the value1
     * @param value2
     *            the value2
     * @return the quaternion
     */
    public Quaternion concatenate(Quaternion value1, Quaternion value2) {
        Quaternion quaternion = new Quaternion();
        quaternion.X = ((value2.X * value1.W) + (value1.X * value2.W))
                + (value2.Y * value1.Z) - (value2.Z * value1.Y);
        quaternion.Y = ((value2.Y * value1.W) + (value1.Y * value2.W))
                + (value2.Z * value1.X) - (value2.X * value1.Z);
        quaternion.Z = ((value2.Z * value1.W) + (value1.Z * value2.W))
                + (value2.X * value1.Y) - (value2.Y * value1.X);
        quaternion.W = (value2.W * value1.W)
                - ((value2.X * value1.X) + (value2.Y * value1.Y))
                + (value2.Z * value1.Z);
        return quaternion;
    }

    /**
     * Conjugate.
     */
    public void conjugate() {
        X = -X;
        Y = -Y;
        Z = -Z;
    }

    /**
     * Conjugate.
     *
     * @param value
     *            the value
     * @return the quaternion
     */
    public Quaternion conjugate(Quaternion value) {
        Quaternion quaternion = new Quaternion();
        quaternion.X = -value.X;
        quaternion.Y = -value.Y;
        quaternion.Z = -value.Z;
        quaternion.W = value.W;
        return quaternion;
    }

    /**
     * Creates the from yaw pitch roll.
     *
     * @param yaw
     *            the yaw
     * @param pitch
     *            the pitch
     * @param roll
     *            the roll
     * @return the quaternion
     */
    public static Quaternion createFromYawPitchRoll(float yaw, float pitch, float roll) {
        Quaternion quaternion = new Quaternion();
        quaternion.X = (((float) Math.cos((double) (yaw * 0.5)) * (float) Math
                .sin((double) (pitch * 0.5))) * (float) Math
                .cos((double) (roll * 0.5)))
                + (((float) Math.sin((double) (yaw * 0.5)) * (float) Math
                        .cos((double) (pitch * 0.5))) * (float) Math
                        .sin((double) (roll * 0.5)));
        quaternion.Y = (((float) Math.sin((double) (yaw * 0.5)) * (float) Math
                .cos((double) (pitch * 0.5))) * (float) Math
                .cos((double) (roll * 0.5)))
                - (((float) Math.cos((double) (yaw * 0.5)) * (float) Math
                        .sin((double) (pitch * 0.5))) * (float) Math
                        .sin((double) (roll * 0.5)));
        quaternion.Z = (((float) Math.cos((double) (yaw * 0.5)) * (float) Math
                .cos((double) (pitch * 0.5))) * (float) Math
                .sin((double) (roll * 0.5)))
                - (((float) Math.sin((double) (yaw * 0.5)) * (float) Math
                        .sin((double) (pitch * 0.5))) * (float) Math
                        .cos((double) (roll * 0.5)));
        quaternion.W = (((float) Math.cos((double) (yaw * 0.5)) * (float) Math
                .cos((double) (pitch * 0.5))) * (float) Math
                .cos((double) (roll * 0.5)))
                + (((float) Math.sin((double) (yaw * 0.5)) * (float) Math
                        .sin((double) (pitch * 0.5))) * (float) Math
                        .sin((double) (roll * 0.5)));
        return quaternion;
    }

    /**
     * Creates the from axis angle.
     *
     * @param axis
     *            the axis
     * @param angle
     *            the angle
     * @return the quaternion
     */
    public  Quaternion createFromAxisAngle(Vector3 axis, float angle) {
        float sin_a = (float) Math.sin(angle / 2.0);
        return new Quaternion(axis.getX() * sin_a, axis.getY() * sin_a,
                axis.getZ() * sin_a, (float) Math.cos(angle / 2.0));
    }

    /**
     * Creates the from rotation matrix.
     *
     * @param matrix
     *            the matrix
     * @return the quaternion
     */
    public Quaternion createFromRotationMatrix(Matrix matrix) {
        Quaternion result = new Quaternion();
        if ((matrix.M[0] + matrix.M[5] + matrix.M[10]) > 0.0) {
            float M1 = (float) Math.sqrt((double) (matrix.M[0] + matrix.M[5]
                    + matrix.M[10] + 1.0));
            result.W = M1 * 0.5f;
            M1 = 0.5f / M1;
            result.X = (matrix.M[6] - matrix.M[9]) * M1;
            result.Y = (matrix.M[8] - matrix.M[2]) * M1;
            result.Z = (matrix.M[1] - matrix.M[4]) * M1;
            return result;
        }
        if ((matrix.M[0] >= matrix.M[5]) && (matrix.M[0] >= matrix.M[10])) {
            float M2 = (float) Math.sqrt((double) (1.0 + matrix.M[0]
                    - matrix.M[5] - matrix.M[10]));
            float M3 = 0.5f / M2;
            result.X = 0.5f * M2;
            result.Y = (matrix.M[1] + matrix.M[4]) * M3;
            result.Z = (matrix.M[2] + matrix.M[8]) * M3;
            result.W = (matrix.M[6] - matrix.M[9]) * M3;
            return result;
        }
        if (matrix.M[5] > matrix.M[10]) {
            float M4 = (float) Math.sqrt((double) (1.0 + matrix.M[5]
                    - matrix.M[0] - matrix.M[10]));
            float M5 = 0.5f / M4;
            result.X = (matrix.M[4] + matrix.M[1]) * M5;
            result.Y = 0.5f * M4;
            result.Z = (matrix.M[9] + matrix.M[6]) * M5;
            result.W = (matrix.M[8] - matrix.M[2]) * M5;
            return result;
        }
        float M6 = (float) Math
                .sqrt((double) (1.0 + matrix.M[10] - matrix.M[0] - matrix.M[5]));
        float M7 = 0.5f / M6;
        result.X = (matrix.M[8] + matrix.M[2]) * M7;
        result.Y = (matrix.M[9] + matrix.M[6]) * M7;
        result.Z = 0.5f * M6;
        result.W = (matrix.M[1] - matrix.M[4]) * M7;
        return result;
    }

    /**
     * Divide.
     *
     * @param quaternion1
     *            the quaternion1
     * @param quaternion2
     *            the quaternion2
     * @return the quaternion
     */
    public Quaternion divide(Quaternion quaternion1, Quaternion quaternion2) {
        Quaternion result = new Quaternion();

        float w5 = 1f / ((quaternion2.X * quaternion2.X)
                + (quaternion2.Y * quaternion2.Y)
                + (quaternion2.Z * quaternion2.Z) + (quaternion2.W * quaternion2.W));
        float w4 = -quaternion2.X * w5;
        float w3 = -quaternion2.Y * w5;
        float w2 = -quaternion2.Z * w5;
        float w1 = quaternion2.W * w5;

        result.X = (quaternion1.X * w1) + (w4 * quaternion1.W)
                + ((quaternion1.Y * w2) - (quaternion1.Z * w3));
        result.Y = (quaternion1.Y * w1) + (w3 * quaternion1.W)
                + ((quaternion1.Z * w4) - (quaternion1.X * w2));
        result.Z = (quaternion1.Z * w1) + (w2 * quaternion1.W)
                + ((quaternion1.X * w3) - (quaternion1.Y * w4));
        result.W = (quaternion1.W * quaternion2.W * w5)
                - ((quaternion1.X * w4) + (quaternion1.Y * w3) + (quaternion1.Z * w2));
        return result;
    }

    /**
     * Dot.
     *
     * @param quaternion1
     *            the quaternion1
     * @param quaternion2
     *            the quaternion2
     * @return the float
     */
    public float dot(Quaternion quaternion1, Quaternion quaternion2) {
        return (quaternion1.X * quaternion2.X)
                + (quaternion1.Y * quaternion2.Y)
                + (quaternion1.Z * quaternion2.Z)
                + (quaternion1.W * quaternion2.W);
    }

    /**
     * Equals.
     *
     * @param other
     *            the other
     * @return true, if successful
     */
    public boolean equals(Quaternion other) {
        if ((X == other.X) && (Y == other.Y) && (Z == other.Z))
            return W == other.W;
        return false;
    }

    /**
     * Inverse.
     *
     * @param quaternion
     *            the quaternion
     * @return the quaternion
     */
    public Quaternion inverse(Quaternion quaternion) {
        Quaternion result = new Quaternion();
        float m1 = 1f / ((quaternion.X * quaternion.X)
                + (quaternion.Y * quaternion.Y) + (quaternion.Z * quaternion.Z) + (quaternion.W * quaternion.W));
        result.X = -quaternion.X * m1;
        result.Y = -quaternion.Y * m1;
        result.Z = -quaternion.Z * m1;
        result.W = quaternion.W * m1;
        return result;
    }

    /**
     * Length.
     *
     * @return the float
     */
    public float length() {
        return (float) Math
                .sqrt((double) ((X * X) + (Y * Y) + (Z * Z) + (W * W)));
    }

    /**
     * Length squared.
     *
     * @return the float
     */
    public float lengthSquared() {
        return (X * X) + (Y * Y) + (Z * Z) + (W * W);
    }

    /**
     * Lerp.
     *
     * @param quaternion1
     *            the quaternion1
     * @param quaternion2
     *            the quaternion2
     * @param amount
     *            the amount
     * @return the quaternion
     */
    public Quaternion lerp(Quaternion quaternion1, Quaternion quaternion2, float amount) {
        Quaternion result = new Quaternion();
        float f2 = 1f - amount;
        if (((quaternion1.X * quaternion2.X) + (quaternion1.Y * quaternion2.Y)
                + (quaternion1.Z * quaternion2.Z) + (quaternion1.W * quaternion2.W)) >= 0.0) {
            result.X = (f2 * quaternion1.X) + (amount * quaternion2.X);
            result.Y = (f2 * quaternion1.Y) + (amount * quaternion2.Y);
            result.Z = (f2 * quaternion1.Z) + (amount * quaternion2.Z);
            result.W = (f2 * quaternion1.W) + (amount * quaternion2.W);
        } else {
            result.X = (f2 * quaternion1.X) - (amount * quaternion2.X);
            result.Y = (f2 * quaternion1.Y) - (amount * quaternion2.Y);
            result.Z = (f2 * quaternion1.Z) - (amount * quaternion2.Z);
            result.W = (f2 * quaternion1.W) - (amount * quaternion2.W);
        }
        float f4 = (result.X * result.X) + (result.Y * result.Y)
                + (result.Z * result.Z) + (result.W * result.W);
        float f3 = 1f / (float) Math.sqrt((double) f4);
        result.X *= f3;
        result.Y *= f3;
        result.Z *= f3;
        result.W *= f3;
        return result;
    }

    /**
     * Slerp.
     *
     * @param quaternion1
     *            the quaternion1
     * @param quaternion2
     *            the quaternion2
     * @param amount
     *            the amount
     * @return the quaternion
     */
    public Quaternion slerp(Quaternion quaternion1, Quaternion quaternion2,
            float amount) {
        Quaternion result = new Quaternion();
        float q2, q3;

        float q4 = (quaternion1.X * quaternion2.X)
                + (quaternion1.Y * quaternion2.Y)
                + (quaternion1.Z * quaternion2.Z)
                + (quaternion1.W * quaternion2.W);
        boolean flag = false;
        if (q4 < 0.0) {
            flag = true;
            q4 = -q4;
        }
        if (q4 > 0.999999) {
            q3 = 1f - amount;
            q2 = flag ? -amount : amount;
        } else {
            float q5 = (float) Math.acos((double) q4);
            float q6 = (float) (1.0 / Math.sin((double) q5));
            q3 = (float) Math.sin((double) ((1.0 - amount) * q5)) * q6;
            q2 = flag ? (float) -Math.sin((double) (amount * q5)) * q6
                    : (float) Math.sin((double) (amount * q5)) * q6;
        }
        result.X = (q3 * quaternion1.X) + (q2 * quaternion2.X);
        result.Y = (q3 * quaternion1.Y) + (q2 * quaternion2.Y);
        result.Z = (q3 * quaternion1.Z) + (q2 * quaternion2.Z);
        result.W = (q3 * quaternion1.W) + (q2 * quaternion2.W);
        return result;
    }

    /**
     * Subtract.
     *
     * @param quaternion1
     *            the quaternion1
     * @param quaternion2
     *            the quaternion2
     * @return the quaternion
     */
    public Quaternion subtract(Quaternion quaternion1, Quaternion quaternion2) {
        quaternion1.X -= quaternion2.X;
        quaternion1.Y -= quaternion2.Y;
        quaternion1.Z -= quaternion2.Z;
        quaternion1.W -= quaternion2.W;
        return quaternion1;
    }

    /**
     * Multiply.
     *
     * @param quaternion1
     *            the quaternion1
     * @param quaternion2
     *            the quaternion2
     * @return the quaternion
     */
    public Quaternion multiply(Quaternion quaternion1, Quaternion quaternion2) {
        Quaternion result = new Quaternion();
        float f12 = (quaternion1.Y * quaternion2.Z)
                - (quaternion1.Z * quaternion2.Y);
        float f11 = (quaternion1.Z * quaternion2.X)
                - (quaternion1.X * quaternion2.Z);
        float f10 = (quaternion1.X * quaternion2.Y)
                - (quaternion1.Y * quaternion2.X);
        float f9 = (quaternion1.X * quaternion2.X)
                + (quaternion1.Y * quaternion2.Y)
                + (quaternion1.Z * quaternion2.Z);
        result.X = (quaternion1.X * quaternion2.W)
                + (quaternion2.X * quaternion1.W) + f12;
        result.Y = (quaternion1.Y * quaternion2.W)
                + (quaternion2.Y * quaternion1.W) + f11;
        result.Z = (quaternion1.Z * quaternion2.W)
                + (quaternion2.Z * quaternion1.W) + f10;
        result.W = (quaternion1.W * quaternion2.W) - f9;
        return result;
    }

    /**
     * Multiply.
     *
     * @param quaternion1
     *            the quaternion1
     * @param scaleFactor
     *            the scale factor
     * @return the quaternion
     */
    public Quaternion multiply(Quaternion quaternion1, float scaleFactor) {
        quaternion1.X *= scaleFactor;
        quaternion1.Y *= scaleFactor;
        quaternion1.Z *= scaleFactor;
        quaternion1.W *= scaleFactor;
        return quaternion1;
    }

    /**
     * Negate.
     *
     * @param quaternion
     *            the quaternion
     * @return the quaternion
     */
    public Quaternion negate(Quaternion quaternion) {
        Quaternion result = new Quaternion();
        result.X = -quaternion.X;
        result.Y = -quaternion.Y;
        result.Z = -quaternion.Z;
        result.W = -quaternion.W;
        return result;
    }

    /**
     * Normalize.
     */
    public void normalize() {
        float f1 = 1f / (float) Math
                .sqrt((double) ((X * X) + (Y * Y) + (Z * Z) + (W * W)));
        X *= f1;
        Y *= f1;
        Z *= f1;
        W *= f1;
    }

    /**
     * Normalize.
     *
     * @param quaternion
     *            the quaternion
     * @return the quaternion
     */
    public Quaternion normalize(Quaternion quaternion) {
        Quaternion result = new Quaternion();
        float f1 = 1f / (float) Math
                .sqrt((double) ((quaternion.X * quaternion.X)
                        + (quaternion.Y * quaternion.Y)
                        + (quaternion.Z * quaternion.Z) + (quaternion.W * quaternion.W)));
        result.X = quaternion.X * f1;
        result.Y = quaternion.Y * f1;
        result.Z = quaternion.Z * f1;
        result.W = quaternion.W * f1;
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return "{X:" + X + " Y:" + Y + " Z:" + Z + " W:" + W + "}";

    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }
    
    public float getZ() {
        return Z;
    }
    public float getW() {
        return W;
    }

}
