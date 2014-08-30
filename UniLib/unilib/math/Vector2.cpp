#ifndef unilib_math_Vector2
#define unilib_math_Vector2


#include "Math.hpp"

namespace unilib
{
    Vector2::Vector2(float x, float y)
    {
        X = x;
        Y = y;
    }

    Vector2::Vector2(float value)
    {
        X = value;
        Y = value;
    }

    Vector2::Vector2()
    {
        X = Y = 0.0;
    }   


    Vector2 Vector2::Zero()
    {
        return Vector2(0, 0);
    }

    Vector2 Vector2::One()
    {
        return Vector2(1, 1);
    }

    Vector2 Vector2::UnitX()
    {
        return Vector2(1, 0);
    }

    Vector2 Vector2::UnitY()
    {
        return Vector2(0, 1);
    }

    Vector2 Vector2::Reflect(Vector2 vector, Vector2 normal)
    {
        Vector2 result;
        float dot = Dot(vector, normal);
        result.X = vector.X - ((2.0 * dot) * normal.X);
        result.Y = vector.Y - ((2.0 * dot) * normal.Y);
        return result;
    }

    Vector2 Vector2::Add(Vector2 value1, Vector2 value2)
    {
        value1.X += value2.X;
        value1.Y += value2.Y;
        return value1;
    }

    Vector2 Vector2::Barycentric(Vector2 value1, Vector2 value2, Vector2 value3, float amount1, float amount2)
    {
        return Vector2(
            MathHelper::Barycentric(value1.X, value2.X, value3.X, amount1, amount2),
            MathHelper::Barycentric(value1.Y, value2.Y, value3.Y, amount1, amount2));
    }


    Vector2 Vector2::CatmullRom(Vector2 value1, Vector2 value2, Vector2 value3, Vector2 value4, float amount)
    {
        return Vector2(
            MathHelper::CatmullRom(value1.X, value2.X, value3.X, value4.X, amount),
            MathHelper::CatmullRom(value1.Y, value2.Y, value3.Y, value4.Y, amount));
    }

    Vector2 Vector2::Clamp(Vector2 value1, Vector2 min, Vector2 max)
    {
        return Vector2(
            MathHelper::Clamp(value1.X, min.X, max.X),
            MathHelper::Clamp(value1.Y, min.Y, max.Y));
    }

    float Vector2::Distance(Vector2 value1, Vector2 value2)
    {
        return (float)sqrt((value1.X - value2.X) * (value1.X - value2.X) + (value1.Y - value2.Y) * (value1.Y - value2.Y));
    }

    float Vector2::DistanceSquared(Vector2 value1, Vector2 value2)
    {
        return (value1.X - value2.X) * (value1.X - value2.X) + (value1.Y - value2.Y) * (value1.Y - value2.Y);
    }

    Vector2 Vector2::Divide(Vector2 value1, Vector2 value2)
    {
        value1.X /= value2.X;
        value1.Y /= value2.Y;
        return value1;
    }

    Vector2 Vector2::Divide(Vector2 value1, float divider)
    {
        float factor = 1.0 / divider;
        value1.X *= factor;
        value1.Y *= factor;
        return value1;
    }

    float Vector2::Dot(Vector2 value1, Vector2 value2)
    {
        return value1.X * value2.X + value1.Y * value2.Y;
    }

    bool Vector2::Equals(Vector2 other)
    {
        return *this == other;
    }

    int Vector2::GetHashCode()
    {
        return (int)(X + Y);
    }

    Vector2 Vector2::Hermite(Vector2 value1, Vector2 tangent1, Vector2 value2, Vector2 tangent2, float amount)
    {
        value1.X = MathHelper::Hermite(value1.X, tangent1.X, value2.X, tangent2.X, amount);
        value1.Y = MathHelper::Hermite(value1.Y, tangent1.Y, value2.Y, tangent2.Y, amount);
        return value1;
    }

    float Vector2::Length()
    {
        return (float)sqrt((double)(X * X + Y * Y));
    }

    float Vector2::LengthSquared()
    {
        return X * X + Y * Y;
    }

    Vector2 Vector2::Lerp(Vector2 value1, Vector2 value2, float amount)
    {
        return Vector2(
            MathHelper::Lerp(value1.X, value2.X, amount),
            MathHelper::Lerp(value1.Y, value2.Y, amount));
    }

    Vector2 Vector2::Max(Vector2 value1, Vector2 value2)
    {
        return Vector2(
            MathHelper::Max(value1.X, value2.X),
            MathHelper::Max(value1.Y, value2.Y));
    }

    Vector2 Vector2::Min(Vector2 value1, Vector2 value2)
    {
        return Vector2(
            MathHelper::Min(value1.X, value2.X),
            MathHelper::Min(value1.Y, value2.Y));
    }

    Vector2 Vector2::Multiply(Vector2 value1, Vector2 value2)
    {
        value1.X *= value2.X;
        value1.Y *= value2.Y;
        return value1;
    }

    Vector2 Vector2::Multiply(Vector2 value1, float scaleFactor)
    {
        value1.X *= scaleFactor;
        value1.Y *= scaleFactor;
        return value1;
    }

    Vector2 Vector2::Negate(Vector2 value)
    {
        value.X = -value.X;
        value.Y = -value.Y;
        return value;
    }

    void Vector2::Normalize()
    {
        float factor = 1.0 / (float)sqrt((double)(X * X + Y * Y));
        X *= factor;
        Y *= factor;
    }

    Vector2 Vector2::Normalize(Vector2 value)
    {
        float factor = 1.0 / (float)sqrt((double)(value.X * value.X + value.Y * value.Y));
        value.X *= factor;
        value.Y *= factor;
        return value;
    }

    Vector2 Vector2::SmoothStep(Vector2 value1, Vector2 value2, float amount)
    {
        return Vector2(
            MathHelper::SmoothStep(value1.X, value2.X, amount),
            MathHelper::SmoothStep(value1.Y, value2.Y, amount));
    }

    Vector2 Vector2::Subtract(Vector2 value1, Vector2 value2)
    {
        value1.X -= value2.X;
        value1.Y -= value2.Y;
        return value1;
    }
    Vector2 Vector2::Transform(Vector2 position, Matrix matrix)
    {
            return Vector2((position.X * matrix.M[0]) + (position.Y * matrix.M[4]) + matrix.M[12],
                             (position.X * matrix.M[1]) + (position.Y * matrix.M[5]) + matrix.M[13]);;
    }

    Vector2 Vector2::Transform(Vector2 value, Quaternion rotation)
    {
        throw "not implemented!";
    }

    Vector2 Vector2::TransformNormal(Vector2 normal, Matrix matrix)
    {
        return Vector2((normal.X * matrix.M[0]) + (normal.Y * matrix.M[4]),
                             (normal.X * matrix.M[1]) + (normal.Y * matrix.M[5]));
    }


    string Vector2::ToString(){
        stringstream out;
        out << "{X:" << X << " Y:" << Y << "}";
        return out.str();
    }


    Vector2 Vector2::operator/=(Vector2 value){
        X/=value.X;
        Y/=value.Y;
        return *this;
    }

    Vector2 Vector2::operator/=(float value){
        X/=value;
        Y/=value;
        return *this;
    }

    Vector2 Vector2::operator/(float value){
        return Vector2(X/value,Y/value);
    }

    Vector2 Vector2::operator/(Vector2 value){
        return Vector2(X/value.X,Y/value.Y);
    }

    Vector2 Vector2::operator+=(Vector2 value){
        X+=value.X;
        Y+=value.Y;
        return *this;
    }

    Vector2 Vector2::operator+(Vector2 value){
        return Vector2(X+value.X,Y+value.Y);
    }

    Vector2 Vector2::operator-=(Vector2 value){
        X-=value.X;
        Y-=value.Y;
        return *this;
    }

    Vector2 Vector2::operator-(Vector2 value){
        return Vector2(X-value.X,Y-value.Y);
    }

    bool Vector2::operator==(Vector2 value){
       return (X==value.X && Y==value.Y);
    }


    bool Vector2::operator!=(Vector2 value){
       return (X!=value.X || Y!=value.Y);
    }

    Vector2 Vector2::operator*=(Vector2 value){
        X*=value.X;
        Y*=value.Y;
        return *this;
    }

    Vector2 Vector2::operator*=(float value){
        X*=value;
        Y*=value;
        return *this;
    }

    Vector2 Vector2::operator*(float value){
        return Vector2(X*value,Y*value);
    }

    Vector2 Vector2::operator*(Vector2 value){
        return Vector2(X*value.X,Y*value.Y);
    }

 
}
#endif
