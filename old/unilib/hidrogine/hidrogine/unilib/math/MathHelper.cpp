#include "MathHelper.h"
#include <math.h>
#include <cmath>
#include <cstdlib>
using namespace std;
namespace unilib
{


  
	const float MathHelper::E = 2.71828183f;
	const float MathHelper::Log10E = 0.4342945f;
	const float MathHelper::Log2E = 1.442695f;
	const float MathHelper::Pi = 3.141592f;
	const float MathHelper::PiOver2 = (3.141592f / 2.0f);
	const float MathHelper::PiOver4 = (3.141592f / 4.0f);
	const float MathHelper::TwoPi = (3.141592f * 2.0f);
    
    float MathHelper::Barycentric(float value1, float value2, float value3, float amount1, float amount2)
    {
        return value1 + (value2 - value1) * amount1 + (value3 - value1) * amount2;
    }

    float MathHelper::CatmullRom(float value1, float value2, float value3, float value4, float amount)
    {
        // Using formula from http://www.mvps.org/directx/articles/catmull/
        // Internally using doubles not to lose precission
        double amountSquared = amount * amount;
        double amountCubed = amountSquared * amount;
        return (float)(0.5 * (2.0 * value2 +
            (value3 - value1) * amount +
            (2.0 * value1 - 5.0 * value2 + 4.0 * value3 - value4) * amountSquared +
            (3.0 * value2 - value1 - 3.0 * value3 + value4) * amountCubed));
    }

    float MathHelper::Clamp(float value, float min, float max)
    {
        // First we check to see if we're greater than the max
        value = (value > max) ? max : value;

        // Then we check to see if we're less than the min.
        value = (value < min) ? min : value;

        // There's no check to see if min > max.
        return value;
    }
        
    float MathHelper::Distance(float value1, float value2)
    {
        return ::abs(value1 - value2);
    }
        
    float MathHelper::Hermite(float value1, float tangent1, float value2, float tangent2, float amount)
    {
        // All transformed to double not to lose precission
        // Otherwise, for high numbers of param:amount the result is NaN instead of Infinity
        double v1 = value1, v2 = value2, t1 = tangent1, t2 = tangent2, s = amount, result;
        double sCubed = s * s * s;
        double sSquared = s * s;

        if (amount == 0)
            result = value1;
        else if (amount == 1)
            result = value2;
        else
            result = (2 * v1 - 2 * v2 + t2 + t1) * sCubed +
                (3 * v2 - 3 * v1 - 2 * t1 - t2) * sSquared +
                t1 * s +
                v1;
        return (float)result;
    }
        
    float MathHelper::Lerp(float value1, float value2, float amount)
    {
        return value1 + (value2 - value1) * amount;
    }

    float MathHelper::Max(float value1, float value2)
    {
        return value1 > value2 ? value1 : value2;
    }
        
    float MathHelper::Min(float value1, float value2)
    {
        return value1 < value2 ? value1 : value2;
    }
        
    long MathHelper::Mod(long x, long m) {
        long r = x%m;
        return r<0 ? r+m : r;
    }

    long MathHelper::NthPrime(long n){
        bool isPrime;
        long num = 1;
        while(n!=0){
            isPrime = true;

            for (long i = 2; i <= sqrt((float)num); ++i)
            {
                if((num%i)==0){
                    isPrime=false;
                    break;
                }
            }

            if(isPrime) --n;
            if(n!=0)    ++num;
        }
        return num;
    }

    float MathHelper::SmoothStep(float value1, float value2, float amount)
    {
        // It is expected that 0 < amount < 1
        // If amount < 0, return value1
        // If amount > 1, return value2
        float result = Clamp(amount, 0, 1);
        result = Hermite(value1, 0, value2, 0, result);
        return result;
    }
        
	float MathHelper::ToDegrees(float radians)
    {
        // This method uses double precission internally,
        // though it returns single float
        // Factor = 180 / pi
        return (float)(radians * 57.295779513082320876798154814105);
    }
        
    float MathHelper::ToRadians(float degrees)
    {
        // This method uses double precission internally,
        // though it returns single float
        // Factor = pi / 180
        return (float)(degrees * 0.017453292519943295769236907684886);
    }

	float MathHelper::round(float x)
	{
		return x >= 0.0f ? floorf(x + 0.5f) : ceilf(x - 0.5f);
	}
        
	float MathHelper::WrapAngle(float angle)
    {
        angle = angle - (TwoPi * round(angle / TwoPi));
        if (angle <= -Pi)
        {
            angle += TwoPi;
            return angle;
        }
        if (angle > Pi)
        {
            angle -= TwoPi;
        }
        return angle;
    }
}
