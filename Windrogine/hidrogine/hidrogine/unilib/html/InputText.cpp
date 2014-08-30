#ifndef unilib_html_InputText
#define unilib_html_InputText
#include "Text.cpp"
#include <iostream>
#include <string>
#include <queue>
#include <sstream>
#include <csignal>
#include <stdlib.h>

using namespace std;
namespace unilib {
	class InputText : public Text
	{
		
		
		public: InputText(string n) : Text("<input type='text' name='"+n+"'>"){
			
		
		}
		
		
	};



}

#endif

