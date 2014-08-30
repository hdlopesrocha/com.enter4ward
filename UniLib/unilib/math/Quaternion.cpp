#ifndef unilib_math_Quaternion
#define unilib_math_Quaternion

#include "Math.hpp"

namespace unilib
{

    Quaternion::Quaternion()
    {
        X = Y = Z = W =0;
    }
   
    Quaternion::Quaternion(float x, float y, float z, float w)
    {
        X = x;
        Y = y;
        Z = z;
        W = w;
    }
   
   
    Quaternion::Quaternion(Vector3 vectorPart, float scalarPart)
    {
        X = vectorPart.X;
        Y = vectorPart.Y;
        Z = vectorPart.Z;
        W = scalarPart;
    }

    Quaternion Quaternion::Identity()
    {
        return Quaternion(0, 0, 0, 1);
    }


    Quaternion Quaternion::Add(Quaternion quaternion1, Quaternion quaternion2)
    {
        quaternion1.X += quaternion2.X;
        quaternion1.Y += quaternion2.Y;
        quaternion1.Z += quaternion2.Z;
        quaternion1.W += quaternion2.W;
        return quaternion1;
    }


    Quaternion Quaternion::Concatenate(Quaternion value1, Quaternion value2)
    {
        Quaternion quaternion;
        quaternion.X = ((value2.X * value1.W) + (value1.X * value2.W)) + (value2.Y * value1.Z) - (value2.Z * value1.Y);
        quaternion.Y = ((value2.Y * value1.W) + (value1.Y * value2.W)) + (value2.Z * value1.X) - (value2.X * value1.Z);
        quaternion.Z = ((value2.Z * value1.W) + (value1.Z * value2.W)) + (value2.X * value1.Y) - (value2.Y * value1.X);
        quaternion.W = (value2.W * value1.W) - ((value2.X * value1.X) + (value2.Y * value1.Y)) + (value2.Z * value1.Z);
        return quaternion;
    }

    void Quaternion::Conjugate()
    {
        X = -X;
        Y = -Y;
        Z = -Z;
    }

    Quaternion Quaternion::Conjugate(Quaternion value)
    {
        Quaternion quaternion;
        quaternion.X = -value.X;
        quaternion.Y = -value.Y;
        quaternion.Z = -value.Z;
        quaternion.W = value.W;
        return quaternion;
    }

    Quaternion Quaternion::CreateFromYawPitchRoll(float yaw, float pitch, float roll)
    {
        Quaternion quaternion;
        quaternion.X = (((float)cos((double)(yaw * 0.5)) * (float)sin((double)(pitch * 0.5))) * (float)cos((double)(roll * 0.5))) + (((float)sin((double)(yaw * 0.5)) * (float)cos((double)(pitch * 0.5))) * (float)sin((double)(roll * 0.5)));
        quaternion.Y = (((float)sin((double)(yaw * 0.5)) * (float)cos((double)(pitch * 0.5))) * (float)cos((double)(roll * 0.5))) - (((float)cos((double)(yaw * 0.5)) * (float)sin((double)(pitch * 0.5))) * (float)sin((double)(roll * 0.5)));
        quaternion.Z = (((float)cos((double)(yaw * 0.5)) * (float)cos((double)(pitch * 0.5))) * (float)sin((double)(roll * 0.5))) - (((float)sin((double)(yaw * 0.5)) * (float)sin((double)(pitch * 0.5))) * (float)cos((double)(roll * 0.5)));
        quaternion.W = (((float)cos((double)(yaw * 0.5)) * (float)cos((double)(pitch * 0.5))) * (float)cos((double)(roll * 0.5))) + (((float)sin((double)(yaw * 0.5)) * (float)sin((double)(pitch * 0.5))) * (float)sin((double)(roll * 0.5)));
        return quaternion;
    }

    Quaternion Quaternion::CreateFromAxisAngle(Vector3 axis, float angle)
    {
        float sin_a = (float)sin(angle / 2.0);
        return Quaternion(axis.X * sin_a,axis.Y * sin_a,axis.Z * sin_a,(float)cos(angle / 2.0));
    }   

  	Quaternion Quaternion::CreateFromRotationMatrix(Matrix matrix)
    {
        Quaternion result;
        if ((matrix.M[0] + matrix.M[5] + matrix.M[10]) > 0.0)
        {
            float M1 = (float)sqrt((double)(matrix.M[0] + matrix.M[5] + matrix.M[10] + 1.0));
            result.W = M1 * 0.5;
            M1 = 0.5 / M1;
            result.X = (matrix.M[6] - matrix.M[9]) * M1;
            result.Y = (matrix.M[8] - matrix.M[2]) * M1;
            result.Z = (matrix.M[1] - matrix.M[4]) * M1;
            return result;
        }
        if ((matrix.M[0] >= matrix.M[5]) && (matrix.M[0] >= matrix.M[10]))
        {
            float M2 = (float)sqrt((double)(1.0 + matrix.M[0] - matrix.M[5] - matrix.M[10]));
            float M3 = 0.5 / M2;
            result.X = 0.5 * M2;
            result.Y = (matrix.M[1] + matrix.M[4]) * M3;
            result.Z = (matrix.M[2] + matrix.M[8]) * M3;
            result.W = (matrix.M[6] - matrix.M[9]) * M3;
            return result;
        }
        if (matrix.M[5] > matrix.M[10])
        {
            float M4 = (float)sqrt((double)(1.0 + matrix.M[5] - matrix.M[0] - matrix.M[10]));
            float M5 = 0.5 / M4;
            result.X = (matrix.M[4] + matrix.M[1]) * M5;
            result.Y = 0.5 * M4;
            result.Z = (matrix.M[9] + matrix.M[6]) * M5;
            result.W = (matrix.M[8] - matrix.M[2]) * M5;
            return result;
        }
        float M6 = (float)sqrt((double)(1.0 + matrix.M[10] - matrix.M[0] - matrix.M[5]));
        float M7 = 0.5 / M6;
        result.X = (matrix.M[8] + matrix.M[2]) * M7;
        result.Y = (matrix.M[9] + matrix.M[6]) * M7;
        result.Z = 0.5 * M6;
        result.W = (matrix.M[1] - matrix.M[4]) * M7;
        return result;
    }

    Quaternion Quaternion::Divide(Quaternion quaternion1, Quaternion quaternion2)
    {
        Quaternion result;

        float w5 = 1.0 / ((quaternion2.X * quaternion2.X) + (quaternion2.Y * quaternion2.Y) + (quaternion2.Z * quaternion2.Z) + (quaternion2.W * quaternion2.W));
        float w4 = -quaternion2.X * w5;
        float w3 = -quaternion2.Y * w5;
        float w2 = -quaternion2.Z * w5;
        float w1 = quaternion2.W * w5;

        result.X = (quaternion1.X * w1) + (w4 * quaternion1.W) + ((quaternion1.Y * w2) - (quaternion1.Z * w3));
        result.Y = (quaternion1.Y * w1) + (w3 * quaternion1.W) + ((quaternion1.Z * w4) - (quaternion1.X * w2));
        result.Z = (quaternion1.Z * w1) + (w2 * quaternion1.W) + ((quaternion1.X * w3) - (quaternion1.Y * w4));
        result.W = (quaternion1.W * quaternion2.W * w5) - ((quaternion1.X * w4) + (quaternion1.Y * w3) + (quaternion1.Z * w2));
        return result;
    }

    float Quaternion::Dot(Quaternion quaternion1, Quaternion quaternion2)
    {
        return (quaternion1.X * quaternion2.X) + (quaternion1.Y * quaternion2.Y) + (quaternion1.Z * quaternion2.Z) + (quaternion1.W * quaternion2.W);
    }



    bool Quaternion::Equals(Quaternion other)
    {
        if ((X == other.X) && (Y == other.Y) && (Z == other.Z))
            return W == other.W;
        return false;  
    }


    int Quaternion::GetHashCode()
    {
        return X + Y + Z + W;
    }


    Quaternion Quaternion::Inverse(Quaternion quaternion)
    {
        Quaternion result;
        float m1 = 1.0 / ((quaternion.X * quaternion.X) + (quaternion.Y * quaternion.Y) + (quaternion.Z * quaternion.Z) + (quaternion.W * quaternion.W));
        result.X = -quaternion.X * m1;
        result.Y = -quaternion.Y * m1;
        result.Z = -quaternion.Z * m1;
        result.W = quaternion.W * m1;
        return result;
    }

    float Quaternion::Length()
    {
        return (float)sqrt((double)((X * X) + (Y * Y) + (Z * Z) + (W * W)));
    }


    float Quaternion::LengthSquared()
    {
        return (X * X) + (Y * Y) + (Z * Z) + (W * W);
    }



    Quaternion Quaternion::Lerp(Quaternion quaternion1, Quaternion quaternion2, float amount)
    {
        Quaternion result;
        float f2 = 1.0 - amount;
        if (((quaternion1.X * quaternion2.X) + (quaternion1.Y * quaternion2.Y) + (quaternion1.Z * quaternion2.Z) + (quaternion1.W * quaternion2.W)) >= 0.0)
        {
            result.X = (f2 * quaternion1.X) + (amount * quaternion2.X);
            result.Y = (f2 * quaternion1.Y) + (amount * quaternion2.Y);
            result.Z = (f2 * quaternion1.Z) + (amount * quaternion2.Z);
            result.W = (f2 * quaternion1.W) + (amount * quaternion2.W);
        }
        else
        {
            result.X = (f2 * quaternion1.X) - (amount * quaternion2.X);
            result.Y = (f2 * quaternion1.Y) - (amount * quaternion2.Y);
            result.Z = (f2 * quaternion1.Z) - (amount * quaternion2.Z);
            result.W = (f2 * quaternion1.W) - (amount * quaternion2.W);
        }
        float f4 = (result.X * result.X) + (result.Y * result.Y) + (result.Z * result.Z) + (result.W * result.W);
        float f3 = 1.0 / (float)sqrt((double)f4);
        result.X *= f3;
        result.Y *= f3;
        result.Z *= f3;
        result.W *= f3;
        return result;
    }

    Quaternion Quaternion::Slerp(Quaternion quaternion1, Quaternion quaternion2, float amount)
    {
        Quaternion result;
        float q2, q3;

        float q4 = (quaternion1.X * quaternion2.X) + (quaternion1.Y * quaternion2.Y) + (quaternion1.Z * quaternion2.Z) + (quaternion1.W * quaternion2.W);
        bool flag = false;
        if (q4 < 0.0)
        {
            flag = true;
            q4 = -q4;
        }
        if (q4 > 0.999999)
        {
            q3 = 1.0 - amount;
            q2 = flag ? -amount : amount;
        }
        else
        {
            float q5 = (float)acos((double)q4);
            float q6 = (float)(1.0 / sin((double)q5));
            q3 = (float)sin((double)((1.0 - amount) * q5)) * q6;
            q2 = flag ? (float)-sin((double)(amount * q5)) * q6 : (float)sin((double)(amount * q5)) * q6;
        }
        result.X = (q3 * quaternion1.X) + (q2 * quaternion2.X);
        result.Y = (q3 * quaternion1.Y) + (q2 * quaternion2.Y);
        result.Z = (q3 * quaternion1.Z) + (q2 * quaternion2.Z);
        result.W = (q3 * quaternion1.W) + (q2 * quaternion2.W);
        return result;
    }

    Quaternion Quaternion::Subtract(Quaternion quaternion1, Quaternion quaternion2)
    {
        quaternion1.X -= quaternion2.X;
        quaternion1.Y -= quaternion2.Y;
        quaternion1.Z -= quaternion2.Z;
        quaternion1.W -= quaternion2.W;
        return quaternion1;
    }

    Quaternion Quaternion::Multiply(Quaternion quaternion1, Quaternion quaternion2)
    {
        Quaternion result;
        float f12 = (quaternion1.Y * quaternion2.Z) - (quaternion1.Z * quaternion2.Y);
        float f11 = (quaternion1.Z * quaternion2.X) - (quaternion1.X * quaternion2.Z);
        float f10 = (quaternion1.X * quaternion2.Y) - (quaternion1.Y * quaternion2.X);
        float f9 = (quaternion1.X * quaternion2.X) + (quaternion1.Y * quaternion2.Y) + (quaternion1.Z * quaternion2.Z);
        result.X = (quaternion1.X * quaternion2.W) + (quaternion2.X * quaternion1.W) + f12;
        result.Y = (quaternion1.Y * quaternion2.W) + (quaternion2.Y * quaternion1.W) + f11;
        result.Z = (quaternion1.Z * quaternion2.W) + (quaternion2.Z * quaternion1.W) + f10;
        result.W = (quaternion1.W * quaternion2.W) - f9;
        return result;
    }


    Quaternion Quaternion::Multiply(Quaternion quaternion1, float scaleFactor)
    {
        quaternion1.X *= scaleFactor;
        quaternion1.Y *= scaleFactor;
        quaternion1.Z *= scaleFactor;
        quaternion1.W *= scaleFactor;
        return quaternion1;
    }

    Quaternion Quaternion::Negate(Quaternion quaternion)
    {
        Quaternion result;
        result.X = -quaternion.X;
        result.Y = -quaternion.Y;
        result.Z = -quaternion.Z;
        result.W = -quaternion.W;
        return result;
    }

    void Quaternion::Normalize()
    {
        float f1 = 1.0 / (float)sqrt((double)((X * X) + (Y * Y) + (Z * Z) + (W * W)));
        X *= f1;
        Y *= f1;
        Z *= f1;
        W *= f1;
    }


    Quaternion Quaternion::Normalize(Quaternion quaternion)
    {
        Quaternion result;
        float f1 = 1.0 / (float)sqrt((double)((quaternion.X * quaternion.X) + (quaternion.Y * quaternion.Y) + (quaternion.Z * quaternion.Z) + (quaternion.W * quaternion.W)));
        result.X = quaternion.X * f1;
        result.Y = quaternion.Y * f1;
        result.Z = quaternion.Z * f1;
        result.W = quaternion.W * f1;
        return result;
    }

    string Quaternion::ToString(){
        stringstream out;

        out << "{X:" << X  << " Y:" << Y << " Z:" << Z << " W:" << W << "}";
        return out.str();
    }

    Quaternion Quaternion::operator/=(Quaternion value){
        *this = Quaternion::Divide(*this, value);
        return *this;
    }


    Quaternion Quaternion::operator/(Quaternion value){
        return Quaternion::Divide(*this, value);
    }


    Quaternion Quaternion::operator+=(Quaternion value){
        X+=value.X;
        Y+=value.Y;
        Z+=value.Z;
        W+=value.W;
        return *this;
    }

    Quaternion Quaternion::operator+(Quaternion value){
        return Quaternion(X+value.X,Y+value.Y, Z+ value.Z, W+ value.W);
    }

    Quaternion Quaternion::operator-=(Quaternion value){
        X-=value.X;
        Y-=value.Y;
        Z-=value.Z;
        W-=value.W;
        return *this;
    }

    Quaternion Quaternion::operator-(Quaternion value){
        return Quaternion(X-value.X,Y-value.Y, Z-value.Z, W-value.W);
    }

    bool Quaternion::operator==(Quaternion value){
       return (X==value.X && Y==value.Y && Z==value.Z && W==value.W);
    }


    bool Quaternion::operator!=(Quaternion value){
       return (X!=value.X || Y!=value.Y || Z!=value.Z || W!=value.W);
    }

    Quaternion Quaternion::operator*=(Quaternion value){
        float f12 = (value.Y * Z) - (value.Z * Y);
        float f11 = (value.Z * X) - (value.X * Z);
        float f10 = (value.X * Y) - (value.Y * X);
        float f9 = (value.X * X) + (value.Y * Y) + (value.Z * Z);
        X = (value.X * W) + (X * value.W) + f12;
        Y = (value.Y * W) + (Y * value.W) + f11;
        Z = (value.Z * W) + (Z * value.W) + f10;
        W = (value.W * W) - f9;
        return *this;
    }

    Quaternion Quaternion::operator*=(float value){
        X*=value;
        Y*=value;
        Z*=value;
        W*=value;
        return *this;
    }

    Quaternion Quaternion::operator*(float value){
        return Quaternion(X*value,Y*value, Z*value, W*value);
    }

    Quaternion Quaternion::operator*(Quaternion value){
        float f12 = (value.Y * Z) - (value.Z * Y);
        float f11 = (value.Z * X) - (value.X * Z);
        float f10 = (value.X * Y) - (value.Y * X);
        float f9 = (value.X * X) + (value.Y * Y) + (value.Z * Z);
        X = (value.X * W) + (X * value.W) + f12;
        Y = (value.Y * W) + (Y * value.W) + f11;
        Z = (value.Z * W) + (Z * value.W) + f10;
        W = (value.W * W) - f9;
        return *this;
    }
}
 
#endif