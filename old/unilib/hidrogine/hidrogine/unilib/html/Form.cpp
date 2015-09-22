#ifndef unilib_html_Form
#define unilib_html_Form

#include "Text.cpp"

using namespace std;
namespace unilib {
	class Form : public Text
	{
		private: string url, method;
		private: queue<Text*> tags;
	
		
		public: Form(string m,string u): Text(""){
			url =u;
			method = m;
		}
				
		public: Form(string m,string u,std::initializer_list<Text*> list) : Text(""){
			url =u;
			method = m;
			for(Text* elem : list ){
				tags.push(elem);
			}		
		}
		
		
		public: ~Form(){
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
			
			ans += "<form action='"+url+"' method='"+method+"'>"+content;
			while(tags.size()>0){
				ans += tags.front()->ToString();
				tags.pop();
			}
			ans += "</form>";
			
		
			return ans;
		}
		
		
	};



}

#endif

