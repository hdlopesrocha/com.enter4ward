#define DEBUG

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <ctime>
#include "api/openGL/openGL.hpp"
#include "api/openGL/OGLModel.hpp"
#include "framework/hidrogine.hpp"
#include <string>
#include <sstream>

using namespace std;
using namespace framework;
using namespace api;

OpenGL * g;
Camera cam;
ViewPort * viewPort = new ViewPort(1280,720);
Cube * cube = new Cube(8);
Vector3 LightPosition  = Vector3(1.0f, 0.0f, 0.0f);
GLfloat LightAmbient[]  = {0.2f, 0.2f, 0.2f, 1.0f};
GLfloat LightDiffuse[]  = {1.0f, 1.0f, 1.0f, 1.0f};
GLfloat LightSpecular[]  = {1.0f, 1.0f, 1.0f, 1.0f};

void init(){
	cam = Camera(Vector3(0, 1, 3)*8000,3.14/2+0.3,3.14);

	srand(time(NULL));
	Iterator<VertexPositionNormal*> * it = cube->_db->_all->iterator();
	while(it->isValid()){
		VertexPositionNormal * vpn = it->getValue();
		vpn->position = Vector3::Normalize(vpn->position)*(8000+ (rand()%100)/100.0);
		it->next();
	}	

	delete it;


	int sz ;
	glEnable(GL_TEXTURE_2D);
	glLineWidth(4);
	glPointSize(3.0);

	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_LIGHT1);

	glEnable(GL_NORMALIZE);
	glEnable(GL_LIGHTING);
	glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
//glDisable(GL_CULL_FACE);

	glUseProgram(0);
	glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
}

void update(){
	if(g->getSpecialKeyState(GLUT_KEY_UP)==KEY_DOWN) cam.VerticalAngle-=0.02;
	if(g->getSpecialKeyState(GLUT_KEY_DOWN)==KEY_DOWN) cam.VerticalAngle+=0.02;
	if(g->getSpecialKeyState(GLUT_KEY_LEFT)==KEY_DOWN) cam.HorizontalAngle+=0.02;
	if(g->getSpecialKeyState(GLUT_KEY_RIGHT)==KEY_DOWN) cam.HorizontalAngle-=0.02;
	if(g->getKeyState('d')==KEY_DOWN) cam.Position += Vector3::Multiply(cam._side,128);
	if(g->getKeyState('a')==KEY_DOWN) cam.Position += Vector3::Multiply(cam._side,-128);
	if(g->getKeyState('w')==KEY_DOWN) cam.Position += Vector3::Multiply(cam.Direction,128);
	if(g->getKeyState('s')==KEY_DOWN) cam.Position += Vector3::Multiply(cam.Direction,-128);

	cam.UpdateDirection();
	if(g->getKeyState('x')==KEY_DOWN);
	else cam.Update(viewPort);
}

/*
void Trig(Triangle * tri, int level){
	if(tri!=NULL){
		Vector3 center = (tri->_corners[0]->position+tri->_corners[1]->position+tri->_corners[2]->position)/3;



		float d0 = (tri->_corners[0]->position - cam.Position).Length();
		float d1 = (tri->_corners[1]->position - cam.Position).Length();
		float d2 = (tri->_corners[2]->position - cam.Position).Length();


		float dist = (d0 < d1 && d0 < d2) ? d0 : d1 < d2 ? d1 : d2;



		float trisz = (tri->_corners[0]->position-tri->_corners[1]->position).Length();



		if(tri->_sub==NULL || level > 32*trisz/dist){
			for (int i = 0; i < 3 ; ++i){
				VertexPositionNormal * vpn = tri->_corners[i];
				glNormal3f(vpn->position.X,vpn->position.Y,vpn->position.Z);
				glVertex3f(vpn->position.X,vpn->position.Y,vpn->position.Z);
			}
		}
		else {
			for (int i = 0; i < 4 ; ++i)
			 	Trig(tri->_sub[i], level+1);
		}


	}
}*/


void Quad(Cube::Face * tri, int level){
	if(tri!=NULL){
		Vector3 center = (tri->_corners[0]->position+tri->_corners[1]->position+tri->_corners[2]->position)/3;

		float dist = (center-cam.Position).Length();



		float trisz = (tri->_corners[0]->position-tri->_corners[1]->position).Length();



		if(tri->_sub==NULL || level > 128*trisz/dist){
			for (int i = 0; i < 4 ; ++i){
				VertexPositionNormal * vpn = tri->_corners[i];
				glNormal3fv((float*) &(vpn->position));


				Vector3 vec = Vector3::Normalize(vpn->position);
				vec *= ln(vpn->position.Length());
				glVertex3f(vpn->position.X,vpn->position.Y,vpn->position.Z);
			}
		}
		else {
			for (int i = 0; i < 4 ; ++i)
			 	Quad(tri->_sub[i], level+1);
		}


	}
}

void draw(Camera * camera){
	glLoadIdentity();
	gluLookAt (camera->Position.X, camera->Position.Y, camera->Position.Z,camera->LookAt.X, camera->LookAt.Y, camera->LookAt.Z, 0.0, 1.0, 0.0);
	glClearColor (0.0, 0.0, 0.0, 0.0);
	glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


	LightPosition.Normalize();
    float lp[4]; lp[0]= LightPosition.X; lp[1]= LightPosition.Y; lp[2]= LightPosition.Z; lp[3]= 0.0;
    glLightfv(GL_LIGHT0, GL_POSITION,lp);
    glLightfv(GL_LIGHT0, GL_AMBIENT, LightAmbient);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, LightDiffuse);
    glLightfv(GL_LIGHT0, GL_SPECULAR, LightSpecular);
    glBindTexture(GL_TEXTURE_2D,0);
	glBegin(GL_QUADS);
	
	for(int i=0; i <6 ;++i)
		Quad(cube->_sub[i],0);

	glEnd();
	glutSwapBuffers();
}

void loop(){
	update();
	draw(&cam);
}

int main(int argc, char **argv){
	g = new OpenGL(argc, argv,viewPort,&init,&loop, "UniLib",false);
	g->init();
	return 0;
}
