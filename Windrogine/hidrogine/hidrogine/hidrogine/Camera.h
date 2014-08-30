#ifndef HIDROGINE_CAMERA
#define HIDROGINE_CAMERA
#include "../unilib/math/Math.hpp"
using namespace unilib;
namespace hidrogine {

	class Camera {

		private: Quaternion rotation;
		private: Vector3 position;
		private: Matrix projectionMatrix;
		private: Matrix viewMatrix;
		private: int width, height;
		private: Vector3 negativePos;
		public: Matrix getViewMatrix();

				
		public: Camera(int w, int h);
		public: void rotate(float x, float y, float z, float w);
		public: int getWidth();
		public: int getHeight();
		public: void lookAt(float posX, float posY, float posZ, float lookAtX,float lookAtY, float lookAtZ);
		public: void move(Vector3 change);
		public: Matrix getProjectionMatrix();
		public: void move(float front, float down, float left);
		public: Vector3 getPosition();

	};
}

#endif