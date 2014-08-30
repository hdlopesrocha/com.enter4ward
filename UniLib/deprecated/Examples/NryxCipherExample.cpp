#include <stdio.h>
#include <iostream>
#include <ctime>
#include "../Security/NryxCipher.cpp"
#include <string>
#include <sstream>
#include <cstring>
#include <signal.h>
using namespace Security;
using namespace std;

#define ENCRYPT 1
#define DECRYPT 2

int c;
string message;
int mode =0;
NryxCipher * cipher;



void Execute(int sig){
    if(mode==ENCRYPT)
        cout << cipher->Encrypt(message);
    else if(mode==DECRYPT)
       cout << cipher->Decrypt(message);
    delete cipher;
    exit(0);
}


int main(int argc, char *argv[]){
    cipher = new NryxCipher("nryx2012");

    signal(SIGABRT,&Execute);//If program aborts go to assigned function "Execute".
    signal(SIGTERM,&Execute);//If program terminates go to assigned function "Execute".
    signal(SIGINT, &Execute);
     if(argc>1){
        if(!strcmp(argv[1],"-e"))
            mode = ENCRYPT;
        else if(!strcmp(argv[1],"-d"))
             mode = DECRYPT;
    }


    while ((c=getchar())!=EOF){
        message = message + (char)c;
    }

    Execute(0);

    return 0;
}