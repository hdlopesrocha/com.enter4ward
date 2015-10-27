#ifndef unilib_html_Href
#define unilib_html_Href

#include "Text.cpp"

using namespace std;
namespace unilib {
	class Href : public Text
	{
		private: string url;
		private: queue<Text*> tags;
		
		public: Href(string u): Text(""){
			url =u;
		}
				
		public: Href(string u,std::initializer_list<Text*> list) : Text(""){
			url =u;
			for(Text* elem : list ){
				tags.push(elem);
			}		
		}
		
		
		public: ~Href(){
			while(tags.size()>0){
				delete tags.front();
				tags.pop();
			}
		}
				
		public: void Add(Text * t){
			tags.push(t);	
		}
		
			
		public: void Add(std::initializer_list<Text*> list){
			for(Text* elem : list ){
				tags.push(elem);
			}	
		}
		
		
		public: virtual string ToString(){
			string ans ="";
			
			ans += "<a href='"+url+"'>"+content;
			while(tags.size()>0){
				ans += tags.front()->ToString();
				tags.pop();
			}
			ans += "</a>";
			
		
			return ans;
		}
		
		
	};



}

#endif

