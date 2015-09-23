package com.enter4ward.math;

import java.nio.FloatBuffer;

// TODO: Auto-generated Javadoc
/**
 * The Class Matrix.
 */
public class Matrix {

    /** The Constant IDENTITY. */
    public static final Matrix IDENTITY = new Matrix().identity();

    /** The m. */
    public float[] M = new float[16];

    /**
     * Instantiates a new matrix.
     */
    public Matrix() {
        M[0] = M[1] = M[2] = M[3] = M[4] = M[5] = M[6] = M[7] = M[8] = M[9] = M[10] = M[11] = M[12] = M[13] = M[14] = M[15] = 0f;
    }

    /**
     * Instantiates a new matrix.
     *
     * @param m
     *            the m
     */
    public Matrix(Matrix m) {
        System.arraycopy(m.M, 0, M, 0, 16);

    }

  
    /**
     * Instantiates a new matrix.
     *
     * @param m
     *            the m
     */
    public Matrix(float[] m) {
        System.arraycopy(m, 0, M, 0, 16);

    }

    /**
     * Gets the.
     *
     * @param i
     *            the i
     * @return the float
     */
    public float get(int i) {
        return M[i];
    }

    /**
     * Instantiates a new matrix.
     *
     * @param m11
     *            the m11
     * @param m12
     *            the m12
     * @param m13
     *            the m13
     * @param m14
     *            the m14
     * @param m21
     *            the m21
     * @param m22
     *            the m22
     * @param m23
     *            the m23
     * @param m24
     *            the m24
     * @param m31
     *            the m31
     * @param m32
     *            the m32
     * @param m33
     *            the m33
     * @param m34
     *            the m34
     * @param m41
     *            the m41
     * @param m42
     *            the m42
     * @param m43
     *            the m43
     * @param m44
     *            the m44
     */
    public Matrix(float m11, float m12, float m13, float m14, float m21,
            float m22, float m23, float m24, float m31, float m32, float m33,
            float m34, float m41, float m42, float m43, float m44) {
        M[0] = m11;
        M[1] = m12;
        M[2] = m13;
        M[3] = m14;
        M[4] = m21;
        M[5] = m22;
        M[6] = m23;
        M[7] = m24;
        M[8] = m31;
        M[9] = m32;
        M[10] = m33;
        M[11] = m34;
        M[12] = m41;
        M[13] = m42;
        M[14] = m43;
        M[15] = m44;
    }

    /**
     * Identity.
     *
     * @return the matrix
     */
    public Matrix identity() {
        M[0] = 1;
        M[1] = 0;
        M[2] = 0;
        M[3] = 0;
        M[4] = 0;
        M[5] = 1;
        M[6] = 0;
        M[7] = 0;
        M[8] = 0;
        M[9] = 0;
        M[10] = 1;
        M[11] = 0;
        M[12] = 0;
        M[13] = 0;
        M[14] = 0;
        M[15] = 1;
        return this;
    }

    private static final Vector3 TEMP_BACKWARD = new Vector3();

    /**
     * Gets the backward.
     *
     * @return the backward
     */
    public Vector3 getBackward() {
        return TEMP_BACKWARD.set(M[8], M[9], M[10]);
    }

    /**
     * Sets the backward.
     *
     * @param value
     *            the new backward
     */
    public void setBackward(Vector3 value) {
        M[8] = value.getX();
        M[9] = value.getY();
        M[10] = value.getZ();
    }

    private static final Vector3 TEMP_DOWN = new Vector3();

    /**
     * Gets the down.
     *
     * @return the down
     */
    public Vector3 getDown() {
        return TEMP_DOWN.set(-M[4], -M[5], -M[6]);
    }

    /**
     * Sets the down.
     *
     * @param value
     *            the new down
     */
    public void setDown(Vector3 value) {
        M[4] = -value.getX();
        M[5] = -value.getY();
        M[6] = -value.getZ();
    }

    private static final Vector3 TEMP_FORWARD = new Vector3();

    /**
     * Gets the forward.
     *
     * @return the forward
     */
    public Vector3 getForward() {
        return TEMP_FORWARD.set(-M[8], -M[9], -M[10]);
    }

    /**
     * Sets the forward.
     *
     * @param value
     *            the new forward
     */
    public void setForward(Vector3 value) {
        M[8] = -value.getX();
        M[9] = -value.getY();
        M[10] = -value.getZ();
    }

    private static final Vector3 TEMP_LEFT = new Vector3();

    /**
     * Gets the left.
     *
     * @return the left
     */
    public Vector3 getLeft() {
        return TEMP_LEFT.set(-M[0], -M[1], -M[2]);
    }

    /**
     * Sets the left.
     *
     * @param value
     *            the new left
     */
    public void setLeft(Vector3 value) {
        M[0] = -value.getX();
        M[1] = -value.getY();
        M[2] = -value.getZ();
    }

    private static final Vector3 TEMP_RIGHT = new Vector3();

    /**
     * Gets the right.
     *
     * @return the right
     */
    public Vector3 getRight() {
        return TEMP_RIGHT.set(M[0], M[1], M[2]);
    }

    /**
     * Sets the right.
     *
     * @param value
     *            the new right
     */
    public void setRight(Vector3 value) {
        M[0] = value.getX();
        M[1] = value.getY();
        M[2] = value.getZ();
    }

    private static final Vector3 TEMP_TRANSLATION = new Vector3();

    /**
     * Gets the translation.
     *
     * @return the translation
     */
    public Vector3 getTranslation() {
        return TEMP_TRANSLATION.set(M[12], M[13], M[14]);
    }

    /**
     * Sets the translation.
     *
     * @param value
     *            the new translation
     */
    public void setTranslation(Vector3 value) {
        M[12] = value.getX();
        M[13] = value.getY();
        M[14] = value.getZ();
    }

    private static final Vector3 TEMP_UP = new Vector3();

    /**
     * Gets the up.
     *
     * @return the up
     */
    public Vector3 getUp() {
        return TEMP_UP.set(M[4], M[5], M[6]);
    }

    /**
     * Sets the up.
     *
     * @param value
     *            the new up
     */
    public void setUp(Vector3 value) {
        M[4] = value.getX();
        M[5] = value.getY();
        M[6] = value.getZ();
    }

    private static final Vector3 TEMP_WORLD_X = new Vector3();
    private static final Vector3 TEMP_WORLD_Y = new Vector3();
    private static final Vector3 TEMP_WORLD_Z = new Vector3();

    /**
     * Creates the world.
     *
     * @param position
     *            the position
     * @param forward
     *            the forward
     * @param up
     *            the up
     * @return the matrix
     */
    public Matrix createWorld(Vector3 position, Vector3 forward, Vector3 up) {
        Vector3 x, y, z;

        z = TEMP_WORLD_X.set(forward).normalize();
        x = TEMP_WORLD_Y.set(forward).cross(up);
        y = TEMP_WORLD_Z.set(x).cross(forward);

        x.normalize();
        y.normalize();

        clear();
        setRight(x);
        setUp(y);
        setForward(z);
        setTranslation(position);
        M[15] = 1f;

        return this;
    }

    /**
     * Creates the from yaw pitch roll.
     *
     * @return the matrix
     */

    private static final Quaternion TEMP_QUATERNION2 = new Quaternion();

    /**
     * Creates the from yaw pitch roll.
     *
     * @param yaw
     *            the yaw
     * @param pitch
     *            the pitch
     * @param roll
     *            the roll
     * @return the matrix
     */
    public Matrix createFromYawPitchRoll(float yaw, float pitch, float roll) {
        return createFromQuaternion(TEMP_QUATERNION2.createFromYawPitchRoll(
                yaw, pitch, roll));
    }

    /**
     * Transform.
     *
     * @param rotation
     *            the rotation
     * @return the matrix
     */
    private static Matrix TEMP_TRANSFORM = new Matrix();

    public Matrix transform(Quaternion rotation) {
        return multiply(TEMP_TRANSFORM.createFromQuaternion(rotation));
    }

    /**
     * Decompose.
     *
     * @param scale
     *            the scale
     * @param rotation
     *            the rotation
     * @param translation
     *            the translation
     * @return true, if successful
     */
    private static Matrix TEMP_DECOMPOSE = new Matrix();

    public boolean decompose(Vector3 scale, Quaternion rotation,
            Vector3 translation) {
        translation.setX(M[12]);
        translation.setY(M[13]);
        translation.setZ(M[14]);
        float xs, ys, zs;

        if (M[0] * M[1] * M[2] * M[3] < 0)
            xs = -1f;
        else
            xs = 1f;

        if (M[4] * M[5] * M[6] * M[7] < 0)
            ys = -1f;
        else
            ys = 1f;

        if (M[8] * M[9] * M[10] * M[11] < 0)
            zs = -1f;
        else
            zs = 1f;

        scale.setX(xs
                * (float) Math.sqrt(M[0] * M[0] + M[1] * M[1] + M[2] * M[2]));
        scale.setY(ys
                * (float) Math.sqrt(M[4] * M[4] + M[5] * M[5] + M[6] * M[6]));
        scale.setZ(zs
                * (float) Math.sqrt(M[8] * M[8] + M[9] * M[9] + M[10] * M[10]));

        if (scale.getX() == 0.0 || scale.getY() == 0.0 || scale.getZ() == 0.0) {
            rotation.identity();
            return false;
        }

        Matrix m1 = TEMP_DECOMPOSE.set(M[0] / scale.getX(),
                M[1] / scale.getX(), M[2] / scale.getX(), 0,
                M[4] / scale.getY(), M[5] / scale.getY(), M[6] / scale.getY(),
                0, M[8] / scale.getZ(), M[9] / scale.getZ(),
                M[10] / scale.getZ(), 0, 0, 0, 0, 1);

        rotation.createFromRotationMatrix(m1);
        return true;
    }

    /**
     * Sets the.
     *
     * @param m11
     *            the m11
     * @param m12
     *            the m12
     * @param m13
     *            the m13
     * @param m14
     *            the m14
     * @param m21
     *            the m21
     * @param m22
     *            the m22
     * @param m23
     *            the m23
     * @param m24
     *            the m24
     * @param m31
     *            the m31
     * @param m32
     *            the m32
     * @param m33
     *            the m33
     * @param m34
     *            the m34
     * @param m41
     *            the m41
     * @param m42
     *            the m42
     * @param m43
     *            the m43
     * @param m44
     *            the m44
     * @return the matrix
     */
    public Matrix set(float m11, float m12, float m13, float m14, float m21,
            float m22, float m23, float m24, float m31, float m32, float m33,
            float m34, float m41, float m42, float m43, float m44) {
        M[0] = m11;
        M[1] = m12;
        M[2] = m13;
        M[3] = m14;
        M[4] = m21;
        M[5] = m22;
        M[6] = m23;
        M[7] = m24;
        M[8] = m31;
        M[9] = m32;
        M[10] = m33;
        M[11] = m34;
        M[12] = m41;
        M[13] = m42;
        M[14] = m43;
        M[15] = m44;
        return this;
    }

    /**
     * Adds the.
     *
     * @param matrix1
     *            the matrix1
     * @param matrix2
     *            the matrix2
     * @return the matrix
     */
    public Matrix add(Matrix matrix2) {
        M[0] += matrix2.M[0];
        M[1] += matrix2.M[1];
        M[2] += matrix2.M[2];
        M[3] += matrix2.M[3];
        M[4] += matrix2.M[4];
        M[5] += matrix2.M[5];
        M[6] += matrix2.M[6];
        M[7] += matrix2.M[7];
        M[8] += matrix2.M[8];
        M[9] += matrix2.M[9];
        M[10] += matrix2.M[10];
        M[11] += matrix2.M[11];
        M[12] += matrix2.M[12];
        M[13] += matrix2.M[13];
        M[14] += matrix2.M[14];
        M[15] += matrix2.M[15];
        return this;
    }

    /**
     * Creates the billboard.
     *
     * @param objectPosition
     *            the object position
     * @param cameraPosition
     *            the camera position
     * @param cameraUpVector
     *            the camera up vector
     * @param cameraForwardVector
     *            the camera forward vector
     * @return the matrix
     */
    public Matrix createBillboard(Vector3 objectPosition,
            Vector3 cameraPosition, Vector3 cameraUpVector,
            Vector3 cameraForwardVector) {
        Vector3 translation = TEMP_TRANSLATION.set(objectPosition).subtract(
                cameraPosition);
        Vector3 backwards, right, up;
        backwards = TEMP_BACKWARD.set(translation).normalize();
        up = TEMP_UP.set(cameraUpVector).normalize();
        right = TEMP_RIGHT.set(backwards).cross(up);
        up = TEMP_UP.set(backwards).cross(right);
        identity();
        setBackward(backwards);
        setRight(right);
        setUp(up);
        setTranslation(translation);
        return this;
    }

    /**
     * Creates the from quaternion.
     *
     * @param quaternion
     *            the quaternion
     * @return the matrix
     */
    public Matrix createFromQuaternion(Quaternion quaternion) {
        identity();

        M[0] = 1 - 2 * (quaternion.getY() * quaternion.getY() + quaternion
                .getZ() * quaternion.getZ());
        M[1] = 2 * (quaternion.getX() * quaternion.getY() + quaternion.getW()
                * quaternion.getZ());
        M[2] = 2 * (quaternion.getX() * quaternion.getZ() - quaternion.getW()
                * quaternion.getY());
        M[4] = 2 * (quaternion.getX() * quaternion.getY() - quaternion.getW()
                * quaternion.getZ());
        M[5] = 1 - 2 * (quaternion.getX() * quaternion.getX() + quaternion
                .getZ() * quaternion.getZ());
        M[6] = 2 * (quaternion.getY() * quaternion.getZ() + quaternion.getW()
                * quaternion.getX());
        M[8] = 2 * (quaternion.getX() * quaternion.getZ() + quaternion.getW()
                * quaternion.getY());
        M[9] = 2 * (quaternion.getY() * quaternion.getZ() - quaternion.getW()
                * quaternion.getX());
        M[10] = 1 - 2 * (quaternion.getX() * quaternion.getX() + quaternion
                .getY() * quaternion.getY());
        return this;
    }

    /**
     * Creates the look at.
     *
     * @param cameraPosition
     *            the camera position
     * @param cameraDirection
     *            the camera direction
     * @param cameraUpVector
     *            the camera up vector
     * @return the matrix
     */

    private static final Vector3 TEMP_LOOK_X = new Vector3();
    private static final Vector3 TEMP_LOOK_Y = new Vector3();
    private static final Vector3 TEMP_LOOK_Z = new Vector3();

    /**
     * Creates the look at.
     *
     * @param cameraPosition
     *            the camera position
     * @param cameraDirection
     *            the camera direction
     * @param cameraUpVector
     *            the camera up vector
     * @return the matrix
     */
    public Matrix createLookAt(Vector3 cameraPosition, Vector3 cameraDirection,
            Vector3 cameraUpVector) {
        identity();
        // http://msdn.microsoft.com/en-us/library/bb205343(v=VS.85).aspx

        Vector3 vz = TEMP_LOOK_Z.set(cameraDirection).multiply(-1f);
        Vector3 vx = TEMP_LOOK_X.set(cameraUpVector).cross(vz).normalize();
        Vector3 vy = TEMP_LOOK_Y.set(vz).cross(vx);

        M[0] = vx.getX();
        M[1] = vy.getX();
        M[2] = vz.getX();
        M[4] = vx.getY();
        M[5] = vy.getY();
        M[6] = vz.getY();
        M[8] = vx.getZ();
        M[9] = vy.getZ();
        M[10] = vz.getZ();
        M[12] = -vx.dot(cameraPosition);
        M[13] = -vy.dot(cameraPosition);
        M[14] = -vz.dot(cameraPosition);
        return this;
    }

    /**
     * Creates the orthographic.
     *
     * @param width
     *            the width
     * @param height
     *            the height
     * @param zNearPlane
     *            the z near plane
     * @param zFarPlane
     *            the z far plane
     * @return the matrix
     */
    public Matrix createOrthographic(float width, float height,
            float zNearPlane, float zFarPlane) {
        M[0] = 2 / width;
        M[1] = 0;
        M[2] = 0;
        M[3] = 0;
        M[4] = 0;
        M[5] = 2 / height;
        M[6] = 0;
        M[7] = 0;
        M[8] = 0;
        M[9] = 0;
        M[10] = 1 / (zNearPlane - zFarPlane);
        M[11] = 0;
        M[12] = 0;
        M[13] = 0;
        M[14] = zNearPlane / (zNearPlane - zFarPlane);
        M[15] = 1;
        return this;
    }

    /**
     * Creates the orthographic off center.
     *
     * @param left
     *            the left
     * @param right
     *            the right
     * @param bottom
     *            the bottom
     * @param top
     *            the top
     * @param zNearPlane
     *            the z near plane
     * @param zFarPlane
     *            the z far plane
     * @return the matrix
     */
    public Matrix createOrthographicOffCenter(float left, float right,
            float bottom, float top, float zNearPlane, float zFarPlane) {
        M[0] = 2 / (right - left);
        M[1] = 0;
        M[2] = 0;
        M[3] = 0;
        M[4] = 0;
        M[5] = 2 / (top - bottom);
        M[6] = 0;
        M[7] = 0;
        M[8] = 0;
        M[9] = 0;
        M[10] = 1 / (zNearPlane - zFarPlane);
        M[11] = 0;
        M[12] = (left + right) / (left - right);
        M[13] = (bottom + top) / (bottom - top);
        M[14] = zNearPlane / (zNearPlane - zFarPlane);
        M[15] = 1;
        return this;
    }

    /**
     * Clear.
     */
    private void clear() {
        for (int i = 0; i < 16; ++i) {
            M[i] = 0f;
        }
    }

    /**
     * Creates the perspective field of view.
     *
     * @param fieldOfView
     *            the field of view
     * @param aspectRatio
     *            the aspect ratio
     * @param nearPlaneDistance
     *            the near plane distance
     * @param farPlaneDistance
     *            the far plane distance
     * @return the matrix
     */
    public Matrix createPerspectiveFieldOfView(float fieldOfView,
            float aspectRatio, float nearPlaneDistance, float farPlaneDistance) {
        clear();
        if (fieldOfView < 0 || fieldOfView > Math.PI)
            throw new RuntimeException(
                    "fieldOfView, fieldOfView takes a value between 0 and Pi (180 degrees) in radians.");

        if (nearPlaneDistance <= 0.0f)
            throw new RuntimeException(
                    "nearPlaneDistance, You should specify positive value for nearPlaneDistance.");

        if (farPlaneDistance <= 0.0f)
            throw new RuntimeException(
                    "farPlaneDistance, You should specify positive value for farPlaneDistance.");

        if (farPlaneDistance <= nearPlaneDistance)
            throw new RuntimeException(
                    "nearPlaneDistance, Near plane distance is larger than Far plane distance. Near plane distance must be smaller than Far plane distance.");

        float yscale = (float) 1 / (float) Math.tan(fieldOfView / 2);
        float xscale = yscale / aspectRatio;

        M[0] = xscale;
        M[5] = yscale;
        M[10] = farPlaneDistance / (nearPlaneDistance - farPlaneDistance);
        M[11] = -1;
        M[14] = nearPlaneDistance * farPlaneDistance
                / (nearPlaneDistance - farPlaneDistance);
        return this;
    }

    /**
     * Creates the rotation x.
     *
     * @param radians
     *            the radians
     * @return the matrix
     */
    public Matrix createRotationX(float radians) {
        identity();
        M[5] = (float) Math.cos(radians);
        M[6] = (float) Math.sin(radians);
        M[9] = -M[6];
        M[10] = M[5];

        return this;
    }

    /**
     * Creates the rotation y.
     *
     * @param radians
     *            the radians
     * @return the matrix
     */
    public Matrix createRotationY(float radians) {
        identity();

        M[0] = (float) Math.cos(radians);
        M[2] = (float) Math.sin(radians);
        M[8] = -M[2];
        M[10] = M[0];

        return this;
    }

    /**
     * Creates the rotation z.
     *
     * @param radians
     *            the radians
     * @return the matrix
     */
    public Matrix createRotationZ(float radians) {
        identity();

        M[0] = (float) Math.cos(radians);
        M[1] = (float) Math.sin(radians);
        M[4] = -M[1];
        M[5] = M[0];

        return this;
    }

    /**
     * Creates the scale.
     *
     * @param scale
     *            the scale
     * @return the matrix
     */
    public Matrix createScale(float scale) {
        identity();

        M[0] = scale;
        M[5] = scale;
        M[10] = scale;

        return this;
    }

    /**
     * Creates the scale.
     *
     * @param xScale
     *            the x scale
     * @param yScale
     *            the y scale
     * @param zScale
     *            the z scale
     * @return the matrix
     */
    public Matrix createScale(float xScale, float yScale, float zScale) {
        identity();

        M[0] = xScale;
        M[5] = yScale;
        M[10] = zScale;

        return this;
    }

    /**
     * Creates the scale.
     *
     * @param scales
     *            the scales
     * @return the matrix
     */
    public Matrix createScale(Vector3 scales) {
        identity();

        M[0] = scales.getX();
        M[5] = scales.getY();
        M[10] = scales.getZ();

        return this;
    }

    /**
     * Creates the translation.
     *
     * @param xPosition
     *            the x position
     * @param yPosition
     *            the y position
     * @param zPosition
     *            the z position
     * @return the matrix
     */
    public Matrix createTranslation(float xPosition, float yPosition,
            float zPosition) {
        identity();

        M[12] = xPosition;
        M[13] = yPosition;
        M[14] = zPosition;

        return this;
    }

    /**
     * Creates the translation.
     *
     * @param position
     *            the position
     * @return the matrix
     */
    public Matrix createTranslation(Vector3 position) {
        identity();
        setTranslation(position);

        return this;
    }

    private static Matrix TEMP_INVERSE = new Matrix();
    private static Matrix TEMP_COPY = new Matrix();

    /**
     * Divide.
     *
     * @param matrix2
     *            the matrix2
     * @return the matrix
     */
    public Matrix divide(Matrix matrix2) {
        Matrix inverse = TEMP_INVERSE.set(matrix2).invert();
        Matrix matrix1 = TEMP_COPY.set(this);

        M[0] = matrix1.M[0] * inverse.M[0] + matrix1.M[1] * inverse.M[4]
                + matrix1.M[2] * inverse.M[8] + matrix1.M[3] * inverse.M[12];
        M[1] = matrix1.M[0] * inverse.M[1] + matrix1.M[1] * inverse.M[5]
                + matrix1.M[2] * inverse.M[9] + matrix1.M[3] * inverse.M[13];
        M[2] = matrix1.M[0] * inverse.M[2] + matrix1.M[1] * inverse.M[6]
                + matrix1.M[2] * inverse.M[10] + matrix1.M[3] * inverse.M[14];
        M[3] = matrix1.M[0] * inverse.M[3] + matrix1.M[1] * inverse.M[7]
                + matrix1.M[2] * inverse.M[11] + matrix1.M[3] * inverse.M[15];

        M[4] = matrix1.M[4] * inverse.M[0] + matrix1.M[5] * inverse.M[4]
                + matrix1.M[6] * inverse.M[8] + matrix1.M[7] * inverse.M[12];
        M[5] = matrix1.M[4] * inverse.M[1] + matrix1.M[5] * inverse.M[5]
                + matrix1.M[6] * inverse.M[9] + matrix1.M[7] * inverse.M[13];
        M[6] = matrix1.M[4] * inverse.M[2] + matrix1.M[5] * inverse.M[6]
                + matrix1.M[6] * inverse.M[10] + matrix1.M[7] * inverse.M[14];
        M[7] = matrix1.M[4] * inverse.M[3] + matrix1.M[5] * inverse.M[7]
                + matrix1.M[6] * inverse.M[11] + matrix1.M[7] * inverse.M[15];

        M[8] = matrix1.M[8] * inverse.M[0] + matrix1.M[9] * inverse.M[4]
                + matrix1.M[10] * inverse.M[8] + matrix1.M[11] * inverse.M[12];
        M[9] = matrix1.M[8] * inverse.M[1] + matrix1.M[9] * inverse.M[5]
                + matrix1.M[10] * inverse.M[9] + matrix1.M[11] * inverse.M[13];
        M[10] = matrix1.M[8] * inverse.M[2] + matrix1.M[9] * inverse.M[6]
                + matrix1.M[10] * inverse.M[10] + matrix1.M[11] * inverse.M[14];
        M[11] = matrix1.M[8] * inverse.M[3] + matrix1.M[9] * inverse.M[7]
                + matrix1.M[10] * inverse.M[11] + matrix1.M[11] * inverse.M[15];

        M[12] = matrix1.M[12] * inverse.M[0] + matrix1.M[13] * inverse.M[4]
                + matrix1.M[14] * inverse.M[8] + matrix1.M[15] * inverse.M[12];
        M[13] = matrix1.M[12] * inverse.M[1] + matrix1.M[13] * inverse.M[5]
                + matrix1.M[14] * inverse.M[9] + matrix1.M[15] * inverse.M[13];
        M[14] = matrix1.M[12] * inverse.M[2] + matrix1.M[13] * inverse.M[6]
                + matrix1.M[14] * inverse.M[10] + matrix1.M[15] * inverse.M[14];
        M[15] = matrix1.M[12] * inverse.M[3] + matrix1.M[13] * inverse.M[7]
                + matrix1.M[14] * inverse.M[11] + matrix1.M[15] * inverse.M[15];

        return this;
    }

    /**
     * Divide.
     *
     * @param matrix1
     *            the matrix1
     * @param divider
     *            the divider
     * @return the matrix
     */
    public Matrix divide(float divider) {
        float inverseDivider = 1f / divider;

        M[0] *= inverseDivider;
        M[1] *= inverseDivider;
        M[2] *= inverseDivider;
        M[3] *= inverseDivider;
        M[4] *= inverseDivider;
        M[5] *= inverseDivider;
        M[6] *= inverseDivider;
        M[7] *= inverseDivider;
        M[8] *= inverseDivider;
        M[9] *= inverseDivider;
        M[10] *= inverseDivider;
        M[11] *= inverseDivider;
        M[12] *= inverseDivider;
        M[13] *= inverseDivider;
        M[14] *= inverseDivider;
        M[15] *= inverseDivider;

        return this;
    }

    /**
     * Invert.
     *
     * @return the matrix
     */
    public Matrix invert() {
        // Use Laplace expansion theorem to calculate the inverse of a 4x4
        // matrix
        //
        // 1. Calculate the 2x2 determinants needed and the 4x4 determinant
        // based on the 2x2 determinants
        // 2. Create the adjugate matrix, which satisfies: A * adj(A) = det(A) *
        // I
        // 3. Divide adjugate matrix with the determinant to find the inverse

        float det1 = M[0] * M[5] - M[1] * M[4];
        float det2 = M[0] * M[6] - M[2] * M[4];
        float det3 = M[0] * M[7] - M[3] * M[4];
        float det4 = M[1] * M[6] - M[2] * M[5];
        float det5 = M[1] * M[7] - M[3] * M[5];
        float det6 = M[2] * M[7] - M[3] * M[6];
        float det7 = M[8] * M[13] - M[9] * M[12];
        float det8 = M[8] * M[14] - M[10] * M[12];
        float det9 = M[8] * M[15] - M[11] * M[12];
        float det10 = M[9] * M[14] - M[10] * M[13];
        float det11 = M[9] * M[15] - M[11] * M[13];
        float det12 = M[10] * M[15] - M[11] * M[14];

        float detMatrix = (float) (det1 * det12 - det2 * det11 + det3 * det10
                + det4 * det9 - det5 * det8 + det6 * det7);

        float invDetMatrix = 1f / detMatrix;

        float M0 = (M[5] * det12 - M[6] * det11 + M[7] * det10) * invDetMatrix;
        float M1 = (-M[1] * det12 + M[2] * det11 - M[3] * det10) * invDetMatrix;
        float M2 = (M[13] * det6 - M[14] * det5 + M[15] * det4) * invDetMatrix;
        float M3 = (-M[9] * det6 + M[10] * det5 - M[11] * det4) * invDetMatrix;
        float M4 = (-M[4] * det12 + M[6] * det9 - M[7] * det8) * invDetMatrix;
        float M5 = (M[0] * det12 - M[2] * det9 + M[3] * det8) * invDetMatrix;
        float M6 = (-M[12] * det6 + M[14] * det3 - M[15] * det2) * invDetMatrix;
        float M7 = (M[8] * det6 - M[10] * det3 + M[11] * det2) * invDetMatrix;
        float M8 = (M[4] * det11 - M[5] * det9 + M[7] * det7) * invDetMatrix;
        float M9 = (-M[0] * det11 + M[1] * det9 - M[3] * det7) * invDetMatrix;
        float M10 = (M[12] * det5 - M[13] * det3 + M[15] * det1) * invDetMatrix;
        float M11 = (-M[8] * det5 + M[9] * det3 - M[11] * det1) * invDetMatrix;
        float M12 = (-M[4] * det10 + M[5] * det8 - M[6] * det7) * invDetMatrix;
        float M13 = (M[0] * det10 - M[1] * det8 + M[2] * det7) * invDetMatrix;
        float M14 = (-M[12] * det4 + M[13] * det2 - M[14] * det1)
                * invDetMatrix;
        float M15 = (M[8] * det4 - M[9] * det2 + M[10] * det1) * invDetMatrix;

        M[0] = M0;
        M[1] = M1;
        M[2] = M2;
        M[3] = M3;
        M[4] = M4;
        M[5] = M5;
        M[6] = M6;
        M[7] = M7;
        M[8] = M8;
        M[9] = M9;
        M[10] = M10;
        M[11] = M11;
        M[12] = M12;
        M[13] = M13;
        M[14] = M14;
        M[15] = M15;

        return this;
    }

    /*
     * 
     * Matrix Matrix::Lerp(Matrix matrix1, Matrix matrix2, float amount) { throw
     * "not implemented!"; }
     */

    /**
     * Multiply.
     *
     * @param matrix2
     *            the matrix2
     * @return the matrix
     */
    public Matrix multiply(Matrix matrix2) {

        float M0 = M[0] * matrix2.M[0] + M[1] * matrix2.M[4] + M[2]
                * matrix2.M[8] + M[3] * matrix2.M[12];
        float M1 = M[0] * matrix2.M[1] + M[1] * matrix2.M[5] + M[2]
                * matrix2.M[9] + M[3] * matrix2.M[13];
        float M2 = M[0] * matrix2.M[2] + M[1] * matrix2.M[6] + M[2]
                * matrix2.M[10] + M[3] * matrix2.M[14];
        float M3 = M[0] * matrix2.M[3] + M[1] * matrix2.M[7] + M[2]
                * matrix2.M[11] + M[3] * matrix2.M[15];

        float M4 = M[4] * matrix2.M[0] + M[5] * matrix2.M[4] + M[6]
                * matrix2.M[8] + M[7] * matrix2.M[12];
        float M5 = M[4] * matrix2.M[1] + M[5] * matrix2.M[5] + M[6]
                * matrix2.M[9] + M[7] * matrix2.M[13];
        float M6 = M[4] * matrix2.M[2] + M[5] * matrix2.M[6] + M[6]
                * matrix2.M[10] + M[7] * matrix2.M[14];
        float M7 = M[4] * matrix2.M[3] + M[5] * matrix2.M[7] + M[6]
                * matrix2.M[11] + M[7] * matrix2.M[15];

        float M8 = M[8] * matrix2.M[0] + M[9] * matrix2.M[4] + M[10]
                * matrix2.M[8] + M[11] * matrix2.M[12];
        float M9 = M[8] * matrix2.M[1] + M[9] * matrix2.M[5] + M[10]
                * matrix2.M[9] + M[11] * matrix2.M[13];
        float M10 = M[8] * matrix2.M[2] + M[9] * matrix2.M[6] + M[10]
                * matrix2.M[10] + M[11] * matrix2.M[14];
        float M11 = M[8] * matrix2.M[3] + M[9] * matrix2.M[7] + M[10]
                * matrix2.M[11] + M[11] * matrix2.M[15];

        float M12 = M[12] * matrix2.M[0] + M[13] * matrix2.M[4] + M[14]
                * matrix2.M[8] + M[15] * matrix2.M[12];
        float M13 = M[12] * matrix2.M[1] + M[13] * matrix2.M[5] + M[14]
                * matrix2.M[9] + M[15] * matrix2.M[13];
        float M14 = M[12] * matrix2.M[2] + M[13] * matrix2.M[6] + M[14]
                * matrix2.M[10] + M[15] * matrix2.M[14];
        float M15 = M[12] * matrix2.M[3] + M[13] * matrix2.M[7] + M[14]
                * matrix2.M[11] + M[15] * matrix2.M[15];

        M[0] = M0;
        M[1] = M1;
        M[2] = M2;
        M[3] = M3;
        M[4] = M4;
        M[5] = M5;
        M[6] = M6;
        M[7] = M7;
        M[8] = M8;
        M[9] = M9;
        M[10] = M10;
        M[11] = M11;
        M[12] = M12;
        M[13] = M13;
        M[14] = M14;
        M[15] = M15;
        return this;
    }

    /**
     * Multiply.
     *
     * @param matrix1
     *            the matrix1
     * @param factor
     *            the factor
     * @return the matrix
     */
    public Matrix multiply(float factor) {
        M[0] *= factor;
        M[1] *= factor;
        M[2] *= factor;
        M[3] *= factor;
        M[4] *= factor;
        M[5] *= factor;
        M[6] *= factor;
        M[7] *= factor;
        M[8] *= factor;
        M[9] *= factor;
        M[10] *= factor;
        M[11] *= factor;
        M[12] *= factor;
        M[13] *= factor;
        M[14] *= factor;
        M[15] *= factor;
        return this;
    }

    /**
     * Negate.
     *
     * @param matrix
     *            the matrix
     * @return the matrix
     */
    public Matrix negate() {
        M[0] = -M[0];
        M[1] = -M[1];
        M[2] = -M[2];
        M[3] = -M[3];
        M[4] = -M[4];
        M[5] = -M[5];
        M[6] = -M[6];
        M[7] = -M[7];
        M[8] = -M[8];
        M[9] = -M[9];
        M[10] = -M[10];
        M[11] = -M[11];
        M[12] = -M[12];
        M[13] = -M[13];
        M[14] = -M[14];
        M[15] = -M[15];
        return this;
    }

    /**
     * Subtract.
     *
     * @param matrix1
     *            the matrix1
     * @param matrix2
     *            the matrix2
     * @return the matrix
     */
    public Matrix subtract(Matrix matrix2) {
        M[0] -= matrix2.M[0];
        M[1] -= matrix2.M[1];
        M[2] -= matrix2.M[2];
        M[3] -= matrix2.M[3];
        M[4] -= matrix2.M[4];
        M[5] -= matrix2.M[5];
        M[6] -= matrix2.M[6];
        M[7] -= matrix2.M[7];
        M[8] -= matrix2.M[8];
        M[9] -= matrix2.M[9];
        M[10] -= matrix2.M[10];
        M[11] -= matrix2.M[11];
        M[12] -= matrix2.M[12];
        M[13] -= matrix2.M[13];
        M[14] -= matrix2.M[14];
        M[15] -= matrix2.M[15];
        return this;
    }

    /**
     * Transpose.
     *
     * @return the matrix
     */
    public Matrix transpose() {
        Matrix copy = TEMP_COPY;
        copy.set(this);
        M[0] = copy.M[0];
        M[1] = copy.M[4];
        M[2] = copy.M[8];
        M[3] = copy.M[12];

        M[4] = copy.M[1];
        M[5] = copy.M[5];
        M[6] = copy.M[9];
        M[7] = copy.M[13];

        M[8] = copy.M[2];
        M[9] = copy.M[6];
        M[10] = copy.M[10];
        M[11] = copy.M[14];

        M[12] = copy.M[3];
        M[13] = copy.M[7];
        M[14] = copy.M[11];
        M[15] = copy.M[15];

        return this;
    }

    /**
     * Determinant.
     *
     * @return the float
     */
    public float determinant() {
        float minor1, minor2, minor3, minor4, minor5, minor6;

        minor1 = M[8] * M[13] - M[9] * M[12];
        minor2 = M[8] * M[14] - M[10] * M[12];
        minor3 = M[8] * M[15] - M[11] * M[12];
        minor4 = M[9] * M[14] - M[10] * M[13];
        minor5 = M[9] * M[15] - M[11] * M[13];
        minor6 = M[10] * M[15] - M[11] * M[14];

        return M[0] * (M[5] * minor6 - M[6] * minor5 + M[7] * minor4) - M[1]
                * (M[4] * minor6 - M[6] * minor3 + M[7] * minor2) + M[2]
                * (M[4] * minor5 - M[5] * minor3 + M[7] * minor1) - M[3]
                * (M[4] * minor4 - M[5] * minor2 + M[6] * minor1);
    }

    /**
     * Equals.
     *
     * @param other
     *            the other
     * @return true, if successful
     */
    public boolean equals(Object obj) {
        if (obj instanceof Matrix) {
            Matrix other = (Matrix) obj;
            return (M[0] == other.M[0]) && (M[1] == other.M[1])
                    && (M[2] == other.M[2]) && (M[3] == other.M[3])
                    && (M[4] == other.M[4]) && (M[5] == other.M[5])
                    && (M[6] == other.M[6]) && (M[7] == other.M[7])
                    && (M[8] == other.M[8]) && (M[9] == other.M[9])
                    && (M[10] == other.M[10]) && (M[11] == other.M[11])
                    && (M[12] == other.M[12]) && (M[13] == other.M[13])
                    && (M[14] == other.M[14]) && (M[15] == other.M[15]);
        }
        return false;
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {

        return "{ {M[0]:" + M[0] + " M[1]:" + M[1] + " M[2]:" + M[2] + " M[3]:"
                + M[3] + "}" + " {M[4]:" + M[4] + " M[5]:" + M[5] + " M[6]:"
                + M[6] + " M[7]:" + M[7] + "}" + " {M[8]:" + M[8] + " M[9]:"
                + M[9] + " M[10]:" + M[10] + " M[11]:" + M[11] + "}"
                + " {M[12]:" + M[12] + " M[13]:" + M[13] + " M[14]:" + M[14]
                + " M[15]:" + M[15] + "} }";
    }

    /**
     * Store.
     *
     * @param buffer
     *            the buffer
     */
    public void store(FloatBuffer buffer) {
        buffer.put(M);
    }

    /**
     * Load.
     *
     * @param matrix
     *            the matrix
     * @return the matrix
     */
    public Matrix set(Matrix matrix) {
        System.arraycopy(matrix.M, 0, M, 0, 16);
        return this;
    }

    /**
     * Sets the.
     *
     * @param matrix
     *            the matrix
     */
    public void set(float[] matrix) {
        System.arraycopy(matrix, 0, M, 0, 16);

    }

    private static final Matrix TEMP_TRANSLATE = new Matrix();

    /**
     * Translate.
     *
     * @param min
     *            the min
     * @return the matrix
     */
    public Matrix translate(Vector3 min) {
        multiply(TEMP_TRANSLATE.createTranslation(min));
        return this;
    }

    /**
     * Translate.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @param z
     *            the z
     * @return the matrix
     */

    public Matrix translate(float x, float y, float z) {
        multiply(TEMP_TRANSLATE.createTranslation(x, y, z));
        return this;
    }

    private static final Matrix TEMP_SCALE = new Matrix();

    /**
     * Scale.
     *
     * @param dim
     *            the dim
     * @return the matrix
     */
    public Matrix scale(Vector3 dim) {
        multiply(TEMP_SCALE.createScale(dim));
        return this;
    }

    /**
     * Scale.
     *
     * @param dim
     *            the dim
     * @return the matrix
     */
    public Matrix scale(float dim) {
        multiply(TEMP_SCALE.createScale(dim));
        return this;
    }

}
