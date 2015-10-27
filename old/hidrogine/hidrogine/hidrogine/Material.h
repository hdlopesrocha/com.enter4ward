#ifndef HIDROGINE_MATERIAL
#define HIDROGINE_MATERIAL
#include <string>
#include <iostream>
#include "TextureLoader.h"
using namespace std;
namespace hidrogine {

	class Material {
		public: float * Ka;
		public: float * Kd;
		public: float * Ks;
		public: float Tf;
		public: float illum;
		public: float d;
		public: float Ns;
		public: float sharpness;
		public: float Ni;
		public: int texture;
		public: string name;
    
		public: Material(string n);
		public: void setTexture(string filename);
		public: string getName();
	};
}
#endif