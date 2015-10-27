#ifndef unilib_math_MathHelper
#define unilib_math_MathHelper



#include <math.h>
#include <cmath>
#include <cstdlib>
using namespace std;
namespace unilib
{

    class MathHelper{
        // STATIC VALUES
        
  
		public: static const float E;
		public: static const float Log10E;
		public: static const float Log2E;
		public: static const float Pi;
		public: static const float PiOver2;
		public: static const float PiOver4;
		public: static const float TwoPi;
    
        public: static float Barycentric(float value1, float value2, float value3, float amount1, float amount2);
        public: static float CatmullRom(float value1, float value2, float value3, float value4, float amount);
        public: static float Clamp(float value, float min, float max);
        public: static float Distance(float value1, float value2);
        public: static float Hermite(float value1, float tangent1, float value2, float tangent2, float amount);
        public: static float Lerp(float value1, float value2, float amount);
        public: static float Max(float value1, float value2);   
        public: static float Min(float value1, float value2);
        public: static long Mod(long x, long m);
        public: static long NthPrime(long n);
        public: static float SmoothStep(float value1, float value2, float amount);
        public: static float ToDegrees(float radians);
        public: static float ToRadians(float degrees);
		public: static float round(float x);
        public: static float WrapAngle(float angle);
    };
}
#endif