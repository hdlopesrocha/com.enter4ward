#ifndef unilib_html_Img
#define unilib_html_Img
#include "Text.cpp"
#include <iostream>
#include <string>
#include <queue>
#include <sstream>
#include <csignal>
#include <stdlib.h>

using namespace std;
namespace unilib {
	class Img : public Text
	{
		private: string src;

		
		
		public: Img(string s) : Text(""){
			src = s;
		
		}
		
		
		public: virtual string ToString(){
			
			return "<img src='"+src+"'/>";
		}
		
	};



}

#endif

