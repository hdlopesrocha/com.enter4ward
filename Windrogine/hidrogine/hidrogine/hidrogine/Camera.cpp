#include "Camera.h"

namespace hidrogine {
    Matrix Camera::getViewMatrix() {
		Matrix m = Matrix::CreateFromQuaternion(rotation);
		m.Translation(position.Negate());
		return m;
    }


	void Camera::rotate(float x, float y, float z, float w) {
		Quaternion q = Quaternion::CreateFromAxisAngle(Vector3(x,y,z),w);
		rotation=Quaternion::Multiply(q,rotation);
		rotation=Quaternion::Normalize(rotation);
    }

    Camera::Camera(int w, int h) {
        position = Vector3();
		rotation = Quaternion::Identity();
        width = w;
        height = h;
		projectionMatrix = Matrix::CreatePerspective(w, h,0.1f,100.0f);
    }


    int Camera::getWidth() {
        return width;
    }

    int Camera::getHeight() {
        return height;
    }

    void Camera::lookAt(float posX, float posY, float posZ, float lookAtX,
            float lookAtY, float lookAtZ) {

				position= Vector3(posX, posY, posZ);
    }

    void Camera::move(Vector3 change) {
		position+=change;
    }


	Matrix Camera::getProjectionMatrix() {
        return projectionMatrix;
    }


    void Camera::move(float front, float down, float left) {
		Matrix trans = Matrix::CreateFromQuaternion(rotation);
        trans = Matrix::Invert(trans);
        if (front != 0) {
			position += Vector3::Transform(Vector3(0, 0, 1), rotation)*front;
        }

        if (down != 0) {
			position += Vector3::Transform(Vector3(0, 1, 0), rotation)*down;
        }

        if (left != 0) {
			position += Vector3::Transform(Vector3(1, 0, 0), rotation)*left;
        }
    }

    Vector3 Camera::getPosition() {
        // TODO Auto-generated method stub
        return position;
    }


}
