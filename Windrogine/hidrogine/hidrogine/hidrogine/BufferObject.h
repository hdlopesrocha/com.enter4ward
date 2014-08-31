#ifndef HIDROGINE_BUFFER_OBJECT
#define HIDROGINE_BUFFER_OBJECT
#include <GL/glew.h>
#include <GL/gl.h>
#include <GL/freeglut.h>
#include "Material.h"
#include "ShaderProgram.h"
#include "../unilib/util/LinkedList.h"

using namespace unilib;
namespace hidrogine {
	class BufferObject {

		private: LinkedList<float> * positions;
		private: LinkedList<float> * normals;
		private: LinkedList<float> * textureCoords;
		private: LinkedList<short> * indexData;
		private: Material * material;
		private: int indexCount;
		private: int vaoId;
		private: int vboiId;
		private: int vboId;
  
		public: Material * getMaterial();
		public: BufferObject();
		public: void setMaterial( Material * f);
		public: void addPosition( float vx,  float vy,  float vz);
		public: void addNormal( float nx,  float ny,  float nz);
		public: void addTextureCoord( float tx,  float ty);
		public: void addIndex( short f);
		public: void buildBuffer();
		public: void bind( ShaderProgram * shader);
		public: void draw( ShaderProgram * shader);
	};
}
#endif
