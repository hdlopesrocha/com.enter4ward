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

Graphic * g;
  
float arr[]= {  0,2,-4,1,0,0,
                -2,-2,-4,0,1,0,
                2,-2,-4,0,0,1};
unsigned int ind[]={0,1,2};

void loop(){
    g->clear();
    glLoadIdentity();
    gluLookAt (0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);


    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_COLOR_ARRAY);

    glVertexPointer(3,GL_FLOAT, 6*sizeof(float), arr);
    glColorPointer(3,GL_FLOAT, 6*sizeof(float), &arr[3]);
    glDrawElements(GL_TRIANGLES,3,GL_UNSIGNED_INT,&ind);

    glDisableClientState(GL_COLOR_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);

    g->flush();
}

int main(int argc, char **argv){

    // NryxCipher * cipher = new NryxCipher("1991hdlr");
    // cipher->CipherFile("name.txt", "junk.txt", TOGGLE);
    // cipher->CipherFile("junk.txt", "back.txt", TOGGLE);
    // cout << Vector3::UnitX().ToString() << endl ;

    ObjectLoader * ol = new ObjectLoader("teapot.obj");


    g = new OpenGL(argc, argv,800, 600,loop, "lol");
    g->init();


    return 0;
}