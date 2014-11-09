#ifndef unilib_html_Tag
#define unilib_html_Tag
#include "Text.cpp"
#include <iostream>
#include <string>
#include <queue>
#include <sstream>
#include <csignal>
#include <stdlib.h>

#include <initializer_list>

using namespace std;
namespace unilib {
	class Tag : public Text
	{
		private: string name;
		private: queue<Text*> tags;
		
		
		public: Tag(string n) : Text(""){
			name = n;
		
		}
		
		public: Tag(string n,std::initializer_list<Text*> list) : Text(""){
			name = n;
			for(Text* elem : list ){
				tags.push(elem);
			}	
		}
		
		public: ~Tag(){
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
			
			ans += "<"+name+">"+content;
			while(tags.size()>0){
				ans += tags.front()->ToString();
				tags.pop();
			}
			ans += "</"+name+">";
			
		
			return ans;
		}
		
	};



}

#endif

