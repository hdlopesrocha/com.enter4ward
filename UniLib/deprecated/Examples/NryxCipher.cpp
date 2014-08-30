#include <stdio.h>
#include <iostream>
#include <ctime>
#include "Collection/Queue.cpp"
#include "Collection/Stack.cpp"
#include "Collection/Node.cpp"
#include "Collection/LinkedList.cpp"
#include "Security/NryxCipher.cpp"
#include "Math/Math.cpp"
#include "Utility/Utility.cpp"

#include <string>
#include <sstream>
using namespace Security;
using namespace Collection;
using namespace std;
using namespace Maths;
using namespace Utilities;

int main(){

    string pass = "a123hdsjhb";
    NryxCipher * cipher = new NryxCipher(pass);
    
    int c;
    string message;

//    while ((c=getchar())!=EOF){
//        message = message + (char)c;
//    }
 //   cout << "====== ENCRYPT ======" << endl; 
 //   cout << cipher->Encrypt(message);//<< endl;
 //   cout << "====== DECRYPT ======" << endl;
 //   cout << cipher->Decrypt(cipher->Encrypt(message));

    cipher->EncryptFile("test.jpg","out");
    cipher->DecryptFile("out","in.jpg");

  //  PrimeCache * pc = new PrimeCache(256);
  //  pc->Print(); 

    delete cipher;

    return 0;
}