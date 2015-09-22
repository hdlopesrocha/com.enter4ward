#ifndef unilib_html_Html
#define unilib_html_Html

#include "Tag.cpp"

using namespace std;
namespace unilib {
	class Html : public Tag
	{
		public: Html(): Tag("html"){
			
		}
				
		public: Html(std::initializer_list<Text*> list) : Tag("html",list){
		
		}
		
		
	};



}

#endif

