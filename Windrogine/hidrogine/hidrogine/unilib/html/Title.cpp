#ifndef unilib_html_Title
#define unilib_html_Title

#include "Tag.cpp"

using namespace std;
namespace unilib {
	class Title : public Tag
	{
		public: Title(string c): Tag("title"){
			content = c;	
		}
	};



}

#endif

