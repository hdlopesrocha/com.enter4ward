#ifndef unilib_html_Head
#define unilib_html_Head

#include "Tag.cpp"

using namespace std;
namespace unilib {
	class Head : public Tag
	{
		public: Head(): Tag("head"){
			
		}
		
		public: Head(std::initializer_list<Text*> list) : Tag("head",list){
		
		}
	};



}

#endif

