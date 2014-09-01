#ifndef HIDROGINE_MODEL3D
#define HIDROGINE_MODEL3D

#include "../unilib/util/LinkedList.h"
#include "../unilib/util/TreeMap.h"
#include "DrawHandler.h"
#include "Group.h"
#include "Material.h"

using namespace unilib;

namespace hidrogine {
	class Model3D  {
		public: LinkedList<Group*> * groups; 
		public: TreeMap<string, Material> * materials;
		private: void loadMaterials(string filename);
		private: void loadGeometry(string filename, float scale);
		public: Model3D(string materials, string geometry, float scale);
		public: void draw(ShaderProgram * shader);
		public: void draw(ShaderProgram * shader, DrawHandler * handler);
		public: void drawBoxs(ShaderProgram * shader);
	};
}
#endif