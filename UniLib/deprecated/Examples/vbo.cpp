#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <ctime>
#include "graphic/openGL/openGL.cpp"
#include "graphic/objectLoader.cpp"
#include "Security/NryxCipher.cpp"
#include "Math/Vector3.cpp"
#include <string>
#include <sstream>

using namespace std;
using namespace math;

using namespace Security;

using namespace graphic;
unsigned int vbo_data, vbo_index;
Graphic * g;
  
float arr[]= {  0,2,-4,1,0,0,
                -2,-2,-4,0,1,0,
                2,-2,-4,0,0,1};
unsigned int ind[]={0,1,2};


void init(){
    // vertex data
    glGenBuffers(1,&vbo_data);
    glBindBuffer(GL_ARRAY_BUFFER, vbo_data);
    glBufferData(GL_ARRAY_BUFFER, sizeof(arr),arr,GL_STATIC_DRAW);
    // index data
    glGenBuffers(1,&vbo_index);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo_index);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(ind),ind,GL_STATIC_DRAW);
}

void loop(){
    g->clear();
    glLoadIdentity();
    gluLookAt (0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

    // bind
    glBindBuffer(GL_ARRAY_BUFFER, vbo_data);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo_index);

    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_COLOR_ARRAY);

    glVertexPointer(3,GL_FLOAT, 6*sizeof(float), 0);
    glColorPointer(3,GL_FLOAT, 6*sizeof(float), (void*)(3*sizeof(float)));
    glDrawElements(GL_TRIANGLES,3,GL_UNSIGNED_INT,0);

    glDisableClientState(GL_COLOR_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);

    // unbind
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    g->flush();
}

int main(int argc, char **argv){

    // NryxCipher * cipher = new NryxCipher("1991hdlr");
    // cipher->CipherFile("name.txt", "junk.txt", TOGGLE);
    // cipher->CipherFile("junk.txt", "back.txt", TOGGLE);
    // cout << Vector3::UnitX().ToString() << endl ;

    ObjectLoader * ol = new ObjectLoader("teapot.obj");


    g = new OpenGL(argc, argv,800, 600,&init,&loop, "lol");
    g->init();


    return 0;
}