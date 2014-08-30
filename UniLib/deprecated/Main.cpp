#define DEBUG

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <ctime>
#include "api/openGL/openGL.hpp"
#include "api/openGL/OGLModel.hpp"
#include <string>
#include <sstream>

using namespace std;
using namespace framework;
using namespace api;

OpenGL * g;
OGLModel * ol, * te, *tmod;
Object * obj1, *obj2, * terr;
Vector3 LightPosition  = Vector3(1.0f, 4.0f, 1.0f);
GLfloat LightAmbient[]  = {0.2f, 0.2f, 0.2f, 1.0f};
GLfloat LightDiffuse[]  = {1.0f, 1.0f, 1.0f, 1.0f};
GLfloat LightSpecular[]  = {1.0f, 1.0f, 1.0f, 1.0f};
Camera cam, lightCam;
ViewPort * viewPort = new ViewPort(1280,720);
SDL_Surface * image;
int water;

void init(){
    cam = Camera(Vector3(0, 5, 15),3.14/2+0.3,3.14);
    lightCam = Camera();

    int sz ;
    glEnable(GL_TEXTURE_2D);

    water = loadTexture("water.jpg");
    ol = new OGLModel("models/", "car.obj",0.2,-90,0,0);
    te = new OGLModel("models/", "monkey.obj",0.2,0,0,0);
    tmod = new OGLModel("models/", "terrain.obj",1.0,0,0,0);

    obj1 = new Object(Vector3(-0.5,0.5,0),ol);
    obj2 = new Object(Vector3( 0.5,0.5,0),ol);
    terr = new Object(Vector3( 0,0,0),tmod);

    glLineWidth(4);
    glPointSize(8.0);

    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glDisable(GL_LIGHT1);

    glEnable(GL_NORMALIZE);
    glEnable(GL_LIGHTING);






    glUseProgram(0);
    glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
}

void update(){
    if(g->getSpecialKeyState(GLUT_KEY_UP)==KEY_DOWN)    cam.VerticalAngle-=0.02;
    if(g->getSpecialKeyState(GLUT_KEY_DOWN)==KEY_DOWN)  cam.VerticalAngle+=0.02;
    if(g->getSpecialKeyState(GLUT_KEY_LEFT)==KEY_DOWN)   cam.HorizontalAngle+=0.02;
    if(g->getSpecialKeyState(GLUT_KEY_RIGHT)==KEY_DOWN)  cam.HorizontalAngle-=0.02;
    if(g->getKeyState('d')==KEY_DOWN)     cam.Position += Vector3::Multiply(cam._side,0.1);
    if(g->getKeyState('a')==KEY_DOWN)     cam.Position += Vector3::Multiply(cam._side,-0.1);
    if(g->getKeyState('w')==KEY_DOWN)     cam.Position += Vector3::Multiply(cam.Direction,0.1);
    if(g->getKeyState('s')==KEY_DOWN)     cam.Position += Vector3::Multiply(cam.Direction,-0.1);
    if(g->getKeyState('h')==KEY_DOWN)     LightPosition += Vector3::Multiply(cam._side,0.1);
    if(g->getKeyState('f')==KEY_DOWN)     LightPosition += Vector3::Multiply(cam._side,-0.1);
    if(g->getKeyState('t')==KEY_DOWN)     LightPosition += Vector3::Multiply(cam.Direction,0.1);
    if(g->getKeyState('g')==KEY_DOWN)     LightPosition += Vector3::Multiply(cam.Direction,-0.1);
    if(g->getKeyState('y')==KEY_DOWN)     LightPosition.Y+=0.1;
    if(g->getKeyState('r')==KEY_DOWN)     LightPosition.Y-=0.1;

    cam.UpdateDirection();
    if(g->getKeyState('x')==KEY_DOWN);
    else cam.Update(viewPort);

    lightCam.Direction = Vector3::Normalize(LightPosition*-1.0);
    lightCam.Position = Vector3::Zero();
    lightCam.Update(viewPort);
}

float t=0;
void draw(Camera * camera, bool shadow){
    glLoadIdentity();
    gluLookAt (camera->Position.X, camera->Position.Y, camera->Position.Z,camera->LookAt.X, camera->LookAt.Y, camera->LookAt.Z, 0.0, 1.0, 0.0);
    glClearColor (1.0, 1.0, 1.0, 1.0);
    glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    float lp[4]; lp[0]= LightPosition.X; lp[1]= LightPosition.Y; lp[2]= LightPosition.Z; lp[3]= 0.0;
    glLightfv(GL_LIGHT0, GL_POSITION,lp);
    glLightfv(GL_LIGHT0, GL_AMBIENT, LightAmbient);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, LightDiffuse);
    glLightfv(GL_LIGHT0, GL_SPECULAR, LightSpecular);

    glUseProgram(shadow? g->_depthProgram : 0);
    glBindTexture(GL_TEXTURE_2D, water);
    glBegin(GL_QUADS);
        int sz=10;
        glNormal3f(0,1,0); glTexCoord2f(sz,sz); glVertex3f(sz,0,sz);
        glNormal3f(0,1,0); glTexCoord2f(sz,-sz);glVertex3f(sz,0,-sz);
        glNormal3f(0,1,0); glTexCoord2f(-sz,-sz);glVertex3f(-sz,0,-sz);
        glNormal3f(0,1,0); glTexCoord2f(-sz,sz);glVertex3f(-sz,0,sz);
    glEnd();
    glBindTexture(GL_TEXTURE_2D,0);
    renderSphere(LightPosition.X,LightPosition.Y, LightPosition.Z, 0.05,16);
    
    // car printing
    obj1->yaw +=0.01;
    obj1->UpdateMatrix();

    obj2->yaw +=0.01;
    obj2->UpdateMatrix();

    ol->setDiffuse("color",cos(t)/2+0.5,cos(t)*sin(t),sin(t)/2+0.5);

    // automatic frustum culling
    ((OGLModel*)obj1->model)->draw(g,shadow? DEPTH_SHADER : DEFAULT_SHADER,obj1,camera);   
    ((OGLModel*)obj2->model)->draw(g,shadow? DEPTH_SHADER : DEFAULT_SHADER,obj2,camera);               
    ((OGLModel*)terr->model)->draw(g,shadow? DEPTH_SHADER : DEFAULT_SHADER,terr,camera);               

    t+=0.01;
   
    if(!shadow){
        glUseProgram(0);
        glPushMatrix();
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        gluOrtho2D(0.0, viewPort->Width, 0.0, viewPort->Height);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glDisable(GL_CULL_FACE);
        glDisable(GL_LIGHTING);
        /* Perform simple 2D drawing here */
        glBindTexture(GL_TEXTURE_2D, g->shadowDepth);
        glBegin(GL_QUADS);
            glTexCoord2f(0,0); glVertex2i(0, 0);
            glTexCoord2f(0,1); glVertex2i(0, 256);
            glTexCoord2f(1,1); glVertex2i(256, 256);
            glTexCoord2f(1,0); glVertex2i(256, 0);
        glEnd();
        glEnable(GL_LIGHTING);
        glEnable(GL_CULL_FACE);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glPopMatrix(); 
    }
    glutSwapBuffers();
}

void loop(){
    update();

    glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, g->shadowTarget);
    glUseProgram(g->_depthProgram);

LightPosition = Vector3::Normalize(LightPosition);
glm::vec3 lightInvDir = glm::vec3(LightPosition.X,LightPosition.Y,LightPosition.Z);
 // Compute the MVP matrix from the light's point of view
 glm::mat4 depthProjectionMatrix = glm::ortho<float>(-10,10,-10,10,-10,20);
 glm::mat4 depthViewMatrix = glm::lookAt(lightInvDir, glm::vec3(0,0,0), glm::vec3(0,1,0));
 glm::mat4 depthModelMatrix = glm::mat4(1.0);
 glm::mat4 depthMVP = depthProjectionMatrix * depthViewMatrix * depthModelMatrix;

glm::mat4 biasMatrix(
0.5, 0.0, 0.0, 0.0,
0.0, 0.5, 0.0, 0.0,
0.0, 0.0, 0.5, 0.0,
0.5, 0.5, 0.5, 1.0
);
glm::mat4 depthBiasMVP = biasMatrix*depthMVP;

    // Send our transformation to the currently bound shader,
    // in the "MVP" uniform
    glUniformMatrix4fv(glGetUniformLocation(g->_depthProgram,"depthMVP"), 1, GL_FALSE, &depthMVP[0][0]);


    glUseProgram(g->_program);

    glUniformMatrix4fv(glGetUniformLocation(g->_program,"biasMatrix"), 1, GL_FALSE, &biasMatrix[0][0]);
    glUniformMatrix4fv(glGetUniformLocation(g->_program,"depthBiasMVP"), 1, GL_FALSE, &depthBiasMVP[0][0]);

    glEnable(GL_TEXTURE_2D);
    glUniform1iARB(glGetUniformLocationARB(g->_program, "tex"),0);
    glUniform1iARB(glGetUniformLocationARB(g->_program, "shadowMap"),1);

    glActiveTexture(GL_TEXTURE1);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, g->shadowDepth);
    glActiveTexture(GL_TEXTURE0);


    draw(&cam,true);

    glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    draw(&cam,false);
}

int main(int argc, char **argv){
    g = new OpenGL(argc, argv,viewPort,&init,&loop, "UniLib",false);
    g->init();
    return 0;
}
