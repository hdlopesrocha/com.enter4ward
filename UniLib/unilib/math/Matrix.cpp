#ifndef unilib_math_Matrix
#define unilib_math_Matrix


#include "Math.hpp"
namespace unilib
{

   Matrix::Matrix()
    {
        M[0]=M[1]=M[2]=M[3]=M[4]=M[5]=M[6]=M[7]=M[8]=M[9]=M[10]=M[11]=M[12]=M[13]=M[14]=M[15]=0.0;
    }

   Matrix::Matrix(float m11, float m12, float m13, float m14, float m21, float m22, float m23, float m24,
                          float m31, float m32, float m33, float m34, float m41, float m42, float m43, float m44)
    {
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

    Matrix Matrix::Identity() {
    	return Matrix(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }


    Vector3 Matrix::Backward() {
        return Vector3(M[8], M[9], M[10]);
    }
    void Matrix::Backward(Vector3 value){
        M[8] = value.X;
        M[9] = value.Y;
        M[10] = value.Z;
    }

    Vector3 Matrix::Down() {
        return Vector3(-M[4], -M[5], -M[6]);
    }
    void Matrix::Down(Vector3 value) {
        M[4] = -value.X;
        M[5] = -value.Y;
        M[6] = -value.Z;
    }

    Vector3 Matrix::Forward() {
        return Vector3(-M[8], -M[9], -M[10]);
    }        
    void Matrix::Forward(Vector3 value) {
        M[8] = -value.X;
        M[9] = -value.Y;
        M[10] = -value.Z;
    } 
     
    Vector3 Matrix::Left() {
        return Vector3(-M[0], -M[1], -M[2]); 
    }
    void Matrix::Left(Vector3 value) {
        M[0] = -value.X;
        M[1] = -value.Y;
        M[2] = -value.Z;
    }

    Vector3 Matrix::Right() {
        return Vector3(M[0], M[1], M[2]); 
    }
    void Matrix::Right(Vector3 value) {
        M[0] = value.X;
        M[1] = value.Y;
        M[2] = value.Z;
    }


    Vector3 Matrix::Translation() {
        return Vector3(M[12], M[13], M[14]);
    }
    void Matrix::Translation(Vector3 value) {
        M[12] = value.X;
        M[13] = value.Y;
        M[14] = value.Z;
    }


    Vector3 Matrix::Up() {
        return Vector3(M[4], M[5], M[6]);
    }
    void Matrix::Up(Vector3 value) {
        M[4] = value.X;
        M[5] = value.Y;
        M[6] = value.Z;
    }      

    Matrix Matrix::CreateWorld(Vector3 position, Vector3 forward, Vector3 up)
    {
        Matrix result;
        Vector3 x, y, z;
        z = Vector3::Normalize(forward);
        x = Vector3::Cross(forward, up);
        y = Vector3::Cross(x,forward);
        x.Normalize();
        y.Normalize();            
       
        result = Matrix();
        result.Right(x);
        result.Up(y);
        result.Forward(z);
        result.Translation(position);
        result.M[15] = 1.0;
        return result;
    }

    Matrix Matrix::CreateShadow(Vector3 lightDirection, Plane plane)
    {
        Matrix result= Matrix();

        // Formula:
        // http://msdn.microsoft.com/en-us/library/bb205364(v=VS.85).aspx
       
        Plane p = Plane::Normalize(plane);
        float d = Vector3::Dot(p.Normal, lightDirection);
       
        result.M[0] = -1 * p.Normal.X * lightDirection.X + d;
        result.M[1] = -1 * p.Normal.X * lightDirection.Y;
        result.M[2] = -1 * p.Normal.X * lightDirection.Z;
        result.M[3] = 0;
        result.M[4] = -1 * p.Normal.Y * lightDirection.X;
        result.M[5] = -1 * p.Normal.Y * lightDirection.Y + d;
        result.M[6] = -1 * p.Normal.Y * lightDirection.Z;
        result.M[7] = 0;
        result.M[8] = -1 * p.Normal.Z * lightDirection.X;
        result.M[9] = -1 * p.Normal.Z * lightDirection.Y;
        result.M[10] = -1 * p.Normal.Z * lightDirection.Z + d;
        result.M[11] = 0;
        result.M[12] = -1 * p.D * lightDirection.X;
        result.M[13] = -1 * p.D * lightDirection.Y;
        result.M[14] = -1 * p.D * lightDirection.Z;
        result.M[15] = d;
        return result;
    }


    Matrix Matrix::CreateReflection(Plane value)
    {
        // Formula:
        // http://msdn.microsoft.com/en-us/library/bb205356(v=VS.85).aspx
        Matrix result;

        Plane p = Plane::Normalize(value);
       
        result.M[0] = -2 * p.Normal.X * p.Normal.X + 1;
        result.M[1] = -2 * p.Normal.X * p.Normal.Y;
        result.M[2] = -2 * p.Normal.X * p.Normal.Z;
        result.M[3] = 0;
        result.M[4] = -2 * p.Normal.Y * p.Normal.X;
        result.M[5] = -2 * p.Normal.Y * p.Normal.Y + 1;
        result.M[6] = -2 * p.Normal.Y * p.Normal.Z;
        result.M[7] = 0;
        result.M[8] = -2 * p.Normal.Z * p.Normal.X;
        result.M[9] = -2 * p.Normal.Z * p.Normal.Y;
        result.M[10] = -2 * p.Normal.Z * p.Normal.Z + 1;
        result.M[11] = 0;
        result.M[12] = -2 * p.D * p.Normal.X;
        result.M[13] = -2 * p.D * p.Normal.Y;
        result.M[14] = -2 * p.D * p.Normal.Z;
        result.M[15] = 1;
        return result;
    }


    Matrix Matrix::CreateFromYawPitchRoll(float yaw, float pitch, float roll)
    {
        Matrix matrix;
        Quaternion quaternion;
        quaternion= Quaternion::CreateFromYawPitchRoll(yaw, pitch, roll);
        matrix = CreateFromQuaternion(quaternion);
        return matrix;
    }

    Matrix Matrix::Transform(Matrix value, Quaternion rotation)
    {
            Matrix result;
            Matrix matrix = CreateFromQuaternion(rotation);
            result = Matrix::Multiply(value,matrix);
            return result;
    }


    bool Matrix::Decompose(Vector3 * scale, Quaternion * rotation, Vector3 * translation)
    {
        translation->X = M[12];
        translation->Y = M[13];
        translation->Z = M[14];
        float xs, ys, zs;
       
        if (M[0] * M[1] * M[2] * M[3] < 0)
            xs = -1.0;
        else
            xs = 1.0;
       
        if (M[4] * M[5] * M[6] * M[7] < 0)
            ys = -1.0;
        else
            ys = 1.0;
       
        if (M[8] * M[9] * M[10] * M[11] < 0)
            zs = -1.0;
        else
            zs = 1.0;
       
        scale->X = xs * (float)sqrt(M[0] * M[0] + M[1] * M[1] + M[2] * M[2]);
        scale->Y = ys * (float)sqrt(M[4] * M[4] + M[5] * M[5] + M[6] * M[6]);
        scale->Z = zs * (float)sqrt(M[8] * M[8] + M[9] * M[9] + M[10] * M[10]);
       
        if (scale->X == 0.0 || scale->Y == 0.0 || scale->Z == 0.0)
        {
                *rotation = Quaternion::Identity();
                return false;
        }

        Matrix m1 = Matrix(M[0]/scale->X, M[1]/scale->X, M[2]/scale->X, 0,
                M[4]/scale->Y, M[5]/scale->Y, M[6]/scale->Y, 0,
                M[8]/scale->Z, M[9]/scale->Z, M[10]/scale->Z, 0,
                0, 0, 0, 1);
       
        *rotation = Quaternion::CreateFromRotationMatrix(m1);
        return true;
    }


    Matrix Matrix::Add(Matrix matrix1, Matrix matrix2)
    {
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

   
    Matrix Matrix::CreateBillboard(Vector3 objectPosition, Vector3 cameraPosition,
        Vector3 cameraUpVector, Vector3 * cameraForwardVector)
    {
        Matrix result;
        Vector3 translation = objectPosition - cameraPosition;
        Vector3 backwards, right, up;
        backwards = Vector3::Normalize(translation);
        up=Vector3::Normalize(cameraUpVector);
        right=Vector3::Cross(backwards, up);
        up=Vector3::Cross(backwards, right);
        result = Matrix::Identity();
        result.Backward(backwards);
        result.Right(right);
        result.Up(up);
        result.Translation(translation);
        return result;
    }

    Matrix Matrix::CreateConstrainedBillboard(Vector3 objectPosition, Vector3 cameraPosition,
        Vector3 rotateAxis, Vector3 * cameraForwardVector, Vector3 * objectForwardVector)
    {
        throw "not implemented!";
    }

    Matrix Matrix::CreateFromAxisAngle(Vector3 axis, float angle)
    {
        throw "not implemented!";
    }

    Matrix Matrix::CreateFromQuaternion(Quaternion quaternion)
    {
        Matrix result;
        result = Matrix::Identity();
                   
        result.M[0] = 1 - 2 * (quaternion.Y * quaternion.Y + quaternion.Z * quaternion.Z);
        result.M[1] = 2 * (quaternion.X * quaternion.Y + quaternion.W * quaternion.Z);
        result.M[2] = 2 * (quaternion.X * quaternion.Z - quaternion.W * quaternion.Y);
        result.M[4] = 2 * (quaternion.X * quaternion.Y - quaternion.W * quaternion.Z);
        result.M[5] = 1 - 2 * (quaternion.X * quaternion.X + quaternion.Z * quaternion.Z);
        result.M[6] = 2 * (quaternion.Y * quaternion.Z + quaternion.W * quaternion.X);
        result.M[8] = 2 * (quaternion.X * quaternion.Z + quaternion.W * quaternion.Y);
        result.M[9] = 2 * (quaternion.Y * quaternion.Z - quaternion.W * quaternion.X);
        result.M[10] = 1 - 2 * (quaternion.X * quaternion.X + quaternion.Y * quaternion.Y);
        return result;
    }

    Matrix Matrix::CreateLookAt(Vector3 cameraPosition, Vector3 cameraDirection, Vector3 cameraUpVector)
    {
        Matrix result;
        // http://msdn.microsoft.com/en-us/library/bb205343(v=VS.85).aspx
       
        Vector3 vz = cameraDirection*-1.0;
        Vector3 vx = Vector3::Normalize(Vector3::Cross(cameraUpVector, vz));
        Vector3 vy = Vector3::Cross(vz, vx);
        result = Matrix::Identity();
        result.M[0] = vx.X;
        result.M[1] = vy.X;
        result.M[2] = vz.X;
        result.M[4] = vx.Y;
        result.M[5] = vy.Y;
        result.M[6] = vz.Y;
        result.M[8] = vx.Z;
        result.M[9] = vy.Z;
        result.M[10] = vz.Z;
        result.M[12] = -Vector3::Dot(vx, cameraPosition);
        result.M[13] = -Vector3::Dot(vy, cameraPosition);
        result.M[14] = -Vector3::Dot(vz, cameraPosition);            
        return result;
    }


    Matrix Matrix::CreateOrthographic(float width, float height, float zNearPlane, float zFarPlane)
    {
        Matrix result;
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


    Matrix Matrix::CreateOrthographicOffCenter(float left, float right, float bottom, float top, float zNearPlane, float zFarPlane)
    {
        Matrix result;
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

    Matrix Matrix::CreatePerspective(float width, float height, float zNearPlane, float zFarPlane)
    {
        throw "not implemented!";
    }




    Matrix Matrix::CreatePerspectiveFieldOfView(float fieldOfView, float aspectRatio, float nearPlaneDistance, float farPlaneDistance)
    {
        Matrix result;
        // http://msdn.microsoft.com/en-us/library/bb205351(v=VS.85).aspx
        // http://msdn.microsoft.com/en-us/library/bb195665.aspx
        if (fieldOfView < 0 || fieldOfView > MathHelper::Pi)
            throw "fieldOfView, fieldOfView takes a value between 0 and Pi (180 degrees) in radians.";

        if (nearPlaneDistance <= 0.0f)
            throw "nearPlaneDistance, You should specify positive value for nearPlaneDistance.";

        if (farPlaneDistance <= 0.0f)
            throw "farPlaneDistance, You should specify positive value for farPlaneDistance.";
       
        if (farPlaneDistance <= nearPlaneDistance)
            throw "nearPlaneDistance, Near plane distance is larger than Far plane distance. Near plane distance must be smaller than Far plane distance.";

        float yscale = (float)1 / (float)tan(fieldOfView / 2);
        float xscale = yscale / aspectRatio;

        result.M[0] = xscale;
        result.M[5] = yscale;
        result.M[10] = farPlaneDistance / (nearPlaneDistance - farPlaneDistance);
        result.M[11] = -1;
        result.M[14] = nearPlaneDistance * farPlaneDistance / (nearPlaneDistance - farPlaneDistance);
        return result;
    }



    Matrix Matrix::CreatePerspectiveOffCenter(float left, float right, float bottom, float top, float zNearPlane, float zFarPlane)
    {
        throw "not implemented!";
    }



    Matrix Matrix::CreateRotationX(float radians)
    {
        Matrix returnMatrix = Matrix::Identity();

        returnMatrix.M[5] = (float)cos(radians);
        returnMatrix.M[6] = (float)sin(radians);
        returnMatrix.M[9] = -returnMatrix.M[6];
        returnMatrix.M[10] = returnMatrix.M[5];

        return returnMatrix;
    }


    Matrix Matrix::CreateRotationY(float radians)
    {
        Matrix returnMatrix = Matrix::Identity();

        returnMatrix.M[0] = (float)cos(radians);
        returnMatrix.M[2] = (float)sin(radians);
        returnMatrix.M[8] = -returnMatrix.M[2];
        returnMatrix.M[10] = returnMatrix.M[0];

        return returnMatrix;
    }


    Matrix Matrix::CreateRotationZ(float radians)
    {
        Matrix returnMatrix = Matrix::Identity();

        returnMatrix.M[0] = (float)cos(radians);
        returnMatrix.M[1] = (float)sin(radians);
        returnMatrix.M[4] = -returnMatrix.M[1];
        returnMatrix.M[5] = returnMatrix.M[0];

        return returnMatrix;
    }




    Matrix Matrix::CreateScale(float scale)
    {
        Matrix returnMatrix = Matrix::Identity();

        returnMatrix.M[0] = scale;
        returnMatrix.M[5] = scale;
        returnMatrix.M[10] = scale;

        return returnMatrix;
    }



    Matrix Matrix::CreateScale(float xScale, float yScale, float zScale)
    {
        Matrix returnMatrix = Matrix::Identity();

        returnMatrix.M[0] = xScale;
        returnMatrix.M[5] = yScale;
        returnMatrix.M[10] = zScale;

        return returnMatrix;
    }

    Matrix Matrix::CreateScale(Vector3 scales)
    {
        Matrix returnMatrix = Matrix::Identity();

        returnMatrix.M[0] = scales.X;
        returnMatrix.M[5] = scales.Y;
        returnMatrix.M[10] = scales.Z;

        return returnMatrix;
    }

    Matrix Matrix::CreateTranslation(float xPosition, float yPosition, float zPosition)
    {
        Matrix returnMatrix = Matrix::Identity();

        returnMatrix.M[12] = xPosition;
        returnMatrix.M[13] = yPosition;
        returnMatrix.M[14] = zPosition;

        return returnMatrix;
    }


    Matrix Matrix::CreateTranslation(Vector3 position)
    {
        Matrix returnMatrix = Matrix::Identity();

        returnMatrix.M[12] = position.X;
        returnMatrix.M[13] = position.Y;
        returnMatrix.M[14] = position.Z;

        return returnMatrix;
    }



    Matrix Matrix::Divide(Matrix matrix1, Matrix matrix2)
    {
        Matrix inverse = Matrix::Invert(matrix2), result;

        result.M[0] = matrix1.M[0] * inverse.M[0] + matrix1.M[1] * inverse.M[4] + matrix1.M[2] * inverse.M[8] + matrix1.M[3] * inverse.M[12];
        result.M[1] = matrix1.M[0] * inverse.M[1] + matrix1.M[1] * inverse.M[5] + matrix1.M[2] * inverse.M[9] + matrix1.M[3] * inverse.M[13];
        result.M[2] = matrix1.M[0] * inverse.M[2] + matrix1.M[1] * inverse.M[6] + matrix1.M[2] * inverse.M[10] + matrix1.M[3] * inverse.M[14];
        result.M[3] = matrix1.M[0] * inverse.M[3] + matrix1.M[1] * inverse.M[7] + matrix1.M[2] * inverse.M[11] + matrix1.M[3] * inverse.M[15];

        result.M[4] = matrix1.M[4] * inverse.M[0] + matrix1.M[5] * inverse.M[4] + matrix1.M[6] * inverse.M[8] + matrix1.M[7] * inverse.M[12];
        result.M[5] = matrix1.M[4] * inverse.M[1] + matrix1.M[5] * inverse.M[5] + matrix1.M[6] * inverse.M[9] + matrix1.M[7] * inverse.M[13];
        result.M[6] = matrix1.M[4] * inverse.M[2] + matrix1.M[5] * inverse.M[6] + matrix1.M[6] * inverse.M[10] + matrix1.M[7] * inverse.M[14];
        result.M[7] = matrix1.M[4] * inverse.M[3] + matrix1.M[5] * inverse.M[7] + matrix1.M[6] * inverse.M[11] + matrix1.M[7] * inverse.M[15];

        result.M[8] = matrix1.M[8] * inverse.M[0] + matrix1.M[9] * inverse.M[4] + matrix1.M[10] * inverse.M[8] + matrix1.M[11] * inverse.M[12];
        result.M[9] = matrix1.M[8] * inverse.M[1] + matrix1.M[9] * inverse.M[5] + matrix1.M[10] * inverse.M[9] + matrix1.M[11] * inverse.M[13];
        result.M[10] = matrix1.M[8] * inverse.M[2] + matrix1.M[9] * inverse.M[6] + matrix1.M[10] * inverse.M[10] + matrix1.M[11] * inverse.M[14];
        result.M[11] = matrix1.M[8] * inverse.M[3] + matrix1.M[9] * inverse.M[7] + matrix1.M[10] * inverse.M[11] + matrix1.M[11] * inverse.M[15];

        result.M[12] = matrix1.M[12] * inverse.M[0] + matrix1.M[13] * inverse.M[4] + matrix1.M[14] * inverse.M[8] + matrix1.M[15] * inverse.M[12];
        result.M[13] = matrix1.M[12] * inverse.M[1] + matrix1.M[13] * inverse.M[5] + matrix1.M[14] * inverse.M[9] + matrix1.M[15] * inverse.M[13];
        result.M[14] = matrix1.M[12] * inverse.M[2] + matrix1.M[13] * inverse.M[6] + matrix1.M[14] * inverse.M[10] + matrix1.M[15] * inverse.M[14];
        result.M[15] = matrix1.M[12] * inverse.M[3] + matrix1.M[13] * inverse.M[7] + matrix1.M[14] * inverse.M[11] + matrix1.M[15] * inverse.M[15];

        return result;
    }

    Matrix Matrix::Divide(Matrix matrix1, float divider)
    {
        float inverseDivider = 1.0 / divider;

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



           
    Matrix Matrix::Invert(Matrix matrix)
    {
        // Use Laplace expansion theorem to calculate the inverse of a 4x4 matrix
        //
        // 1. Calculate the 2x2 determinants needed and the 4x4 determinant based on the 2x2 determinants
        // 2. Create the adjugate matrix, which satisfies: A * adj(A) = det(A) * I
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
       
        float detMatrix = (float)(det1*det12 - det2*det11 + det3*det10 + det4*det9 - det5*det8 + det6*det7);
       
        float invDetMatrix = 1.0 / detMatrix;
       
        Matrix ret; // Allow for matrix and result to point to the same structure
       
        ret.M[0] = (matrix.M[5]*det12 - matrix.M[6]*det11 + matrix.M[7]*det10) * invDetMatrix;
        ret.M[1] = (-matrix.M[1]*det12 + matrix.M[2]*det11 - matrix.M[3]*det10) * invDetMatrix;
        ret.M[2] = (matrix.M[13]*det6 - matrix.M[14]*det5 + matrix.M[15]*det4) * invDetMatrix;
        ret.M[3] = (-matrix.M[9]*det6 + matrix.M[10]*det5 - matrix.M[11]*det4) * invDetMatrix;
        ret.M[4] = (-matrix.M[4]*det12 + matrix.M[6]*det9 - matrix.M[7]*det8) * invDetMatrix;
        ret.M[5] = (matrix.M[0]*det12 - matrix.M[2]*det9 + matrix.M[3]*det8) * invDetMatrix;
        ret.M[6] = (-matrix.M[12]*det6 + matrix.M[14]*det3 - matrix.M[15]*det2) * invDetMatrix;
        ret.M[7] = (matrix.M[8]*det6 - matrix.M[10]*det3 + matrix.M[11]*det2) * invDetMatrix;
        ret.M[8] = (matrix.M[4]*det11 - matrix.M[5]*det9 + matrix.M[7]*det7) * invDetMatrix;
        ret.M[9] = (-matrix.M[0]*det11 + matrix.M[1]*det9 - matrix.M[3]*det7) * invDetMatrix;
        ret.M[10] = (matrix.M[12]*det5 - matrix.M[13]*det3 + matrix.M[15]*det1) * invDetMatrix;
        ret.M[11] = (-matrix.M[8]*det5 + matrix.M[9]*det3 - matrix.M[11]*det1) * invDetMatrix;
        ret.M[12] = (-matrix.M[4]*det10 + matrix.M[5]*det8 - matrix.M[6]*det7) * invDetMatrix;
        ret.M[13] = (matrix.M[0]*det10 - matrix.M[1]*det8 + matrix.M[2]*det7) * invDetMatrix;
        ret.M[14] = (-matrix.M[12]*det4 + matrix.M[13]*det2 - matrix.M[14]*det1) * invDetMatrix;
        ret.M[15] = (matrix.M[8]*det4 - matrix.M[9]*det2 + matrix.M[10]*det1) * invDetMatrix;
       
        return ret;
    }


    Matrix Matrix::Lerp(Matrix matrix1, Matrix matrix2, float amount)
    {
        throw "not implemented!";
    }



    Matrix Matrix::Multiply(Matrix matrix1, Matrix matrix2)
    {
        Matrix result;

        result.M[0] = matrix1.M[0] * matrix2.M[0] + matrix1.M[1] * matrix2.M[4] + matrix1.M[2] * matrix2.M[8] + matrix1.M[3] * matrix2.M[12];
        result.M[1] = matrix1.M[0] * matrix2.M[1] + matrix1.M[1] * matrix2.M[5] + matrix1.M[2] * matrix2.M[9] + matrix1.M[3] * matrix2.M[13];
        result.M[2] = matrix1.M[0] * matrix2.M[2] + matrix1.M[1] * matrix2.M[6] + matrix1.M[2] * matrix2.M[10] + matrix1.M[3] * matrix2.M[14];
        result.M[3] = matrix1.M[0] * matrix2.M[3] + matrix1.M[1] * matrix2.M[7] + matrix1.M[2] * matrix2.M[11] + matrix1.M[3] * matrix2.M[15];

        result.M[4] = matrix1.M[4] * matrix2.M[0] + matrix1.M[5] * matrix2.M[4] + matrix1.M[6] * matrix2.M[8] + matrix1.M[7] * matrix2.M[12];
        result.M[5] = matrix1.M[4] * matrix2.M[1] + matrix1.M[5] * matrix2.M[5] + matrix1.M[6] * matrix2.M[9] + matrix1.M[7] * matrix2.M[13];
        result.M[6] = matrix1.M[4] * matrix2.M[2] + matrix1.M[5] * matrix2.M[6] + matrix1.M[6] * matrix2.M[10] + matrix1.M[7] * matrix2.M[14];
        result.M[7] = matrix1.M[4] * matrix2.M[3] + matrix1.M[5] * matrix2.M[7] + matrix1.M[6] * matrix2.M[11] + matrix1.M[7] * matrix2.M[15];

        result.M[8] = matrix1.M[8] * matrix2.M[0] + matrix1.M[9] * matrix2.M[4] + matrix1.M[10] * matrix2.M[8] + matrix1.M[11] * matrix2.M[12];
        result.M[9] = matrix1.M[8] * matrix2.M[1] + matrix1.M[9] * matrix2.M[5] + matrix1.M[10] * matrix2.M[9] + matrix1.M[11] * matrix2.M[13];
        result.M[10] = matrix1.M[8] * matrix2.M[2] + matrix1.M[9] * matrix2.M[6] + matrix1.M[10] * matrix2.M[10] + matrix1.M[11] * matrix2.M[14];
        result.M[11] = matrix1.M[8] * matrix2.M[3] + matrix1.M[9] * matrix2.M[7] + matrix1.M[10] * matrix2.M[11] + matrix1.M[11] * matrix2.M[15];

        result.M[12] = matrix1.M[12] * matrix2.M[0] + matrix1.M[13] * matrix2.M[4] + matrix1.M[14] * matrix2.M[8] + matrix1.M[15] * matrix2.M[12];
        result.M[13] = matrix1.M[12] * matrix2.M[1] + matrix1.M[13] * matrix2.M[5] + matrix1.M[14] * matrix2.M[9] + matrix1.M[15] * matrix2.M[13];
        result.M[14] = matrix1.M[12] * matrix2.M[2] + matrix1.M[13] * matrix2.M[6] + matrix1.M[14] * matrix2.M[10] + matrix1.M[15] * matrix2.M[14];
        result.M[15] = matrix1.M[12] * matrix2.M[3] + matrix1.M[13] * matrix2.M[7] + matrix1.M[14] * matrix2.M[11] + matrix1.M[15] * matrix2.M[15];    

        return result;
    }

    Matrix Matrix::Multiply(Matrix matrix1, float factor)
    {
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

    Matrix Matrix::Negate(Matrix matrix)
    {
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

    Matrix Matrix::Subtract(Matrix matrix1, Matrix matrix2)
    {
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

    Matrix Matrix::Transpose(Matrix matrix)
    {
        Matrix result;
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

    float Matrix::Determinant()
    {
        float minor1, minor2, minor3, minor4, minor5, minor6;

        minor1 = M[8] * M[13] - M[9] * M[12];
        minor2 = M[8] * M[14] - M[10] * M[12];
        minor3 = M[8] * M[15] - M[11] * M[12];
        minor4 = M[9] * M[14] - M[10] * M[13];
        minor5 = M[9] * M[15] - M[11] * M[13];
        minor6 = M[10] * M[15] - M[11] * M[14];

        return  M[0] * (M[5] * minor6 - M[6] * minor5 + M[7] * minor4) -
                        M[1] * (M[4] * minor6 - M[6] * minor3 + M[7] * minor2) +
                        M[2] * (M[4] * minor5 - M[5] * minor3 + M[7] * minor1) -
                        M[3] * (M[4] * minor4 - M[5] * minor2 + M[6] * minor1);
    }

    bool Matrix::Equals(Matrix other)
    {
        return (M[0] == other.M[0]) && (M[1] == other.M[1]) &&
               (M[2] == other.M[2]) && (M[3] == other.M[3]) &&
               (M[4] == other.M[4]) && (M[5] == other.M[5]) &&
               (M[6] == other.M[6]) && (M[7] == other.M[7]) &&
               (M[8] == other.M[8]) && (M[9] == other.M[9]) &&
               (M[10] == other.M[10]) && (M[11] == other.M[11]) &&
               (M[12] == other.M[12]) && (M[13] == other.M[13]) &&
               (M[14] == other.M[14]) && (M[15] == other.M[15]);
    }


    int Matrix::GetHashCode()
    {
        return 0;
    }


    string Matrix::ToString(){
        stringstream out;

        out << "{ {M[0]:" << M[0] << " M[1]:" << M[1] << " M[2]:" << M[2] << " M[3]:" << M[3] << "}" <<
                    " {M[4]:" << M[4] << " M[5]:" << M[5] << " M[6]:" << M[6] << " M[7]:" << M[7] << "}" <<
                    " {M[8]:" << M[8] << " M[9]:" << M[9] << " M[10]:" << M[10] << " M[11]:" << M[11] << "}" <<
                    " {M[12]:" << M[12] << " M[13]:" << M[13] << " M[14]:" << M[14] << " M[15]:" << M[15] << "} }";
        return out.str();
    }

    Matrix Matrix::operator/=(Matrix value){
        *this = Matrix::Divide(*this, value);
        return *this;
    }

    Matrix Matrix::operator/=(float value){
        *this = Matrix::Divide(*this, value);
        return *this;
    }

    Matrix Matrix::operator/(float value){
        return Matrix::Divide(*this,value);
    }

    Matrix Matrix::operator/(Matrix value){
        return Matrix::Divide(*this,value);
    }


    Matrix Matrix::operator+=(Matrix value){
        *this = Matrix::Add(*this, value);
        return *this;
    }

    Matrix Matrix::operator+(Matrix value){        
        return Matrix::Add(*this,value);
    }

    Matrix Matrix::operator-=(Matrix value){
        *this = Matrix::Subtract(*this, value);
        return *this;
    }

    Matrix Matrix::operator-(Matrix value){        
        return Matrix::Subtract(*this,value);
    }

    bool Matrix::operator==(Matrix value){
        return (M[0] == value.M[0]) && (M[1] == value.M[1]) &&
               (M[2] == value.M[2]) && (M[3] == value.M[3]) &&
               (M[4] == value.M[4]) && (M[5] == value.M[5]) &&
               (M[6] == value.M[6]) && (M[7] == value.M[7]) &&
               (M[8] == value.M[8]) && (M[9] == value.M[9]) &&
               (M[10] == value.M[10]) && (M[11] == value.M[11]) &&
               (M[12] == value.M[12]) && (M[13] == value.M[13]) &&
               (M[14] == value.M[14]) && (M[15] == value.M[15]);
    }


    bool Matrix::operator!=(Matrix value){
        return (M[0] != value.M[0]) || (M[1] != value.M[1]) ||
               (M[2] != value.M[2]) || (M[3] != value.M[3]) ||
               (M[4] != value.M[4]) || (M[5] != value.M[5]) ||
               (M[6] != value.M[6]) || (M[7] != value.M[7]) ||
               (M[8] != value.M[8]) || (M[9] != value.M[9]) ||
               (M[10] != value.M[10]) || (M[11] != value.M[11]) ||
               (M[12] != value.M[12]) || (M[13] != value.M[13]) ||
               (M[14] != value.M[14]) || (M[15] != value.M[15]);
    }

    Matrix Matrix::operator*=(Matrix value){
        *this = Matrix::Multiply(*this, value);
        return *this;
    }

    Matrix Matrix::operator*=(float value){
        *this = Matrix::Multiply(*this, value);
        return *this;
    }

    Matrix Matrix::operator*(float value){
        return Matrix::Multiply(*this, value);
    }

    Matrix Matrix::operator*(Matrix value){
        return Matrix::Multiply(*this, value);
    }
}

#endif
