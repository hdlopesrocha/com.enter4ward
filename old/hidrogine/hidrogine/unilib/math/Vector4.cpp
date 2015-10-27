#ifndef unilib_math_Vector4
#define unilib_math_Vector4

#include "Math.hpp"

namespace unilib
{

    Vector4::Vector4(float x, float y, float z, float w)
    {
        X = x;
        Y = y;
        Z = z;
        W = w;
    }

    Vector4::Vector4(Vector2 value, float z, float w)
    {
        X = value.X;
        Y = value.Y;
        Z = z;
        W = w;
    }

    Vector4::Vector4(Vector3 value, float w)
    {
        X = value.X;
        Y = value.Y;
        Z = value.Z;
        W = w;
    }

    Vector4::Vector4(float value)
    {
        X = value;
        Y = value;
        Z = value;
        W = value;
    }

    Vector4::Vector4()
    {
        X = Y = Z = W = 0.0;
    }

    Vector4 Vector4::Zero()
    {
        return Vector4(0,0,0,0);
    }

    Vector4 Vector4::One()
    {
        return Vector4(1,1,1,1);
    }

    Vector4 Vector4::UnitX()
    {
        return Vector4(1,0,0,0);
    }

    Vector4 Vector4::UnitY()
    {
        return Vector4(0,1,0,0);
    }

    Vector4 Vector4::UnitZ()
    {
        return Vector4(0,0,1,0);
    }

    Vector4 Vector4::UnitW()
    {
        return Vector4(0,0,0,1);
    }

    Vector4 Vector4::Add(Vector4 value1, Vector4 value2)
    {
        value1.W += value2.W;
        value1.X += value2.X;
        value1.Y += value2.Y;
        value1.Z += value2.Z;
        return value1;
    }

    Vector4 Vector4::Barycentric(Vector4 value1, Vector4 value2, Vector4 value3, float amount1, float amount2)
    {
        return Vector4(
            MathHelper::Barycentric(value1.X, value2.X, value3.X, amount1, amount2),
            MathHelper::Barycentric(value1.Y, value2.Y, value3.Y, amount1, amount2),
            MathHelper::Barycentric(value1.Z, value2.Z, value3.Z, amount1, amount2),
            MathHelper::Barycentric(value1.W, value2.W, value3.W, amount1, amount2));
    }

    Vector4 Vector4::CatmullRom(Vector4 value1, Vector4 value2, Vector4 value3, Vector4 value4, float amount)
    {
        return Vector4(
            MathHelper::CatmullRom(value1.X, value2.X, value3.X, value4.X, amount),
            MathHelper::CatmullRom(value1.Y, value2.Y, value3.Y, value4.Y, amount),
            MathHelper::CatmullRom(value1.Z, value2.Z, value3.Z, value4.Z, amount),
            MathHelper::CatmullRom(value1.W, value2.W, value3.W, value4.W, amount));
    }

    Vector4 Vector4::Clamp(Vector4 value1, Vector4 min, Vector4 max)
    {
        return Vector4(
            MathHelper::Clamp(value1.X, min.X, max.X),
            MathHelper::Clamp(value1.Y, min.Y, max.Y),
            MathHelper::Clamp(value1.Z, min.Z, max.Z),
            MathHelper::Clamp(value1.W, min.W, max.W));
    }

    float Vector4::Distance(Vector4 value1, Vector4 value2)
    {
        return (float)sqrt((value1.W - value2.W) * (value1.W - value2.W) +
                 (value1.X - value2.X) * (value1.X - value2.X) +
                 (value1.Y - value2.Y) * (value1.Y - value2.Y) +
                 (value1.Z - value2.Z) * (value1.Z - value2.Z));
    }

    float Vector4::DistanceSquared(Vector4 value1, Vector4 value2)
    {
        return (value1.W - value2.W) * (value1.W - value2.W) +
                 (value1.X - value2.X) * (value1.X - value2.X) +
                 (value1.Y - value2.Y) * (value1.Y - value2.Y) +
                 (value1.Z - value2.Z) * (value1.Z - value2.Z);
    }

    Vector4 Vector4::Divide(Vector4 value1, Vector4 value2)
    {
        value1.W /= value2.W;
        value1.X /= value2.X;
        value1.Y /= value2.Y;
        value1.Z /= value2.Z;
        return value1;
    }

    Vector4 Vector4::Divide(Vector4 value1, float divider)
    {
        float factor = 1.0 / divider;
        value1.W *= factor;
        value1.X *= factor;
        value1.Y *= factor;
        value1.Z *= factor;
        return value1;
    }

    float Vector4::Dot(Vector4 vector1, Vector4 vector2)
    {
        return vector1.X * vector2.X + vector1.Y * vector2.Y + vector1.Z * vector2.Z + vector1.W * vector2.W;
    }

    bool Vector4::Equals(Vector4 other)
    {
        return W == other.W
            && X == other.X
            && Y == other.Y
            && Z == other.Z;
    }

    int Vector4::GetHashCode()
    {
        return (int)(W + X + Y + Y);
    }

    Vector4 Vector4::Hermite(Vector4 value1, Vector4 tangent1, Vector4 value2, Vector4 tangent2, float amount)
    {
        value1.W = MathHelper::Hermite(value1.W, tangent1.W, value2.W, tangent2.W, amount);
        value1.X = MathHelper::Hermite(value1.X, tangent1.X, value2.X, tangent2.X, amount);
        value1.Y = MathHelper::Hermite(value1.Y, tangent1.Y, value2.Y, tangent2.Y, amount);
        value1.Z = MathHelper::Hermite(value1.Z, tangent1.Z, value2.Z, tangent2.Z, amount);

        return value1;
    }

    float Vector4::Length()
    {
        return (float)sqrt((double)(X * X + Y * Y + Z * Z + W * W));
    }

    float Vector4::LengthSquared()
    {
        return X * X + Y * Y + Z * Z + W * W;
    }

    Vector4 Vector4::Lerp(Vector4 value1, Vector4 value2, float amount)
    {
        return Vector4(
            MathHelper::Lerp(value1.X, value2.X, amount),
            MathHelper::Lerp(value1.Y, value2.Y, amount),
            MathHelper::Lerp(value1.Z, value2.Z, amount),
            MathHelper::Lerp(value1.W, value2.W, amount));
    }

    Vector4 Vector4::Max(Vector4 value1, Vector4 value2)
    {
        return Vector4(
           MathHelper::Max(value1.X, value2.X),
           MathHelper::Max(value1.Y, value2.Y),
           MathHelper::Max(value1.Z, value2.Z),
           MathHelper::Max(value1.W, value2.W));
    }

    Vector4 Vector4::Min(Vector4 value1, Vector4 value2)
    {
        return Vector4(
           MathHelper::Min(value1.X, value2.X),
           MathHelper::Min(value1.Y, value2.Y),
           MathHelper::Min(value1.Z, value2.Z),
           MathHelper::Min(value1.W, value2.W));
    }

    Vector4 Vector4::Multiply(Vector4 value1, Vector4 value2)
    {
        value1.W *= value2.W;
        value1.X *= value2.X;
        value1.Y *= value2.Y;
        value1.Z *= value2.Z;
        return value1;
    }

    Vector4 Vector4::Multiply(Vector4 value1, float scaleFactor)
    {
        value1.W *= scaleFactor;
        value1.X *= scaleFactor;
        value1.Y *= scaleFactor;
        value1.Z *= scaleFactor;
        return value1;
    }

    Vector4 Vector4::Negate(Vector4 value)
    {
        value.X = -value.X;
        value.Y = -value.Y;
        value.Z = -value.Z;
        value.W = -value.W;
        return value;
    }

    void Vector4::Normalize()
    {
        float factor = 1.0 / (float)sqrt((double)(X * X + Y * Y + Z * Z + W * W));

        W = W * factor;
        X = X * factor;
        Y = Y * factor;
        Z = Z * factor;
    }

    Vector4 Vector4::Normalize(Vector4 vector)
    {
        float factor = 1.0 / (float)sqrt((double)(vector.X * vector.X + vector.Y * vector.Y + vector.Z * vector.Z + vector.W * vector.W));

        vector.W = vector.W * factor;
        vector.X = vector.X * factor;
        vector.Y = vector.Y * factor;
        vector.Z = vector.Z * factor;

        return vector;
    }

    Vector4 Vector4::SmoothStep(Vector4 value1, Vector4 value2, float amount)
    {
        return Vector4(
            MathHelper::SmoothStep(value1.X, value2.X, amount),
            MathHelper::SmoothStep(value1.Y, value2.Y, amount),
            MathHelper::SmoothStep(value1.Z, value2.Z, amount),
            MathHelper::SmoothStep(value1.W, value2.W, amount));
    }

    Vector4 Vector4::Subtract(Vector4 value1, Vector4 value2)
    {
        value1.W -= value2.W;
        value1.X -= value2.X;
        value1.Y -= value2.Y;
        value1.Z -= value2.Z;
        return value1;
    }


    Vector4 Vector4::Transform(Vector2 value, Quaternion rotation)
    {
        throw "not implemented!";
    }

    Vector4 Vector4::Transform(Vector3 value, Quaternion rotation)
    {
        throw "not implemented!";
    }

    Vector4 Vector4::Transform(Vector4 value, Quaternion rotation)
    {
        throw "not implemented!";
    }

    Vector4 Vector4::Transform(Vector2 position, Matrix matrix)
    {
        return Vector4((position.X * matrix.M[0]) + (position.Y * matrix.M[4]) + matrix.M[12],
                             (position.X * matrix.M[1]) + (position.Y * matrix.M[5]) + matrix.M[13],
                             (position.X * matrix.M[2]) + (position.Y * matrix.M[6]) + matrix.M[14],
                             (position.X * matrix.M[3]) + (position.Y * matrix.M[7]) + matrix.M[15]);
    }

    Vector4 Vector4::Transform(Vector3 position, Matrix matrix)
    {
        return Vector4((position.X * matrix.M[0]) + (position.Y * matrix.M[4]) + (position.Z * matrix.M[8]) + matrix.M[12],
                             (position.X * matrix.M[1]) + (position.Y * matrix.M[5]) + (position.Z * matrix.M[9]) + matrix.M[13],
                             (position.X * matrix.M[2]) + (position.Y * matrix.M[6]) + (position.Z * matrix.M[10]) + matrix.M[14],
                             (position.X * matrix.M[3]) + (position.Y * matrix.M[7]) + (position.Z * matrix.M[11]) + matrix.M[15]);
    }

    Vector4 Vector4::Transform(Vector4 vector, Matrix matrix)
    {
        return Vector4((vector.X * matrix.M[0]) + (vector.Y * matrix.M[4]) + (vector.Z * matrix.M[8]) + (vector.W * matrix.M[12]),
                             (vector.X * matrix.M[1]) + (vector.Y * matrix.M[5]) + (vector.Z * matrix.M[9]) + (vector.W * matrix.M[13]),
                             (vector.X * matrix.M[2]) + (vector.Y * matrix.M[6]) + (vector.Z * matrix.M[10]) + (vector.W * matrix.M[14]),
                             (vector.X * matrix.M[3]) + (vector.Y * matrix.M[7]) + (vector.Z * matrix.M[11]) + (vector.W * matrix.M[15]));
    }



    string Vector4::ToString(){
        stringstream out;

        out << "{X:" << X  << " Y:" << Y << " Z:" << Z << " W:" << W << "}";
        return out.str();
    }

    Vector4 Vector4::operator/=(Vector4 value){
        X/=value.X;
        Y/=value.Y;
        Z/=value.Z;
        W/=value.W;
        return *this;
    }

    Vector4 Vector4::operator/=(float value){
        X/=value;
        Y/=value;
        Z/=value;
        W/=value;
        return *this;
    }

    Vector4 Vector4::operator/(float value){
        return Vector4(X/value,Y/value, Z/value, W/value);
    }

    Vector4 Vector4::operator/(Vector4 value){
        return Vector4(X/value.X,Y/value.Y, Z/value.Z,W/value.W);
    }


    Vector4 Vector4::operator+=(Vector4 value){
        X+=value.X;
        Y+=value.Y;
        Z+=value.Z;
        W+=value.W;
        return *this;
    }

    Vector4 Vector4::operator+(Vector4 value){
        return Vector4(X+value.X,Y+value.Y, Z+ value.Z, W+ value.W);
    }

    Vector4 Vector4::operator-=(Vector4 value){
        X-=value.X;
        Y-=value.Y;
        Z-=value.Z;
        W-=value.W;
        return *this;
    }

    Vector4 Vector4::operator-(Vector4 value){
        return Vector4(X-value.X,Y-value.Y, Z-value.Z, W-value.W);
    }

    bool Vector4::operator==(Vector4 value){
       return (X==value.X && Y==value.Y && Z==value.Z && W==value.W);
    }


    bool Vector4::operator!=(Vector4 value){
       return (X!=value.X || Y!=value.Y || Z!=value.Z || W!=value.W);
    }

    Vector4 Vector4::operator*=(Vector4 value){
        X*=value.X;
        Y*=value.Y;
        Z*=value.Z;
        W*=value.W;
        return *this;
    }

    Vector4 Vector4::operator*=(float value){
        X*=value;
        Y*=value;
        Z*=value;
        W*=value;
        return *this;
    }

    Vector4 Vector4::operator*(float value){
        return Vector4(X*value,Y*value, Z*value, W*value);
    }

    Vector4 Vector4::operator*(Vector4 value){
        return Vector4(X*value.X,Y*value.Y, Z*value.Z, W*value.W);
    }
}
#endif