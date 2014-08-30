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
Planet * planet;
Vector3 LightPosition  = Vector3(1.0f, 0.0f, 0.0f);
GLfloat LightAmbient[]  = {0.2f, 0.2f, 0.2f, 1.0f};
GLfloat LightDiffuse[]  = {1.0f, 1.0f, 1.0f, 1.0f};
GLfloat LightSpecular[]  = {0.2f, 0.2f, 0.2f, 0.8f};
int tex[6] ;
int white;
Region * selReg;

int water,sand,grass,rock,snow;


void init(){
            white = loadTexture("data/white.png");

	planet = new Planet(20,6378135, 
		loadSurface("data/0.png",&tex[0]),
		loadSurface("data/1.png",&tex[1]),
		loadSurface("data/2.png",&tex[2]),
		loadSurface("data/3.png",&tex[3]),
		loadSurface("data/4.png",&tex[4]),
		loadSurface("data/5.png",&tex[5]));
	selReg = planet->_sub[0];


	cam = Camera(Vector3(2, 0, 0)*planet->_radius);
	cam.Rotation = Quaternion::CreateFromYawPitchRoll(MathHelper::Pi,0,0);

    glHint(GL_CLIP_VOLUME_CLIPPING_HINT_EXT,GL_FASTEST);
	srand(time(NULL));
	glEnable(GL_TEXTURE_2D);
	glLineWidth(1);
	glPointSize(1);

	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_LIGHT1);

	//glEnable(GL_NORMALIZE);
	glEnable(GL_LIGHTING);
	//glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
	glEnable(GL_CULL_FACE);

	glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);


	glMaterialfv(GL_FRONT, GL_DIFFUSE, LightDiffuse);
	glMaterialfv(GL_FRONT, GL_AMBIENT, LightAmbient);
	glMaterialfv(GL_FRONT, GL_SPECULAR, LightSpecular);
	glMaterialf(GL_FRONT, GL_SHININESS, 1.0);

	glDepthFunc(GL_LESS);

	glColor3f(1,1,1);

   	water = loadTexture("data/water.png");
    sand = 	loadTexture("data/sand.png");
    grass = loadTexture("data/grass.png");    
    rock = 	loadTexture("data/rock.png"); 
    snow = 	loadTexture("data/snow.png"); 

	glUseProgram(g->_hidroProgram);

	int loc;
    loc = glGetUniformLocation(g->_hidroProgram, "water");	glUniform1i(loc, 1);  
    loc = glGetUniformLocation(g->_hidroProgram, "sand");	glUniform1i(loc, 2);  
    loc = glGetUniformLocation(g->_hidroProgram, "grass");	glUniform1i(loc, 3);
    loc = glGetUniformLocation(g->_hidroProgram, "rock");	glUniform1i(loc, 4);   
    loc = glGetUniformLocation(g->_hidroProgram, "snow");	glUniform1i(loc, 5);   

 
	glActiveTexture(GL_TEXTURE1);
	glBindTexture(GL_TEXTURE_2D,water);

	glActiveTexture(GL_TEXTURE2);  
	glBindTexture(GL_TEXTURE_2D,sand);

	glActiveTexture(GL_TEXTURE3);
	glBindTexture(GL_TEXTURE_2D,grass);
	
	glActiveTexture(GL_TEXTURE4);
	glBindTexture(GL_TEXTURE_2D,rock);

	glActiveTexture(GL_TEXTURE5);
	glBindTexture(GL_TEXTURE_2D,snow);

	glActiveTexture(GL_TEXTURE0);

}



void update(){

	if(g->getSpecialKeyState(GLUT_KEY_UP)==KEY_DOWN) cam.Rotation = Quaternion::CreateFromYawPitchRoll(0,0,+0.01)*cam.Rotation;
	if(g->getSpecialKeyState(GLUT_KEY_DOWN)==KEY_DOWN) cam.Rotation = Quaternion::CreateFromYawPitchRoll(0,0,-0.01)*cam.Rotation;
	if(g->getSpecialKeyState(GLUT_KEY_LEFT)==KEY_DOWN) cam.Rotation = Quaternion::CreateFromYawPitchRoll(+0.01,0,0)*cam.Rotation;
	if(g->getSpecialKeyState(GLUT_KEY_RIGHT)==KEY_DOWN) cam.Rotation = Quaternion::CreateFromYawPitchRoll(-0.01,0,0)*cam.Rotation;

	float dist = abs(((cam.Position).Length() - planet->_radius))/16+4;


	if(g->getKeyState('d')==KEY_DOWN) cam.Position += Vector3::Multiply(cam.Side,dist);
	if(g->getKeyState('a')==KEY_DOWN) cam.Position += Vector3::Multiply(cam.Side,-dist);
	if(g->getKeyState('w')==KEY_DOWN) cam.Position += Vector3::Multiply(cam.Direction,dist);
	if(g->getKeyState('s')==KEY_DOWN) cam.Position += Vector3::Multiply(cam.Direction,-dist);

	if(g->getKeyState('t')==KEY_UP_DOWN && selReg->_neighbor[0]!=NULL)
		selReg = selReg->_neighbor[0];
	if(g->getKeyState('h')==KEY_UP_DOWN && selReg->_neighbor[1]!=NULL)
		selReg = selReg->_neighbor[1];
	if(g->getKeyState('g')==KEY_UP_DOWN && selReg->_neighbor[2]!=NULL)
		selReg = selReg->_neighbor[2];
	if(g->getKeyState('f')==KEY_UP_DOWN && selReg->_neighbor[3]!=NULL)
		selReg = selReg->_neighbor[3];	
	if(g->getKeyState('0')==KEY_UP_DOWN && selReg->_parent!=NULL)
		selReg = selReg->_parent;

	if(g->getKeyState('1')==KEY_UP_DOWN && selReg->_sub!=NULL && selReg->_sub[0]!=NULL)
		selReg = selReg->_sub[0];
	if(g->getKeyState('2')==KEY_UP_DOWN && selReg->_sub!=NULL && selReg->_sub[1]!=NULL)
		selReg = selReg->_sub[1];
	if(g->getKeyState('3')==KEY_UP_DOWN && selReg->_sub!=NULL && selReg->_sub[2]!=NULL)
		selReg = selReg->_sub[2];
	if(g->getKeyState('4')==KEY_UP_DOWN && selReg->_sub!=NULL && selReg->_sub[3]!=NULL)
		selReg = selReg->_sub[3];


	if(g->getKeyState('x')==KEY_DOWN);
	else cam.Update(viewPort);

	g->UpdateKeyboard();
}


void Quad(Region * region, int level, int s){

	if(region!=NULL && cam._viewFrustum.Contains(region->_sphere)!=ContainmentType::Disjoint){
		float trisz = planet->_radius/(pow(2,level-1));
		float dist= (cam.Position-region->Closest(cam.Position)).Length();

		bool visible = false;

		for (int i = 0; i<3 ; ++i){
			Vector3 nrm = region->GetNormal(i);
			if(Vector3::Dot(cam.Direction,nrm)<0.5){
				visible = true;
				break;
			}
		}

		if (!visible)
		{
			return;				
		}


		if(level > 4*trisz/dist || trisz<1 || level>=planet->_levels){
			region->Draw(planet);
		}
		else {
			for (int i = 0; i < 4 ; ++i)
			 	Quad(region->GetRegion(planet,i), level+1,s);
		}
	}
}

void draw(Camera * camera){
	glUseProgram(g->_hidroProgram);
	
	GLint loc = glGetUniformLocation(g->_hidroProgram, "inv_log_far");
	if (loc != -1)
	   glUniform1f(loc, 1.0/log(cam.Position.Length()+1.0));
	else
		cout << "merda"<<endl;


	

	glLoadIdentity();
	//gluLookAt (camera->Position.X, camera->Position.Y, camera->Position.Z,camera->LookAt.X, camera->LookAt.Y, camera->LookAt.Z, camera->Up.X, camera->Up.Y, camera->Up.Z);
	
	glMultMatrixf(camera->_viewMatrix.M);
	glClearColor (0.0, 0.0, 0.0, 0.0);
	glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	LightPosition.Normalize();
    float lp[4]; lp[0]= LightPosition.X; lp[1]= LightPosition.Y; lp[2]= LightPosition.Z; lp[3]= 0.0;
    glLightfv(GL_LIGHT0, GL_POSITION,lp);
    glLightfv(GL_LIGHT0, GL_AMBIENT, LightAmbient);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, LightDiffuse);
    glLightfv(GL_LIGHT0, GL_SPECULAR, LightSpecular);
	
	glPushMatrix();
	glBindTexture(GL_TEXTURE_2D,0);
	Vector3 cent=Vector3::Normalize((selReg->_points[0]+selReg->_points[2])*0.5)*planet->_radius;
	float rad=(selReg->_points[0]-selReg->_points[2]).Length()*0.1;
	//cout << selReg->GetName() << endl;


	glTranslatef(cent.X,cent.Y,cent.Z);
	glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);	
	glutSolidSphere(rad, 16, 8);
	glPopMatrix();

	glPolygonMode(GL_FRONT_AND_BACK,g->getKeyState('m')==KEY_DOWN ? GL_LINE:GL_FILL);

	glBegin(GL_QUADS);
	for(int i=0; i <6 ;++i){
		Quad(planet->_sub[i],0,i);
	}
	glEnd();
	
/*
	glUseProgram(g->_whiteProgram);
	glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);	
	glBegin(GL_QUADS);
	for(int i=0; i <6 ;++i)
		Quad(planet->_sub[i],0, planet->_face[i],i);
	glEnd();

*/
	glutSwapBuffers();
}

void loop(){
	update();
	draw(&cam);
}

int main(int argc, char **argv){

	g = new OpenGL(argc, argv,viewPort,&init,&loop, "UniLib",false);
	g->init(0.01f,1024.0f);
	return 0;
}
