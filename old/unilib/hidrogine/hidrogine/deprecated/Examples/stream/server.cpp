#include "lang/socketStream.cpp"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
using namespace std;
using namespace lang;
  
int main(){
    SocketStream * sd = new SocketStream();
    sd->bind(1991);


    while(true){

        cout << sd->read() << endl;
        char buf[128];
        time_t t = time(NULL);
        sprintf(buf, "server clock: %02ld:%02ld:%02ld",(t/3600)%24,(t/60)%60,t%60);
        
        string message = buf;
        sd->write(message);
    }
    delete sd;
    return 0;
}