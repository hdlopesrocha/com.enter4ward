#ifndef SHADER_PROGRAM
#define SHADER_PROGRAM

#pragma once

#include <fstream>
#include <string>
#include <vector>
#include "Utils.h"
#include <GL/glew.h>
#include <GL/gl.h>
#include <GL/freeglut.h>
#include <iostream>

using namespace std;


namespace hidrogine {
	class ShaderProgram
	{
		public:	ShaderProgram(string vsFilename, string fsFilename);
		public:	~ShaderProgram(void);
	};
}

#endif