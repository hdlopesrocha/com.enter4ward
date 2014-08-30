#ifndef SHADER_PROGRAM
#define SHADER_PROGRAM

#pragma once

#include <fstream>
#include <string>
#include <vector>
#include "Utils.h"
#include "Camera.h"
#include <GL/glew.h>
#include <GL/gl.h>
#include <GL/freeglut.h>
#include <iostream>
#include "../unilib/math/Math.hpp"
using namespace std;
using namespace unilib;

namespace hidrogine {
	class ShaderProgram
	{
		private: GLuint mPositionHandle;
		private: GLuint mNormalHandle;
		private: GLuint mTextureCoordinateHandle;
		private: GLuint projectionMatrixLocation;
		private: GLuint viewMatrixLocation;
		private: GLuint modelMatrixLocation;
		private: GLuint cameraPositionLocation;
		private: GLuint materialShininessLocation;
		private: GLuint materialAlphaLocation;
		private: GLuint materialSpecularLocation;
		private: GLuint timeLocation;
		private: GLuint opaqueLocation;
		private: GLuint ambientColorLocation;
		private: GLuint diffuseColorLocation;
		private: GLuint lightPositionLocation[10];
		private: GLuint lightSpecularColorLocation[10];
		private: Vector3 lightPositions[10];
		private: Vector3 lightSpecularColor[10];
		private: int stackPointer;
		private: Matrix matrixStack[128];

		private: GLuint program;

		public:	ShaderProgram(string vsFilename, string fsFilename);
		public:	~ShaderProgram(void);

		public: void use();
		public: void update(Camera * camera);
		public: void setMaterialAlpha(float value);
		public: void setMaterialShininess(float value);
		public: void setTime(float value);
		public: void setAmbientColor(float r, float g, float b);
		public: void setMaterialSpecular(float r, float g, float b);
		public: void setDiffuseColor(float r, float g, float b);
		public: void setLightPosition(int index, Vector3 lightPosition);
		public: void setLightColor(int index, Vector3 lightColor);
		public: void setOpaque(bool value);
		private: int createShader(string filename, int shaderType);
		private: static string getLogInfo(int obj);
		public: void pushMatrix();
		public: void popMatrix();
		protected: void updateModelMatrix();
		public: void setIdentity();
		public: Matrix getModelMatrix();
	};
}

#endif