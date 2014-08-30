#ifndef FRAMEWORK_MATHS
#define FRAMEWORK_MATHS

namespace unilib {
	class Vector2;
	class Vector3;
	class Vector4;
	class Plane;
	class Matrix;
	class Quaternion;
	class BoundingBox;
	class BoundingFrustum;
    class BoundingSphere;
    class Ray;
    class PlaneHelper;
}
#include <string>
#include <cmath>
#include <cstdlib>
#include <iostream>
#include <sstream>
#include <vector>
#include "MathHelper.cpp"
using namespace std;

namespace unilib {
    namespace PowerLineStatus {
        enum PowerLineStatus { Offline, Online, Unknown };
    }
    namespace PlayerIndex {
        enum PlayerIndex { One, Two, Three, Four };
    }
    namespace TargetPlatform {
        enum TargetPlatform { Unknown, Windows, Xbox360, Zune, Linux };
    }
    namespace PlaneIntersectionType {
        enum PlaneIntersectionType { Front, Back, Intersecting };
    }
    namespace ContainmentType {
        enum ContainmentType { Disjoint, Contains, Intersects };
    }    

	class Vector2{
		// ATTRIBUTES
        public: float X,Y;
        // CONSTRUCTORS
        public: Vector2();  
        public: Vector2(float x, float y);
        public: Vector2(float value);
        // STATIC VALUES
        public: static Vector2 Zero();
        public: static Vector2 One();
        public: static Vector2 UnitX();
        public: static Vector2 UnitY();
        // METHODS
        public: static Vector2 Reflect(Vector2 vector, Vector2 normal);
        public: static Vector2 Add(Vector2 value1, Vector2 value2);
        public: static Vector2 Barycentric(Vector2 value1, Vector2 value2, Vector2 value3, float amount1, float amount2);
        public: static Vector2 CatmullRom(Vector2 value1, Vector2 value2, Vector2 value3, Vector2 value4, float amount);
        public: static Vector2 Clamp(Vector2 value1, Vector2 min, Vector2 max);
        public: static float Distance(Vector2 value1, Vector2 value2);       
        public: static float DistanceSquared(Vector2 value1, Vector2 value2);
        public: static Vector2 Divide(Vector2 value1, Vector2 value2);
        public: static Vector2 Divide(Vector2 value1, float divider);
        public: static float Dot(Vector2 value1, Vector2 value2);
        public: bool Equals(Vector2 other);
        public: int GetHashCode();
        public: static Vector2 Hermite(Vector2 value1, Vector2 tangent1, Vector2 value2, Vector2 tangent2, float amount);
        public: float Length();
        public: float LengthSquared();
        public: static Vector2 Lerp(Vector2 value1, Vector2 value2, float amount);
        public: static Vector2 Max(Vector2 value1, Vector2 value2);
        public: static Vector2 Min(Vector2 value1, Vector2 value2);
        public: static Vector2 Multiply(Vector2 value1, Vector2 value2);
        public: static Vector2 Multiply(Vector2 value1, float scaleFactor);
        public: static Vector2 Negate(Vector2 value);
        public: void Normalize();
        public: static Vector2 Normalize(Vector2 value);
        public: static Vector2 SmoothStep(Vector2 value1, Vector2 value2, float amount);
        public: static Vector2 Subtract(Vector2 value1, Vector2 value2);
        public: static Vector2 Transform(Vector2 position, Matrix matrix);
        public: static Vector2 Transform(Vector2 value, Quaternion rotation);
        public: static Vector2 TransformNormal(Vector2 normal, Matrix matrix);        
        public: string ToString();
        public: bool operator==(Vector2 value);
        public: bool operator!=(Vector2 value);
        public: Vector2 operator+(Vector2 value);
        public: Vector2 operator+=(Vector2 value);
        public: Vector2 operator-(Vector2 value);
        public: Vector2 operator-=(Vector2 value);
        public: Vector2 operator/=(float value);
        public: Vector2 operator/=(Vector2 value);
        public: Vector2 operator/(float value);
        public: Vector2 operator/(Vector2 value);
        public: Vector2 operator*=(float value);
        public: Vector2 operator*=(Vector2 value);
        public: Vector2 operator*(float value);
        public: Vector2 operator*(Vector2 value);    
	};

	class Vector3{
		// ATTRIBUTES
		public: float X,Y,Z;
        // CONSTRUCTORS
		public: Vector3();
		public: Vector3(float x, float y, float z);		
    	public: Vector3(float value);
    	public: Vector3(Vector2 value, float z);
        // STATIC VALUES
    	public: static Vector3 Zero();
        public: static Vector3 One();
        public: static Vector3 UnitX();
        public: static Vector3 UnitY();
        public: static Vector3 UnitZ();
        public: static Vector3 Up();
        public: static Vector3 Down();
        public: static Vector3 Right();
        public: static Vector3 Left();
        public: static Vector3 Forward();
        public: static Vector3 Backward();
        // METHODS
        public: static Vector3 Add(Vector3 value1, Vector3 value2);
        public: static Vector3 Barycentric(Vector3 value1, Vector3 value2, Vector3 value3, float amount1, float amount2);
        public: static Vector3 CatmullRom(Vector3 value1, Vector3 value2, Vector3 value3, Vector3 value4, float amount);
        public: static Vector3 Clamp(Vector3 value1, Vector3 min, Vector3 max);
        public: static Vector3 Cross(Vector3 vector1, Vector3 vector2);
        public: static float Distance(Vector3 value1, Vector3 value2);
        public: static float DistanceSquared(Vector3 value1, Vector3 value2);
        public: static Vector3 Divide(Vector3 value1, Vector3 value2);
        public: static Vector3 Divide(Vector3 value1, float value2);
        public: static float Dot(Vector3 vector1, Vector3 vector2);
        public: bool Equals(Vector3 other);
        public: int GetHashCode();
        public: static Vector3 Hermite(Vector3 value1, Vector3 tangent1, Vector3 value2, Vector3 tangent2, float amount);
        public: float Length();
        public: float LengthSquared();
        public: static Vector3 Lerp(Vector3 value1, Vector3 value2, float amount);
        public: static Vector3 Max(Vector3 value1, Vector3 value2);
        public: static Vector3 Min(Vector3 value1, Vector3 value2);
        public: static Vector3 Multiply(Vector3 value1, Vector3 value2);
        public: static Vector3 Multiply(Vector3 value1, float scaleFactor);
        public: static Vector3 Negate(Vector3 value);
        public: void Normalize();
        public: static Vector3 Normalize(Vector3 value);
        public: static Vector3 Reflect(Vector3 vector, Vector3 normal);
        public: static Vector3 SmoothStep(Vector3 value1, Vector3 value2, float amount);
        public: static Vector3 Subtract(Vector3 value1, Vector3 value2);
        public: static Vector3 Transform(Vector3 position, Matrix matrix);
        public: static Vector3 Transform(Vector3 value, Quaternion rotation);
        public: static Vector3 TransformNormal(Vector3 normal, Matrix matrix);
        public: string ToString();
        public: bool operator==(Vector3 value);
        public: bool operator!=(Vector3 value);
        public: Vector3 operator+(Vector3 value);
        public: Vector3 operator+=(Vector3 value);
        public: Vector3 operator-(Vector3 value);
        public: Vector3 operator-=(Vector3 value);
        public: Vector3 operator/=(float value);
        public: Vector3 operator/=(Vector3 value);
        public: Vector3 operator/(float value);
        public: Vector3 operator/(Vector3 value);
        public: Vector3 operator*=(float value);
        public: Vector3 operator*=(Vector3 value);
        public: Vector3 operator*(float value);
        public: Vector3 operator*(Vector3 value);     
	};

	class Vector4{
		// ATTRIBUTES
		public: float X,Y,Z,W;
		// CONSTRUCTORS
        public: Vector4();
        public: Vector4(float x, float y, float z, float w);
        public: Vector4(Vector2 value, float z, float w);
        public: Vector4(Vector3 value, float w);
        public: Vector4(float value);
        // STATIC VALUES
        public: static Vector4 Zero();
        public: static Vector4 One();
        public: static Vector4 UnitX();
        public: static Vector4 UnitY();
        public: static Vector4 UnitZ();
        public: static Vector4 UnitW();
        // METHODS
        public: static Vector4 Add(Vector4 value1, Vector4 value2);
        public: static Vector4 Barycentric(Vector4 value1, Vector4 value2, Vector4 value3, float amount1, float amount2);
        public: static Vector4 CatmullRom(Vector4 value1, Vector4 value2, Vector4 value3, Vector4 value4, float amount);
        public: static Vector4 Clamp(Vector4 value1, Vector4 min, Vector4 max);
        public: static float Distance(Vector4 value1, Vector4 value2);
        public: static float DistanceSquared(Vector4 value1, Vector4 value2);
        public: static Vector4 Divide(Vector4 value1, Vector4 value2);
        public: static Vector4 Divide(Vector4 value1, float divider);
        public: static float Dot(Vector4 vector1, Vector4 vector2);
        public: bool Equals(Vector4 other);
        public: int GetHashCode();
        public: static Vector4 Hermite(Vector4 value1, Vector4 tangent1, Vector4 value2, Vector4 tangent2, float amount);
        public: float Length();
        public: float LengthSquared();
        public: static Vector4 Lerp(Vector4 value1, Vector4 value2, float amount);
        public: static Vector4 Max(Vector4 value1, Vector4 value2);
        public: static Vector4 Min(Vector4 value1, Vector4 value2);
        public: static Vector4 Multiply(Vector4 value1, Vector4 value2);
        public: static Vector4 Multiply(Vector4 value1, float scaleFactor);
        public: static Vector4 Negate(Vector4 value);
        public: void Normalize();
        public: static Vector4 Normalize(Vector4 vector);
        public: static Vector4 SmoothStep(Vector4 value1, Vector4 value2, float amount);
        public: static Vector4 Subtract(Vector4 value1, Vector4 value2);
        public: static Vector4 Transform(Vector2 value, Quaternion rotation);
        public: static Vector4 Transform(Vector3 value, Quaternion rotation);
        public: static Vector4 Transform(Vector4 value, Quaternion rotation);       
        public: static Vector4 Transform(Vector2 position, Matrix matrix);
        public: static Vector4 Transform(Vector3 position, Matrix matrix);
        public: static Vector4 Transform(Vector4 vector, Matrix matrix);
        public: string ToString();
        public: bool operator==(Vector4 value);
        public: bool operator!=(Vector4 value);
        public: Vector4 operator+(Vector4 value);
        public: Vector4 operator+=(Vector4 value);
        public: Vector4 operator-(Vector4 value);
        public: Vector4 operator-=(Vector4 value);
        public: Vector4 operator/=(float value);
        public: Vector4 operator/=(Vector4 value);
        public: Vector4 operator/(float value);
        public: Vector4 operator/(Vector4 value);
        public: Vector4 operator*=(float value);
        public: Vector4 operator*=(Vector4 value);
        public: Vector4 operator*(float value);
        public: Vector4 operator*(Vector4 value);
	};

	class Plane{
		// ATTRIBUTES
		public: float D;
        public: Vector3 Normal;
        // CONSTRUCTORS
        public: Plane();
        public: Plane(Vector4 value);
        public: Plane(Vector3 normal, float d);
        public: Plane(Vector3 a, Vector3 b, Vector3 c);
        public: Plane(float a, float b, float c, float d);
        // METHODS
        public: float Dot(Vector4 value);
        public: float DotCoordinate(Vector3 value);
        public: float DotNormal(Vector3 value);
        public: void Normalize();
        public: static Plane Normalize(Plane value);
        public: bool operator==(Plane value);
        public: bool operator!=(Plane value);
        public: bool Equals(Plane other);
        public: int GetHashCode();
        public: PlaneIntersectionType::PlaneIntersectionType Intersects(BoundingBox box);
        public: PlaneIntersectionType::PlaneIntersectionType Intersects(BoundingFrustum frustum);
        public: PlaneIntersectionType::PlaneIntersectionType Intersects(BoundingSphere sphere);
        public: string ToString();        
	};

	class Matrix{
        // ATTRIBUTES
        public: float M[16];
        // CONSTRUCTORS
        public: Matrix();
        public: Matrix(float m11, float m12, float m13, float m14, float m21, float m22, float m23, float m24,
                              float m31, float m32, float m33, float m34, float m41, float m42, float m43, float m44);
        // STATIC VALUES
        public: static Matrix Identity();       
        // GETs AND SETs     
        public: Vector3 Backward();
        public: void Backward(Vector3 value);
        public: Vector3 Down();
        public: void Down(Vector3 value);                
        public: Vector3 Forward();
        public: void Forward(Vector3 value);               
        public: Vector3 Left();
        public: void Left(Vector3 value);
        public: Vector3 Right();
        public: void Right(Vector3 value);
        public: Vector3 Translation();
        public: void Translation(Vector3 value);
        public: Vector3 Up();
        public: void Up(Vector3 value); 
        // METHODS
        public: static Matrix CreateWorld(Vector3 position, Vector3 forward, Vector3 up);
        public: static Matrix CreateShadow(Vector3 lightDirection, Plane plane);
        public: static Matrix CreateReflection(Plane value);
        public: static Matrix CreateFromYawPitchRoll(float yaw, float pitch, float roll);
        public: static Matrix Transform(Matrix value, Quaternion rotation);
        public: bool Decompose(Vector3 * scale, Quaternion * rotation, Vector3 * translation);
        public: static Matrix Add(Matrix matrix1, Matrix matrix2);
        public: static Matrix CreateBillboard(Vector3 objectPosition, Vector3 cameraPosition,
            Vector3 cameraUpVector, Vector3 * cameraForwardVector);
        public: static Matrix CreateConstrainedBillboard(Vector3 objectPosition, Vector3 cameraPosition,
            Vector3 rotateAxis, Vector3 * cameraForwardVector, Vector3 * objectForwardVector);
        public: static Matrix CreateFromAxisAngle(Vector3 axis, float angle);
        public: static Matrix CreateFromQuaternion(Quaternion quaternion);
        public: static Matrix CreateLookAt(Vector3 cameraPosition, Vector3 cameraTarget, Vector3 cameraUpVector);
        public: static Matrix CreateOrthographic(float width, float height, float zNearPlane, float zFarPlane);
        public: static Matrix CreateOrthographicOffCenter(float left, float right, float bottom, float top, float zNearPlane, float zFarPlane);
        public: static Matrix CreatePerspective(float width, float height, float zNearPlane, float zFarPlane);
        public: static Matrix CreatePerspectiveFieldOfView(float fieldOfView, float aspectRatio, float nearPlaneDistance, float farPlaneDistance);
        public: static Matrix CreatePerspectiveOffCenter(float left, float right, float bottom, float top, float zNearPlane, float zFarPlane);
        public: static Matrix CreateRotationX(float radians);
        public: static Matrix CreateRotationY(float radians);
        public: static Matrix CreateRotationZ(float radians);
        public: static Matrix CreateScale(float scale);
        public: static Matrix CreateScale(float xScale, float yScale, float zScale);
        public: static Matrix CreateScale(Vector3 scales);
        public: static Matrix CreateTranslation(float xPosition, float yPosition, float zPosition);
        public: static Matrix CreateTranslation(Vector3 position);
        public: static Matrix Divide(Matrix matrix1, Matrix matrix2);
        public: static Matrix Divide(Matrix matrix1, float divider);
        public: static Matrix Invert(Matrix matrix);
        public: static Matrix Lerp(Matrix matrix1, Matrix matrix2, float amount);
        public: static Matrix Multiply(Matrix matrix1, Matrix matrix2);
        public: static Matrix Multiply(Matrix matrix1, float factor);
        public: static Matrix Negate(Matrix matrix);
        public: static Matrix Subtract(Matrix matrix1, Matrix matrix2);
        public: static Matrix Transpose(Matrix matrix);
        public: float Determinant();
        public: bool Equals(Matrix other);
        public: bool operator==(Matrix value);
        public: bool operator!=(Matrix value);
        public: Matrix operator+(Matrix value);
        public: Matrix operator+=(Matrix value);
        public: Matrix operator-(Matrix value);
        public: Matrix operator-=(Matrix value);
        public: Matrix operator/=(float value);
        public: Matrix operator/=(Matrix value);
        public: Matrix operator/(float value);
        public: Matrix operator/(Matrix value);
        public: Matrix operator*=(float value);
        public: Matrix operator*=(Matrix value);
        public: Matrix operator*(float value);
        public: Matrix operator*(Matrix value);
        public: int GetHashCode();
        public: string ToString();
	};

	class Quaternion{
		// ATTRIBUTES
		public: float X,Y,Z,W;
		// CONSTRUCTORS
        public: Quaternion();       
        public: Quaternion(float x, float y, float z, float w);
        public: Quaternion(Vector3 vectorPart, float scalarPart);
		// STATIC VALUES
        public: static Quaternion Identity();
		// METHODS
        public: static Quaternion Add(Quaternion quaternion1, Quaternion quaternion2);
        public: static Quaternion Concatenate(Quaternion value1, Quaternion value2);
        public: void Conjugate();
        public: static Quaternion Conjugate(Quaternion value);
        public: static Quaternion CreateFromYawPitchRoll(float yaw, float pitch, float roll);
        public: static Quaternion CreateFromAxisAngle(Vector3 axis, float angle);
        public: static Quaternion CreateFromRotationMatrix(Matrix matrix);
        public: static Quaternion Divide(Quaternion quaternion1, Quaternion quaternion2);
        public: static float Dot(Quaternion quaternion1, Quaternion quaternion2);
        public: bool Equals(Quaternion other);
        public: int GetHashCode();
        public: static Quaternion Inverse(Quaternion quaternion);
        public: float Length();
        public: float LengthSquared();
        public: static Quaternion Lerp(Quaternion quaternion1, Quaternion quaternion2, float amount);
        public: static Quaternion Slerp(Quaternion quaternion1, Quaternion quaternion2, float amount);
        public: static Quaternion Subtract(Quaternion quaternion1, Quaternion quaternion2);
        public: static Quaternion Multiply(Quaternion quaternion1, Quaternion quaternion2);
        public: static Quaternion Multiply(Quaternion quaternion1, float scaleFactor);
        public: static Quaternion Negate(Quaternion quaternion);
        public: void Normalize();
        public: static Quaternion Normalize(Quaternion quaternion);
        public: string ToString();
        public: bool operator==(Quaternion value);
        public: bool operator!=(Quaternion value);
        public: Quaternion operator+(Quaternion value);
        public: Quaternion operator+=(Quaternion value);
        public: Quaternion operator-(Quaternion value);
        public: Quaternion operator-=(Quaternion value);
        public: Quaternion operator/=(Quaternion value);
        public: Quaternion operator/(Quaternion value);
        public: Quaternion operator*=(float value);
        public: Quaternion operator*=(Quaternion value);
        public: Quaternion operator*(float value);
        public: Quaternion operator*(Quaternion value);        
	};

    class BoundingBox
    {
        // ATTRIBUTES
        public: Vector3 Min;
        public: Vector3 Max;
        public: static const int CornerCount = 8;
        // CONSTRUCTORS
        public: BoundingBox();
        public: BoundingBox(Vector3 min, Vector3 max);
        // METHODS
        public: ContainmentType::ContainmentType Contains(BoundingBox box);
        public: ContainmentType::ContainmentType Contains(BoundingFrustum frustum);
        public: ContainmentType::ContainmentType Contains(BoundingSphere sphere);
        public: ContainmentType::ContainmentType Contains(Vector3 point);
        /* 
        public: static BoundingBox CreateFromPoints(vector<Vector3> points);
        */
        public: static BoundingBox CreateFromSphere(BoundingSphere sphere);
        public: static BoundingBox CreateMerged(BoundingBox original, BoundingBox additional);
        public: bool Equals(BoundingBox other);
        public: vector<Vector3> GetCorners();
        public: int GetHashCode();
        public: bool Intersects(BoundingBox box);
        public: bool Intersects(BoundingFrustum frustum);
        public: bool Intersects(BoundingSphere sphere);
        public: PlaneIntersectionType::PlaneIntersectionType Intersects(Plane plane);
        public: float * Intersects(Ray ray);
        public: bool operator==(BoundingBox value);
        public: bool operator!=(BoundingBox value);
        public: string ToString();        
    };

    class BoundingFrustum
    {
        // ATTRIBUTES
        private: Matrix matrix;
        private: Plane planes[6];

        private: vector<Vector3> corners;
        public: static const int CornerCount = 8;
        // CONSTRUCTORS
        public: BoundingFrustum();
        public: BoundingFrustum(Matrix value);
        // METHODS
        public: Plane Bottom();
        public: Plane Far();
        public: Plane Left();
        public: Matrix GetMatrix();
        public: void SetMatrix(Matrix value);        
        public: Plane Near();
        public: Plane Right();
        public: Plane Top();
        public: bool operator==(BoundingFrustum value);
        public: bool operator!=(BoundingFrustum value);
        public: ContainmentType::ContainmentType Contains(BoundingBox box);
        public: ContainmentType::ContainmentType Contains(Vector3 point);
        public: ContainmentType::ContainmentType Contains(BoundingFrustum frustum);
        public: ContainmentType::ContainmentType Contains(BoundingSphere sphere);
        public: bool Equals(BoundingFrustum other);
        public: vector<Vector3> GetCorners();
        public: int GetHashCode();
        public: bool Intersects(BoundingBox box);
        public: bool Intersects(BoundingFrustum frustum);
        public: bool Intersects(BoundingSphere sphere);
        public: PlaneIntersectionType::PlaneIntersectionType Intersects(Plane plane);
        public: float * Intersects(Ray ray);
        /*
        public: string ToString();
        */
        private: void CreateCorners();
        private: void CreatePlanes();
        private: static Vector3 IntersectionPoint(Plane a, Plane b, Plane c);
        private: void NormalizePlane(Plane * p);
    };



    class BoundingSphere
    {
        // ATTRIBUTES
        public: Vector3 Center;
        public: float Radius;
        // CONSTRUCTORS
        public: BoundingSphere();
        public: BoundingSphere(Vector3 center, float radius);
        // METHODS
        public: BoundingSphere Transform(Matrix matrix);
        public: ContainmentType::ContainmentType Contains(BoundingBox box);
        public: ContainmentType::ContainmentType Contains(BoundingFrustum frustum);
        public: ContainmentType::ContainmentType Contains(BoundingSphere sphere);
        public: ContainmentType::ContainmentType Contains(Vector3 point);
        public: static BoundingSphere CreateFromBoundingBox(BoundingBox box);
        public: static BoundingSphere CreateFromFrustum(BoundingFrustum frustum);
        public: static BoundingSphere CreateFromPoints(vector<Vector3> points);
        public: static BoundingSphere CreateMerged(BoundingSphere original, BoundingSphere additional);
        public: bool Equals(BoundingSphere other);
        public: int GetHashCode();
        public: bool Intersects(BoundingBox box);
        public: bool Intersects(BoundingFrustum frustum);
        public: bool Intersects(BoundingSphere sphere);
        public: PlaneIntersectionType::PlaneIntersectionType Intersects(Plane plane);
        public: float * Intersects(Ray ray);
        public: bool operator==(BoundingSphere value);
        public: bool operator!=(BoundingSphere value);
        public: string ToString();
    };

    class Ray
    {
        public: Vector3 Direction;
        public: Vector3 Position;
        public: Ray(Vector3 position, Vector3 direction);
        public: bool Equals(Ray other);
        public: int GetHashCode();
        public: float * Intersects(BoundingBox box);
        public: float * Intersects(BoundingFrustum frustum);
        public: float * Intersects(BoundingSphere sphere);
        public: float Intersects(Plane plane);
        public: bool operator==(Ray value);
        public: bool operator!=(Ray value);
        public: string ToString();
    };

    class PlaneHelper
    {
        public: static float ClassifyPoint(Vector3 point, Plane plane);
        public: static float PerpendicularDistance(Vector3 point, Plane plane);
    };
}
#endif