#ifndef unilib_html_InputSubmit
#define unilib_html_InputSubmit
#include "Text.cpp"
#include <iostream>
#include <string>
#include <queue>
#include <sstream>
#include <csignal>
#include <stdlib.h>

using namespace std;
namespace unilib {
	class InputSubmit : public Text
	{
		
		
		public: InputSubmit(string n) : Text("<input type='submit' value='"+n+"'>"){
			
		
		}
		
		
	};



}

#endif

