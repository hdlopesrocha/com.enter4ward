#pragma comment(lib, "glew32.lib")
#include <math.h>
#include <stdio.h>
#include <GL/glew.h>
#include <GL/gl.h>
#include <GL/freeglut.h>
#include "math_3d.h"
#include <fstream>
#include <string>
#include <iostream>
#include "ShaderProgram.h"
using namespace std;
using namespace hidrogine;
GLuint VBO;
float t=0;
static void RenderSceneCB()
{
t+=0.01f;
    glClearColor(1.0f, (cos(t)+1.0f)/2.0f, 0.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT);

    glEnableVertexAttribArray(0);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);

    glDrawArrays(GL_TRIANGLES, 0, 3);

    glDisableVertexAttribArray(0);
    glutSwapBuffers();   // swapping image buffer for double buffering
	glFinish();
    glutPostRedisplay(); // redrawing. Omit this line if you don't want constant redraw
}


static void InitializeGlutCallbacks()
{
    glutDisplayFunc(RenderSceneCB);
}

static void CreateVertexBuffer()
{
    Vector3f Vertices[3];
    Vertices[0] = Vector3f(-1.0f, -1.0f, 0.0f);
    Vertices[1] = Vector3f(1.0f, -1.0f, 0.0f);
    Vertices[2] = Vector3f(0.0f, 1.0f, 0.0f);

 	glGenBuffers(1, &VBO);
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(Vertices), Vertices, GL_STATIC_DRAW);
}


int main(int argc, char** argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE|GLUT_RGBA);
    glutInitWindowSize(1024, 768);
    glutInitWindowPosition(100, 100);
    glutCreateWindow("hidrogine");

    InitializeGlutCallbacks();
	printf("%s\n", glGetString(GL_VERSION));
    // Must be done after glut is initialized!
    GLenum res = glewInit();
    if (res != GLEW_OK) {
      fprintf(stderr, "Error: '%s'\n", glewGetErrorString(res));
      return 1;
    }

    glClearColor(1.0f, 0.0f, 0.0f, 1.0f);

    CreateVertexBuffer();
	ShaderProgram * program = new ShaderProgram("vertex.glsl", "fragment.glsl");
    glutMainLoop();

    return 0;
}


