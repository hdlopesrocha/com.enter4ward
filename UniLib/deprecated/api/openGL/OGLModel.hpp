#ifndef GRAPHIC_MOD2
#define GRAPHIC_MOD2

#define NONE_SHADER 0
#define DEFAULT_SHADER 1
#define TOON_SHADER 2
#define DEPTH_SHADER 3
#define NORMAL_SHADER 4
#define HIDRO_SHADER 5

namespace api {
	class OGLModel;
}

		
#include "../../framework/math.hpp"



#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>


#include "../../framework/graphic.hpp"
#include "openGL.hpp"

using namespace std;
using namespace framework;


namespace api {

	class OGLModel : public Model
	{
		public: OGLModel(string dir,string filename, float scale, float rx, float ry, float rz);
		public:	void draw(Object * obj,Camera * cam);
		public: void draw(OpenGL*ogl,int shader, Object * obj, Camera * cam);

	};	


	void renderSphere(float x, float y, float z, float radius,int subdivisions);
	void renderSphere(BoundingSphere bs,int subdivisions);
}




#endif
