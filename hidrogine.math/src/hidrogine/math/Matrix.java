package hidrogine.math;

import hidrogine.math.api.IVector3;

// TODO: Auto-generated Javadoc
/**
 * The Class Matrix.
 */
class Matrix {

    /** The m. */
    float[] M = new float[16];

    /**
     * Instantiates a new matrix.
     */
    public Matrix() {
        M[0] = M[1] = M[2] = M[3] = M[4] = M[5] = M[6] = M[7] = M[8] = M[9] = M[10] = M[11] = M[12] = M[13] = M[14] = M[15] = 0f;
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
    public static Matrix identity() {
        return new Matrix(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }

    /**
     * Gets the backward.
     *
     * @return the backward
     */
    Vector3 getBackward() {
        return new Vector3(M[8], M[9], M[10]);
    }

    /**
     * Sets the backward.
     *
     * @param value
     *            the new backward
     */
    void setBackward(IVector3 value) {
        M[8] = value.getX();
        M[9] = value.getY();
        M[10] = value.getZ();
    }

    /**
     * Gets the down.
     *
     * @return the down
     */
    IVector3 getDown() {
        return new Vector3(-M[4], -M[5], -M[6]);
    }

    /**
     * Sets the down.
     *
     * @param value
     *            the new down
     */
    void setDown(IVector3 value) {
        M[4] = -value.getX();
        M[5] = -value.getY();
        M[6] = -value.getZ();
    }

    /**
     * Gets the forward.
     *
     * @return the forward
     */
    IVector3 getForward() {
        return new Vector3(-M[8], -M[9], -M[10]);
    }

    /**
     * Sets the forward.
     *
     * @param value
     *            the new forward
     */
    void setForward(IVector3 value) {
        M[8] = -value.getX();
        M[9] = -value.getY();
        M[10] = -value.getZ();
    }

    /**
     * Gets the left.
     *
     * @return the left
     */
    IVector3 getLeft() {
        return new Vector3(-M[0], -M[1], -M[2]);
    }

    /**
     * Sets the left.
     *
     * @param value
     *            the new left
     */
    void setLeft(IVector3 value) {
        M[0] = -value.getX();
        M[1] = -value.getY();
        M[2] = -value.getZ();
    }

    /**
     * Gets the right.
     *
     * @return the right
     */
    IVector3 getRight() {
        return new Vector3(M[0], M[1], M[2]);
    }

    /**
     * Sets the right.
     *
     * @param value
     *            the new right
     */
    void setRight(IVector3 value) {
        M[0] = value.getX();
        M[1] = value.getY();
        M[2] = value.getZ();
    }

    /**
     * Gets the translation.
     *
     * @return the translation
     */
    IVector3 getTranslation() {
        return new Vector3(M[12], M[13], M[14]);
    }

    /**
     * Sets the translation.
     *
     * @param value
     *            the new translation
     */
    void setTranslation(IVector3 value) {
        M[12] = value.getX();
        M[13] = value.getY();
        M[14] = value.getZ();
    }

    /**
     * Gets the up.
     *
     * @return the up
     */
    IVector3 getUp() {
        return new Vector3(M[4], M[5], M[6]);
    }

    /**
     * Sets the up.
     *
     * @param value
     *            the new up
     */
    void setUp(IVector3 value) {
        M[4] = value.getX();
        M[5] = value.getY();
        M[6] = value.getZ();
    }

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
    static Matrix createWorld(Vector3 position, Vector3 forward, Vector3 up) {
        Matrix result;
        IVector3 x, y, z;

        z = Vector3.normalize(forward, new Vector3());
        x = Vector3.cross(forward, up, new Vector3());
        y = Vector3.cross(x, forward, new Vector3());

        x.normalize();
        y.normalize();

        result = new Matrix();
        result.setRight(x);
        result.setUp(y);
        result.setForward(z);
        result.setTranslation(position);
        result.M[15] = 1f;

        return result;
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
     * @return the matrix
     */
    Matrix createFromYawPitchRoll(float yaw, float pitch, float roll) {
        Matrix matrix;
        Quaternion quaternion;
        quaternion = Quaternion.createFromYawPitchRoll(yaw, pitch, roll);
        matrix = createFromQuaternion(quaternion);
        return matrix;
    }

    /**
     * Transform.
     *
     * @param value
     *            the value
     * @param rotation
     *            the rotation
     * @return the matrix
     */
    Matrix transform(Matrix value, Quaternion rotation) {
        Matrix matrix = createFromQuaternion(rotation);
        Matrix result = multiply(value, matrix);
        return result;
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
    boolean decompose(Vector3 scale, Quaternion rotation, Vector3 translation) {
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
            rotation = Quaternion.identity();
            return false;
        }

        Matrix m1 = new Matrix(M[0] / scale.getX(), M[1] / scale.getX(), M[2]
                / scale.getX(), 0, M[4] / scale.getY(), M[5] / scale.getY(),
                M[6] / scale.getY(), 0, M[8] / scale.getZ(), M[9]
                        / scale.getZ(), M[10] / scale.getZ(), 0, 0, 0, 0, 1);

        rotation = Quaternion.createFromRotationMatrix(m1);
        return true;
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
    Matrix add(Matrix matrix1, Matrix matrix2) {
        matrix1.M[0] += matrix2.M[0];
        matrix1.M[1] += matrix2.M[1];
        matrix1.M[2] += matrix2.M[2];
        matrix1.M[3] += matrix2.M[3];
        matrix1.M[4] += matrix2.M[4];
        matrix1.M[5] += matrix2.M[5];
        matrix1.M[6] += matrix2.M[6];
        matrix1.M[7] += matrix2.M[7];
        matrix1.M[8] += matrix2.M[8];
        matrix1.M[9] += matrix2.M[9];
        matrix1.M[10] += matrix2.M[10];
        matrix1.M[11] += matrix2.M[11];
        matrix1.M[12] += matrix2.M[12];
        matrix1.M[13] += matrix2.M[13];
        matrix1.M[14] += matrix2.M[14];
        matrix1.M[15] += matrix2.M[15];
        return matrix1;
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
    Matrix createBillboard(Vector3 objectPosition, Vector3 cameraPosition,
            Vector3 cameraUpVector, Vector3 cameraForwardVector) {
        Matrix result = Matrix.identity();
        IVector3 translation = new Vector3(objectPosition)
                .subtract(cameraPosition);
        IVector3 backwards, right, up;
        backwards = Vector3.normalize(translation, new Vector3());
        up = Vector3.normalize(cameraUpVector, new Vector3());
        right = Vector3.cross(backwards, up, new Vector3());
        up = Vector3.cross(backwards, right, new Vector3());
        result = Matrix.identity();
        result.setBackward(backwards);
        result.setRight(right);
        result.setUp(up);
        result.setTranslation(translation);
        return result;
    }

    /**
     * Creates the from quaternion.
     *
     * @param quaternion
     *            the quaternion
     * @return the matrix
     */
    Matrix createFromQuaternion(Quaternion quaternion) {
        Matrix result = Matrix.identity();

        result.M[0] = 1 - 2 * (quaternion.getY() * quaternion.getY() + quaternion
                .getZ() * quaternion.getZ());
        result.M[1] = 2 * (quaternion.getX() * quaternion.getY() + quaternion
                .getW() * quaternion.getZ());
        result.M[2] = 2 * (quaternion.getX() * quaternion.getZ() - quaternion
                .getW() * quaternion.getY());
        result.M[4] = 2 * (quaternion.getX() * quaternion.getY() - quaternion
                .getW() * quaternion.getZ());
        result.M[5] = 1 - 2 * (quaternion.getX() * quaternion.getX() + quaternion
                .getZ() * quaternion.getZ());
        result.M[6] = 2 * (quaternion.getY() * quaternion.getZ() + quaternion
                .getW() * quaternion.getX());
        result.M[8] = 2 * (quaternion.getX() * quaternion.getZ() + quaternion
                .getW() * quaternion.getY());
        result.M[9] = 2 * (quaternion.getY() * quaternion.getZ() - quaternion
                .getW() * quaternion.getX());
        result.M[10] = 1 - 2 * (quaternion.getX() * quaternion.getX() + quaternion
                .getY() * quaternion.getY());
        return result;
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
    Matrix createLookAt(Vector3 cameraPosition, Vector3 cameraDirection,
            Vector3 cameraUpVector) {
        Matrix result = Matrix.identity();
        // http://msdn.microsoft.com/en-us/library/bb205343(v=VS.85).aspx

        IVector3 vz = new Vector3(cameraDirection).multiply(-1f);
        IVector3 vx = Vector3.cross(cameraUpVector, vz, new Vector3())
                .normalize();
        IVector3 vy = Vector3.cross(vz, vx, new Vector3());

        result.M[0] = vx.getX();
        result.M[1] = vy.getX();
        result.M[2] = vz.getX();
        result.M[4] = vx.getY();
        result.M[5] = vy.getY();
        result.M[6] = vz.getY();
        result.M[8] = vx.getZ();
        result.M[9] = vy.getZ();
        result.M[10] = vz.getZ();
        result.M[12] = -Vector3.dot(vx, cameraPosition);
        result.M[13] = -Vector3.dot(vy, cameraPosition);
        result.M[14] = -Vector3.dot(vz, cameraPosition);
        return result;
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
    Matrix createOrthographic(float width, float height, float zNearPlane,
            float zFarPlane) {
        Matrix result = new Matrix();
        result.M[0] = 2 / width;
        result.M[1] = 0;
        result.M[2] = 0;
        result.M[3] = 0;
        result.M[4] = 0;
        result.M[5] = 2 / height;
        result.M[6] = 0;
        result.M[7] = 0;
        result.M[8] = 0;
        result.M[9] = 0;
        result.M[10] = 1 / (zNearPlane - zFarPlane);
        result.M[11] = 0;
        result.M[12] = 0;
        result.M[13] = 0;
        result.M[14] = zNearPlane / (zNearPlane - zFarPlane);
        result.M[15] = 1;
        return result;
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
    Matrix createOrthographicOffCenter(float left, float right, float bottom,
            float top, float zNearPlane, float zFarPlane) {
        Matrix result = new Matrix();
        result.M[0] = 2 / (right - left);
        result.M[1] = 0;
        result.M[2] = 0;
        result.M[3] = 0;
        result.M[4] = 0;
        result.M[5] = 2 / (top - bottom);
        result.M[6] = 0;
        result.M[7] = 0;
        result.M[8] = 0;
        result.M[9] = 0;
        result.M[10] = 1 / (zNearPlane - zFarPlane);
        result.M[11] = 0;
        result.M[12] = (left + right) / (left - right);
        result.M[13] = (bottom + top) / (bottom - top);
        result.M[14] = zNearPlane / (zNearPlane - zFarPlane);
        result.M[15] = 1;
        return result;
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
    Matrix createPerspectiveFieldOfView(float fieldOfView, float aspectRatio,
            float nearPlaneDistance, float farPlaneDistance) {
        Matrix result = new Matrix();
        // http://msdn.microsoft.com/en-us/library/bb205351(v=VS.85).aspx
        // http://msdn.microsoft.com/en-us/library/bb195665.aspx
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

        result.M[0] = xscale;
        result.M[5] = yscale;
        result.M[10] = farPlaneDistance
                / (nearPlaneDistance - farPlaneDistance);
        result.M[11] = -1;
        result.M[14] = nearPlaneDistance * farPlaneDistance
                / (nearPlaneDistance - farPlaneDistance);
        return result;
    }

    /**
     * Creates the rotation x.
     *
     * @param radians
     *            the radians
     * @return the matrix
     */
    Matrix createRotationX(float radians) {
        Matrix returnMatrix = Matrix.identity();

        returnMatrix.M[5] = (float) Math.cos(radians);
        returnMatrix.M[6] = (float) Math.sin(radians);
        returnMatrix.M[9] = -returnMatrix.M[6];
        returnMatrix.M[10] = returnMatrix.M[5];

        return returnMatrix;
    }

    /**
     * Creates the rotation y.
     *
     * @param radians
     *            the radians
     * @return the matrix
     */
    Matrix createRotationY(float radians) {
        Matrix returnMatrix = Matrix.identity();

        returnMatrix.M[0] = (float) Math.cos(radians);
        returnMatrix.M[2] = (float) Math.sin(radians);
        returnMatrix.M[8] = -returnMatrix.M[2];
        returnMatrix.M[10] = returnMatrix.M[0];

        return returnMatrix;
    }

    /**
     * Creates the rotation z.
     *
     * @param radians
     *            the radians
     * @return the matrix
     */
    Matrix createRotationZ(float radians) {
        Matrix returnMatrix = Matrix.identity();

        returnMatrix.M[0] = (float) Math.cos(radians);
        returnMatrix.M[1] = (float) Math.sin(radians);
        returnMatrix.M[4] = -returnMatrix.M[1];
        returnMatrix.M[5] = returnMatrix.M[0];

        return returnMatrix;
    }

    /**
     * Creates the scale.
     *
     * @param scale
     *            the scale
     * @return the matrix
     */
    Matrix createScale(float scale) {
        Matrix returnMatrix = Matrix.identity();

        returnMatrix.M[0] = scale;
        returnMatrix.M[5] = scale;
        returnMatrix.M[10] = scale;

        return returnMatrix;
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
    Matrix createScale(float xScale, float yScale, float zScale) {
        Matrix returnMatrix = Matrix.identity();

        returnMatrix.M[0] = xScale;
        returnMatrix.M[5] = yScale;
        returnMatrix.M[10] = zScale;

        return returnMatrix;
    }

    /**
     * Creates the scale.
     *
     * @param scales
     *            the scales
     * @return the matrix
     */
    Matrix createScale(IVector3 scales) {
        Matrix returnMatrix = Matrix.identity();

        returnMatrix.M[0] = scales.getX();
        returnMatrix.M[5] = scales.getY();
        returnMatrix.M[10] = scales.getZ();

        return returnMatrix;
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
    Matrix createTranslation(float xPosition, float yPosition, float zPosition) {
        Matrix returnMatrix = Matrix.identity();

        returnMatrix.M[12] = xPosition;
        returnMatrix.M[13] = yPosition;
        returnMatrix.M[14] = zPosition;

        return returnMatrix;
    }

    /**
     * Creates the translation.
     *
     * @param position
     *            the position
     * @return the matrix
     */
    Matrix createTranslation(Vector3 position) {
        Matrix returnMatrix = Matrix.identity();

        returnMatrix.M[12] = position.getX();
        returnMatrix.M[13] = position.getY();
        returnMatrix.M[14] = position.getZ();

        return returnMatrix;
    }

    /**
     * Divide.
     *
     * @param matrix1
     *            the matrix1
     * @param matrix2
     *            the matrix2
     * @return the matrix
     */
    Matrix divide(Matrix matrix1, Matrix matrix2) {
        Matrix inverse = Matrix.invert(matrix2);
        Matrix result = new Matrix();

        result.M[0] = matrix1.M[0] * inverse.M[0] + matrix1.M[1] * inverse.M[4]
                + matrix1.M[2] * inverse.M[8] + matrix1.M[3] * inverse.M[12];
        result.M[1] = matrix1.M[0] * inverse.M[1] + matrix1.M[1] * inverse.M[5]
                + matrix1.M[2] * inverse.M[9] + matrix1.M[3] * inverse.M[13];
        result.M[2] = matrix1.M[0] * inverse.M[2] + matrix1.M[1] * inverse.M[6]
                + matrix1.M[2] * inverse.M[10] + matrix1.M[3] * inverse.M[14];
        result.M[3] = matrix1.M[0] * inverse.M[3] + matrix1.M[1] * inverse.M[7]
                + matrix1.M[2] * inverse.M[11] + matrix1.M[3] * inverse.M[15];

        result.M[4] = matrix1.M[4] * inverse.M[0] + matrix1.M[5] * inverse.M[4]
                + matrix1.M[6] * inverse.M[8] + matrix1.M[7] * inverse.M[12];
        result.M[5] = matrix1.M[4] * inverse.M[1] + matrix1.M[5] * inverse.M[5]
                + matrix1.M[6] * inverse.M[9] + matrix1.M[7] * inverse.M[13];
        result.M[6] = matrix1.M[4] * inverse.M[2] + matrix1.M[5] * inverse.M[6]
                + matrix1.M[6] * inverse.M[10] + matrix1.M[7] * inverse.M[14];
        result.M[7] = matrix1.M[4] * inverse.M[3] + matrix1.M[5] * inverse.M[7]
                + matrix1.M[6] * inverse.M[11] + matrix1.M[7] * inverse.M[15];

        result.M[8] = matrix1.M[8] * inverse.M[0] + matrix1.M[9] * inverse.M[4]
                + matrix1.M[10] * inverse.M[8] + matrix1.M[11] * inverse.M[12];
        result.M[9] = matrix1.M[8] * inverse.M[1] + matrix1.M[9] * inverse.M[5]
                + matrix1.M[10] * inverse.M[9] + matrix1.M[11] * inverse.M[13];
        result.M[10] = matrix1.M[8] * inverse.M[2] + matrix1.M[9]
                * inverse.M[6] + matrix1.M[10] * inverse.M[10] + matrix1.M[11]
                * inverse.M[14];
        result.M[11] = matrix1.M[8] * inverse.M[3] + matrix1.M[9]
                * inverse.M[7] + matrix1.M[10] * inverse.M[11] + matrix1.M[11]
                * inverse.M[15];

        result.M[12] = matrix1.M[12] * inverse.M[0] + matrix1.M[13]
                * inverse.M[4] + matrix1.M[14] * inverse.M[8] + matrix1.M[15]
                * inverse.M[12];
        result.M[13] = matrix1.M[12] * inverse.M[1] + matrix1.M[13]
                * inverse.M[5] + matrix1.M[14] * inverse.M[9] + matrix1.M[15]
                * inverse.M[13];
        result.M[14] = matrix1.M[12] * inverse.M[2] + matrix1.M[13]
                * inverse.M[6] + matrix1.M[14] * inverse.M[10] + matrix1.M[15]
                * inverse.M[14];
        result.M[15] = matrix1.M[12] * inverse.M[3] + matrix1.M[13]
                * inverse.M[7] + matrix1.M[14] * inverse.M[11] + matrix1.M[15]
                * inverse.M[15];

        return result;
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
    Matrix divide(Matrix matrix1, float divider) {
        float inverseDivider = 1f / divider;

        matrix1.M[0] = matrix1.M[0] * inverseDivider;
        matrix1.M[1] = matrix1.M[1] * inverseDivider;
        matrix1.M[2] = matrix1.M[2] * inverseDivider;
        matrix1.M[3] = matrix1.M[3] * inverseDivider;
        matrix1.M[4] = matrix1.M[4] * inverseDivider;
        matrix1.M[5] = matrix1.M[5] * inverseDivider;
        matrix1.M[6] = matrix1.M[6] * inverseDivider;
        matrix1.M[7] = matrix1.M[7] * inverseDivider;
        matrix1.M[8] = matrix1.M[8] * inverseDivider;
        matrix1.M[9] = matrix1.M[9] * inverseDivider;
        matrix1.M[10] = matrix1.M[10] * inverseDivider;
        matrix1.M[11] = matrix1.M[11] * inverseDivider;
        matrix1.M[12] = matrix1.M[12] * inverseDivider;
        matrix1.M[13] = matrix1.M[13] * inverseDivider;
        matrix1.M[14] = matrix1.M[14] * inverseDivider;
        matrix1.M[15] = matrix1.M[15] * inverseDivider;

        return matrix1;
    }

    /**
     * Invert.
     *
     * @param matrix
     *            the matrix
     * @return the matrix
     */
    static Matrix invert(Matrix matrix) {
        // Use Laplace expansion theorem to calculate the inverse of a 4x4
        // matrix
        //
        // 1. Calculate the 2x2 determinants needed and the 4x4 determinant
        // based on the 2x2 determinants
        // 2. Create the adjugate matrix, which satisfies: A * adj(A) = det(A) *
        // I
        // 3. Divide adjugate matrix with the determinant to find the inverse

        float det1 = matrix.M[0] * matrix.M[5] - matrix.M[1] * matrix.M[4];
        float det2 = matrix.M[0] * matrix.M[6] - matrix.M[2] * matrix.M[4];
        float det3 = matrix.M[0] * matrix.M[7] - matrix.M[3] * matrix.M[4];
        float det4 = matrix.M[1] * matrix.M[6] - matrix.M[2] * matrix.M[5];
        float det5 = matrix.M[1] * matrix.M[7] - matrix.M[3] * matrix.M[5];
        float det6 = matrix.M[2] * matrix.M[7] - matrix.M[3] * matrix.M[6];
        float det7 = matrix.M[8] * matrix.M[13] - matrix.M[9] * matrix.M[12];
        float det8 = matrix.M[8] * matrix.M[14] - matrix.M[10] * matrix.M[12];
        float det9 = matrix.M[8] * matrix.M[15] - matrix.M[11] * matrix.M[12];
        float det10 = matrix.M[9] * matrix.M[14] - matrix.M[10] * matrix.M[13];
        float det11 = matrix.M[9] * matrix.M[15] - matrix.M[11] * matrix.M[13];
        float det12 = matrix.M[10] * matrix.M[15] - matrix.M[11] * matrix.M[14];

        float detMatrix = (float) (det1 * det12 - det2 * det11 + det3 * det10
                + det4 * det9 - det5 * det8 + det6 * det7);

        float invDetMatrix = 1f / detMatrix;

        Matrix ret = new Matrix();

        ret.M[0] = (matrix.M[5] * det12 - matrix.M[6] * det11 + matrix.M[7]
                * det10)
                * invDetMatrix;
        ret.M[1] = (-matrix.M[1] * det12 + matrix.M[2] * det11 - matrix.M[3]
                * det10)
                * invDetMatrix;
        ret.M[2] = (matrix.M[13] * det6 - matrix.M[14] * det5 + matrix.M[15]
                * det4)
                * invDetMatrix;
        ret.M[3] = (-matrix.M[9] * det6 + matrix.M[10] * det5 - matrix.M[11]
                * det4)
                * invDetMatrix;
        ret.M[4] = (-matrix.M[4] * det12 + matrix.M[6] * det9 - matrix.M[7]
                * det8)
                * invDetMatrix;
        ret.M[5] = (matrix.M[0] * det12 - matrix.M[2] * det9 + matrix.M[3]
                * det8)
                * invDetMatrix;
        ret.M[6] = (-matrix.M[12] * det6 + matrix.M[14] * det3 - matrix.M[15]
                * det2)
                * invDetMatrix;
        ret.M[7] = (matrix.M[8] * det6 - matrix.M[10] * det3 + matrix.M[11]
                * det2)
                * invDetMatrix;
        ret.M[8] = (matrix.M[4] * det11 - matrix.M[5] * det9 + matrix.M[7]
                * det7)
                * invDetMatrix;
        ret.M[9] = (-matrix.M[0] * det11 + matrix.M[1] * det9 - matrix.M[3]
                * det7)
                * invDetMatrix;
        ret.M[10] = (matrix.M[12] * det5 - matrix.M[13] * det3 + matrix.M[15]
                * det1)
                * invDetMatrix;
        ret.M[11] = (-matrix.M[8] * det5 + matrix.M[9] * det3 - matrix.M[11]
                * det1)
                * invDetMatrix;
        ret.M[12] = (-matrix.M[4] * det10 + matrix.M[5] * det8 - matrix.M[6]
                * det7)
                * invDetMatrix;
        ret.M[13] = (matrix.M[0] * det10 - matrix.M[1] * det8 + matrix.M[2]
                * det7)
                * invDetMatrix;
        ret.M[14] = (-matrix.M[12] * det4 + matrix.M[13] * det2 - matrix.M[14]
                * det1)
                * invDetMatrix;
        ret.M[15] = (matrix.M[8] * det4 - matrix.M[9] * det2 + matrix.M[10]
                * det1)
                * invDetMatrix;

        return ret;
    }

    /*
     * 
     * Matrix Matrix::Lerp(Matrix matrix1, Matrix matrix2, float amount) { throw
     * "not implemented!"; }
     */

    /**
     * Multiply.
     *
     * @param matrix1
     *            the matrix1
     * @param matrix2
     *            the matrix2
     * @return the matrix
     */
    Matrix multiply(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix();

        result.M[0] = matrix1.M[0] * matrix2.M[0] + matrix1.M[1] * matrix2.M[4]
                + matrix1.M[2] * matrix2.M[8] + matrix1.M[3] * matrix2.M[12];
        result.M[1] = matrix1.M[0] * matrix2.M[1] + matrix1.M[1] * matrix2.M[5]
                + matrix1.M[2] * matrix2.M[9] + matrix1.M[3] * matrix2.M[13];
        result.M[2] = matrix1.M[0] * matrix2.M[2] + matrix1.M[1] * matrix2.M[6]
                + matrix1.M[2] * matrix2.M[10] + matrix1.M[3] * matrix2.M[14];
        result.M[3] = matrix1.M[0] * matrix2.M[3] + matrix1.M[1] * matrix2.M[7]
                + matrix1.M[2] * matrix2.M[11] + matrix1.M[3] * matrix2.M[15];

        result.M[4] = matrix1.M[4] * matrix2.M[0] + matrix1.M[5] * matrix2.M[4]
                + matrix1.M[6] * matrix2.M[8] + matrix1.M[7] * matrix2.M[12];
        result.M[5] = matrix1.M[4] * matrix2.M[1] + matrix1.M[5] * matrix2.M[5]
                + matrix1.M[6] * matrix2.M[9] + matrix1.M[7] * matrix2.M[13];
        result.M[6] = matrix1.M[4] * matrix2.M[2] + matrix1.M[5] * matrix2.M[6]
                + matrix1.M[6] * matrix2.M[10] + matrix1.M[7] * matrix2.M[14];
        result.M[7] = matrix1.M[4] * matrix2.M[3] + matrix1.M[5] * matrix2.M[7]
                + matrix1.M[6] * matrix2.M[11] + matrix1.M[7] * matrix2.M[15];

        result.M[8] = matrix1.M[8] * matrix2.M[0] + matrix1.M[9] * matrix2.M[4]
                + matrix1.M[10] * matrix2.M[8] + matrix1.M[11] * matrix2.M[12];
        result.M[9] = matrix1.M[8] * matrix2.M[1] + matrix1.M[9] * matrix2.M[5]
                + matrix1.M[10] * matrix2.M[9] + matrix1.M[11] * matrix2.M[13];
        result.M[10] = matrix1.M[8] * matrix2.M[2] + matrix1.M[9]
                * matrix2.M[6] + matrix1.M[10] * matrix2.M[10] + matrix1.M[11]
                * matrix2.M[14];
        result.M[11] = matrix1.M[8] * matrix2.M[3] + matrix1.M[9]
                * matrix2.M[7] + matrix1.M[10] * matrix2.M[11] + matrix1.M[11]
                * matrix2.M[15];

        result.M[12] = matrix1.M[12] * matrix2.M[0] + matrix1.M[13]
                * matrix2.M[4] + matrix1.M[14] * matrix2.M[8] + matrix1.M[15]
                * matrix2.M[12];
        result.M[13] = matrix1.M[12] * matrix2.M[1] + matrix1.M[13]
                * matrix2.M[5] + matrix1.M[14] * matrix2.M[9] + matrix1.M[15]
                * matrix2.M[13];
        result.M[14] = matrix1.M[12] * matrix2.M[2] + matrix1.M[13]
                * matrix2.M[6] + matrix1.M[14] * matrix2.M[10] + matrix1.M[15]
                * matrix2.M[14];
        result.M[15] = matrix1.M[12] * matrix2.M[3] + matrix1.M[13]
                * matrix2.M[7] + matrix1.M[14] * matrix2.M[11] + matrix1.M[15]
                * matrix2.M[15];

        return result;
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
    Matrix multiply(Matrix matrix1, float factor) {
        matrix1.M[0] *= factor;
        matrix1.M[1] *= factor;
        matrix1.M[2] *= factor;
        matrix1.M[3] *= factor;
        matrix1.M[4] *= factor;
        matrix1.M[5] *= factor;
        matrix1.M[6] *= factor;
        matrix1.M[7] *= factor;
        matrix1.M[8] *= factor;
        matrix1.M[9] *= factor;
        matrix1.M[10] *= factor;
        matrix1.M[11] *= factor;
        matrix1.M[12] *= factor;
        matrix1.M[13] *= factor;
        matrix1.M[14] *= factor;
        matrix1.M[15] *= factor;
        return matrix1;
    }

    /**
     * Negate.
     *
     * @param matrix
     *            the matrix
     * @return the matrix
     */
    Matrix negate(Matrix matrix) {
        matrix.M[0] = -matrix.M[0];
        matrix.M[1] = -matrix.M[1];
        matrix.M[2] = -matrix.M[2];
        matrix.M[3] = -matrix.M[3];
        matrix.M[4] = -matrix.M[4];
        matrix.M[5] = -matrix.M[5];
        matrix.M[6] = -matrix.M[6];
        matrix.M[7] = -matrix.M[7];
        matrix.M[8] = -matrix.M[8];
        matrix.M[9] = -matrix.M[9];
        matrix.M[10] = -matrix.M[10];
        matrix.M[11] = -matrix.M[11];
        matrix.M[12] = -matrix.M[12];
        matrix.M[13] = -matrix.M[13];
        matrix.M[14] = -matrix.M[14];
        matrix.M[15] = -matrix.M[15];
        return matrix;
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
    Matrix subtract(Matrix matrix1, Matrix matrix2) {
        matrix1.M[0] -= matrix2.M[0];
        matrix1.M[1] -= matrix2.M[1];
        matrix1.M[2] -= matrix2.M[2];
        matrix1.M[3] -= matrix2.M[3];
        matrix1.M[4] -= matrix2.M[4];
        matrix1.M[5] -= matrix2.M[5];
        matrix1.M[6] -= matrix2.M[6];
        matrix1.M[7] -= matrix2.M[7];
        matrix1.M[8] -= matrix2.M[8];
        matrix1.M[9] -= matrix2.M[9];
        matrix1.M[10] -= matrix2.M[10];
        matrix1.M[11] -= matrix2.M[11];
        matrix1.M[12] -= matrix2.M[12];
        matrix1.M[13] -= matrix2.M[13];
        matrix1.M[14] -= matrix2.M[14];
        matrix1.M[15] -= matrix2.M[15];
        return matrix1;
    }

    /**
     * Transpose.
     *
     * @param matrix
     *            the matrix
     * @return the matrix
     */
    Matrix transpose(Matrix matrix) {
        Matrix result = new Matrix();
        result.M[0] = matrix.M[0];
        result.M[1] = matrix.M[4];
        result.M[2] = matrix.M[8];
        result.M[3] = matrix.M[12];

        result.M[4] = matrix.M[1];
        result.M[5] = matrix.M[5];
        result.M[6] = matrix.M[9];
        result.M[7] = matrix.M[13];

        result.M[8] = matrix.M[2];
        result.M[9] = matrix.M[6];
        result.M[10] = matrix.M[10];
        result.M[11] = matrix.M[14];

        result.M[12] = matrix.M[3];
        result.M[13] = matrix.M[7];
        result.M[14] = matrix.M[11];
        result.M[15] = matrix.M[15];

        return result;
    }

    /**
     * Determinant.
     *
     * @return the float
     */
    float determinant() {
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
    boolean equals(Matrix other) {
        return (M[0] == other.M[0]) && (M[1] == other.M[1])
                && (M[2] == other.M[2]) && (M[3] == other.M[3])
                && (M[4] == other.M[4]) && (M[5] == other.M[5])
                && (M[6] == other.M[6]) && (M[7] == other.M[7])
                && (M[8] == other.M[8]) && (M[9] == other.M[9])
                && (M[10] == other.M[10]) && (M[11] == other.M[11])
                && (M[12] == other.M[12]) && (M[13] == other.M[13])
                && (M[14] == other.M[14]) && (M[15] == other.M[15]);
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

}
