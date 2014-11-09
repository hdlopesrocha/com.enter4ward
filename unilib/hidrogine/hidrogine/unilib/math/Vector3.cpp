#ifndef unilib_math_Vector3
#define unilib_math_Vector3

#include "Math.hpp"

namespace unilib
{
    Vector3::Vector3(float x, float y, float z){
        X = x;
        Y = y;
        Z = z;
    }

    Vector3::Vector3(){
        X = Y = Z = 0.0;
    }

    Vector3::Vector3(float value){
        X = value;
        Y = value;
        Z = value;
    }

    Vector3::Vector3(Vector2 value, float z){
        X = value.X;
        Y = value.Y;
        Z = z;
    }

    Vector3 Vector3::Zero(){
       	return Vector3(0, 0, 0);
    }

    Vector3 Vector3::One(){
		return Vector3(1, 1, 1);
    }

    Vector3 Vector3::UnitX(){
		return Vector3(1, 0, 0);
    }

    Vector3 Vector3::UnitY(){
		return Vector3(0, 1, 0);
    }

    Vector3 Vector3::UnitZ(){
		return Vector3(0, 0, 1);
    }

    Vector3 Vector3::Up(){
		return Vector3(0, 1, 0);
    }

    Vector3 Vector3::Down(){
		return Vector3(0, -1, 0);
    }

    Vector3 Vector3::Right(){
		return Vector3(1, 0, 0);
    }

    Vector3 Vector3::Left(){
		return Vector3(-1, 0, 0);
    }

    Vector3 Vector3::Forward(){
        return Vector3(0, 0, -1);
    }

    Vector3 Vector3::Backward(){
        return Vector3(0, 0, 1);
    }

    Vector3 Vector3::Add(Vector3 value1, Vector3 value2){
        value1.X += value2.X;
        value1.Y += value2.Y;
        value1.Z += value2.Z;
        return value1;
    }

    Vector3 Vector3::Barycentric(Vector3 value1, Vector3 value2, Vector3 value3, float amount1, float amount2){
        return Vector3(
            MathHelper::Barycentric(value1.X, value2.X, value3.X, amount1, amount2),
            MathHelper::Barycentric(value1.Y, value2.Y, value3.Y, amount1, amount2),
            MathHelper::Barycentric(value1.Z, value2.Z, value3.Z, amount1, amount2));
    }

 
    Vector3 Vector3::CatmullRom(Vector3 value1, Vector3 value2, Vector3 value3, Vector3 value4, float amount){
        return Vector3(
            MathHelper::CatmullRom(value1.X, value2.X, value3.X, value4.X, amount),
            MathHelper::CatmullRom(value1.Y, value2.Y, value3.Y, value4.Y, amount),
            MathHelper::CatmullRom(value1.Z, value2.Z, value3.Z, value4.Z, amount));
    }

    Vector3 Vector3::Clamp(Vector3 value1, Vector3 min, Vector3 max){
        return Vector3(
            MathHelper::Clamp(value1.X, min.X, max.X),
            MathHelper::Clamp(value1.Y, min.Y, max.Y),
            MathHelper::Clamp(value1.Z, min.Z, max.Z));
    }

    Vector3 Vector3::Cross(Vector3 vector1, Vector3 vector2){
        Vector3 result;
        result.X = vector1.Y * vector2.Z - vector2.Y * vector1.Z;
        result.Y = vector2.X * vector1.Z - vector1.X * vector2.Z;
        result.Z = vector1.X * vector2.Y - vector2.X * vector1.Y;
        return result;
    }


    float Vector3::Distance(Vector3 value1, Vector3 value2){
        return (float)sqrt((value1.X - value2.X) * (value1.X - value2.X) +
                 (value1.Y - value2.Y) * (value1.Y - value2.Y) +
                 (value1.Z - value2.Z) * (value1.Z - value2.Z));
    }

    float Vector3::DistanceSquared(Vector3 value1, Vector3 value2){
        return (value1.X - value2.X) * (value1.X - value2.X) +
                 (value1.Y - value2.Y) * (value1.Y - value2.Y) +
                 (value1.Z - value2.Z) * (value1.Z - value2.Z); ;
    }

    Vector3 Vector3::Divide(Vector3 value1, Vector3 value2){
        value1.X /= value2.X;
        value1.Y /= value2.Y;
        value1.Z /= value2.Z;
        return value1;
    }


    Vector3 Vector3::Divide(Vector3 value1, float value2){
        float factor = 1.0f / value2;
        value1.X *= factor;
        value1.Y *= factor;
        value1.Z *= factor;
        return value1;
    }

    float Vector3::Dot(Vector3 vector1, Vector3 vector2){
        return vector1.X * vector2.X + vector1.Y * vector2.Y + vector1.Z * vector2.Z;
    }

    bool Vector3::Equals(Vector3 other){
        return *this == other;
    }

	Vector3 Vector3::Negate(){
		return Vector3(-X,-Y,-Z);
    }

    int Vector3::GetHashCode(){
        return (int)(X + Y + Z);
    }

    Vector3 Vector3::Hermite(Vector3 value1, Vector3 tangent1, Vector3 value2, Vector3 tangent2, float amount){
        value1.X = MathHelper::Hermite(value1.X, tangent1.X, value2.X, tangent2.X, amount);
        value1.Y = MathHelper::Hermite(value1.Y, tangent1.Y, value2.Y, tangent2.Y, amount);
        value1.Z = MathHelper::Hermite(value1.Z, tangent1.Z, value2.Z, tangent2.Z, amount);
        return value1;
    }


    float Vector3::Length(){
        return (float)sqrt((double)(X * X + Y * Y + Z * Z));
    }

    float Vector3::LengthSquared(){
        return X * X + Y * Y + Z * Z;
    }

    Vector3 Vector3::Lerp(Vector3 value1, Vector3 value2, float amount){
        return Vector3(
            MathHelper::Lerp(value1.X, value2.X, amount),
            MathHelper::Lerp(value1.Y, value2.Y, amount),
            MathHelper::Lerp(value1.Z, value2.Z, amount));
    }
           
    Vector3 Vector3::Max(Vector3 value1, Vector3 value2){
        return Vector3(
            MathHelper::Max(value1.X, value2.X),
            MathHelper::Max(value1.Y, value2.Y),
            MathHelper::Max(value1.Z, value2.Z));
    }

    Vector3 Vector3::Min(Vector3 value1, Vector3 value2){
        return Vector3(
            MathHelper::Min(value1.X, value2.X),
            MathHelper::Min(value1.Y, value2.Y),
            MathHelper::Min(value1.Z, value2.Z));
    }


    Vector3 Vector3::Multiply(Vector3 value1, Vector3 value2){
        value1.X *= value2.X;
        value1.Y *= value2.Y;
        value1.Z *= value2.Z;
        return value1;
    }

    Vector3 Vector3::Multiply(Vector3 value1, float scaleFactor){
        value1.X *= scaleFactor;
        value1.Y *= scaleFactor;
        value1.Z *= scaleFactor;
        return value1;
    }

    Vector3 Vector3::Negate(Vector3 value){
        value.X = -value.X;
        value.Y = -value.Y;
        value.Z = -value.Z;
        return value;
    }

    void Vector3::Normalize(){
        float factor = 1.0 / (float)sqrt((double)(X * X + Y * Y + Z * Z));
        X *= factor;
        Y *= factor;
        Z *= factor;
    }

    Vector3 Vector3::Normalize(Vector3 value){
        float factor = 1.0 / (float)sqrt((double)(value.X * value.X + value.Y * value.Y + value.Z * value.Z));
        value.X *= factor;
        value.Y *= factor;
        value.Z *= factor;
        return value;
    }

    Vector3 Vector3::Reflect(Vector3 vector, Vector3 normal){
        float dotTimesTwo = 2.0 * Dot(vector, normal);
        vector.X = vector.X - dotTimesTwo * normal.X;
        vector.Y = vector.Y - dotTimesTwo * normal.Y;
        vector.Z = vector.Z - dotTimesTwo * normal.Z;
        return vector;
    }

    Vector3 Vector3::SmoothStep(Vector3 value1, Vector3 value2, float amount){
        return Vector3(
            MathHelper::SmoothStep(value1.X, value2.X, amount),
            MathHelper::SmoothStep(value1.Y, value2.Y, amount),
            MathHelper::SmoothStep(value1.Z, value2.Z, amount));
    }
    

    Vector3 Vector3::Subtract(Vector3 value1, Vector3 value2){
        value1.X -= value2.X;
        value1.Y -= value2.Y;
        value1.Z -= value2.Z;
        return value1;
    }

   

    Vector3 Vector3::Transform(Vector3 position, Matrix matrix){
        Vector3 result;

        result = Vector3((position.X * matrix.M[0]) + (position.Y * matrix.M[4]) + (position.Z * matrix.M[8]) + matrix.M[12],
                             (position.X * matrix.M[1]) + (position.Y * matrix.M[5]) + (position.Z * matrix.M[9]) + matrix.M[13],
                             (position.X * matrix.M[1]) + (position.Y * matrix.M[6]) + (position.Z * matrix.M[10]) + matrix.M[14]);
 

        return result;
    }


    Vector3 Vector3::Transform(Vector3 value, Quaternion rotation){
        Vector3 u = Vector3(rotation.X, rotation.Y, rotation.Z);
        float s = rotation.W;
        return  u * 2.0f * Vector3::Dot(u, value)  + value * (s*s - Vector3::Dot(u, u))  + Vector3::Cross(u, value) * 2.0f * s;
    }


    Vector3 Vector3::TransformNormal(Vector3 normal, Matrix matrix){
        return Vector3((normal.X * matrix.M[0]) + (normal.Y * matrix.M[4]) + (normal.Z * matrix.M[8]),
                             (normal.X * matrix.M[1]) + (normal.Y * matrix.M[5]) + (normal.Z * matrix.M[9]),
                             (normal.X * matrix.M[2]) + (normal.Y * matrix.M[6]) + (normal.Z * matrix.M[10]));;
    }

    string Vector3::ToString(){
        stringstream out;
        out << "{X:" << X << " Y:" << Y << " Z:" << Z << "}";
        return out.str();
    }

    Vector3 Vector3::operator/=(Vector3 value){
        X/=value.X;
        Y/=value.Y;
        Z/=value.Z;
        return *this;
    }

    Vector3 Vector3::operator/=(float value){
        X/=value;
        Y/=value;
        Z/=value;
        return *this;
    }

    Vector3 Vector3::operator/(float value){
        return Vector3(X/value,Y/value, Z/value);
    }

    Vector3 Vector3::operator/(Vector3 value){
        return Vector3(X/value.X,Y/value.Y, Z/value.Z);
    }


    Vector3 Vector3::operator+=(Vector3 value){
        X+=value.X;
        Y+=value.Y;
        Z+=value.Z;
        return *this;
    }

    Vector3 Vector3::operator+(Vector3 value){
        return Vector3(X+value.X,Y+value.Y, Z+ value.Z);
    }

    Vector3 Vector3::operator-=(Vector3 value){
        X-=value.X;
        Y-=value.Y;
        Z-=value.Z;
        return *this;
    }

	Vector3 Vector3::operator-(){
        return Vector3(-X,-Y,-Z);
    }

    Vector3 Vector3::operator-(Vector3 value){
        return Vector3(X-value.X,Y-value.Y, Z-value.Z);
    }

    bool Vector3::operator==(Vector3 value){
       return (X==value.X && Y==value.Y && Z==value.Z);
    }


    bool Vector3::operator!=(Vector3 value){
       return (X!=value.X || Y!=value.Y || Z!=value.Z);
    }

    Vector3 Vector3::operator*=(Vector3 value){
        X*=value.X;
        Y*=value.Y;
        Z*=value.Z;
        return *this;
    }

    Vector3 Vector3::operator*=(float value){
        X*=value;
        Y*=value;
        Z*=value;
        return *this;
    }

    Vector3 Vector3::operator*(float value){
        return Vector3(X*value,Y*value, Z*value);
    }

    Vector3 Vector3::operator*(Vector3 value){
        return Vector3(X*value.X,Y*value.Y, Z*value.Z);
    }
}
#endif