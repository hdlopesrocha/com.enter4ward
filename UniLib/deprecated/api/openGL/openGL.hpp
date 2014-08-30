#ifndef GRAPHIC_OPENGL
#define GRAPHIC_OPENGL

/*
#pragma comment(lib,"glew32.lib")
#pragma comment(lib,"glu32.lib")
#pragma comment(lib,"opengl32.lib")
*/

namespace api {
	class OpenGL; 
}		
#include "../../framework/graphic.hpp"
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <GL/glew.h>	// Header File For The GLu32 Library
#include <GL/glut.h>    // Header File For The GLUT Library 
#include <GL/gl.h>	// Header File For The OpenGL32 Library
#include <GL/glu.h>	// Header File For The GLu32 Library
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>

#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <math.h>
#include <time.h>
#include <string>
#include <sstream>
#include <vector>

#include <ctime>
#include <fstream>

using namespace framework;
using namespace std;
#define KEY_DOWN 1
#define KEY_UP 0
#define KEY_UP_DOWN 2



namespace api {

	class OpenGL 
	{
		public: GLuint shadowTexture, shadowTarget, shadowDepth;
		int _argc;
		char **_argv;
		void (*_drawFunction)();
		void (*_initFunction)();
		string _title;
		bool _fullScreen;
		ViewPort * _viewPort;
		public: unsigned int _toonProgram, _toonLineProgram, _program, _depthProgram, _normalProgram, _hidroProgram, _whiteProgram;
		public: OpenGL(int argc, char **argv, ViewPort * viewPort,void(*initFunction)(),void(*drawFunction)(), string title, bool fullScreen);
		public: char getKeyState(int key);
		public: char getSpecialKeyState(int key);
		public: void init(float near,float far);
		public:	void UpdateKeyboard();

	}; 

	SDL_Surface * loadSurface(const char * filename,int  * tex);
	unsigned int loadTexture(const char * filename);
	void SetupShader(int shader,const char * path);
	Uint32 getPixel(SDL_Surface *surface, int x, int y);
	int getRed(SDL_Surface *surface, int x, int y);
	float getHeight(SDL_Surface *surface, Vector2 coord, float radius);
}




#endif
