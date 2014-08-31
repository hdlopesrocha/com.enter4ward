#ifndef GAME
#define GAME

#pragma once
#include "ShaderProgram.h"

using namespace std;

namespace hidrogine {
	class Game
	{
		public: ShaderProgram * program;
		public: Camera * camera;

		public: Game(int w, int h);
		public: void useDefaultShader();
		public: void looper();
		public: static void start(Game * g);

		public: virtual void update()=0;

		public: virtual void draw()=0;

		public: virtual void setup()=0;
	};

}

#endif