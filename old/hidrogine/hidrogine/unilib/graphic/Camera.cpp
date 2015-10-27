#include "Graphic.hpp"

namespace unilib
{       
    Camera::Camera(Vector3 position)
    {
        Position = position;
        Up = Vector3::Up();
        Rotation = Quaternion::Identity();

    }

    Camera::Camera()
    {
        Position = Vector3::Zero();
        Up = Vector3::Up();
        Direction = Vector3::Up();
        Rotation = Quaternion::Identity();
    }


    void Camera::Update(ViewPort * viewPort)
    {
        Rotation.Normalize();
        
        Direction = Vector3::Transform(Vector3::UnitX(),Rotation);
        Direction.Normalize();

        Up = Vector3::Transform(Vector3::UnitY(),Rotation);
        Up.Normalize();

        Side = Vector3::Transform(Vector3::UnitZ(),Rotation);
        Side.Normalize();


        // UPDATE MATRICES NEEDED FOR 3D

        _worldMatrix =  Matrix::Identity();
        _viewMatrix = Matrix::CreateLookAt(Position, Direction, Up);
        _projectionMatrix = Matrix::CreatePerspectiveFieldOfView(MathHelper::PiOver4, (float)viewPort->Width/(float)viewPort->Height, 0.1, 10240000);
        _viewFrustum = BoundingFrustum(_viewMatrix * _projectionMatrix);
    }
}
