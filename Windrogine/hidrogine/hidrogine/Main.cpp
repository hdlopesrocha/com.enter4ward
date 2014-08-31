#pragma comment(lib, "glew32.lib")
#include <math.h>
#include <stdio.h>
#include <GL/glew.h>
#include <GL/gl.h>
#include <GL/freeglut.h>
#include <fstream>
#include <string>
#include <iostream>
#include "hidrogine/Game.h"
using namespace std;
using namespace hidrogine;
using namespace unilib;

class MyGame : public Game
{
	GLuint VBO;
	float t;

	public: MyGame(int w, int h):Game(w,h){
			t=0;
	}
		
	public: void setup(){
		Vector3 Vertices[3];
		Vertices[0] = Vector3(-1.0f, -1.0f, 0.0f);
		Vertices[1] = Vector3(1.0f, -1.0f, 0.0f);
		Vertices[2] = Vector3(0.0f, 1.0f, 0.0f);

 		glGenBuffers(1, &VBO);
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, sizeof(Vertices), Vertices, GL_STATIC_DRAW);
	}

	public: void update(){
		t+=0.01f;

	}

	public: void draw(){
				glUseProgram(0);
		glClearColor(1.0f, (cos(t)+1.0f)/2.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		
		glEnableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
		glDrawArrays(GL_TRIANGLES, 0, 3);
		glDisableVertexAttribArray(0);	
		
	}
};




int main(int argc, char** argv)
{
	Game::start(new MyGame(800,600));
    return 0;
}


