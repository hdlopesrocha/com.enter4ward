#ifndef unilib_html_Body
#define unilib_html_Body

#include "Tag.cpp"

using namespace std;
namespace unilib {
	class Body : public Tag
	{
		public: Body(): Tag("body"){
			
		}
		
		public: Body(std::initializer_list<Text*> list) : Tag("body",list){
		
		}
	};



}

#endif

