#ifndef unilib_html_Text
#define unilib_html_Text

#include <iostream>
#include <string>
#include <queue>
#include <sstream>
#include <csignal>
#include <stdlib.h>
using namespace std;
namespace unilib {
	class Text
	{
		protected: string content;
		
		
		public: Text(string c){
			content = c;
		}
		
		public: void Echo(string c){
			content += c;	
		}
		
	
		public: virtual string ToString(){	
			return content;
		}
		
	};



}

#endif

