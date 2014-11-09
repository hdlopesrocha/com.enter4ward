#include "lang/socketDatagram.cpp"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
using namespace std;
using namespace lang;

int main(){
    SocketDatagram * sd = new SocketDatagram();
    sd->find("127.0.0.1", 1991);

    while(true){
        sd->write("hack");
    }
    delete sd;
    return 0;
}