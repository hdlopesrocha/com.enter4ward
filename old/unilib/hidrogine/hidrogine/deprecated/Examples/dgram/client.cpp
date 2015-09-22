#include "lang/socketDatagram.cpp"
#include <stdlib.h>
using namespace std;
using namespace lang;

int main(){
    SocketDatagram * sd = new SocketDatagram();
    sd->bind(1992);

    sd->find("127.0.0.1", 1991);

    while(true){
        sleep(1);
        sd->write("get");
        system("clear");
        cout << sd->read() << endl; // security fail here, why?
        // create a hacker server, check if srv changes with bruteforce
    }
    delete sd;
    return 0;
}