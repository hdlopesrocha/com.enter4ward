#include "Graphic.hpp"

namespace unilib {
	
	Object::Object(Vector3 pos, Model * mod){
		model = mod;
		position = pos;
		yaw = pitch = roll = 0;
		UpdateMatrix();
	}

	void Object::UpdateMatrix(){
		matrix = Matrix::CreateFromYawPitchRoll(yaw, pitch, roll);
		matrix *= Matrix::CreateTranslation(position);
	}

}	   